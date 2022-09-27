<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.text.*, ac.th.itsci.durable.entity.*"%>
<!DOCTYPE html>
<%
	PurchaseOrderDocument d = (PurchaseOrderDocument) request.getAttribute("date");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date(d.getDate().getTime());
Date prescript = new Date(d.getRequisitionItemLists().get(0).getDate().getTime());
%>
<html>
<head>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/purchaseOrderScript.js"></script>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>แก้ไขรายการเอกสารขอซื้อ/จ้าง</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="do_editPurchaseOrderDocument" name="purchaseOrderFrm"
				class="form-horizontal" method="post">
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
											<label class="col-sm-5 control-label"> </label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getMoney_used() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getBudget() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรื่อง : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getDoc_title() }
											</label>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่เอกสาร :
											</label>
											<div class="col-sm-5">
												<input type="text" id="purchaseOrder_id"
													name="purchaseOrder_id" placeholder="เลขที่เอกสาร"
													class="form-control"
													value="${ purchaseOrder.getPurchaseOrderDocument_id() }"
													readonly="readonly"> <input type="hidden"
													id="doc_id" name="doc_id"
													value="${ purchaseOrder.getDoc_id()}">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getFund_name() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ : </label>
											<div class="col-sm-5">
												<input type="date" id="date" name="date"
													class="form-control" value="<%=formatter.format(date)%>">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรียน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrder.getDoc_dear() }</label>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">กําหนดเวลาแล้วเสร็จ
												: </label>
											<div class="col-sm-5">
												<input type="date" class="form-control"
													value="<%=formatter.format(prescript)%>"
													name="prescription" id="prescription">
											</div>
										</div>
									</div>
								</div>
							</div>

						</fieldset>
						<!--  item_cart table -->
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table>
										<tr>
											<th>ลําดับที่</th>
											<th>เลขที่ใบเสนอซื้อ/จ้าง</th>
											<th>รายการ</th>
											<th>หน่วยนับ</th>
											<th>จํานวน</th>
											<th>ราคา/หน่วย</th>
											<th>ราคากลางของพัสดุที่จะซื้อ</th>
											<th>วงเงินที่จะซื้อ (ราตารวม Vat)</th>
											<!-- <th>กําหนดเวลาที่แล้วเสร็จ</th> -->
											<th>หมายเหตุ</th>
										</tr>
										<c:forEach
											items="${ purchaseOrder.getRequisitionItemLists() }"
											var="item_cart" varStatus="loop">
											<tr>
												<td>${ loop.index+1 }</td>
												<td>${ purchaseOrderRequest.getDoc_id() }<input
													type="hidden" name="item_id"
													value="${ item_cart.getId().getItem().getItem_id() }"></td>
												<td>${ item_cart.getId().getItem().getItem_name() }</td>

												<td>${ item_cart.getId().getItem().getItem_unit() }</td>
												<td>${ item_cart.getAmount() }</td>
												<td><fmt:formatNumber type="number"
														minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getItem_price() }" /></td>
												<td><fmt:formatNumber type="number"
														minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getId().getItem().getItem_price() }" /></td>
												<td><fmt:formatNumber type="number"
														minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getTotal_price() }" /></td>
												<%-- <td><input type="date"
													value="<%=formatter.format(prescription.getTime())%>"
													name="prescription" id="prescription" class="form-control"></td> --%>
												<td><textarea name="note" id="note"
														class="form-control">${ item_cart.getNote() }</textarea></td>
											</tr>
										</c:forEach>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td colspan="4">จํานวนเงินก่อนภาษีมูลค่าเพิ่ม</td>
											<td><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrder.getPrice_with_out_tax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td colspan="4">ภาษีมูลค่าเพิ่ม 7%</td>
											<td><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrder.getTax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="3">รวม ${item_cart.size() } รายการ</td>
											<td colspan="4">รวมเป็นเงินทั้งสิ้น</td>
											<td><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrder.getTotal_price() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="3">จํานวนเงิน (ตัวอักษร)</td>
											<td colspan="6">${ purchaseOrder.getPrice_txt() }</td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<!--  end item_cart -->
						<div class="row">
							<div class="" style="margin: 20px">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="">เหตุผล/ความจําเป็น : ${ purchaseOrder.getDoc_reason_describe() }</label>

									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="" style="margin: 20px">
								<div class="col-sm-6">
									<div class="form-group">
										<label class="col-sm-4">จัดซื้อจัดจ้าง โดยวิธี : </label>
										<div class="col-sm-5">
											<input type="text" id="purchase_orderType"
												name="purchase_orderType" placeholder=""
												class="form-control" value="เฉพาะเจาะจง">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-12">
								<label class="">เนื่องจากการจัดซื้อจัดจ้างพัสดุที่มีการผลิด
									จําหน่าย ก่อสร้าง หรือให้บริการทั่วไป
									และมีวงเงินในการจัดซื้อจัดซื้อจัดจ้างครั้งนึงไม่เกิน 500,000
									บาท ที่กําหนดในกฎกระทรวง หลักเกณฑ์การพิจารณาคัดเลือกข้อเสนอ <b>เกณฑ์ราคา</b>
									และเห็นควรแต่งตั้งกรรมการตรวจรับพัสดุ ดังนี้
								</label>
							</div>
						</div>
						<!--  end first section -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table>
										<tr>
											<th>ที่</th>
											<th>ชื่อ-สกุล</th>
											<th>ตําแหน่ง/สังกัด</th>
											<th>แต่งตั้งเป็น</th>
										</tr>
										<c:forEach items="${ purchaseOrder.getCommittee() }"
											var="committee" varStatus="loop">
											<tr>
												<td>${ loop.index+1 }</td>
												<td>${ committee.getCommittee().getPersonnel().getPersonnel_prefix() }${ committee.getCommittee().getPersonnel().getPersonnel_firstName() }
													${ committee.getCommittee().getPersonnel().getPersonnel_lastName() }</td>
												<td>${ committee.getCommittee().getPersonnel().getPosition().getPosition_name() }</td>
												<td>${ committee.getCommittee_status() }</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>

						<div class="row" style="margin-top: 20px">

							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้จัดทําเอกสาร
									</legend>
									<div id="old_purchase">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_1" name="doc_2_1">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_1 }" var="doc_2_1">
														<option
															<c:if test="${ purchaseOrder.getPurchase_order_1() == doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_1.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>

							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้จัดซื้อจัดจ้าง
									</legend>
									<div id="old_purchase">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_2" name="doc_2_2">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_2 }" var="doc_2_2">
														<option
															<c:if test="${ purchaseOrder.getRequisition_person() == doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_2.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้ตรวจสอบบัญชีงบประมาณอนุมัติ
									</legend>
									<div id="">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_3" name="doc_2_3">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_3 }" var="doc_2_3">
														<option
															<c:if test="${ purchaseOrder.getPurchase_order_3() == doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_3.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										หัวหน้างานคลังและพัสดุ
									</legend>
									<div id="">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_4" name="doc_2_4">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_4 }" var="doc_2_4">
														<option
															<c:if test="${ purchaseOrder.getPurchase_order_4() == doc_2_4.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_4.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_4.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้เห็นชอบ/อนุมัติ
									</legend>
									<div id="">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_5" name="doc_2_5">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_5 }" var="doc_2_5">
														<option
															<c:if test="${ purchaseOrder.getPurchase_order_5() == doc_2_5.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_5.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_5.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_5.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_5.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_5.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										คณบดีคณะวิทยาศาสตร์
									</legend>
									<div id="old_dean">
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_2_6" name="doc_2_6">
													<option value="-">-</option>
													<c:forEach items="${ doc_2_6 }" var="doc_2_6">
														<option
															<c:if test="${ purchaseOrder.getFaculty_dean() == doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_id() }">selected</c:if>
															value="${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
															${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_2_6.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
					</div>

					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase" id=""
									onClick="return checkPurchase(purchaseOrderFrm)">
									<i class="fa fa-floppy-o"></i> บันทึก
								</button>
								<button type="submit" class="btn btn-hot text-uppercase"
									formaction="backLoadListDocument">
									<i class="fa fa-times"></i> ยกเลิก
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/dist/js/custom/calculate.js"></script>
	<script>
		function openPurchase(purchase) {
			var i;
			var x = document.getElementsByClassName("purchase");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(purchase).style.display = "block";
		}

		function openDean(rp) {
			var i;
			var x = document.getElementsByClassName("dean");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(rp).style.display = "block";
		}
	</script>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
	<c:remove var="item_search" scope="session" />
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>


</body>
</html>