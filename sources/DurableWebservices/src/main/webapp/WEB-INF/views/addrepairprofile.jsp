<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->

	<div class="row" style="margin-top: 90px">
		<div class="container">
			<form action="/AddRepairServlet" name="repairfrom"
				class="form-horizontal" enctype="multipart/form-data" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-floppy-o"></i> บันทึกข้อมูลการซ่อมครุภัณฑ์
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
											<label class="col-sm-5 control-label">รหัสครุภัณฑ์ ::
											</label>

											<div class="col-sm-5">


												<c:if test="${type == 1}">
													<input type="hidden" class="form-control" id="durablecode"
														name="durablecode" value="${durable_code}">
													<input type="text" class="form-control" id="durablecode1"
														name="durablecode1" value="${durable_code}"
														disabled="disabled">
												</c:if>
												<c:if test="${type != 1}">
													<input type="text" class="form-control" id="durablecode"
														name="durablecode" value="${durable_code}">
												</c:if>

											</div>

										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">หัวข้อการส่งซ่อม
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="titlename"
													name="titlename">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ส่งซ่อม
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="repairdate">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ค่าใช้จ่าย ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="charges"
													name="charges">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="detail"
													name="detail"></textarea>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">บริษัทผู้รับผิดชอบ
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="company"
													name="company">
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
											<i class="fa fa-picture-o"></i> รูปภาพประกอบ
										</legend>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใบส่่งซ่อม ::</label>
											<div class="input-group col-sm-5">
												<div class="input-group-addon">
													<input type="checkbox" name="checkbox1" id="checkbox1">
												</div>
												<input class="form-control" type="file"
													id="pic_repairreport" name="pic_repairreport" disabled>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ใบเสนอราคา ::</label>
											<div class="input-group col-sm-5">
												<div class="input-group-addon">
													<input type="checkbox" name="checkbox2" id="checkbox2">
												</div>
												<input class="form-control" type="file" id="pic_quatation"
													name="pic_quatation" disabled>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ใบรับของ ::</label>
											<div class="input-group col-sm-5">
												<div class="input-group-addon">
													<input type="checkbox" name="checkbox3" id="checkbox3">
												</div>
												<input class="form-control" type="file" id="pic_invoice"
													name="pic_invoice" disabled>
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
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_addrepair">
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
	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<script type="text/javascript"
		src="/dist/js/custom/js_disableupfilepicture.js"></script>
	<script type="text/javascript"
		src="/dist/js/custom/js_pageadddrepair.js"></script>

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