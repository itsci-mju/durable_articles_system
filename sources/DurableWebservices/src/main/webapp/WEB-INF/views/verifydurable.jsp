<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="common/importhead.jsp"></jsp:include>
<link rel="stylesheet" href="/dist/css/custom/table-listborrowing.css">
</head>
<body>
<fmt:setLocale value="th_TH" scope="session"/>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<!-- 							 	<fmt:formatDate value="${now}" pattern="dd MMMM yyyy" var="searchFormated" /> -->
<!-- 							 	<c:set var="strDate" value="${searchFormated}" />  -->

	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<!-- 	Content -->
	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-tag"></i> รายการตรวจสอบครุภัณฑ์
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<div class="panel-body">
					<!-- 				Search -->
					<form
						action="verifyDurable"
						class="form-inline" role="form" method="post">
						<fieldset>
							<legend align="right" style="font-size: 1em">
							 	<label>ปีการศึกษา : </label> <input class="form-control"
									type="text" id="year" name="year" size="4">  
									<label>หมายเลขห้อง : </label> 
										<select class="form-control" name="room_id" id="room_id">
										<option value="-">-</option>
													<c:forEach var="rooms" items="${roomBeanAll}">
														<option value="${rooms}">${rooms}</option>
													</c:forEach>
												</select>
										<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_search">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form>
					<!-- 					Table -->
				
				<c:choose>
					<c:when test="${status == '1'}">
						<c:choose>
							<c:when test="${verify_durableprofiles != null}">
								<form
									action="addVerifyDurable"
									method="post" class="form-inline">
									<div align="center" class="col-xs-6 col-md-4">
									
										ปีการศึกษา : <input class="form-control" type="text" id="year_save"
											name="year_save" size="4">
										<button type="submit" class="btn btn-sky text-uppercase"
											id="btn_save">
											<i class="fa fa-floppy-o"></i> บันทึก
										</button>
									
									</div><br>
									<div class="row" style="margin-top: 50px">
									<table>
										<thead>
											<tr>
												<th>รหัสครุภัณฑ์</th>
												<th>ชื่อครุภัณฑ์</th>
												<th>ห้องที่ใช้</th>
												<th>ชื่อผู้ใช้</th>
												<th>สภาพ</th>
												<th>ทมายเหตุ</th>
												
											</tr>
										</thead>
										<c:forEach var="list_profiles"
											items="${verify_durableprofiles}">
											<tbody>
												<tr>
													<td width="200">${list_profiles.durable_code}</td>
													<td width="200">${list_profiles.durable_name}</td>
													<td width="200">${list_profiles.room.room_number}</td>
													<td width="200">${list_profiles.responsible_person}</td>
													<td><select class="form-control" id="status"
														name="status">
															<option value="ดี"
																${list_profiles.durable_statusnow == 'ดี' ? 'selected' : ''}>ดี</option>
															<option value="ชำรุด"
																${list_profiles.durable_statusnow == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
															<option value="รอซ่อม"
																${list_profiles.durable_statusnow == 'รอซ่อม' ? 'selected' : ''}>รอซ่อม</option>
															<option value="แทงจำหน่าย"
																${list_profiles.durable_statusnow == 'แทงจำหน่าย' ? 'selected' : ''}>แทงจำหน่าย</option>
													</select></td>
													<td width="200"><textarea class="form-control" id="note"
															name="note">${list_profiles.note}</textarea></td>
															
												
												</tr>
												
											</tbody>
											<input type="hidden" id="staff_id" name="staff_id"
												value="${sessionScope.staffSession.id_staff}">
											<input type="hidden" id="durable_id" name="durable_id"
												value="${list_profiles.durable_code}">
										</c:forEach>
									</table>
									
									</div>
										<input type="hidden" id="save_date" name="save_date"
									value="${strDate}">
									</form>
								
							</c:when>
							<c:otherwise>
								<p align="center">ไม่พบข้อมูล</p>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
					</div>
				<c:remove var="verify_durableprofiles" scope="session" />
				<c:remove var="status" scope="session" />
			</div>
		</div>
	</div>
	<c:remove var="listdu_byyear" />
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<script type="text/javascript"
	src="/dist/js/custom/js_modal.js"></script>

<%-- <script type="text/javascript"
	src="/dist/js/custom/js_pageverify.js"></script> --%>
 
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

</html>