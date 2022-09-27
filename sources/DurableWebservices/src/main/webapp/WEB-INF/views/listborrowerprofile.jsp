<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<c:if test="${sessionScope.staffSession.staff_name == null}">
		<jsp:forward page="/durable"></jsp:forward>
	</c:if>
	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-tag"></i> ข้อมูลผู้ยืมครุภัณฑ์
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->
				<div class="panel-body">
					<!-- 				Table -->
					<form action="ListBorrowerProfileServlet?method=show"
						class="form-inline" role="form" method="post">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<label>รหัสประชาชน : </label> <input type="text"
									class="form-control" id="idcode" name="idcode">
								<button type="submit" class="btn btn-sky text-uppercase btn-sm"
									id="search">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form>
				</div>

				<div class="bs-example"
					data-example-id="thumbnails-with-custom-content">
					<div class="row">
						<c:choose>
							<c:when test="${borrowwer_list != null}">
								<c:forEach var="list_borrowwers" items="${borrowwer_list}">
									<div class="col-sm-6 col-md-4">
										<div class="thumbnail">
											<table class="table table-condensed">
												<thead>
													<tr>
														<th>รูปบัตรประชาชน</th>
													</tr>
												</thead>
											</table>
											<img data-src="holder.js/100%x200" alt="100%x200"
												src="../WebContent/file/borrower/${list_borrowwers.borrower_picture}"
												data-holder-rendered="true"
												style="height: 200px; width: 100%; display: block;">
											<div class="caption">

												<table class="table table-condensed">
													<thead>
														<tr>
															<th>ข้อมูลผู้ยืม</th>
														</tr>
													</thead>
													<thead>
														<tr>
															<td>ชื่อ-นามสกุล : ${list_borrowwers.borrower_name}</td>
														</tr>
														<tr>
															<td>รหัสประชาชน : ${list_borrowwers.idcard_borrower}</td>
														</tr>
														<tr>
															<td>เบอร์โทรติดต่อ :
																${list_borrowwers.telephonenumber}</td>
														</tr>
														<tr>
															<td>เคยยืมไปแล้ว : ${list_borrowwers.countBorrow}
																รายการ</td>
														</tr>
														<tr>
															<td>ครุภัณฑ์ที่ยังไม่คืน :
																${list_borrowwers.countreturns} รายการ</td>
														</tr>
													</thead>
												</table>

											</div>
										</div>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<p align="center">ไม่พบข้อมูล</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>




				<!-- 				End Table -->
				<c:remove var="list_borrowing" scope="session" />
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<script type="text/javascript"
	src="/dist/js/custom/js_pagelistborrower.js"></script>
<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
<c:remove var="item_search" scope="session" />
</html>