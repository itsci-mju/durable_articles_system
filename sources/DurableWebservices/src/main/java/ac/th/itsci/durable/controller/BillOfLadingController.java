package ac.th.itsci.durable.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.BillOfLading;
import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.PersonnelAssign;
import ac.th.itsci.durable.entity.PurchaseOrderDocument;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.BillOfLadingRepository;
import ac.th.itsci.durable.repo.DocumentRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.PersonnelAssignRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;
import ac.th.itsci.durable.repo.PositionRepository;
import ac.th.itsci.durable.repo.PurchaseOrderDocumentRepository;
import ac.th.itsci.durable.repo.ReceiveOrderDocumentRepository;
import ac.th.itsci.durable.util.ConnectionDB;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;

@Controller
public class BillOfLadingController {

	@Autowired
	PositionRepository positionRepo;

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	BillOfLadingRepository billOfLadingRepo;

	@Autowired
	DocumentRepository documentRepo;

	@Autowired
	PurchaseOrderDocumentRepository podRepo;

	@Autowired
	ReceiveOrderDocumentRepository rodRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	PersonnelAssignRepository personnelAssignRepo;

	@GetMapping(value = "/do_loadAddBillOfLading")
	public ModelAndView do_loadAddBillOfLading(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addBillOfLadingPage");
		Document document = new Document();
		try {
			Staff staffSession = (Staff) session.getAttribute("staffSession");

			String doc_id = request.getParameter("doc_id");

			document.setDoc_id(doc_id);

			PurchaseOrderDocument pod = podRepo.get_purchaseOrderDocument(document.getDoc_id());

			List<PersonnelAssign> doc_4_1 = personnelAssignRepo.get_personnelByAssignType("doc_4_1");
			List<PersonnelAssign> doc_4_2 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_2",
					staffSession.getMajor().getID_Major());
			List<PersonnelAssign> doc_4_3 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_3",
					staffSession.getMajor().getID_Major());
			List<PersonnelAssign> doc_4_4 = personnelAssignRepo.get_personnelByAssignType("doc_4_4");
			List<PersonnelAssign> doc_4_5 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_5",
					staffSession.getMajor().getID_Major());

			mav.addObject("purchaseOrderDocument", pod);
			mav.addObject("doc_4_1", doc_4_1);
			mav.addObject("doc_4_2", doc_4_2);
			mav.addObject("doc_4_3", doc_4_3);
			mav.addObject("doc_4_4", doc_4_4);
			mav.addObject("doc_4_5", doc_4_5);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addBillOfLading")
	public ModelAndView do_addBillOfLading(ServletRequest request, HttpSession session, Model md,
			ServletResponse response) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		BillOfLading billOfLading = new BillOfLading();
		int result = 0;
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staff = (Staff) session.getAttribute("staffSession");

			String billOfLading_id = request.getParameter("billOfLading_id");
			String doc_id = request.getParameter("doc_id");
			String receive_old = request.getParameter("doc_4_3");
			String doc_4_1 = request.getParameter("doc_4_1");
			String doc_4_2 = request.getParameter("doc_4_2");
			String doc_4_4 = request.getParameter("doc_4_4");
			String doc_4_5 = request.getParameter("doc_4_5");

			billOfLading = new BillOfLading(billOfLading_id, "", receive_old);
			billOfLading.setDoc_id(doc_id);
			billOfLading.setBill_of_lading_1(doc_4_1);
			billOfLading.setBill_of_lading_2(doc_4_2);
			billOfLading.setBill_of_lading_4(doc_4_4);
			billOfLading.setBill_of_lading_5(doc_4_5);

			result = billOfLadingRepo.add_billOfLading(billOfLading.getBillOfLading_id(),
					billOfLading.getReceive_person(), billOfLading.getRepresentative(), billOfLading.getDoc_id(),
					billOfLading.getBill_of_lading_1(), billOfLading.getBill_of_lading_2(),
					billOfLading.getBill_of_lading_4(), billOfLading.getBill_of_lading_5());


		} catch (Exception e) {
			e.printStackTrace();
		}
		int result_add_check = 0;
		if (result != -1) {
			message = "บันทึกข้อมูลใบเบิกพัสดุสําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลใบเบิกพัสดุได้ !";
		}

		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "4");
		mav.addObject("doc_id", billOfLading.getDoc_id());

		return mav;
	}
	
	@GetMapping(value="do_loadEditBillOflading")
	public ModelAndView do_loadEditBillOflading(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editBillOfLadingPage");
		BillOfLading bill_of_lading = new BillOfLading();
		
		try {
			Staff staffSession = (Staff) session.getAttribute("staffSession");
			String doc_id = request.getParameter("doc_id");
			
			bill_of_lading = billOfLadingRepo.get_billOfLading(doc_id);
			
			List<PersonnelAssign> doc_4_1 = personnelAssignRepo.get_personnelByAssignType("doc_4_1");
			List<PersonnelAssign> doc_4_2 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_2",
					staffSession.getMajor().getID_Major());
			List<PersonnelAssign> doc_4_3 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_3",
					staffSession.getMajor().getID_Major());
			List<PersonnelAssign> doc_4_4 = personnelAssignRepo.get_personnelByAssignType("doc_4_4");
			List<PersonnelAssign> doc_4_5 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_4_5",
					staffSession.getMajor().getID_Major());
			
			mav.addObject("doc_4_1", doc_4_1);
			mav.addObject("doc_4_2", doc_4_2);
			mav.addObject("doc_4_3", doc_4_3);
			mav.addObject("doc_4_4", doc_4_4);
			mav.addObject("doc_4_5", doc_4_5);
			mav.addObject("bill_of_lading", bill_of_lading);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@PostMapping(value = "/do_editBillOfLading")
	public ModelAndView do_editBillOfLading(ServletRequest request, HttpSession session, Model md,
			ServletResponse response) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		BillOfLading billOfLading = new BillOfLading();
		int result = 0;
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staff = (Staff) session.getAttribute("staffSession");

			String billOfLading_id = request.getParameter("billOfLading_id");
			String doc_id = request.getParameter("doc_id");
			String receive_old = request.getParameter("doc_4_3");
			String doc_4_1 = request.getParameter("doc_4_1");
			String doc_4_2 = request.getParameter("doc_4_2");
			String doc_4_4 = request.getParameter("doc_4_4");
			String doc_4_5 = request.getParameter("doc_4_5");

			billOfLading = new BillOfLading(billOfLading_id, "", receive_old);
			billOfLading.setDoc_id(doc_id);
			billOfLading.setBill_of_lading_1(doc_4_1);
			billOfLading.setBill_of_lading_2(doc_4_2);
			billOfLading.setBill_of_lading_4(doc_4_4);
			billOfLading.setBill_of_lading_5(doc_4_5);

			result = billOfLadingRepo.edit_billOfLading(billOfLading.getBillOfLading_id(),
					billOfLading.getReceive_person(), billOfLading.getRepresentative(), billOfLading.getDoc_id(),
					billOfLading.getBill_of_lading_1(), billOfLading.getBill_of_lading_2(),
					billOfLading.getBill_of_lading_4(), billOfLading.getBill_of_lading_5());


		} catch (Exception e) {
			e.printStackTrace();
		}
		int result_add_check = 0;
		if (result != -1) {
			message = "แก้ไขข้อมูลใบเบิกพัสดุสําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลใบเบิกพัสดุได้ !";
		}

		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "4");
		mav.addObject("doc_id", billOfLading.getDoc_id());

		return mav;
	}

	public ModelAndView do_loadListDocumentPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");
			List<Document> listDocument = null;
			List<Major> listMajor = majorRepo.getAllMajor();
			List<String> document_creation = new ArrayList<>();

			if (staff.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				listDocument = documentRepo.get_allDocument();
			} else {
				listDocument = documentRepo.get_DocumentByDepartName(staff.getMajor().getMajor_Name());
			}

			List<String> all_date = formatDate(listDocument);

			mav.addObject("date", all_date);
			mav.addObject("documents", listDocument);
			mav.addObject("document_creation", document_creation);
			mav.addObject("major", listMajor);

		} catch (Exception e) {

		}

		return mav;
	}

	public List<String> formatDate(List<Document> listDocument) {
		List<String> all_date = new ArrayList<>();
		String DATE_FORMAT = "dd MMMM yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

		for (Document d : listDocument) {
			all_date.add("" + sdf.format(d.getDoc_date()));
		}

		return all_date;
	}

//	@GetMapping(value = "/do_getBillOfLadingDocument")
//	public ModelAndView do_getBillOfLadingDocument(ServletRequest request, HttpSession session, Model md, String id) {
//		ModelAndView mav = new ModelAndView("viewBillOfLading");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String doc_id = request.getParameter("doc_id");
//
//			if (id != null) {
//				doc_id = id;
//			}
//			BillOfLading billOfLading = billOfLadingRepo.get_billOfLading(doc_id);
//
//			String requisition_person_id = podRepo.get_requisition_person(doc_id);
//			String faculty_dean = podRepo.get_faculty_dean(doc_id);
//
//			Personnel purchase_person = personnelRepo.get_PersonnelByPersonnelId(requisition_person_id);
//			Personnel dean = personnelRepo.get_PersonnelByPersonnelId(faculty_dean);
//			Personnel receivePerson = personnelRepo.get_PersonnelByPersonnelId(billOfLading.getReceive_person());
//			Personnel acc_person = personnelRepo.get_PersonnelByPersonnelId(billOfLading.getAccounting_officer());
//			Personnel secretary = personnelRepo.get_PersonnelByPersonnelId(billOfLading.getSecretary());
//
//			mav.addObject("dean", dean);
//			mav.addObject("receivePerson", receivePerson);
//			mav.addObject("acc_person", acc_person);
//			mav.addObject("secretary", secretary);
//			mav.addObject("purchase_person", purchase_person);
//			mav.addObject("billOfLading", billOfLading);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

	@GetMapping(value = "print_BillOfLadingV")
	public void print_BillOfLadingV(ServletRequest request, HttpSession session, Model md, ServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			print_BillOfLading(request, session, md, response, doc_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "print_BillOfLading")
	public void print_BillOfLading(ServletRequest request, HttpSession session, Model md, ServletResponse response,
			String doc_ids) {
		try {
			request.setCharacterEncoding("UTF-8");
			
			ConnectionDB condb = new ConnectionDB();
			Connection conn = condb.getConnection();
			
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/durablewebservice?characterEncoding=UTF-8",
//					"durable", "Elbarud");

			String doc_id = request.getParameter("doc_id");
			if (doc_ids != null) {
				doc_id = doc_ids;
			}
			BillOfLading billOfLading = billOfLadingRepo.get_billOfLading(doc_id);

			String requisition_person_id = podRepo.get_requisition_person(doc_id);
			System.out.println(requisition_person_id);

			String path = request.getServletContext().getRealPath("/");
			JasperReportsContext jrContext = DefaultJasperReportsContext.getInstance();
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNew.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNewBold.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.encoding",
					"Identity-H");
			File file = null;
			
			if(billOfLading.getVat_check().equals("novat") || billOfLading.getVat_check().equals("repair") || billOfLading.getVat_check().equals("novatotherstore")) {
				file = ResourceUtils.getFile(path+"file/iReport/billOfLadingTemplateComplete_novat.jrxml");
			}else {
				file = ResourceUtils.getFile(path + "file/iReport/billOfLadingTemplateComplete.jrxml");
			}
			
			
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			Map<String, Object> map = new HashMap<>();
			map.put("doc_id", doc_id);
			map.put("re_id", billOfLading.getBillOfLading_id());
			map.put("requis_id_1", requisition_person_id);
			map.put("requis_id_2", requisition_person_id);
			map.put("accept", billOfLading.getRequest_order_person());
			map.put("rec_id", billOfLading.getReceive_person());
			map.put("acc", billOfLading.getAccounting_officer());
			map.put("path", path + "file/iReport/");

			JasperPrint print = JasperFillManager.fillReport(report, map, conn);
			print.setLocaleCode("UTF-8");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
