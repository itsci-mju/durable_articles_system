<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, ac.th.itsci.durable.entity.*,java.text.*"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>แก้ไขข้อมูลตรวจสอบครุภัณฑ์</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta name="viewport" content="width=device-width">
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body onload="check_repair()">
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<c:choose>
		<c:when test="${ not empty verify_check }">
			<div class="row" style="margin-top: 70px">
				<div class="container">
					<form action="do_updateVerify" name="verify_form" class="form-horizontal"
						method="post">
						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">แก้ไขข้อมูลการตรวจสอบครุภัณฑ์</h3>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-sm-6">
										<div class="row" style="margin: 10px">
											<fieldset>
												<legend style="font-size: 1em">
													<i class="fa fa-folder-o"></i> ข้อมูลครุภัณฑ์
												</legend>
												<div class="form-group">
													<label class="col-sm-5 control-label">รหัสครุภัณฑ์
														::</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" id="DurableCode"
															name="DurableCode" style="width: 200px" readonly
															value="${ vd.getPk().getDurable().getDurable_code() }">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-5 control-label">รายการ :: </label>
													<div class="col-sm-5">
														<input type="text" class="form-control" id="durable_name"
															name="durable_name"
															value="${ vd.getPk().getDurable().getDurable_name() }"
															readonly="readonly">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-5 control-label">รูปภาพ :: </label>
													<div class="col-sm-5">
														<img width="70%" style="margin-left: 15%;"
															src="${pageContext.request.contextPath}/file/durable_image/${ vd.getPk().getDurable().getDurable_image() }">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-5 control-label">รายละเอียด ::
													</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" id="durable_name"
															name="durable_name"
															value="${ vd.getPk().getDurable().getDurable_model() }"
															readonly="readonly">
													</div>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="row" style="margin: 10px">
											<fieldset>
												<legend style="font-size: 1em">
													<i class="fa fa-folder-o"></i> ข้อมูลการตรวจสอบครุภัณฑ์
												</legend>
												<div class="form-group">
													<div class="row">
														<label class="col-sm-4 control-label">รหัสครุภัณฑ์</label>
														<div class="col-sm-5">
															<input type="text" class="form-control" id="DurableCode"
																name="DurableCode" style="width: 200px" readonly
																value="${ vd.getPk().getDurable().getDurable_code() }">
														</div>
													</div>
												</div>

												<div class="form-group">
													<div class="row">

														<label class="col-sm-4 control-label">ปีที่ตรวจสอบ</label>
														<div class="col-sm-5">
															<input type="text" class="form-control" id="yearVerify"
																name="yearVerify" style="width: 200px" readonly
																value="${ vd.getPk().getVerify().getYears() }">
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<label class="col-sm-4 control-label">ผู้ตรวจสอบ</label>
														<div class="col-sm-5">
															<input type="text" class="form-control" id="staff"
																name="staff" style="width: 200px" readonly
																value="${ vd.getPk().getStaff().getStaff_name() }">
															<input type="hidden" class="form-control" id="id_staff"
																name="id_staff" style="width: 200px" readonly
																value="${ vd.getPk().getStaff().getId_staff()}">
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label class="col-sm-4 control-label">ห้อง</label>
														</div>
														<div class="col-sm-5">
															<select class="col-sm-4 form-control" id="roomDurable"
																name="roomDurable" style="width: 200px">
																<c:forEach items="${ room_bean }" var="room_bean">
																	<option value="${room_bean.getRoom_number() }"
																		${vd.getPk().getDurable().getRoom().getRoom_number() == room_bean.getRoom_number() ? 'selected' : ''}>${ room_bean.getRoom_number()}</option>
																</c:forEach>
															</select>

														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label class="col-sm-4 control-label">ผู้รับผิดชอบดูแล</label>
														</div>
														<div class="col-sm-5">
															<select class="col-sm-4 form-control" id="responsible_person"
																name="responsible_person" style="width: 200px">
																<c:forEach items="${ responsible_person }"
																	var="responsible_person">
																	<option value="${responsible_person }"
																		${p = responsible_person ? 'selected' : ''}>${responsible_person}</option>
																</c:forEach>
															</select>

														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label class="col-sm-4 control-label">สถานะครุภัณฑ์</label>
														</div>
														<div class="col-sm-5">
															<select class="col-sm-4 form-control" id="statusDurable"
																name="statusDurable" style="width: 200px" onchange="check_repair()">
																<option value="ดี"
																	${vd.durable_status == 'ดี' ? 'selected' : ''}>ดี</option>
																<option value="ชำรุด"
																	${vd.durable_status == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
																<option value="หมดความจําเป็น"
																	${listdu.durable_status == 'หมดความจําเป็น' ? 'selected' : ''}>หมดความจําเป็น</option>
															</select>

														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<label class="col-sm-4 control-label">หมายเหตุ</label>
														<div class="col-sm-5">
															<input type="text" class="form-control" id="note"
																name="note" style="width: 200px;" value="${ vd.note }" disabled>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</div>
								</div>
							</div>
							<div class="panel-footer" style="height: 65px">
								<div class="form-group">
									<div align="right" style="margin-right: 15px">
										<button type="submit" class="btn btn-sky text-uppercase" id="" onclick="return verify_form_check(verify_form)">
											<i class="fa fa-floppy-o"></i> บันทึก
										</button>
										<button type="reset" class="btn btn-hot text-uppercase"
											onclick="history.back()" >
											<i class="fa fa-times"></i> ยกเลิก
										</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:when>
		<%-- <c:otherwise>
			<div class="row" style="margin-top: 70px">
				<div class="container">
					<form action="do_updateVerify" name="" class="form-horizontal"
						method="post" onSubmit="">
						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">ตรวจเช็คครุภัณฑ์</h3>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-sm-12">
										<div class="row" style="margin: 10px">
											<fieldset>
												<legend style="font-size: 1em">
													<i class="fa fa-folder-o"></i> ข้อมูลวัสดุ
												</legend>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label for="exampleFormControlInput1">รหัสครุภัณฑ์</label>
														</div>
														<div class="column02">
															<input type="text" class="form-control" id="DurableCode"
																name="DurableCode" style="width: 200px" readonly>
														</div>
													</div>
												</div>

												<div class="form-group">
													<div class="row">
														<div class="column">
															<label for="exampleFormControlInput1">ปีที่ตรวจสอบ</label>
														</div>
														<div class="column02">
															<input type="text" class="form-control" id="yearVerify"
																name="yearVerify" style="width: 200px" readonly>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label for="exampleFormControlInput1">ผู้ตรวจสอบ</label>
														</div>
														<div class="column02">
															<input type="text" class="form-control" id="staff"
																name="staff" style="width: 200px" readonly> <input
																type="hidden" class="form-control" id="id_staff"
																name="id_staff" style="width: 200px" readonly>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label for="exampleFormControlInput1">สถานะครุภัณฑ์</label>
														</div>
														<div class="column02">
															<select class="col-sm-4 form-control" id="statusDurable"
																name="statusDurable" style="width: 200px" onchange="check_repair()">
																<option value="ดี"
																	${listdu.durable_status == 'ดี' ? 'selected' : ''}>ดี</option>
																<option value="ชำรุด"
																	${listdu.durable_status == 'ชำรุด' ? 'selected' : ''}>ชำรุด</option>
																<option value="รอซ่อม"
																	${listdu.durable_status == 'รอซ่อม' ? 'selected' : ''}>รอซ่อม</option>
																<option value="แทงจำหน่าย"
																	${listdu.durable_status == 'แทงจำหน่าย' ? 'selected' : ''}>แทงจำหน่าย</option>
															</select>

														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="column">
															<label for="exampleFormControlInput1">หมายเหตุ</label>
														</div>
														<div class="column02">
															<input type="text" class="form-control" id="note"
																name="note" style="width: 200px;" disabled>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</div>
								</div>
							</div>
							<div class="panel-footer" style="height: 65px">
								<div class="form-group">
									<div align="right" style="margin-right: 15px">
										<button type="submit" class="btn btn-sky text-uppercase" id="">
											<i class="fa fa-floppy-o" onclick="return verify_form_checkQR()"></i> บันทึก
										</button>
										<button type="reset" class="btn btn-hot text-uppercase"
											onclick="history.back()">
											<i class="fa fa-times"></i> ยกเลิก
										</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:otherwise> --%>
	</c:choose>
</body>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/custom/verify_check_all.js"></script>
</html>