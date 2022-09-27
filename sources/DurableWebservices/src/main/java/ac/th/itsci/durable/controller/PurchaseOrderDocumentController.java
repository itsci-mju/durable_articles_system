package ac.th.itsci.durable.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.PersonnelAssign;
import ac.th.itsci.durable.entity.Position;
import ac.th.itsci.durable.entity.PurchaseOrderDocument;
import ac.th.itsci.durable.entity.ReceiveOrderDocument;
import ac.th.itsci.durable.entity.RequestOrderItemList;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.DocumentRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.PersonnelAssignRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;
import ac.th.itsci.durable.repo.PositionRepository;
import ac.th.itsci.durable.repo.PurchaseOrderDocumentRepository;
import ac.th.itsci.durable.repo.ReceiveOrderDocumentRepository;
import ac.th.itsci.durable.repo.RequestOrderItemListRepository;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;

@Controller
public class PurchaseOrderDocumentController {

	@Autowired
	PositionRepository positionRepo;

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	PurchaseOrderDocumentRepository podRepo;

	@Autowired
	DocumentRepository documentRepo;

	@Autowired
	RequestOrderItemListRepository requestItemRepo;

	@Autowired
	ReceiveOrderDocumentRepository rodRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	PersonnelAssignRepository personnelAssignRepo;

	@GetMapping(value = "/do_loadAddPurchaseOrderPage")
	public ModelAndView do_loadAddPurchaseOrderPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderPage");
		Document document = new Document();
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");

			String document_id = request.getParameter("doc_id");
			document.setDoc_id(document_id);

			System.out.println(document.getDoc_id());

			// get document
			Document purchaseOrderRequest = documentRepo.get_purchaseOrderRequestDocument(document.getDoc_id());

			// get personnel
			List<PersonnelAssign> doc_2_1 = personnelAssignRepo.get_personnelByAssignType("doc_2_1");
			List<PersonnelAssign> doc_2_2 = personnelAssignRepo.get_personnelByAssignType("doc_2_2");
			List<PersonnelAssign> doc_2_3 = personnelAssignRepo.get_personnelByAssignType("doc_2_3");
			List<PersonnelAssign> doc_2_4 = personnelAssignRepo.get_personnelByAssignType("doc_1_2");
			List<PersonnelAssign> doc_2_5 = personnelAssignRepo.get_personnelByAssignType("doc_1_4");
			List<PersonnelAssign> doc_2_6 = personnelAssignRepo.get_personnelByAssignType("doc_2_6");

			request.setAttribute("date", purchaseOrderRequest);
			System.out.println(purchaseOrderRequest.getDoc_date());

			mav.addObject("purchaseOrderRequest", purchaseOrderRequest);

			mav.addObject("doc_2_1", doc_2_1);
			mav.addObject("doc_2_2", doc_2_2);
			mav.addObject("doc_2_3", doc_2_3);
			mav.addObject("doc_2_4", doc_2_4);
			mav.addObject("doc_2_5", doc_2_5);
			mav.addObject("doc_2_6", doc_2_6);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@GetMapping(value = "/do_loadEditPurchaseOrderPage")
	public ModelAndView do_loadEditPurchaseOrderPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderPage");
		Document document = new Document();
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");

			String document_id = request.getParameter("doc_id");
			document.setDoc_id(document_id);

			System.out.println(document.getDoc_id());

			// get document
			PurchaseOrderDocument purchaseOrder = podRepo.get_purchaseOrderDocument(document.getDoc_id());

			// get personnel
			List<PersonnelAssign> doc_2_1 = personnelAssignRepo.get_personnelByAssignType("doc_2_1");
			List<PersonnelAssign> doc_2_2 = personnelAssignRepo.get_personnelByAssignType("doc_2_2");
			List<PersonnelAssign> doc_2_3 = personnelAssignRepo.get_personnelByAssignType("doc_2_3");
			List<PersonnelAssign> doc_2_4 = personnelAssignRepo.get_personnelByAssignType("doc_1_2");
			List<PersonnelAssign> doc_2_5 = personnelAssignRepo.get_personnelByAssignType("doc_1_4");
			List<PersonnelAssign> doc_2_6 = personnelAssignRepo.get_personnelByAssignType("doc_2_6");

			request.setAttribute("date", purchaseOrder);

			mav.addObject("purchaseOrder", purchaseOrder);
			mav.addObject("doc_2_1", doc_2_1);
			mav.addObject("doc_2_2", doc_2_2);
			mav.addObject("doc_2_3", doc_2_3);
			mav.addObject("doc_2_4", doc_2_4);
			mav.addObject("doc_2_5", doc_2_5);
			mav.addObject("doc_2_6", doc_2_6);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_addPurchaseOrderDocument")
	public ModelAndView do_addPurchaseOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("viewPurchaseOrderPage");
		String message = "";
		PurchaseOrderDocument purchaseDocument = new PurchaseOrderDocument();
		int result = 0;
		try {
			String purchaseOrderRequest_id = request.getParameter("doc_id");
			String purchaseOrder_id = request.getParameter("purchaseOrder_id");
			String purchase_orderType = request.getParameter("purchase_orderType");
			String date = request.getParameter("date");
			String prescription = request.getParameter("prescription");

			String[] item_id = request.getParameterValues("item_id");
			String[] note = request.getParameterValues("note");

			Date doc_date = new Date();
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				doc_date = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String purchase_old = request.getParameter("doc_2_2");// doc_2_2
			String dean_old = request.getParameter("doc_2_6");// doc_2_6
			String doc_2_1 = request.getParameter("doc_2_1");
			String doc_2_3 = request.getParameter("doc_2_3");
			String doc_2_4 = request.getParameter("doc_2_4");
			String doc_2_5 = request.getParameter("doc_2_5");

			for (int i = 0; i < item_id.length; i++) {
				requestItemRepo.insert_requestItem_PurchaseOrderDocument(note[i], prescription, purchaseOrderRequest_id,
						item_id[i]);
			}

			purchaseDocument = new PurchaseOrderDocument(purchaseOrder_id, purchase_orderType, "",
					doc_date, purchase_old, dean_old);
			purchaseDocument.setDoc_id(purchaseOrderRequest_id);
			purchaseDocument.setPurchase_order_1(doc_2_1);
			purchaseDocument.setPurchase_order_3(doc_2_3);
			purchaseDocument.setPurchase_order_4(doc_2_4);
			purchaseDocument.setPurchase_order_5(doc_2_5);

			result = podRepo.add_purchaseOrderDocument(date, purchaseDocument.getFaculty_dean(),
					purchaseDocument.getPurchaseOrderDoc_describe(), purchaseDocument.getPurchaseOrderDocument_id(),
					purchaseDocument.getPurchaseOrder_type(), purchaseDocument.getRequisition_person(),
					purchaseDocument.getDoc_id(), purchaseDocument.getPurchase_order_1(),
					purchaseDocument.getPurchase_order_3(), purchaseDocument.getPurchase_order_4(),
					purchaseDocument.getPurchase_order_5());

			
			

			// mav = do_GetPurchaseOrderDocument(request, session, md,
			// purchaseDocument.getDoc_id());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int result_add_check = 0;
		
		if (result != 0) {
			message = "บันทึกข้อมูลเอกสารขอซื้อ/จ้าง สําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลเอกสารขอซื้อ/จ้างได้ !";
		}
		
		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "2");
		mav.addObject("doc_id", purchaseDocument.getDoc_id());

		return mav;
	}

	@PostMapping(value = "/do_editPurchaseOrderDocument")
	public ModelAndView do_editPurchaseOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("viewPurchaseOrderPage");
		String message = "";
		PurchaseOrderDocument purchaseDocument = new PurchaseOrderDocument();
		int result = 0;
		try {
			String purchaseOrderRequest_id = request.getParameter("doc_id");
			String purchaseOrder_id = request.getParameter("purchaseOrder_id");
			String purchase_orderType = request.getParameter("purchase_orderType");
			String date = request.getParameter("date");
			String prescription = request.getParameter("prescription");

			String[] item_id = request.getParameterValues("item_id");
			String[] note = request.getParameterValues("note");

			Date doc_date = new Date();
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				doc_date = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String purchase_old = request.getParameter("doc_2_2");// doc_2_2
			String dean_old = request.getParameter("doc_2_6");// doc_2_6
			String doc_2_1 = request.getParameter("doc_2_1");
			String doc_2_3 = request.getParameter("doc_2_3");
			String doc_2_4 = request.getParameter("doc_2_4");
			String doc_2_5 = request.getParameter("doc_2_5");

			for (int i = 0; i < item_id.length; i++) {
				requestItemRepo.insert_requestItem_PurchaseOrderDocument(note[i], prescription, purchaseOrderRequest_id,
						item_id[i]);
			}

			purchaseDocument = new PurchaseOrderDocument(purchaseOrder_id, purchase_orderType, "", doc_date,
					purchase_old, dean_old);
			purchaseDocument.setDoc_id(purchaseOrderRequest_id);
			purchaseDocument.setPurchase_order_1(doc_2_1);
			purchaseDocument.setPurchase_order_3(doc_2_3);
			purchaseDocument.setPurchase_order_4(doc_2_4);
			purchaseDocument.setPurchase_order_5(doc_2_5);

			result = podRepo.edit_purchaseOrderDocument(date, purchaseDocument.getFaculty_dean(),
					purchaseDocument.getPurchaseOrderDoc_describe(), purchaseDocument.getPurchaseOrderDocument_id(),
					purchaseDocument.getPurchaseOrder_type(), purchaseDocument.getRequisition_person(),
					purchaseDocument.getDoc_id(), purchaseDocument.getPurchase_order_1(),
					purchaseDocument.getPurchase_order_3(), purchaseDocument.getPurchase_order_4(),
					purchaseDocument.getPurchase_order_5());

			// mav = do_GetPurchaseOrderDocument(request, session, md,
			// purchaseDocument.getDoc_id());

		} catch (Exception e) {
			e.printStackTrace();
		}

		int result_add_check = 0;

		if (result != 0) {
			message = "แก้ไขข้อมูลเอกสารขอซื้อ/จ้าง สําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลเอกสารขอซื้อ/จ้างได้ !";
		}

		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "2");
		mav.addObject("doc_id", purchaseDocument.getDoc_id());

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

//	
	public List<String> formatDate(List<Document> listDocument) {
		List<String> all_date = new ArrayList<>();
		String DATE_FORMAT = "dd MMMM yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

		for (Document d : listDocument) {
			all_date.add("" + sdf.format(d.getDoc_date()));
		}

		return all_date;
	}
//
//
//	@GetMapping(value = "/do_GetPurchaseOrderDocument")
//	public ModelAndView do_GetPurchaseOrderDocument(ServletRequest request, HttpSession session, Model md, String id) {
//		ModelAndView mav = new ModelAndView("viewPurchaseOrderPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String doc_id = request.getParameter("doc_id");
//			if(id != null) {
//				doc_id = id;
//			}
//			
//			PurchaseOrderDocument purchaseOrderDoc = podRepo.get_purchaseOrderDocument(doc_id);
//			Personnel purchasePerson = personnelRepo
//					.get_PersonnelByPersonnelId(purchaseOrderDoc.getRequisition_person());
//			Personnel dean = personnelRepo.get_PersonnelByPersonnelId(purchaseOrderDoc.getFaculty_dean());
//
//			Personnel documentor = personnelRepo.get_PersonnelByPersonnelId(purchaseOrderDoc.getRequest_order_person());
//			Personnel aoc = personnelRepo.get_PersonnelByPersonnelId(purchaseOrderDoc.getAccounting_officer());
//			Personnel secretary = personnelRepo.get_PersonnelByPersonnelId(purchaseOrderDoc.getSecretary());
//			Personnel cofPerson = personnelRepo.get_PersonnelByPersonnelId(purchaseOrderDoc.getChief_of_procurement());
//
//			String DATE_FORMAT = "dd MMMM yyyy";
//			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
//			List<String> prescription_date = new ArrayList<>();
//
//			for (int i = 0; i < purchaseOrderDoc.getRequisitionItemLists().size(); i++) {
//				prescription_date.add("" + sdf.format(purchaseOrderDoc.getRequisitionItemLists().get(i).getDate()));
//			}
//
//			mav.addObject("prescription_date", prescription_date);
//			mav.addObject("documentor", documentor);
//			mav.addObject("date", sdf.format(purchaseOrderDoc.getDate()));
//			mav.addObject("aoc", aoc);
//			mav.addObject("secretary", secretary);
//			mav.addObject("cofPerson", cofPerson);
//
//			mav.addObject("purchaseOrderDoc", purchaseOrderDoc);
//			mav.addObject("purchasePerson", purchasePerson);
//			mav.addObject("dean", dean);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return mav;
//	}

	@GetMapping(value = "print_PurchaseOrderDocumentV")
	public void print_PurchaseOrderDocumentV(ServletRequest request, HttpSession session, Model md,
			ServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			print_PurchaseOrderDocument(request, session, md, response, doc_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "print_PurchaseOrderDocument")
	public void print_PurchaseOrderDocument(ServletRequest request, HttpSession session, Model md,
			ServletResponse response, String doc_ids) {
		try {
			request.setCharacterEncoding("UTF-8");
			Connection conn = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/durablewebservice?characterEncoding=UTF-8",
					"root", "Fluke123");

			String doc_id = request.getParameter("doc_id");
			if (doc_ids != null) {
				doc_id = doc_ids;
			}
			System.out.println(doc_id +" "+ doc_ids);
			PurchaseOrderDocument p = podRepo.get_purchaseOrderDocument(doc_id);

			String path = request.getServletContext().getRealPath("/");
			JasperReportsContext jrContext = DefaultJasperReportsContext.getInstance();
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNew.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNewBold.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.encoding",
					"Identity-H");
			
			File file = null;
			if(p.getVat_check().equals("novat") || p.getVat_check().equals("repair") || p.getVat_check().equals("novatotherstore")) {
				file = ResourceUtils.getFile(path+"file/iReport/PurchaseOrderTemplateComplete_novat.jrxml");
			}else {
				file = ResourceUtils.getFile(path + "file/iReport/PurchaseOrderTemplateComplete.jrxml");
			}
			
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			Map<String, Object> map = new HashMap<>();
			map.put("doc_id", doc_id);
			map.put("purchaseOrderID", p.getPurchaseOrderDocument_id());
			map.put("item_size", p.getRequisitionItemLists().size() + "");
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
