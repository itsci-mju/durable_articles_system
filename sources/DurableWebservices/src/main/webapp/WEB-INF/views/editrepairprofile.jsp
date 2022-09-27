<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />

<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>

</head>
<body>
<<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<div class="row" style="margin-top: 50px">
		<div class="container">
			<form class="form-horizontal" role="form"
				action="/editRepair"
				method="post" enctype="multipart/form-data">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-pencil-square-o"></i> แก้ไขข้อมูลการซ่อมครุภัณฑ์
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
												<input type="text" class="form-control" id="durablecode"
													name="durablecode"
													value="${re_profile.durableBean.durable_code}"> 
													<input
													type="hidden" id="re_id" name="re_id" value="${re_id}" />
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">หัวข้อการส่งซ่อม
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="titlename"
													name="titlename" value="${re_profile.repair_title}">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ส่งซ่อม
												::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="repairdate" value="${re_profile.repair_date}">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ค่าใช้จ่าย ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="charges"
													name="charges" value="${re_profile.repair_charges}">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="detail"
													name="detail">${re_profile.repair_detail}</textarea>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">บริษัทผู้รับผิดชอบ
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="company"
													name="company"
													value="${re_profile.companyBean.companyname}">
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
										<div class="col-sm-3">
										<a class="fancybox"
											href="../WebContent/file/borrower/${re_profile.picture_repairreport}"
												data-fancybox-group="gallery" title="ใบส่่งซ่อม">
											<img
												src="../WebContent/file/borrower/${re_profile.picture_repairreport}"
												class="img-responsive" height="100" width="100"></a> 		</div>
											<label class="col-sm-3 control-label">ใบส่่งซ่อม ::</label>
											<div class="input-group col-sm-5">
												<div class="input-group-addon">
													<input type="checkbox" name="checkbox1" id="checkbox1">
												</div>
												<input class="form-control" type="file"
													id="pic_repairreport" name="pic_repairreport" disabled>
											</div>
										</div>

										<div class="form-group">
										<div class="col-sm-3">
										<a class="fancybox"
											href="../WebContent/file/borrower/${re_profile.picture_quatation}"
												data-fancybox-group="gallery" title="ใบเสนอราคา">
										<img
												src="../WebContent/file/borrower/${re_profile.picture_quatation}"
												class="img-responsive"  height="100" width="100">	</a>	</div>
											<label class="col-sm-3 control-label">ใบเสนอราคา ::</label>
											<div class="input-group col-sm-5">
												<div class="input-group-addon">
													<input type="checkbox" name="checkbox2" id="checkbox2">
												</div>
												<input class="form-control" type="file" id="pic_quatation"
													name="pic_quatation" disabled>
											</div>
										</div>

										<div class="form-group">
										<div class="col-sm-3">
										<a class="fancybox"
											href="../WebContent/file/borrower/${re_profile.picture_invoice}"
												data-fancybox-group="gallery" title="ใบรับของ">
										<img
												src="../WebContent/file/borrower/${re_profile.picture_invoice}"
												
												class="img-responsive"  height="100" width="100">	</a>	</div>
										
											<label class="col-sm-3 control-label">ใบรับของ ::</label>
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
					<!-- **********End Panel Body**********7*-->
					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_editrepair">
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

</body>
</html>