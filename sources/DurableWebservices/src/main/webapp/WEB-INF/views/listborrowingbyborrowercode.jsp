<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" href="/dist/css/custom/table-listborrowing.css">
</head>
<body>

	<jsp:include
	page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<fmt:setLocale value="th_TH" scope="session"/>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<fmt:formatDate value="${now}" var="searchFormated"
		 pattern="dd MMMM yyyy" />
	<c:set var="strDate" value="${searchFormated}" />
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
						action="/ListBorrowingByBorrowercodeServlet?method=show"
						class="form-inline" role="form" method="post">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<label>รหัสประชาชน : </label> <input type="text"
									class="form-control" id="student_code" name="student_code"
									size="10">
								<button type="submit" class="btn btn-sky text-uppercase btn-sm"
									id="search">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form>
				</div>
				<!-- 				End Panel Body -->
				
						<c:choose>
							<c:when test="${listborrowing != null}">
								<table>
									<thead>
										<tr>
											<th></th>
											<th>รหัสประชาชน</th>
											<th>ชื่อผู้ยืม</th>
											<th>หมายเลขโทรศัพท์ผู้ยืม</th>
											<th>รหัสครุภัณฑ์</th>
											<th>ชื่อครุภัณฑ์</th>
											<th>วันที่ยืม</th>
											<th>วันที่กำหนดคืน</th>
											<th></th>
										</tr>
									</thead>
									<c:forEach var="borrowings" items="${listborrowing}"
										varStatus="loop">
										<tbody>
											<tr>
												<td>${loop.index+1}</td>
												<td>${borrowings.borrowerBean.idcard_borrower}</td>
												<td>${borrowings.borrowerBean.borrower_name}</td>
												<td>${borrowings.borrowerBean.telephonenumber}</td>
												<td>${borrowings.durableBean.durable_code}</td>
												<td>${borrowings.durableBean.durable_name}</td>
												<td>${borrowings.borrow_date}</td>
												<td>${borrowings.schedurablereturn_date}</td>
												<td>
												<a href="/LoanDurableServlet?borrowing_id=${borrowings.borrowing_id}&return_date=${strDate}&borrowing_id=${borrowings.borrowing_id}">	<button type="submit"
														class="btn btn-fresh text-capitalize btn-xs" id="btn_loan"
														name="btn_loan" value="${borrowings.borrowing_id}">
														<i class="fa fa-tag"></i> คืนครุภัณฑ์
													</button></a>
												</td>
											</tr>
										</tbody>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<p align="center">ไม่พบข้อมูล</p>
							</c:otherwise>
						</c:choose>
				
				<c:remove var="borrowings" scope="session" />
				<c:remove var="status" scope="session" />
				<!-- 				End Table -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<c:if test="${sessionScope.staffSession.staff_name == null}">
<jsp:forward page="/"></jsp:forward>
</c:if>
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
<c:remove var="item_search" scope="session" />
<script type="text/javascript"
	src="/dist/js/custom/js_pagelistborrowing1.js"></script>
<script type="text/javascript"
	src="/dist/js/custom/ajax_loandurable.js"></script>
</html>