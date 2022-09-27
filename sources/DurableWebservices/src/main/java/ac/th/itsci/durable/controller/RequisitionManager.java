package ac.th.itsci.durable.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.util.ConnectionDB;

public class RequisitionManager {

	public int sum_balance_amount(int year, double item_price, String item_id, String major) {
		int sum_balance_amount = 0;
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();
		try {

			String sql = "select sum(amount_balance) from request_order_item_list rl inner join document d on d.doc_id = rl.document_doc_id "
					+ " where rl.item_item_id = '" + item_id + "' and rl.item_price = " + item_price
					+ " and rl.budget_year <= " + year + " and d.depart_name = '" + major + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sum_balance_amount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum_balance_amount;
	}

	public int insertRequisition(List<RequisitionItem> requisition_item_list, String date) {
		int result_doc = 0;
		int result_item = 0;
		int result_all = 0;
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

			CallableStatement stmt_1 = conn.prepareCall("{call insert_requisition_doc(?, ?, ?)}");
			stmt_1.setString(1, requisition_item_list.get(0).getPk().getRequisition().getRequisition_id());
			stmt_1.setString(2, date);
			stmt_1.setInt(3, requisition_item_list.get(0).getPk().getRequisition().getRequisition_budget_year());
			stmt_1.execute();
			result_doc = stmt_1.getUpdateCount();

			CallableStatement stmt_2 = conn.prepareCall("{call insert_requisition_item(?,?,?,?,?,?,?,?,?,?,?,?)}");
			if (result_doc > 0) {
				for (RequisitionItem r_item : requisition_item_list) {
					stmt_2.setString(1, r_item.getPk().getRequisition().getRequisition_id());
					stmt_2.setString(2, sdf.format(r_item.getPk().getRequisition().getRequisition_date()));
					stmt_2.setInt(3, r_item.getRequisition_total());
					stmt_2.setDouble(4, r_item.getTotal_price_balance());
					stmt_2.setDouble(5, r_item.getTotal_price_purchase());
					stmt_2.setString(6, r_item.getPk().getRequestOrderItemList().getId().getItem().getItem_id());
					stmt_2.setString(7, r_item.getPk().getPersonnel().getPersonnel_id());
					stmt_2.setInt(8, r_item.getRequisition_total_balance());
					stmt_2.setString(9, r_item.getRequisition_note());
					stmt_2.setString(10, r_item.getPk().getRequestOrderItemList().getId().getDocument().getDoc_id());
					stmt_2.setString(11, r_item.getPk().getRequestOrderItemList().getId().getRequestOrderItemList_id());
					stmt_2.setInt(12, r_item.getLast_balance());
					stmt_2.execute();
					result_item += stmt_2.getUpdateCount();
				}
			}

			if (result_doc > 0) {
				if (result_item != requisition_item_list.size()) {
					result_all = 0;
				} else {
					result_all = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result_all;
	}

	public List<RequestOrderItemList> get_listRequestOrderItemforRequisition(int year, String depart_name,
			String vat_check) {
		List<RequestOrderItemList> requisition_item_list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requisition_item_list;

	}

	public List<RequisitionItem> get_requisition_detail(int year, String item_id, Double item_price,
			String depart_name) {
		List<RequisitionItem> requisition_item = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();

		try {

			String sql = "select i.item_name, i.item_category,i.item_unit, rd.requisition_id, p.personnel_prefix, p.personnel_first_name, p.personnel_last_name, rd.requisition_date, "
					+ "rl.item_price, ri.requisition_total, ri.total_price_purchase, "
					+ " ri.requisition_total_balance, ri.total_price_balance, ri.request_order_item_list_request_order_item_list_id, ri.requisition_note, rl.budget_year, rd.requisition_budget_year, i.item_id,"
					+ " rl.document_doc_id, ri.requisition_note "
					+ " from request_order_item_list rl inner join item i " + " on rl.item_item_id = i.item_id "
					+ " inner join requisition_item ri on ri.request_order_item_list_request_order_item_list_id "
					+ " = rl.request_order_item_list_id inner join personnel p on p.personnel_id = ri.personnel_personnel_id "
					+ " inner join requisition_document rd on rd.requisition_id = ri.requisition_requisition_id "
					+ " inner join document d on d.doc_id = rl.document_doc_id " + " where i.item_id = '" + item_id
					+ "' and rd.requisition_budget_year = " + year + " and rl.item_price = " + item_price
					+ " and rl.budget_year <= " + year + "" + " and d.depart_name = '" + depart_name + "' "
					+ " order by rl.budget_year ASC";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Item item = new Item();
				Personnel personnel = new Personnel();
				RequisitionDocument rd = new RequisitionDocument();
				RequisitionItem rit = new RequisitionItem();
				RequisitionItemID rid = new RequisitionItemID();
				RequestOrderItemList ril = new RequestOrderItemList();
				RequestOrderItemListID rild = new RequestOrderItemListID();
				Document doc = new Document();

				item.setItem_id(rs.getString(18));
				item.setItem_name(rs.getString(1));
				item.setItem_category(rs.getString(2));
				item.setItem_unit(rs.getString(3));

				doc.setDoc_id(rs.getString(19));
				rd.setRequisition_id(rs.getString(4));

				personnel.setPersonnel_prefix(rs.getString(5));
				personnel.setPersonnel_firstName(rs.getString(6));
				personnel.setPersonnel_lastName(rs.getString(7));

				rd.setRequisition_date(rs.getTimestamp(8));
				rd.setRequisition_budget_year(rs.getInt(17));
				ril.setItem_price(rs.getDouble(9));
				ril.setBudget_year(rs.getInt(16));

				rit.setRequisition_total(rs.getInt(10));
				rit.setTotal_price_purchase(rs.getDouble(11));
				rit.setRequisition_total_balance(rs.getInt(12));
				rit.setTotal_price_balance(rs.getDouble(13));
				rit.setRequisition_note(rs.getString(15));
				rit.setRequisition_note(rs.getString(20));
				rild.setRequestOrderItemList_id(rs.getString(14));
				rild.setItem(item);
				rild.setDocument(doc);

				ril.setId(rild);

				rid.setRequestOrderItemList(ril);
				rid.setPersonnel(personnel);
				rid.setRequisition(rd);

				rit.setPk(rid);
				requisition_item.add(rit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requisition_item;
	}
	
	//need to add depart
	public List<RequisitionItem> get_requisition_detail_by_request_year(int year, String item_id, String doc_id,
			double price, int budget_year, String depart_name) {
		List<RequisitionItem> requisition_item = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();

		try {

			String sql = "select i.item_name, i.item_category,i.item_unit, rd.requisition_id, p.personnel_prefix, p.personnel_first_name, p.personnel_last_name, rd.requisition_date, "
					+ "rl.item_price, ri.requisition_total, ri.total_price_purchase, "
					+ " ri.requisition_total_balance, ri.total_price_balance, ri.request_order_item_list_request_order_item_list_id, ri.requisition_note, rl.budget_year, rd.requisition_budget_year, "
					+ " i.item_id, rl.document_doc_id, ri.requisition_note "
					+ " from request_order_item_list rl inner join item i " + " on rl.item_item_id = i.item_id "
					+ " inner join requisition_item ri on ri.request_order_item_list_request_order_item_list_id "
					+ " = rl.request_order_item_list_id inner join personnel p on p.personnel_id = ri.personnel_personnel_id "
					+ " inner join requisition_document rd on rd.requisition_id = ri.requisition_requisition_id "
					+ " inner join document d on d.doc_id = rl.document_doc_id"
					+ " where i.item_id = '" + item_id + "' and rd.requisition_budget_year = " + year
					+ " and rl.budget_year = " + budget_year + " and rl.item_price = " + price + " and d.depart_name = '"+depart_name+"' "
					+ "  order by rd.requisition_id ASC";
			// and ri.request_order_item_list_document_doc_id = '" + doc_id + "'
			// System.out.println(sql);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Item item = new Item();
				Personnel personnel = new Personnel();
				RequisitionDocument rd = new RequisitionDocument();
				RequisitionItem rit = new RequisitionItem();
				RequisitionItemID rid = new RequisitionItemID();
				RequestOrderItemList ril = new RequestOrderItemList();
				RequestOrderItemListID rild = new RequestOrderItemListID();
				Document doc = new Document();

				item.setItem_id(rs.getString(18));
				item.setItem_name(rs.getString(1));
				item.setItem_category(rs.getString(2));
				item.setItem_unit(rs.getString(3));

				doc.setDoc_id(rs.getString(19));
				rd.setRequisition_id(rs.getString(4));

				personnel.setPersonnel_prefix(rs.getString(5));
				personnel.setPersonnel_firstName(rs.getString(6));
				personnel.setPersonnel_lastName(rs.getString(7));

				rd.setRequisition_date(rs.getTimestamp(8));
				rd.setRequisition_budget_year(rs.getInt(17));
				ril.setItem_price(rs.getDouble(9));
				ril.setBudget_year(rs.getInt(16));

				rit.setRequisition_total(rs.getInt(10));
				rit.setTotal_price_purchase(rs.getDouble(11));
				rit.setRequisition_total_balance(rs.getInt(12));
				rit.setTotal_price_balance(rs.getDouble(13));
				rit.setRequisition_note(rs.getString(15));
				rit.setRequisition_note(rs.getString(20));

				rild.setRequestOrderItemList_id(rs.getString(14));
				rild.setItem(item);
				rild.setDocument(doc);

				ril.setId(rild);

				rid.setRequestOrderItemList(ril);
				rid.setPersonnel(personnel);
				rid.setRequisition(rd);

				rit.setPk(rid);
				requisition_item.add(rit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requisition_item;
	}

	public int get_max_total_balance(int year, String item_id, String doc_id, String ri_id, int b_year,
			String depart_name, String status, Double item_price) {
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();
		int result = 0;
		try {
			String s = "";
			if (year == b_year) {
				s = "<=";
			} else {
				s = "=";
			}
			System.out.println("======Max==========");
			System.out.println("marks :: " + s);
			System.out.println("ri_id "+ri_id);
			Statement stmt = conn.createStatement();
			// เอาค่าเหลือเยอะสุดของวัสดุคงเหลือของปีที่แล้วๆ ที่เบิกในปีนี้
			// get max balance from last year that
			// not actually
			System.out.println("requisition year :: "+year+" budget year :: "+b_year);
//			String sql2 = "select max(ri.last_balance) " + " from request_order_item_list rl inner join item i "
//					+ " on rl.item_item_id = i.item_id "
//					+ " inner join requisition_item ri on ri.request_order_item_list_request_order_item_list_id "
//					+ " = rl.request_order_item_list_id inner join personnel p on p.personnel_id = ri.personnel_personnel_id "
//					+ " inner join requisition_document rd on rd.requisition_id = ri.requisition_requisition_id "
//					+ " where i.item_id = '" + item_id + "' and rd.requisition_budget_year = " + year
//					+ " and rl.request_order_item_list_id = '" + ri_id + "' and rl.budget_year " + s + "" + b_year;
			String sql2 = "select max(ri.last_balance) " + " from request_order_item_list rl inner join item i "
					+ " on rl.item_item_id = i.item_id "
					+ " inner join requisition_item ri on ri.request_order_item_list_request_order_item_list_id "
					+ " = rl.request_order_item_list_id inner join personnel p on p.personnel_id = ri.personnel_personnel_id "
					+ " inner join requisition_document rd on rd.requisition_id = ri.requisition_requisition_id "
					+ " inner join document d on d.doc_id = rl.document_doc_id "
					+ " where i.item_id = '" + item_id + "' and rd.requisition_budget_year = " + year
					+ " and rl.budget_year " + s + "" + b_year +" and rl.item_price = "+item_price 
							+ " and d.depart_name = +'"+depart_name +"' ";
			
			String sql3 = "select count(ri.last_balance) " + " from request_order_item_list rl inner join item i "
					+ " on rl.item_item_id = i.item_id "
					+ " inner join requisition_item ri on ri.request_order_item_list_request_order_item_list_id "
					+ " = rl.request_order_item_list_id inner join personnel p on p.personnel_id = ri.personnel_personnel_id "
					+ " inner join requisition_document rd on rd.requisition_id = ri.requisition_requisition_id "
					+ " inner join document d on d.doc_id = rl.document_doc_id "
					+ " where i.item_id = '" + item_id + "' and rd.requisition_budget_year < " + year
					+ " and rl.budget_year = " + b_year +" and rl.item_price = "+item_price
					+ " and d.depart_name = +'"+depart_name +"' ";
//			
			String sql = "select sum(rl.amount_received) as total "
					+ "from request_order_item_list rl inner join item i on i.item_id = rl.item_item_id "
					+ "inner join document d on d.doc_id = rl.document_doc_id " + "where  d.doc_status = '" + status
					+ "' and d.depart_name = '" + depart_name + "' and rl.budget_year = " + b_year
					+ " and rl.item_item_id = '" + item_id + "' and rl.item_price = "+item_price;

			int result_1 = 0;
			int result_2 = 0;
			int old_requisition_count = 0;

			ResultSet rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				result_1 = rs.getInt(1);
			}
//			
			ResultSet rs2 = stmt.executeQuery(sql);
			while (rs2.next()) {
				result_2 = rs2.getInt(1);
			}
			
			ResultSet rs3 = stmt.executeQuery(sql3);
			while (rs3.next()) {
				old_requisition_count = rs3.getInt(1);
			}
			
			System.out.println(result_1 + "re1 " + result_2 + " re2");
			if (year == b_year) {
				result = result_2;
			} else {
				
				if(old_requisition_count != 0) {
					result = result_1;
				}else {
					result = result_2;
				}
//				if (result_1 < result_2) {
//					result = result_2;
//				} else {
//					result = result_1;
//				}
//				result = result_1;
			}

			System.out.println(result);
			System.out.println("=================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int get_amount_received(String depart_name, int year, String status, String item_id, Double i_price) {
		int amount_received = 0;
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();

		try {
			Statement stmt = conn.createStatement();
			String sql = "select sum(rl.amount_received) as total "
					+ "from request_order_item_list rl inner join item i on i.item_id = rl.item_item_id "
					+ "inner join document d on d.doc_id = rl.document_doc_id " + "where  d.doc_status = '" + status
					+ "' and d.depart_name = '" + depart_name + "' and rl.budget_year = " + year
					+ " and rl.item_item_id = '" + item_id + "' and rl.item_price =  "+i_price;

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				amount_received = rs.getInt(1);
			}

			stmt.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return amount_received;
	}

	public List<RequisitionItem> get_listRequisition(String depart_name, int year, String status) {
		List<RequestOrderItemList> requisition_item_list_balance_last_year = new ArrayList<>();
		List<RequestOrderItemList> requisition_item_list = new ArrayList<>();
		List<RequisitionItem> requisition_item_list_page = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection conn = condb.getConnection();

		try {
			Statement stmt = conn.createStatement();
			String day_before = year + "-09-1";
			String day_end = (year) + "-09-1";
			String sql1 = "select rls.item_item_id, sum(rls.amount_balance), rls.item_price from  request_order_item_list rls inner join document ds on ds.doc_id = rls.document_doc_id"
					+ " where ds.doc_status = '" + status + "' and ds.depart_name = '" + depart_name
					+ "' and rls.budget_year < " + year + " and rls.amount_balance != 0 and ds.vat_check != 'repair'"
					+ " group by rls.item_item_id, rls.item_price ";
			String sql2 = "select rl.item_item_id, i.item_name, i.item_unit, rl.item_price, sum(rl.amount_received) as total, sum(rl.amount_balance) as total_balance, i.item_price "
					+ "from  request_order_item_list rl inner join item i on i.item_id = rl.item_item_id "
					+ "inner join document d on d.doc_id = rl.document_doc_id " + "where  d.doc_status = '" + status
					+ "' and d.depart_name = '" + depart_name + "' and rl.budget_year <= " + year
					+ " and d.vat_check != 'repair' group by rl.item_item_id, rl.item_price ";

//			String day_before = year+"-10-1";
//			String day_end = (year+1)+"-10-1";
//			System.out.println(day_before+" "+day_end);
//			String sql1 = "select rls.item_item_id, sum(rls.amount_balance), rls.item_price from  request_order_item_list rls inner join document ds on ds.doc_id = rls.document_doc_id"
//					+ " where ds.doc_status = '" + status + "' and ds.depart_name = '" + depart_name
//					+ "' and year(ds.doc_date) < " + year + " and month(ds.doc_date) < 10 "
//					+ "and rls.amount_balance != 0" + " group by rls.item_item_id, rls.item_price ";
//			String sql2 = "select rl.item_item_id, i.item_name, i.item_unit, rl.item_price, sum(rl.amount_received) as total, sum(rl.amount_balance) as total_balance, i.item_price "
//					+ "from  request_order_item_list rl inner join item i on i.item_id = rl.item_item_id "
//					+ "inner join document d on d.doc_id = rl.document_doc_id " + "where  d.doc_status = '" + status
//					+ "' and d.depart_name = '" + depart_name + "' and (d.doc_date between '"+day_before+"' AND '"+day_end+"') "
//					+ " group by rl.item_item_id, rl.item_price ";

			ResultSet rs1 = stmt.executeQuery(sql1);
			while (rs1.next()) {
				RequestOrderItemList request_order_item_list = new RequestOrderItemList();
				RequestOrderItemListID request_order_item_list_id = new RequestOrderItemListID();
				Item item = new Item();
				item.setItem_id(rs1.getString(1));

				request_order_item_list.setAmount_balance(rs1.getInt(2));
				request_order_item_list.setItem_price(rs1.getDouble(3));

				request_order_item_list_id.setItem(item);

				request_order_item_list.setId(request_order_item_list_id);

				requisition_item_list_balance_last_year.add(request_order_item_list);
			}

			ResultSet rs2 = stmt.executeQuery(sql2);
			while (rs2.next()) {
				RequestOrderItemList request_order_item_list = new RequestOrderItemList();
				RequestOrderItemListID request_order_item_list_id = new RequestOrderItemListID();
				Item item = new Item();

				item.setItem_id(rs2.getString(1));
				item.setItem_name(rs2.getString(2));
				item.setItem_unit(rs2.getString(3));

				request_order_item_list.setItem_price(rs2.getDouble(4));
				request_order_item_list.setAmount_received(rs2.getInt(5));
				request_order_item_list.setAmount_balance(rs2.getInt(6));
				item.setItem_price(rs2.getDouble(7));

				request_order_item_list_id.setItem(item);

				request_order_item_list.setId(request_order_item_list_id);

				requisition_item_list.add(request_order_item_list);
			}

			System.out.println(requisition_item_list.size() + " " + requisition_item_list_balance_last_year.size());
			for (RequestOrderItemList rol : requisition_item_list) {
				RequisitionItem requisition_item = new RequisitionItem();
				RequisitionItemID requisition_item_id = new RequisitionItemID();
				if (requisition_item_list_balance_last_year.size() != 0) {
					for (RequestOrderItemList r : requisition_item_list_balance_last_year) {

						if (rol.getId().getItem().getItem_id().equals(r.getId().getItem().getItem_id())
								&& rol.getItem_price().equals(r.getItem_price())) {
							requisition_item.setRequisition_total_balance(r.getAmount_balance());
						} else {
							if (requisition_item.getRequisition_total_balance() == 0) {
								requisition_item.setRequisition_total_balance(0);
							}
						}

						requisition_item_id.setRequestOrderItemList(rol);
						requisition_item.setPk(requisition_item_id);
					}
				} else {
					requisition_item.setRequisition_total_balance(0);
					requisition_item_id.setRequestOrderItemList(rol);
					requisition_item.setPk(requisition_item_id);
				}
				requisition_item_list_page.add(requisition_item);
			}

			stmt.close();
			rs1.close();
			rs2.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return requisition_item_list_page;
	}
//
//	public List<RequisitionItem> get_requisition_list(String depart_name, String year) {
//		List<RequisitionItem> requisition_item_list = new ArrayList<>();
//		ConnectionDB condb = new ConnectionDB();
//		Connection conn = condb.getConnection();
//		System.out.println(depart_name + "" + year);
//		try {
//			Statement stmt = conn.createStatement();
//			String sql = "select rl.item_item_id, i.item_name, i.item_unit, rl.item_price, sum(rl.amount_received) as total, COALESCE(sum(ri.requisition_total),0), "
//					+ "sum(rl.amount_received) - COALESCE(sum(ri.requisition_total),0) as total_balance "
//					+ "from request_order_item_list rl inner join item i on i.item_id = rl.item_item_id "
//					+ "inner join document d on d.doc_id = rl.document_doc_id left join requisition_item ri on i.item_id = ri.item_item_id "
//					+ "where d.doc_status = '" + "จัดทําใบเบิกพัสดุ สําเร็จ" + "' and d.depart_name = '" + depart_name
//					+ "' and year(d.doc_date) = '" + year + "' group by rl.item_item_id, rl.item_price ";
//		
//			ResultSet rs = stmt.executeQuery(sql);
//
//			while (rs.next()) {
//				Item item = new Item();
//				item.setItem_id(rs.getString(1));
//				item.setItem_name(rs.getString(2));
//				item.setItem_unit(rs.getString(3));
//
//				RequestOrderItemListID requestOrderItemListID = new RequestOrderItemListID();
//				requestOrderItemListID.setItem(item);
//
//				RequestOrderItemList requestOrderItemList = new RequestOrderItemList();
//				requestOrderItemList.setId(requestOrderItemListID);
//				requestOrderItemList.setItem_price(rs.getDouble(4));
//				requestOrderItemList.setAmount_received(rs.getInt(5));
//
//				RequisitionItemID requisitionItemID = new RequisitionItemID();
//				requisitionItemID.setItem(item);
//
//				List<RequestOrderItemList> requestOrderItemLists = new ArrayList<>();
//				requestOrderItemLists.add(requestOrderItemList);
//
//				item.setRequisitionItemLists(requestOrderItemLists);
//
//				RequisitionItem requisitionItem = new RequisitionItem();
//				requisitionItem.setPk(requisitionItemID);
//				requisitionItem.setRequisition_total(rs.getInt(6));
//				requisitionItem.setRequisition_total_balance(rs.getInt(7));
//
//				requisition_item_list.add(requisitionItem);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return requisition_item_list;
//	}

}
