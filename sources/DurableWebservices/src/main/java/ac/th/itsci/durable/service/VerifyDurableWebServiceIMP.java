package ac.th.itsci.durable.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RoomRepository;
import ac.th.itsci.durable.repo.VerifyDurableRepository;
import ac.th.itsci.durable.repo.VerifyRepository;

@Service
public class VerifyDurableWebServiceIMP implements VerifyDurableWebService {

	@Autowired
	VerifyRepository verifyRepository;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	VerifyDurableRepository verifyRepo;

	@Autowired
	RoomRepository roomRepo;

	@Autowired
	DurableRepository durableRepo;

	@Override
	public ModelAndView call_listDurableProfileByYear(ServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("listdurablebyyear");
		try {
			request.setCharacterEncoding("UTF-8");
			// conditions for search by edit
			String yearsearch = request.getParameter("yearsearch");

			// condition for frist time
			String year = request.getParameter("year");
			String id_Major = request.getParameter("major_id");
			String statusDurable = request.getParameter("statusSelect");
			String room_number = request.getParameter("room_ID");
			SimpleDateFormat formatterThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Date newDate = null;
			String dateString = null;
			
			if (yearsearch != null) {
				year = yearsearch;
			}

			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			Calendar calendar = Calendar.getInstance();
			int years = calendar.get(Calendar.YEAR);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dateNowString = null;
			Date dateNow = null;

			dateNowString = simpleDateFormat.format(date);
			dateNow = simpleDateFormat.parse(dateNowString);

			List<String> listRoom = new ArrayList<>();
			List<VerifyDurable> verify = null;

			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {

				if (id_Major != null && room_number.equalsIgnoreCase("-") && statusDurable.equalsIgnoreCase("-")) {
					verify = verifyRepo.verify_getAllVerifyByYear_Major(year, Integer.parseInt(id_Major));
				} else if (id_Major != null && !room_number.equalsIgnoreCase("-") && statusDurable.equalsIgnoreCase("-")) {
					verify = verifyRepo.verify_getAllVerifyByYear_Id_major_Room(year, Integer.parseInt(id_Major), room_number);
				} else if (id_Major != null && !room_number.equalsIgnoreCase("-") && !statusDurable.equalsIgnoreCase("-")) {
					verify = verifyRepo.verify_getAllVerifyByYear_Id_major_Room_Status(year, Integer.parseInt(id_Major), room_number, statusDurable);
				}
				
				//chang format date 
				for (VerifyDurable v : verify) {
					System.out.println("date befor chang :" + v.getSave_date());
					newDate = simpleDateFormat.parse(v.getSave_date());
					dateString = formatterThai.format(newDate);
					v.setSave_date(dateString);
					System.out.println("date after channg : " + v.getSave_date());
				}
				
				call_modelMajorAndRoom(mav);
				
				List<Major> test = (List<Major>) mav.getModel().get("listMajor");
				
				for (int i = 0; i < test.size(); i++) {
					if (test.get(i).getID_Major() == Integer.parseInt(id_Major)) {
						Collections.swap(test, 0, i);
					}
				}
				
				// set room select to jsp
				listRoom.add("-");
				List<Room> roomList = roomRepo.room_getAllRoomByMajor(Integer.parseInt(id_Major));
				for (Room r : roomList) {
					listRoom.add(r.getRoom_number());
				}

				for (int i = 0; i < listRoom.size(); i++) {
					if (listRoom.get(i).equalsIgnoreCase(room_number)) {
						Collections.swap(listRoom, 0, i);
					}
				}
				
				// check list data verify
				if (verify.size() == 0) {
					mav.addObject("durableprofiles", null);
				} else {
					mav.addObject("durableprofiles", verify);
					session.setAttribute("exportdurableprofiles", verify);
				}

				// check verify for enable button edit
				Verify checkVerify = verifyRepository.verify_getVerifyDetailByYears(year);
				
				try {
					if (!checkVerify.getYears().equalsIgnoreCase(String.valueOf(years + 543))) {
						mav.addObject("disbleButton", 0);
					} else if (dateNow.before(checkVerify.getDateStart())) {
						mav.addObject("disbleButton", 1);
					} else if (dateNow.after(checkVerify.getDateEnd())) {
						mav.addObject("disbleButton", 2);
						mav.addObject("textNotificationsHeader", " **** หมดกำหนดระยะเวลาการตรวจสอบ ****");
						mav.addObject("textNotification",
								"กรุณาติดต่อเจ้าหน้าที่พัสดุคณะวิทยาศาสตร์เพื่อทำการขอขยายเวลา โทร.xxx-xxxxxxx");
					} else {
						mav.addObject("disbleButton", 3);
					}
					
				}catch(Exception e) {
					mav.addObject("no_year",1);
				}
				mav.addObject("roomAll", listRoom);
				mav.addObject("year", year);
				mav.addObject("status", 1);
				mav.addObject("statusSelectOld", statusDurable);
				mav.addObject("listMajor", test);
			
			} else {
				try {
					if (room_number.equalsIgnoreCase("-") && statusDurable.equalsIgnoreCase("-")) {
						verify = verifyRepo.verify_getAllVerifyByYear(year);
					} else if (room_number.equalsIgnoreCase("-") && !statusDurable.equalsIgnoreCase("-")) {
						verify = verifyRepo.verify_getAllVerifyByYear_Status(year, statusDurable);
					} else if (!room_number.equalsIgnoreCase("-") && statusDurable.equalsIgnoreCase("-")) {
						verify = verifyRepo.verify_getAllVerifyByYear_Room(year, room_number);
					} else if (!room_number.equalsIgnoreCase("-") && !statusDurable.equalsIgnoreCase("-")) {
						verify = verifyRepo.verify_getAllVerifyByYear_Room_Status(year, room_number, statusDurable);
					}
				}catch(Exception e) {
					
				}

				//chang format date 
				for (VerifyDurable v : verify) {
					System.out.println("date befor chang :" + v.getSave_date());
					newDate = simpleDateFormat.parse(v.getSave_date());
					dateString = formatterThai.format(newDate);
					v.setSave_date(dateString);
					System.out.println("date after channg : " + v.getSave_date());
				}
				
				// set room select to jsp
				listRoom.add("-");
				List<Room> roomList = roomRepo.room_getAllRoomByMajor(staffDetail.getMajor().getID_Major());
				for (Room r : roomList) {
					listRoom.add(r.getRoom_number());
				}

				for (int i = 0; i < listRoom.size(); i++) {
					if (listRoom.get(i).equalsIgnoreCase(room_number)) {
						Collections.swap(listRoom, 0, i);
					}
				}

				// check list data verify
				
				if (verify.size() == 0) {
					mav.addObject("durableprofiles", null);
				} else {
					mav.addObject("durableprofiles", verify);
					session.setAttribute("exportdurableprofiles", verify);
				}

				// check verify for enable button edit
				System.out.println(year);
				Verify checkVerify = verifyRepository.verify_getVerifyDetailByYears(year);

				//System.out.println(checkVerify.getYears());
				
				if(checkVerify != null) {
					System.out.println(checkVerify.getYears());
					if (!checkVerify.getYears().equalsIgnoreCase(String.valueOf(years + 543))) {
						mav.addObject("disbleButton", 0);
					} else if (dateNow.before(checkVerify.getDateStart())) {
						mav.addObject("disbleButton", 1);
					} else if (dateNow.after(checkVerify.getDateEnd())) {
						mav.addObject("disbleButton", 2);
						mav.addObject("textNotificationsHeader", " **** หมดกำหนดระยะเวลาการตรวจสอบ ****");
						mav.addObject("textNotification",
								"กรุณาติดต่อเจ้าหน้าที่พัสดุคณะวิทยาศาสตร์เพื่อทำการขอขยายเวลา โทร.xxx-xxxxxxx");
					} else {
						mav.addObject("disbleButton", 3);
					}
				}else {
					System.out.println("no_year");
					mav.addObject("no_year",1);
				}
				
				mav.addObject("roomAll", listRoom);
				mav.addObject("year", year);
				mav.addObject("status", 1);
				mav.addObject("statusSelectOld", statusDurable);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public ModelAndView call_update_VerifyDurable(ServletRequest request, HttpSession session) {
		ModelAndView mav = null;
		Staff staffDetail = (Staff) session.getAttribute("staffSession");
		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setCharacterEncoding("UTF-8");

			// data for update VerifyDurable
			String DurableCode = request.getParameter("DurableCode");
			String yearVerify = request.getParameter("yearVerify");
			String status = request.getParameter("statusDurable");
			String note = request.getParameter("note");
//			String id_staff = request.getParameter("id_staff");

//			int result = verifyRepo.verify_updateVerifyDurable(yearVerify, staffDetail.getId_staff(), DurableCode,
//					status, note, simpleDateFormat.format(nowDate));
//
//			if (result != 0) {
//				int results = durableRepo.UpdateDurableStatus(status, DurableCode);
//				if (results != 0) {
//					mav = call_listDurableProfileByYear(request, session);
//					mav.addObject("value", 1);
//					mav.addObject("message", "แก้ไขข้อมูลการตรวจสอบประจำปีเรียบร้อย");
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public ModelAndView call_loadListDurableByYear(HttpSession session) {
		ModelAndView mav = new ModelAndView("listdurablebyyear");
		Staff staffDetail = (Staff) session.getAttribute("staffSession");

		List<String> listRoomString = new ArrayList<>();

		if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
			call_modelMajorAndRoom(mav);
		} else {
			List<Room> roomList = roomRepo.room_getAllRoomByMajor(staffDetail.getMajor().getID_Major());
			listRoomString.add("-");
			for (Room r : roomList) {
				listRoomString.add(r.getRoom_number());
			}
			mav.addObject("roomAll", listRoomString);
		}

		return mav;
	}

	public void call_modelMajorAndRoom(ModelAndView mav) {
		List<Major> listMajor = majorRepo.getAllMajor();
		List<Room> listRoom = roomRepo.getAllRoom();

		JSONObject json = new JSONObject();

		for (int i = 0; i < listMajor.size(); i++) {
			JSONArray jsonArray = new JSONArray();
			for (int j = 0; j < listRoom.size(); j++) {
				if (listMajor.get(i).getID_Major() == listRoom.get(j).getMajor().getID_Major()) {
					jsonArray.put(listRoom.get(j).getRoom_number());
				}
			}
			json.put(String.valueOf(listMajor.get(i).getID_Major()), jsonArray);
		}

		JSONObject jsonMajor = new JSONObject();
		jsonMajor.put("major", json);

		String jsonText = jsonMajor.toString();
		String replaceAll = jsonText.replaceAll("\"", "\'");

		listMajor.add(new Major(99, "-"));

		for (int i = 0; i < listMajor.size(); i++) {
			if (listMajor.get(i).getMajor_Name().equalsIgnoreCase("-")) {
				Collections.swap(listMajor, 0, i);
			}
		}

		mav.addObject("listMajor", listMajor);
		mav.addObject("roomBeanAll", replaceAll);
	}

}
