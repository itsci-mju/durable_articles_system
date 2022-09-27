<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, java.text.*, ac.th.itsci.durable.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>เอกสารขอซื้อ/จ้าง</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="print_PurchaseOrderDocument"
				name="purchaseOrderRequestFrm" class="form-horizontal" method="post">
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
											<label class="col-sm-5 control-label"> </label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getPlan_id() }</label>
											<input type="hidden" name="doc_id" value="${ purchaseOrderDoc.getDoc_id() }">
											<input type="hidden" name="purchaseOrderID" value="${ purchaseOrderDoc.getPurchaseOrderDocument_id() }">
											<input type="hidden" name="item_size" value="${ purchaseOrderDoc.getRequisitionItemLists().size() }">
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label> <label class="col-sm-5 control-label">${ purchaseOrderDoc.getDepart_id() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getFund_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getMoney_used() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getBudget() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรื่อง : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getDoc_title() }
											</label>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่เอกสาร :
											</label> <label class="col-sm-5 control-label">${ purchaseOrderDoc.getPurchaseOrderDocument_id() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getFund_name() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ : </label> <label
												class="col-sm-5 control-label">${ date }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เรียน : </label> <label
												class="col-sm-5 control-label">${ purchaseOrderDoc.getDoc_dear() }</label>
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
											<th style="text-align: center;">ลําดับที่</th>
											<th style="text-align: center;">เลขที่ใบเสนอซื้อ/จ้าง</th>
											<th style="text-align: center;">รายการ</th>
											<th style="text-align: center;">หน่วยนับ</th>
											<th style="text-align: center;">จํานวน</th>
											<th style="text-align: center;">ราคา/หน่วย</th>
											<th style="text-align: center;">ราคากลางของพัสดุที่จะซื้อ</th>
											<th style="text-align: center;">วงเงินที่จะซื้อ (ราตารวม
												Vat)</th>
											<th style="text-align: center;">กําหนดเวลาที่แล้วเสร็จ</th>
											<th style="text-align: center;">หมายเหตุ</th>
										</tr>
										<c:forEach
											items="${ purchaseOrderDoc.getRequisitionItemLists() }"
											var="item_cart" varStatus="loop">
											<tr>
												<td style="text-align: center;">${ loop.index+1 }</td>
												<td style="text-align: center;">${ purchaseOrderDoc.getDoc_id() }<input
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
												<td style="text-align: center;">${ prescription_date.get(loop.index) }</td>
												<td style="text-align: center;">${ item_cart.getNote() }</td>
											</tr>
										</c:forEach>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td colspan="4" style="text-align: center;">จํานวนเงินก่อนภาษีมูลค่าเพิ่ม</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDoc.getPrice_with_out_tax() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td colspan="4" style="text-align: center;">ภาษีมูลค่าเพิ่ม
												7%</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDoc.getTax() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="3">รวม
												${purchaseOrderDoc.getRequisitionItemLists().size() } รายการ</td>
											<td colspan="4" style="text-align: center;">รวมเป็นเงินทั้งสิ้น</td>
											<td style="text-align: center;"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ purchaseOrderDoc.getTotal_price() }" /></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="3" style="text-align: center;">จํานวนเงิน
												(ตัวอักษร)</td>
											<td colspan="7" style="text-align: center;">${ purchaseOrderDoc.getPrice_txt() }</td>
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
										<label class="col-sm-12 " style="margin-left: 80px">เหตุผล/ความจําเป็น
											: ${ purchaseOrderDoc.getDoc_reason_describe() }</label>

									</div>
								</div>
							</div>
						</div>
						<div class="row" style="margin: 20px">
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-5 control-label">จัดซื้อจัดจ้าง
										โดยวิธี : </label> <label class="col-sm-5 control-label">${ purchaseOrderDoc.getPurchaseOrder_type() }</label>
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
										<c:forEach items="${ purchaseOrderDoc.getCommittee() }"
											var="committee" varStatus="loop">
											<tr>
												<td>${ loop.index+1 }</td>
												<td>${ committee.getCommittee().getPersonnel().getPersonnel_prefix() }${ committee.getCommittee().getPersonnel().getPersonnel_firstName() }
													${ committee.getCommittee().getPersonnel().getPersonnel_lastName() }</td>
												<td>${ committee.getCommittee().getPersonnel().getPosition().getPosition_name() }</td>
												<td>กรรมการ</td>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${documentor.getPersonnel_prefix() }${ documentor.getPersonnel_firstName() }
												${ documentor.getPersonnel_lastName() } </label><br> <label
												class="control-label">ผู้จัดทําเอกสาร </label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${purchasePerson.getPersonnel_prefix() }${ purchasePerson.getPersonnel_firstName() }
												${ purchasePerson.getPersonnel_lastName() } </label><br> <label
												class="control-label">ผู้จัดซื้อจัดจ้าง </label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group " align="center">
											<label class="control-label">${aoc.getPersonnel_prefix() }${ aoc.getPersonnel_firstName() }
												${ aoc.getPersonnel_lastName() } </label><br> <label
												class="control-label">ผู้ตรวจสอบบัญชีงบประมาณอนุมัติ
											</label>
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
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${cofPerson.getPersonnel_prefix() }${ cofPerson.getPersonnel_firstName() }
												${ cofPerson.getPersonnel_lastName() } </label><br> <label
												class="control-label">${  cofPerson.getAssignType().getAssignType_name()}
											</label>
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
										เลขานุการคณะวิทยาศาสตร์
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
							<div class="col-sm-6 col-md-6 col-lg-6">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-user" aria-hidden="true"></i>
										คณบดีคณะวิทยาศาสตร์
									</legend>
									<div class="row" style="margin: 10px">
										<div class="form-group" align="center">
											<label class="control-label">${dean.getPersonnel_prefix() }${ dean.getPersonnel_firstName() }
												${ dean.getPersonnel_lastName() } </label><br> <label
												class="control-label">คณบดีคณะวิทยาศาสตร์ </label>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
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

	<script
		src="${pageContext.request.contextPath}/dist/js/custom/calculate.js"></script>
	<c:if test="${not empty sessionScope.messages}">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
	</c:if>
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