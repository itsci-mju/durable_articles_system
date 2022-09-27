package ac.th.itsci.durable.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import ac.th.itsci.durable.entity.Committee;
import ac.th.itsci.durable.entity.CommitteeID;
import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Item;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.PersonnelAssign;
import ac.th.itsci.durable.entity.Position;
import ac.th.itsci.durable.entity.ReceiveOrderDocument;
import ac.th.itsci.durable.entity.RequestOrderItemList;
import ac.th.itsci.durable.entity.RequestOrderItemListID;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.CommitteeRepository;
import ac.th.itsci.durable.repo.DocumentRepository;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.ItemRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.PersonnelAssignRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;
import ac.th.itsci.durable.repo.PositionRepository;
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

import java.util.*;

@Controller
public class DocumentController {

	@Autowired
	DocumentRepository documentRepo;

	@Autowired
	RequestOrderItemListRepository requestOrderRepo;

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	PersonnelAssignRepository personnelAssignRepo;

	@Autowired
	CommitteeRepository committeeRepo;

	@Autowired
	PositionRepository positionRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	ReceiveOrderDocumentRepository rodRepo;

	@Autowired
	DurableRepository durableRepo;

	@Autowired
	ItemRepository itemRepo;

	@GetMapping(value = "/do_ReportDocument")
	public ModelAndView do_ReportDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");

		try {
			request.setCharacterEncoding("UTF-8");
			String document_id = request.getParameter("doc_id");
			String status = "กรุณาแก้ไขเอกสาร";

			documentRepo.report_document(status, document_id);

			mav = do_loadListDocumentPage(request, session, md);

			mav.addObject("message", "แจ้งแก้ไขเอกสารสําเร็จ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@GetMapping(value = "/do_loadAddPurchaseOrderRequestPage")
	public ModelAndView do_loadAddPurchaseOrderRequestPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");

			List<Personnel> committee_list = new ArrayList<>();
			List<PersonnelAssign> request_order_1 = new ArrayList<>();
			List<PersonnelAssign> request_order_2 = new ArrayList<>();
			List<PersonnelAssign> request_order_3 = new ArrayList<>();
			List<PersonnelAssign> request_order_4 = new ArrayList<>();

			committee_list = personnelRepo.get_personnelByMajor(staff.getMajor().getID_Major());

			request_order_1 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_1",
					staff.getMajor().getID_Major());
			request_order_2 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_2",
					staff.getMajor().getID_Major());
			request_order_3 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_3",
					staff.getMajor().getID_Major());
			request_order_4 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_4",
					staff.getMajor().getID_Major());

			session.setAttribute("committee_list", committee_list);

			session.setAttribute("doc_1_1", request_order_1);
			session.setAttribute("doc_1_2", request_order_2);
			session.setAttribute("doc_1_3", request_order_3);
			session.setAttribute("doc_1_4", request_order_4);

		} catch (Exception e) {
			e.printStackTrace();
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

	@GetMapping(value = "/do_loadListDocumentPage")
	public ModelAndView do_loadListDocumentPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");
			List<Document> listDocument = null;
			List<Major> listMajor = majorRepo.getAllMajor();

			if (staff.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				listDocument = documentRepo.get_allDocument();
			} else {
				listDocument = documentRepo.get_DocumentByDepartName(staff.getMajor().getMajor_Name());
			}

			System.out.println(listDocument.size());

			List<String> all_date = formatDate(listDocument);

			mav.addObject("date", all_date);
			mav.addObject("documents", listDocument);
			mav.addObject("major", listMajor);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/backLoadListDocument")
	public ModelAndView backLoadListDocument(ServletRequest request, HttpSession session, Model md) {
		session.removeAttribute("document");
		session.removeAttribute("date");
		session.removeAttribute("item_cart");
		session.removeAttribute("committees");
		session.removeAttribute("requisition_old");
		session.removeAttribute("cof_old");
		session.removeAttribute("acc_old");
		session.removeAttribute("old_secretary");
		session.removeAttribute("committee_old");
		session.removeAttribute("check_type");
		session.removeAttribute("check_type");
		session.removeAttribute("repair");
		session.removeAttribute("item_cart_update");
		session.removeAttribute("repair_update");
		session.removeAttribute("total_price_repair_update");
		session.removeAttribute("date");
		session.removeAttribute("committees");
		session.removeAttribute("document");
		session.removeAttribute("vats");
		session.removeAttribute("item_vat");
		session.removeAttribute("store_check");

		ModelAndView mav = do_loadListDocumentPage(request, session, md);

		return mav;
	}

	@PostMapping(value = "/do_SearchDocument")
	public ModelAndView do_SearchDocument(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		List<Document> listDocument = null;
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");
			String doc_id = request.getParameter("doc_id");
			String doc_status = request.getParameter("doc_status");
			List<ReceiveOrderDocument> rod = rodRepo.get_receiveOrderDocumentAll();

			if (staff.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				if (!doc_status.equalsIgnoreCase("-") && !doc_id.equals("")) {
					listDocument = documentRepo.searchDocumentByStatusAndDocId(doc_status, doc_id);
					System.out.println("s s d");
				} else if (doc_id.equals("") && !doc_status.equalsIgnoreCase("-")) {
					listDocument = documentRepo.searchDocumentByStatus(doc_status);
					System.out.println("s");
				} else if (!doc_id.equals("") && doc_status.equalsIgnoreCase("-")) {
					listDocument = documentRepo.searchDocumentById(doc_id);
					System.out.println("d");
				} else {
					listDocument = documentRepo.get_allDocument();
					System.out.println("a");
				}
				mav.addObject("documents", listDocument);
			} else {
				List<Document> document_by_major = new ArrayList<>();
				if (!doc_status.equalsIgnoreCase("-") && !doc_id.equals("")) {
					listDocument = documentRepo.searchDocumentByStatusAndDocId(doc_status, doc_id);
					document_by_major = documentRepo.searchDocumentByStatusAndDocId(doc_status, doc_id);
					System.out.println("s s d");
				} else if (doc_id.equals("") && !doc_status.equalsIgnoreCase("-")) {
					listDocument = documentRepo.searchDocumentByStatus(doc_status);
					document_by_major = documentRepo.searchDocumentByStatus(doc_status);
					System.out.println("s");
				} else if (!doc_id.equals("") && doc_status.equalsIgnoreCase("-")) {
					listDocument = documentRepo.searchDocumentById(doc_id);
					document_by_major = documentRepo.searchDocumentById(doc_id);
					System.out.println("d");
				} else {
					listDocument = documentRepo.get_allDocument();
					document_by_major = documentRepo.get_allDocument();
					System.out.println("a");
				}
				int i = 0;
				for (Document d : listDocument) {
					Personnel p = new Personnel();
					// p = personnelRepo.get_PersonnelByPersonnelId(d.getRequest_order_person());
					if (p.getMajor().getID_Major() == staff.getMajor().getID_Major()) {
						document_by_major.remove(i);
					}
					i++;
				}
				mav.addObject("documents", document_by_major);
			}

			List<String> all_date = formatDate(listDocument);
			mav.addObject("rod", rod);
			mav.addObject("searchKey", doc_id);
			mav.addObject("doc_status", doc_status);
			mav.addObject("date", all_date);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView save_doc_session(ServletRequest request, HttpSession session, Model md, String page) {
		ModelAndView mav = new ModelAndView(page);
		try {
			// save session from document
			String doc_id = request.getParameter("doc_id");
			String plan_name = request.getParameter("plan_name");
			String depart_name = request.getParameter("depart_name");
			String fund_name = request.getParameter("fund_name");
			String money_used = request.getParameter("money_used");
			String work_name = request.getParameter("work_name");
			String budget = request.getParameter("budget");
			String date = request.getParameter("date");
			String doc_title = request.getParameter("doc_title");
			String doc_dear = request.getParameter("doc_dear");
			String doc_title_describe = request.getParameter("doc_title_describe");
			String doc_reason_title = request.getParameter("doc_reason_title");
			String doc_reason_describe = request.getParameter("doc_reason_describe");
			String price_txt = request.getParameter("price_txt");
			String check_type = request.getParameter("check_type");

			session.setAttribute("check_type", check_type);

			String store_name = request.getParameter("store_name");
			String store_check = request.getParameter("store_check");
			String vats = null;
			String item_vat = null;

			session.setAttribute("store_check", store_check);

			if (store_check != null) {
				if (store_check.equals("2")) {
					vats = request.getParameter("vats");
				}

				if (vats != null) {
					item_vat = request.getParameter("item_vat");
					session.setAttribute("vats", "1");
				} else {
					session.removeAttribute("vats");
				}

				if (item_vat != null) {
					session.setAttribute("item_vat", "1");
				} else {
					session.removeAttribute("item_vat");
				}
			}

			String doc_1_1 = request.getParameter("doc_1_1");
			String doc_1_2 = request.getParameter("doc_1_2");
			String doc_1_3 = request.getParameter("doc_1_3");
			String doc_1_4 = request.getParameter("doc_1_4");

			Document document = new Document(doc_id, "", plan_name, "", depart_name, "", fund_name, money_used,
					work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
					doc_reason_describe, null);
			document.setPrice_txt(price_txt);
			document.setStore_name(store_name);

			document.setRequest_order_person(doc_1_1);
			document.setChief_of_procurement(doc_1_2);
			document.setAccounting_officer(doc_1_3);
			document.setSecretary(doc_1_4);

			session.setAttribute("document", document);
			session.setAttribute("date", date);

			if (session.getAttribute("item_cart") != null) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_searchItemDocument")
	public ModelAndView do_searchItemDocument(ServletRequest request, HttpSession session, Model md, String item_name_s,
			String item_category_s) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		Item item = new Item();
		List<Item> items = new ArrayList<>();
		try {
			request.setCharacterEncoding("UTF-8");

			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");

			if (item_name_s == null && item_category_s == null) {

				String item_category = request.getParameter("item_category_search");
				String item_name = request.getParameter("item_name_search");

				item.setItem_name(item_name);
				item.setItem_category(item_category);

				if (item.getItem_category().equalsIgnoreCase("-") && item.getItem_name() != "") {
					items = itemRepo.get_search_item_byName(item.getItem_name());
				} else if (!item.getItem_category().equalsIgnoreCase("-") && item.getItem_name() == "") {
					items = itemRepo.get_search_item_byCategory(item.getItem_category());
				} else {
					items = itemRepo.get_search_item(item.getItem_name(), item.getItem_category());
				}

				mav.addObject("item_search_doc", item);
				session.setAttribute("item_search_doc", item);

			} else {

				Item item_search = new Item();
				item_search.setItem_name(item_name_s);
				item_search.setItem_category(item_category_s);

				if (item_search.getItem_category().equalsIgnoreCase("-") && item_search.getItem_name() != "") {
					items = itemRepo.get_search_item_byName(item_search.getItem_name());
				} else if (!item_search.getItem_category().equalsIgnoreCase("-") && item_search.getItem_name() == "") {
					items = itemRepo.get_search_item_byCategory(item_search.getItem_category());
				} else {
					items = itemRepo.get_search_item(item_search.getItem_name(), item_search.getItem_category());
				}

				mav.addObject("item_search_doc", item_search);
				session.setAttribute("item_search_doc", item_search);
			}

			mav.addObject("items", items);
			mav.addObject("itemSize", 0);

			List<RequestOrderItemList> request_order_item_list = new ArrayList<>();

			if (session.getAttribute("item_cart") != null) {
				request_order_item_list = (List<RequestOrderItemList>) session.getAttribute("item_cart");
				String[] item_price = request.getParameterValues("item_price");
				String[] item_total = request.getParameterValues("item_total");

				int i = 0;
				for (RequestOrderItemList rol : request_order_item_list) {

					if (item_price[i] == null || item_price[i].equals("")) {
						item_price[i] = "0";
					}
					if (item_total[i] == null || item_total[i].equals("")) {
						item_total[i] = "0";
					}

					rol.setAmount(Integer.parseInt(item_total[i]));
					rol.setItem_price(Double.parseDouble(item_price[i]));

					i++;
				}

				session.setAttribute("item_cart", request_order_item_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addItemCart")
	public ModelAndView do_addItemCart(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		String message = "";

		try {
			request.setCharacterEncoding("UTF-8");
			String item_id = request.getParameter("item_id");

			Item item = itemRepo.get_ItemByID(item_id);
			RequestOrderItemListID request_order_item_id = new RequestOrderItemListID();
			RequestOrderItemList request_order_item = new RequestOrderItemList();
			List<RequestOrderItemList> request_order_item_list = new ArrayList<>();
			boolean check_dup = false;
			if (session.getAttribute("item_cart") != null) {
				request_order_item_list = (List<RequestOrderItemList>) session.getAttribute("item_cart");
				for (RequestOrderItemList r : request_order_item_list) {
					if (r.getId().getItem().getItem_id().equals(item_id)) {
						check_dup = true;
					}
				}
			}
			if (!check_dup) {
				request_order_item_id.setItem(item);
				request_order_item.setId(request_order_item_id);
				request_order_item.setAmount(1);
				request_order_item_list.add(request_order_item);
				message = "เพิ่มรายการเสนอซื้อเรียบร้อย";
			} else {
				message = "วัสดุชิ้นนี้มีอยู่ในรายการแล้ว";
			}

			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");

			session.setAttribute("item_cart", request_order_item_list);

		} catch (Exception e) {
			message = "ข้อผิดพลาด :: ไม่สามารถเพิ่มรายการเสนอซื้อจ้างได้";
			e.printStackTrace();
		}

		mav.addObject("message", message);

		return mav;
	}

	@PostMapping(value = "/do_deleteItemCart")
	public ModelAndView do_deleteItemCart(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		try {

			request.setCharacterEncoding("UTF-8");
			String item_id = request.getParameter("item_id");
			List<RequestOrderItemList> item_cart = (List<RequestOrderItemList>) session.getAttribute("item_cart");
			String message = "";
			int i = 0;
			for (RequestOrderItemList ic : item_cart) {
				if (ic.getId().getItem().getItem_id().equals(item_id)) {
					item_cart.remove(i);
					message = "ลบรายการ " + ic.getId().getItem().getItem_name() + " เรียบร้อย";
					break;
				}
				i++;
			}
			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
			session.setAttribute("item_cart", item_cart);
			mav.addObject("message", message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addPurchaseOrderRequest")
	public ModelAndView do_addPurchaseOrderRequest(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("viewPurchaseOrderRequestPage");

		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			String plan_name = request.getParameter("plan_name");
			String depart_name = request.getParameter("depart_name");
			String fund_name = request.getParameter("fund_name");
			String money_used = request.getParameter("money_used");
			String work_name = request.getParameter("work_name");
			String budget = request.getParameter("budget");
			String date = request.getParameter("date");
			String doc_title = request.getParameter("doc_title");
			String doc_dear = request.getParameter("doc_dear");
			String doc_title_describe = request.getParameter("doc_title_describe");
			String doc_reason_describe = request.getParameter("doc_reason_describe");
			String doc_status = "รอการอนุมัติ";
			System.out.println(date);
			String store_check = request.getParameter("store_check");
			String store_name = request.getParameter("store_name");
			String vat_check = null;
			String item_vat = null;

			if (store_check != null) {
				if (store_check.equals("2")) {
					vat_check = request.getParameter("vats");
					System.out.println("vate_check" + vat_check);
				}
			}
			if (vat_check != null) {
				item_vat = request.getParameter("item_vat");
			}

			String[] item_id = request.getParameterValues("item_id");
			String[] item_total = request.getParameterValues("item_total");
			String[] item_total_price = request.getParameterValues("item_total_price");
			String[] item_price = request.getParameterValues("item_price");

			String price_txt = request.getParameter("price_txt");

			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Date dateStart = null;
			String dateStirngStartNew = null;

			Date dateStartNew = null;

			dateStart = simpleDateFormatThai.parse(date);
			dateStirngStartNew = simpledate.format(dateStart);
			dateStartNew = simpledate.parse(dateStirngStartNew);

			int budget_year = 0;
			if ((dateStartNew.getMonth() + 1) >= 10) {
				budget_year = ((dateStartNew.getYear() + 1900) + 1);
			} else {
				budget_year = (dateStartNew.getYear() + 1900);
			}
			
			System.out.println("=====> this is budget year" +budget_year);

			int result = 0;

			String request_order_person = request.getParameter("doc_1_1");
			String chief_of_procurement = request.getParameter("doc_1_2");

			if (chief_of_procurement == null) {
				chief_of_procurement = "PER20";
			}

			String accounting_officer = request.getParameter("doc_1_3");
			String secretary = request.getParameter("doc_1_4");

			// end assign signature

			// add document
			Document document = new Document(doc_id, "", plan_name, "", depart_name, "", fund_name, money_used,
					work_name, budget, dateStartNew, doc_title, doc_dear, doc_title_describe, "", doc_reason_describe,
					doc_status);
			document.setAccounting_officer(accounting_officer);
			document.setChief_of_procurement(chief_of_procurement);
			document.setRequest_order_person(request_order_person);
			document.setSecretary(secretary);

			Double total_price = 0.0;
			Double total_price_with_out_tax = 0.0;
			Double tax = 0.0;

			List<RequestOrderItemList> rols_repair = new ArrayList<>();
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String check_type = request.getParameter("check_type");
			if (check_type.equalsIgnoreCase("i")) {

				for (int k = 0; k < item_id.length; k++) {
					total_price += Double.parseDouble(item_total_price[k]);
				}

				if (vat_check != null) {
					if (item_vat != null) {
						total_price_with_out_tax = total_price / 1.07;
						tax = total_price_with_out_tax * 0.07;
						vat_check = "vat:itemvatinclude";
					} else {

						total_price_with_out_tax = total_price;
						tax = total_price * 0.07;
						total_price = total_price_with_out_tax + tax;
						vat_check = "vat:itemvatunclude";
					}
				} else {
					total_price_with_out_tax = total_price;
					tax = total_price;
					if (store_check.equals("1")) {
						vat_check = "novatotherstore";
					} else {
						vat_check = "novat";
					}

				}
			} else {
				price_txt = request.getParameter("price_txt_repair");
				rols_repair = (List<RequestOrderItemList>) session.getAttribute("repair");

				for (RequestOrderItemList r : rols_repair) {
					total_price += r.getTotal_price();
				}
				vat_check = "repair";
			}

			document.setStore_name(store_name);
			document.setVat_check(vat_check);

			document.setPrice_txt(price_txt);
			document.setTotal_price(total_price);
			document.setPrice_with_out_tax(Double.parseDouble(decimalFormat.format(total_price_with_out_tax)));
			document.setTax(Double.parseDouble(decimalFormat.format(tax)));

			int committee_result = 1;
			int check_add_item_request = 0;
			int ril_size = 0;

			int count_request_item_id = 0;
			try {
				result = documentRepo.add_Document(document.getDoc_id(), document.getAccounting_officer(),
						document.getBudget(), document.getChief_of_procurement(), dateStirngStartNew,
						document.getDepart_id(), document.getDepart_name(), document.getDoc_dear(),
						document.getDoc_reason_describe(), document.getDoc_reason_title(), document.getDoc_title(),
						document.getDoc_title_decribe(), document.getFund_id(), document.getFund_name(),
						document.getMoney_used(), document.getPlan_id(), document.getPlan_name(),
						document.getPrice_txt(), document.getRequest_order_person(), document.getSecretary(),
						document.getStatus(), document.getWork_name(), document.getTotal_price(),
						document.getPrice_with_out_tax(), document.getTax(), document.getStore_name(),
						document.getVat_check());
				List<RequestOrderItemList> ril = new ArrayList<>();

				count_request_item_id = requestOrderRepo.get_countRequestOrderItem();
				String request_item_id = "";
				if (check_type.equalsIgnoreCase("i")) {
					for (int i = 0; i < item_id.length; i++) {
						count_request_item_id += 1;
						Item item = new Item();
						item.setItem_id(item_id[i]);

						RequestOrderItemListID rid = new RequestOrderItemListID();
						rid.setItem(item);
						rid.setDocument(document);
						rid.setRequestOrderItemList_id("I" + count_request_item_id);

						RequestOrderItemList requestOrderItemList = new RequestOrderItemList();
						requestOrderItemList.setItem_price(Double.parseDouble(item_price[i]));
						requestOrderItemList.setAmount(Integer.parseInt(item_total[i]));
						requestOrderItemList.setTotal_price(Double.parseDouble(item_total_price[i]));
						requestOrderItemList.setId(rid);
						requestOrderItemList.setBudget_year(budget_year);

						ril.add(requestOrderItemList);

						check_add_item_request += requestOrderRepo.add_itemRequestItem_firstDoc(ril.get(i).getAmount(),
								ril.get(i).getTotal_price(), ril.get(i).getId().getDocument().getDoc_id(),
								ril.get(i).getId().getItem().getItem_id(), ril.get(i).getItem_price(),
								ril.get(i).getId().getRequestOrderItemList_id(), ril.get(i).getBudget_year());
					}
					ril_size = ril.size();
					document.setRequisitionItemLists(ril);
				} else {
					String item_category = "";
					if (document.getFund_name().equalsIgnoreCase("สินทรัพย์ถาวร")) {
						item_category = "หมวดวัสดุสินทรัพย์ถาวร";
					} else {
						item_category = "หมวดบํารุงรักษาสินทรัพย์";
					}

					int count_repair = requestOrderRepo.get_countRequestOrderItem();

					for (RequestOrderItemList r : rols_repair) {
						int repair_id = itemRepo.count_Item();
						String repair_ids = "RP";
						count_repair += 1;
						repair_ids += (repair_id + 1) + "";
						itemRepo.insert_Item(repair_ids, r.getId().getItem().getItem_name(),
								r.getId().getItem().getItem_price(), r.getId().getItem().getItem_unit(), "",
								item_category);
						System.out.println("check" + r.getId().getRequestOrderItemList_id());
						check_add_item_request += requestOrderRepo.add_itemRequestItem_firstDoc(r.getAmount(),
								r.getTotal_price(), doc_id, repair_ids, r.getItem_price(), "R" + count_repair,
								budget_year);
					}
					ril_size = rols_repair.size();
				}
				List<Committee> committee = (List<Committee>) session.getAttribute("committees");
				// List<Committee> com = new ArrayList<>();
				for (Committee c : committee) {
					c.getCommittee().setDocument(document);
				}

				try {
					committeeRepo.saveAll(committee);
				} catch (Exception e) {
					committee_result = 0;
					e.printStackTrace();
				}
			} catch (Exception e) {
			}

			String message = "";
			int result_add_check = 0;
			if (result != 0 && check_add_item_request == ril_size && committee_result == 1) {
				message = "บันทึกข้อมูลเอกสารเสนอซื้อ/จ้างสําเร็จ";
				result_add_check = 1;
			} else {
				System.out.println("check_add_item_request" + check_add_item_request + " ril_size" + ril_size + " cr"
						+ committee_result + " result" + result);
				message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลเอกสารเสนอซื้อ/จ้างได้ !";
			}

			session.setAttribute("messages", message);

			// mav = do_GetPurchaseOrderRequestDocument(request, session, md,
			// document.getDoc_id());
			mav = do_loadListDocumentPage(request, session, md);

			mav.addObject("message", message);
			mav.addObject("result_add_check", result_add_check);
			mav.addObject("doc_type", "1");
			mav.addObject("doc_id", doc_id);

			session.removeAttribute("document");
			session.removeAttribute("date");
			session.removeAttribute("item_cart");
			session.removeAttribute("committees");
			session.removeAttribute("doc_1_1");
			session.removeAttribute("doc_1_2");
			session.removeAttribute("doc_1_3");
			session.removeAttribute("doc_1_4");
			session.removeAttribute("committee_list");
			session.removeAttribute("check_type");
			session.removeAttribute("repair");
			session.removeAttribute("item_vat");
			session.removeAttribute("store_check");
			session.removeAttribute("vats");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/do_loadEditPurchaseOrderRequestPage")
	public ModelAndView do_loadEditPurchaseOrderRequestPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			Document purchaseOrderRequest = documentRepo.get_purchaseOrderRequestDocument(doc_id);
			List<RequestOrderItemList> item_cart = purchaseOrderRequest.getRequisitionItemLists();
			List<Committee> committee_list_old = purchaseOrderRequest.getCommittee();

			List<Personnel> committee_list = new ArrayList<>();
			List<PersonnelAssign> request_order_1 = new ArrayList<>();
			List<PersonnelAssign> request_order_2 = new ArrayList<>();
			List<PersonnelAssign> request_order_3 = new ArrayList<>();
			List<PersonnelAssign> request_order_4 = new ArrayList<>();

			committee_list = personnelRepo.get_personnelByMajor(staffDetail.getMajor().getID_Major());

			request_order_1 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_1",
					staffDetail.getMajor().getID_Major());
			request_order_2 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_2",
					staffDetail.getMajor().getID_Major());
			request_order_3 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_3",
					staffDetail.getMajor().getID_Major());
			request_order_4 = personnelAssignRepo.get_personnelByAssignTypeAndMajor("doc_1_4",
					staffDetail.getMajor().getID_Major());

			session.setAttribute("committee_list", committee_list);
			session.setAttribute("committee_update", committee_list_old);

			session.setAttribute("doc_1_1", request_order_1);
			session.setAttribute("doc_1_2", request_order_2);
			session.setAttribute("doc_1_3", request_order_3);
			session.setAttribute("doc_1_4", request_order_4);

			Double total_repair_price = 0.0;
			for (RequestOrderItemList rol : item_cart) {
				total_repair_price += rol.getTotal_price();
			}
			session.setAttribute("total_price_repair_update", total_repair_price);

			// String date = purchaseOrderRequest.getDates().toString().replaceAll("
			// 00:00:00.0", "");
			String date = purchaseOrderRequest.getDoc_date().toString().split(" ")[0];

			session.setAttribute("document", purchaseOrderRequest);
			mav.addObject("date", date);

			String check_type = "i";
			String store_check = "";

			if (purchaseOrderRequest.getVat_check().equals("novatotherstore")) {
				store_check = "1";
				session.setAttribute("store_check", store_check);
			} else if (purchaseOrderRequest.getVat_check().equals("repair")) {
				check_type = "r";
			} else if (purchaseOrderRequest.getVat_check().equals("novat")) {
				store_check = "2";
				session.setAttribute("store_check", store_check);
			} else {
				store_check = "2";

				String[] vat_check = purchaseOrderRequest.getVat_check().split(":");

				if (vat_check[0].equals("vat") && vat_check[1].equals("itemvatinclude")) {
					session.setAttribute("vats", "1");
					session.setAttribute("item_vat", "1");
				} else {
					session.setAttribute("vats", "1");

				}
				session.setAttribute("store_check", store_check);
			}

			session.setAttribute("check_type", check_type);

			if (check_type.equals("i")) {
				session.setAttribute("item_cart_update", item_cart);
			} else {
				session.setAttribute("repair_update", item_cart);
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_searchItemDocument_update")
	public ModelAndView do_searchItemDocument_update(ServletRequest request, HttpSession session, Model md,
			String item_name_s, String item_category_s) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		Item item = new Item();
		List<Item> items = new ArrayList<>();
		try {
			request.setCharacterEncoding("UTF-8");
			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			if (item_name_s == null && item_category_s == null) {

				String item_category = request.getParameter("item_category_search");
				String item_name = request.getParameter("item_name_search");

				item.setItem_name(item_name);
				item.setItem_category(item_category);

				if (item.getItem_category().equalsIgnoreCase("-") && item.getItem_name() != "") {
					items = itemRepo.get_search_item_byName(item.getItem_name());
				} else if (!item.getItem_category().equalsIgnoreCase("-") && item.getItem_name() == "") {
					items = itemRepo.get_search_item_byCategory(item.getItem_category());
				} else {
					items = itemRepo.get_search_item(item.getItem_name(), item.getItem_category());
				}

				mav.addObject("item_search_doc", item);
				session.setAttribute("item_search_doc", item);

			} else {

				Item item_search = new Item();
				item_search.setItem_name(item_name_s);
				item_search.setItem_category(item_category_s);

				if (item_search.getItem_category().equalsIgnoreCase("-") && item_search.getItem_name() != "") {
					items = itemRepo.get_search_item_byName(item_search.getItem_name());
				} else if (!item_search.getItem_category().equalsIgnoreCase("-") && item_search.getItem_name() == "") {
					items = itemRepo.get_search_item_byCategory(item_search.getItem_category());
				} else {
					items = itemRepo.get_search_item(item_search.getItem_name(), item_search.getItem_category());
				}

				mav.addObject("item_search_doc", item_search);
				session.setAttribute("item_search_doc", item_search);
			}

			mav.addObject("items", items);
			mav.addObject("itemSize", 0);

			List<RequestOrderItemList> request_order_item_list = new ArrayList<>();

			if (session.getAttribute("item_cart_update") != null) {
				request_order_item_list = (List<RequestOrderItemList>) session.getAttribute("item_cart_update");
				String[] item_price = request.getParameterValues("item_price");
				String[] item_total = request.getParameterValues("item_total");

				int i = 0;
				for (RequestOrderItemList rol : request_order_item_list) {

					if (item_price[i] == null) {
						item_price[i] = "0";
					}
					if (item_total[i] == null) {
						item_total[i] = "0";
					}

					rol.setAmount(Integer.parseInt(item_total[i]));
					rol.setItem_price(Double.parseDouble(item_price[i]));

					i++;
				}

				session.setAttribute("item_cart_update", request_order_item_list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addItemCart_update")
	public ModelAndView do_addItemCart_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			String item_id = request.getParameter("item_id");

			Item item = itemRepo.get_ItemByID(item_id);
			List<RequestOrderItemList> itemsCart = new ArrayList<>();
			RequestOrderItemListID rid = new RequestOrderItemListID();
			RequestOrderItemList rl = new RequestOrderItemList();
			String message = "";

			boolean check_duplicate = false;

			if (session.getAttribute("item_cart_update") == null) {
				rid.setItem(item);
				rl.setId(rid);
				rl.setAmount(1);
				itemsCart.add(rl);
				session.setAttribute("item_cart_update", itemsCart);
				message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
			} else {
				itemsCart = (List<RequestOrderItemList>) session.getAttribute("item_cart_update");
				for (RequestOrderItemList i : itemsCart) {
					if (i.getId().getItem().getItem_id().equals(item_id)) {
						check_duplicate = true;
						break;
					}
				}

				if (check_duplicate == false) {
					rid.setItem(item);
					rl.setId(rid);
					itemsCart.add(rl);
					message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
				} else {
					message = "รายการเสนอซื้อซํ้า !!";
				}
			}
			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			session.setAttribute("item_cart_update", itemsCart);
			mav.addObject("message", message);
			System.out.println(itemsCart.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_deleteItemCart_update")
	public ModelAndView do_deleteItemCart_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			request.setCharacterEncoding("UTF-8");
			String item_id = request.getParameter("item_id");
			String message = "";
			List<RequestOrderItemList> item_cart = (List<RequestOrderItemList>) session
					.getAttribute("item_cart_update");
			int i = 0;
			for (RequestOrderItemList ic : item_cart) {
				if (ic.getId().getItem().getItem_id().equals(item_id)) {
					item_cart.remove(i);
					message = "ลบรายการ " + ic.getId().getItem().getItem_name() + " เรียบร้อย";
					break;
				}
			}

			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			mav.addObject("message", message);

			session.setAttribute("item_cart_update", item_cart);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_editPurchaseOrderRequest")
	public ModelAndView do_editPurchaseOrderRequest(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDocumentPage");
		int result_committee = 1;
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			String plan_id = request.getParameter("plan_id");
			String plan_name = request.getParameter("plan_name");
			String depart_id = request.getParameter("depart_id");
			String depart_name = request.getParameter("depart_name");
			String fund_id = request.getParameter("fund_id");
			String fund_name = request.getParameter("fund_name");
			String money_used = request.getParameter("money_used");
			String work_name = request.getParameter("work_name");
			String budget = request.getParameter("budget");
			String date = request.getParameter("date");
			String doc_title = request.getParameter("doc_title");
			String doc_dear = request.getParameter("doc_dear");
			String doc_title_describe = request.getParameter("doc_title_describe");
			String doc_reason_describe = request.getParameter("doc_reason_describe");
			String doc_status = "รอการอนุมัติ";

			String store_check = request.getParameter("store_check");
			String store_name = request.getParameter("store_name");
			String vat_check = null;
			String item_vat = null;

			if (store_check != null) {
				if (store_check.equals("2")) {
					vat_check = request.getParameter("vats");
					System.out.println("vate_check" + vat_check);
				}
			}

			if (vat_check != null) {
				item_vat = request.getParameter("item_vat");
			}

			String[] item_id = request.getParameterValues("item_id");
			String[] item_total = request.getParameterValues("item_total");
			String[] item_total_price = request.getParameterValues("item_total_price");
			String[] item_price = request.getParameterValues("item_price");

			String price_txt = request.getParameter("price_txt");

			int budget_year = 0;
			if (Integer.parseInt(date.split("-")[1]) >= 10) {
				budget_year = (Integer.parseInt(date.split("-")[0]) + 1);
			} else {
				budget_year = Integer.parseInt(date.split("-")[0]);
			}
			
			System.out.println("=====> this is budget year" +budget_year+" "+date.split("-")[1]);
			System.out.println(date);
			Date doc_date = new Date();
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				doc_date = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			int result = 0;

			// add Assign Signature
			String requisition_old = request.getParameter("doc_1_1");
			String cof_old = request.getParameter("doc_1_2");
			String acc_old = request.getParameter("doc_1_3");
			String old_secretary = request.getParameter("doc_1_4");

			if (cof_old == null) {
				cof_old = "PER20";
			}

			// end assign signature

			// add document
			Document d_check_status = documentRepo.get_purchaseOrderRequestDocument(doc_id);

			if (!d_check_status.getStatus().equals("รอการอนุมัติ")
					&& !d_check_status.getStatus().equals("กรุณาแก้ไขเอกสาร")) {
				doc_status = d_check_status.getStatus();
			}
			System.out.println(doc_status);

			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
					money_used, work_name, budget, doc_date, doc_title, doc_dear, doc_title_describe, "",
					doc_reason_describe, doc_status);
			document.setAccounting_officer(acc_old);
			document.setChief_of_procurement(cof_old);
			document.setRequest_order_person(requisition_old);
			document.setSecretary(old_secretary);

			Double total_price = 0.0;
			Double total_price_with_out_tax = 0.0;
			Double tax = 0.0;

			List<RequestOrderItemList> rols_repair = new ArrayList<>();
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String check_type = request.getParameter("check_type");
			if (check_type.equalsIgnoreCase("i")) {

				for (int k = 0; k < item_id.length; k++) {
					total_price += Double.parseDouble(item_total_price[k]);
				}

				if (vat_check != null) {
					if (item_vat != null) {
						total_price_with_out_tax = total_price / 1.07;
						tax = total_price_with_out_tax * 0.07;
						vat_check = "vat:itemvatinclude";
					} else {

						total_price_with_out_tax = total_price;
						tax = total_price * 0.07;
						total_price = total_price_with_out_tax + tax;
						vat_check = "vat:itemvatunclude";
					}
				} else {
					total_price_with_out_tax = total_price;
					tax = total_price;
					if (store_check.equals("1")) {
						vat_check = "novatotherstore";
					} else {
						vat_check = "novat";
					}

				}
			} else {

				price_txt = request.getParameter("price_txt_repair");
				rols_repair = (List<RequestOrderItemList>) session.getAttribute("repair_update");
				for (RequestOrderItemList r : rols_repair) {

					total_price += r.getTotal_price();
				}
				vat_check = "repair";
			}

			document.setStore_name(store_name);
			document.setVat_check(vat_check);

			document.setPrice_txt(price_txt);
			document.setTotal_price(total_price);
			document.setPrice_with_out_tax(Double.parseDouble(decimalFormat.format(total_price_with_out_tax)));
			document.setTax(Double.parseDouble(decimalFormat.format(tax)));

			try {
				result = documentRepo.update_Document(document.getDoc_id(), document.getAccounting_officer(),
						document.getBudget(), document.getChief_of_procurement(), date, document.getDepart_id(),
						document.getDepart_name(), document.getDoc_dear(), document.getDoc_reason_describe(),
						document.getDoc_reason_title(), document.getDoc_title(), document.getDoc_title_decribe(),
						document.getFund_id(), document.getFund_name(), document.getMoney_used(), document.getPlan_id(),
						document.getPlan_name(), document.getPrice_txt(), document.getRequest_order_person(),
						document.getSecretary(), document.getStatus(), document.getWork_name(),
						document.getTotal_price(), document.getPrice_with_out_tax(), document.getTax(),
						document.getStore_name(), document.getVat_check());

				requestOrderRepo.delete_RequestItem(doc_id);

				List<RequestOrderItemList> ril = new ArrayList<RequestOrderItemList>();
				if (request.getParameter("check_type").equalsIgnoreCase("i")) {
					int request_item_count = requestOrderRepo.get_countRequestOrderItem();
					for (int i = 0; i < item_id.length; i++) {
						request_item_count += 1;
						Item item = new Item();
						item = itemRepo.get_ItemByID(item_id[i]);

						RequestOrderItemListID rid = new RequestOrderItemListID();
						rid.setItem(item);
						rid.setDocument(document);
						rid.setRequestOrderItemList_id("I" + request_item_count);

						RequestOrderItemList rols = new RequestOrderItemList();
						rols.setItem_price(Double.parseDouble(item_price[i]));
						rols.setTotal_price(Double.parseDouble(item_total_price[i]));
						rols.setAmount(Integer.parseInt(item_total[i]));
						rols.setId(rid);
						rols.setBudget_year(budget_year);
						ril.add(rols);

						requestOrderRepo.add_itemRequestItem_firstDoc(rols.getAmount(), rols.getTotal_price(),
								rols.getId().getDocument().getDoc_id(), rols.getId().getItem().getItem_id(),
								rols.getItem_price(), rols.getId().getRequestOrderItemList_id(),
								rols.getBudget_year());
					}
				} else {

					int repair_count = requestOrderRepo.get_countRequestOrderItem();
					String item_category = "";
					if (document.getFund_name().equalsIgnoreCase("สินทรัพย์ถาวร")) {
						item_category = "หมวดวัสดุสินทรัพย์ถาวร";
					} else {
						item_category = "หมวดบํารุงรักษาสินทรัพย์";
					}

					rols_repair = (List<RequestOrderItemList>) session.getAttribute("repair_update");
					for (RequestOrderItemList r : rols_repair) {
						System.out.println(r.getId().getItem().getItem_id());
						if (r.getId().getItem().getItem_id() == null || r.getId().getItem().getItem_id() == "") {
							int item_id_c = itemRepo.count_Item();

							String item_ids = "RP" + (item_id_c + 1) + "";
							itemRepo.insert_Item(item_ids, r.getId().getItem().getItem_name(),
									r.getId().getItem().getItem_price(), r.getId().getItem().getItem_unit(), "",
									item_category);
							Item item = new Item();
							item.setItem_id(item_ids);
							RequestOrderItemListID id = new RequestOrderItemListID();
							id.setItem(item);
							r.setId(id);
						} else {
							itemRepo.update_Item(r.getId().getItem().getItem_id(), r.getId().getItem().getItem_name(),
									r.getId().getItem().getItem_price(), r.getId().getItem().getItem_unit(), "",
									item_category);

						}
						repair_count += 1;
						requestOrderRepo.add_itemRequestItem_firstDoc(r.getAmount(), r.getTotal_price(),
								document.getDoc_id(), r.getId().getItem().getItem_id(), r.getItem_price(),
								"R" + repair_count, budget_year);
					}
				}

				List<Committee> committee = (List<Committee>) session.getAttribute("committee_update");

				committeeRepo.delete_CommitteeFromDocument(doc_id);

				for (Committee c : committee) {
					c.getCommittee().setDocument(document);
				}
				try {
					committeeRepo.saveAll(committee);
				} catch (Exception e) {
					result_committee = 0;
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			String message = "";
			int result_add_check = 0;
			if (result != 0 && result_committee != 0) {
				message = "แก้ไขข้อมูลเอกสารเสนอซื้อ/จ้างสําเร็จ";
				result_add_check = 1;
			} else {
				message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลเอกสารเสนอซื้อ/จ้างได้ !";
			}

			mav = do_loadListDocumentPage(request, session, md);

			mav.addObject("message", message);
			mav.addObject("result_add_check", result_add_check);
			mav.addObject("doc_type", "1");
			mav.addObject("doc_id", doc_id);

			session.removeAttribute("item_cart_update");
			session.removeAttribute("repair_update");
			session.removeAttribute("total_price_repair_update");
			session.removeAttribute("date");
			session.removeAttribute("committee_update");
			session.removeAttribute("document");
			session.removeAttribute("vats");
			session.removeAttribute("item_vat");
			session.removeAttribute("store_check");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addRepairList")
	public ModelAndView do_addRepairList(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");

		try {

			String repair_detail = request.getParameter("repair_detail");
			int repair_amount = Integer.parseInt(request.getParameter("repair_amount"));
			String repair_unit = request.getParameter("repair_unit");
			Double repair_price = Double.parseDouble(request.getParameter("repair_price"));

			Item item = new Item("", repair_detail, repair_unit, repair_price, "", "");

			RequestOrderItemListID rid = new RequestOrderItemListID();
			RequestOrderItemList riol = new RequestOrderItemList();
			rid.setItem(item);

			List<RequestOrderItemList> riols = new ArrayList<>();

			riol.setId(rid);
			riol.setAmount(repair_amount);
			riol.setTotal_price(repair_price * repair_amount);
			riol.setItem_price(repair_price);

			if (session.getAttribute("repair") != null) {
				riols = (List<RequestOrderItemList>) session.getAttribute("repair");
				riols.add(riol);
			} else {
				riols.add(riol);
			}

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}

			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair", total_price);
			session.setAttribute("repair", riols);
			mav.addObject("message", "เพิ่มรายการเสนอซ่อมเรียบร้อย");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_addRepairList_update")
	public ModelAndView do_addRepairList_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");

		try {

			String repair_detail = request.getParameter("repair_detail");
			int repair_amount = Integer.parseInt(request.getParameter("repair_amount"));
			String repair_unit = request.getParameter("repair_unit");
			Double repair_price = Double.parseDouble(request.getParameter("repair_price"));

			Item item = new Item("", repair_detail, repair_unit, repair_price, "", "");

			RequestOrderItemListID rid = new RequestOrderItemListID();
			RequestOrderItemList riol = new RequestOrderItemList();
			rid.setItem(item);

			List<RequestOrderItemList> riols = new ArrayList<>();

			riol.setId(rid);
			riol.setAmount(repair_amount);
			riol.setTotal_price(repair_price * repair_amount);
			riol.setItem_price(repair_price);

			if (session.getAttribute("repair_update") != null) {
				riols = (List<RequestOrderItemList>) session.getAttribute("repair_update");
				riols.add(riol);
			} else {
				riols.add(riol);
			}

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}

			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair_update", total_price);
			session.setAttribute("repair_update", riols);

			mav.addObject("message", "เพิ่มรายการเสนอซ่อมเรียบร้อย");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/deleteRepair")
	public ModelAndView deleteRepair(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		try {
			int position = Integer.parseInt(request.getParameter("position"));

			List<RequestOrderItemList> riols = (List<RequestOrderItemList>) session.getAttribute("repair");
			riols.remove(position);

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}
			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair", total_price);

			mav.addObject("message", "ลบรายการสําเร็จ");
			session.setAttribute("repair", riols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/deleteRepair_update")
	public ModelAndView deleteRepair_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			int position = Integer.parseInt(request.getParameter("position"));

			List<RequestOrderItemList> riols = (List<RequestOrderItemList>) session.getAttribute("repair_update");
			riols.remove(position);

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}
			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair_update", total_price);

			mav.addObject("message", "ลบรายการสําเร็จ");
			session.setAttribute("repair_update", riols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_editRepair_update")
	public ModelAndView do_editRepair_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			int position_edit = Integer.parseInt(request.getParameter("position_edit"));
			String repair_detail_edit = request.getParameter("repair_detail_edit");
			int repair_amount_edit = Integer.parseInt(request.getParameter("repair_amount_edit"));
			String repair_unit_edit = request.getParameter("repair_unit_edit");
			Double repair_price_edit = Double.parseDouble(request.getParameter("repair_price_edit"));

			List<RequestOrderItemList> riols = (List<RequestOrderItemList>) session.getAttribute("repair_update");
			Item item = new Item("", repair_detail_edit, repair_unit_edit, repair_price_edit, "", "");
			riols.get(position_edit).setAmount(repair_amount_edit);
			riols.get(position_edit).getId().setItem(item);
			riols.get(position_edit).setItem_price(repair_price_edit);
			riols.get(position_edit).setTotal_price(repair_price_edit * repair_amount_edit);

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}

			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair_update", total_price);

			session.setAttribute("repair_update", riols);

			mav.addObject("message", "แก้ไขรายการเสนอซ่อมสําเร็จ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_editRepair")
	public ModelAndView do_editRepair(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		try {
			int position_edit = Integer.parseInt(request.getParameter("position_edit"));
			String repair_detail_edit = request.getParameter("repair_detail_edit");
			int repair_amount_edit = Integer.parseInt(request.getParameter("repair_amount_edit"));
			String repair_unit_edit = request.getParameter("repair_unit_edit");
			Double repair_price_edit = Double.parseDouble(request.getParameter("repair_price_edit"));

			List<RequestOrderItemList> riols = (List<RequestOrderItemList>) session.getAttribute("repair");
			Item item = new Item("", repair_detail_edit, repair_unit_edit, repair_price_edit, "", "");
			riols.get(position_edit).setAmount(repair_amount_edit);
			riols.get(position_edit).getId().setItem(item);
			riols.get(position_edit).setItem_price(repair_price_edit);
			riols.get(position_edit).setTotal_price(repair_price_edit * repair_amount_edit);

			Double total_price = 0.0;
			for (RequestOrderItemList r : riols) {
				total_price += r.getTotal_price();
			}

			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
			session.setAttribute("total_price_repair", total_price);

			session.setAttribute("repair", riols);

			mav.addObject("message", "แก้ไขรายการเสนอซ่อมสําเร็จ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "print_purchaseOrderRequestV")
	public void print_purchaseOrderRequestV(ServletRequest request, HttpSession session, Model md,
			ServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			String doc_id = request.getParameter("doc_id");
			print_purchaseOrderRequest(request, session, md, response, doc_id);
		} catch (Exception e) {

		}

	}

	@PostMapping(value = "print_purchaseOrderRequest")
	public void print_purchaseOrderRequest(ServletRequest request, HttpSession session, Model md,
			ServletResponse response, String doc_ids) {
		try {
			request.setCharacterEncoding("UTF-8");
			Connection conn = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sciinstservicedb?characterEncoding=UTF-8",
					"root", "Fluke123");

			String doc_id = request.getParameter("doc_id");
			if (doc_ids != null) {
				doc_id = doc_ids;
			}
			String path = request.getServletContext().getRealPath("/");
			JasperReportsContext jrContext = DefaultJasperReportsContext.getInstance();
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNew.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.font.name",
					path + "file/font/THSarabunNewBold.ttf");
			JRPropertiesUtil.getInstance(jrContext).setProperty("net.sf.jasperreports.default.pdf.encoding",
					"Identity-H");

			Document doc = documentRepo.get_purchaseOrderRequestDocument(doc_id);
			File file = null;
			if (!doc.getDepart_name().equalsIgnoreCase("คณะวิทยาศาสตร์")) {
				if (doc.getVat_check().equals("novat") || doc.getVat_check().equals("repair")
						|| doc.getVat_check().equals("novatotherstore")) {
					file = ResourceUtils
							.getFile(path + "file/iReport/PurchaseOrderRequestTemplateComplete_Major_no_vat.jrxml");
				} else {
					file = ResourceUtils
							.getFile(path + "file/iReport/PurchaseOrderRequestTemplateComplete_Major.jrxml");
				}

			} else {
				if (doc.getVat_check().equals("novat") || doc.getVat_check().equals("repair")
						|| doc.getVat_check().equals("novatotherstore")) {
					file = ResourceUtils
							.getFile(path + "file/iReport/PurchaseOrderRequestTemplateComplete_Faculty_no_vat.jrxml");
				} else {
					file = ResourceUtils
							.getFile(path + "file/iReport/PurchaseOrderRequestTemplateComplete_Faculty.jrxml");
				}
			}
			
//			file = ResourceUtils
//					.getFile(path + "file/iReport/F001-test.jrxml");
//			System.out.println("PATH :::::" + path+"file/iReport/F001-test.jrxml");
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			Map<String, Object> map = new HashMap<>();
			map.put("id", "E001/2564");
			map.put("image_path", path + "file/iReport/logosci.jpg");

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

	// committee_part
	@PostMapping(value = "/do_addCommittee")
	public ModelAndView do_addCommittee(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String committee_id = request.getParameter("committee");

			Personnel personnel = new Personnel();
			CommitteeID committeeID = new CommitteeID();
			Committee committee = new Committee();
			List<Committee> committee_list = new ArrayList<>();
			
			if (session.getAttribute("committees") != null) {
				committee_list = (List<Committee>) session.getAttribute("committees");
				if(committee_list.size() != 0) {
					committee_list.get(0).setCommittee_status("ประธานกรรมการ");
				}
			}

			personnel = personnelRepo.get_personnelByID(committee_id);
			committeeID.setPersonnel(personnel);
			committee.setCommittee(committeeID);
			committee.setCommittee_status("กรรมการ");

			committee_list.add(committee);
			message = "เพิ่มรายชื่อกรรมการสําเร็จ";

			session.setAttribute("committees", committee_list);
		} catch (Exception e) {
			e.printStackTrace();
			message = "เพิ่มรายชื่อกรรมการไม่สําเร็จ";
		}

		List<RequestOrderItemList> request_order_item_list = new ArrayList<>();
		try {
			if (session.getAttribute("item_cart") != null) {
				request_order_item_list = (List<RequestOrderItemList>) session.getAttribute("item_cart");
				String[] item_price = request.getParameterValues("item_price");
				String[] item_total = request.getParameterValues("item_total");

				int i = 0;
				for (RequestOrderItemList rol : request_order_item_list) {

					if (item_price[i] == null || item_price[i].equals("")) {
						item_price[i] = "0";
					}
					if (item_total[i] == null || item_total[i].equals("")) {
						item_total[i] = "0";
					}

					rol.setAmount(Integer.parseInt(item_total[i]));
					rol.setItem_price(Double.parseDouble(item_price[i]));

					i++;
				}

				session.setAttribute("item_cart", request_order_item_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
		mav.addObject("message", message);

		return mav;
	}

	@PostMapping(value = "/do_removeCommittee")
	public ModelAndView do_removeCimittee(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
		try {
			request.setCharacterEncoding("UTF-8");

			String committee_index = request.getParameter("committee_index");
			List<Committee> committees = (List<Committee>) session.getAttribute("committees");
			committees.remove(Integer.parseInt(committee_index));

			if (committees.size() == 1) {
				committees.get(0).setCommittee_status("กรรมการ");
			} else {
				if (committees.size() != 0) {
					if (committee_index.equals("0")) {
						committees.get(0).setCommittee_status("ประธานกรรมการ");
					}
				}
			}

			String message = "นํากรรมการออกเสร็จสิ้น";

			mav = save_doc_session(request, session, md, "addPurchaseOrderRequestPage");
			mav.addObject("message", message);
			session.setAttribute("committees", committees);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addCommittee_old_update")
	public ModelAndView do_addCommittee_old_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String committee_id = request.getParameter("committee");

			Personnel personnel = new Personnel();
			CommitteeID committeeID = new CommitteeID();
			Committee committee = new Committee();
			List<Committee> committee_list = (List<Committee>) session.getAttribute("committee_update");

			if (committee_list.size() != 0) {
				committee_list = (List<Committee>) session.getAttribute("committee_update");
				committee_list.get(0).setCommittee_status("ประธานกรรมการ");
			}

			personnel = personnelRepo.get_personnelByID(committee_id);
			committeeID.setPersonnel(personnel);
			committee.setCommittee(committeeID);
			committee.setCommittee_status("กรรมการ");

			committee_list.add(committee);
			message = "เพิ่มรายชื่อกรรมการสําเร็จ";

			session.setAttribute("committee_update", committee_list);
		} catch (Exception e) {
			message = "เพิ่มรายชื่อกรรมการไม่สําเร็จ";
		}

		try {
			List<RequestOrderItemList> request_order_item_list = new ArrayList<>();
			if (session.getAttribute("item_cart_update") != null) {
				request_order_item_list = (List<RequestOrderItemList>) session.getAttribute("item_cart_update");
				String[] item_price = request.getParameterValues("item_price");
				String[] item_total = request.getParameterValues("item_total");

				int i = 0;
				for (RequestOrderItemList rol : request_order_item_list) {

					if (item_price[i] == null) {
						item_price[i] = "0";
					}
					if (item_total[i] == null) {
						item_total[i] = "0";
					}

					rol.setAmount(Integer.parseInt(item_total[i]));
					rol.setItem_price(Double.parseDouble(item_price[i]));

					i++;
				}

				session.setAttribute("item_cart_update", request_order_item_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
		mav.addObject("message", message);

		return mav;
	}

	@PostMapping(value = "/do_removeCommittee_update")
	public ModelAndView do_removeCommittee_update(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
		try {
			request.setCharacterEncoding("UTF-8");

			String committee_index = request.getParameter("committee_index");
			List<Committee> committees = (List<Committee>) session.getAttribute("committee_update");
			committees.remove(Integer.parseInt(committee_index));

			if (committees.size() == 1) {
				committees.get(0).setCommittee_status("กรรมการ");
			} else {
				if (committee_index.equals("0")) {
					committees.get(0).setCommittee_status("ประธานกรรมการ");
				}
			}

			String message = "นํากรรมการออกเสร็จสิ้น";
			mav = save_doc_session(request, session, md, "editPurchaseOrderRequestPage");
			mav.addObject("message", message);
			session.setAttribute("committee_update", committees);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}
