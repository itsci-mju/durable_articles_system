<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>เพิ่มไฟล์ครุภัณฑ์</title>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<jsp:useBean id="date" class="java.util.Date" />
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<%-- <script
	src="${pageContext.request.contextPath}/dist/js/custom/file_check.js"></script> --%>
</head>
<body>

	<c:if test="${sessionScope.staffSession.staff_name == null}">
		<jsp:forward page="/durable"></jsp:forward>
	</c:if>
	<fmt:setLocale value="th_TH" scope="session" />
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<%@include file="common/navbarmenu.jsp"%>
	<!-- content -->
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-file-o"></i> เพิ่มไฟล์ครุภัณฑ์
					</h3>
				</div>
				<div class="panel-body">
					<form action="doimportFileDurable" class="form-horizontal"
						method="POST" name="exfrm" enctype='multipart/form-data'>
						<div class="form-group">
							<label class="col-sm-5 control-label"> เพิ่มไฟล์ครุภัณฑ์</label>
							<div class="col-sm-3">
								<input type="file" id="filedurable" name="filedurable"
									class="form-control" /> <br>
								<p style="color: red">** ไฟล์ที่อัพโหลดต้องเป็นไฟล์นามสกุล
									xlsx</p>
							</div>
						</div>
						<div align="center">
							<p style="color: red">(ก่อนอัพโหลดไฟล์กรุณาเช็คไฟล์ excel
								ของท่านก่อนอัพโหลดว่ามีหัวข้อตามที่กำหนดไว้หรือไม่)</p>
							<p style="color: red">(ลำดับที่, รหัสครุภัณฑ์, จำนวน,
								ชื่อครุภัณฑ์, ยี่ห้อ, รุ่น/รายละเอียด, ราคา/หน่วย,
								วัน/เดือน/ปี(ที่ได้มา), ห้อง, ชื่อบุคคลผู้ใช้,
								ผู้ดูแลรับผิดชอบในการตรวจสอบ)</p>
							<p>...........................................................................(
								*** ตัวอย่างไฟล์เอกสาร Excel ***
								)...............................................................................</p>
							<img
								src="${pageContext.request.contextPath}/images/title_excel_import.png"><br>
							<br>
							<br>
						</div>
						<div align="center">
							<button type="submit" class="btn btn-sky text-uppercase"
								id="btn_upload" onclick="return checkimport(exfrm)">
								<i class="fa fa-cloud-upload"></i> อัพโหลด
							</button>

						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
	<!-- container -->
</body>

<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

<!-- <script type="text/javascript" -->
<%-- 	src="/dist/js/custom/js_import.js"></script> --%>

<c:if test="${value == 1}">
	<c:if test="${message != null }">
		<script>
			var msg = '${message}';
			alert(msg)
		</script>
		<c:remove var="message" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
</c:if>
<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
<c:remove var="item_search" scope="session" />
</html>