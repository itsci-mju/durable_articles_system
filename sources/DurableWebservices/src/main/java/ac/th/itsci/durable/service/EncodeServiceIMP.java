package ac.th.itsci.durable.service;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ac.th.itsci.durable.entity.*;

@Service
public class EncodeServiceIMP implements EncodeService {

	@Override
	public List<Durable> EncodeDurable(List<Durable> durable) {
		List<Durable> d = new ArrayList<>();
		try {
			for (int i = 0; i < durable.size(); i++) {
				Durable durables = new Durable();
				durables.setDurable_code(URLEncoder.encode(durable.get(i).getDurable_code(), "UTF-8"));
				
						
				if (durable.get(i).getDurable_Borrow_Status().equalsIgnoreCase("")
						|| durable.get(i).getDurable_Borrow_Status() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_Borrow_Status())) {

					durables.setDurable_Borrow_Status("-");
				} else {
					durables.setDurable_Borrow_Status(
							URLEncoder.encode(durable.get(i).getDurable_Borrow_Status(), "UTF-8"));
				}
				if (durable.get(i).getDurable_brandname().equalsIgnoreCase("")
						|| durable.get(i).getDurable_brandname() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_brandname())) {
					// durable.get(i).setDurable_brandname("-");
					durables.setDurable_brandname("-");
				} else {
					durables.setDurable_brandname(URLEncoder.encode(durable.get(i).getDurable_brandname(), "UTF-8"));
				}
				if (durable.get(i).getDurable_entrancedate().equalsIgnoreCase("")
						|| durable.get(i).getDurable_entrancedate() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_entrancedate())) {
					// durable.get(i).setDurable_entrancedate("-");
					durables.setDurable_entrancedate("-");
				} else {
					durables.setDurable_entrancedate(
							URLEncoder.encode(durable.get(i).getDurable_entrancedate(), "UTF-8"));
				}
				if (durable.get(i).getDurable_image() == null || durable.get(i).getDurable_image().equalsIgnoreCase("")
						|| "".equalsIgnoreCase(durable.get(i).getDurable_image())) {
					// durable.get(i).setDurable_image("-");
					durables.setDurable_image("-");
				} else {
					durables.setDurable_image(URLEncoder.encode(durable.get(i).getDurable_image(), "UTF-8"));
				}
				if (durable.get(i).getDurable_model().equalsIgnoreCase("") || durable.get(i).getDurable_model() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_model())) {
					// durable.get(i).setDurable_model("-");
					durables.setDurable_model("-");
				} else {
					durables.setDurable_model(URLEncoder.encode(durable.get(i).getDurable_model(), "UTF-8"));
				}
				if (durable.get(i).getDurable_name().equalsIgnoreCase("") || durable.get(i).getDurable_name() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_name())) {
					// durable.get(i).setDurable_name("-");
					durables.setDurable_name("-");
				} else {
					durables.setDurable_name(URLEncoder.encode(durable.get(i).getDurable_name(), "UTF-8"));
				}
				if (durable.get(i).getDurable_number().equalsIgnoreCase("")
						|| durable.get(i).getDurable_number() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_number())) {
					// durable.get(i).setDurable_number("-");
					durables.setDurable_number("-");
				} else {
					durables.setDurable_number(URLEncoder.encode(durable.get(i).getDurable_number(), "UTF-8"));
				}
				if (durable.get(i).getDurable_price().equalsIgnoreCase("") || durable.get(i).getDurable_price() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_price())) {
					durables.setDurable_price("-");
				} else {
					durables.setDurable_price(URLEncoder.encode(durable.get(i).getDurable_price(), "UTF-8"));

				}
				if (durable.get(i).getDurable_statusnow().equalsIgnoreCase("")
						|| durable.get(i).getDurable_statusnow() == null
						|| "".equalsIgnoreCase(durable.get(i).getDurable_statusnow())) {
					durables.setDurable_statusnow("-");
				} else {
					durables.setDurable_statusnow(URLEncoder.encode(durable.get(i).getDurable_statusnow(), "UTF-8"));
				}
				if (durable.get(i).getResponsible_person().equalsIgnoreCase("")
						|| durable.get(i).getResponsible_person() == null
						|| "".equalsIgnoreCase(durable.get(i).getResponsible_person())) {
					durables.setResponsible_person("-");
				} else {
					durables.setResponsible_person(URLEncoder.encode(durable.get(i).getResponsible_person(), "UTF-8"));
				}
				
				if (durable.get(i).getNote() == null || durable.get(i).getNote().equalsIgnoreCase("") || durable.get(i).getNote() == null || "".equalsIgnoreCase(durable.get(i).getNote())) {
					durables.setNote("-");
				} else {
					durables.setNote(URLEncoder.encode(durable.get(i).getNote(), "UTF-8"));
				}

				durables.setRoom(new Room(URLEncoder.encode(durable.get(i).getRoom().getRoom_number(), "UTF-8"),
						URLEncoder.encode(durable.get(i).getRoom().getRoom_name(), "UTF-8"),
						URLEncoder.encode(durable.get(i).getRoom().getBuild(), "UTF-8"),
						URLEncoder.encode(durable.get(i).getRoom().getFloor(), "UTF-8")));

				durables.setMajor(new Major(durable.get(i).getMajor().getID_Major(),
						URLEncoder.encode(durable.get(i).getMajor().getMajor_Name(), "UTF-8")));
			

				d.add(durables);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	@Override
	public List<Borrowing> EncodeBorrowing(List<Borrowing> borrowingList) {
		List<Borrowing> borrowingdatalist = new ArrayList<>();
		try {

			for (int i = 0; i < borrowingList.size(); i++) {

				Borrowing borrowing = new Borrowing();

				borrowing.setBorrowing_ID(borrowingList.get(i).getBorrowing_ID());
				borrowing.setBorrow_status(URLEncoder.encode(borrowingList.get(i).getBorrow_status(), "UTF-8"));
				borrowing.setBorrow_date(URLEncoder.encode(borrowingList.get(i).getBorrow_date(), "UTF-8"));
				borrowing.setSchedulereturn(URLEncoder.encode(borrowingList.get(i).getSchedulereturn(), "UTF-8"));
				
				if(borrowingList.get(i).getReturn_date() == null) {
					borrowing.setReturn_date("-");
				} else {
					borrowing.setReturn_date(URLEncoder.encode(borrowingList.get(i).getReturn_date(), "UTF-8"));
				}
			
				borrowing.setBorrower(new Borrower(borrowingList.get(i).getBorrower().getIdcard_borrower(),
						URLEncoder.encode(borrowingList.get(i).getBorrower().getBorrower_name(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getBorrower().getBorrower_picture(), "UTF-8"),
						borrowingList.get(i).getBorrower().getTelephonenumber()));

				borrowing.setStaff(new Staff(borrowingList.get(i).getStaff().getId_card(),
						URLEncoder.encode(borrowingList.get(i).getStaff().getStaff_name(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getStaff_lastname(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getStaff_status(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getEmail(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getBrithday(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getPhone_number(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getStaff().getImage_staff(), "UTF-8")));

				if (borrowingList.get(i).getDurable().getDurable_number() == null) {
					borrowingList.get(i).getDurable().setDurable_number("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_brandname() == null) {
					borrowingList.get(i).getDurable().setDurable_brandname("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_model() == null) {
					borrowingList.get(i).getDurable().setDurable_model("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_price() == null) {
					borrowingList.get(i).getDurable().setDurable_price("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_statusnow() == null) {
					borrowingList.get(i).getDurable().setDurable_statusnow("-");
				}
				if (borrowingList.get(i).getDurable().getResponsible_person() == null) {
					borrowingList.get(i).getDurable().setResponsible_person("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_image() == null) {
					borrowingList.get(i).getDurable().setDurable_image("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_Borrow_Status() == null) {
					borrowingList.get(i).getDurable().setDurable_Borrow_Status("-");
				}
				if (borrowingList.get(i).getDurable().getDurable_entrancedate() == null) {
					borrowingList.get(i).getDurable().setDurable_entrancedate("-");
				}
				

				borrowing.setDurable(new Durable(
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_code(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_name(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_number(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_brandname(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_model(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_price(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_statusnow(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getResponsible_person(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_image(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_Borrow_Status(), "UTF-8"),
						URLEncoder.encode(borrowingList.get(i).getDurable().getDurable_entrancedate(), "UTF-8")));

				borrowingdatalist.add(borrowing);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return borrowingdatalist;
	}

	@Override
	public Durable EncoderDurable(Durable durable) {
		Durable durables = new Durable();
		try {
				
		if (durable.getDurable_code().equalsIgnoreCase("") || durable.getDurable_code() == null) {
			durables.setDurable_code("-");
		} else {
			durables.setDurable_code(URLEncoder.encode(durable.getDurable_code(), "UTF-8"));
		}
		if (durable.getDurable_Borrow_Status().equalsIgnoreCase("") || durable.getDurable_Borrow_Status() == null) {
			durables.setDurable_Borrow_Status("-");
		} else {
			durables.setDurable_Borrow_Status(URLEncoder.encode(durable.getDurable_Borrow_Status(), "UTF-8"));
		}
		if (durable.getDurable_brandname().equalsIgnoreCase("") || durable.getDurable_brandname() == null) {
			durables.setDurable_brandname("-");
		} else {
			durables.setDurable_brandname(URLEncoder.encode(durable.getDurable_brandname(), "UTF-8"));
		}
		if (durable.getDurable_entrancedate().equalsIgnoreCase("") || durable.getDurable_entrancedate() == null) {
			durables.setDurable_entrancedate("-");
		} else {
			durables.setDurable_entrancedate(URLEncoder.encode(durable.getDurable_entrancedate(), "UTF-8"));
		}
		if (durable.getDurable_image().equalsIgnoreCase("") || durable.getDurable_image() == null) {
			durables.setDurable_image("-");
		} else {
			durables.setDurable_image(URLEncoder.encode(durable.getDurable_image(), "UTF-8"));
		}
		if (durable.getDurable_model().equalsIgnoreCase("") || durable.getDurable_model() == null) {
			durables.setDurable_model("-");
		} else {
			durables.setDurable_model(URLEncoder.encode(durable.getDurable_model(), "UTF-8"));
		}
		if (durable.getDurable_name().equalsIgnoreCase("") || durable.getDurable_name() == null) {
			durables.setDurable_name("-");
		} else {
			durables.setDurable_name(URLEncoder.encode(durable.getDurable_name(), "UTF-8"));
		}
		if (durable.getDurable_number().equalsIgnoreCase("") || durable.getDurable_number() == null) {
			durables.setDurable_number("-");
		} else {
			durables.setDurable_number(URLEncoder.encode(durable.getDurable_number(), "UTF-8"));
		}
		if (durable.getDurable_price().equalsIgnoreCase("") || durable.getDurable_price() == null) {
			durables.setDurable_price("-");
		} else {
			durables.setDurable_price(URLEncoder.encode(durable.getDurable_price(), "UTF-8"));
		}
		if (durable.getDurable_statusnow().equalsIgnoreCase("") || durable.getDurable_statusnow() == null) {
			durables.setDurable_statusnow("-");
		} else {
			durables.setDurable_statusnow(URLEncoder.encode(durable.getDurable_statusnow(), "UTF-8"));
		}
		if (durable.getResponsible_person().equalsIgnoreCase("") || durable.getResponsible_person() == null) {
			durables.setResponsible_person("-");
		} else {
			durables.setResponsible_person(URLEncoder.encode(durable.getResponsible_person(), "UTF-8"));
		}
		// Room room = mRoomRepository.RoomDetailByRoomID(d[11]);

		durables.setRoom(new Room(URLEncoder.encode(durable.getRoom().getRoom_number(), "UTF-8"),
				URLEncoder.encode(durable.getRoom().getRoom_name(), "UTF-8"),
				URLEncoder.encode(durable.getRoom().getBuild(), "UTF-8"),
				URLEncoder.encode(durable.getRoom().getFloor(), "UTF-8")));

		durables.setMajor(new Major(durable.getMajor().getID_Major(),
				URLEncoder.encode(durable.getMajor().getMajor_Name(), "UTF-8")));
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return durables;
	}

}
