package ac.th.itsci.durable.service;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.repo.VerifyRepository;
import ac.th.itsci.durable.util.VerifyDateString;

@Service
public class VerifyWebServiceIMP implements VerifyWebService {
	
	@Autowired
	VerifyRepository verifyRepository;

	@Override
	public ModelAndView call_getAllVerify(HttpSession session) {
		ModelAndView mav = new ModelAndView("inspection_schedule");

		List<Verify> verifyList = verifyRepository.verify_getAllVerify();
		List<VerifyDateString> VerifyDateStringList = new ArrayList<>();
		
		Format formatter = null;
		try {
			formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (verifyList == null || verifyList.size() == 0) {
			mav.addObject("Inspection_schedule", null);
		} else {
			for (int i = 0; i < verifyList.size(); i++) {

				VerifyDateString verifyDateString = new VerifyDateString();
				verifyDateString.setYears(verifyList.get(i).getYears());
				verifyDateString.setDateStart(formatter.format(verifyList.get(i).getDateStart()));
				verifyDateString.setDateEnd(formatter.format(verifyList.get(i).getDateEnd()));
				VerifyDateStringList.add(verifyDateString);

			}
			mav.addObject("Inspection_schedule", VerifyDateStringList);
		}

		mav.addObject("startDate", formatter.format(new Date()));
		mav.addObject("endDate", formatter.format(new Date()));
		mav.addObject("status", 1);

		return mav;
	}

	@Override
	public ModelAndView call_createVerify(ServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("inspection_schedule");
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
		try {
			request.setCharacterEncoding("UTF-8");
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			String years = request.getParameter("years");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String message = null;
			Date dateStart = null;
			Date dateEnd = null;
			String dateStirngStartNew = null;
			String dateStringEndNew = null;
			Date dateStartNew = null;
			Date dateEndNew = null;
			String datenowString = null;
			Date nowDate = null;
			try {
				dateStart = simpleDateFormatThai.parse(startDate);
				dateEnd = simpleDateFormatThai.parse(endDate);
				dateStirngStartNew = simpledate.format(dateStart);
				dateStringEndNew = simpledate.format(dateEnd);
				dateStartNew = simpledate.parse(dateStirngStartNew);
				dateEndNew = simpledate.parse(dateStringEndNew);
				datenowString = simpledate.format(new Date());
				nowDate = simpledate.parse(datenowString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (dateStartNew.before(nowDate)) {
				message = " วันที่เริ่มการตรวจสอบไม่ควรเลือกวันที่ในอดีต";
				mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
				mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			}else if (dateStartNew.equals(dateEndNew)) {
				message = " วันที่เริ่มการตรวจสอบและวันที่สิ้นสุดการตรวจสอบห้ามเป็นวันเดียวกัน";
				mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
				mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			} else if (Integer.parseInt(years) != (year + 543)) {
				message = " ระบุปีให้ถูกต้อง ";
				mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
				mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			} else if (dateStartNew.after(dateEndNew)) {
				message = " ระบุวันที่ไม่ถูกต้องวันเริ่มการตรวจสอบควรอยู่ก่อนวันสิ้นสุดการตรวจสอบ ";
				mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
				mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			} else if (dateEndNew.before(dateStartNew)) {
				message = " ระบุวันที่ไม่ถูกต้องวันสิ้นสุดการตรวจสอบควรอยู่ก่อนวันเริ่มตรวจสอบ ";
				mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
				mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			} else {
				Verify verify = new Verify();
				verify.setYears(years);
				verify.setDateEnd(dateEndNew);
				verify.setDateStart(dateStartNew);

				int result = verifyRepository.verify_addVerify(verify.getYears(), verify.getDateEnd(),
						verify.getDateStart());

				if (result == 1) {
					message = " บันทึกกำหนดวันเสร็จสิ้น ";
				}

			}
			List<VerifyDateString> VerifyDateStringList = new ArrayList<>();
			List<Verify> verifyList = verifyRepository.verify_getAllVerify();

			for (int i = 0; i < verifyList.size(); i++) {

				VerifyDateString verifyDateString = new VerifyDateString();
				verifyDateString.setYears(verifyList.get(i).getYears());
				verifyDateString.setDateStart(simpleDateFormatThai.format(verifyList.get(i).getDateStart()));
				verifyDateString.setDateEnd(simpleDateFormatThai.format(verifyList.get(i).getDateEnd()));
				VerifyDateStringList.add(verifyDateString);

			}
			mav.addObject("Inspection_schedule", VerifyDateStringList);
			mav.addObject("status", 1);
			mav.addObject("value", 1);
			mav.addObject("message", message);

		} catch (Exception e) {
			List<Verify> verifyList = verifyRepository.verify_getAllVerify();
			List<VerifyDateString> VerifyDateStringList = new ArrayList<>();

			for (int i = 0; i < verifyList.size(); i++) {

				VerifyDateString verifyDateString = new VerifyDateString();
				verifyDateString.setYears(verifyList.get(i).getYears());
				verifyDateString.setDateStart(simpleDateFormatThai.format(verifyList.get(i).getDateStart()));
				verifyDateString.setDateEnd(simpleDateFormatThai.format(verifyList.get(i).getDateEnd()));
				VerifyDateStringList.add(verifyDateString);

			}
			mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
			mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
			mav.addObject("Inspection_schedule", VerifyDateStringList);
			mav.addObject("status", 1);
			mav.addObject("value", 1);
			mav.addObject("message", "ไม่สามารถกำหนดวันตรวจสอบเพิ่มได้อีกในปีนี้");
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public ModelAndView call_updateVerify(ServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("inspection_schedule");
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
		String years = request.getParameter("YEARS");
		String endDate = request.getParameter("ENDDATE");
		String message = null;
		Date dateEnd = null;
		String dateStringEndNew = null;
		Date dateEndNew = null;
		try {
			dateEnd = simpleDateFormatThai.parse(endDate);
			dateStringEndNew = simpledate.format(dateEnd);
			dateEndNew = simpledate.parse(dateStringEndNew);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Verify verify = verifyRepository.verify_getVerifyDetailByYears(years);

		if (dateEndNew.before(verify.getDateEnd())) {
			message = "ท่านระบุระยะเวลาไม่ถูกต้องกรุณาเลือกวันที่ใหม่";
		} else {
			verify.setDateEnd(dateEndNew);
			verifyRepository.save(verify);
			message = "เปลี่ยนแปลงวันที่สิ้นสุดการตรวจสอบสำเร็จ";
		}

		List<VerifyDateString> VerifyDateStringList = new ArrayList<>();
		List<Verify> verifyList = verifyRepository.verify_getAllVerify();

		for (int i = 0; i < verifyList.size(); i++) {

			VerifyDateString verifyDateString = new VerifyDateString();
			verifyDateString.setYears(verifyList.get(i).getYears());
			verifyDateString.setDateStart(simpleDateFormatThai.format(verifyList.get(i).getDateStart()));
			verifyDateString.setDateEnd(simpleDateFormatThai.format(verifyList.get(i).getDateEnd()));
			VerifyDateStringList.add(verifyDateString);

		}
		mav.addObject("startDate", simpleDateFormatThai.format(new Date()));
		mav.addObject("endDate", simpleDateFormatThai.format(new Date()));
		mav.addObject("Inspection_schedule", VerifyDateStringList);
		mav.addObject("status", 1);
		mav.addObject("value", 1);
		mav.addObject("message", message);
		return mav;
	}

}
