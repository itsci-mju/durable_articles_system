<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ac.th.itsci.durable.entity.*, java.util.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<%
	int year = Calendar.getInstance().get(Calendar.YEAR);
int month = Calendar.getInstance().get(Calendar.MONTH);

if (month + 1 >= 10) {
	year = year + 543;
	year += 1;
} else {
	year += 543;
}
%>
<!DOCTYPE html>
<html>
<head>
<title>ทะเบียนควบคุมทรัพย์สิน</title>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/durableControlCheck.js"></script>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<script>
	var loadFile = function(event) {
		var image = document.getElementById('output');
		image.src = URL.createObjectURL(event.target.files[0]);
	};
</script>
<body
	onload="companyChange(); durableTypeChange(); openGroupGenerated();">
	<fmt:setLocale value="th_TH" scope="session" />
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<!-- <img src="../../file/picture/pic.jpg" class="img-responsive"
		alt="Responsive image"> -->
	<!-- 		row -->
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<form action="do_addDurableControll" name="durableC"
				class="form-horizontal" method="post" enctype="multipart/form-data">
				<!-- <fmt:formatDate value="${now}" pattern="dd MMMM yyyy" var="searchFormated" /> -->
				<!-- <c:set var="strDate" value="${searchFormated}" />  -->
				<input type="hidden" id="save_date" name="save_date"
					value="${strDate}"> <input type="hidden"
					id="jsonDurableType" value="${durable_type_bean }"> <input
					type="hidden" id="jsonCompany" value="${ company_add_bean }">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-registered" aria-hidden="true"></i>
							ทะเบียนควบคุมทรัพย์สิน
						</h3>
					</div>
					<!-- **********Panel Body***********-->
					<div class="panel-body">

						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------Durable DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> ข้อมูลครุภัณฑ์
										</legend>
										<div class="form-group">
											<label class="col-sm-5 control-label">เพิ่มครุภัณฑ์แบบกลุ่ม
												:: </label>
											<div class="col-sm-5" style="color: red">
												<input type="checkbox" class="" id="group_add_check"
													name="group_add_check"
													<c:if test="${ not empty group_check }">checked readonly = "readonly"</c:if>
													onclick="openGroupGenerated()">
												****ถ้าต้องการเพิ่มแบบกลุ่มกรุณาเลือกช่องนี้ด้วย
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durablecode"
													name="durablecode"
													value="${ durable_data_form.getDurable_code() }">
											</div>
										</div>
										<c:forEach items="${ durable_group_code }" var="d">
											<div class="form-group">
												<label class="col-sm-5 control-label">รหัสครุภัณฑ์
													:: </label>
												<div class="col-sm-5">
													<input type="text" class="form-control" id="durablecode"
														name="durablecode" value="${ d.getDurable_code() }">
												</div>
											</div>
										</c:forEach>
										<div class="form-group">
											<label class="col-sm-5 control-label">จำนวนครุภัณฑ์
												:: </label>
											<div class="col-sm-2">
												<input type="text" class="form-control" id="numdurable"
													name="numdurable"
													value="${ durable_data_form.getDurable_number() }">
											</div>
											<div class="col-sm-2">
												<button class="btn btn-xs btn-fresh"
													id="generate_durable_code"
													 onclick="return checkDurableNum()" formaction="do_generatedurablecode" disabled>สร้างรหัสครุภัณฑ์</button>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="namedurable"
													name="namedurable"
													value="${ durable_data_form.getDurable_name() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ยี่ห้อ ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="brand"
													name="brand"
													value="${ durable_data_form.getDurable_brandname() }">
											</div>
										</div>
										<div class="form-group">

											<label class="col-sm-5 control-label">ราคาต่อหน่วย ::
											</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="pricedurable"
													name="pricedurable"
													value="${ durable_data_form.getDurable_price() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ได้รับ ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="datepicker" value="${ entranceDate }">
											</div>
										</div>



										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="detail"
													name="detail"><c:if
														test="${ durable_data_form.getDurable_model() != '' }">${ durable_data_form.getDurable_model() }</c:if><c:if
														test="${ durable_data_form.getDurable_model() == '' || empty durable_data_form.getDurable_model() }">-</c:if></textarea>
											</div>
										</div>


									</fieldset>
								</div>


								<!------------------------------------------End Durable DATA--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i> สถานะ
										</legend>


										<div class="form-group">
											<label class="col-sm-5 control-label">ห้องที่ใช้ครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" name="room_id" id="room_id">
													<option value="-">-</option>
													<c:forEach var="room" items="${ roomBeanAll }">
														<option
															<c:if test="${ durable_data_form.getRoom().getRoom_number() == room.getRoom_number() }">selected</c:if>
															value="${ room.getRoom_number() }">${ room.getRoom_number() }</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ผู้ใช้ครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" id="owner_id" name="owner_id">
													<option value="-">-</option>
													<c:forEach items="${ ownerBeanAll }" var="staff">
														<option
															<c:if test="${ durable_data_form.getResponsible_person() == staff}">selected</c:if>
															value="${ staff }">${ staff }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">สถาพครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<select class="form-control" id="status" name="status">
													<option value="-">-</option>
													<option
														<c:if test="${ durable_data_form.getDurable_statusnow() == 'ดี'}">selected</c:if>
														value="ดี">ดี</option>
													<option
														<c:if test="${ durable_data_form.getDurable_statusnow() == 'ชำรุด'}">selected</c:if>
														value="ชำรุด">ชำรุด</option>
													<option
														<c:if test="${ durable_data_form.getDurable_statusnow() == 'รอซ่อม'}">selected</c:if>
														value="รอซ่อม">รอซ่อม</option>
													<option
														<c:if test="${ durable_data_form.getDurable_statusnow() == 'แทงจำหน่าย'}">selected</c:if>
														value="แทงจำหน่าย">แทงจำหน่าย</option>
												</select>
											</div>
										</div>
									<%-- 	<div class="form-group">
											<label class="col-sm-5 control-label">ปีงบประมาณ :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="budget_year"
													name="budget_year" value="<%=year%>" readonly="readonly">
											</div>
										</div> --%>
										<div class="form-group">
											<label class="col-sm-5 control-label">หมายเหตุ :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" id="note" name="note"><c:if
														test="${ durable_data_form.getNote() != '' }">${ durable_data_form.getNote() }</c:if><c:if
														test="${ durable_data_form.getNote() == '' || empty durable_data_form.getNote() }">-</c:if></textarea>

											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รูปภาพครุภัณฑ์
												::</label>
											<div class="col-sm-5">
												<input type="file" class="form-control" name="durable_img"
													id="durable_img" onchange="loadFile(event)">
											</div>
										</div>
										<div class="col-50">
											<img width="70%" style="margin-left: 15%;" id="output"
												name="output">
										</div>
									</fieldset>
								</div>
							</div>
						</div>
						<!-- start -->
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<fieldset>
									<legend style="font-size: 1em">
										<i class="fa fa-file-o"></i> ทะเบียนควบคุมทรัพย์สิน
									</legend>
									<div class="col-sm-6 col-md-6 col-lg-6">

										<div class="form-group">
											<label class="col-sm-5 control-label">ประเภท :: </label>
											<div class="col-sm-5">
												<select id="durable_types" name="durable_types"
													class="form-control" onchange="durableTypeChange()">
													<option value="-">-</option>
													<c:forEach items="${ durable_type }" var="durable_type">
														<option
															<c:if test="${ durable_data_form.getDurable_type() == durable_type.getDurable_type_name() }">selected</c:if>
															value="${ durable_type.getDurable_type_name() }">${ durable_type.getDurable_type_name() }</option>
													</c:forEach>
												</select>

											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">อายุการใช้ :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control"
													id="durable_life_time" name="durable_life_time">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่ฎีกา :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control"
													id="durable_petition_number" name="durable_petition_number"
													value="${ durable_data_form.getDurable_petition_number() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">Serial Number
												:: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3"
													id="durable_serial_number" name="durable_serial_number"><c:if
														test="${ durable_data_form.getDurable_serial_number() != '' }">${ durable_data_form.getDurable_serial_number() }</c:if><c:if
														test="${ durable_data_form.getDurable_serial_number() == '' || empty durable_data_form.getDurable_serial_number() }">-</c:if></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">อัตราเสื่อมราคา
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control"
													id="depreciation_rate" name="depreciation_rate">
											</div>
										</div>
									</div>
									<div class="col-sm-6 col-md-6 col-lg-6">
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อผู้ขาย/ผู้รับจ้าง/ผู้บริจาค
												::</label>
											<div class="col-sm-5">
												<!-- 												<select name="company_name" id="company_name" class="form-control"> -->
												<!-- 													<option> - </option> -->
												<%-- 													<c:forEach var="company" items="${ company_list }"> --%>
												<%-- 														<option value="">${ company }</option> --%>
												<%-- 													</c:forEach> --%>
												<!-- 												</select> -->
												<input class="form-control" type="text" list="company_names"
													name="company_name" id="company_name"
													placeholder="กรุณาเลือก" onchange="companyChange()"
													value="${ durable_data_form.getCompany().getCompanyname() }">
												<datalist id="company_names">
													<c:forEach items="${ company_list }" var="company_name">
														<option value="${ company_name }">${ company_name }</option>
													</c:forEach>
												</datalist>

											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">เบอร์โทร :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="tell"
													name="tell" value="-" max="10">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ที่อยู่ :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="address"
													name="address">-</textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ประเภทเงิน :: </label>
											<div class="col-sm-5">
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_money_type" value="เงินงบประมาณ"
														name="durable_money_type"
														onClick="openMoneyType(durableC)" checked> <label
														class="form-check-label">เงินงบประมาณ</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_money_type" name="durable_money_type"
														value="รายได้" onClick="openMoneyType(durableC)">
													<label class="form-check-label">รายได้</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_money_type" name="durable_money_type"
														value="เงินบริจาค/เงินช่วยเหลือ"
														onClick="openMoneyType(durableC)"> <label
														class="form-check-label">เงินบริจาค/เงินช่วยเหลือ</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_money_type" name="durable_money_type"
														value="อื่นๆ" onClick="openMoneyType(durableC)"> <label
														class="form-check-label">อื่นๆ</label>
												</div>
												<div class="form-group">
													<input class="form-control" type="text"
														name="durable_money_type_text"
														id="durable_money_type_text" disabled>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วิธีการได้มา ::
											</label>
											<div class="col-sm-5">
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_optain_type" value="เฉพาะเจาะจง"
														name="durable_optain_type" checked> <label
														class="form-check-label">เฉพาะเจาะจง</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_optain_type" name="durable_optain_type"
														value="คัดเลือก"> <label class="form-check-label">คัดเลือก</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_optain_type" name="durable_optain_type"
														value="e-bidding"> <label class="form-check-label">e-bidding</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_optain_type" name="durable_optain_type"
														value="e-market"> <label class="form-check-label">e-market</label>
												</div>
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="radio"
														id="durable_optain_type" name="durable_optain_type"
														value="รับบริจาค"> <label class="form-check-label">รับบริจาค</label>
												</div>

											</div>
										</div>

									</div>
								</fieldset>
							</div>
						</div>
						<!--  end -->
					</div>
					<!-- **********End Panel Body***********-->
					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_addrepair"
									onClick="return durableControlFrmChk(durableC)">
									<i class="fa fa-floppy-o"></i> บันทึก
								</button>
								<button type="reset" class="btn btn-hot text-uppercase"
									onclick="history.back()">
									<i class="fa fa-times"></i> ยกเลิก
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		function openMoneyType(form) {

			if (form.durable_money_type.value == 'เงินงบประมาณ') {
				form.durable_money_type_text.disabled = true;
			} else if (form.durable_money_type.value == 'รายได้') {
				form.durable_money_type_text.disabled = true;
			} else if (form.durable_money_type.value == 'เงินบริจาค/เงินช่วยเหลือ') {
				form.durable_money_type_text.disabled = true;
			} else if (form.durable_money_type.value == 'อื่นๆ') {
				form.durable_money_type_text.disabled = false;
			}
		}
	</script>




	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

	<%-- 	<script type="text/javascript"
		src="/dist/js/custom/js_recorddurable.js"></script>
 --%>
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