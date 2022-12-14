<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta name="viewport" content="width=device-width">
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include
	page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<style type="text/css">
@media screen and (min-width: 768px) {
	.modal-dialog {
		width: 700px; /* New width for default modal */
	}
	.modal-sm {
		width: 350px; /* New width for small modal */
	}
}

@media screen and (min-width: 992px) {
	.modal-lg {
		width: 950px; /* New width for large modal */
	}
}

.modal-header, h4, .close {
	background-color: #FF0000;
	color: white !important;
	text-align: center;
	font-size: 30px;
}

.modal-body, .modal-content {
	width: 100%;
	height: 500px;
	text-align: center;
}

.btn-fresh {
	color: #000000;
	background-color: #51bf87;
	border-bottom: 2px solid #41996c;
}

* {
	box-sizing: border-box;
}

.column {
	float: left;
	width: 40%;
	text-align: right;
	padding: 10px;
	height: 40px;
}

.column02 {
	float: left;
	width: 60%;
	padding: 10px;
	text-align: center;
	height: 40px;
	padding: 10px;
}

.column03 {
	float: left;
	width: 100%;
	padding: 10px;
	text-align: center;
	height: 40px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}
}
</style>

</head>



<script type="text/javascript">
	function myFunction() {
		var major_id = document.getElementById("major_id").value;
		var jsonRoom = document.getElementById("jsonRoom").value;
		var replacJsonRoom = jsonRoom.replace(/'/g, '"');
		var values = null;
		var x = document.getElementById("room_ID");
		var length = x.options.length;

		obj = JSON.parse(replacJsonRoom);
		values = obj.major[major_id];

		if (length > 0) {
			for (i = length - 1; i >= 0; i--) {
				x.options[i] = null;
			}
		}

		if (major_id === "99") {
			var option = document.createElement("option");
			option.value = "-";
			option.text = "-";
			x.appendChild(option);
		} else {
			if (values.length > 0) {
				var option = document.createElement("option");
				option.value = "-";
				option.text = "-";
				x.appendChild(option);
				for (var i = 0; i < values.length; i++) {
					var option = document.createElement("option");
					option.value = values[i];
					option.text = values[i];
					x.appendChild(option);
				}
			} else {
				var option = document.createElement("option");
				option.value = "-";
				option.text = "-";
				x.appendChild(option);
			}
		}
	}
</script>
<body>
	<jsp:include
		page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<input type="hidden" value="${roomBeanAll}" id="jsonRoom">

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-list-alt"></i> ?????????????????????????????????????????????????????????????????????????????????????????????
						</h3>
					</div>
				</div>
				<!-- 				End Panel Heading -->
				<!-- 				Panel Body -->
				<div class="panel-body">
					<!-- 				Table -->
					<form action="listDurableProfileByYear" class="form-inline"
						role="form" method="post">
						<fieldset>
							<legend align="right" style="font-size: 1em">

								<c:if
									test="${sessionScope.staffSession.staff_status == '?????????????????????????????????????????????????????????'}">
									<label>???????????? : </label>
									<select class="form-control" name="major_id" id="major_id"
										style="width: 200px;" onchange="myFunction()">
										<c:forEach var="major" items="${listMajor}">
											<option value="${major.ID_Major}"
												${major.ID_Major == selectedDept ? 'selected="selected"' : ''}>${major.major_Name}</option>
										</c:forEach>
									</select>
								</c:if>

								<label> &nbsp;&nbsp;&nbsp; ???????????? : </label> <select
									class="form-control" style="width: 200px;" name="room_ID"
									id="room_ID">
									<c:forEach var="room" items="${roomAll}">
										<option value="${room}">${room}</option>
									</c:forEach>

								</select> <label> &nbsp;&nbsp;&nbsp; ??????????????? : </label> <select
									class="form-control" id="statusSelect" name="statusSelect"
									style="width: 125px;">
									<option value="-" ${statusSelectOld == '-' ? 'selected' : ''}>-</option>
									<option value="??????" ${statusSelectOld == '??????' ? 'selected' : ''}>??????</option>
									<option value="???????????????"
										${statusSelectOld == '???????????????' ? 'selected' : ''}>???????????????</option>
									<option value="??????????????????"
										${statusSelectOld == '??????????????????' ? 'selected' : ''}>??????????????????</option>
									<option value="??????????????????????????????"
										${statusSelectOld == '??????????????????????????????' ? 'selected' : ''}>??????????????????????????????</option>
								</select> <label> &nbsp;&nbsp;&nbsp; ?????????????????????????????? : </label> <input
									type="text" class="form-control" id="year" name="year"
									style="width: 100px;" value="${year}" maxlength="4">
								<button type="submit" class="btn btn-default" id="yearsearch">
									<i class="fa fa-search"></i> ???????????????
								</button>
							</legend>
						</fieldset>
					</form>
					<c:choose>
						<c:when test="${status == '1'}">
							<c:choose>
								<c:when test="${durableprofiles != null}">
									<div class="table-responsive">
										<form action="ExportFileExcelServlet" class="form-inline"
											role="form" method="POST">
											<div align="right" style="margin-bottom: 5px">
												<button type="submit"
													class="btn btn-sky text-uppercase btn-sm">
													<i class="fa fa-file-excel-o"></i> ????????????????????????????????????????????? .xlsx
												</button>
											</div>
										</form>
										<table>
											<thead>
												<tr>
													<th style="text-align: center;">????????????????????????????????????</th>
													<th style="text-align: center;">????????????????????????????????????</th>
													<th style="text-align: center;">???????????????????????????????????????</th>
													<th style="text-align: center;">????????????????????????????????????</th>
													<th style="text-align: center;">??????????????????????????????</th>
													<th style="text-align: center;">??????????????????????????????</th>
													<th style="text-align: center;">??????????????????????????????</th>
													<th style="text-align: center;">????????????</th>
													<th style="text-align: center;">????????????????????????</th>
													<th style="text-align: center;">?????????????????????????????????????????????????????????</th>
												</tr>
											</thead>
											<c:forEach var="listdu" items="${durableprofiles}">
												<tbody>
													<tr>
														<td style="text-align: center;">${listdu.pk.durable.durable_code}</td>
														<td style="text-align: center;">${listdu.pk.verify.years}</td>
														<td style="text-align: center;">${listdu.save_date}</td>
														<td style="text-align: center;">${listdu.pk.durable.durable_name}</td>
														<td style="text-align: center;">${listdu.pk.durable.room.room_number}</td>
														<td style="text-align: center;">${listdu.pk.durable.responsible_person}</td>
														<td style="text-align: center;">${listdu.pk.staff.staff_name}</td>
														<td style="text-align: center;">${listdu.durable_status}</td>
														<td style="text-align: center;">${listdu.note}</td>

														<c:choose>

															<c:when test="${disbleButton == 3}">
																<td style="text-align: center;">
																	<button type="button"
																		class="btn btn-fresh text-capitalize btn-xs"
																		id="btn_modal" style="color: #000000;"
																		value="${listdu.pk.durable.durable_code},${listdu.pk.verify.years},${listdu.save_date},${listdu.pk.staff.staff_name},${listdu.pk.staff.id_staff},${listdu.durable_status},${listdu.note}">???????????????????????????????????????????????????????????????</button>
																</td>
															</c:when>
															<c:when test="${disbleButton == 1}">
																<td style="text-align: center;">
																	<button class="btn btn-hot text-capitalize btn-xs">??????????????????????????????????????????????????????????????????</button>
																</td>
															</c:when>
															<c:when test="${disbleButton == 2}">
																<td style="text-align: center;">
																	<button class="btn btn-hot text-capitalize btn-xs"
																		value="${textNotification},${textNotificationsHeader}"
																		id="btn_Notification">???????????????????????????????????????????????????????????????????????????</button>
																</td>
															</c:when>
															<c:when test="${disbleButton == 0}">
																<td style="text-align: center;"><button
																		class="btn btn-hot text-capitalize btn-xs"
																		disabled="disabled">???????????????????????????????????????????????????????????????????????????</button></td>
															</c:when>
														</c:choose>
														<!-- ---------------------- -->
													</tr>
												</tbody>
											</c:forEach>
										</table>
									</div>
								</c:when>
								<c:when test="${ no_year != null }">
									<p align="center">???????????????????????????????????????</p>
								</c:when>
								<c:otherwise>
									<p align="center">?????????????????????????????????</p>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
					<!-- 				End Table -->
					<c:remove var="durableprofiles" scope="session" />
					<c:remove var="status" scope="session" />
				</div>
				<!-- 				End Panel Body -->
				<!-- 			End Panel -->
			</div>
		</div>
	</div>

	<!-------- show Notifications -------->
	<div class="modal fade" id="showNotifications" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3>???????????????????????????</h3>
				</div>
				<div class="modal-body" style="height: auto;">
					<label for="exampleFormControlInput1" id="textNotificationsHeader"
						style="text-align: center; font-size: 20px"></label> <label
						for="exampleFormControlInput1" id="textNotifications"
						style="text-align: center; font-size: 20px"></label>
				</div>
			</div>
		</div>
	</div>

	<!-------- Edit verify -------->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="background-color: #283e56;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 style="background-color: #283e56;">???????????????????????????????????????????????????????????????</h4>
				</div>
				<div class="modal-body">
					<form action="update_VerifyDurable" method="POST">
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">????????????????????????????????????</label>
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
									<label for="exampleFormControlInput1">????????????????????????????????????</label>
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
									<label for="exampleFormControlInput1">???????????????????????????????????????</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="verifyDate"
										name="verifyDate" style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">??????????????????????????????</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="staff" name="staff"
										style="width: 200px" readonly> <input type="hidden"
										class="form-control" id="id_staff" name="id_staff"
										style="width: 200px" readonly>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">???????????????????????????????????????</label>
								</div>
								<div class="column02">
									<select class="col-sm-4 form-control" id="statusDurable"
										name="statusDurable" style="width: 200px">
										<option value="??????"
											${listdu.durable_status == '??????' ? 'selected' : ''}>??????</option>
										<option value="???????????????"
											${listdu.durable_status == '???????????????' ? 'selected' : ''}>???????????????</option>
										<option value="??????????????????"
											${listdu.durable_status == '??????????????????' ? 'selected' : ''}>??????????????????</option>
										<option value="??????????????????????????????"
											${listdu.durable_status == '??????????????????????????????' ? 'selected' : ''}>??????????????????????????????</option>
									</select>

								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="column">
									<label for="exampleFormControlInput1">????????????????????????</label>
								</div>
								<div class="column02">
									<input type="text" class="form-control" id="note" name="note"
										style="width: 200px;">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="column03">
								<input type="hidden" id="room_ID" name="room_ID" readonly>
								<input type="hidden" id="major_id" name="major_id" readonly>
								<input type="hidden" id="statusSelect" name="statusSelect"
									readonly> <input type="hidden" id="yearsearch"
									name="yearsearch" readonly>
								<button type="submit" class="btn btn-success btn-block">???????????????</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include
		page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<jsp:include
		page="/WEB-INF/views/modal-durabledeail.jsp"></jsp:include>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/custom/js_pagelistyear.js"></script>

</body>

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
<c:remove var="item_search" scope="session" />
</html>