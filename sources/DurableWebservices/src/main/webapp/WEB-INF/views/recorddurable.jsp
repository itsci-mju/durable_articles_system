<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ac.th.itsci.durable.entity.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<body>
	<fmt:setLocale value="th_TH" scope="session" />
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<!-- <img src="../../file/picture/pic.jpg" class="img-responsive"
		alt="Responsive image"> -->
	<!-- 		row -->
	<div class="row" style="margin-top: 50px">
		<div class="container">
			<form
				action="doRecordDurable"
				name="recordfrom" class="form-horizontal" method="post">
				<!-- <fmt:formatDate value="${now}" pattern="dd MMMM yyyy" var="searchFormated" /> -->
				<!-- <c:set var="strDate" value="${searchFormated}" />  -->
				<input type="hidden" id="save_date" name="save_date"
					value="${strDate}">

				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-floppy-o"></i> บันทึกข้อมูลรหัสครุภัณฑ์
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
													name="durablecode">
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">จำนวนครุภัณฑ์
												:: :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="numdurable"
													name="numdurable">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อครุภัณฑ์ ::
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="namedurable"
													name="namedurable">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ยี่ห้อ :: :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="brand"
													name="brand">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ราคาต่อหน่วย ::
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="pricedurable"
													name="pricedurable">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ได้รับ ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="datepicker">
											</div>
										</div>



										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="detail"
													name="detail"> - </textarea>
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
											<i class="fa fa-file-o"></i> สถานะ
										</legend>


										<div class="form-group">
											<label class="col-sm-5 control-label">ห้องที่ใช้ครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" name="room_id" id="room_id">
													<option value="-">-</option>
													<c:forEach var="rooms" items="${roomBeanAll}">

														<option value="${rooms}">${rooms}</option>
															
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ผู้ใช้ครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" id="owner_id" name="owner_id">
													<option value="-">-</option>
													<c:forEach var="owners" items="${ownerBeanAll}">
														<option value="${owners}">${owners}</option>
													</c:forEach>
												</select>
											</div>
										</div> 
										<div class="form-group">
											<label class="col-sm-5 control-label">สถาพครุภัณฑ์ ::
											</label>
											<div class="col-sm-5">
												<select class="form-control" id="status" name="status">
													<option value="-">-</option>
													<option value="ดี">ดี</option>
													<option value="ชำรุด">ชำรุด</option>
													<option value="รอซ่อม">รอซ่อม</option>
													<option value="แทงจำหน่าย">แทงจำหน่าย</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ปีการศึกษา ::
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="termyear"
													name="termyear">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หมายเหตุ :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" id="note" name="note"> - </textarea>

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

<%-- 	<script type="text/javascript"
		src="/dist/js/custom/js_recorddurable.js"></script>
 --%>
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