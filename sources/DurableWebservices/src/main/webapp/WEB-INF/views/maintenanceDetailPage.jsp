<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>ประวัติการซ่อมบํารุง</title>
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
							<i class="fa fa-floppy-o"></i> ประวัติการซ่อมบํารุง
						</h3>
					</div>
				</div>
				<div class="panel-body">
				<div class="column" align="left">
					<label> รหัสครุภัณฑ์ :: ${ maintenance_detail.get(0).getDurable().getDurable_code() }</label><br>
					<label> รายการ :: ${ maintenance_detail.get(0).getDurable().getDurable_name() }</label>
				</div>
					<table style="margin-top: 10px">
						<tr>
							<th style="text-align: center">ครั้งที่</th>
							<th style="text-align: center" width="150">วันเดือนปี</th>
							<th style="text-align: center" width="500">รายการ</th>
							<th style="text-align: center">จํานวนเงิน</th>
							<th style="text-align: center">หมายเหตุ</th>
							<th style="text-aling: center">เพิ่มเติม</th>
						</tr>
						<c:forEach var="rd" items="${ maintenance_detail }"
							varStatus="loop">
							<tr>
								<td style="text-align: center">${ loop.index+1 }</td>
								<td style="text-align: center" width="150">${ rd.getDate_of_repair() }</td>
								<td style="text-align: center" width="500">${ rd.getRepair_detail() }</td>
								<td style="text-align: center"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ rd.getRepair_charges() }" /></td>
								<td style="text-align: center">${ rd.getCompany().getCompanyname() }</td>
								<td style="text-align: center"><c:if test="${ rd.getDurable().getMajor().getID_Major() == sessionScope.staffSession.major.ID_Major }"><button
										class="btn btn-xs btn-sunny"
										onclick="location.href='do_loadEditMaintenance?repair_id=${ rd.getRepair_id()}'">แก้ไขรายละเอียดการซ่อม</button></c:if></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3" style="text-align: right">รวม</td>
							<td style="text-align: center"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ price }" /></td>
							<td colspan="2"></td>
						</tr>
					</table>
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