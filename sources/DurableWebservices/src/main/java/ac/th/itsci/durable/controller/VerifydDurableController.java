package ac.th.itsci.durable.controller;

import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import ac.th.itsci.durable.entity.*;

import ac.th.itsci.durable.repo.*;

@RestController
@Controller
@RequestMapping(path = "/verify")
public class VerifydDurableController {

//	@Autowired
//	DurableRepository durableRepo;
//
//	@Autowired
//	VerifyDurableRepository verifyDurableRepo;
//
//	@GetMapping(value = "/do_loadListDurableByYears")
//	public ModelAndView do_loadVerifyPage(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("listDurableByYears");
//		List<VerifyDurable> not_verify_durable_by_year = new ArrayList<>();
//		
//		try {
//			
//			int year = Calendar.getInstance().get(Calendar.YEAR);
//			year += 543;
//			System.out.println(year);
//			
//			not_verify_durable_by_year = verifyDurableRepo.getNotVerifyDurableByYear(year+"");
//			
//			System.out.println(not_verify_durable_by_year.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return mav;
//	}

//	@Autowired
//	private FileStorageService fileStorageService;
//
//	@Autowired
//	public JavaMailSender javamailsender;
//
//	@Autowired
//	private VerifyDurableRepository mVerifyDurableRepository;
//
//	@Autowired
//	private MajorRepository mMajorRepository;
//
//	@Autowired
//	private DurableRepository mDurableRepository;
//
//	@Autowired
//	private StaffRepository mStaffRepository;
//	
//	@Autowired
//	private EncodeService encodeservice;

//	Properties properties = new Properties();

//	@PostMapping(path = "/verifydurable")
//	public @ResponseBody ResponseObj verifydurable(@RequestBody VerifyDurable v)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		System.out.println(v.getDurable_status());
//
//		VerifyDurable verify = new VerifyDurable();
//
//		Staff staff = new Staff(URLDecoder.decode(v.getStaff().getId_card(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getStaff_name(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getStaff_lastname(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getStaff_status(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getEmail(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getBrithday(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getPhone_number(), "UTF-8"),
//				URLDecoder.decode(v.getStaff().getImage_staff(), "UTF-8"));
//
//		Durable durable = new Durable(URLDecoder.decode(v.getDurable().getDurable_code(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_name(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_number(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_brandname(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_model(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_price(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_statusnow(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getResponsible_person(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_image(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_Borrow_Status(), "UTF-8"),
//				URLDecoder.decode(v.getDurable().getDurable_entrancedate(), "UTF-8"));
//			
//
//		System.out.println(durable.getDurable_code());
//
//		Durable durables = mDurableRepository.detaildurablebydurablecodes(durable.getDurable_code());
//		Staff staffs = mStaffRepository.StaffDetailByIDcard(staff.getId_card());
//
//		mDurableRepository.UpdateDurableStatus(URLDecoder.decode(v.getDurable_status(), "UTF-8"),
//				durable.getDurable_code());
//
//		verify.setDurable(durables);
//		verify.setStaff(staffs);
//		verify.setNote(URLDecoder.decode(v.getNote(), "UTF-8"));
//		verify.setSave_date(URLDecoder.decode(v.getSave_date(), "UTF-8"));
//		verify.setYears(URLDecoder.decode(v.getYears(), "UTF-8"));
//		verify.setDurable_status(URLDecoder.decode(v.getDurable_status(), "UTF-8"));
//
//		mVerifyDurableRepository.save(verify);
//
//		return new ResponseObj(200, "finish");
//	}
//
//	@PostMapping(path = "/sentemailreport")
//	public @ResponseBody ResponseObj SentExcelReportToEmail(@RequestBody String DataConditions,
//			HttpServletRequest request, HttpServletResponse response)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException, IOException {
//
//		ResponseObj Respon = null;
//
//		String data = DataConditions.replaceAll("\"", "");
//		String[] dataQ = data.split(",");
//
//		Major m = mMajorRepository.getMajorByName(URLDecoder.decode(dataQ[1], "UTF-8"));
//
//		List<VerifyDurable> verifyList = null;
//
//		if (dataQ[2].equalsIgnoreCase("0")) {
//			verifyList = mVerifyDurableRepository.verify_getAlldata(URLDecoder.decode(dataQ[0], "UTF-8"),
//					m.getID_Major());
//		} else {
//			verifyList = mVerifyDurableRepository.verify_getAllbyCondition(URLDecoder.decode(dataQ[0], "UTF-8"),
//					URLDecoder.decode(dataQ[2], "UTF-8"), m.getID_Major());
//		}
//
//		for (int i = 0; i < verifyList.size(); i++) {
//			System.out.println(verifyList.get(i).getDurable().getDurable_name());
//		}
//
//		System.out.println(dataQ[3]);
//
//		try {
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
//			properties.load(inputStream);
//			String urlFileReport = properties.getProperty("uriFileReport");
//
//			FileInputStream file = new FileInputStream(new File(urlFileReport + "/reportdurables.xlsx"));
//
//			XSSFWorkbook workbook = new XSSFWorkbook(file);
//			XSSFSheet sheet = workbook.getSheetAt(0);
//
//			Row r = sheet.createRow(0);
//			Cell cells = r.createCell(0);
//			// cells.setCellValue("การสำรวจครุภัณฑ์ทั่วไปของสาขา
//			// "+verifyList.get(0).getDurable().getRoom().getMajor().getMajor_Name());
//
//			Calendar c = Calendar.getInstance();
//			int years = c.get(Calendar.YEAR);
//			int month = c.get(Calendar.MONTH);
//			int dayofmonth = c.get(Calendar.DAY_OF_MONTH);
//
//			Row r2 = sheet.createRow(2);
//			Cell cellss = r2.createCell(0);
//			cellss.setCellValue("ณ วันที่ " + dayofmonth + "/" + month + "/" + years);
//
//			// Update the value of cell
//			for (int j = 0; j < verifyList.size(); j++) {
//
//				Row row2 = sheet.createRow(j + 5);
//				Cell cell = row2.createCell(0);
//				cell.setCellValue(j + 1);
//
//				// durablecode
//				Cell cell1 = row2.createCell(1);
//				cell1.setCellValue(verifyList.get(j).getDurable().getDurable_code());
//
//				// number
//				Cell cell2 = row2.createCell(2);
//				cell2.setCellValue(verifyList.get(j).getDurable().getDurable_number());
//
//				// durablename
//				Cell cell3 = row2.createCell(3);
//				cell3.setCellValue(verifyList.get(j).getDurable().getDurable_name());
//
//				// brandname
//				Cell cell4 = row2.createCell(4);
//				cell4.setCellValue(verifyList.get(j).getDurable().getDurable_brandname());
//
//				// modelordetail
//				Cell cell5 = row2.createCell(5);
//				cell5.setCellValue(verifyList.get(j).getDurable().getDurable_model());
//
//				// price column7
//				Cell cell6 = row2.createCell(6);
//				cell6.setCellValue(verifyList.get(j).getDurable().getDurable_price());
//
//				// entrancedate column8
//				Cell cell7 = row2.createCell(7);
//				cell7.setCellValue(verifyList.get(j).getDurable().getDurable_entrancedate());
//
//				// roomnumber column9
//				Cell cell8 = row2.createCell(8);
//				cell8.setCellValue(verifyList.get(j).getDurable().getRoom().getRoom_number());
//
//				// ownername column10
//				Cell cell9 = row2.createCell(9);
//				cell9.setCellValue(verifyList.get(j).getDurable().getResponsible_person());
//
//				// staffname column11
//				Cell cell10 = row2.createCell(10);
//				cell10.setCellValue(verifyList.get(j).getStaff().getStaff_name());
//
//				// status column12
//				Cell cell11 = row2.createCell(11);
//				cell11.setCellValue(verifyList.get(j).getDurable_status());
//
//				// note column13
//				Cell cell12 = row2.createCell(12);
//				cell12.setCellValue(verifyList.get(j).getNote());
//			}
//
//			file.close();
//			FileOutputStream outFile = new FileOutputStream(new File(urlFileReport + "/reportdurables.xlsx"));
//			workbook.write(outFile);
//			outFile.close();
//			outFile.flush();
//
//			Respon = new ResponseObj(200, "/verify/report/reportdurables.xlsx");
//			System.out.println("create success");
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return Respon;
//	}
//
//	@GetMapping("/report/{fileName:.+}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
//			throws IOException {
//
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
//		properties.load(inputStream);
//		String urlFileReport = properties.getProperty("uriFileReport");
//
//		Path fileStorageLocations = Paths.get(urlFileReport).toAbsolutePath().resolve(fileName).normalize();
//
//		Resource resource = new UrlResource(fileStorageLocations.toUri());
//
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//		}
//
//		if (contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}
//
//	@PostMapping(path = "/verifycheck")
//	public @ResponseBody ResponseObj verifycheck(@RequestBody String Durablecode)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		String replacDurableCode = Durablecode.replaceAll("\"", "");
//		String durablecode = URLDecoder.decode(replacDurableCode, "UTF-8");
//
//		List<VerifyDurable> verifys = mVerifyDurableRepository.verifyCheck(URLDecoder.decode(durablecode, "UTF-8"));
//
//		if (verifys.size() == 0) {
//			return new ResponseObj(500, "dont veryfy to year");
//		} else {
//
//			List<VerifyDurable> verify = new ArrayList<>();
//
//			for (int i = 0; i < verifys.size(); i++) {
//				VerifyDurable verifydurable = new VerifyDurable();
//
//				verifydurable.setId(verifys.get(i).getId());
//				verifydurable.setYears(URLEncoder.encode(verifys.get(i).getYears(), "UTF-8"));
//				verifydurable.setSave_date(URLEncoder.encode(verifys.get(i).getSave_date(), "UTF-8"));
//				verifydurable.setDurable_status(URLEncoder.encode(verifys.get(i).getDurable_status(), "UTF-8"));
//				verifydurable.setNote(URLEncoder.encode(verifys.get(i).getNote(), "UTF-8"));
//
//				verifydurable.setStaff(new Staff(verifys.get(i).getStaff().getId_card(),
//						URLEncoder.encode(verifys.get(i).getStaff().getStaff_name(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getStaff_lastname(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getStaff_status(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getEmail(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getBrithday(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getPhone_number(), "UTF-8"),
//						URLEncoder.encode(verifys.get(i).getStaff().getImage_staff(), "UTF-8")));
//
//				verifydurable.getStaff().setMajor(verifys.get(i).getStaff().getMajor());
//				verifydurable.getStaff().setLogin(verifys.get(i).getStaff().getLogin());
//
//				if (verifys.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_Borrow_Status("-");
//					;
//				}
//				if (verifys.get(i).getDurable().getDurable_name().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_name().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_name("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_number().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_number().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_number("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_brandname().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_brandname().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_brandname("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_model().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_model().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_model("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_price().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_price().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_price("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_statusnow().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_statusnow().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_statusnow("-");
//				}
//				if (verifys.get(i).getDurable().getResponsible_person().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getResponsible_person().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setResponsible_person("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_image().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_image().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_image("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_Borrow_Status("-");
//				}
//				if (verifys.get(i).getDurable().getDurable_entrancedate().equalsIgnoreCase(null)
//						|| verifys.get(i).getDurable().getDurable_entrancedate().equalsIgnoreCase("")) {
//					verifys.get(i).getDurable().setDurable_entrancedate("-");
//				}
//
//				verifydurable.setDurable(
//						new Durable(URLEncoder.encode(verifys.get(i).getDurable().getDurable_code(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_name(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_number(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_brandname(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_model(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_price(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_statusnow(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getResponsible_person(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_image(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_Borrow_Status(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getDurable_entrancedate(), "UTF-8")));
//
//				verifydurable.getDurable()
//						.setRoom(new Room(
//								URLEncoder.encode(verifys.get(i).getDurable().getRoom().getRoom_number(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getRoom().getRoom_name(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getRoom().getBuild(), "UTF-8"),
//								URLEncoder.encode(verifys.get(i).getDurable().getRoom().getFloor(), "UTF-8")));
//
//				verifydurable.getDurable().getRoom().setMajor(new Major(
//						verifys.get(i).getDurable().getRoom().getMajor().getID_Major(),
//						URLEncoder.encode(verifys.get(i).getDurable().getRoom().getMajor().getMajor_Name(), "UTF-8")));
//
//				verify.add(verifydurable);
//
//			}
//			return new ResponseObj(200, verify);
//		}
//	}
//
//	@PostMapping(path = "/listverifydurable")
//	public @ResponseBody ResponseObj getAllDurableByYear(@RequestBody String year)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		String replaceYear = year.replaceAll("\"", "");
//		String[] data = replaceYear.split(",");
//		// URLDecoder.decode(v.getStaff().getId_card(),"UTF-8");
//
//		Major m = mMajorRepository.getMajorByName(URLDecoder.decode(data[1], "UTF-8"));
//
//		List<VerifyDurable> verifyList = null;
//
//		if (data[2].equalsIgnoreCase("0") || "0".equalsIgnoreCase(data[2])) {
//
//			verifyList = mVerifyDurableRepository.verify_getAlldata(URLDecoder.decode(data[0], "UTF-8"),
//					m.getID_Major());
//		} else {
//			verifyList = mVerifyDurableRepository.verify_getAllbyCondition(URLDecoder.decode(data[0], "UTF-8"),
//					URLDecoder.decode(data[2], "UTF-8"), m.getID_Major());
//		}
//
//		if (verifyList.size() == 0 || verifyList == null) {
//			return new ResponseObj(500, "Not Found");
//		} else {
//			for (VerifyDurable v : verifyList) {
//				System.out.println("savedate :" + v.getSave_date());
//				System.out.println("staffID :" + v.getStaff().getId_card());
//				System.out.println("Room :" + v.getDurable().getRoom().getRoom_number() + " : "
//						+ v.getDurable().getRoom().getRoom_name());
//				System.out.println("durablecode :" + v.getDurable().getDurable_code());
////				System.out.println("major :"+v.getDurable().getRoom().getMajor().getMajor_Name());
//				System.out.println("major from staff" + v.getStaff().getMajor().getID_Major());
//				System.out.println("username staff" + v.getStaff().getLogin().getUsername());
//			}
//
//			List<VerifyDurable> verifyDurableTojson = new ArrayList<VerifyDurable>();
//
//			for (int i = 0; i < verifyList.size(); i++) {
//				VerifyDurable v = new VerifyDurable();
//
//				v.setId(verifyList.get(i).getId());
//				v.setYears(URLEncoder.encode(verifyList.get(i).getYears(), "UTF-8"));
//				v.setSave_date(URLEncoder.encode(verifyList.get(i).getSave_date(), "UTF-8"));
//				v.setDurable_status(URLEncoder.encode(verifyList.get(i).getDurable_status(), "UTF-8"));
//				v.setNote(URLEncoder.encode(verifyList.get(i).getNote(), "UTF-8"));
//
//				v.setStaff(new Staff(verifyList.get(i).getStaff().getId_card(),
//						URLEncoder.encode(verifyList.get(i).getStaff().getStaff_name(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getStaff_lastname(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getStaff_status(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getEmail(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getBrithday(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getPhone_number(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getStaff().getImage_staff(), "UTF-8")));
//
//				v.getStaff().setMajor(verifyList.get(i).getStaff().getMajor());
//				v.getStaff().setLogin(verifyList.get(i).getStaff().getLogin());
//
//				if (verifyList.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_Borrow_Status("-");
//					;
//				}
//				if (verifyList.get(i).getDurable().getDurable_name().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_name().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_name("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_number().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_number().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_number("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_brandname().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_brandname().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_brandname("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_model().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_model().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_model("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_price().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_price().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_price("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_statusnow().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_statusnow().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_statusnow("-");
//				}
//				if (verifyList.get(i).getDurable().getResponsible_person().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getResponsible_person().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setResponsible_person("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_image().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_image().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_image("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_Borrow_Status().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_Borrow_Status("-");
//				}
//				if (verifyList.get(i).getDurable().getDurable_entrancedate().equalsIgnoreCase(null)
//						|| verifyList.get(i).getDurable().getDurable_entrancedate().equalsIgnoreCase("")) {
//					verifyList.get(i).getDurable().setDurable_entrancedate("-");
//				}
//
//				v.setDurable(new Durable(URLEncoder.encode(verifyList.get(i).getDurable().getDurable_code(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_name(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_number(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_brandname(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_model(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_price(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_statusnow(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getResponsible_person(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_image(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_Borrow_Status(), "UTF-8"),
//						URLEncoder.encode(verifyList.get(i).getDurable().getDurable_entrancedate(), "UTF-8")));
//
//				v.getDurable()
//						.setRoom(new Room(
//								URLEncoder.encode(verifyList.get(i).getDurable().getRoom().getRoom_number(), "UTF-8"),
//								URLEncoder.encode(verifyList.get(i).getDurable().getRoom().getRoom_name(), "UTF-8"),
//								URLEncoder.encode(verifyList.get(i).getDurable().getRoom().getBuild(), "UTF-8"),
//								URLEncoder.encode(verifyList.get(i).getDurable().getRoom().getFloor(), "UTF-8")));
//
//				v.getDurable().getRoom().setMajor(
//						new Major(verifyList.get(i).getDurable().getRoom().getMajor().getID_Major(), URLEncoder
//								.encode(verifyList.get(i).getDurable().getRoom().getMajor().getMajor_Name(), "UTF-8")));
//
//				verifyDurableTojson.add(v);
//			}
//			return new ResponseObj(200, verifyDurableTojson);
//		}
//
//	}
//
//	@PostMapping(path = "/listverifydurablenotverify")
//	public @ResponseBody ResponseObj getAlldatanotverufydurable(@RequestBody int idmajor)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		Calendar calendar = Calendar.getInstance();
//
//		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//		String date = formatter.format(calendar.getTime());
//
//		String[] datadate = date.split("-");
//		String years = datadate[2];
//
//		System.out.println(years);
//
//		List<VerifyDurable> listverif = mVerifyDurableRepository.verify_getAlldata(years, idmajor);
//		List<Durable> durable = mDurableRepository.getDurable_DetailByIDmajor(idmajor);
//		List<Durable> durableListEncoder ;
//
//		if (listverif.size() == 0) {
//			
//			durableListEncoder = encodeservice.EncodeDurable(durable);
//			
//			return new ResponseObj(200, durableListEncoder);
//		} else {
//			
//			for (int i = 0; i < durable.size(); i++) {
//				for (int j = 0; j < listverif.size(); j++) {
//					if (durable.get(i).getDurable_code()
//							.equalsIgnoreCase(listverif.get(j).getDurable().getDurable_code())) {
//						durable.remove(i);
//					}
//				}
//			}
//			
//			durableListEncoder = encodeservice.EncodeDurable(durable);
//
//			return new ResponseObj(200, durableListEncoder);
//		}
//
//	}

}
