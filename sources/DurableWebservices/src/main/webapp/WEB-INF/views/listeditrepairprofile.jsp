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
							<i class="fa fa-floppy-o"></i> รายการแก้ไขข้อมูลการซ่อมครุภัณฑ์
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->
				<div class="panel-body">
					<!-- 				Table -->
					<form
						action="/ListEditRepairServlet"
						class="form-inline" role="form">
						
					</form>
				</div>
									<table>
						<thead>
							<tr>
								<th></th>
								<th>รหัสครุภัณฑ์</th>
								<th>หัวข้อการส่งซ่อม</th>
								<th>ค่าใช้จ่าย</th>
								<th>รายละเอียด</th>
								<th>บริษัทผู้รับผิดชอบ</th>
								<th></th>
							</tr>
						</thead>
						<c:forEach var="list_re" items="${list_repairedit}"
							varStatus="loop">
							<thead>
								<tr>
									<td>${loop.index+1}</td>
									<td>${list_re.durableBean.durable_code}</td>
									<td>${list_re.repair_title}</td>
									<td>${list_re.repair_charges}</td>
									<td>${list_re.repair_detail}</td>
									<td>${list_re.companyBean.companyname}</td>
									<td>
									<form class="form-horizontal" role="form"
										action="/EditRepairServlet"
										method="get" enctype="multipart/form-data">
										
													<input type="hidden" class="form-control" id="repair_id"
													name="repair_id" value="${list_re.repair_id}" >
										<button type="submit" id="btn_edit" name="btn_edit"
											value="${list_re.repair_id}">แก้ไขข้อมูล</button>
												</form>
									</td>
								</tr>
							</thead>
						</c:forEach>
					</table>
					<!-- 				End Table -->
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>
</body>

<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<c:remove var="item_search" scope="session" />
</body>
</html>