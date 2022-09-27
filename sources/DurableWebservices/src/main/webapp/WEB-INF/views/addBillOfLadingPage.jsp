<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>เพิ่มใบเบิกพัสดุ</title>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/billOfLadingCheck.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="do_addBillOfLading" name="billOfLading"
				class="form-horizontal" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">ใบเบิกพัสดุ</h3>
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
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getPlan_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label> <label class="col-sm-5 control-label">${ purchaseOrderDocument.getDepart_id() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getFund_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getMoney_used() }
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
												<input type="text" id="billOfLading_id"
													name="billOfLading_id" placeholder="เลขที่เอกสาร"
													class="form-control" value="${ purchaseOrderDocument.getPurchaseOrderDocument_id() }"> <input type="hidden"
													id="doc_id" name="doc_id"
													value="${ purchaseOrderDocument.getDoc_id()}">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getFund_name() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDocument.getBudget() }</label>
										</div>
										<!-- 										<div class="form-group"> -->
										<!-- 											<label class="col-sm-5 control-label">วันที่ : </label> -->
										<!-- 											<div class="col-sm-5"> -->
										<!-- 												<input type="date" id="date" name="date" -->
										<!-- 													class="form-control" value=""> -->
										<!-- 											</div> -->
										<!-- 										</div> -->
									</div>
								</div>
							</div>

						</fieldset>
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table border="1" id="tableform">
										<tr align="center">
											<th style="text-align: center">ที่</th>
											<th style="text-align: center">หมายเลขพัสดุ</th>
											<th style="text-align: center">รายการ</th>
											<th style="text-align: center">จํานวน</th>
											<th style="text-align: center">หน่วย</th>
											<th style="text-align: center">ราคา/หน่วย</th>
											<th style="text-align: center;">จํานวนเงิน</th>
											<th style="text-align: center;">เหตุผลที่ขอเบิก</th>
										</tr>

										<c:forEach
											items="${ purchaseOrderDocument.getRequisitionItemLists() }"
											var="item_cart" varStatus="loop">
											<tr>
												<td style="text-align: center">${ loop.index+1 }</td>
												<td><input type="hidden" name="item_id"
													value="${ item_cart.getId().getItem().getItem_id() }"></td>
												<td>${ item_cart.getId().getItem().getItem_name() }</td>
												<td style="text-align: center">${ item_cart.getAmount() }</td>
												<td style="text-align: center">${ item_cart.getId().getItem().getItem_unit() }</td>
												<td style="text-align: center"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getItem_price() }" /></td>
												<td style="text-align: center"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getTotal_price() }" /></td>
												<td style="text-align: center">${ item_cart.getNote() }</td>
											</tr>
										</c:forEach>
										<tr>
											<td style="text-align: center" colspan="6">จํานวนเงินก่อนภาษีมูลค่าเพิ่ม</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDocument.getPrice_with_out_tax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center" colspan="6">ภาษีมูลค่าเพิ่ม
												7%</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDocument.getTax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center" colspan="6">รวมเป็นเงินทั้งสิ้น</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDocument.getTotal_price() }" /></td>
											<td>บาท</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12"
								style="text-align: right;">
								<label>${ receiveDocument.getPrice_txt() }</label>
							</div>
						</div>
						<!-- signature -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> อนุญาติให้จ่าย
									</legend>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_4_1"
												name="doc_4_1">
												<option value="-">-</option>
												<c:forEach items="${ doc_4_1 }" var="doc_4_1">
													<option value="${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${ doc_4_1.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้เบิก
									</legend>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_4_2"
												name="doc_4_2">
												<option value="-">-</option>
												<c:forEach items="${ doc_4_2 }" var="doc_4_2">
													<option value="${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${ doc_4_2.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้รับของ
									</legend>
									<div>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" id="doc_4_3"
													name="doc_4_3">
													<option value="-">-</option>
													<c:forEach items="${ doc_4_3 }" var="doc_4_3">
														<option value="${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
															${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
															ตําแหน่ง ${ doc_4_3.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
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
										บันทึกลงบัญชีวัสดุแล้ว
									</legend>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_4_5"
												name="doc_4_5">
												<option value="-">-</option>
												<c:forEach items="${ doc_4_5 }" var="doc_4_5">
													<option	<c:if test="${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_id() == purchaseOrderDocument.getAccounting_officer() }">selected</c:if>
													 value="${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${  doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${  doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${  doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${  doc_4_5.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้จ่ายของ
									</legend>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-6">
											<select class="form-control" id="doc_4_4"
												name="doc_4_4">
												<option value="-">-</option>
												<c:forEach items="${ doc_4_4 }" var="doc_4_4">
													<option
													 value="${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_id() }">${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }
														${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }
														ตําแหน่ง ${ doc_4_4.getPersonnelAssign().getPersonnel().getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</fieldset>
							</div>

						</div>
						<!--  end signature -->
					</div>



					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase" id=""
									onClick="return checkBill(billOfLading)">
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

	<script src="/dist/js/custom/calculate.js"></script>
	<script>
		function openPurchase(receive) {
			var i;
			var x = document.getElementsByClassName("receive");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(receive).style.display = "block";
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