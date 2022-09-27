<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 	   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<c:if test="${sessionScope.staffSession.staff_name == null}">
<jsp:forward page="/durable"></jsp:forward>
</c:if>
</head>
<body>
	<jsp:include
	page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-tag"></i> รายการคืนครุภัณฑ์
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->
				<div class="panel-body">
					<!-- 				Table -->
					<form
						action="/ListBorrowingByDurablecodeServlet?method=show"
						class="form-inline" role="form" method="post">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<label>รหัสครุภัณฑ์ : </label> <input type="text"
									class="form-control" id="durablecode" name="durablecode">
								<button type="submit" class="btn btn-sky text-uppercase btn-sm"
									id="search">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form>
				</div>
				
				
						<c:choose>
							<c:when test="${borrowing_list != null}">
								<table class="table table-condensed">
									<thead>
										<tr>
											<th>รหัสครุภัณฑ์</th>
											<th>ชื่อครุภัณฑ์</th>
											<th>วัน/เดือน/ปี ที่ยืม</th>
											<th>วัน/เดือน/ปี กำหนดคืน</th>
											<th>วัน/เดือน/ปี คืน</th>
											<th>ชื่อผู้ยืม</th>
											<th>หมายเลขโทรศัพท์ผู้ยืม</th>
											<th>สถานะ</th>
										</tr>
									</thead>
									<c:forEach var="list_borrowing" items="${borrowing_list}">
										<thead>
											<tr>
												<td>${list_borrowing.durableBean.durable_code}</td>
												<td>${list_borrowing.durableBean.durable_name}</td>
												<td>${list_borrowing.borrow_date}</td>
												<td>${list_borrowing.schedurablereturn_date}</td>
												<td>${list_borrowing.return_date}</td>
												<td>${list_borrowing.borrowerBean.borrower_name}</td>
												<td>${list_borrowing.borrowerBean.telephonenumber}</td>
												<td>${list_borrowing.borrow_status}</td>
											</tr>
										</thead>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
							<p align="center"> ไม่พบข้อมูล </p>
							</c:otherwise>
						</c:choose>
					
				
				<!-- 				End Table -->
				<c:remove var="list_borrowing" scope="session" />
				<c:remove var="status" scope="session" />
				<c:remove var="item_search" scope="session" />
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<script type="text/javascript"
	src="/dist/js/custom/js_pagelistborrowing.js"></script>
</html>