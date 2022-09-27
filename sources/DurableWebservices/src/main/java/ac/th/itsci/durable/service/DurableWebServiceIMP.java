package ac.th.itsci.durable.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.DurableControll;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.repo.DurableControllRepository;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RoomRepository;
import ac.th.itsci.durable.util.ReadFileExcelType;

@Service
public class DurableWebServiceIMP implements DurableWebService {

	@Autowired
	DurableRepository durableRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	RoomRepository roomRepo;

	@Autowired
	DurableControllRepository durableControllRepo;

	Properties properties = new Properties();

	public ModelAndView callDurableList(String major_id, String room_id, HttpSession session, ServletRequest request) {
		ModelAndView mav = new ModelAndView("listqrcode");
		try {
			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			request.setCharacterEncoding("UTF-8");
			// list data room all for select room in jsp
			List<String> roomAllForSelect = new ArrayList<>();
			Set<String> roomAllForSelectSet = new HashSet<String>();
			// list data room for edit durable
			List<String> roomAllForEdit = new ArrayList<>();
			// list data owner for edit durable
			Set<String> ownerBeanAll = new HashSet<>();
			// list data room for create select room string
			List<Room> listRoom;
			// list data durable for show in jsp
			List<Durable> durableList = null;
			// list data durable don't have room
			List<Durable> durableNoRoom = new ArrayList<>();
			// list data durable by room;
			
			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				if (major_id != null) {
//					if (room_id.equalsIgnoreCase("-")) {
//						mav = callListMajorAndRoom();
//						mav.addObject("value", 1);
//						durableList = durableRepo.getDurable_DetailByIDmajor(Integer.parseInt(major_id));
						//mav.addObject("message", "กรุณาเลือกสาขา");
//						mav.addObject("roomNumber", "-");
//					} else {
						if (room_id.equalsIgnoreCase("-")) {
							durableList = durableRepo.getDurable_DetailByIDmajor(Integer.parseInt(major_id));

							for (Durable d : durableList) {
								ownerBeanAll.add(d.getResponsible_person());
							}

							if (durableList.size() == 0) {
								mav = callListMajorAndRoom();
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								roomAllForSelect.add("-");
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", null);
								mav.addObject("dataNotfound", 0);
							} else {
								mav = callListMajorAndRoom();
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
									roomAllForEdit.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								roomAllForSelect.add("-");
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								mav.addObject("roomSelectEdit", roomAllForEdit);
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("ownerBeanAll", ownerBeanAll);
								mav.addObject("listdurableall", durableList);
							}
						} else {
							durableList = durableRepo.getDurable_DetailByRoom(room_id);
							System.out.println(durableList.size());
							for (Durable d : durableList) {
								ownerBeanAll.add(d.getResponsible_person());
							}
							if (durableList.size() == 0) {
								mav = callListMajorAndRoom();
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}
								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase(room_id)) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", null);
								mav.addObject("dataNotfound", 0);
							} else {
								mav = callListMajorAndRoom();
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
									roomAllForEdit.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase(room_id)) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								
								mav.addObject("ownerBeanAll", ownerBeanAll);
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("roomSelectEdit", roomAllForEdit);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", durableList);
							}
						}
					//}
				} else {
					mav = callListMajorAndRoom();
					mav.addObject("roomNumber", "-");
//					List<Room> roomAll = roomRepo.getAllRoom();
//					List<Major> majorAll = majorRepo.getAllMajor();
//					mav.addObject("roomNumber", roomAll);

				}
			} else {

				listRoom = roomRepo.room_getAllRoomByMajor(staffDetail.getMajor().getID_Major());
				durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());

				// durableList = durableRepo.getSearchDurable("", "", "");
				// set data room all for select room in jsp
				roomAllForSelectSet.add("-");
				for (Room room : listRoom) {
					roomAllForSelectSet.add(room.getRoom_number());
					roomAllForEdit.add(room.getRoom_number());
				}
				// set data owner for edit durable
				for (Durable d : durableList) {
					ownerBeanAll.add(d.getResponsible_person());
				}

				if (room_id == null) {
					if (durableList.size() == 0) {
						mav.addObject("listdurableall", null);
					} else {
						// check durable room == null
						for (Durable d : durableList) {
							if (d.getRoom() == null) {
								roomAllForSelectSet.add("ยังไม่ได้ระบุห้อง");
							}
						}
						roomAllForSelect.addAll(roomAllForSelectSet);

						for (int i = 0; i < roomAllForSelect.size(); i++) {
							if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
								Collections.swap(roomAllForSelect, 0, i);
							} else if (roomAllForSelect.get(i).equalsIgnoreCase("ยังไม่ได้ระบุห้อง")) {
								Collections.swap(roomAllForSelect, roomAllForSelect.size() - 1, i);
							}
						}
						mav.addObject("listdurableall", durableList);
						mav.addObject("roomBeanAll", roomAllForSelect);
						mav.addObject("roomSelectEdit", roomAllForEdit);
						mav.addObject("ownerBeanAll", ownerBeanAll);
					}
				} else {
					request.setCharacterEncoding("UTF-8");
					String durable_code = request.getParameter("durable_code");
					String durable_name = request.getParameter("durable_name");
					session.setAttribute("durable_code_search", durable_code);
					session.setAttribute("durable_name_search", durable_name);
					System.out.println(durable_code + " " + durable_name);
					mav.addObject("durable_code_old",durable_code);
					mav.addObject("durable_name_old",durable_name);
					if (room_id.equalsIgnoreCase("-")) {
						if (durable_code != "" && durable_name == "") {
							// durableList = durableRepo.getSearchDurable(durable_code, null, null);
							durableList = durableRepo.getSearchDurableByDurableCodeNoRoom(durable_code,
									staffDetail.getMajor().getID_Major());
							System.out.println("search by code");
						} else if (durable_name != "" && durable_code == "") {
							durableList = durableRepo.getSearchDurableByNameNoRoom(durable_name,
									staffDetail.getMajor().getID_Major());
							// durableList = durableRepo.getSearchDurable(null, durable_name, null);
							System.out.println("search by name");
						} else {
							durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());
							// durableList = durableRepo.getSearchDurable(durable_code, durable_name, null);
							System.out.println("search by code and name");
						}
						
					} else {
						if (room_id.equalsIgnoreCase("ยังไม่ได้ระบุห้อง")) {
							durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());
							for (Durable d : durableList) {
								if (d.getRoom() == null) {
									durableNoRoom.add(d);
								}
							}
							System.out.println("el");
							durableList.clear();
							durableList.addAll(durableNoRoom);
						} else {
							//durableList = durableRepo.getSearchDurable(durable_code, durable_name, room_id);
							if (durable_code != "" && durable_name == "") {
								// durableList = durableRepo.getSearchDurable(durable_code, null, room_id);
								durableList = durableRepo.getSearchDurableByDurableCodeAndRoom(durable_code, room_id);
								System.out.println("search by code");
							} else if (durable_name != "" && durable_code == "") {
								// durableList = durableRepo.getSearchDurable(null, durable_name, room_id);
								durableList = durableRepo.getSearchDurableByNameAndRoom(durable_name, room_id);
								System.out.println("search by name");

							} else if (durable_name == "" && durable_code == "") {
								// durableList = durableRepo.getSearchDurable(null, null, room_id);
								//durableList = durableRepo.getSearchDurable(durable_code, durable_name, room_id);
								durableList = durableRepo.getDurable_DetailByRoom(room_id);
								System.out.println("search by room_id");
							} else {
								durableList = durableRepo.getSearchDurable(durable_code, durable_name, room_id);
							}
						}
					}
					
					//save data for after edit
					
					
					// check durable room == null
					for (Durable d : durableList) {
						if (d.getRoom() == null) {
							roomAllForSelectSet.add("ยังไม่ได้ระบุห้อง");
						}
					}
					roomAllForSelect.addAll(roomAllForSelectSet);

					for (int i = 0; i < roomAllForSelect.size(); i++) {
						if (room_id.equalsIgnoreCase(roomAllForSelect.get(i))) {
							Collections.swap(roomAllForSelect, 0, i);
						}
					}

					if (durableList.size() == 0) {
						mav.addObject("listdurableall", null);
					} else {
						mav.addObject("listdurableall", durableList);
					}

					mav.addObject("roomBeanAll", roomAllForSelect);
					mav.addObject("roomSelectEdit", roomAllForEdit);
					mav.addObject("ownerBeanAll", ownerBeanAll);
					mav.addObject("search_code", durable_code);
					mav.addObject("durable_name", durable_name);
				
					session.setAttribute("durable_name", durable_name);
					session.setAttribute("search_code", durable_code);
				}
				mav.addObject("type", 0);
			}
			mav.addObject("major_old",major_id);
			mav.addObject("room_id_old",room_id);
			session.setAttribute("major_old", major_id);
			session.setAttribute("room_id_old", room_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView callListMajorAndRoom() {
		ModelAndView modelAndView = new ModelAndView("listqrcode");
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

		System.out.println(replaceAll);

		modelAndView.addObject("listMajor", listMajor);
		modelAndView.addObject("roomBeanAll", replaceAll);
		return modelAndView;
	}

	@Override
	public ModelAndView call_UpdateDurableDetail(ServletRequest request, HttpSession session) {
		ModelAndView mav = null;
		try {
			request.setCharacterEncoding("UTF-8");
			
			String durablecode = request.getParameter("durablecode");
			String numdurable = request.getParameter("numdurable");
			String namedurable = request.getParameter("namedurable");
			String brand = request.getParameter("brand");
			String pricedurable = request.getParameter("pricedurable");
			String datepicker = request.getParameter("datepicker");
			String detail = request.getParameter("detail");
			String room_id = request.getParameter("room_Id");
			String owner_id = request.getParameter("owner_id");
			String status = request.getParameter("status");
			String note = request.getParameter("note");
			String roomSelect = request.getParameter("roomSelect");
			String majorSelect = request.getParameter("majorSelect");

			int result = durableRepo.UpdateDurableData(namedurable, numdurable, brand, detail, pricedurable, status,
					owner_id, datepicker, "คืน", room_id, note, durablecode);

			if (result != 0) {
				if (majorSelect == null) {
					mav = callDurableList(null, roomSelect, session, request);
					mav.addObject("value", 1);
					mav.addObject("message", "แก้ไขรายละเอียดครุภัณฑ์เสร็จสิ้น");
				} else {
					mav = callDurableList(majorSelect, roomSelect, session, request);
					mav.addObject("value", 1);
					mav.addObject("message", "แก้ไขรายละเอียดครุภัณฑ์เสร็จสิ้น");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

//	@Override
//	public ModelAndView call_PrintQrCodeDurable(ServletRequest request, HttpSession session) {
//		ModelAndView mav = new ModelAndView("printqrcode");
//		try {
//			HashMap<String, String> piclist = new HashMap<String, String>();
//			String[] durablecode = request.getParameterValues("durablecode");
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
//			properties.load(inputStream);
//			String root = properties.getProperty("uriQRCode");
//			for (int i = 0; i < durablecode.length; i++) {
//
//				String[] namepath = durablecode[i].split("/");
//				String[] s = namepath[0].split("_");
//
//				String name = "";
//				for (int j = 0; j < s.length; j++) {
//					name += s[j];
//				}
//
//				String picture_name = name += ".PNG";
//				String d = s[0];
//
//				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//				hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
//
//				BitMatrix matrix;
//				try {
//					matrix = new MultiFormatWriter().encode(new String(d.getBytes("ISO-8859-1"), "ISO-8859-1"),
//							BarcodeFormat.QR_CODE, 400, 400, hints);
//
//					MatrixToImageWriter.writeToFile(matrix,
//							(root + "/" + name).substring((root + "/" + name).lastIndexOf('.') + 1),
//							new File(root + "/" + name));
//
//				} catch (WriterException e) {
//					e.printStackTrace();
//				}
//
//				piclist.put(durablecode[i], picture_name);
//
//			}
//			session.setAttribute("picpathlist", piclist);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return mav;
//	}

	@Override
	public ModelAndView call_PrintQrCodeDurable(ServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("printqrcode");
		try {
			String[] durablecode = request.getParameterValues("durablecode");

			HashMap<String, String> piclist = new HashMap<String, String>();
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
//			properties.load(inputStream);
//			String uriGenQrcode = properties.getProperty("uriGenQrcode");
			
			//new path
			String path = request.getServletContext().getRealPath("/")+"file/qrcode";
			System.out.println("path : "+path);
			
			//properties
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
			properties.load(inputStream);
			String uriGenQrcode = properties.getProperty("uriQRCode");
			
			System.out.println("p"+uriGenQrcode);
			
			//String uriGenQrcode = "/usr/share/apache-tomcat-9.0.0.M21/webapps/DurableWebservices/images/qrcode";
			for (int i = 0; i < durablecode.length; i++) {

				String durableCode = durablecode[i];
				String durableCodeReplace = durableCode.replaceAll("/", "_");
				String picture_name = durableCodeReplace += ".PNG";

				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

				BitMatrix matrix;
				try {
					matrix = new MultiFormatWriter().encode(new String(durableCodeReplace.getBytes("UTF-8"), "UTF-8"),
							BarcodeFormat.QR_CODE, 400, 400, hints);
					File myObj = new File(path + "/" + picture_name);
					
					if (myObj.createNewFile()) {
						System.out.println("Create qrcode : " + myObj.getName());
					} else {
						System.out.println("Qrcode already exists.");
					}

					MatrixToImageWriter.writeToStream(matrix, "PNG", new FileOutputStream(myObj));

				} catch (WriterException e) {
					e.printStackTrace();
				}
				piclist.put(durablecode[i], picture_name);
			}
			session.setAttribute("picpathlist", piclist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@Override
	public ModelAndView call_ImportFileDurable(ServletRequest request, HttpServletRequest requesth,
			MultipartFile[] file_excel) {
		ModelAndView mav = new ModelAndView();
		List<Durable> durableList = new ArrayList<>();
		List<Major> listAllMajor = majorRepo.getAllMajor();
		Major majorDetail = new Major();

		try {

			requesth.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			HttpSession session = requesth.getSession();
			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			int result_test = 0;
			int result = 0;
			for (MultipartFile file_excels : file_excel) {
				InputStream fileName = file_excels.getInputStream();
				try {

					XSSFWorkbook workbook = new XSSFWorkbook(fileName);

					XSSFSheet sheet = workbook.getSheetAt(0);

					Iterator<Row> rowIterator = sheet.iterator();

					Row rowTest = rowIterator.next();
					Iterator<Cell> cellIteratorTest = rowTest.cellIterator();
					Cell cellTest = cellIteratorTest.next();

					if (cellTest.getColumnIndex() == 0 && cellTest.getRowIndex() == 0) {

						if (ReadFileExcelType.readfile.cellType(cellTest) == null) {
							String message = " ไม่พบสาขาวิชาในไฟล์ที่ท่านอัพโหลด ";
							mav = new ModelAndView("importfiledurable");
							System.out.println("1");
							mav.addObject("value", 1);
							mav.addObject("message", message);
						} else {
							for (Major m : listAllMajor) {
								if (ReadFileExcelType.readfile.cellType(cellTest).contains(m.getMajor_Name())) {
									System.out.println(ReadFileExcelType.readfile.cellType(cellTest));
									majorDetail.setID_Major(m.getID_Major());
									majorDetail.setMajor_Name(m.getMajor_Name());
								}
							}

							if (staffDetail.getMajor().getID_Major() != majorDetail.getID_Major()) {
								String message = " ไม่พบสาขาวิชาในไฟล์ที่ท่านอัพโหลด  ";
								System.out.println(
										"2" + staffDetail.getMajor().getID_Major() + " " + majorDetail.getID_Major());
								mav = new ModelAndView("importfiledurable");
								mav.addObject("value", 1);
								mav.addObject("message", message);
							} else if (staffDetail.getMajor().getID_Major() == majorDetail.getID_Major()) {

								List<Room> listRoom = roomRepo.room_getAllRoomByMajor(majorDetail.getID_Major());

								while (rowIterator.hasNext()) {
									Row row = rowIterator.next();

									Durable durableBean = new Durable();
									Room roomBean = new Room();
									// For each row, iterate through each columns
									Iterator<Cell> cellIterator = row.cellIterator();

									while (cellIterator.hasNext()) {
										Cell cell = cellIterator.next();
										// if(cell.getColumnIndex() == 0 && cell.getRowIndex() >= 5) {
										// System.out.println(ReadFileExcelType.readfile.cellType(cell));
										// }
										if (cell.getColumnIndex() == 1 && cell.getRowIndex() >= 5
												&& ReadFileExcelType.readfile.cellType(cell) != null) {

											durableBean.setDurable_code(ReadFileExcelType.readfile.cellType(cell).trim());

										} else if (cell.getColumnIndex() == 2 && cell.getRowIndex() >= 5) {

											if (ReadFileExcelType.readfile.cellType(cell) == null) {
												durableBean.setDurable_number("-");
											} else {
												durableBean
														.setDurable_number(ReadFileExcelType.readfile.cellType(cell));
											}
										} else if (cell.getColumnIndex() == 3 && cell.getRowIndex() >= 5) {

											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												durableBean.setDurable_name(ReadFileExcelType.readfile.cellType(cell));
											} else {
												durableBean.setDurable_name("-");
											}
										} else if (cell.getColumnIndex() == 4 && cell.getRowIndex() >= 5) {
											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												if (ReadFileExcelType.readfile.cellType(cell).equalsIgnoreCase("")) {
													durableBean.setDurable_brandname("-");
												} else {
													durableBean.setDurable_brandname(
															ReadFileExcelType.readfile.cellType(cell));
												}
											} else {
												durableBean.setDurable_brandname("-");
											}
										} else if (cell.getColumnIndex() == 5 && cell.getRowIndex() >= 5) {
											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												durableBean.setDurable_model(ReadFileExcelType.readfile.cellType(cell));
											} else {
												durableBean.setDurable_model("-");
											}
										} else if (cell.getColumnIndex() == 6 && cell.getRowIndex() >= 5) {
											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												durableBean.setDurable_price(ReadFileExcelType.readfile.cellType(cell));
											} else {
												durableBean.setDurable_price("-");
											}
										} else if (cell.getColumnIndex() == 7 && cell.getRowIndex() >= 5) {
											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												durableBean.setDurable_entrancedate(
														ReadFileExcelType.readfile.cellType(cell));

											} else {
												durableBean.setDurable_entrancedate("-");
											}

										} else if (cell.getColumnIndex() == 8 && cell.getRowIndex() >= 5) {

											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												String room_number = ReadFileExcelType.readfile.cellType(cell)
														.replaceAll("'", "");
												for (Room r : listRoom) {
													if (ReadFileExcelType.readfile.cellType(cell)
															.equalsIgnoreCase(r.getRoom_number())) {
														roomBean.setBuild(r.getBuild());
														roomBean.setFloor(r.getFloor());
														roomBean.setRoom_name(r.getRoom_name());
														roomBean.setRoom_number(r.getRoom_number());
													}
												}
												if (roomBean.getRoom_number() != null) {
													durableBean.setRoom(roomBean);
												} else {
													Major majorRoom = new Major();
													majorRoom.setID_Major(staffDetail.getMajor().getID_Major());
													majorRoom.setMajor_Name(staffDetail.getMajor().getMajor_Name());
													roomBean.setBuild("-");
													roomBean.setFloor("-");
													roomBean.setRoom_name("-");
													roomBean.setMajor(majorRoom);
													roomBean.setRoom_number(room_number);
//													int resultAddRoom = roomRepo.add_RoomDetail(
//															roomBean.getRoom_number(), roomBean.getBuild(),
//															roomBean.getRoom_name(), roomBean.getFloor(),
//															majorDetail.getID_Major());
													try {
														roomRepo.save(roomBean);
														durableBean.setRoom(roomBean);
														listRoom.clear();
														listRoom = roomRepo
																.room_getAllRoomByMajor(majorDetail.getID_Major());
													} catch (Exception e) {
														e.printStackTrace();
													}
//													if (resultAddRoom == 1) {
//														durableBean.setRoom(roomBean);
//														listRoom.clear();
//														listRoom = roomRepo
//																.room_getAllRoomByMajor(majorDetail.getID_Major());
//													}
													// System.out.println(dura);
												}
											} else {
												Room room_null = new Room();
												room_null.setRoom_number("-");
												room_null.setRoom_name("-");
												durableBean.setRoom(room_null);
											}

										} else if (cell.getColumnIndex() == 9 && cell.getRowIndex() >= 5) {

											if (ReadFileExcelType.readfile.cellType(cell) != null) {
												durableBean.setResponsible_person(
														ReadFileExcelType.readfile.cellType(cell));
											} else {
												durableBean.setResponsible_person("-");
											}

										} else if (cell.getColumnIndex() == 11 && cell.getRowIndex() >= 5
												&& ReadFileExcelType.readfile.cellType(cell) != null) {

											durableBean.setDurable_statusnow(ReadFileExcelType.readfile.cellType(cell));
										} else if (cell.getColumnIndex() == 12 && cell.getRowIndex() >= 5) {
											if (ReadFileExcelType.readfile.cellType(cell) == null) {
												durableBean.setNote("-");
											} else {
												durableBean.setNote(ReadFileExcelType.readfile.cellType(cell));
											}
										}

										durableBean.setMajor(staffDetail.getMajor());

									}
									if (durableBean.getDurable_code() != null) {
										durableList.add(durableBean);
									}

								}

								if (durableList.size() == 0) {
									String message = "ไม่สามารถเพิ่มข้อมูลได้เนื่องจากไม่มีข้อมูลครุภัณฑ์ในไฟล์ที่ท่านเลือก ";
									mav = new ModelAndView("importfiledurable");
									session.setAttribute("value", 1);
									session.setAttribute("message", message);

								} else {

									int year = Calendar.getInstance().get(Calendar.YEAR);
									int month = Calendar.getInstance().get(Calendar.MONTH);

									if (month + 1 == 10) {
										year = year + 543;
										year += 1;
									} else {
										year += 543;
									}

									List<Durable> durableRecodeFail = new ArrayList<Durable>();
									for (Durable durableLists : durableList) {
										try {
											if (durableLists.getRoom() == null) {
												result_test = durableRepo.recordDurable(durableLists.getDurable_code(),
														"คืน", durableLists.getDurable_brandname(),
														durableLists.getDurable_entrancedate(), "-.png",
														durableLists.getDurable_model(), durableLists.getDurable_name(),
														durableLists.getDurable_number(),
														durableLists.getDurable_price(),
														durableLists.getDurable_statusnow(),
														durableLists.getResponsible_person(), durableLists.getNote(),
														staffDetail.getMajor().getID_Major(), null);
//												durableLists.setDurable_Borrow_Status("คืน");
//												durableLists.setDurable_image("-");
//												durableLists.setRoom(null);
//												durableRepo.save(durableLists);
												durableControllRepo.insert_durableControll(0, "-", "-", "-", "-", "-",
														durableLists.getDurable_code(), 1, year + "", 0.0);
												// durableControllRepo.insert_durableControll_with_out_company(durableLists.getDurable_code(),
												// year+"");
											} else {
												result_test = durableRepo.recordDurable(durableLists.getDurable_code(),
														"คืน", durableLists.getDurable_brandname(),
														durableLists.getDurable_entrancedate(), "-.png",
														durableLists.getDurable_model(), durableLists.getDurable_name(),
														durableLists.getDurable_number(),
														durableLists.getDurable_price(),
														durableLists.getDurable_statusnow(),
														durableLists.getResponsible_person(), durableLists.getNote(),
														staffDetail.getMajor().getID_Major(),
														durableLists.getRoom().getRoom_number());
//												durableLists.setDurable_Borrow_Status("คืน");
//												durableLists.setDurable_image("-");
//												durableRepo.save(durableLists);
												durableControllRepo.insert_durableControll(0, "-", "-", "-", "-", "-",
														durableLists.getDurable_code(), 1, year + "", 0.0);
												// durableControllRepo.insert_durableControll_with_out_company(durableLists.getDurable_code(),
												// year+"");
											}
										} catch (Exception e) {
											durableRecodeFail.add(durableLists);
											result++;
										}
									}
									// System.out.println(result_test);

									if (result == 0) {
										String message = "เพิ่มข้อมูลสำเร็จ";
										mav = new ModelAndView("home");
										mav.addObject("value", 1);
										mav.addObject("message", message);
									} else {
										if (durableList.size() == result) {
											String message = "ข้อมูลครุภัณฑ์ในไฟล์ทั้งหมดมีอยู่แล้ว";
											mav = new ModelAndView("home");
											mav.addObject("value", 1);
											mav.addObject("message", message);
										} else {
											String message = "ไม่สามารถเพิ่มข้อมูลครุภัณฑ์ : ";

											for (int i = 0; i < durableRecodeFail.size(); i++) {
												if (i == 0) {
													message = message + durableRecodeFail.get(i).getDurable_code();
												} else if (i > 0 && i <= durableRecodeFail.size()) {
													message = message + ","
															+ durableRecodeFail.get(i).getDurable_code();
												}
											}

											mav = new ModelAndView("home");
											mav.addObject("value", 1);
											mav.addObject("message", message);
										}

									}
								}
							}
						}

					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public void call_ExportFileExcelDurable(ServletRequest request, HttpServletResponse response, HttpSession session) {
		List<VerifyDurable> durableProfileBeans = (List<VerifyDurable>) session.getAttribute("exportdurableprofiles");
		List<Major> listMajor = majorRepo.getAllMajor();
		String majorName = null;

		for (Major m : listMajor) {
			if (m.getID_Major() == durableProfileBeans.get(0).getPk().getDurable().getMajor().getID_Major()) {
				majorName = m.getMajor_Name();
			}
		}

		try {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DurableReports.xlsx");
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			File file = ResourceUtils.getFile("classpath:reportDurableFileExcel/3.xlsx");

			InputStream is = new FileInputStream(file);

			Workbook wb = WorkbookFactory.create(is);
			Sheet s = wb.getSheetAt(0);

			Row row0 = s.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue("รายงานตรวจสอบครุภัณฑ์ของ" + majorName);

			CellStyle cellstyle = cell0.getCellStyle();
			cellstyle.setAlignment(CellStyle.ALIGN_CENTER);

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Row row02 = s.createRow(2);
			Cell cell02 = row02.createCell(0);
			cell02.setCellValue("ณ วันที่ " + ((DateFormat) formatter).format(new Date()));

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
				cell8.setCellValue(durableProfileBeans.get(j).getPk().getDurable().getRoom().getRoom_number());

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
			s.getPrintSetup().setLandscape(true);
			s.getPrintSetup().setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE);
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
