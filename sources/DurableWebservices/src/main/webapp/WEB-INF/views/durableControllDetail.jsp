<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, ac.th.itsci.durable.entity.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	DurableControll durable_controll = new DurableControll();
Double d = 0.0;

durable_controll = (DurableControll) request.getAttribute("durable_controll");
d = Double.parseDouble(durable_controll.getDurable_price());
NumberFormat formatter = new DecimalFormat("#0.00");
%>
<!DOCTYPE html>
<html>
<style>
#detail {
	color: blue;
	text-align: left;
}
</style>
<head>
<meta charset="UTF-8">
<title>ทะเบียนคุมทรัพย์สิน</title>
</head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 100px">
		<div class="container">
			<form action="do_editDurableControll" name="recordfrom"
				class="form-horizontal" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-registered" aria-hidden="true"></i>
							ทะเบียนควบคุมทรัพย์สิน
						</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-folder-o"></i> ข้อมูลครุภัณฑ์
									</legend>
									<div class="col-sm-6 col-md-6 col-lg-6">
										<div class="row" style="margin: 10px">
											<div class="form-group">
												<label class="col-sm-6 control-label">ประเภท :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_type()%></label>

											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">รหัส :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_code()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">ลักษณะ/คุณสมบัติ
													:: </label> <label class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_model()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">รุ่น/แบบ :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_serial_number()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">สถานที่ตั้ง/หน่วยงานผู้รับผิดชอบ
													:: </label> <label class="col-sm-5 control-label" id="detail"><%=durable_controll.getMajor().getMajor_Name()%></label>
											</div>
										</div>
									</div>
									<div class="col-sm-6 col-md-6 col-lg-6">
										<div class="row" style="margin: 10px">
											<div class="form-group">
												<label class="col-sm-6 control-label">
													ชื่อผู้ขาย/ผู้รับจ้าง/ผู้รับบริจาค :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getCompany().getCompanyname()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label"> ที่อยู่ :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getCompany().getAddress()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label"> โทร :: </label> <label
													class="col-sm-5 control-label" id="detail"><%=durable_controll.getCompany().getTell()%></label>
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">ประเภทเงิน ::
												</label> <label class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_money_type()%></label>
												<!-- 												<div class="col-sm-5"> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_money_type" value="เงินงบประมาณ" -->
												<!-- 															name="durable_money_type" -->
												<%-- 															<c:if test="${ durable.getDurable_money_type() == 'เงินงบประมาณ' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">เงินงบประมาณ</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_money_type" name="durable_money_type" -->
												<!-- 															value="รายได้" -->
												<%-- 															<c:if test="${ durable.getDurable_money_type() == 'รายได้' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">รายได้</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_money_type" name="durable_money_type" -->
												<!-- 															value="เงินบริจาค/เงินช่วยเหลือ" -->
												<%-- 															<c:if test="${ durable.getDurable_money_type() == 'เงินบริจาค/เงินช่วยเหลือ' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">เงินบริจาค/เงินช่วยเหลือ</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_money_type" name="durable_money_type" -->
												<!-- 															value="อื่นๆ" -->
												<%-- 															<c:if test="${ othercheck != null }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">อื่นๆ</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-group"> -->
												<!-- 														<input class="form-control" type="text" -->
												<!-- 															name="durable_money_type_text" -->
												<!-- 															id="durable_money_type_text" -->
												<%-- 															<c:if test="${ othercheck != null }">value="${ durable.getDurable_money_type() }" </c:if> --%>
												<!-- 															disabled> -->
												<!-- 													</div> -->
												<!-- 												</div> -->
											</div>
											<div class="form-group">
												<label class="col-sm-6 control-label">วิธีการได้มา
													:: </label> <label class="col-sm-5 control-label" id="detail"><%=durable_controll.getDurable_optain_type()%></label>
												<!-- 												<div class="col-sm-5"> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_optain_type" value="เฉพาะเจาะจง" -->
												<!-- 															name="durable_optain_type" -->
												<%-- 															<c:if test="${ durable.getDurable_optain_type() == 'เฉพาะเจาะจง' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">เฉพาะเจาะจง</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_optain_type" name="durable_optain_type" -->
												<!-- 															value="คัดเลือก" -->
												<%-- 															<c:if test="${ durable.getDurable_optain_type() == 'คัดเลือก' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">คัดเลือก</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_optain_type" name="durable_optain_type" -->
												<!-- 															value="e-bidding" -->
												<%-- 															<c:if test="${ durable.getDurable_optain_type() == 'e-bidding' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">e-bidding</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_optain_type" name="durable_optain_type" -->
												<!-- 															value="e-market" -->
												<%-- 															<c:if test="${ durable.getDurable_optain_type() == 'e-market' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">e-market</label> -->
												<!-- 													</div> -->
												<!-- 													<div class="form-check form-check-inline"> -->
												<!-- 														<input class="form-check-input" type="radio" -->
												<!-- 															id="durable_optain_type" name="durable_optain_type" -->
												<!-- 															value="รับบริจาค" -->
												<%-- 															<c:if test="${ durable.getDurable_optain_type() == 'รับบริจาค' }">checked</c:if> --%>
												<!-- 															disabled> <label class="form-check-label">รับบริจาค</label> -->
												<!-- 													</div> -->

												<!-- 												</div> -->
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						</div>

						<!-- ค่าเสื่อมประจําปี -->
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table>
										<tr>
											<th style="text-align: center;">วันเดือนปี</th>
											<th style="text-align: center;">ที่เอกสาร</th>
											<th style="text-align: center;">รายการ</th>
											<th style="text-align: center;">จํานวนหน่วย</th>
											<th style="text-align: center;">ราคาต่อหน่วย/ชุด/กลุ่ม</th>
											<th style="text-align: center;">มูลค่ารวม</th>
											<th style="text-align: center;">อายุการใช้งาน</th>
											<th style="text-align: center;">อัตราค่าเสื่อมราคา</th>
											<th style="text-align: center;">ค่าเสื่อมราคาประจําปี</th>
											<th style="text-align: center;">ค่าเสื่อมราคาสะสม</th>
											<th style="text-align: center;">มูลค่าสุทธิ</th>
											<th style="text-align: center;">หมายเหตุ</th>
										</tr>
										<tr>
											<td style="text-align: center;"><%=durable_controll.getDurable_entrancedate()%></td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;"><%=durable_controll.getDurable_name()%></td>
											<td style="text-align: center;"><%=durable_controll.getDurable_number()%></td>
											<td style="text-align: right;"><%=durable_controll.getDurable_price()%></td>
											<td style="text-align: right;"><%=durable_controll.getDurable_price()%></td>
											<td style="text-align: center;"><%=durable_controll.getDurable_life_time()%></td>
											<td style="text-align: center;"><%=durable_controll.getDepreciation_rate()%></td>
											<td style="text-align: right;">0.00</td>
											<td style="text-align: right;">.00</td>
											<td style="text-align: right;"><%=durable_controll.getDurable_price()%></td>
											<td style="text-align: center;"></td>
										</tr>
										<tr>
											<td style="text-align: center;"><%="30 "
		+ durable_controll.getDurable_entrancedate().substring(2, durable_controll.getDurable_entrancedate().length())%></td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;">คํานวณ 1 เดือน</td>
											<td style="text-align: center;"></td>
											<td style="text-align: right;"></td>
											<td style="text-align: right;"></td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;"></td>
											<td style="text-align: right;"><%=formatter.format(((d * durable_controll.getDepreciation_rate()) / 100) / 12)%></td>
											<td style="text-align: right;"><%=formatter.format(((d * durable_controll.getDepreciation_rate()) / 100) / 12)%></td>
											<td style="text-align: right;"><%=formatter.format(d - (((d * durable_controll.getDepreciation_rate()) / 100) / 12))%></td>
											<td style="text-align: center;"></td>
										</tr>
										<%
											Double depre_year = 0.0;
										Double depre_current = (d * durable_controll.getDepreciation_rate()) / 100;
										Double depre_one_month = (d * durable_controll.getDepreciation_rate()) / 100 / 12;
										Double durable_net = d;
										String date = durable_controll.getDurable_entrancedate();
										String date_show = "";
										date_show = date.substring(date.length() - 4);
										int year = Calendar.getInstance().get(Calendar.YEAR) + 543;
										int total_year = year - Integer.parseInt(date_show);
										%>
										<%
											for (int i = 0; i < total_year; i++) {
											String total_date = durable_controll.getDurable_entrancedate();
											String year_plus = "30 กันยายน " + (Integer.parseInt(total_date.substring(total_date.length() - 4)) + i);
											if (i == 0) {
												depre_year += depre_one_month;
												durable_net -= depre_one_month;
											}
											depre_year += depre_current;
											durable_net -= depre_current;
											if (i >= durable_controll.getDurable_life_time()) {
												break;
											}
										%>

										<tr>
											<td style="text-align: center;"><%=year_plus%></td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;">คํานวณ 1 ปี</td>
											<td style="text-align: center;"></td>
											<td style="text-align: right;"></td>
											<td style="text-align: right;"></td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;"></td>
											<td style="text-align: right;"><%=formatter.format(depre_current)%></td>
											<td style="text-align: right;"><%=formatter.format(depre_year)%></td>
											<td style="text-align: right;"><%=formatter.format(durable_net)%></td>
											<td style="text-align: center;"></td>
										</tr>

										<%
											}
										%>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer">
					<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="reset" class="btn btn-hot text-uppercase"
									onclick="history.back()">
									ย้อนกลับ
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>

		</div>
	</div>

	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

	<!-- <script type="text/javascript" -->
	<%-- 		src="/dist/js/custom/js_recorddurable.js"></script> --%>
	<c:if test="${value == 1}">
		<c:if test="${message != null }">
			<script type="text/javascript">
				var msg = '${message}';
				alert(msg);
			</script>
			<c:remove var="message" scope="session" />
			<c:remove var="value" scope="session" />
		</c:if>
	</c:if>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
	<c:remove var="item_search" scope="session" />
</body>
</html>