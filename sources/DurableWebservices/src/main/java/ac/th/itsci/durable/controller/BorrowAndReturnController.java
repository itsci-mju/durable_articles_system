package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.repo.BorrowAndReturnRepository;
import ac.th.itsci.durable.repo.BorrowerRepository;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.StaffRepository;
import ac.th.itsci.durable.service.EncodeService;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/borrow")
public class BorrowAndReturnController {

	@Autowired
	BorrowAndReturnRepository mBorrowAndReturnRepository;

	@Autowired
	BorrowerRepository mBorrowerRepository;

	@Autowired
	DurableRepository mDurableRepository;

	@Autowired
	StaffRepository mStaffRepository;

	@Autowired
	private EncodeService encodeservice;

	@PostMapping(path = "/saveBorrow")
	public @ResponseBody ResponseObj saveBorrows(@RequestBody Borrowing borrow)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ResponseObj r = null;

		String statusdurable = mDurableRepository
				.Durable_CheckstatusBorrow(URLDecoder.decode(borrow.getDurable().getDurable_code(), "UTF-8"));
		if (statusdurable.equalsIgnoreCase("ยืม")) {
			r = new ResponseObj(500, "0");
		} else if (statusdurable.equalsIgnoreCase("คืน")) {

//			Borrower borrowerss = mBorrowerRepository.checkBorrower(borrow.getBorrower().getIdcard_borrower());
//			if (borrowerss == null) {
//				Borrower borrows = new Borrower();
//				borrows.setIdcard_borrower(borrow.getBorrower().getIdcard_borrower());
//				borrows.setBorrower_name(URLDecoder.decode(borrow.getBorrower().getBorrower_name(), "UTF-8"));
//				borrows.setBorrower_picture(borrow.getBorrower().getBorrower_picture());
//				borrows.setTelephonenumber(borrow.getBorrower().getTelephonenumber());
//				mBorrowerRepository.save(borrows);
//			}
			
			
			Borrower borrowers = mBorrowerRepository.checkBorrower(borrow.getBorrower().getIdcard_borrower());

			Durable d = mDurableRepository.detaildurablebydurablecodes(URLDecoder.decode(borrow.getDurable().getDurable_code(), "UTF-8"));			
			Staff s = mStaffRepository.StaffDetailByIDcard(borrow.getStaff().getId_card());
			
			Borrowing borrowDetail = new Borrowing();
			borrowDetail.setBorrow_date(URLDecoder.decode(borrow.getBorrow_date(), "UTF-8"));
			borrowDetail.setBorrow_status(URLDecoder.decode(borrow.getBorrow_status(), "UTF-8"));
			borrowDetail.setSchedulereturn(URLDecoder.decode(borrow.getSchedulereturn(), "UTF-8"));
			borrowDetail.setBorrower(borrowers);
			borrowDetail.setDurable(d);
			borrowDetail.setStaff(s);
			mBorrowAndReturnRepository.save(borrowDetail);

			int result = mDurableRepository.UpdateStatusBorrow("ยืม",
					URLDecoder.decode(borrow.getDurable().getDurable_code(), "UTF-8"));
			System.out.println(result);

			r = new ResponseObj(200, "1");

		}

		return r;
	}

	
	@PostMapping(path = "/listborrowbyidcard")
	public @ResponseBody ResponseObj listBorrowsByidcad(@RequestBody String idCard)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String idcardReplace = idCard.replaceAll("\"", "");

		List<Borrowing> listBorrowing = mBorrowAndReturnRepository.listBorrowByIdcard(idcardReplace);
		if (listBorrowing.size() == 0) {
			return new ResponseObj(500, "0");
		} else {
			List<Borrowing> BorrowingList = encodeservice.EncodeBorrowing(listBorrowing);
			return new ResponseObj(200, BorrowingList);
		}
		
	}
	
	@PostMapping(path = "/listborrow")
	public @ResponseBody ResponseObj listBorrows(@RequestBody String idmajor)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		System.out.println(idmajor);
		int idmajorString = Integer.parseInt(idmajor);

		List<Borrowing> listBorrowing = mBorrowAndReturnRepository.listborrow(idmajorString);

		if (listBorrowing.size() == 0) {
			return new ResponseObj(500, "not found");
		} else {
			List<Borrowing> BorrowingList = encodeservice.EncodeBorrowing(listBorrowing);
			return new ResponseObj(200, BorrowingList);
		}
	}

	@PostMapping(path = "/checkBorrow")
	public @ResponseBody ResponseObj checkBorrow(@RequestBody String Json) {

		String replaceall = Json.replaceAll("\"", "");
		String[] spliteJson = replaceall.split(":");
		String Durablecode = null;
		int idMajor = Integer.parseInt(spliteJson[0]);

		try {
			Durablecode = URLDecoder.decode(spliteJson[1], "UTF-8");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Durable durableDetail = mDurableRepository.getDetailDurableByMajor(Durablecode, idMajor);

		if (durableDetail == null) {
			return new ResponseObj(500, "0");
		} else {
			Borrowing borrow = mBorrowAndReturnRepository.checkBorrowStatus(durableDetail.getDurable_code());
			if (borrow == null) {
				Durable durable = encodeservice.EncoderDurable(durableDetail);
				return new ResponseObj(200, durable);
			} else {
				return new ResponseObj(500, "2");
			}
		}

	}

//	@PostMapping(path = "/listborrowbydate")
//	public @ResponseBody ResponseObj listborrowbydates(@RequestBody String borrowdate)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		System.out.println(borrowdate);
//		List<Borrowing> borrowingdatalist = new ArrayList<>();
//
//		String data = borrowdate.replaceAll("\"", "");
//		String dateborrow = URLDecoder.decode(data, "UTF-8");
//
//		System.out.println(dateborrow);
//
//		List<Borrowing> listBorrowing = mBorrowAndReturnRepository.listborrowBydate(dateborrow);
//
//		if (listBorrowing.size() == 0) {
//			return new ResponseObj(500, "not found");
//		} else {
//			for (int i = 0; i < listBorrowing.size(); i++) {
//
//				Borrowing borrowing = new Borrowing();
//
//				borrowing.setBorrowing_ID(listBorrowing.get(i).getBorrowing_ID());
//				borrowing.setBorrow_status(URLEncoder.encode(listBorrowing.get(i).getBorrow_status(), "UTF-8"));
//				borrowing.setBorrow_date(URLEncoder.encode(listBorrowing.get(i).getBorrow_date(), "UTF-8"));
//				borrowing.setSchedulereturn(URLEncoder.encode(listBorrowing.get(i).getSchedulereturn(), "UTF-8"));
//				borrowing.setReturn_date(URLEncoder.encode(listBorrowing.get(i).getReturn_date(), "UTF-8"));
//
//				borrowing.setBorrower(new Borrower(listBorrowing.get(i).getBorrower().getIdcard_borrower(),
//						URLEncoder.encode(listBorrowing.get(i).getBorrower().getBorrower_name(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getBorrower().getBorrower_picture(), "UTF-8"),
//						listBorrowing.get(i).getBorrower().getTelephonenumber()));
//
//				borrowing.setStaff(new Staff(listBorrowing.get(i).getStaff().getId_card(),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getStaff_name(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getStaff_lastname(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getStaff_status(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getEmail(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getBrithday(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getPhone_number(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getStaff().getImage_staff(), "UTF-8")));
//
//				if (listBorrowing.get(i).getDurable().getDurable_number() == null) {
//					listBorrowing.get(i).getDurable().setDurable_number("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_brandname() == null) {
//					listBorrowing.get(i).getDurable().setDurable_brandname("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_model() == null) {
//					listBorrowing.get(i).getDurable().setDurable_model("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_price() == null) {
//					listBorrowing.get(i).getDurable().setDurable_price("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_statusnow() == null) {
//					listBorrowing.get(i).getDurable().setDurable_statusnow("-");
//				}
//				if (listBorrowing.get(i).getDurable().getResponsible_person() == null) {
//					listBorrowing.get(i).getDurable().setResponsible_person("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_image() == null) {
//					listBorrowing.get(i).getDurable().setDurable_image("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_Borrow_Status() == null) {
//					listBorrowing.get(i).getDurable().setDurable_Borrow_Status("-");
//				}
//				if (listBorrowing.get(i).getDurable().getDurable_entrancedate() == null) {
//					listBorrowing.get(i).getDurable().setDurable_entrancedate("-");
//				}
//
//				borrowing.setDurable(new Durable(
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_code(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_name(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_number(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_brandname(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_model(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_price(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_statusnow(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getResponsible_person(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_image(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_Borrow_Status(), "UTF-8"),
//						URLEncoder.encode(listBorrowing.get(i).getDurable().getDurable_entrancedate(), "UTF-8")));
//
//				borrowingdatalist.add(borrowing);
//
//			}
//		}
//
//		System.out.println(borrowingdatalist.get(0).getReturn_date());
//
//		return new ResponseObj(200, borrowingdatalist);
//	}

	@PostMapping(path = "/saveretuen")
	public @ResponseBody ResponseObj saveretuens(@RequestBody String data)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String datas = data.replaceAll("\"", "");
		String[] datareturn = datas.split(",");

		String durablecode = URLDecoder.decode(datareturn[0], "UTF-8");
		String statusreturn = URLDecoder.decode(datareturn[1], "UTF-8");
		String returndate = URLDecoder.decode(datareturn[2], "UTF-8");
		int reult = mDurableRepository.UpdateStatusBorrow(statusreturn, durablecode);
		int re = mBorrowAndReturnRepository.UpdateStatusBorrow(statusreturn, returndate, durablecode);

		System.out.println(reult);
		System.out.println(re);

		return new ResponseObj(200, "succ");

	}

	@PostMapping(path = "/getdataborrow")
	public @ResponseBody ResponseObj getdataborrows(@RequestBody String durablecode)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		ResponseObj r = null;

		String durablecodes = durablecode.replaceAll("\"", "");
		String decodeDurable = URLDecoder.decode(durablecodes, "UTF-8");

		Borrowing borrow = mBorrowAndReturnRepository.Borrow_DetailByDurableCode(decodeDurable);

		if (borrow == null) {
			r = new ResponseObj(500, "");
		} else {

			if (borrow.getReturn_date() == null) {
				borrow.setReturn_date("-");
			}

			borrow.setBorrow_date(URLEncoder.encode(borrow.getBorrow_date(), "UTF-8"));
			borrow.setSchedulereturn(URLEncoder.encode(borrow.getSchedulereturn(), "UTF-8"));
			borrow.setBorrow_status(URLEncoder.encode(borrow.getBorrow_status(), "UTF-8"));

			borrow.getDurable().setDurable_code(URLEncoder.encode(borrow.getDurable().getDurable_code(), "UTF-8"));
			if (borrow.getDurable().getDurable_Borrow_Status().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_Borrow_Status() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_Borrow_Status())) {

				borrow.getDurable().setDurable_Borrow_Status("-");
			} else {
				borrow.getDurable().setDurable_Borrow_Status(
						URLEncoder.encode(borrow.getDurable().getDurable_Borrow_Status(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_brandname().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_brandname() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_brandname())) {

				borrow.getDurable().setDurable_brandname("-");
			} else {
				borrow.getDurable()
						.setDurable_brandname(URLEncoder.encode(borrow.getDurable().getDurable_brandname(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_entrancedate().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_entrancedate() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_entrancedate())) {

				borrow.getDurable().setDurable_entrancedate("-");
			} else {
				borrow.getDurable().setDurable_entrancedate(
						URLEncoder.encode(borrow.getDurable().getDurable_entrancedate(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_image().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_image() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_image())) {

				borrow.getDurable().setDurable_image("-");
			} else {
				borrow.getDurable()
						.setDurable_image(URLEncoder.encode(borrow.getDurable().getDurable_image(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_model().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_model() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_model())) {

				borrow.getDurable().setDurable_model("-");
			} else {
				borrow.getDurable()
						.setDurable_model(URLEncoder.encode(borrow.getDurable().getDurable_model(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_name().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_name() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_name())) {

				borrow.getDurable().setDurable_name("-");
			} else {
				borrow.getDurable().setDurable_name(URLEncoder.encode(borrow.getDurable().getDurable_name(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_number().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_number() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_number())) {
				// durable.get(i).setDurable_number("-");
				borrow.getDurable().setDurable_number("-");
			} else {
				borrow.getDurable()
						.setDurable_number(URLEncoder.encode(borrow.getDurable().getDurable_number(), "UTF-8"));
			}
			if (borrow.getDurable().getDurable_price().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_price() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_price())) {
				// durable.get(i).setDurable_price("-");
				borrow.getDurable().setDurable_price("-");
			} else {
				borrow.getDurable()
						.setDurable_price(URLEncoder.encode(borrow.getDurable().getDurable_price(), "UTF-8"));

			}
			if (borrow.getDurable().getDurable_statusnow().equalsIgnoreCase("")
					|| borrow.getDurable().getDurable_statusnow() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getDurable_statusnow())) {
				// durable.get(i).setDurable_statusnow("-");
				borrow.getDurable().setDurable_statusnow("-");
			} else {
				borrow.getDurable()
						.setDurable_statusnow(URLEncoder.encode(borrow.getDurable().getDurable_statusnow(), "UTF-8"));
			}
			if (borrow.getDurable().getResponsible_person().equalsIgnoreCase("")
					|| borrow.getDurable().getResponsible_person() == null
					|| "".equalsIgnoreCase(borrow.getDurable().getResponsible_person())) {
				// durable.get(i).setResponsible_person("-");
				borrow.getDurable().setResponsible_person("-");
			} else {
				borrow.getDurable()
						.setResponsible_person(URLEncoder.encode(borrow.getDurable().getResponsible_person(), "UTF-8"));
			}
			borrow.getBorrower().setBorrower_name(URLEncoder.encode(borrow.getBorrower().getBorrower_name(), "UTF-8"));
			borrow.getBorrower()
					.setBorrower_picture(URLEncoder.encode(borrow.getBorrower().getBorrower_picture(), "UTF-8"));
			borrow.getStaff().setBrithday(URLEncoder.encode(borrow.getStaff().getBrithday(), "UTF-8"));
			borrow.getStaff().setImage_staff(URLEncoder.encode(borrow.getStaff().getImage_staff(), "UTF-8"));
			borrow.getStaff().setStaff_name(URLEncoder.encode(borrow.getStaff().getStaff_name(), "UTF-8"));
			borrow.getStaff().setStaff_lastname(URLEncoder.encode(borrow.getStaff().getStaff_lastname(), "UTF-8"));
			borrow.getStaff().setStaff_name(URLEncoder.encode(borrow.getStaff().getStaff_status(), "UTF-8"));

			r = new ResponseObj(200, borrow);

		}

		return r;
	}

}
