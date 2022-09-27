<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ac.th.itsci.durable.entity.*, java.util.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<title>เพิ่มประวัติการซ่อมบํารุง</title>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/maintenanceCheck.js"></script>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<script>
	var loadFile = function(event) {
		var image = document.getElementById('output');
		image.src = URL.createObjectURL(event.target.files[0]);
	};
</script>
<body>
	<fmt:setLocale value="th_TH" scope="session" />
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<!-- <img src="../../file/picture/pic.jpg" class="img-responsive"
		alt="Responsive image"> -->
	<!-- 		row -->
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<form action="do_AddMaintenance" name="maintenance"
				class="form-horizontal" method="post">
				<!-- <fmt:formatDate value="${now}" pattern="dd MMMM yyyy" var="searchFormated" /> -->
				<!-- <c:set var="strDate" value="${searchFormated}" />  -->
				<input type="hidden" id="save_date" name="save_date"
					value="${strDate}">

				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-registered" aria-hidden="true"></i>
							บันทึกการซ่อมบํารุง
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
											<i class="fa fa-folder-o"></i> ข้อมูลครุภัณฑ์
										</legend>
										<div class="form-group">
											<label class="col-sm-5 control-label">ประเภทครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="" name=""
													value="${ durable_data.getDurable_type() }"
													readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durablecode"
													name="durablecode"
													value="${ durable_data.getDurable_code() }"
													readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รายการ :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durable_name"
													name="durable_name"
													value="${ durable_data.getDurable_name() }"
													readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รูปภาพ :: </label>
											<div class="col-sm-5">
												<img width="70%" style="margin-left: 15%;"
													src="${pageContext.request.contextPath}/file/durable_image/${ durable_data.getDurable_image() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durable_name"
													name="durable_name"
													value="${ durable_data.getDurable_model() }"
													readonly="readonly">
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------End Durable DATA--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i> ข้อมูลการซ่อมบํารุง
										</legend>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันเดือนปีที่ส่งซ่อม
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="datepicker" value="">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียดการซ่อม
												:: </label>
											<div class="col-sm-5">
												<textarea class="form-control" name="maintenance_detail"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">จํานวนเงิน :: </label>
											<div class="col-sm-5">
												<input class="form-control" type="text"
													name="maintenance_price" id="maintenance_price">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">บริษัทที่เข้ารับการซ่อม
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" name="company"
													id="company">
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
						<div class="row">
							<c:if test="${ maintenance_detail.size() != 0 }">
								<table style="margin-top: 10px">
									<tr>
										<th style="text-align: center">ครั้งที่</th>
										<th style="text-align: center" width="150">วันเดือนปี</th>
										<th style="text-align: center" width="500">รายการ</th>
										<th style="text-align: center">จํานวนเงิน</th>
										<th style="text-align: center">หมายเหตุ</th>
										<th style="text-align: center">เพิ่มเติม</th>
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
											<td style="text-align: center">
												<button class="btn btn-xs btn-sunny"
													formaction="do_loadEditMaintenance?repair_id=${ rd.getRepair_id()}">แก้ไขรายละเอียดการซ่อม</button>
											</td>
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
							</c:if>
							<c:if test="${ maintenance_detail.size() == 0 }">
								<h3 align="center">ยังไม่มีประวัติการซ่อมบํารุง</h3>
							</c:if>
						</div>
					</div>
					<!-- **********End Panel Body***********-->
					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_addrepair" onClick="return maintenanceCheck(maintenance)">
									<i class="fa fa-floppy-o"></i> บันทึก
								</button>
								<button type="reset" class="btn btn-hot text-uppercase"
									onclick="history.back(-1)">
									<i class="fa fa-times"></i> ยกเลิก
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

	<%-- 	<script type="text/javascript"
		src="/dist/js/custom/js_recorddurable.js"></script>
 --%>
	<c:if test="${not empty sessionScope.messages}">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
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
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
	<c:remove var="item_search" scope="session" />
</body>




</html>