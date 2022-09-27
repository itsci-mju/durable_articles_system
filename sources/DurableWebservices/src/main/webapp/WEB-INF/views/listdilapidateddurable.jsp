<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<style type="text/css">
.btn-fresh {
	color: #000000;
	background-color: #51bf87;
	border-bottom: 2px solid #41996c;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-floppy-o"></i> รายการครุภัณฑ์ที่เสียหาย
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->

				<form action="listDilapidatedDurable" class="form-inline"
					role="form" method="get">
					<fieldset>
						<legend align="right" style="font-size: 1em">
							<c:if
								test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
								<label style="margin-bottom: 15px;">สาขา : </label>
								<select class="form-control" name="major_id" id="major_id"
									style="margin-bottom: 15px; margin-top: 15px;">
									<c:forEach var="major" items="${listMajor}">
										<option value="${major.ID_Major}"
											${major.ID_Major == selectedDept ? 'selected="selected"' : ''}>${major.major_Name}</option>
									</c:forEach>
								</select>

								<button type="submit" class="btn btn-default" id="yearsearch"
									style="margin-right: 30px; margin-bottom: 15px; margin-top: 15px;">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</c:if>
						</legend>
					</fieldset>
				</form>

				<c:choose>
					<c:when test="${list_dilapidatedDurable != null}">
						<table>
							<thead>
								<tr>
									<th style="text-align: center;">ลำดับ</th>
									<th style="text-align: center;">วันที่แจ้งซ่อม</th>
									<th style="text-align: center;">รหัสครุภัณฑ์</th>
									<th style="text-align: center;">ชื่อครุภัณฑ์</th>
									<th style="text-align: center;">ห้องที่ใช้</th>
									<th style="text-align: center;">ชื่อผู้ใช้</th>
									<th style="text-align: center;">สถานะครุภัณฑ์</th>
									<th style="text-align: center;">สถานะการซ่อมแซม</th>
									<th style="text-align: center;">รายละเอียดการซ่อมแซม</th>
									<th style="text-align: center;">รูปภาพ</th>
								</tr>
							</thead>
							<c:forEach var="list_dil" items="${list_dilapidatedDurable}"
								varStatus="loop">

								<thead>
									<tr>
										<td style="text-align: center;">${loop.index+1}</td>
										<td style="text-align: center;">${list_dil.date_of_repair}</td>
										<td style="text-align: center;">${list_dil.durable.durable_code}</td>
										<td style="text-align: center;">${list_dil.durable.durable_name}</td>
										<td style="text-align: center;">${list_dil.durable.room.room_number}</td>
										<td style="text-align: center;">${list_dil.durable.responsible_person}</td>
										<td style="text-align: center;">${list_dil.durable.durable_statusnow}</td>
										<td style="text-align: center;"><a href="#">${list_dil.repair_status}</a></td>
										<td style="text-align: center;">${list_dil.repair_detail}</td>
										<td style="text-align: center;">
											<button data-target="#imageModal" type="button"
												data-toggle="modal"
												style="background: url(/images/button_image.png); width: 30px; height: 30px; background-size: 30px 30px;"></button>
											<div class="modal" id="imageModal">
												<div class="modal-dialog">
													<div class="modal-content">
														<button class="close" data-dismiss="model">&times;</button>
													</div>
													<div class="medal-body">
														<img src="/images/repair/${list_dil.picture_repair}"
															width="100%" height="500">
													</div>
												</div>
											</div> <%-- 						<form
						action="/CreateRepairServlet"
						class="form-inline" role="form">
					
										<button type="submit" id="btn_add" name="btn_add"
											value="${list_dil.durable.durable_code}">ส่งซ่อมครุภัณฑ์</button>
							</form>	 --%>
										</td>
									</tr>
								</thead>

							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<p align="center">ไม่พบข้อมูล</p>
					</c:otherwise>
				</c:choose>

				<!-- 				End Table -->
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>

</body>

<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/dist/js/custom/ajax_editrepairprofile.js"></script>

<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
<c:remove var="item_search" scope="session" />
</body>
</html>