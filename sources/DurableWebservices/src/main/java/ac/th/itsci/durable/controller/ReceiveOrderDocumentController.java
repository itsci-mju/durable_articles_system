package ac.th.itsci.durable.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.PersonnelAssign;
import ac.th.itsci.durable.entity.PurchaseOrderDocument;
import ac.th.itsci.durable.entity.ReceiveOrderDocument;
import ac.th.itsci.durable.entity.RequestOrderItemList;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.DocumentRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.PersonnelAssignRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;
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
public class ReceiveOrderDocumentController {

	@Autowired
	ReceiveOrderDocumentRepository rodRepo;

	@Autowired
	RequestOrderItemListRepository requestItemRepo;

	@Autowired
	DocumentRepository documentRepo;

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	PurchaseOrderDocumentRepository podRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	PersonnelAssignRepository personnelAssignRepo;

	@GetMapping(value = "/do_loadAddReceiveOrderDocument")
	public ModelAndView do_loadAddReceiveOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addReceiveOrderPage");
		List<PersonnelAssign> doc_3_1 = new ArrayList<>();
		List<PersonnelAssign> doc_3_2 = new ArrayList<>();
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			PurchaseOrderDocument purchaseOrderDoc = podRepo.get_purchaseOrderDocument(doc_id);

			doc_3_1 = personnelAssignRepo.get_personnelByAssignType("doc_2_2");
			doc_3_2 = personnelAssignRepo.get_personnelByAssignType("doc_3_2");

			mav.addObject("purchaseOrderDoc", purchaseOrderDoc);
			mav.addObject("doc_3_1", doc_3_1);
			mav.addObject("doc_3_2", doc_3_2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addReceiveOrderDocument")
	public ModelAndView do_addReceiveOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");

		ReceiveOrderDocument rods = new ReceiveOrderDocument();

		int result = 0;
		String message = "";
		int result_add_check = 0;

		try {
			String doc_id = request.getParameter("doc_id");
			String receiveOrderDoc_id = request.getParameter("receiveOrder_id");
			String date = request.getParameter("date");
			String receiveOrderDocument_from = request.getParameter("receiveOrderDocument_from");
			String invoice_number = request.getParameter("invoice_number");
			String doc_3_1 = request.getParameter("doc_3_1");
			String doc_3_2 = request.getParameter("doc_3_2");

			String[] invoice_amount = request.getParameterValues("invoice_amount");
			String[] received_amount = request.getParameterValues("received_amount");
			String[] item_id = request.getParameterValues("item_id");

			rods = new ReceiveOrderDocument(receiveOrderDoc_id, receiveOrderDocument_from, invoice_number, "", null);
			rods.setDoc_id(doc_id);
			rods.setReceive_order_1(doc_3_1);
			rods.setReceive_order_2(doc_3_2);

			result = rodRepo.add_ReceiveOrderDocument(date, rods.getInvoice_number(), "",
					rods.getReceiveOrderDocument_from(), rods.getReceiveOrderDocument_id(), rods.getDoc_id(),
					rods.getReceive_order_1(), rods.getReceive_order_2());

			for (int i = 0; i < invoice_amount.length; i++) {
				requestItemRepo.insert_requestItem_ReceivedOrderDocument(Integer.parseInt(invoice_amount[i]),
						Integer.parseInt(received_amount[i]), doc_id, item_id[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result != 0) {
			message = "บันทึกข้อมูลใบตรวจรับพัสดุสําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลใบตรวจรับพัสดุได้";
		}

		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "3");
		mav.addObject("doc_id", rods.getDoc_id());

		return mav;
	}
	
	@GetMapping(value="/do_loadEditReceiveOrderDocument")
	public ModelAndView do_loadEditReceiveOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editReceiveOrderPage");
		ReceiveOrderDocument rod = new ReceiveOrderDocument();
		List<PersonnelAssign> doc_3_1 = new ArrayList<>();
		List<PersonnelAssign> doc_3_2 = new ArrayList<>();
		
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			System.out.println(doc_id);
			rod = rodRepo.get_ReceiveOrderDocument(doc_id);
			
			doc_3_1 = personnelAssignRepo.get_personnelByAssignType("doc_2_2");
			doc_3_2 = personnelAssignRepo.get_personnelByAssignType("doc_3_2");

			mav.addObject("rod", rod);
			mav.addObject("doc_3_1", doc_3_1);
			mav.addObject("doc_3_2", doc_3_2);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@PostMapping(value = "/do_editReceiveOrderDocument")
	public ModelAndView do_editReceiveOrderDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");

		ReceiveOrderDocument rods = new ReceiveOrderDocument();

		int result = 0;
		String message = "";
		int result_add_check = 0;

		try {
			String doc_id = request.getParameter("doc_id");
			String receiveOrderDoc_id = request.getParameter("receiveOrder_id");
			String date = request.getParameter("date");
			String receiveOrderDocument_from = request.getParameter("receiveOrderDocument_from");
			String invoice_number = request.getParameter("invoice_number");
			String doc_3_1 = request.getParameter("doc_3_1");
			String doc_3_2 = request.getParameter("doc_3_2");

			String[] invoice_amount = request.getParameterValues("invoice_amount");
			String[] received_amount = request.getParameterValues("received_amount");
			String[] item_id = request.getParameterValues("item_id");

			rods = new ReceiveOrderDocument(receiveOrderDoc_id, receiveOrderDocument_from, invoice_number, "", null);
			rods.setDoc_id(doc_id);
			rods.setReceive_order_1(doc_3_1);
			rods.setReceive_order_2(doc_3_2);

			result = rodRepo.edit_ReceiveOrderDocument(date, rods.getInvoice_number(), "",
					rods.getReceiveOrderDocument_from(), rods.getReceiveOrderDocument_id(), rods.getDoc_id(),
					rods.getReceive_order_1(), rods.getReceive_order_2());

			for (int i = 0; i < invoice_amount.length; i++) {
				requestItemRepo.insert_requestItem_ReceivedOrderDocument(Integer.parseInt(invoice_amount[i]),
						Integer.parseInt(received_amount[i]), doc_id, item_id[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result != 0) {
			message = "แก้ไขข้อมูลใบตรวจรับพัสดุสําเร็จ";
			result_add_check = 1;
		} else {
			message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลใบตรวจรับพัสดุได้";
		}

		mav = do_loadListDocumentPage(request, session, md);
		mav.addObject("message", message);
		mav.addObject("result_add_check", result_add_check);
		mav.addObject("doc_type", "3");
		mav.addObject("doc_id", rods.getDoc_id());

		return mav;
	}

	public ModelAndView do_loadListDocumentPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");
			List<Document> listDocument = null;
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

	@GetMapping(value = "print_ReceiveOrderV")
	public void print_ReceiveOrderV(ServletRequest request, HttpSession session, Model md, ServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			print_ReceiveOrder(request, session, md, response, doc_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "print_ReceiveOrder")
	public void print_ReceiveOrder(ServletRequest request, HttpSession session, Model md, ServletResponse response,
			String doc_ids) {
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
			ReceiveOrderDocument rid = rodRepo.get_ReceiveOrderDocument(doc_id);
			String faculty_dean = podRepo.get_faculty_dean(doc_id);

			String requisition_person_id = podRepo.get_requisition_person(doc_id);
			System.out.println(faculty_dean + " " + requisition_person_id);
			String path = request.getServletContext().getRealPath("/");
			JasperReportsContext jrContext = DefaultJasperReportsContext.getInstance();
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNew.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNewBold.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.encoding",
					"Identity-H");
			
			File file = null;
			
			if(rid.getVat_check().equals("novat") || rid.getVat_check().equals("repair") || rid.getVat_check().equals("novatotherstore")) {
				file = ResourceUtils.getFile(path+"file/iReport/ReceiveOrderTemplateComplete_novat.jrxml");
			}else {
				file = ResourceUtils.getFile(path + "file/iReport/ReceiveOrderTemplateComplete.jrxml");
			}
			
			
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			Map<String, Object> map = new HashMap<>();
			map.put("doc_id", doc_id);
			map.put("reid", rid.getReceiveOrderDocument_id());
			map.put("requisition_id", requisition_person_id);
			map.put("dean_id", faculty_dean);
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

	// nice close code receiveDocument, chang mav object to null
//	@GetMapping(value = "/do_getReceiveDocument")
//	public ModelAndView do_getReceiveDocument(ServletRequest request, HttpSession session, Model md, String id) {
//		ModelAndView mav = new ModelAndView("viewReceiveDocumentPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String doc_id = request.getParameter("doc_id");
//			if (id != null) {
//				doc_id = id;
//			}
//			ReceiveOrderDocument receiveDocument = rodRepo.get_ReceiveOrderDocument(doc_id);
//
//			String requisition_person_id = podRepo.get_requisition_person(doc_id);
//			String faculty_dean = podRepo.get_faculty_dean(doc_id);
//
//			Personnel purchase_person = personnelRepo.get_PersonnelByPersonnelId(requisition_person_id);
//			Personnel dean = personnelRepo.get_PersonnelByPersonnelId(faculty_dean);
//
//			String DATE_FORMAT = "dd MMMM yyyy";
//			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
//
//			mav.addObject("receiveDocument", receiveDocument);
//			mav.addObject("dean", dean);
//			mav.addObject("purchase_person", purchase_person);
//			mav.addObject("date", sdf.format(receiveDocument.getDate()));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

}
