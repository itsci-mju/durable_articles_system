<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>
<head>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<meta charset="UTF-8">
<title>เอกสารเสนอซื้อ/จ้าง</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="print_purchaseOrderRequest" name=""
				class="form-horizontal" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">เอกสารเสนอซื้อ/จ้าง</h3>
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
											</label> <label class="col-sm-5 control-label">${ purchaseOrderRequest.getDoc_id() }</label>
											<input type="hidden"
												value="${ purchaseOrderRequest.getDoc_id() }" name="doc_id">
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
												class="col-sm-5 control-label">${ purchaseOrderRequest.getMoney_used() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getBudget() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรื่อง : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getDoc_title() }</label>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label"></label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label"></label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getFund_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ : </label> <label
												class="col-sm-5 control-label">${ date }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรียน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderRequest.getDoc_dear() }</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<!-- 								<div class="col-sm-6 col-md-6 col-lg-6"> -->
								<!-- 									<div class="" style="margin: 10px"> -->
								<!-- 										<div class="form-group"> -->
								<!-- 											<label class="col-sm-6 control-label">รายละเอียด : </label> -->
								<!-- 										</div> -->
								<!-- 										<div class="form-group"> -->
								<!-- 											<label class="col-sm-6 control-label">เหตุผล/ความจําเป็น -->
								<!-- 												:</label> -->

								<!-- 										</div> -->
								<!-- 									</div> -->

								<!-- 								</div> -->
								<div class="col-sm-1 col-md-1 col-lg-1"></div>
								<div class="col-sm-10 col-md-10 col-lg-10">
									<div class="form-group">
										<label class="col-sm-10 ">${ purchaseOrderRequest.getDoc_title_decribe() }</label>
									</div>
									<div class="form-group">
										<label class="col-sm-10"><b>เหตุผล/ความจําเป็น</b> ${ purchaseOrderRequest.getDoc_reason_describe() }</label>
									</div>
								</div>
							</div>
						</fieldset>
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table>
										<tr>
											<th style="text-align: center;" width="90">ลําดับที่</th>
											<th style="text-align: center;" width="200">เลขที่ใบเสนอซื้อ/จ้าง</th>
											<th style="text-align: center;" width="300">รายการ</th>
											<th style="text-align: center;">หน่วยนับ</th>
											<th style="text-align: center;">จํานวน</th>
											<th style="text-align: center;">ราคา/หน่วย</th>
											<th style="text-align: center;">ราคากลางของพัสดุที่จะซื้อ</th>
											<th style="text-align: center;">วงเงินที่จะซื้อ (ราคารวม
												Vat)</th>
											<th style="text-align: center;">กําหนดเวลาที่แล้วเสร็จ</th>
											<th style="text-align: center;">หมายเหตุ</th>
										</tr>
										<c:forEach
											items="${ purchaseOrderRequest.getRequisitionItemLists() }"
											var="item_cart" varStatus="loop">
											<tr>
												<td style="text-align: center;">${ loop.index+1 }</td>
												<td style="text-align: center;">${ purchaseOrderRequest.getDoc_id() }<input
													type="hidden" name="item_id"
													value="${ item_cart.getId().getItem().getItem_id() }"></td>
												<td style="text-align: center;">${ item_cart.getId().getItem().getItem_name() }</td>

												<td style="text-align: center;">${ item_cart.getId().getItem().getItem_unit() }</td>
												<td style="text-align: center;">${ item_cart.getAmount() }</td>
												<td style="text-align: center;"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getItem_price() }" /></td>
												<td style="text-align: center;"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getItem_price() }" /></td>
												<td style="text-align: center;"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getTotal_price() }" /></td>
												<td></td>
												<td></td>
											</tr>
										</c:forEach>
										<tr>
											<td style="text-align: center;" colspan="7">จํานวนเงินก่อนภาษีมูลค่าเพิ่ม</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderRequest.getPrice_with_out_tax() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="7">ภาษีมูลค่าเพิ่ม
												7%</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderRequest.getTax() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="7">รวมเป็นเงินทั้งสิ้น</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderRequest.getTotal_price() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="3">จํานวนเงิน (ตัวอักษร)</td>
											<td style="text-align: center;" colspan="7">${ purchaseOrderRequest.getPrice_txt() }</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<!--  end item_cart -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-12 col-md-12 col-lg-12">
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
											</tr>
											<c:forEach var="committees"
												items="${ purchaseOrderRequest.getCommittee() }"
												varStatus="loop">
												<tr>
													<td>${ loop.index+1 }</td>
													<td>${ committees.getCommittee().getPersonnel().getPersonnel_prefix() }${ committees.getCommittee().getPersonnel().getPersonnel_firstName() }
														${ committees.getCommittee().getPersonnel().getPersonnel_lastName() }</td>
													<td>${ committees.getCommittee().getPersonnel().getPosition().getPosition_name() }</td>

												</tr>
											</c:forEach>
										</table>
									</div>
								</fieldset>
							</div>
						</div>

						<!--  start signature -->

						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้จัดทําเอกสาร
									</legend>
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${requestOrderPerson.getPersonnel_prefix() }${ requestOrderPerson.getPersonnel_firstName() }
												${ requestOrderPerson.getPersonnel_lastName() } </label><br> <label
												class="control-label">${ requestOrderPerson.getPosition().getPosition_name() }
												${ requestOrderPerson.getPersonnel_tier() }</label><br> <label
												class="control-label">ผู้เสนอขอซื้อ / จ้าง </label>
										</div>
									</div>
								</fieldset>
							</div>
							<c:if
								test="${ purchaseOrderRequest.getDepart_name() == 'คณะวิทยาศาสตร์์'}">
								<div class="col-sm-6 col-md-6 col-lg-6">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-user" aria-hidden="true"></i>
											หัวหน้างานคลังและพัสดุ
										</legend>
										<div class="row" style="margin: 10px">
											<div class="form-group" align="center">
												<label class="control-label">${cofPerson.getPersonnel_prefix() }${ cofPerson.getPersonnel_firstName() }
													${ cofPerson.getPersonnel_lastName() } </label><br> <label
													class="control-label">${ cofPerson.getPosition().getPosition_name() }
													${ cofPerson.getPersonnel_tier() }</label><br> <label
													class="control-label">${  cofPerson.getAssignType().getAssignType_name()}
												</label>
											</div>
										</div>
									</fieldset>
								</div>
							</c:if>
						</div>

						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้ตรวจสอบบัญชีงบประมาณอนุมัติ
									</legend>
									<div class="row" style="margin: 10px">
										<div class="form-group " align="center">
											<label class="control-label">${aocPerson.getPersonnel_prefix() }${ aocPerson.getPersonnel_firstName() }
												${ aocPerson.getPersonnel_lastName() } </label><br> <label
												class="control-label">${ aocPerson.getPosition().getPosition_name() }
												${ aocPerson.getPersonnel_tier() }</label><br> <label
												class="control-label">เจ้าหน้าที่ลงบัญชี </label>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										ผู้เห็นชอบ/อนุมัติ
									</legend>
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${secretary.getPersonnel_prefix() }${ secretary.getPersonnel_firstName() }
												${ secretary.getPersonnel_lastName() } </label><br> <label
												class="control-label">${ secretary.getPosition().getPosition_name() }</label><br>
											<label class="control-label">${  secretary.getAssignType().getAssignType_name()}
											</label>
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
									formtarget="_blank">
									<i class="fa fa-print" aria-hidden="true"></i> พิมพ์
								</button>
								<button type="submit" formaction="backLoadListDocument"
									class="btn btn-danger text-uppercase" id="">ย้อนกลับ</button>

							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<c:if test="${not empty sessionScope.messages}">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
	</c:if>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>


</body>
</html>