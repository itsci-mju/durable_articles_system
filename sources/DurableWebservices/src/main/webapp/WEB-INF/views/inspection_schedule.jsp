<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>กําหนดวันตรวจสอบครุภัณฑ์</title>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<style>
.modal-header, h4, .close {
	background-color: #283e56;
	color: white !important;
	text-align: center;
	font-size: 20px;
}

.modal-footer {
	background-color: #283e56;
}
</style>
</head>
<body>
	<fmt:setLocale value="th_TH" scope="session" />
	<jsp:useBean id="date" class="java.util.Date" />
	<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />

	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<!-- 	Content -->
	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-tag"></i> รายการกำหนดวันตรวจสอบครุภัณฑ์
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<div class="panel-body">
					<!-- Search -->
					<fieldset>
						<legend align="right" style="font-size: 1em">
							<button type="button" class="btn btn-sky text-uppercase"
								onclick="dialog()">
								<i class="fa fa-plus"></i> เพิ่มข้อมูล
							</button>
						</legend>
					</fieldset>
					<!-- </form> -->
					<!-- Table -->
					<c:choose>
						<c:when test="${status == '1'}">
							<c:choose>
								<c:when test="${Inspection_schedule != null}">
									<div class="row" style="margin-top: 20px">
										<table>
											<thead>
												<tr>
													<th style="text-align: center;">รายการที่</th>
													<th style="text-align: center;">ปีตรวจสอบ</th>
													<th style="text-align: center;">วันที่เริ่มตรวจสอบ</th>
													<th style="text-align: center;">วันที่สิ้นสุดการตรวจสอบ</th>
													<th style="text-align: center;">หมายเหตุ</th>
												</tr>
											</thead>
											<%
												int i = 1;
											%>
											<c:forEach var="list_profiles" items="${Inspection_schedule}">
												<tbody>
													<tr>
														<td style="text-align: center;"><%=i%></td>
														<td style="text-align: center;">${list_profiles.years}</td>
														<td style="text-align: center;">${list_profiles.dateStart}</td>
														<td style="text-align: center;"><p id="dateEND">${list_profiles.dateEnd}</p></td>
														<c:choose>
															<c:when test="${list_profiles.years == currentYear}">
																<td style="text-align: center;"><button
																		class="btn btn-hot text-capitalize btn-xs"
																		value="${list_profiles.years},${list_profiles.dateEnd}"
																		id="btn_modal">ขยายเวลาการตรวจสอบ</button></td>
															</c:when>
															<c:otherwise>
																<td style="text-align: center;">-</td>
															</c:otherwise>
														</c:choose>
													</tr>
												</tbody>
												<%
													i++;
												%>
											</c:forEach>
										</table>
									</div>
									<!-- </form> -->
								</c:when>
								<c:otherwise>
									<p align="center">ไม่มีข้อมูลกำหนดเวลาตรวจสอบครุภัณฑ์</p>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
				</div>
				<c:remove var="Inspection_schedule" scope="session" />
				<c:remove var="status" scope="session" />
			</div>
		</div>
	</div>
	<c:remove var="listdu_byyear" />
	<div class="modal fade" id="myModalEdit" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 35px 50px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>ขยายเวลาการตรวจสอบครุภัณฑ์</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
					<form role="form" action="Edit_VerifyDate" method="POST">
						<div class="row">
							<div class="form-group">
								<label class="col-sm-5 control-label">ปีการตรวจสอบ ::</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="YEARS" name="YEARS"
										maxlength="4" class="form-control text-center" readonly><br>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label">วันที่สิ้นสุดการตรวจสอบ
									::</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="ENDDATE"
										name="ENDDATE"><br>
								</div>
							</div>
						</div>
						<br>
						<button type="submit" class="btn btn-success btn-block"
							id="btn_submit_edit">ขยายเวลาการตรวจสอบ</button>

					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 35px 50px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>กำหนดวันตรวจสอบครุภัณฑ์</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
					<form role="form" action="doAdd_Inspection_schedule" method="POST">
						<div class="row">
							<div class="form-group">
								<label class="col-sm-5 control-label">ปีการตรวจสอบ :: </label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="years" name="years"
										maxlength="4"><br>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label">วันที่เริ่มการตรวจสอบ
									::</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="startDate"
										name="startDate" value="${startDate}"><br>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label">วันที่สิ้นสุดการตรวจสอบ
									::</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="endDate"
										name="endDate" value="${endDate}"><br>
								</div>
							</div>
						</div>

						<br>
						<button type="submit" class="btn btn-success btn-block"
							id="btn_submit_inspection">บันทึกข้อมูล</button>

					</form>
				</div>
			</div>
		</div>
	</div>
</body>

<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

<script type="text/javascript" src="/dist/js/custom/js_inspection.js"></script>

<script>
	$(document).ready(function() {
		$("button[id*='btn_modal']").click(function() {
			var text = $(this).attr('value').split(',');
			$('#myModalEdit').modal('show');
			$('.modal-body #YEARS').val(text[0]);
			$('.modal-body #ENDDATE').val(text[1]);

		});
	});

	function dialog() {
		$("#myModal").modal();
	}

	$(function() {
		$.datepicker.setDefaults({
			onClose : function(date, inst) {
				$("#startDate").html(date);
			}
		});
		$("#startDate").datepicker();
	});

	$(function() {
		$.datepicker.setDefaults({
			onClose : function(date, inst) {
				$("#ENDDATE").html(date);
			}
		});
		$("#ENDDATE").datepicker();
	});

	$(function() {
		$.datepicker.setDefaults({
			onClose : function(date, inst) {
				$("#endDate").html(date);
			}
		});
		$("#endDate").datepicker();
	});
</script>

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
</html>