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
<title>ใบเบิกพัสดุ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="print_BillOfLading" name=""
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
										<input type="hidden" name="doc_id" value="${ billOfLading.getDoc_id() }">
										<div class="form-group">
											<label class="col-sm-5 control-label"> </label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getPlan_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label> <label class="col-sm-5 control-label">${ billOfLading.getDepart_id() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getFund_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getMoney_used() }
											</label>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่เอกสาร :
											</label><label class="col-sm-5 control-label">${ billOfLading.getBillOfLading_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getFund_name() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ billOfLading.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${  billOfLading.getBudget() }</label>
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
						<!--  item_cart table -->
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

										<c:forEach items="${ billOfLading.getRequisitionItemLists() }"
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
													value="${ billOfLading.getPrice_with_out_tax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center" colspan="6">ภาษีมูลค่าเพิ่ม
												7%</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ billOfLading.getTax() }" /></td>
											<td></td>
										</tr>
										<tr>
											<td style="text-align: center" colspan="6">รวมเป็นเงินทั้งสิ้น</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ billOfLading.getTotal_price() }" /></td>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${purchase_person.getPersonnel_prefix() }${ purchase_person.getPersonnel_firstName() }
												${ purchase_person.getPersonnel_lastName() } </label><br> <label
												class="control-label">${  purchase_person.getAssignType().getAssignType_name()}
											</label>
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i> ผู้เบิก
									</legend>
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${secretary.getPersonnel_prefix() }${ secretary.getPersonnel_firstName() }
												${ secretary.getPersonnel_lastName() } </label><br> <label
												class="control-label">${  secretary.getAssignType().getAssignType_name()}
											</label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${receivePerson.getPersonnel_prefix() }${ receivePerson.getPersonnel_firstName() }
												${ receivePerson.getPersonnel_lastName() } </label><br> <label
												class="control-label">ผู้รับของ </label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${acc_person.getPersonnel_prefix() }${ acc_person.getPersonnel_firstName() }
												${ acc_person.getPersonnel_lastName() } </label><br> <label
												class="control-label">${  acc_person.getAssignType().getAssignType_name()}
											</label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${purchase_person.getPersonnel_prefix() }${ purchase_person.getPersonnel_firstName() }
												${ purchase_person.getPersonnel_lastName() } </label><br> <label
												class="control-label">${  purchase_person.getAssignType().getAssignType_name()}
											</label>
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
								<button type="submit" class="btn btn-sky text-uppercase" id="" formtarget="_blank">>
									<i class="fa fa-print" aria-hidden="true"></i> พิมพ์
								</button>
								<button type="submit" formaction="backLoadListDocument" class="btn btn-danger text-uppercase"
									id="">ย้อนกลับ</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script src="/dist/js/custom/calculate.js"></script>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>


</body>
</html>