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
<title>กําหนดหน้าที่การเซ็นเอกสาร</title>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/AssignCheck.js"></script>
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
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-registered" aria-hidden="true"></i>
						กําหนดหน้าที่การเซ็นเอกสาร
					</h3>
				</div>
				<!-- **********Panel Body***********-->
				<div class="panel-body">
					<form action="do_addAssignType" name="signs" class="form-horizontal"
						method="post">
						<div class="row">
							<div class="col-sm-12">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-user"></i> กําหนดหน้าที่การเซ็นเอกสาร
										</legend>
									</fieldset>
									<div class="form-group">
										<label class="col-sm-3 control-label">หน้าที่การเซ็นเอกสาร
											: </label>
										<div class="col-sm-5">
											<select class="form-control" name="assing_id" id="assing_id">
												<c:forEach items="${ assignType }" var="assignType">
													<option value="${ assignType.assignType_id }">${ assignType.assignType_name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">รายชื่อ : </label>
										<div class="col-sm-5">
											<select class="form-control" name="personnel_id"
												id="personnel_id">
												<option value="-">-</option>
												<c:forEach items="${ all }" var="all">
													<option value="${ all.getPersonnel_id() }">${all.getPersonnel_prefix() }
														${ all.getPersonnel_firstName() } ${ all.getPersonnel_lastName() }
														ตําแหน่ง ${ all.getPosition().getPosition_name() }</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-3">
											<button class="btn btn-fresh"
												onclick="return checkAssign(signs)">กําหนดหน้าที่</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<div class="row" style="margin: 10px">

						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นผู้เสนอขอซื้อ/จ้าง
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_1_1" items="${ doc_1_1 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้เสนอขอซื้อ/จ้าง หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_1_1.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_1_1.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นหัวหน้างานคลังและพัสดุ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_1_2" items="${ doc_1_2 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น หัวหน้างานคลังและพัสดุ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_1_2.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_1_2.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i> รายการหน้าที่การเซ็นผู้ลงบัญชี
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_1_3" items="${ doc_1_3 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้เสนอขอซื้อ/จ้าง หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_1_3.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_1_3.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นผู้เห็นชอบอนุมัติ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_1_4" items="${ doc_1_4 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้เห็นชอบอนุมัติ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_1_4.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_1_4.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นผู้จัดทําเอกสาร
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_2_1" items="${ doc_2_1 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้จัดทําเอกสาร หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_2_1.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_2_1.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นผู้จัดซื้อจัดจ้าง
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_2_2" items="${ doc_2_2 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้จัดซื้อจัดจ้าง หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_2_2.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_2_2.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นผู้ตรวจสอบบัญชีงบประมาณอนุมัติ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_2_3" items="${ doc_2_3 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ตรวจสอบบัญชีงบประมาณอนุมัติ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_2_3.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_2_3.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นคณบดีคณะวิทยาศาสตร์
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_2_6" items="${ doc_2_6 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น คณบดีคณะวิทยาศาสตร์ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_2_6.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_2_6.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นทราบและอนุมัติเบิกจ่าย
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_3_2" items="${ doc_3_2 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ทราบและอนุมัติเบิกจ่าย หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_3_2.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_3_2.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i>
												รายการหน้าที่การเซ็นอนุญาติให้จ่ายได้
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_4_1" items="${ doc_4_1 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น อนุญาติให้จ่ายได้ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_4_1.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_4_1.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i> รายการหน้าที่การเซ็นผู้เบิก
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_4_2" items="${ doc_4_2 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้เบิก หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_4_2.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_4_2.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i> รายการหน้าที่การเซ็นผู้รับของ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_4_3" items="${ doc_4_3 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้รับของ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_4_3.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_4_3.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post">
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i> รายการหน้าที่การเซ็นผู้จ่ายของ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th>ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_4_4" items="${ doc_4_4 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น ผู้จ่ายของ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_4_4.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_4_4.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<fieldset>
											<legend style="font-size: 1em">
												<i class="fa fa-file-o"></i> รายการหน้าที่การเซ็นบันทึกลงบัญชีวัสดุ
											</legend>
											<div class="form-group">
												<table>
													<tr>
														<th>ลําดับ</th>
														<th width="250">ชื่อ</th>
														<th>ตําแหน่ง</th>
														<th>เพิ่มเติม</th>
													</tr>
													<c:forEach var="doc_4_5" items="${ doc_4_5 }"
														varStatus="loop">
														<tr>
															<td>${ loop.index+1 }</td>
															<td>${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_firstName() }
																${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_lastName() }</td>
															<td>${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_position() }${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_tier() }</td>
															<td style="text-align: center"><button
																	class="btn btn-hot btn-xs"
																	onclick="return confirm('คุณต้องการลบ ${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_prefix() }${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_firstName() } ${ doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_lastName() } ออกจากหน้าที่การเซ็น บันทึกลงบัญชีวัสดุ หรือไม่ ?');"
																	formaction="do_removeAssign?personnel_id=${doc_4_5.getPersonnelAssign().getPersonnel().getPersonnel_id()}&assign_id=${doc_4_5.getPersonnelAssign().getAssignType().getAssignType_id()}">ลบ</button></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- **********End Panel Body***********-->
				<div class="panel-footer" style="height: 65px">
					<div class="form-group">
						<div align="right" style="margin-right: 15px">
							<button type="reset" class="btn btn-hot text-uppercase"
								onclick="location.href='durable'">
								<i class="fa fa-times"></i> ย้อนกลับ
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

	<%-- 	<script type="text/javascript"
		src="/dist/js/custom/js_recorddurable.js"></script>
 --%>
	<c:if test="${message != null }">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
		<c:remove var="value" scope="session" />
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

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>กําหนดหน้าที่การเซ็นเอกสาร</title>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/durableControlCheck.js"></script>
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
	<div class="row" style="margin-top: 100px">
		<div class="container">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-registered" aria-hidden="true"></i>
						กําหนดหน้าที่การเซ็นเอกสาร
					</h3>
				</div>
				<!-- **********Panel Body***********-->
				<div class="panel-body">
					<form action="do_addAssignType" name="assign"
						class="form-horizontal" method="post" onSubmit="">
						<input type="hidden" name="check" value="3">
						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------PERSONNEL DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> กําหนดหน้าที่การเซ็นเอกสาร หัวหน้างานคลัง
										</legend>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" name="personnel_id" id="personnel_id">
													<option value="-">-</option>
													<c:forEach items="${ cof }" var="cof">
														<option value="${ cof.getPersonnel_id() }">${cof.getPersonnel_prefix() }
															${ cof.getPersonnel_firstName() } ${ cof.getPersonnel_lastName() }
															ตําแหน่ง ${ cof.getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-3">
												<button class="btn btn-fresh">กําหนดหน้าที่</button>
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------Start History COF--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i>
											รายการหน้าที่การเซ็นหัวหน้างานคลัง
										</legend>
										<div class="form-group">
											<table>
												<tr>
													<th>ลําดับ</th>
													<th>ชื่อ</th>
													<th>ตําแหน่ง</th>
													<th>เพิ่มเติม</th>
												</tr>
												<c:forEach var="old_cof" items="${ old_cof }"
													varStatus="loop">
													<tr>
														<td>${ loop.index+1 }</td>
														<td>${ old_cof.getPersonnel_prefix() }${ old_cof.getPersonnel_firstName() }
															${ old_cof.getPersonnel_lastName() }</td>
														<td>${ old_cof.getPersonnel_position() } ${ old_cof.getPersonnel_tier() }</td>
														<td><button formaction="do_removeAssign?personnel_id=${ old_cof.getPersonnel_id() }" class="btn-xs btn-hot btn">ลบ</button></td>
													</tr>
												</c:forEach>
											</table>
										</div>

									</fieldset>
								</div>
							</div>
						</div>
					</form>
					<form action="do_addAssignType" name="assign"
						class="form-horizontal" method="post" onSubmit="">
						<input type="hidden" name="check" value="5">
						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------PERSONNEL DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> กําหนดหน้าที่การเซ็นเอกสาร ผู้เห็นชอบ/อนุมัติ
										</legend>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" name="personnel_id" id="personnel_id">
													<option value="-">-</option>
													<c:forEach items="${ secretary }" var="secretary">
														<option value="${ secretary.getPersonnel_id() }">${secretary.getPersonnel_prefix() }
															${ secretary.getPersonnel_firstName() } ${ secretary.getPersonnel_lastName() }
															ตําแหน่ง ${ secretary.getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-3">
												<button class="btn btn-fresh">กําหนดหน้าที่</button>
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------Start History Secretary--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i>
											รายการหน้าที่การเซ็นผู้เห็นชอบ/อนุมัติ
										</legend>
										<div class="form-group">
											<table>
												<tr>
													<th>ลําดับ</th>
													<th width="250">ชื่อ</th>
													<th>ตําแหน่ง</th>
													<th>เพิ่มเติม</th>
												</tr>
												<c:forEach var="old_secretary" items="${ old_secretary }"
													varStatus="loop">
													<tr>
														<td>${ loop.index+1 }</td>
														<td>${ old_secretary.getPersonnel_prefix() }${ old_secretary.getPersonnel_firstName() }
															${ old_secretary.getPersonnel_lastName() }</td>
														<td>${ old_secretary.getPersonnel_position() } ${ old_secretary.getPersonnel_tier() }</td>
														<td><button formaction="do_removeAssign?personnel_id=${ old_secretary.getPersonnel_id() }" class="btn-xs btn-hot btn">ลบ</button></td>
													</tr>
												</c:forEach>
											</table>
										</div>

									</fieldset>
								</div>
							</div>
						</div>
					</form>
					
					<form action="do_addAssignType" name="assign"
						class="form-horizontal" method="post" onSubmit="">
						<input type="hidden" name="check" value="4">
						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------PERSONNEL DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> กําหนดหน้าที่การเซ็นเอกสาร เจ้าหน้าที่ลงบัญชี
										</legend>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" name="personnel_id" id="personnel_id">
													<option value="-">-</option>
													<c:forEach items="${ cof }" var="cof">
														<option value="${ cof.getPersonnel_id() }">${cof.getPersonnel_prefix() }
															${ cof.getPersonnel_firstName() } ${ cof.getPersonnel_lastName() }
															ตําแหน่ง ${ cof.getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-3">
												<button class="btn btn-fresh">กําหนดหน้าที่</button>
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------Start History Secretary--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i>
											รายการหน้าที่การเซ็นเจ้าหน้าที่ลงบัญชี
										</legend>
										<div class="form-group">
											<table>
												<tr>
													<th>ลําดับ</th>
													<th width="250">ชื่อ</th>
													<th  >ตําแหน่ง</th>
													<th>เพิ่มเติม</th>
												</tr>
												<c:forEach var="old_acc" items="${ old_acc }"
													varStatus="loop">
													<tr>
														<td>${ loop.index+1 }</td>
														<td>${ old_acc.getPersonnel_prefix() }${ old_acc.getPersonnel_firstName() }
															${ old_acc.getPersonnel_lastName() }</td>
														<td>${ old_acc.getPersonnel_position() } ${ old_acc.getPersonnel_tier() }</td>
														<td><button formaction="do_removeAssign?personnel_id=${ old_acc.getPersonnel_id() }" class="btn-xs btn-hot btn">ลบ</button></td>
													</tr>
												</c:forEach>
											</table>
										</div>

									</fieldset>
								</div>
							</div>
						</div>
					</form>
					
						
					<form action="do_addAssignType" name="assign"
						class="form-horizontal" method="post" onSubmit="">
						<input type="hidden" name="check" value="6">
						<div class="row">
							<div class="col-sm-6">
								<!---------------------------------------------PERSONNEL DATA--------------------------------------->
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> กําหนดหน้าที่การเซ็นเอกสาร ผู้จัดซื้อจัดจ้าง
										</legend>
										<div class="form-group">
											<label class="col-sm-3 control-label">รายชื่อ : </label>
											<div class="col-sm-6">
												<select class="form-control" name="personnel_id" id="personnel_id">
													<option value="-">-</option>
													<c:forEach items="${ all }" var="all">
														<option value="${ all.getPersonnel_id() }">${all.getPersonnel_prefix() }
															${ all.getPersonnel_firstName() } ${ all.getPersonnel_lastName() }
															ตําแหน่ง ${ all.getPosition().getPosition_name() }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-3">
												<button class="btn btn-fresh">กําหนดหน้าที่</button>
											</div>
										</div>
									</fieldset>
								</div>
								<!------------------------------------------Start History Secretary--------------------------------------->
							</div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i>
											รายการหน้าที่การเซ็นผู้จัดซื้อจัดจ้าง
										</legend>
										<div class="form-group">
											<table>
												<tr>
													<th>ลําดับ</th>
													<th width="250">ชื่อ</th>
													<th  >ตําแหน่ง</th>
													 <th>เพิ่มเติม</th>
												</tr>
												<c:forEach var="old_purchase" items="${ old_purchase }"
													varStatus="loop">
													<tr>
														<td>${ loop.index+1 }</td>
														<td>${ old_purchase.getPersonnel_prefix() }${ old_purchase.getPersonnel_firstName() }
															${ old_purchase.getPersonnel_lastName() }</td>
														<td>${ old_purchase.getPersonnel_position() } ${ old_purchase.getPersonnel_tier() }</td>
														<td><button formaction="do_removeAssign?personnel_id=${ old_purchase.getPersonnel_id() }" class="btn-xs btn-hot btn">ลบ</button></td>
													</tr>
												</c:forEach>
											</table>
										</div>

									</fieldset>
								</div>
							</div>
						</div>
					</form>
					<!-- start -->
					<!--  end -->
				</div>
				<!-- **********End Panel Body***********-->
				<div class="panel-footer" style="height: 65px">
					<div class="form-group">
						<div align="right" style="margin-right: 15px">
							<button type="reset" class="btn btn-hot text-uppercase"
								onclick="history.back()">
								<i class="fa fa-times"></i> ย้อนกลับ
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 	End Row -->
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

		<script type="text/javascript"
		src="/dist/js/custom/js_recorddurable.js"></script>

	<c:if test="${message != null }">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
	<c:remove var="item_search" scope="session" />
</body>




</html> --%>