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
<fmt:setLocale value="th_TH" scope="session"/>
	<c:set var="now" value="<%=new java.util.Date()%>" />
<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<div class="row" style="margin-top: 80px">
		<div class="container">
			<form action="borrowing"
				class="form-horizontal" enctype="multipart/form-data" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-tag"></i> ยืมครุภัณฑ์
						</h3>
					</div>
					<!-- **********Panel Body***********-->
					<div class="panel-body">

						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------Durable DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> ข้อมูลการยืมครุภัณฑ์
										</legend>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durablecode"
													name="durablecode">
											</div>
										</div>
										 <fmt:formatDate value="${now}" pattern="dd MMMM yyyy"
											var="searchFormated" />
										<c:set var="strDate" value="${searchFormated}" /> 
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ยืม ::</label> <div class="col-sm-5">
												<%-- <input type="hidden" class="form-control" id="date_now"
													name="date_now" value="${strDate}" > --%>
													<input type="text" class="form-control" id="datepicker"
													name="date_now" value="${strDate}">
													<%-- <span
												class="col-sm-12" id="date_now" >${strDate}</span> --%>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่กำหนดคืน
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker2"
													name="schedurablereturn_date">
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------End Durable DATA------------------------------------- -->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-user"></i> ข้อมูลผู้ยืม
										</legend>

										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสประชาชน
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="idcard"
													name="idcard" maxlength="13">
												<p style="color: red">** ตัวอย่าง 1369900267937</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อผู้ยืม :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="borrowname"
													name="borrowname">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หมายเลขโทรศัพท์ผู้ยืม
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="borrowtel"
													name="borrowtel">
												<p style="color: red">** ตัวอย่าง 0XXXXXXXXX</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รูปภาพผู้ยืม ::
											</label>
											<div class="col-sm-5">
											<div class="input-group-addon">
													<input type="checkbox" name="checkbox4" id="checkbox4">
												</div>
												<input type="file" class="form-control" id="borrowpicture"
													name="borrowpicture" disabled>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
					</div>
					<!-- **********End Panel Body***********-->
					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<input type="hidden" name="staffid" id="staffid"
									value="${sessionScope.staffSession.id_staff}"> <input
									type="hidden" id="save_date" name="save_date"
									value="${strDate}">
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_borrow">
									<i class="fa fa-floppy-o"></i> บันทึก
								</button>
								<button type="reset" class="btn btn-hot text-uppercase">
									<i class="fa fa-times"></i> ยกเลิก
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>

<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

<script type="text/javascript"
	src="/dist/js/custom/js_pageborrow.js"></script>
<script type="text/javascript"
		src="/dist/js/custom/js_disableupfilepicture.js"></script>
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