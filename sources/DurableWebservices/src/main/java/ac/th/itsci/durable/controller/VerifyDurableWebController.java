package ac.th.itsci.durable.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.*;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RoomRepository;
import ac.th.itsci.durable.repo.VerifyDurableRepository;
import ac.th.itsci.durable.repo.VerifyRepository;

@Controller
public class VerifyDurableWebController {

	@Autowired
	DurableRepository durableRepo;

	@Autowired
	VerifyDurableRepository verifyDurableRepo;

	@Autowired
	VerifyRepository verifyRepo;

	@Autowired
	RoomRepository roomRepo;

	@Autowired
	MajorRepository majorRepo;

	@GetMapping(value = "/openCameraScan")
	public ModelAndView load_scanQR() {
		ModelAndView mav = new ModelAndView("scanQRCode");
		return mav;
	}

	@GetMapping(value = "/do_loadeditVerifyDurableByQR")
	public ModelAndView do_loadeditVerifyDurableByQR(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editVerifyDurablePage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String durable_code = request.getParameter("durable_code").replace(":", "/").replaceAll(".PNG", "");
			Verify verify = new Verify();
			Durable durable = new Durable();
			durable.setDurable_code(durable_code);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			year += 543;

			verify = verifyRepo.verify_getVerifyDetailByYears(year + "");
			Date date_now = new Date();
			VerifyDurable vd = new VerifyDurable();
			if (verify != null) {
				if (date_now.before(verify.getDateStart())) {
					System.out.println("Before due");
					message = "ยังไม่ถึงวันเวลาตรวจสอบครุภัณฑ์";
					mav = do_loadVerifyPage(request, session, md, null);
				} else if (date_now.after(verify.getDateEnd())) {
					System.out.println("over  due");
					message = "หมดช่วงวันเวลาตรวจสอบครุภัณฑ์แล้ว";
					mav.addObject("check_over_due", "1");
					mav = do_loadVerifyPage(request, session, md, null);
				} else {
					vd = verifyDurableRepo.getVerifyDurableById(durable.getDurable_code(), year + "");

					System.out.println(year + " " + durable_code);
					if (vd != null) {
						mav.addObject("p", vd.getPk().getDurable().getResponsible_person());
						mav.addObject("vd", vd);
						mav.addObject("verify_check", "1");
					} else {
						System.out.println("dsd");
						mav = do_LoadaddVerifyByQRCode(request, session, md, durable_code, year + "");
					}
				}
				session.setAttribute("messages", message);
			} else {
				message = "ยังไม่มีการกําหนดช่วงเวลาการตรวจครุภัณฑ์";
				session.setAttribute("messages", message);
				mav = do_loadVerifyPage(request, session, md, null);
			}
			List<Room> room_bean = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
			List<String> responsible_person = getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());

			mav.addObject("room_bean", room_bean);
			mav.addObject("responsible_person", responsible_person);

			mav.addObject("message", message);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@GetMapping(value = "/do_loadListDurableByYears")
	public ModelAndView do_loadVerifyPage(ServletRequest request, HttpSession session, Model md, String durable_code) {
		ModelAndView mav = new ModelAndView("listDurableByYears");
		List<Durable> not_verify_durable = new ArrayList<>();
		List<VerifyDurable> verify_durable = new ArrayList<>();
		List<Room> roomByMajor = new ArrayList<>();
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			System.out.println(staffDetail.getMajor().getID_Major());

			int year = Calendar.getInstance().get(Calendar.YEAR);
			year += 543;

			System.out.println(year);

			Verify verify = verifyRepo.verify_getVerifyDetailByYears(year + "");
			Date date_now = new Date();

			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				mav = callListMajorAndRoom();
			} else {
				roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
				mav.addObject("roomNumberMajor", roomByMajor);
			}

			if (verify != null) {
				System.out.println(date_now + " " + verify.getDateStart() + " " + verify.getDateEnd());
				if (date_now.before(verify.getDateStart())) {
					System.out.println("Before due");
					message = "ยังไม่ถึงวันเวลาตรวจสอบครุภัณฑ์";
					mav.addObject("before", "1");
				} else if (date_now.after(verify.getDateEnd())) {
					System.out.println("over  due");
					message = "หมดช่วงวันเวลาตรวจสอบครุภัณฑ์แล้ว";
					mav.addObject("check_over_due", "1");
				} else {
					not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
							staffDetail.getMajor().getID_Major());
					mav.addObject("not_verify_durable", not_verify_durable);
				}
				session.setAttribute("start_date", sdf.format(verify.getDateStart()));
				System.out.println(verify.getDateEnd());
				session.setAttribute("date_end", sdf.format(verify.getDateEnd()));
			} else {
				message = "ยังไม่มีการกําหนดการตรวจสอบครุภัณฑ์ในช่วงเวลานี้";
				mav.addObject("no_verify_year", "1");
			}

			verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(year + "",
					staffDetail.getMajor().getID_Major());
			session.setAttribute("exportdurableprofiles", verify_durable);

			System.out.println(verify_durable.size());

			if (durable_code != null) {
				int i = 0;
				for (VerifyDurable v : verify_durable) {
					if (v.getPk().getDurable().getDurable_code().equals(durable_code)) {
						Collections.swap(verify_durable, 0, i);
					}
					i++;
				}
			}

			List<Room> room_bean = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
			List<String> responsible_person = getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());

			mav.addObject("room_bean", room_bean);
			mav.addObject("responsible_person", responsible_person);
			mav.addObject("date_now", sdf.format(date_now));
			mav.addObject("year_search", year);
			mav.addObject("verify_durable", verify_durable);
			mav.addObject("message", message);
			System.out.println(not_verify_durable.size() + " : " + verify_durable.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("verify_durable", verify_durable);

		return mav;
	}

	public List<String> getRidOfDuplicateStaff(int id_major) {
		List<String> responsible_personList = new ArrayList<>();
		Set<String> responsible_personString = new HashSet<String>();
		responsible_personList = durableRepo.getAllResponsible_person(id_major);
		for (String s : responsible_personList) {
			responsible_personString.add(s);
		}
		responsible_personList.clear();
		responsible_personList.addAll(responsible_personString);
		return responsible_personList;
	}

	@PostMapping(value = "/do_addVerifyDurable")
	public ModelAndView do_addVerifyDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		String message = "";
		int result = 0;
		try {

			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			String[] chk_verify = request.getParameterValues("chk_verify");

			int year = Calendar.getInstance().get(Calendar.YEAR);
			year += 543;
			System.out.println(chk_verify.length);

			for (int i = 0; i < chk_verify.length; i++) {

				String status = request.getParameter("status:" + chk_verify[i]);
				String note = request.getParameter("verify_note:" + chk_verify[i]);
				String date = request.getParameter("date:" + chk_verify[i]);
				String room = request.getParameter("room:" + chk_verify[i]);
				String responsible_person = request.getParameter("responsible_person:" + chk_verify[i]);
				try {
					if (room == null) {
						room = "-";
					}
					result += verifyDurableRepo.insertVerifyDurable(status, date, note.trim(),
							staffDetail.getId_staff(), year + "", chk_verify[i], room, responsible_person);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("result : " + result);

			if (result == chk_verify.length) {
				message = "บันทึกข้อมูลการตรวจครุภัณฑ์สําเร็จ";
			} else {
				message = "ข้อผิดพลาดไม่สามารถบันทึกข้อมูลได้";
			}

			session.setAttribute("messages", message);
			mav = do_loadVerifyPage(request, session, md, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "do_LoadaddVerifyByQRCode")
	public ModelAndView do_LoadaddVerifyByQRCode(ServletRequest request, HttpSession session, Model md,
			String durable_code, String year) {
		ModelAndView mav = new ModelAndView("addVerifyDurableByQR");
		try {
			request.setCharacterEncoding("UTF-8");
			Durable durable = new Durable();
			durable = durableRepo.get_durableByDurableCode(durable_code);
			mav.addObject("year", year);
			mav.addObject("durable", durable);
			mav.addObject("p", durable.getResponsible_person());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "do_addVerifyDurableByQRCode")
	public ModelAndView do_addVerifyDurableByQRCode(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDurableByYears");
		int result = 0;
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String DurableCode = request.getParameter("DurableCode");
			String yearVerify = request.getParameter("yearVerify");
			String id_staff = request.getParameter("id_staff");
			String statusDurable = request.getParameter("statusDurable");
			String roomDurable = request.getParameter("roomDurable");
			String responsible_person = request.getParameter("responsible_person");
			String note = request.getParameter("note");
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

			result = verifyDurableRepo.insertVerifyDurable(statusDurable, sdf.format(date_now), note.trim(),
					Integer.parseInt(id_staff), yearVerify, DurableCode, roomDurable, responsible_person);

			if (result > 0) {
				message = "บันทึกข้อมูลตรวจสอบตรุภัณฑ์สําเร็จ";
			} else {
				message = "ข้อผิดพลาด :: ไม่สามารถบันทึกข้อมูตรวจสอบครุภัณฑ์ลได้";
			}
			session.setAttribute("messages", message);
			mav = do_loadVerifyPage(request, session, md, DurableCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_getListVerifyDurable")
	public ModelAndView do_getListVerifyDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDurableByYears");
		List<Durable> not_verify_durable = null;
		List<VerifyDurable> verify_durable = null;
		List<Room> roomByMajor = null;
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String room_number = request.getParameter("room_id");
			String status = request.getParameter("statusSelect");
			String years = request.getParameter("year");
			Verify verify = verifyRepo.verify_getVerifyDetailByYears(years);

			Date date_now = new Date();

			if (verify != null) {
				if (date_now.before(verify.getDateStart())) {
					System.out.println("Before due");
					message = "ยังไม่ถึงวันเวลาตรวจสอบครุภัณฑ์";
					mav.addObject("before", "1");
					if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
						String major = request.getParameter("major_id");
						List<Room> roomNumber = roomRepo.get_allRoomByMajor(Integer.parseInt(major));

						mav = callListMajorAndRoom();

						mav.addObject("mid", major);
						mav.addObject("roomNumber", roomNumber);
					} else {
						roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
						mav.addObject("roomNumberMajor", roomByMajor);
					}
				} else if (date_now.after(verify.getDateEnd())) {
					System.out.println("over  due");
					message = "หมดช่วงวันเวลาตรวจสอบครุภัณฑ์แล้ว";

					if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
						String major = request.getParameter("major_id");
						String room = "";
						if (major.trim().equals("0")) {
							room = "-";
						} else {
							room = request.getParameter("room_id");
						}
						List<Room> roomNumber = roomRepo.get_allRoomByMajor(Integer.parseInt(major));

						mav = callListMajorAndRoom();

						if (major.equalsIgnoreCase("0") && room.equals("-") && status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							System.out.println("Faculty search by year");
						} else if (!major.equalsIgnoreCase("0") && room.equalsIgnoreCase("-")
								&& status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									Integer.parseInt(major));
							System.out.println("Faculty search by major");
						} else if (!major.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
								&& status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(years, room,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(years,
									Integer.parseInt(major), room);
							System.out.println("Faculty search by room and major");
						} else if (!major.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
								&& !status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(years, room,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(years,
									Integer.parseInt(major), room, status);
							System.out.println("Faculty search by room and major and status");
						} else {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndStatus(years,
									staffDetail.getMajor().getID_Major(), status);
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							System.out.println("Faculty search by year and status");
						}

						mav.addObject("mid", major);
						mav.addObject("roomNumber", roomNumber);

					} else {
						if (!room_number.equalsIgnoreCase("-") && status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(years,
									staffDetail.getMajor().getID_Major(), room_number);
							// not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoom(years,
							// room_number);
							System.out.println("search by room_number");
						} else if (room_number.equalsIgnoreCase("-") && !status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndStatus(years,
									staffDetail.getMajor().getID_Major(), status);
							// not_verify_durable = durableRepo.getNotVerifyDurableByYear(years);
							System.out.println("search by status");
						} else if (!room_number.equalsIgnoreCase("-") && !status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(years,
									staffDetail.getMajor().getID_Major(), room_number, status);
							// not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoom(years,
							// room_number);
							System.out.println("search by room_number and status");
						} else {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							// not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
							// staffDetail.getMajor().getID_Major());
							System.out.println("search by year");
						}
						roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
						mav.addObject("roomNumberMajor", roomByMajor);
					}

					mav.addObject("check_over_due", "1");
				} else {
					if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
						String room = "";
						String major = request.getParameter("major_id");
						System.out.println(major);
						System.out.println(status);
						if (major.trim().equals("0")) {
							room = "-";
						} else {
							room = request.getParameter("room_id");
						}
						System.out.println(room);
						List<Room> roomNumber = roomRepo.get_allRoomByMajor(Integer.parseInt(major));

						if (major.equalsIgnoreCase("0") && room.equals("-") && status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							System.out.println("Faculty search by year");
						} else if (!major.equalsIgnoreCase("0") && room.equalsIgnoreCase("-")
								&& status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									Integer.parseInt(major));
							System.out.println("Faculty search by major");
						} else if (!major.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
								&& status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(years, room,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(years,
									Integer.parseInt(major), room);
							System.out.println("Faculty search by room and major");
						} else if (!major.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
								&& !status.equalsIgnoreCase("-")) {
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(years, room,
									Integer.parseInt(major));
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(years,
									Integer.parseInt(major), room, status);
							System.out.println("Faculty search by room and major and status");
						} else {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndStatus(years,
									staffDetail.getMajor().getID_Major(), status);
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							System.out.println("Faculty search by year and status");
						}

						mav = callListMajorAndRoom();

						mav.addObject("mid", major);
						mav.addObject("roomNumber", roomNumber);

					} else {
						if (!room_number.equalsIgnoreCase("-") && status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(years,
									staffDetail.getMajor().getID_Major(), room_number);
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoom(years, room_number);
							System.out.println("search by room_number");
						} else if (room_number.equalsIgnoreCase("-") && !status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndStatus(years,
									staffDetail.getMajor().getID_Major(), status);
							not_verify_durable = durableRepo.getNotVerifyDurableByYear(years);
							System.out.println("search by status");
						} else if (!room_number.equalsIgnoreCase("-") && !status.equals("-")) {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(years,
									staffDetail.getMajor().getID_Major(), room_number, status);
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoom(years, room_number);
							System.out.println("search by room_number and status");
						} else {
							verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(years,
									staffDetail.getMajor().getID_Major());
							System.out.println("search by year");
						}
						roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
						mav.addObject("roomNumberMajor", roomByMajor);
					}
				}

			} else {
				if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
					String major = request.getParameter("major_id");
					List<Room> roomNumber = roomRepo.get_allRoomByMajor(Integer.parseInt(major));

					mav = callListMajorAndRoom();

					mav.addObject("mid", major);
					mav.addObject("roomNumber", roomNumber);

				} else {
					roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
					mav.addObject("roomNumberMajor", roomByMajor);
				}
				mav.addObject("no_verify_year", "1");
				mav.addObject("message", "ไม่พบข้อมูลปีตรวจสอบครุภัณฑ์");
			}

			session.setAttribute("messages", message);
			mav.addObject("not_verify_durable", not_verify_durable);
			mav.addObject("verify_durable", verify_durable);
			session.setAttribute("exportdurableprofiles", verify_durable);
			mav.addObject("year_search", years);
			mav.addObject("statusSelectOld", status);
			mav.addObject("room_select", room_number);

			List<Room> room_bean = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
			List<String> responsible_person = getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());

			mav.addObject("room_bean", room_bean);
			mav.addObject("responsible_person", responsible_person);

		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("verify_durable", verify_durable);

		return mav;
	}

	@PostMapping(value = "/do_updateVerify")
	public ModelAndView do_updateVerify(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDurableByYears");
		List<Room> roomByMajor = null;
		List<Durable> not_verify_durable = null;
		List<VerifyDurable> verify_durable = null;
		String messages = "";
		int result = 0;
		try {

			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String durableCode = request.getParameter("DurableCode");
			String yearVerify = request.getParameter("yearVerify");
			String verifyDate = request.getParameter("verifyDate");
			String id_staff = request.getParameter("id_staff");
			String statusDurable = request.getParameter("statusDurable");
			String note = request.getParameter("note");
			String room_update = request.getParameter("roomDurable");
			String responsible_persons = request.getParameter("responsible_person");

			// old search
			String search_year = request.getParameter("search_year");
			String search_status = request.getParameter("search_status");
			String search_room = request.getParameter("search_room");

			Date date_now = new Date();

			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

			result = verifyDurableRepo.verify_updateVerifyDurable(yearVerify, Integer.parseInt(id_staff), durableCode,
					statusDurable, note, sdf.format(date_now), room_update, responsible_persons);

			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				// old searchฃ
				String major_search = request.getParameter("major_search");
				String room = "";
				List<Room> roomNumber = null;

				int year = Calendar.getInstance().get(Calendar.YEAR);
				year += 543;

				if (major_search == null || major_search.equals("0") || major_search.equals("")) {
					room = "-";
				} else {
					room = request.getParameter("search_room");
					roomNumber = roomRepo.get_allRoomByMajor(Integer.parseInt(major_search));
				}
				try {
					if (major_search.equalsIgnoreCase("0") && room.equals("-") && search_status.equalsIgnoreCase("-")
							&& !search_year.equals("")) {
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(search_year,
								staffDetail.getMajor().getID_Major());
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(search_year,
								staffDetail.getMajor().getID_Major());
						System.out.println("Faculty search by year");
					} else if (!major_search.equalsIgnoreCase("0") && room.equalsIgnoreCase("-")
							&& search_status.equalsIgnoreCase("-") && !search_year.equals("")) {
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(search_year,
								Integer.parseInt(major_search));
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(search_year,
								Integer.parseInt(major_search));
						System.out.println("Faculty search by major");
					} else if (!major_search.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
							&& search_status.equalsIgnoreCase("-") && !search_year.equals("")) {
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(search_year, room,
								Integer.parseInt(major_search));
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(search_year,
								Integer.parseInt(major_search), room);
						System.out.println("Faculty search by room and major");
					} else if (!major_search.equalsIgnoreCase("0") && !room.equalsIgnoreCase("-")
							&& !search_status.equalsIgnoreCase("-") && !search_year.equals("")) {
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoomAndMajor(search_year, room,
								Integer.parseInt(major_search));
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(search_year,
								Integer.parseInt(major_search), room, search_status);
						System.out.println("Faculty search by room and major and status");
					} else if (!search_year.equals("") && !search_status.equalsIgnoreCase("-") && room == null
							&& major_search.equalsIgnoreCase("0")) {
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndStatus(search_year,
								staffDetail.getMajor().getID_Major(), search_status);
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(search_year,
								staffDetail.getMajor().getID_Major());
						System.out.println("Faculty search by year and status" + " " + search_year + " "
								+ staffDetail.getMajor().getID_Major() + " " + search_status);
					} else {
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(search_year,
								staffDetail.getMajor().getID_Major());
						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(search_year,
								staffDetail.getMajor().getID_Major());
						System.out.println("Faculty search by year");
					}
				} catch (Exception e) {
					verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(year + "",
							staffDetail.getMajor().getID_Major());
					not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
							staffDetail.getMajor().getID_Major());
					search_year = year + "";
				}

				mav = callListMajorAndRoom();

				mav.addObject("mid", major_search);
				mav.addObject("roomNumber", roomNumber);

				mav.addObject("major_search", major_search);
			} else {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				year += 543;
				try {
					if (!search_room.equalsIgnoreCase("-") && search_status.equals("-") && !search_year.equals(null)) {

						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(search_year,
								staffDetail.getMajor().getID_Major(), search_room);
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndRoom(search_year, search_room);

						System.out.println("search by room_number");
					} else if (search_room.equalsIgnoreCase("-") && search_room.equals("") && !search_status.equals("-")
							&& !search_status.equals("") && search_year != null) {

						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoom(search_year,
								staffDetail.getMajor().getID_Major(), search_status);
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
								staffDetail.getMajor().getID_Major());

						System.out.println("search by status");
					} else if (!search_room.equalsIgnoreCase("-") && !search_room.equals("")
							&& !search_status.equals("-") && !search_status.equals("") && search_year != null) {

						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajorAndRoomAndStatus(search_year,
								staffDetail.getMajor().getID_Major(), search_room, search_status);
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
								staffDetail.getMajor().getID_Major());

						System.out.println("search by room_number and status");
					} else {

						verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(year + "",
								staffDetail.getMajor().getID_Major());
						not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
								staffDetail.getMajor().getID_Major());
						System.out.println("search by year");
					}
				} catch (Exception e) {
					verify_durable = verifyDurableRepo.getVerifyDurableByYearAndMajor(year + "",
							staffDetail.getMajor().getID_Major());
					not_verify_durable = durableRepo.getNotVerifyDurableByYearAndMajor(year + "",
							staffDetail.getMajor().getID_Major());
					search_year = year + "";
				}

				roomByMajor = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
				mav.addObject("roomNumberMajor", roomByMajor);
			}
			
			int i = 0;
			for(VerifyDurable v : verify_durable) {
				if(v.getPk().getDurable().getDurable_code().equals(durableCode)) {
					Collections.swap(verify_durable, 0, i);
				}
				i++;
			}

			mav.addObject("verify_durable", verify_durable);
			session.setAttribute("exportdurableprofiles", verify_durable);
			mav.addObject("not_verify_durable", not_verify_durable);
			mav.addObject("room_select", search_room);
			mav.addObject("statusSelectOld", search_status);
			mav.addObject("year_search", search_year);

			if (result != -1) {
				messages = "แก้ไขข้อมูลการตรวจสอบครุภัณฑ์สําเร็จ";
				session.setAttribute("messages", messages);
			} else {
				messages = "ข้อผิดพลาด :: ไม่สามารถแก้ไขข้อมูลการตรวจสอบครุภัณฑ์ได้";
				session.setAttribute("messages", messages);
			}

			List<Room> room_bean = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
			List<String> responsible_person = getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());

			mav.addObject("room_bean", room_bean);
			mav.addObject("responsible_person", responsible_person);

		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("verify_durable", verify_durable);

		return mav;
	}

	@GetMapping(value = "/do_exportFileDurable")
	public void do_exportFileDurable(ServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			request.setCharacterEncoding("UTF-8");
			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			List<VerifyDurable> durableProfileBeans = (List<VerifyDurable>) session
					.getAttribute("exportdurableprofiles");

			response.setContentType("application/octet-stream");
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=DurableReport.xlsx";

			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.setHeader(headerKey, headerValue);

			String path = request.getServletContext().getRealPath("/") + "file/excel/3.xlsx";
			// File file = ResourceUtils.getFile(path);

			FileInputStream is = new FileInputStream(path);
			if (is != null) {
				System.out.println("habe fiel");
			}
			Workbook wb = WorkbookFactory.create(is);
			Sheet s = wb.getSheetAt(0);

			Row row0 = s.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue("รายงานตรวจสอบครุภัณฑ์ของ"
					+ durableProfileBeans.get(0).getPk().getStaff().getMajor().getMajor_Name());

			CellStyle cellstyle = cell0.getCellStyle();
			cellstyle.setAlignment(CellStyle.ALIGN_CENTER);

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Row row02 = s.createRow(2);
			Cell cell02 = row02.createCell(0);
			cell02.setCellValue("ณ วันที่ " + ((DateFormat) formatter).format(new Date()));

			Row row03 = s.createRow(3);

			Cell cell00 = row03.createCell(0);
			cell00.setCellValue("ลำดับที่");

			Cell cell01 = row03.createCell(1);
			cell01.setCellValue("รหัสครุภัณฑ์");

			Cell cell02_3 = row03.createCell(2);
			cell02_3.setCellValue("จำนวน");

			Cell cell03 = row03.createCell(3);
			cell03.setCellValue("ชื่อครุภัณฑ์");

			Cell cell04 = row03.createCell(4);
			cell04.setCellValue("ยี่ห้อ");

			Cell cell05 = row03.createCell(5);
			cell05.setCellValue("รุ่น/รายละเอียด");

			Cell cell06 = row03.createCell(6);
			cell06.setCellValue("ราคา/หน่วย");

			Cell cell07 = row03.createCell(7);
			cell07.setCellValue("วัน เดือน ปี(ที่ได้มา)");

			Cell cell11_3 = row03.createCell(11);
			cell11_3.setCellValue("สภาพ");

			Cell cell12_3 = row03.createCell(12);
			cell12_3.setCellValue("หมายเหตุ");

			Cell cell08 = row03.createCell(8);
			cell08.setCellValue("ใช้ประจําที่(" + durableProfileBeans.get(0).getPk().getVerify().getYears() + ")");

			CellStyle cellstyles = cell02.getCellStyle();
			cellstyles.setAlignment(CellStyle.ALIGN_CENTER);

			for (int j = 0; j < durableProfileBeans.size(); j++) {
				Row row2 = s.createRow(j + 5);
				Cell cell = row2.createCell(0);
				cell.setCellValue(j + 1);

				// durablecode
				Cell cell1 = row2.createCell(1);
				cell1.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_code());

				// number
				Cell cell2 = row2.createCell(2);
				cell2.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_number());

				// durablename
				Cell cell3 = row2.createCell(3);
				cell3.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_name());

				// brandname
				Cell cell4 = row2.createCell(4);
				cell4.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_brandname());

				// modelordetail
				Cell cell5 = row2.createCell(5);
				cell5.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_model());

				// price column7
				Cell cell6 = row2.createCell(6);
				cell6.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_price());

				// entrancedate column8
				Cell cell7 = row2.createCell(7);
				cell7.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getDurable_entrancedate());

				// roomnumber column9
				Cell cell8 = row2.createCell(8);
				try {
					cell8.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getRoom().getRoom_number());
				} catch (Exception e) {
					cell8.setCellValue("-");
				}

				// ownername column10
				Cell cell9 = row2.createCell(9);
				cell9.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getResponsible_person());

				// staffname column11
				Cell cell10 = row2.createCell(10);
				cell10.setCellValue(durableProfileBeans.get(j).getPk().getStaff().getStaff_name() + " "
						+ durableProfileBeans.get(j).getPk().getStaff().getStaff_lastname());

				// status column12
				Cell cell11 = row2.createCell(11);
				cell11.setCellValue(durableProfileBeans.get(j).getDurable_status());

				// note column13
				Cell cell12 = row2.createCell(12);
				cell12.setCellValue(durableProfileBeans.get(j).getNote());

			}

			ServletOutputStream outputStream = response.getOutputStream();
			s.getPrintSetup().setLandscape(true);
			s.getPrintSetup().setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE);
			wb.write(outputStream);
			wb.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "testEx")
	public void testExpor(HttpServletResponse response) {
		response.setContentType("application/cotet-stream");
	}

	// callListRoom
	public ModelAndView callListMajorAndRoom() {
		ModelAndView modelAndView = new ModelAndView("listDurableByYears");
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

		listMajor.add(new Major(0, "-"));

		for (int i = 0; i < listMajor.size(); i++) {
			if (listMajor.get(i).getMajor_Name().equalsIgnoreCase("-")) {
				Collections.swap(listMajor, 0, i);
			}
		}

		System.out.println(replaceAll);

		modelAndView.addObject("listMajor", listMajor);
		modelAndView.addObject("roomBeanAll", replaceAll);
		return modelAndView;
	}

}
