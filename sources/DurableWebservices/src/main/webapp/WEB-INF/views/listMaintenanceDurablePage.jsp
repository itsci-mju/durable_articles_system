<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>รายการครุภัณฑ์ที่ได้รับการซ่อมบํารุง</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-floppy-o"></i>
							รายการครุภัณฑ์ที่ได้รับการซ่อมบํารุง
						</h3>
					</div>
				</div>
				<div class="panel-body">
					<c:if
						test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
						<div class="column" align="right">
							<!-- 							<form action="serchDurable" class="form-inline" role="form" method="post"> -->
							<form action="do_searchMaintenance" class="form-inline" role="form"
								method="post">
								<c:choose>
									<c:when
										test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
										<label> &nbsp;&nbsp;&nbsp; สาขา : </label>
										<select class="form-control" name="major_id" id="major_id"
											style="margin-bottom: 15px; margin-top: 15px; width: 200px;"
											>
											<c:forEach var="major" items="${major}">
												<option value="${major.getID_Major()}"
													<c:if test="${ major_id == major.getID_Major() }">selected</c:if>>${major.getMajor_Name()}</option>
											</c:forEach>
										</select>
										<button type="submit" class="btn btn-sky text-uppercase"
											id="btn_search">
											<i class="fa fa-search"></i> ค้นหา
										</button>
									</c:when>
								</c:choose>
							</form>
						</div>
					</c:if>
					<c:choose>
						<c:when test="${not empty durable_maintenance }">
							<table style="margin-top: 10px">
								<tr>
									<th style="text-align: center">ลําดับ</th>
									<th style="text-align: center">รหัสครุภัณฑ์</th>
									<th style="text-align: center">ประเภทครุภัณฑ์</th>
									<th style="text-align: center">รายการ</th>
									<th style="text-align: center">รายละเอียด</th>
								</tr>
								<c:forEach var="rd" items="${ durable_maintenance }"
									varStatus="loop">
									<tr>
										<td style="text-align: center">${ loop.index+1 }</td>
										<td style="text-align: center">${ rd.getDurable_code() }</td>
										<td style="text-align: center">${ rd.getDurable_type() }</td>
										<td style="text-align: center">${ rd.getDurable_name() }</td>
										<td style="text-align: center"><button type="submit"
												class="btn btn-xs btn-fresh"
												onclick="location.href = 'do_loadMaintenanceDetail?durable_code=${ rd.getDurable_code() }'">รายละเอียด</button></td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							<h3 align="center">ไม่มีข้อมูลประวัติการซ่อมบํารุง</h3>
						</c:otherwise>

					</c:choose>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<c:if test="${ sessionScope.messages != null }">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
</body>
</html>