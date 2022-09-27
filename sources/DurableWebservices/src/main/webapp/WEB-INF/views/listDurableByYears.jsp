<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="java.util.*, ac.th.itsci.durable.entity.*,ac.th.itsci.durable.repo.*,java.text.*"%>
<!DOCTYPE html>
<%
Date date_now = new Date();
String DATE_FORMAT = "dd MMMM yyyy";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
%>
<html>
<head>
<title>รายงานการตรวจสอบครุภัณฑ์ประจำปี</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta name="viewport" content="width=device-width">
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!--  script scan -->
<script type="text/javascript" charset="UTF-8"
	src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>
<script type="text/javascript">
	function myFunction() {
		var major_id = document.getElementById("major_id").value;
		var jsonRoom = document.getElementById("jsonRoom").value;
		var replacJsonRoom = jsonRoom.replace(/'/g, '"');
		var values = null;
		var x = document.getElementById("room_id");
		var length = x.options.length;

		obj = JSON.parse(replacJsonRoom);
		values = obj.major[major_id];

		if (length > 0) {
			for (i = length - 1; i >= 0; i--) {
				x.options[i] = null;
			}
		}

		if (major_id === "99") {
			var option = document.createElement("option");
			option.value = "-";
			option.text = "-";
			x.appendChild(option);
		} else {
			if (values.length > 0) {
				var option = document.createElement("option");
				option.value = "-";
				option.text = "-";
				x.appendChild(option);
				for (var i = 0; i < values.length; i++) {
					var option = document.createElement("option");
					option.value = values[i];
					option.text = values[i];
					x.appendChild(option);
				}
			} else {
				var option = document.createElement("option");
				option.value = "-";
				option.text = "-";
				x.appendChild(option);
			}
		}
	}
</script>
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
	height: 600px;
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

</head>
<body onload="check_repair()">
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<input type="hidden" value="${roomBeanAll}" id="jsonRoom">

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-list-alt"></i> รายงานการตรวจสอบครุภัณฑ์ประจำปี
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->
				<div class="panel-body">
					<!-- 				Table -->
					<form action="do_getListVerifyDurable" class="form-inline"
						role="form" method="post">
						<div align="left"></div>
						<fieldset>
							<legend align="right" style="font-size: 1em">

								<c:choose>
									<c:when
										test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
										<label> &nbsp;&nbsp;&nbsp; สาขาวิชา : </label>
										<select class="form-control" name="major_id" id="major_id"
											style="margin-bottom: 15px; margin-top: 15px; width: 200px;"
											onchange="myFunction()">
											<c:forEach var="major" items="${listMajor}">
												<option value="${major.getID_Major()}"
													${major.getID_Major() == selectedDept ? 'selected="selected"' : ''}
													<c:if test="${ mid == major.getID_Major() }">selected</c:if>>${major.getMajor_Name()}</option>
											</c:forEach>
										</select>
										<label> &nbsp;&nbsp;&nbsp; หมายเลขห้อง : </label>
										<select class="form-control" name="room_id" id="room_id"
											style="width: 100px;">
											<option value="-">-</option>
											<c:forEach var="room" items="${roomNumber}">
												<option value="${room.getRoom_number()}"
													<c:if test="${ room_select == room.getRoom_number() }">selected</c:if>>${room.getRoom_number()}</option>
											</c:forEach>
										</select>
										<label> &nbsp;&nbsp;&nbsp; สถานะ : </label>
										<select class="form-control" id="statusSelect"
											name="statusSelect" style="width: 125px;">
											<option value="-" ${statusSelectOld == '-' ? 'selected' : ''}>-</option>
											<option value="ดี"
												${statusSelectOld == 'ดี' ? 'selected' : ''}>ดี</option>
											<option value="ชำรุด"
												${statusSelectOld == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
											<option value="รอซ่อม"
												${statusSelectOld == 'รอซ่อม' ? 'selected' : ''}>รอซ่อม</option>
											<option value="แทงจำหน่าย"
												${statusSelectOld == 'แทงจำหน่าย' ? 'selected' : ''}>แทงจำหน่าย</option>
										</select>
									</c:when>
									<c:otherwise>
										<label> &nbsp;&nbsp;&nbsp; หมายเลขห้อง : </label>
										<select class="form-control" name="room_id" id="room_id"
											style="width: 200px;">
											<option value="-">-</option>
											<c:forEach var="room" items="${roomNumberMajor}">
												<option value="${room.getRoom_number()}"
													<c:if test="${ room_select == room.getRoom_number() }">selected</c:if>>${room.getRoom_number()}</option>
											</c:forEach>
										</select>
										<label> &nbsp;&nbsp;&nbsp; สถานะ : </label>
										<select class="form-control" id="statusSelect"
											name="statusSelect" style="width: 125px;">
											<option value="-" ${statusSelectOld == '-' ? 'selected' : ''}>-</option>
											<option value="ดี"
												${statusSelectOld == 'ดี' ? 'selected' : ''}>ดี</option>
											<option value="ชำรุด"
												${statusSelectOld == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
											<option value="รอซ่อม"
												${statusSelectOld == 'รอซ่อม' ? 'selected' : ''}>รอซ่อม</option>
											<option value="แทงจำหน่าย"
												${statusSelectOld == 'แทงจำหน่าย' ? 'selected' : ''}>แทงจำหน่าย</option>
										</select>
									</c:otherwise>
								</c:choose>
								<label>&nbsp;&nbsp;&nbsp; ปี : </label> <input
									class="form-control" type="text" id="year" name="year"
									value="${ year_search }">
								<button type="submit" class="btn btn-sky text-uppercase"
									onClick="check_search_durable_control(searchDurableControll)">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
						<%-- 		<fieldset>
							<legend align="right" style="font-size: 1em">

								<c:if
									test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
									<label>สาขา : </label>
									<select class="form-control" name="major_id" id="major_id"
										style="width: 200px;" onchange="myFunction()">
										<c:forEach var="major" items="${listMajor}">
											<option value="${major.ID_Major}"
												${major.ID_Major == selectedDept ? 'selected="selected"' : ''}>${major.major_Name}</option>
										</c:forEach>
									</select>
								</c:if>

								<label> &nbsp;&nbsp;&nbsp; ห้อง : </label> <select
									class="form-control" style="width: 200px;" name="room_ID"
									id="room_ID">
									<c:forEach var="room" items="${roomAll}">
										<option value="${room}">${room}</option>
									</c:forEach>

								</select> <label> &nbsp;&nbsp;&nbsp; สถานะ : </label> <select
									class="form-control" id="statusSelect" name="statusSelect"
									style="width: 125px;">
									<option value="-" ${statusSelectOld == '-' ? 'selected' : ''}>-</option>
									<option value="ดี" ${statusSelectOld == 'ดี' ? 'selected' : ''}>ดี</option>
									<option value="ชำรุด"
										${statusSelectOld == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
									<option value="รอซ่อม"
										${statusSelectOld == 'รอซ่อม' ? 'selected' : ''}>รอซ่อม</option>
									<option value="แทงจำหน่าย"
										${statusSelectOld == 'แทงจำหน่าย' ? 'selected' : ''}>แทงจำหน่าย</option>
								</select> <label> &nbsp;&nbsp;&nbsp; ปีการศึกษา : </label> <input
									type="text" class="form-control" id="year" name="year"
									style="width: 100px;" value="${year}" maxlength="4">
								<button type="submit" class="btn btn-sky" id="yearsearch">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset> --%>
					</form>
					<form method="get">
						<div align="right">
							<c:if test="${ not empty verify_durable }">
								<c:choose>
									<c:when test="${ empty no_verify_year }">
										<c:if test="${ empty before }">
											<label>ช่วงเวลาตรวจสอบครุภัณฑ์ วันที่ ${ sessionScope.start_date }
												ถึง ${ sessionScope.date_end }</label>
											<button type="submit" class="btn btn-sky text-uppercase"
												id="btn_printqr" style="margin-bottom: 15px;"
												formaction="do_exportFileDurable">
												<i class="fa fa-table"></i> ดาวน์โหลดเอกสาร .xlxs
											</button>
											<c:if test="${empty check_over_due }">
												<button type="submit" class="btn btn-sky text-uppercase"
													id="btn_scan" style="margin-bottom: 15px;"
													formaction="openCameraScan">
													<i class="fa fa-qrcode" aria-hidden="true"></i> แสกน qr
													code
												</button>
											</c:if>
										</c:if>
									</c:when>
									<c:otherwise>
										<label>ไม่พบข้อมูลปีตรวจครุภัณฑ์</label>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</form>
					<c:if test="${not empty not_verify_durable}">
						<c:if
							test="${ sessionScope.staffSession.major.ID_Major == not_verify_durable.get(0).getMajor().getID_Major() }">
							<div class="table-responsive">
								<form action="do_addVerifyDurable" method="post"
									name="verify_form">
									<div align="right">
										<button type="submit" class="btn btn-sky text-uppercase"
											id="btn_printqr" style="margin-bottom: 15px;">
											<i class="fa fa-save"></i> บันทึกข้อมูล
										</button>
									</div>
									<table>
										<thead>
											<tr>
												<th style="text-align: center;">เลือก<input
													type="checkbox" id="chk_all" name="chk_all"></th>
												<th style="text-align: center;">รหัสครุภัณฑ์</th>
												<th style="text-align: center;">วันที่ตรวจสอบ</th>
												<th style="text-align: center;" width="200">ชื่อครุภัณฑ์</th>
												<th style="text-align: center;" width="150">ห้องที่ใช้</th>
												<th style="text-align: center;">ชื่อผู้ใช้</th>
												<th style="text-align: center;">สภาพ</th>
												<th style="text-align: center;">หมายเหตุ</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${ not_verify_durable }"
												var="not_verify_durable" varStatus="i">
												<tr>
													<td style="text-align: center;"><input type="checkbox"
														name="chk_verify"
														value="${ not_verify_durable.getDurable_code() }"
														id="chk_verify"></td>
													<td style="text-align: center;">${ not_verify_durable.getDurable_code() }</td>
													<td style="text-align: center;"><%=sdf.format(date_now)%><input
														type="hidden"
														name="date:${ not_verify_durable.getDurable_code() }"
														value="<%= sdf.format(date_now) %>"></td>
													<td style="text-align: center;">${ not_verify_durable.getDurable_name() }</td>
													<td style="text-align: center;">${ not_verify_durable.getRoom().getRoom_number() }<input type="hidden" value="${ not_verify_durable.getRoom().getRoom_number() }" name="room:${ not_verify_durable.getDurable_code() }"></td>
													<td style="text-align: center;">${ not_verify_durable.getResponsible_person() }<input type="hidden" value="${ not_verify_durable.getResponsible_person() }" name="responsible_person:${ not_verify_durable.getDurable_code() }"></td>
													<td><select class="form-control"
														id="status:${not_verify_durable.getDurable_code()}"
														name="status:${not_verify_durable.getDurable_code()}">
															<option value="ดี" selected>ดี</option>
															<option value="ชำรุด">ชำรุด</option>
															<option value="รอซ่อม">รอซ่อม</option>
															<option value="แทงจำหน่าย">แทงจำหน่าย</option>
													</select></td>
													<td><textarea class="form-control"
															id="verify_note:${not_verify_durable.getDurable_code()}"
															name="verify_note:${not_verify_durable.getDurable_code()}">-</textarea></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</div>
						</c:if>
						<c:if
							test="${ sessionScope.staffSession.major.ID_Major != not_verify_durable.get(0).getMajor().getID_Major() }">
							<div class="table-responsive">
								<form action="do_addVerifyDurable" method="post"
									name="verify_form">
									<table>
										<thead>
											<tr>
												<th style="text-align: center;">รหัสครุภัณฑ์</th>
												<th style="text-align: center;">วันที่ตรวจสอบ</th>
												<th style="text-align: center;" width="200">ชื่อครุภัณฑ์</th>
												<th style="text-align: center;" width="150">ห้องที่ใช้</th>
												<th style="text-align: center;">ชื่อผู้ใช้</th>
												<th style="text-align: center;">สภาพ</th>
												<th style="text-align: center;">หมายเหตุ</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${ not_verify_durable }"
												var="not_verify_durable" varStatus="i">
												<tr>
													<td style="text-align: center;">${ not_verify_durable.getDurable_code() }</td>
													<td style="text-align: center;"><input type="hidden"
														name="date:${ not_verify_durable.getDurable_code() }"
														value="<%= sdf.format(date_now) %>"></td>
													<td style="text-align: center;">${ not_verify_durable.getDurable_name() }</td>
													<td style="text-align: center;">${ not_verify_durable.getRoom().getRoom_number() }</td>
													<td style="text-align: center;">${ not_verify_durable.getResponsible_person() }</td>
													<td></td>
													<td>ยังไม่ได้รับการตรวจสอบในปี ${ year_search }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</div>
						</c:if>
					</c:if>
					<c:choose>
						<c:when test="${ not empty verify_durable }">
							<form>
								<div align="right"></div>
							</form>
							<form action="export_file_durable" style="margin-top: 20px"
								method="post">
								<fieldset>
									<legend align="left" style="font-size: 1em">
										<i class="fa fa-check-square" style="color: green"
											aria-hidden="true"></i>
										ข้อมูลครุภัณฑ์ที่ได้รับการตรวจสอบแล้วในปี ${ year_search }
									</legend>
								</fieldset>
							</form>

							<div class="table-responsive">
								<table>
									<thead>
										<tr>
											<th style="text-align: center;">รหัสครุภัณฑ์</th>
											<th style="text-align: center;">วันที่ตรวจสอบ</th>
											<th style="text-align: center;" width="200">ชื่อครุภัณฑ์</th>
											<th style="text-align: center;" width="150">ห้องที่ใช้</th>
											<th style="text-align: center;">ชื่อผู้ใช้</th>
											<th style="text-align: center;">ผู้ตรวจสอบ</th>
											<th style="text-align: center;">สภาพ</th>
											<th style="text-align: center;">หมายเหตุ</th>
											<th style="text-align: center">เพิ่มเติม</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${ verify_durable }" var="verify_durable">
											<tr>
												<td>${ verify_durable.getPk().getDurable().getDurable_code() }</td>
												<td>${ verify_durable.getSave_date() }</td>
												<td>${ verify_durable.getPk().getDurable().getDurable_name() }</td>
												<td>${ verify_durable.getPk().getDurable().getRoom().getRoom_number() }</td>
												<td>${ verify_durable.getPk().getDurable().getResponsible_person() }</td>
												<td>${ verify_durable.getPk().getStaff().getStaff_name() }</td>
												<td>${ verify_durable.getDurable_status() }</td>
												<td>${ verify_durable.getNote() }</td>
												<c:choose>
													<c:when
														test="${ sessionScope.staffSession.major.ID_Major == verify_durable.getPk().getDurable().getMajor().getID_Major() }">
														<c:if test="${ empty check_over_due }">
															<td style="text-align: center"><button
																	class="btn btn-sunny text-capitalize btn-xs"
																	id="btn-modal"
																	value="${ verify_durable.getPk().getDurable().getDurable_code()},${verify_durable.getPk().getVerify().getYears()},${verify_durable.getSave_date()},${verify_durable.getPk().getStaff().getStaff_name()},${sessionScope.staffSession.id_staff},${verify_durable.getDurable_status()},${verify_durable.getNote()},${ year_search },${statusSelectOld},${room_select},${mid},${ verify_durable.getPk().getDurable().getRoom().getRoom_number() },${ verify_durable.getPk().getDurable().getResponsible_person() }">แก้ไขข้อมูล</button>
														</c:if>
														<c:if test="${not empty check_over_due }">
															<td></td>
														</c:if>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

						</c:when>
						<c:otherwise>
							<div align="center">
								<h4>ไม่พบครุภัณฑ์ที่ได้รับการตรวจสอบในปี ${ year_search }</h4>
							</div>
						</c:otherwise>
					</c:choose>
					<%-- 					<c:choose> --%>
					<%-- 						<c:when test="${status == '1'}"> --%>
					<%-- 							<c:choose> --%>
					<%-- 								<c:when test="${durableprofiles != null}"> --%>
					<!-- 									<div class="table-responsive"> -->
					<!-- 										<form action="ExportFileExcelServlet" class="form-inline" -->
					<!-- 											role="form" method="POST"> -->
					<!-- 											<div align="right" style="margin-bottom: 5px"> -->
					<!-- 												<button type="submit" -->
					<!-- 													class="btn btn-sky text-uppercase btn-sm"> -->
					<!-- 													<i class="fa fa-file-excel-o"></i> ดาวน์โหลดเอกสาร .xlsx -->
					<!-- 												</button> -->
					<!-- 											</div> -->
					<!-- 										</form> -->
					<!-- 										<table> -->
					<!-- 											<thead> -->
					<!-- 												<tr> -->
					<!-- 													<th style="text-align: center;">รหัสครุภัณฑ์</th> -->
					<!-- 													<th style="text-align: center;">ปีที่ตรวจสอบ</th> -->
					<!-- 													<th style="text-align: center;">วันที่ตรวจสอบ</th> -->
					<!-- 													<th style="text-align: center;">ชื่อครุภัณฑ์</th> -->
					<!-- 													<th style="text-align: center;">ห้องที่ใช้</th> -->
					<!-- 													<th style="text-align: center;">ชื่อผู้ใช้</th> -->
					<!-- 													<th style="text-align: center;">ผู้ตรวจสอบ</th> -->
					<!-- 													<th style="text-align: center;">สภาพ</th> -->
					<!-- 													<th style="text-align: center;">หมายเหตุ</th> -->
					<!-- 													<th style="text-align: center;">รายละเอียดเพิ่มเติม</th> -->
					<!-- 												</tr> -->
					<!-- 											</thead> -->
					<%-- 											<c:forEach var="listdu" items="${durableprofiles}"> --%>
					<!-- 												<tbody> -->
					<!-- 													<tr> -->
					<%-- 														<td style="text-align: center;">${listdu.pk.durable.durable_code}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.pk.verify.years}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.save_date}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.pk.durable.durable_name}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.pk.durable.room.room_number}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.pk.durable.responsible_person}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.pk.staff.staff_name}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.durable_status}</td> --%>
					<%-- 														<td style="text-align: center;">${listdu.note}</td> --%>

					<%-- 														<c:choose> --%>

					<%-- 															<c:when test="${disbleButton == 3}"> --%>
					<!-- 																<td style="text-align: center;"> -->
					<!-- 																	<button type="button" -->
					<!-- 																		class="btn btn-fresh text-capitalize btn-xs" -->
					<!-- 																		id="btn_modal" style="color: #000000;" -->
					<%-- 																		value="${listdu.pk.durable.durable_code},${listdu.pk.verify.years},${listdu.save_date},${listdu.pk.staff.staff_name},${listdu.pk.staff.id_staff},${listdu.durable_status},${listdu.note}">แก้ไขข้อมูลการตรวจสอบ</button> --%>
					<!-- 																</td> -->
					<%-- 															</c:when> --%>
					<%-- 															<c:when test="${disbleButton == 1}"> --%>
					<!-- 																<td style="text-align: center;"> -->
					<!-- 																	<button class="btn btn-hot text-capitalize btn-xs">ยังไม่ถึงวันที่ตรวจสอบ</button> -->
					<!-- 																</td> -->
					<%-- 															</c:when> --%>
					<%-- 															<c:when test="${disbleButton == 2}"> --%>
					<!-- 																<td style="text-align: center;"> -->
					<!-- 																	<button class="btn btn-hot text-capitalize btn-xs" -->
					<%-- 																		value="${textNotification},${textNotificationsHeader}" --%>
					<!-- 																		id="btn_Notification">เลยกำหนดว้นที่ตรวจสอบแล้ว</button> -->
					<!-- 																</td> -->
					<%-- 															</c:when> --%>
					<%-- 															<c:when test="${disbleButton == 0}"> --%>
					<!-- 																<td style="text-align: center;"><button -->
					<!-- 																		class="btn btn-hot text-capitalize btn-xs" -->
					<!-- 																		disabled="disabled">เลยกำหนดว้นที่ตรวจสอบแล้ว</button></td> -->
					<%-- 															</c:when> --%>
					<%-- 														</c:choose> --%>
					<!-- 														---------------------- -->
					<!-- 													</tr> -->
					<!-- 												</tbody> -->
					<%-- 											</c:forEach> --%>
					<!-- 										</table> -->
					<!-- 									</div> -->
					<%-- 								</c:when> --%>
					<%-- 								<c:when test="${ no_year != null }"> --%>
					<!-- 									<p align="center">ไม่พบข้อมูลปี</p> -->
					<%-- 								</c:when> --%>
					<%-- 								<c:otherwise> --%>
					<!-- 									<p align="center">ไม่พบข้อมูล</p> -->
					<%-- 								</c:otherwise> --%>
					<%-- 							</c:choose> --%>
					<%-- 						</c:when> --%>
					<%-- 					</c:choose> --%>
					<!-- 				End Table -->
					<c:remove var="durableprofiles" scope="session" />
					<c:remove var="status" scope="session" />
				</div>
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>

	<!-------- show Notifications -------->
	<div class="modal fade" id="showNotifications" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3>แจ้งเตือน</h3>
				</div>
				<div class="modal-body" style="height: auto;">
					<label for="exampleFormControlInput1" id="textNotificationsHeader"
						style="text-align: center; font-size: 20px"></label> <label
						for="exampleFormControlInput1" id="textNotifications"
						style="text-align: center; font-size: 20px"></label>
				</div>
			</div>
		</div>
	</div>
	<!-------- Edit verify -------->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="background-color: #283e56;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 style="background-color: #283e56;">แก้ไขข้อมูลการตรวจสอบ</h4>
				</div>
				<div class="modal-body">
					<form action="do_updateVerify" method="POST" name="verify_form">
						<input type="hidden" name="search_year" id="search_year">
						<input type="hidden" name="search_status" id="search_status">
						<input type="hidden" name="search_room" id="search_room">
						<input type="hidden" name="major_search" id="major_search">
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">รหัสครุภัณฑ์</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="DurableCode"
										name="DurableCode" style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">ปีที่ตรวจสอบ</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="yearVerify"
										name="yearVerify" style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">วันที่ตรวจสอบ</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="verifyDate"
										name="verifyDate" style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">ผู้ตรวจสอบ</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="staff" name="staff"
										style="width: 200px" readonly> <input type="hidden"
										class="form-control" id="id_staff" name="id_staff"
										style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">ห้อง</label>
								</div>
								<div class="column02">
									<select class="col-sm-4 form-control" id="roomDurable"
										name="roomDurable" style="width: 200px">
										<c:forEach var="room" items="${room_bean }">
											<option value="${ room.getRoom_number() }" ${listdu.room.Room_number == room.getRoom_number() ? 'selected' : ''}>${ room.getRoom_number() }</option>
										</c:forEach>
									</select>

								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">ผู้รับผิดชอบดูแล</label>
								</div>
								<div class="column02">
									<select class="col-sm-4 form-control" id="responsible_person"
										name="responsible_person" style="width: 200px">
										<c:forEach var="responsible_person" items="${responsible_person }">
											<option value="${ responsible_person }" ${listdu.Responsible_person == responsible_person ? 'selected' : ''}>${ responsible_person }</option>
										</c:forEach>
									</select>

								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">สถานะครุภัณฑ์</label>
								</div>
								<div class="column02">
									<select class="col-sm-4 form-control" id="statusDurable"
										name="statusDurable" style="width: 200px" onchange="check_repair()">
										<option value="ดี"
											${listdu.durable_status == 'ดี' ? 'selected' : ''}>ดี</option>
										<option value="ชำรุด"
											${listdu.durable_status == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
										<option value="หมดความจําเป็น"
											${listdu.durable_status == 'หมดความจําเป็น' ? 'selected' : ''}>หมดความจําเป็น</option>
									</select>

								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">หมายเหตุ</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="note" name="note"
										style="width: 200px;" disabled>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="column03">
								<input type="hidden" id="room_ID" name="room_ID" readonly>
								<input type="hidden" id="major_id" name="major_id" readonly>
								<input type="hidden" id="statusSelect" name="statusSelect"
									readonly> <input type="hidden" id="yearsearch"
									name="yearsearch" readonly>
								<button type="submit" class="btn btn-success btn-block" onClick="return verify_form_check(verify_form)">แก้ไข</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/modal-durabledeail.jsp"></jsp:include>
	<%-- 	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/custom/js_pagelistyear.js"></script> --%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/custom/verify_check_all.js"></script>
</body>
<c:if
	test="${ sessionScope.messages != null && sessionScope.messages != ''  }">
	<script type="text/javascript">
		var msg = '${messages}';
		alert(msg);
	</script>
	<c:remove var="messages" scope="session" />
	<c:remove var="value" scope="session" />
</c:if>
<c:if test="${ message != '' &&  message != null}">
	<script type="text/javascript">
		var msg = '${message}';
		alert(msg);
	</script>
	<c:remove var="message" scope="session" />
	<c:remove var="value" scope="session" />
</c:if>

<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
<c:remove var="item_search" scope="session" />
</html>