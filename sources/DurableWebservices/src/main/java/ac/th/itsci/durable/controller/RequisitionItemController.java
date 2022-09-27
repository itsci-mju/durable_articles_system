package ac.th.itsci.durable.controller;

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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.AssignType;
import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Item;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.Position;
import ac.th.itsci.durable.entity.RequestOrderItemList;
import ac.th.itsci.durable.entity.RequestOrderItemListID;
import ac.th.itsci.durable.entity.RequisitionDocument;
import ac.th.itsci.durable.entity.RequisitionItem;
import ac.th.itsci.durable.entity.RequisitionItemID;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.DocumentRepository;
import ac.th.itsci.durable.repo.ItemRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;
import ac.th.itsci.durable.repo.RequestOrderItemListRepository;
import ac.th.itsci.durable.repo.RequisitionDocumentRepository;
import ac.th.itsci.durable.repo.RequisitionItemRepository;

@Controller
public class RequisitionItemController {

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	DocumentRepository documentRepo;

	@Autowired
	ItemRepository itemRepo;

	@Autowired
	RequestOrderItemListRepository requestOrderItemListRepo;

	@Autowired
	RequisitionItemRepository requisitionItemRepo;

	@Autowired
	RequestOrderItemListRepository rolRepo;

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	RequisitionDocumentRepository requisitionDocRepo;

	@PostMapping(value = "/do_loadRequisitionDetail")
	public ModelAndView do_loadRequisitionDetail(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listRequisitionDetail");

		List<RequisitionItem> requistion_detail = new ArrayList<>();
		List<RequestOrderItemList> year_count = new ArrayList<>();

		RequisitionManager rm = new RequisitionManager();
		try {
			request.setCharacterEncoding("UTF-8");
			String itemID = request.getParameter("itemID");
			String item_price = request.getParameter("item_price");
			int year = Integer.parseInt(request.getParameter("year"));
			Staff staff = (Staff) session.getAttribute("staffSession");
			requistion_detail = rm.get_requisition_detail((year - 543), itemID, Double.parseDouble(item_price),
					staff.getMajor().getMajor_Name());

			int i = 0;
			int total_balance = 0;
			String check_dup_year = "";
			for (RequisitionItem r : requistion_detail) {
				total_balance = r.getRequisition_total_balance();
				if (!check_dup_year.equals(r.getPk().getRequisition().getRequisition_budget_year() + "/"
						+ r.getPk().getRequestOrderItemList().getBudget_year()) || i == 0) {
					check_dup_year = r.getPk().getRequisition().getRequisition_budget_year() + "/"
							+ r.getPk().getRequestOrderItemList().getBudget_year();

					System.out.println("c year " + check_dup_year);
					int amount = 0;

					amount = (rm.get_max_total_balance(r.getPk().getRequisition().getRequisition_budget_year(),
							r.getPk().getRequestOrderItemList().getId().getItem().getItem_id(),
							r.getPk().getRequestOrderItemList().getId().getDocument().getDoc_id(),
							r.getPk().getRequestOrderItemList().getId().getRequestOrderItemList_id(),
							r.getPk().getRequestOrderItemList().getBudget_year(), staff.getMajor().getMajor_Name(),
							"จัดทําใบเบิกพัสดุ สําเร็จ", r.getPk().getRequestOrderItemList().getItem_price()));

					RequestOrderItemList requestOrderItemList = new RequestOrderItemList();

					requestOrderItemList.setItem_price(r.getPk().getRequestOrderItemList().getItem_price());
					requestOrderItemList.setAmount(amount);
					requestOrderItemList.setTotal_price(amount * requestOrderItemList.getItem_price());
					requestOrderItemList.setBudget_year(r.getPk().getRequestOrderItemList().getBudget_year());

					List<RequisitionItem> requistion_detail_by_year = new ArrayList<>();

					requistion_detail_by_year = rm.get_requisition_detail_by_request_year(
							r.getPk().getRequisition().getRequisition_budget_year(),
							r.getPk().getRequestOrderItemList().getId().getItem().getItem_id(),
							r.getPk().getRequestOrderItemList().getId().getDocument().getDoc_id(),
							Double.parseDouble(item_price), r.getPk().getRequestOrderItemList().getBudget_year(),
							staff.getMajor().getMajor_Name());

					System.out.println("requistion_detail_by_year :: " + requistion_detail_by_year.size());

					requestOrderItemList.setRequisitionItem(requistion_detail_by_year);
					year_count.add(requestOrderItemList);
				}
				i++;
			}

			System.out.println("Year_count_size :: " + year_count.size());
			for (RequestOrderItemList y : year_count) {
				int amount_old = 0;
				double total_price_balance = 0.0;
				int k = 0;
				System.out.println("year :: " + y.getBudget_year() + " amount :: " + y.getAmount() + " price :: "
						+ y.getItem_price() + " total_price :: " + y.getTotal_price());
				for (RequisitionItem d : y.getRequisitionItem()) {
					if (k == 0) {
						amount_old = y.getAmount() - d.getRequisition_total();
					} else {
						amount_old = amount_old - d.getRequisition_total();
					}

					total_price_balance = y.getItem_price() * amount_old;

					d.setRequisition_total_balance(amount_old);
					d.setTotal_price_balance(total_price_balance);

//					System.out.println("date :: " + d.getPk().getRequisition().getRequisition_date());
//					System.out.println("personnel :: " + d.getPk().getPersonnel().getPersonnel_firstName() + " "
//							+ d.getPk().getPersonnel().getPersonnel_lastName());
//					System.out.println("r_id :: " + d.getPk().getRequisition().getRequisition_id());
//					System.out.println("i_price :: " + d.getPk().getRequestOrderItemList().getItem_price());
//					System.out.println("total :: " + d.getRequisition_total());
//					System.out.println("total_price :: " + d.getTotal_price_purchase());
//
//					System.out.println("total_balance :: " + d.getRequisition_total_balance());
//					System.out.println("***************************************************************************");

					k++;
				}
			}

			Item item = itemRepo.get_ItemByID(itemID);
			request.setAttribute("year_count", year_count);
			mav.addObject("item", item);
			mav.addObject("year", year - 543);
			// mav.addObject("year_count", year_count);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_SearchRequisition")
	public ModelAndView do_SearchRequisition(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		try {
			request.setCharacterEncoding("UTF-8");
			int year = (Integer.parseInt(request.getParameter("year_search")) - 543);
			System.out.println(year);
			mav = do_loadListRequisition(request, session, md, year + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_backToRequisitionList")
	public ModelAndView do_backToRequisitionList(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		try {
			request.setCharacterEncoding("UTF-8");
			String y = request.getParameter("y");
			System.out.println("year :: " + y);
			mav = do_loadListRequisition(request, session, md, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_backRequistionToRequisitionList")
	public ModelAndView do_backRequistionToRequisitionList(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		try {
			request.setCharacterEncoding("UTF-8");
			String y = request.getParameter("y");
			System.out.println("year :: " + y);
			mav = do_loadListRequisition(request, session, md, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/do_loadListRequisition")
	public ModelAndView do_loadListRequisition(ServletRequest request, HttpSession session, Model md,
			String year_search) {
		ModelAndView mav = new ModelAndView("listRequisitionPage");
		List<RequisitionItem> requisition_item = new ArrayList<>();
		List<String> amount = new ArrayList<>();
		try {
			request.setCharacterEncoding("UTF-8");
			Staff staffSession = (Staff) session.getAttribute("staffSession");
			String years = Calendar.getInstance().get(Calendar.YEAR) + "";
			int years_current = Calendar.getInstance().get(Calendar.YEAR);

			if (year_search != null) {
				years = year_search;
				if (years_current < Integer.parseInt(years)) {
					mav.addObject("over_year", "o");
				} else if (years_current > Integer.parseInt(years)) {
					mav.addObject("previus_year", "o");
				}
			}
			System.out.println(years + " :: " + year_search);
			RequisitionManager rm = new RequisitionManager();

			requisition_item = rm.get_listRequisition(staffSession.getMajor().getMajor_Name(), Integer.parseInt(years),
					"จัดทําใบเบิกพัสดุ สําเร็จ");
			System.out.println(requisition_item.size());
			for (RequisitionItem r : requisition_item) {
				String a = rm.get_amount_received(staffSession.getMajor().getMajor_Name(), Integer.parseInt(years),
						"จัดทําใบเบิกพัสดุ สําเร็จ", r.getPk().getRequestOrderItemList().getId().getItem().getItem_id(),
						r.getPk().getRequestOrderItemList().getItem_price()) + "";
				amount.add(a);
			}
//			for(RequisitionItem r : requisition_item) {
//				System.out.println("item_name : "+r.getPk().getRequestOrderItemList().getId().getItem().getItem_name());
//				System.out.println("item_price_main : "+r.getPk().getRequestOrderItemList().getId().getItem().getItem_price());
//				System.out.println(r.getPk().getRequestOrderItemList().getItem_price());
//				System.out.println(r.getPk().getRequestOrderItemList().getId().getItem().getItem_unit());
//				System.out.println(r.getPk().getRequestOrderItemList().getAmount_received());
//				System.out.println(r.getPk().getRequestOrderItemList().getAmount_balance());
//				System.out.println(r.getRequisition_total_balance());
//			}
			mav.addObject("get_amount_received", amount);
			mav.addObject("year_search", (Integer.parseInt(years) + 543));
			mav.addObject("requisition_item", requisition_item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_loadAddRequisition")
	public ModelAndView do_loadAddRequisition(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addRequisitionPage");
		List<RequisitionItem> requisition_item = new ArrayList<>();
		List<Personnel> personnel = new ArrayList<>();
		List<String> amount = new ArrayList<>();

		RequisitionManager rm = new RequisitionManager();

		try {
			Staff staffSession = (Staff) session.getAttribute("staffSession");
			personnel = personnelRepo.get_allPersonnel();

			String years = Calendar.getInstance().get(Calendar.YEAR) + "";
			requisition_item = rm.get_listRequisition(staffSession.getMajor().getMajor_Name(), Integer.parseInt(years),
					"จัดทําใบเบิกพัสดุ สําเร็จ");

			System.out.println(requisition_item.size());
			System.out.println("size :: " + requisition_item.size());

			mav.addObject("personnel", personnel);
			mav.addObject("requisition_item", requisition_item);
			mav.addObject("year_search", (Integer.parseInt(years) + 543));
			mav.addObject("get_amount_received", amount);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			request.setCharacterEncoding("UTF-8");
//			Staff staffSession = (Staff) session.getAttribute("staffSession");
//
//			int year = Calendar.getInstance().get(Calendar.YEAR);
//			int month = Calendar.getInstance().get(Calendar.MONTH);
//
//			requisition_item = requestOrderItemListRepo.get_listRequestOrderItemforRequisition(year,
//					staffSession.getMajor().getMajor_Name(), "repair", "จัดทําใบเบิกพัสดุ สําเร็จ");
//			personnel = personnelRepo.get_allPersonnel();
//
//			List<String> delete_index = new ArrayList<>();
//			for (RequestOrderItemList r : requisition_item) {
//				if (r.getAmount_balance() == 0 && r.getId().getDocument().getDoc_date().getYear() + 1900 != year) {
//					delete_index.add("true");
//				} else {
//					delete_index.add("false");
//				}
//			}
//
//			if (check_empty_stock == requisition_item.size()) {
//				mav.addObject("all_gone", "1");
//			}
//
//			mav.addObject("delete_index", delete_index);
//			mav.addObject("personnel", personnel);
//			mav.addObject("requisition_item", requisition_item);
//			mav.addObject("year_search", (year + 543));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return mav;
	}

	@PostMapping(value = "/do_addRequistion")
	public ModelAndView do_addRequistion(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView();
		int result = 0;
		RequisitionManager rqm = new RequisitionManager();
		RequisitionDocument requisitionDoc = new RequisitionDocument();
		List<RequisitionItem> requisition_item_list = new ArrayList<>();
		String message = "";
		try {
			Staff staff = (Staff) session.getAttribute("staffSession");
			request.setCharacterEncoding("UTF-8");
			String re_id = request.getParameter("re_id");
			String re_per = request.getParameter("re_per");
			String requisition_date = request.getParameter("requisition_date");

			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Date dateStart = null;
			String re_date_new = null;

			Date re_date = null;

			dateStart = simpleDateFormatThai.parse(requisition_date);
			re_date_new = simpledate.format(dateStart);
			re_date = simpledate.parse(re_date_new);

			int budget_year = 0;
			if ((re_date.getMonth() + 1) >= 10) {
				budget_year = ((re_date.getYear() + 1900) + 1);
			} else {
				budget_year = (re_date.getYear() + 1900);
			}

			String[] item_id = request.getParameterValues("item_id");
			String[] remain = request.getParameterValues("remain");
			String[] requisition_total = request.getParameterValues("requisition_total");
			String[] total_price_purchase = request.getParameterValues("total_price_purchase");
			String[] re_note = request.getParameterValues("re_note");
			String[] ip = request.getParameterValues("ip");
//			String[] receive_amount = request.getParameterValues("receive_amount");

			int last_balance = 0;
			int amount_balance = 0;
			double total_price_balance = 0.0;

			List<RequestOrderItemList> roil = new ArrayList<>();

			for (int i = 0; i < item_id.length; i++) {
				roil = requestOrderItemListRepo.get_request_order_item_by_amount(item_id[i], Double.parseDouble(ip[i]),
						0, staff.getMajor().getMajor_Name());
				int amount_item_item_balance = 0;
				int requisition_balance = 0;
				int requisition_total_actually_in_row = 0;
				int l = 0;
				System.out.println(roil.size());
				for (RequestOrderItemList r : roil) {

					RequestOrderItemListID requestOrderItemID = new RequestOrderItemListID();
					RequestOrderItemList requestOrderItemList = new RequestOrderItemList();
					RequisitionDocument requisitionDocument = new RequisitionDocument();
					RequisitionItemID requisitionItemId = new RequisitionItemID();
					RequisitionItem requisitionItem = new RequisitionItem();
					Item item = new Item();
					Personnel personnel = new Personnel();
					Document document = new Document();

					if (l == 0) {
						requisition_balance = Integer.parseInt(requisition_total[i]);
					}
					if (r.getAmount_balance() < requisition_balance) {
						if (l == 0) {
							requisition_balance = Integer.parseInt(requisition_total[i]) - r.getAmount_balance();
						} else {
							requisition_balance = requisition_balance - r.getAmount_balance();
						}
						requisition_total_actually_in_row = r.getAmount_balance();
						amount_item_item_balance = 0;
					} else {
						requisition_total_actually_in_row = requisition_balance;
						requisition_balance = r.getAmount_balance() - requisition_balance;
						amount_item_item_balance = requisition_balance;
					}

					System.out.println("requisition_balance :: " + requisition_balance);
					System.out.println("requisition_total_actually_in_row :: " + requisition_total_actually_in_row);
					System.out.println("amount_item :: " + amount_item_item_balance);

					last_balance = r.getAmount_balance();

					item.setItem_id(r.getId().getItem().getItem_id());
					personnel.setPersonnel_id(re_per);
					document.setDoc_id(r.getId().getDocument().getDoc_id());

					requestOrderItemID.setDocument(document);
					requestOrderItemID.setItem(item);
					requestOrderItemID.setRequestOrderItemList_id(r.getId().getRequestOrderItemList_id());

					requestOrderItemList.setId(requestOrderItemID);

					requisitionDocument.setRequisition_id(re_id);
					requisitionDocument.setRequisition_date(re_date);
					requisitionDocument.setRequisition_budget_year(budget_year);

					requisitionItemId.setRequestOrderItemList(requestOrderItemList);
					requisitionItemId.setPersonnel(personnel);
					requisitionItemId.setRequisition(requisitionDocument);

					requisitionItem.setPk(requisitionItemId);
					requisitionItem.setRequisition_total(requisition_total_actually_in_row);
					requisitionItem.setRequisition_total_balance(amount_item_item_balance);
					requisitionItem.setTotal_price_balance(total_price_balance);
					requisitionItem.setTotal_price_purchase(Double.parseDouble(total_price_purchase[i]));
					requisitionItem.setRequisition_note(re_note[i]);
					requisitionItem.setLast_balance(last_balance);
					requisition_item_list.add(requisitionItem);

					l++;
				}
//
//
//
//				roil = requestOrderItemListRepo.get_request_order_item_by_amount(item_id[i], Double.parseDouble(ip[i]),
//						0, staff.getMajor().getMajor_Name());
//
//				if (roil.get(0).getAmount_balance() < Integer.parseInt(requisition_total[i])) {
//
//					int amount_balance_for_loop = 0;
//					int breaks = 0;
//					int j = 0;
//					for (RequestOrderItemList r : roil) {
//						int requisition_total_s = 0;
//						RequestOrderItemListID requestOrderItemID_sum = new RequestOrderItemListID();
//						RequestOrderItemList requestOrderItemList_sum = new RequestOrderItemList();
//						RequisitionDocument requisitionDocument_sum = new RequisitionDocument();
//						RequisitionItemID requisitionItemId_sum = new RequisitionItemID();
//						RequisitionItem requisitionItem_sum = new RequisitionItem();
//						Item item_sum = new Item();
//						Personnel personnel_sum = new Personnel();
//						Document document_sum = new Document();
//
//						System.out.println("amount balance :: " + r.getAmount_balance());
//
//						if (j == 0) {
//							breaks = Integer.parseInt(requisition_total[i]) - r.getAmount_balance();
//							amount_balance_for_loop = Integer.parseInt(requisition_total[i]) - r.getAmount_balance();
//							amount_balance = 0;
//							requisition_total_s = r.getAmount_balance();
//						} else {
//							if (r.getAmount_balance() < amount_balance_for_loop) {
//								breaks -= amount_balance_for_loop;
//								amount_balance_for_loop = r.getAmount_balance() - amount_balance_for_loop;
//								amount_balance = amount_balance_for_loop;
//								requisition_total_s = r.getAmount_balance();
//							} else {
//								amount_balance_for_loop = r.getAmount_balance() - amount_balance_for_loop;
//							}
//						}
//
//						System.out.println("bresk :: " + breaks);
//
//						System.out.println("amount_loop :: " + amount_balance_for_loop);
////						amount_balance = amount_balance_for_loop;
//						total_price_balance = amount_balance * Double.parseDouble(ip[i]);
//						last_balance = r.getAmount_balance();
//
//						item_sum.setItem_id(roil.get(0).getId().getItem().getItem_id());
//						personnel_sum.setPersonnel_id(re_per);
//						document_sum.setDoc_id(r.getId().getDocument().getDoc_id());
//
//						requestOrderItemID_sum.setDocument(document_sum);
//						requestOrderItemID_sum.setItem(item_sum);
//						requestOrderItemID_sum.setRequestOrderItemList_id(r.getId().getRequestOrderItemList_id());
//
//						requestOrderItemList_sum.setId(requestOrderItemID_sum);
//
//						requisitionDocument_sum.setRequisition_id(re_id);
//						requisitionDocument_sum.setRequisition_date(re_date);
//						requisitionDocument_sum.setRequisition_budget_year(budget_year);
//
//						requisitionItemId_sum.setRequestOrderItemList(requestOrderItemList_sum);
//						requisitionItemId_sum.setPersonnel(personnel_sum);
//						requisitionItemId_sum.setRequisition(requisitionDocument_sum);
//
//						requisitionItem_sum.setPk(requisitionItemId_sum);
//						//
//						requisitionItem_sum.setRequisition_total(requisition_total_s);
//						requisitionItem_sum.setRequisition_total_balance(amount_balance);
//						requisitionItem_sum.setTotal_price_balance(total_price_balance);
//						requisitionItem_sum.setTotal_price_purchase(Double.parseDouble(total_price_purchase[i]));
//						requisitionItem_sum.setRequisition_note(re_note[i]);
//						requisitionItem_sum.setLast_balance(last_balance);
//						requisition_item_list.add(requisitionItem_sum);
//
//						if (breaks == 0) {
//							break;
//						}
//						j++;
//					}
//					System.out.println();
//					System.out.println("1");
//				} else {
//					amount_balance = roil.get(0).getAmount_balance() - Integer.parseInt(requisition_total[i]);
//					total_price_balance = amount_balance * Double.parseDouble(ip[i]);
//					last_balance = roil.get(0).getAmount_balance();
//
//
//
//					System.out.println("2");
//				}
			}

			result = rqm.insertRequisition(requisition_item_list, re_date_new);

			if (result > 0) {
				message = "บันทึกการเบิกวัสดุสําเร็จ";
			} else {
				message = "ข้อผิดพลาด :: ไม่สามารถบันทึกการเบิกวัสดุได้กรุณาลองใหม่อีกครั้ง";
			}

			mav = do_loadListRequisition(request, session, md, null);
			mav.addObject("message", message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

//	@PostMapping(value="/do_backRequistionToRequisitionList")
//	public ModelAndView do_backRequistion(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView();
//		try {
//			String doc_id_back = request.getParameter("doc_id_back");
//			mav = do_loadListRequisition(request, session, md, doc_id_back);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

//	@GetMapping(value = "/do_loadListRequisition")
//	public ModelAndView do_loadListRequisition(ServletRequest request, HttpSession session, Model md, String doc_id) {
//		ModelAndView mav = new ModelAndView("listRequisitionPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			if (doc_id == null) {
//				doc_id = request.getParameter("doc_id");
//			}
//
//			List<Personnel> personnel = personnelRepo.get_personnelRequisition();
//			Document document = new Document();
//			document.setDoc_id(doc_id);
//			document = documentRepo.get_purchaseOrderRequestDocument(document.getDoc_id());
//			String[] remain = new String[document.getRequisitionItemLists().size()];
//			int i = 0;
//			int checkAllRemain = 0;
//			for (RequestOrderItemList s : document.getRequisitionItemLists()) {
//				remain[i] = requisitionItemRepo.getLowestItemBalance(s.getId().getDocument().getDoc_id(),
//						s.getId().getItem().getItem_id());
//				if (remain[i] == null) {
//					remain[i] = s.getAmount_received() + "";
//				}
//				if(remain[i].equals("0")) {
//					checkAllRemain += 1;
//				}
//				i++;
//			}
//			
//			if(checkAllRemain == document.getRequisitionItemLists().size()) {
//				mav.addObject("all_gone","1");
//			}
//
//			mav.addObject("personnel", personnel);
//			mav.addObject("remain", remain);
//			mav.addObject("request_item", document.getRequisitionItemLists());
//			mav.addObject("doc", document);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_addRequistions")
//	public ModelAndView do_addRequistions(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("listRequisitionPage");
//		int result_doc = 0;
//		int result_item = 0;
//		String message = "";
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String doc_id = request.getParameter("doc_id");
//			String re_id = request.getParameter("re_id");
//			String re_per = request.getParameter("re_per");
//			String requisition_date = request.getParameter("requisition_date");
//
//			String[] item_id = request.getParameterValues("item_id");
//			String[] remain = request.getParameterValues("remain");
//			String[] requisition_total = request.getParameterValues("requisition_total");
//			String[] total_price_purchase = request.getParameterValues("total_price_purchase");
//			String[] re_note = request.getParameterValues("re_note");
//			String[] ip = request.getParameterValues("ip");
//
//			int requisition_total_balance = 0;
//			Double total_price_balance = 0.0;
//			Double total_price_purchases = 0.0;
//
//			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
//			Date dateStart = null;
//			String dateStirngStartNew = null;
//
//			Date dateStartNew = null;
//
//			dateStart = simpleDateFormatThai.parse(requisition_date);
//			dateStirngStartNew = simpledate.format(dateStart);
//			dateStartNew = simpledate.parse(dateStirngStartNew);
//
//			RequisitionDocument rd = new RequisitionDocument();
//			rd.setRequisition_id(re_id);
//			rd.setRequisition_date(dateStartNew);
//			
//			try {
//				result_doc = requisitionDocRepo.insert_requisition(rd.getRequisition_id(),
//						rd.getRequisition_date());
//
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		
//			Personnel p = new Personnel();
//			p.setPersonnel_id(re_per);
//
//			Document document = new Document();
//			document.setDoc_id(doc_id);
//			List<RequisitionItem> requisition_item = new ArrayList<>();
//			for (int i = 0; i < item_id.length; i++) {
//				Item item = new Item();
//				item.setItem_id(item_id[i]);
//
//				RequisitionItemID rid = new RequisitionItemID();
//				rid.setDocument(document);
//				rid.setItem(item);
//				rid.setRequisition(rd);
//				rid.setPersonnel(p);
//
//				RequisitionItem ri = new RequisitionItem();
//				ri.setPk(rid);
//				ri.setRequisition_note(re_note[i]);
//				ri.setRequisition_total(Integer.parseInt(requisition_total[i]));
//				total_price_purchases = ri.getRequisition_total()*Double.parseDouble(ip[i]);
//				ri.setTotal_price_purchase(total_price_purchases);
//
//				requisition_total_balance = Integer.parseInt(remain[i]) - ri.getRequisition_total();
//				total_price_balance = requisition_total_balance * Double.parseDouble(ip[i]);
//
//				ri.setRequisition_total_balance(requisition_total_balance);
//				ri.setTotal_price_balance(total_price_balance);
//
//				requisition_item.add(ri);
//			}
//
//			for (RequisitionItem r : requisition_item) {
//				result_item += requisitionItemRepo.insert_requisition(r.getPk().getRequisition().getRequisition_id(),
//						dateStirngStartNew, r.getRequisition_total(),
//						r.getTotal_price_balance(), r.getTotal_price_purchase(), r.getPk().getItem().getItem_id(),
//						r.getPk().getPersonnel().getPersonnel_id(), r.getPk().getDocument().getDoc_id(),
//						r.getRequisition_total_balance(), r.getRequisition_note());
//			}
//			
//			if(result_doc != 0 && result_item != 0) {
//				message = "บันทึกข้อมูลการเบิกวัสดุสําเร็จ";
//			}else{
//				message = "ข้อผิดพลาด :: ไม่สามารถบันทึกข้อมูลการเบิกวัสดุได้";
//			}
//			
//			mav = do_loadListRequisition(request, session, md, doc_id);
//			
//			session.setAttribute("messages", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_loadListRequisitionDetail")
//	public ModelAndView do_loadListRequisitionDetail(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("listRequisitionDetail");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String item_id = request.getParameter("item_id");
//			String doc_id = request.getParameter("doc_id");
//			List<RequisitionItem> rqi = new ArrayList<>();
//			RequestOrderItemList rol = rolRepo.get_itemDetailInDocument(item_id, doc_id);
//			System.out.println(rol.getId().getItem().getItem_name());
//
//			rqi = requisitionItemRepo.getRequisitionItemDetail(doc_id, item_id);
//			
//			System.out.println(rol.getDate());
//
//			List<String> all_date = new ArrayList<>();
//			String DATE_FORMAT = "dd MMMM yyyy";
//			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
//
//			for (RequisitionItem r : rqi) {
//				System.out.println(r.getPk().getPersonnel().getPersonnel_prefix());
//				all_date.add(sdf.format(r.getPk().getRequisition().getRequisition_date()));
//			}
//
//			request.setAttribute("rol", rol);
//			mav.addObject("date", all_date);
//			mav.addObject("rqi", rqi);
//			mav.addObject("doc_id",doc_id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@GetMapping(value = "/do_loadAddRequisitionPage")
//	public ModelAndView do_loadAddRequisitionPage(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addRequisitionPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String item_id = request.getParameter("item_id");
//			String doc_id = request.getParameter("doc_id");
//
//			Document document = documentRepo.get_purchaseOrderRequestDocument(doc_id);
//			Item item = itemRepo.get_ItemByID(item_id);
//
//			RequisitionItem requisitionItem = new RequisitionItem();
//			requisitionItem.setDoc_id(document);
//			requisitionItem.setItem_id(item);
//
//			RequestOrderItemList rol = rolRepo.get_itemDetailInDocument(item.getItem_id(), document.getDoc_id());
//
//			String lowest_item_balance = requisitionItemRepo.getLowestItemBalance(
//					requisitionItem.getDoc_id().getDoc_id(), requisitionItem.getItem_id().getItem_id());
//
//			if (lowest_item_balance == null) {
//				lowest_item_balance = rol.getAmount_received() + "";
//			}
//			
//
//			System.out.println(lowest_item_balance);
//
//			List<Major> listMajor = majorRepo.getAllMajor();
//
//			mav.addObject("total_price", rol.getTotal_price());
//			mav.addObject("listMajor", listMajor);
//			mav.addObject("lowest", lowest_item_balance);
//			mav.addObject("item", item);
//			mav.addObject("document", doc_id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_addRequistion")
//	public ModelAndView do_addRequisition(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addRequisitionPage");
//		String message = "";
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String personnel_prefix = request.getParameter("personnel_prefix");
//			String personnel_name = request.getParameter("personnel_name");
//			String personnel_lastName = request.getParameter("personnel_lastName");
//			int major = Integer.parseInt(request.getParameter("major"));
//			String note = request.getParameter("note").trim();
//			Double item_price = Double.parseDouble(request.getParameter("item_price"));
//			int requisition_total = Integer.parseInt(request.getParameter("requisition_total"));
//			int item_amount_balance = Integer.parseInt(request.getParameter("item_amount_balance"));//lowest
//			String requisition_id = request.getParameter("requisition_id");
//			String requisition_date = request.getParameter("requisition_date");
//			String doc_id = request.getParameter("doc_id");
//			System.out.println(doc_id+"doc_id");
//			Double total_price = Double.parseDouble(request.getParameter("total_price"));
//			String item_id = request.getParameter("item_id");
//			Date doc_date = new Date();
//			try {
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//				doc_date = dateFormat.parse(requisition_date);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//			
//			int requisition_total_balance = item_amount_balance - requisition_total;
//			System.out.println("จํานวนคงเหลือเดิม :: "+item_amount_balance);
//			System.out.println("จํานวนคงเหลือล่าสุด :: "+requisition_total_balance);
//			System.out.println("จํานวนที่เบิก :: "+requisition_total);
//			int personnel_id = personnelRepo.get_countPersonnel();
//			String personel_id_add = "PER" + (personnel_id + 1);
//
//			
//
//			Position position = new Position();
//			position.setPosition_id("9");
//			position.setPosition_name("เบิกวัสดุ");
//
//			Item item = itemRepo.get_ItemByID(item_id);
//
//			Major majors = majorRepo.getMajorByName(major);
//
//			Document document = documentRepo.get_purchaseOrderRequestDocument(doc_id);
//
//			Personnel personnel = new Personnel();
//			personnel.setPersonnel_id(personel_id_add);
//			personnel.setPersonnel_prefix(personnel_prefix);
//			personnel.setPersonnel_firstName(personnel_name);
//			personnel.setPersonnel_lastName(personnel_lastName);
//			personnel.setPersonnel_position("เบิกวัสดุ");
//			//personnel.setAssignType(assignType);
//			personnel.setMajor(majors);
//
//			personnelRepo.insert_personnel(personnel.getPersonnel_id(), personnel.getPersonnel_firstName(), personnel.getPersonnel_lastName(), personnel.getPersonnel_position(), personnel.getPersonnel_prefix(), "9", "POS0", major);
//
//			RequisitionItem rqi = new RequisitionItem();
//			rqi.setRequisition_id(requisition_id);
//			rqi.setRequisition_total(requisition_total);
//			rqi.setRequisition_total_balance(requisition_total_balance);
//			rqi.setRequisition_date(doc_date);
//			rqi.setTotal_price_purchase(item_price * requisition_total);
//			rqi.setTotal_price_balance(item_price * requisition_total_balance);
//			rqi.setRequisition_note(note);
//			rqi.setPersonnel_id(personnel);
//			rqi.setItem_id(item);
//			rqi.setDoc_id(document);
//			int result = 0;
//			System.out.println(requisition_date);
//			result = requisitionItemRepo.insert_requisition(rqi.getRequisition_id(), requisition_date,
//					requisition_total, rqi.getTotal_price_balance(), rqi.getTotal_price_purchase(), item_id,
//					personnel.getPersonnel_id(), doc_id, requisition_total_balance, rqi.getRequisition_note());
//		
//
//			if (result != 0) {
//				message = "บันทึกข้อมูลการเบิิกวัสดุสําเร็จ";
//			} else {
//				message = "ข้อผิดพลาด : ไม่สามารถแก้ไขการบันทึกข้อมูลการเบิกวัสดุได้";
//			}
//
//			session.setAttribute("messages", message);
//
//			mav = do_loadListRequisition(request, session, md, doc_id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
}
