<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.text.*"%>
<!DOCTYPE html>
<%
	String DATE_FORMAT = "dd MMMM yyyy";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
Date date = new Date(System.currentTimeMillis());
%>
<html>
<head>
<style type="text/css">
@media screen and (min-width: 768px) {
	.modal-dialog {
		width: 700px; /* New width for default modal */
	}
	.modal-sm {
		width: 350px; /* New width for small modal */
	}
}

@media screen and (min-width: 992px) {
	.modal-lg {
		width: 950px; /* New width for large modal */
	}
}

.modal-header, h4, .close {
	background-color: #FF0000;
	color: white !important;
	text-align: center;
	font-size: 30px;
}

.modal-body, .modal-content {
	width: 100%;
	height: 500px;
	text-align: center;
}

.btn-fresh {
	color: #000000;
	background-color: #51bf87;
	border-bottom: 2px solid #41996c;
}

* {
	box-sizing: border-box;
}

.column {
	float: left;
	width: 40%;
	text-align: right;
	padding: 10px;
	height: 40px;
}

.column02 {
	float: left;
	width: 60%;
	padding: 10px;
	text-align: center;
	height: 40px;
	padding: 10px;
}

.column03 {
	float: left;
	width: 100%;
	padding: 10px;
	text-align: center;
	height: 40px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}
}
</style>

<script src="thaibath.js" type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/calculate.js"></script>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<meta charset="UTF-8">
<title>เพิ่มรายการเอกสารเสนอซื้อ/จ้าง</title>
</head>
<body
	onload="caltotal(); repair_price_txt('${total_price_repair}'); checkType(); openVatCheck();">
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<form action="do_addPurchaseOrderRequest"
		name="purchaseOrderRequestFrm" class="form-horizontal" method="post">
		<div class="row" style="margin-top: 70px">
			<div class="container">

				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">เอกสารขอซื้อ/จ้าง</h3>
					</div>
					<div class="panel-body">
						<fieldset>
							<legend style="font-size: 1em">
								<i class="fa fa-file-o"></i> ข้อมูลเอกสาร
							</legend>
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่เอกสาร :
											</label>
											<div class="col-sm-5">
												<input type="text" id="doc_id" name="doc_id"
													class="form-control" value="${ document.getDoc_id() }">
											</div>
										</div>
										<%-- <div class="form-group">
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label>
											<div class="col-sm-5">
												<input type="text" id="plan_id" name="plan_id"
													class="form-control" value="${ document.getPlan_id() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label>
											<div class="col-sm-5">
												<input type="text" id="depart_id" name="depart_id"
													class="form-control" value="${ document.getDepart_id() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label>
											<div class="col-sm-5">
												<input type="text" id="fund_id" name="fund_id"
													class="form-control" value="${ document.getFund_id() }">
											</div>
										</div> --%>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label>
											<div class="col-sm-5">
												<input type="text" id="money_used" name="money_used"
													class="form-control" value="${ document.getMoney_used() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label>
											<div class="col-sm-5">
												<input type="text" id="budget" name="budget" placeholder=""
													class="form-control" value="${ document.getBudget() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรื่อง : </label>
											<div class="col-sm-5">
												<input type="text" id="doc_title" name="doc_title"
													placeholder="" class="form-control"
													value="${ document.getDoc_title() }">
											</div>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<!-- <div class="form-group">
											<label class="col-sm-5 control-label"></label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label"></label>
											<div class="col-sm-5"></div>
										</div> -->
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label>
											<div class="col-sm-5">
												<input type="text" id="plan_name" name="plan_name"
													class="form-control" value="${ document.getPlan_name() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label>
											<div class="col-sm-5">
												<input type="text" id="depart_name" name="depart_name"
													class="form-control"
													value="${ sessionScope.staffSession.major.major_Name }"
													readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label>
											<div class="col-sm-5">
												<input type="text" id="fund_name" name="fund_name"
													class="form-control" value="${ document.getFund_name() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label>
											<div class="col-sm-5">
												<input type="text" id="work_name" name="work_name"
													class="form-control" value="${ document.getWork_name() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ : </label>
											<div class="col-sm-5">
												<input type="text" id="datepicker" name="date"
													class="form-control" value="<%=sdf.format(date)%>">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรียน : </label>
											<div class="col-sm-5">
												<input type="text" id="doc_dear" name="doc_dear"
													class="form-control" value="${ document.getDoc_dear() }">
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-2 control-label"
												style="margin-left: 44px;">รายละเอียด : </label>
											<div class="col-sm-8">
												<textarea class="form-control" id="doc_title_describe"
													name="doc_title_describe" rows="4">${ document.getDoc_title_decribe() }</textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"
												style="margin-left: 44px;">เหตุผล/ความจําเป็น :</label>
											<div class="col-sm-8">
												<textarea class="form-control" id="doc_reason_describe"
													name="doc_reason_describe" rows="4">${ document.getDoc_reason_describe() }</textarea>
											</div>
										</div>
										<!-- 										<div class="form-group">
											<label class="col-sm-2 control-label"
												style="margin-left: 44px;">ชื่อวัสดุ : </label>
											<div class="col-sm-3">
												<input type="text" id="item_name" name="item_name"
													class="form-control">
											</div>
											<div class="col-sm-2">
												<input type="submit" formaction="do_searchItemDocument"
													class="btn btn-sky btn-block" value="ค้นหา">
											</div>
											<div class="col-sm-4">
												<label style="color: red;" class="control-label">**กรุณาค้นหารายการวัสดุเพืิ่ิอเพิ่มรายการเสนอ
													!!</label>
											</div>
										</div> -->
									</div>

								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend style="font-size: 1em">
								<i class="fa fa-cart"></i> ข้อมูลการซื้อ
							</legend>
							<div class="row">
								<div class="col-sm-6">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลือกรายการ : </label>
											<label class="radio-inline"><input type="radio"
												name="check_type" value="i" onClick="openAdd('item_search')"
												<c:if test="${ check_type == 'i' }">checked</c:if>>ซื้อวัสดุ</label>
											<label class="radio-inline"><input type="radio"
												name="check_type" value="r" onClick="openAdd('repair')"
												<c:if test="${ check_type == 'r' }">checked</c:if>>ซ่อมแซม</label>
										</div>
									</div>
								</div>
							</div>

						</fieldset>

						<!--  start search table -->
						<div class="tabcontent" id="item_search"
							style="display:<c:if test="${ check_type == 'i' }">block</c:if><c:if test="${ check_type != 'i' }"> none;</c:if> margin-top:20px; ">
							<fieldset>
								<legend align="left" style="font-size: 1em">รายละเอียดการซื้อวัสดุ</legend>
							</fieldset>
							<div class="row">
								<div class="col-sm-6">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">ซื้อจาก : </label> <label
												class="radio-inline"><input type="radio"
												name="store_check" id="1" value="1" onClick="checkType()"
												<c:if test="${ sessionScope.store_check == '1' }">checked</c:if>>ร้านค้าทั่วไป</label>
											<label class="radio-inline"><input type="radio"
												name="store_check" id="2" value="2" onClick="checkType()"
												<c:if test="${ sessionScope.store_check == '2' }">checked</c:if>>หจก.</label>

										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อร้าน : </label>
											<div class="col-sm-5">
												<input type="text" id="store_name" name="store_name"
													class="form-control" value="${ document.getStore_name() }">
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">รวม Vat : </label> <input
												type="checkbox" name="vats" id="vats"
												onClick="openVatCheck()"
												<c:if test="${ empty sessionScope.vats }">disabled</c:if>
												<c:if test="${ not empty sessionScope.vats }">checked</c:if>>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">ราคาวัสดุรวม
												Vat แล้ว : </label> <input type="checkbox" name="item_vat"
												id="item_vat"
												<c:if test="${ empty sessionScope.item_vat }">disabled</c:if>
												<c:if test="${ not empty sessionScope.item_vat }">checked</c:if>>
										</div>
									</div>
								</div>
							</div>
							<fieldset>
								<legend align="left" style="font-size: 1em">รายการซื้อวัสดุ</legend>
							</fieldset>
							<div class="form-inline" style="margin-top: 20px" align="right">
								<label>หมวดวัสดุ : </label> <select id="item_category_search"
									name="item_category_search" class="form-control">
									<option value="-"
										<c:if test="${ item_search_doc.item_category == '-' }">selected</c:if>>-</option>
									<option value="วัสดุสํานักงาน"
										<c:if test="${ item_search_doc.item_category == 'วัสดุสํานักงาน' }">selected</c:if>>วัสดุสํานักงาน</option>
									<option value="วัสดุคอมพิวเตอร์"
										<c:if test="${ item_search_doc.item_category == 'วัสดุคอมพิวเตอร์' }">selected</c:if>>วัสดุคอมพิวเตอร์</option>
									<option value="วัสดุงานบ้านงานครัว"
										<c:if test="${ item_search_doc.item_category == 'วัสดุงานบ้านงานครัว' }">selected</c:if>>วัสดุงานบ้านงานครัว</option>
									<option value="วัสดุไฟฟ้า"
										<c:if test="${ item_search_doc.item_category == 'วัสดุไฟฟ้า' }">selected</c:if>>วัสดุไฟฟ้า</option>
								</select> <label>ชื่อวัสดุ : </label> <input class="form-control"
									type="text" id="item_name_search" name="item_name_search"
									value="${ item_search_doc.item_name }">
								<button type="submit" formaction="do_searchItemDocument"
									class="btn btn-sky text-uppercase">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</div>
							<div class="table-responsive">
								<table style="margin-top: 10px">
									<tr>
										<th style="text-align: center;">ลําดับ</th>
										<th style="text-align: center;">หมวด</th>
										<th style="text-align: center;">ชื่อวัสดุ</th>
										<th style="text-align: center;">ราคากลางวัสดุ</th>
										<th style="text-align: center;">หน่วย</th>
										<th style="text-align: center;">ใส่ตะกร้า</th>
									</tr>
									<c:forEach var="items" items="${ items }" varStatus="loop">
										<tr>
											<td style="text-align: center;">${ loop.index+1 }</td>
											<td style="text-align: center;">${ items.getItem_category() }</td>
											<td style="text-align: center;">${ items.getItem_name() }</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ items.getItem_price() }" /></td>
											<td style="text-align: center;">${ items.getItem_unit() }</td>
											<%-- 	<a class=""
												href="do_addItemCart?item_id=${ items.getItem_id() }"><i
													class="fa fa-shopping-cart fa-2x" aria-hidden="true"></i></a> --%>
											<td style="text-align: center;"><button
													formaction="do_addItemCart?item_id=${ items.getItem_id() }"
													class="btn btn-xs btn-fresh">
													เลือกรายการ <i class="fa fa-shopping-cart"
														aria-hidden="true"></i>
												</button></td>
										</tr>
									</c:forEach>


								</table>
								<c:if test="${items.size() == 0 }">
									<h5 align="center" style="color: red">ไม่พบข้อมูลวัสดุ !!</h5>
								</c:if>
							</div>
							<!--  item_cart table -->
							<c:if test="${ not empty  item_cart}">
								<div class="table-responsive" style="margin-top: 30px;">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i> รายการเสนอซื้อ/จ้าง
										</legend>

										<div class="table-responsive">
											<table style="margin-top: 10px">
												<tr>
													<th>ลําดับ</th>
													<th>ชื่อวัสดุ</th>
													<th>ราคากลางวัสดุ</th>
													<th>จํานวน</th>
													<th>หน่วย</th>
													<th>ราคารวม</th>
													<th>ลบ</th>
												</tr>
												<c:forEach var="items" items="${ item_cart }"
													varStatus="loop">
													<tr>
														<td>${ loop.index+1 }<input type="hidden"
															name="item_cart_check" value="${ loop.index }"
															class="item_cart_check"></td>
														<td>${ items.getId().getItem().getItem_name() }<input
															type="hidden" name="item_id" class="item_check_dup"
															value="${ items.getId().getItem().getItem_id()}"></td>
														<td><input type="text"
															class="form-control item_price" name="item_price"
															value="${ items.getItem_price() }" onkeyup="caltotal()"></td>
														<td><input type="text" id="item_total"
															name="item_total" class="form-control item_total"
															onkeyup="caltotal()" value="${ items.getAmount() }"></td>
														<td>${ items.getId().getItem().getItem_unit() }</td>

														<td><label for="item_total_price_l"
															class="item_total_price_l control-label">0</label> บาท <input
															type="hidden"
															class="form-control col-sm-1 item_total_price"
															id="item_total_price" name="item_total_price" value=""></td>
														<%-- 	<a class="trash"
															href="do_deleteItemCart?item_id=${ items.getId().getItem().getItem_id() }"
															onclick="return confirm('คุณต้องต้องการลบ ${ items.getId().getItem().getItem_name() } ออกจากรายการของคุณ ?');"><i
																class="fa fa-trash fa-2x" aria-hidden="true"
																style="color: red;"></i></a> --%>
														<td><button class="btn-xs btn btn-danger"
																onclick="return confirm('คุณต้องต้องการลบ ${ items.getId().getItem().getItem_name() } ออกจากรายการของคุณ ?');"
																formaction="do_deleteItemCart?item_id=${ items.getId().getItem().getItem_id() }">
																ลบรายการ <i class="fa fa-trash " aria-hidden="true"></i>
															</button></td>
													</tr>
												</c:forEach>
												<tr>
													<td class="textAling" colspan="5">ราคารวม :</td>
													<td colspan="2" style="text-align: center"><label
														for="grand_total" id="grand_total">0</label> บาท</td>
												</tr>
											</table>
										</div>
									</fieldset>
								</div>
								<!--  end item_cart -->
								<div class="row">
									<div class="" style="margin: 20px">
										<div class="col-sm-12">
											<div class="row" style="margin: 10px">
												<div class="form-group">
													<label class="col-sm-2 control-label"
														style="margin-left: 44px;">ในวงเงิน : </label>
													<div class="col-sm-5">
														<input type="text" id="price_txt" name="price_txt"
															class="form-control" value="${ document.getPrice_txt() }">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!--  end first section -->
							</c:if>
						</div>
						<div class="tabcontent" id="repair"
							style="display:<c:if test="${ check_type == 'r' }">block</c:if><c:if test="${ check_type != 'r' }"> none</c:if>">
							<div class="row" style="margin-top: 20px">
								<div class="col-sm-12">
									<fieldset>
										<legend align="left" style="font-size: 1em">รายการซ่อมบํารุง</legend>
										<div class="row">
											<div class="col-sm-12">
												<div class="row" style="margin: 10px">
													<div class="form-group">
														<label class="col-sm-2 control-label"
															style="margin-left: 44px;">รายละเอียดการซ่อม : </label>
														<div class="col-sm-8">
															<textarea class="form-control" id="repair_detail"
																name="repair_detail" rows="4"></textarea>
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label"
															style="margin-left: 44px;">จํานวน : </label>
														<div class="col-sm-2">
															<input type="text" name="repair_amount"
																id="repair_amount" class="form-control">
														</div>
														<label class="col-sm-1 control-label"
															style="margin-left: 44px;">หน่วย : </label>
														<div class="col-sm-2">
															<input type="text" name="repair_unit" id="repair_unit"
																class="form-control">
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label"
															style="margin-left: 44px;">ราคา : </label>
														<div class="col-sm-3">
															<input type="text" name="repair_price" id="repair_price"
																class="form-control">
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="margin: 10px">
											<div class="form-inline" align="right">
												<button class="btn btn-fresh" formaction="do_addRepairList"
													onClick="return check_repair(purchaseOrderRequestFrm)">
													<i class="fa fa-plus"></i> เพิ่มรายการ
												</button>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div style="margin: 10px" class="table-responsive">
										<table>
											<tr>
												<th style="text-align: center">ลําดับ</th>
												<th style="text-align: center">รายการ</th>
												<th style="text-align: center">จํานวน</th>
												<th style="text-align: center">ราคา/หน่วย</th>
												<th style="text-align: center">ราคารวม</th>
												<th style="text-align: center">เพิ่มเติม</th>
											</tr>
											<c:forEach var="repair" items="${ repair }" varStatus="loop">
												<tr>

													<td>${ loop.index +1 }<input type="hidden"
														name="r_check" value="${ loop.index }"></td>
													<td>${ repair.getId().getItem().getItem_name() }</td>
													<td>${ repair.getAmount() }${ repair.getId().getItem().getItem_unit() }</td>
													<td><fmt:formatNumber type="number"
															minFractionDigits="2" maxFractionDigits="2"
															value="${ repair.getItem_price() }" /></td>
													<td><fmt:formatNumber type="number"
															minFractionDigits="2" maxFractionDigits="2"
															value="${ repair.getTotal_price() }" /></td>
													<td style="text-align: center"><button
															class="btn btn-sunny" type="button"
															value="${ loop.index },${ repair.getId().getItem().getItem_name() },${ repair.getAmount() },${ repair.getId().getItem().getItem_unit() },${ repair.getItem_price() }"
															id="btn_edit">แก้ไข</button>
														<button formaction="deleteRepair?position=${ loop.index }"
															class="btn btn-danger"
															onclick="return confirm('คุณต้องต้องการนํารายการจ้างที่ ${ loop.index+1 } ออกจากหรือไม่ ?');">ลบ</button></td>
												</tr>
											</c:forEach>
											<tr>
												<td class="textAling" colspan="5">ราคารวม :</td>
												<td colspan="2" style="text-align: center"><label><fmt:formatNumber
															type="number" minFractionDigits="2" maxFractionDigits="2"
															value="${total_price_repair}" /></label> บาท</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="" style="margin: 20px">
									<div class="col-sm-12">
										<div class="row" style="margin: 10px">
											<div class="form-group">
												<label class="col-sm-2 control-label"
													style="margin-left: 44px;">ในวงเงิน : </label>
												<div class="col-sm-5">
													<input type="text" id="price_txt_repair"
														name="price_txt_repair" class="form-control" value="">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--  end item_search -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> กรรมการ
									</legend>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" name="committee" id="committee">
												<option value="-">-</option>
												<c:forEach items="${ committee_list }" var="committee">
													<option value="${ committee.getPersonnel_id() }">${committee.getPersonnel_prefix() }
														${ committee.getPersonnel_firstName() } ${ committee.getPersonnel_lastName() }
														ตําแหน่ง ${ committee.getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-3">
											<!-- <button onclick="createInput();" class="btn btn-fresh">เพิ่มกรรมการ</button>  -->
											<input type="submit" onclick="return checkDupCommittee()"
												formaction="do_addCommittee" class="btn btn-sky"
												value="เพิ่มกรรมการ">
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> รายชื่อกรรมการ
									</legend>
									<div class="row table-responsive" style="margin: 10px">
										<table>
											<tr>
												<th>ลําดับ</th>
												<th>ชื่อ</th>
												<th>ตําแหน่ง</th>
												<th>แต่งตั้งเป็น</th>
												<th>นําออก</th>
											</tr>
											<c:forEach var="committees" items="${ committees }"
												varStatus="loop">
												<tr>
													<td style="text-align: center">${ loop.index+1 }<input
														type="hidden" name="committees_check"
														value="${ loop.index }"><input type="hidden"
														name="c_id_dup_check" id="c_id_dup_check"
														class="c_id_dup_check"
														value="${ committees.getCommittee().getPersonnel().getPersonnel_id() }"></td>
													<td>${ committees.getCommittee().getPersonnel().getPersonnel_prefix() }${ committees.getCommittee().getPersonnel().getPersonnel_firstName() }
														${ committees.getCommittee().getPersonnel().getPersonnel_lastName() }</td>
													<td>${ committees.getCommittee().getPersonnel().getPosition().getPosition_name() }</td>
													<td>${ committees.getCommittee_status() }</td>
													<td><button
															formaction="do_removeCommittee?committee_index=${ loop.index }"
															class="btn btn-xs btn-danger"
															onclick="return confirm('คุณต้องต้องการนํา ${ committees.getCommittee().getPersonnel().getPersonnel_prefix() }${ committees.getCommittee().getPersonnel().getPersonnel_firstName() } ${ committees.getCommittee().getPersonnel().getPersonnel_lastName() } ออกจากรายชื่อกรรมการ ?');">
															นําออก <i class="fa fa-times" aria-hidden="true"></i>
														</button></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</fieldset>
							</div>
						</div>

						<!--  start signature -->

						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6">
								<fieldset>
									<!--  Requisition Person -->
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้เสนอขอซื้อ/จ้าง
									</legend>
									<div>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" name="doc_1_1" id="doc_1_1">
													<option value="-">-</option>
													<c:forEach items="${ doc_1_1 }" var="doc_1_1">
														<option
															<c:if test="${ document.getRequest_order_person() == doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_id() }"> selected</c:if>
															value="${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_1_1.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>

									</div>
								</fieldset>
							</div>
							<c:if test="${ sessionScope.staffSession.major.ID_Major == 999 }">
								<div class="col-sm-6">
									<fieldset>
										<!-- COF -->
										<legend style="font-size: 1em">
											<i class="fa fa-user" aria-hidden="true"></i>
											หัวหน้างานคลังและพัสดุ
										</legend>
										<div class="row" style="margin: 10px">
											<div>
												<div class="form-group">
													<label class="col-sm-3 control-label">รายชื่อ : </label>
													<div class="col-sm-6">
														<select class="form-control" name="doc_1_2" id="doc_1_2">
															<c:forEach items="${ doc_1_2 }" var="doc_1_2">
																<option
																	<c:if test="${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_id() == document.getChief_of_procurement() }">selected</c:if>
																	value="${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
																	${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																	${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
																	ตําแหน่ง ${ doc_1_2.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
										</div>
									</fieldset>
								</div>
							</c:if>
						</div>

						<div class="row">
							<div class="col-sm-6">
								<!--  accouting person -->
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										เจ้าหน้าที่ลงบัญชี
									</legend>
								</fieldset>
								<div>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_1_3" name="doc_1_3">
												<option value="-">-</option>
												<c:forEach items="${ doc_1_3 }" var="acc_old">
													<option
														<c:if test="${ document.getAccounting_officer() ==  acc_old.getPersonnelAssign().getPersonnel().getPersonnel_id()}">selected</c:if>
														value="${ acc_old.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ acc_old.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${ acc_old.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
														${ acc_old .getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${ acc_old.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<!--  secretary -->
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้เห็นชอบ/อนุมัติ เพื่อดําเนินการ
									</legend>
								</fieldset>
								<div>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_1_4" name="doc_1_4">
												<option value="-">-</option>
												<c:forEach items="${ doc_1_4 }" var="old_secretary">
													<option
														<c:if test="${ old_secretary.getPersonnelAssign().getPersonnel().getPersonnel_id() == document.getSecretary() }">selected</c:if>
														value="${ old_secretary.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ old_secretary.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${ old_secretary.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
														${ old_secretary.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${ old_secretary.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase" id=""
									onClick="return purchaseOrderRequestCheck(purchaseOrderRequestFrm)">
									<i class="fa fa-floppy-o"></i> บันทึก
								</button>
								<button class="btn btn-hot text-uppercase"
									formaction="backLoadListDocument">
									<i class="fa fa-times"></i> ยกเลิก
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="modal fade" id="editModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header" style="background-color: #283e56;">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 style="background-color: #283e56;">แก้ไขข้อมูลการตรวจสอบ</h4>
					</div>
					<div class="modal-body">
							<input type="hidden" name="position_edit" id="position_edit"
								value="">
							<div class="form-group">
								<div class="row">
									<div class="column">
										<label for="exampleFormControlInput1">รายการ</label>
									</div>
									<div class="column02">
										<textarea id="repair_detail_edit" name="repair_detail_edit"
											class="form-control"></textarea>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<div class="column">
										<label for="exampleFormControlInput1">จํานวน</label>
									</div>
									<div class="column02">
										<input type="text" class="form-control"
											id="repair_amount_edit" name="repair_amount_edit"
											style="width: 200px">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<div class="column">
										<label for="exampleFormControlInput1">หน่วย</label>
									</div>
									<div class="column02">
										<input type="text" class="form-control" id="repair_unit_edit"
											name="repair_unit_edit" style="width: 200px">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<div class="column">
										<label for="exampleFormControlInput1">ราคา</label>
									</div>
									<div class="column02">
										<input type="text" class="form-control" id="repair_price_edit"
											name="repair_price_edit" style="width: 200px">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="column03">
									<button type="submit" formaction="do_editRepair" onClick="return check_repair()" class="btn btn-success btn-block">แก้ไข</button>
								</div>
							</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script
		src="${pageContext.request.contextPath}/dist/js/custom/purchaseOrderRequestCheck.js"></script>
	​
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
	<c:remove var="item_search" scope="session" />
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
</body>
</html>