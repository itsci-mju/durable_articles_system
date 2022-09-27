package ac.th.itsci.durable.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Item;
import ac.th.itsci.durable.entity.RequestOrderItemList;
import ac.th.itsci.durable.entity.RequestOrderItemListID;
import ac.th.itsci.durable.repo.ItemRepository;

import java.util.*;

@Controller
public class ItemController {

	@Autowired
	ItemRepository itemRepo;

	@GetMapping(value = "/do_loadAddItemPage")
	public String do_loadAddItemPage() {
		return "addItemPage";
	}

	@GetMapping(value = "/do_loadSearchItemPage")
	public ModelAndView do_loadSearchItemPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listItemPage");
		try {
			List<Item> items = itemRepo.get_all_item();
			mav.addObject("items", items);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_addItem")
	public ModelAndView do_addItem(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addItemPage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			int item_count = itemRepo.count_Item();

			if (item_count == 0) {
				item_count = 1;
			} else {
				item_count += 1;
			}

			String item_id = "ITM" + (item_count);
			String item_category = request.getParameter("item_category");
			String item_name = request.getParameter("item_name");
			String item_unit = request.getParameter("item_unit");
			Double item_price = Double.parseDouble(request.getParameter("item_price"));
			String item_note = request.getParameter("item_note").trim();

			Item item = new Item(item_id, item_name, item_unit, item_price, item_note, item_category);

			try {
				itemRepo.save(item);
				message = "บันทึกข้อมูลวัสดุสําเร็จ";
			} catch (Exception e) {
				message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลวัสดุได้";
				e.printStackTrace();
			}

			session.setAttribute("messages", message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/do_searchItem")
	public ModelAndView do_searchItem(ServletRequest request, HttpSession session, Model md, String item_name_s, String item_category_s) {
		ModelAndView mav = new ModelAndView("listItemPage");
		List<Item> items = new ArrayList<>();
		Item item = new Item();
		try {
			request.setCharacterEncoding("UTF-8");
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

				mav.addObject("item_search", item);
				session.setAttribute("item_search", item);
				
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

				mav.addObject("item_search", item_search);
				session.setAttribute("item_search", item_search);
			}

			mav.addObject("items", items);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/do_getItemByID")
	public ModelAndView do_getItemByID(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("updateItemPage");

		try {

			request.setCharacterEncoding("UTF-8");
			String item_id = request.getParameter("item_id");

			Item item = new Item();
			item.setItem_id(item_id);

			Item item_search = itemRepo.get_ItemByID(item.getItem_id());

			mav.addObject("item", item_search);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_updateItem")
	public ModelAndView do_updateItem(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listItemPage");
		Item item = new Item();
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			String item_id = request.getParameter("item_id");
			String item_name = request.getParameter("item_name");
			String item_price = request.getParameter("item_price");
			String item_unit = request.getParameter("item_unit");
			String item_note = request.getParameter("item_note").trim();
			String item_category = request.getParameter("item_category");

			item = new Item(item_id, item_name, item_unit, Double.parseDouble(item_price), item_note, item_category);

			int result = 0;
			result = itemRepo.update_Item(item.getItem_id(), item.getItem_name(), item.getItem_price(),
					item.getItem_unit(), item.getItem_note(), item.getItem_category());

			if (result != 0) {
				message = "แก้ไขข้อมูลวัสดุสําเร็จ";
			} else {
				message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลวัสดุได้";
			}

			Item item_search = (Item) session.getAttribute("item_search");
			session.setAttribute("messages", message);
			mav.addObject("remove_session_check","1");
			
			
			if (item_search != null) {
				mav = do_searchItem(request, session, md, item_search.getItem_name(), item_search.getItem_category());
			} else {
				mav = do_loadSearchItemPage(request, session, md);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

//
//	@GetMapping(value = "/do_loadListItemPage")
//	public String do_loadListItemPage() {
//		return "listItemPage";
//	}
//
//	@PostMapping(value = "/do_addItem")
//	public ModelAndView do_addItem(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addItemPage");
//		Item item = new Item();
//		int result = 0;
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			int item_id = itemRepo.count_Item();
//			String item_ids = "";
//			if (item_id == 0) {
//				item_ids = "ITM1";
//			} else {
//				item_ids = "ITM" + (item_id + 1);
//			}
//
//			String item_name = request.getParameter("item_name");
//			String item_price = request.getParameter("item_price");
//			String item_unit = request.getParameter("item_unit");
//			String item_note = request.getParameter("item_note");
//
//			item = new Item(item_ids, item_name, item_unit, Double.parseDouble(item_price), item_note);
//
//			result = itemRepo.insert_Item(item.getItem_id(), item.getItem_name(), item.getItem_price(),
//					item.getItem_unit(), item.getItem_note(), item.getItem_category());
//
//			String message = "";
//
//			if (result != 0) {
//				message = "บันทึกข้อมูลวัสดุสําเร็จ";
//			} else {
//				message = "ข้อผิดพลาด : ไม่สามารถบันทึกข้อมูลวัสดุได้";
//			}
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_updateItem")
//	public ModelAndView do_updateItem(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("updateItemPage");
//		Item item = new Item();
//		String message = "";
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String item_id = (String) session.getAttribute("item_id");
//			String item_name = request.getParameter("item_name");
//			String item_price = request.getParameter("item_price");
//			String item_unit = request.getParameter("item_unit");
//			String item_note = request.getParameter("item_note");
//
//			item = new Item(item_id, item_name, item_unit, Double.parseDouble(item_price), item_note);
//
//			int result = 0;
//			result = itemRepo.update_Item(item.getItem_id(), item.getItem_name(), item.getItem_price(),
//					item.getItem_unit(), item.getItem_note(), item.getItem_category());
//
//			if (result != 0) {
//				message = "แก้ไขข้อมูลวัสดุสําเร็จ";
//			} else {
//				message = "ข้อผิดพลาด : ไม่สามารถแก้ไขข้อมูลวัสดุได้";
//			}
//
//			session.removeAttribute("item_id");
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return mav;
//	}
//

//	@PostMapping(value = "/do_searchItemDocument")
//	public ModelAndView do_searchItemDocument(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String item_name = request.getParameter("item_name");
//
//			List<Item> items = itemRepo.get_search_item(item_name);
//
//			// save session from document
//			// Document document =
//			// documentController.savePurchaseOrdeRequestDocumentData(request);
//			// String date = request.getParameter("date");
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//			document.setPrice_txt(price_txt);
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("items", items);
//			mav.addObject("itemSize", 0);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_searchItemDocument_update")
//	public ModelAndView do_searchItemDocument_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String item_name = request.getParameter("item_name");
//
//			List<Item> items = itemRepo.get_search_item(item_name);
//
//			// save session from document
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			String requisition_old = request.getParameter("requisition_old");
//			String cof_old = request.getParameter("cof_old");
//			String acc_old = request.getParameter("acc_old");
//			String old_secretary = request.getParameter("old_secretary");
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//
//			document.setPrice_txt(price_txt);
//			document.setChief_of_procurement(cof_old);
//			document.setRequest_order_person(requisition_old);
//			document.setAccounting_officer(acc_old);
//			document.setSecretary(old_secretary);
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("items", items);
//			mav.addObject("itemSize", 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@GetMapping(value = "/do_addItemCart")
//	public ModelAndView do_addItemCart(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		try {
//			String item_id = request.getParameter("item_id");
//
//			Item item = itemRepo.get_ItemByID(item_id);
//			List<Item> itemsCart = new ArrayList<>();
//
//			String message = "";
//
//			boolean check_duplicate = false;
//
//			if (session.getAttribute("item_cart") == null) {
//				itemsCart.add(item);
//				session.setAttribute("item_cart", itemsCart);
//				message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
//			} else {
//				itemsCart = (List<Item>) session.getAttribute("item_cart");
//				for (Item i : itemsCart) {
//					if (i.getItem_id().equals(item_id)) {
//						check_duplicate = true;
//						break;
//					}
//				}
//
//				if (check_duplicate == false) {
//					itemsCart.add(item);
//					message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
//				} else {
//					message = "รายการเสนอซื้อซํ้า !!";
//				}
//			}
//
//			session.setAttribute("item_cart", itemsCart);
//			mav.addObject("message", message);
//			System.out.println(itemsCart.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@GetMapping(value = "/do_addItemCart_update")
//	public ModelAndView do_addItemCart_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		try {
//			String item_id = request.getParameter("item_id");
//
//			Item item = itemRepo.get_ItemByID(item_id);
//			List<RequestOrderItemList> itemsCart = new ArrayList<>();
//			RequestOrderItemListID rid = new RequestOrderItemListID();
//			RequestOrderItemList rl = new RequestOrderItemList();
//			String message = "";
//
//			boolean check_duplicate = false;
//
//			if (session.getAttribute("item_cart_update") == null) {
//				rid.setItem(item);
//				rl.setId(rid);
//				itemsCart.add(rl);
//				session.setAttribute("item_cart_update", itemsCart);
//				message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
//			} else {
//				itemsCart = (List<RequestOrderItemList>) session.getAttribute("item_cart_update");
//				for (RequestOrderItemList i : itemsCart) {
//					if (i.getId().getItem().getItem_id().equals(item_id)) {
//						check_duplicate = true;
//						break;
//					}
//				}
//
//				if (check_duplicate == false) {
//					rid.setItem(item);
//					rl.setId(rid);
//					itemsCart.add(rl);
//					message = "เพิ่มรายการเสนอซื้อเรียบร้อย !";
//				} else {
//					message = "รายการเสนอซื้อซํ้า !!";
//				}
//			}
//
//			session.setAttribute("item_cart_update", itemsCart);
//			mav.addObject("message", message);
//			System.out.println(itemsCart.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@GetMapping(value = "/do_deleteItemCart")
//	public ModelAndView do_deleteItemCart(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		try {
//
//			request.setCharacterEncoding("UTF-8");
//			String item_id = request.getParameter("item_id");
//			List<Item> item_cart = (List<Item>) session.getAttribute("item_cart");
//			String message = "";
//			int i = 0;
//			for (Item ic : item_cart) {
//				if (ic.getItem_id().equals(item_id)) {
//					item_cart.remove(i);
//					message = "ลบรายการ " + ic.getItem_name() + " เรียบร้อย";
//					break;
//				}
//				i++;
//			}
//
//			session.setAttribute("item_cart", item_cart);
//			mav.addObject("message", message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@GetMapping(value = "/do_deleteItemCart_update")
//	public ModelAndView do_deleteItemCart_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//			request.setCharacterEncoding("UTF-8");
//			String item_id = request.getParameter("item_id");
//			String message = "";
//			List<RequestOrderItemList> item_cart = (List<RequestOrderItemList>) session
//					.getAttribute("item_cart_update");
//			int i = 0;
//			for (RequestOrderItemList ic : item_cart) {
//				if (ic.getId().getItem().getItem_id().equals(item_id)) {
//					item_cart.remove(i);
//					message = "ลบรายการ " + ic.getId().getItem().getItem_name() + " เรียบร้อย";
//					break;
//				}
//			}
//			mav.addObject("message", message);
//			session.setAttribute("item_cart_update", item_cart);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

}
