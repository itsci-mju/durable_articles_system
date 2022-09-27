package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.*;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.repo.*;
import ac.th.itsci.durable.service.DurableWebService;
import ac.th.itsci.durable.service.RepairWebService;
import ac.th.itsci.durable.service.StaffWebService;
import ac.th.itsci.durable.service.VerifyDurableWebService;
import ac.th.itsci.durable.service.VerifyWebService;

@Controller
public class WebController {

	@Autowired
	VerifyRepository verifyRepository;

	@Autowired
	DurableRepository durableRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	RoomRepository roomRepo;

	@Autowired
	RepairRepository repairRepo;

	@Autowired
	VerifyDurableRepository verifyRepo;

	@Autowired
	private DurableWebService durableWebService;

	@Autowired
	private StaffWebService staffWebService;

	@Autowired
	private VerifyWebService verifyWebService;

	@Autowired
	private RepairWebService repairWebService;

	@Autowired
	private VerifyDurableWebService verifyDurableWebService;

	Properties properties = new Properties();

	@PostMapping(value = "/loginweb")
	public ModelAndView do_login(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = staffWebService.call_login(request, session);
		return mav;
	}

	@GetMapping(value = "/logout")
	public ModelAndView do_logout(ServletRequest request, HttpSession session, Model md, HttpServletResponse response) {
		ModelAndView mav = staffWebService.call_logout(request, session);
		return mav;
	}

	@GetMapping(value = "/durable")
	public ModelAndView welcomeController(HttpSession session) {
		ModelAndView mav = staffWebService.call_checkStaffSession(session);
		return mav;
	}

	@PostMapping(value = "/durable")
	public ModelAndView welcomeControllerPost(HttpSession session) {
		ModelAndView mav = staffWebService.call_checkStaffSession(session);
		return mav;
	}

	@GetMapping(value = "/home")
	public ModelAndView welcome(HttpSession session) {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}

	@GetMapping(value = "/load_importfiledurable_page")
	public ModelAndView load_importFileDurable_page() {
		ModelAndView mav = new ModelAndView("importfiledurable");
		return mav;
	}

	@PostMapping(value = "/doimportFileDurable")
	public ModelAndView do_importFileDurable(ServletRequest request, Model md, HttpServletRequest requesth,
			@RequestParam("filedurable") MultipartFile[] file_excel) throws UnsupportedEncodingException {
		ModelAndView mav = durableWebService.call_ImportFileDurable(request, requesth, file_excel);
		return mav;
	}

	@PostMapping(value = "/ExportFileExcelServlet")
	public void ExportFileExcelServlet(ServletRequest request, HttpServletResponse response, HttpSession session,
			Model md) {
		durableWebService.call_ExportFileExcelDurable(request, response, session);
	}

	@PostMapping(value = "/do_UpdateDurableDetail")
	public ModelAndView do_UpdateDurableDetail(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableWebService.call_UpdateDurableDetail(request, session);
		return mav;
	}

	@PostMapping(value = "/printQrCode")
	public ModelAndView printQrCode(ServletRequest request, HttpSession session, HttpServletResponse response) {
		ModelAndView mav = durableWebService.call_PrintQrCodeDurable(request, session, response);
		return mav;
	}

	@GetMapping(value = "/listDurableAll")
	public ModelAndView listDurableAll(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = null;
		try {
			request.setCharacterEncoding("UTF-8");
			String room_id = request.getParameter("room_id");
			String major_id = request.getParameter("major_id");
			mav = durableWebService.callDurableList(major_id, room_id, session, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/load_inspection_schedule")
	public ModelAndView do_inspection_schedule(HttpSession session) {
		ModelAndView mav = verifyWebService.call_getAllVerify(session);
		return mav;
	}

	@PostMapping(value = "/doAdd_Inspection_schedule")
	public ModelAndView doAdd_Verify(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = verifyWebService.call_createVerify(request, session);
		return mav;
	}

	@PostMapping(value = "/Edit_VerifyDate")
	public ModelAndView Edit_VerifyDate(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = verifyWebService.call_updateVerify(request, session);
		return mav;
	}

	@GetMapping(value = "/listDilapidatedDurable")
	public ModelAndView listDilapidatedDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = repairWebService.call_listDilapidatedDurable(request, session);
		return mav;
	}

	@GetMapping(value = "/load_listdurablebyyear")
	public ModelAndView do_loadListDurableByYear(HttpSession session) {
		ModelAndView mav = verifyDurableWebService.call_loadListDurableByYear(session);
		return mav;
	}

	@PostMapping(value = "/listDurableProfileByYear")
	public ModelAndView listDurableProfileByYear(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = verifyDurableWebService.call_listDurableProfileByYear(request, session);
		return mav;
	}

	@PostMapping(value = "/update_VerifyDurable")
	public ModelAndView update_VerifyDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = verifyDurableWebService.call_update_VerifyDurable(request, session);
		return mav;
	}

	// ------------------------------------------------------ code fluke
	// ------------------------------------------------------

	@GetMapping(value = "/loadRecordborrowdurable")
	public ModelAndView loadRecordborrowdurable() {
		ModelAndView mav = new ModelAndView("recordborrowdurable");
		return mav;
	}

	@GetMapping(value = "/listBorrowingByBorrowercode")
	public ModelAndView listBorrowingByBorrowercode(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listborrowingbyborrowercode");
		try {
			request.setCharacterEncoding("UTF-8");
//			BorrowingManager borrowingManager = new BorrowingManager();
//			String method = request.getParameter("method");
			//
//			if ("showall".equals(method)) {
//				Vector<BorrowingBean> borrowingBeans = borrowingManager
//						.listBorrowing();
			//
//				httpSession.setAttribute("listborrowing", borrowingBeans);
//				response.sendRedirect(path
//						+ "/page/listborrowingbyborrowercode.jsp");
//			} else {
//				String student_code = request.getParameter("student_code");
			//
//				Vector<BorrowingBean> borrowingBeans = borrowingManager
//						.listBorrowingByBorrowerCode(student_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/listBorrowingByDurablecode")
	public ModelAndView listBorrowingByDurablecode(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listborrowingbydurablecode");
		try {
			request.setCharacterEncoding("UTF-8");
//			String method = request.getParameter("method");
			//
//					if ("showall".equals(method)) {
//						BorrowingManager borrowingManager = new BorrowingManager();
//						Vector<BorrowingBean> borrowingBeans = borrowingManager
//								.listProfileBorrowing();
			//
//						session.setAttribute("borrowing_list", borrowingBeans);
//						response.sendRedirect(request.getContextPath()
//								+ "/page/listborrowingbydurablecode.jsp");
//					} else {
//						String durablecode = request.getParameter("durablecode");
			//
//						BorrowingManager borrowingManager = new BorrowingManager();
//						Vector<BorrowingBean> borrowingBeans = borrowingManager
//								.listBorrowingByDurableCode(durablecode);
			//
//						session.setAttribute("borrowing_list", borrowingBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/listBorrowerProfile")
	public ModelAndView listBorrowerProfile(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listborrowerprofile");

		try {
			request.setCharacterEncoding("UTF-8");
//			String method = request.getParameter("method");
//			if ("showall".equals(method)) {
//				BorrowingManager borrowingManager = new BorrowingManager();
//				Vector<BorrowerBean> borrowwerBeans = borrowingManager
//						.listBorrowerprofile();
			//
//				for (int i = 0; i < borrowwerBeans.size(); i++) {
//					borrowwerBeans.get(i).setCountBorrow(
//							borrowingManager.searchCountBorrow(borrowwerBeans
//									.get(i).getBorrower_name()));
//					borrowwerBeans.get(i).setCountreturns(
//							borrowingManager.searchCountReturns(borrowwerBeans.get(
//									i).getBorrower_name()));
//				}
//				session.setAttribute("borrowwer_list", borrowwerBeans);
//				response.sendRedirect(request.getContextPath()
//						+ "/page/listborrowerprofile.jsp");
//			} else {
//				String idcode = request.getParameter("idcode");
			//
//				BorrowingManager borrowingManager = new BorrowingManager();
//				Vector<BorrowerBean> borrowwerBeans = borrowingManager
//						.listBorrowerprofileByidcard(idcode);
			//
//				session.setAttribute("borrowwer_list", borrowwerBeans);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/createRepair")
	public ModelAndView createRepair(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		try {
			String durablecode = request.getParameter("btn_add");
			if (durablecode != null) {
				session.setAttribute("durable_code", durablecode);
				session.setAttribute("type", "1");

			} else {
				session.setAttribute("durable_code", "");
				session.setAttribute("type", "2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/listEditRepair")
	public ModelAndView listEditRepair(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listeditrepairprofile");
		try {
			request.setCharacterEncoding("UTF-8");
//			RepairProfileManager repairProfileMng = new RepairProfileManager();
//			Vector<RepairDurableBean> repairProfileBeans = repairProfileMng
//					.listRepairProfileByDurableCode();
			//
//			session.setAttribute("list_repairedit", repairProfileBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public List<String> getRidOfDuplicateRoom(int id_major) {
		List<String> roomnumber = new ArrayList<>();
		Set<String> listRoomString = new HashSet<String>();
		roomnumber = roomRepo.RoomAllByIdmajor(id_major);
		for (String r : roomnumber) {
			listRoomString.add(r);
		}
		roomnumber.clear();
		roomnumber.addAll(listRoomString);
		return roomnumber;
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

	@GetMapping(value = "/displayRecordDurable")
	public ModelAndView displayRecordDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("recorddurable");

		try {
			request.setCharacterEncoding("UTF-8");
			Staff staffDetail = (Staff) session.getAttribute("staffSession");

//			List<Durable> roomBeans =
//					durableRepo.getDurableDetailByIDMajor(staffDetail.getMajor().getID_Major());

			List<String> roomBeans = this.getRidOfDuplicateRoom(staffDetail.getMajor().getID_Major());

//			Set<String> listRoomString = new HashSet<String>();
//			roomBeans = roomRepo.RoomAllByIdmajor(staffDetail.getMajor().getID_Major());
//			for (String r : roomBeans) {
//				listRoomString.add(r);
//			}
//			roomBeans.clear();
//			roomBeans.addAll(listRoomString);

			List<String> responsible_personList = this.getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());

//			Set<String> responsible_personString = new HashSet<String>();
//			responsible_personList = durableRepo.getAllResponsible_person(staffDetail.getMajor().getID_Major());
//			for (String s : responsible_personList) {
//				responsible_personString.add(s);
//			}
//			responsible_personList.clear();
//			responsible_personList.addAll(responsible_personString);

//			for(int i=0; i<roomBeans.size(); i++) {
//				System.out.println(roomBeans.get(i));
//			}
			// roomRepo.RoomAllByIdmajor(staffDetail.getMajor().getID_Major());

			// System.out.println(roomBeans.size());

//			List<String> Responsible_personList = durableRepo.getDurableDetailByIDMajor(staffDetail.getMajor().getID_Major());
//			
//			for(int i = 0 ;  i < Responsible_personList.size() ; i++) {
//				if(Responsible_personList.get(i) == null) {
//					Responsible_personList.remove(i);
//				}
//			}
//			

			session.setAttribute("roomBeanAll", roomBeans);
			session.setAttribute("ownerBeanAll", responsible_personList);

			session.setAttribute("status", "1");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "doRecordDurable")
	public ModelAndView do_recordDurable(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();

		try {
			request.setCharacterEncoding("UTF-8");
			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String durablecode = request.getParameter("durablecode");
			String numdurable = request.getParameter("numdurable");
			String namedurable = request.getParameter("namedurable");
			String brand = request.getParameter("brand");
			String detail = request.getParameter("detail");
			String pricedurable = request.getParameter("pricedurable");
			String datepicker = request.getParameter("datepicker");
			String room = request.getParameter("room_id");
			String owner = request.getParameter("owner_id");
			String status = request.getParameter("status");
			String note = request.getParameter("note");

			List<Room> roomList = roomRepo.getAllRoom();
			Room roomDetail = new Room();
			for (Room r : roomList) {
				if (r.getRoom_number().equalsIgnoreCase(room)) {
					roomDetail.setRoom_number(r.getRoom_number());
					roomDetail.setRoom_name(r.getRoom_name());
					roomDetail.setBuild(r.getBuild());
					roomDetail.setFloor(r.getFloor());
				}
			}

			Durable durableDetail = new Durable();
			durableDetail.setDurable_code(durablecode);
			durableDetail.setDurable_name(namedurable);
			durableDetail.setDurable_number(numdurable);
			durableDetail.setDurable_brandname(brand);
			durableDetail.setDurable_model(detail);
			durableDetail.setDurable_price(pricedurable);
			durableDetail.setDurable_statusnow(status);
			durableDetail.setResponsible_person(owner);
			durableDetail.setDurable_image("-");
			durableDetail.setDurable_Borrow_Status("คืน");
			durableDetail.setDurable_entrancedate(datepicker);
			durableDetail.setNote(note);
			durableDetail.setMajor(staffDetail.getMajor());
			durableDetail.setRoom(roomDetail);

			String message = "";
			try {
				durableRepo.recordDurable(durableDetail.getDurable_code(), durableDetail.getDurable_Borrow_Status(),
						durableDetail.getDurable_brandname(), durableDetail.getDurable_entrancedate(),
						durableDetail.getDurable_image(), durableDetail.getDurable_model(),
						durableDetail.getDurable_name(), durableDetail.getDurable_number(),
						durableDetail.getDurable_price(), durableDetail.getDurable_statusnow(),
						durableDetail.getResponsible_person(), durableDetail.getNote(),
						staffDetail.getMajor().getID_Major(), roomDetail.getRoom_number());
				message = "เพิ่มข้อมูลสำเร็จ";
				session.setAttribute("value", 1);
				session.setAttribute("message", message);
				mav = new ModelAndView("home");
			} catch (Exception e) {
				System.out.println("fail");
				message = "เพิ่มข้อมูลไม่สำเร็จเนื่องจากรหัสครุภัณฑ์นี้มีข้อมูลอยู่แล้ว";
				session.setAttribute("value", 1);
				session.setAttribute("message", message);
				mav = new ModelAndView("recorddurable");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	// To be repair
//	@PostMapping(value = "/addVerifyDurable")
//	public ModelAndView addVerifyDurable(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView();
//		try {
//			request.setCharacterEncoding("UTF-8");
//			Staff staffDetail = (Staff) session.getAttribute("staffSession");
//
//			String s_year = request.getParameter("year_save");
//			int yearInput = Integer.parseInt(s_year);
//			String[] durable_id = request.getParameterValues("durable_id");
//			String[] status = request.getParameterValues("status");
//			String[] note = request.getParameterValues("note");
//
//			List<VerifyDurable> listVerifyDurable = new ArrayList<>();
//
//			Calendar c = Calendar.getInstance();
//			int year = c.get(Calendar.YEAR);
//			int month = c.get(Calendar.MONTH);
//			int dayofmonth = c.get(Calendar.DAY_OF_MONTH);
//			System.out.println(year);
//			List<VerifyDurable> result = new ArrayList<>();
//			if (yearInput != year) {
//				session.setAttribute("value", 1);
//				session.setAttribute("message", "ไม่สามารถบันทึกข้อมูลได้ กรูณาใส่ปีการศึกษาใหม่ให้ถูกต้อง");
//				mav = new ModelAndView("verifydurable");
//			} else {
//				try {
//					verifyRepo.add_verify_year_insert(yearInput + "");
//				} catch (Exception e) {
//					System.out.println("duplicate year !");
//				}
//				for (int i = 0; i < durable_id.length; i++) {
//					VerifyDurable v = new VerifyDurable();
//					VerifyDurableID vid = new VerifyDurableID();
//					Durable d = new Durable();
//					d.setDurable_code(durable_id[i]);
//					vid.setDurable(d);
//					Staff s = new Staff();
//					s.setId_staff(staffDetail.getId_staff());
//					vid.setStaff(s);
//					v.setNote(note[i]);
//					v.setSave_date(dayofmonth + "/" + (month + 1) + "/" + year);
//					Verify vf = new Verify();
//					vf.setYears(s_year);
//					vid.setVerify(vf);
//					v.setDurable_status(status[i]);
//					v.setPk(vid);
//
//					listVerifyDurable.add(v);
////					v.setDurable(new Durable(durable_id[i]));
////					v.setStaff(new Staff(staffDetail.getId_staff()));
////					v.setNote(note[i]);
////					v.setSave_date(dayofmonth + "/" + (month + 1) + "/" + year);
////					v.setYears(s_year);
////					v.setDurable_status(status[i]);
////					listVerifyDurable.add(v);
//
//					try {
//						result = verifyRepo.addVerifyFromList(v.getDurable_status(), v.getSave_date(), v.getNote(),
//								v.getPk().getStaff().getId_staff(), v.getPk().getVerify().getYears(),
//								v.getPk().getDurable().getDurable_code());
//					} catch (Exception e) {
////						session.setAttribute("value", 1);
////						session.setAttribute("message", " �ѹ�֡�����š�õ�Ǩ�ͺ���º����  ");
////						mav = new ModelAndView("verifydurable");
//					}
//					durableRepo.UpdateDurableStatus(v.getDurable_status(), v.getPk().getDurable().getDurable_code());
//				}
//				if (null == result || result.size() == 0) {
//					session.setAttribute("value", 1);
//					session.setAttribute("message", "บันทึกข้อมูลการตรวจสอบเรียบร้อย");
//					mav = new ModelAndView("verifydurable");
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

}
