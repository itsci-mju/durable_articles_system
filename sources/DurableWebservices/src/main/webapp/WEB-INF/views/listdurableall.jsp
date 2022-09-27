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
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-print"></i> รายการพิมพ์รหัสคิวอาร์
					</h3>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-bordered panel">
							<thead>
								<tr>
									<th class="info">เลือก</th>
									<th class="info">รหัสครุภัณฑ์</th>
									<th class="info">จำนวน</th>
									<th class="info">ชื่อครุภัณฑ์</th>
									<th class="info">ยี่ห้อ</th>
									<th class="info">รุ่น/รายละเอียด</th>
									<th class="info">ห้องที่ใช้</th>
									<th class="info">ชื่อผู้ใช้</th>
								</tr>
							</thead>
							<c:forEach var="listdu" items="${listall}">
								<thead>
									<tr>
										<td align="center"><input type="checkbox" id="select"
											name="select" value="${listdu.durablecode}"></td>
										<td align="center">${listdu.durablecode}</td>
										<td align="center">${listdu.numberofdurable}</td>
										<td align="center">${listdu.durablename}</td>
										<td align="center">${listdu.brandname}</td>
										<td align="center">${listdu.modelordetail}</td>
										<c:forEach var="listpro" items="${listdu.durableprofiles}">
											<td align="center">${listpro.room.roomnumber}</td>
											<td align="center">${listpro.owner.owner_name}</td>
										</c:forEach>
									</tr>
								</thead>
							</c:forEach>
						</table>
					</div>
					<c:remove var="listall" scope="session" />
				</div>
			</div>
		</div>
		<!-- 		container -->
	</div>
	<!-- 	row -->
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
<c:remove var="item_search" scope="session" />
</html>