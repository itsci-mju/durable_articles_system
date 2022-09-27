<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<title>ค้นหา, แก้ไข และสร้างรหัสคิวอาร์โค้ด</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<!-- <script type="text/javascript" -->
<!-- 	src="//code.jquery.com/jquery-1.11.3.min.js"></script> -->
<!-- <script -->
<!-- 	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<style type="text/css">
.column {
	float: left;
	width: 100%;
	padding: 10px;
	height: auto;
}

.columnSubmit {
	float: left;
	width: 50%;
	padding: 10px;
	float: left;
}

.columnDetail {
	margin-left: 20%;
	margin-top: 10px;
	float: left;
	width: 30%;
	text-align: left;
	float: left;
}

.columnDetail2 {
	margin-top: 10px;
	float: left;
	width: 50%;
	text-align: left;
}

.rowSubmit:after {
	content: "";
	display: table;
	clear: both;
}

.modal-header, h4, .close {
	background-color: #283e56;
	color: white !important;
	text-align: center;
	font-size: 30px;
}

.modal {
	text-align: center;
}

@media screen and (min-width: 768px) {
	.modal:before {
		display: inline-block;
		vertical-align: middle;
		content: " ";
		height: 100%;
	}
}

.modal-dialog-scrollable {
	display: inline-block;
	text-align: left;
	vertical-align: middle;
}

.modal-body, .modal-content {
	width: auto;
	height: auto;
	margin-left: 10px;
}

.modal-footer {
	background-color: #283e56;
}

.modal-content {
	width: 1000px;
	vertical-align: middle;
}

.btn-sunny {
	color: #000000;
	background-color: #f4ad49;
	border-bottom: 2px solid #c38a3a;
}
</style>

<script>
	$(function() {
		$.datepicker.setDefaults({
			onClose : function(date, inst) {
				$("#endDate").html(date);
			}
		});
		$("#endDate").datepicker();
	});
</script>

<script type="text/javascript">
	function myFunction() {
		var major_id = document.getElementById("major_id").value;
		var jsonRoom = document.getElementById("jsonRoom").value;
		var replacJsonRoom = jsonRoom.replace(/'/g, '"');
		var values = null;
		var x = document.getElementById("room_id");
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

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<input type="hidden" value="${roomBeanAll}" id="jsonRoom">

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<!-- 		Panel -->
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-print"></i> แก้ไขข้อมูลและสร้างรหัสคิวอาร์โค้ด
						</h3>
					</div>
				</div>
				<!-- End Panel Heading -->
				<!-- Panel Body -->
				<div class="panel-body">
					<div class="row">

						<div class="column" align="right">
							<!-- 							<form action="serchDurable" class="form-inline" role="form" method="post"> -->
							<form action="listDurableAll" class="form-inline" role="form"
								method="get">
								<c:choose>
									<c:when
										test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
										<label> &nbsp;&nbsp;&nbsp; สาขา : </label>
										<select class="form-control" name="major_id" id="major_id"
											style="margin-bottom: 15px; margin-top: 15px; width: 200px;"
											onchange="myFunction()">
											<c:forEach var="major" items="${listMajor}">
												<option value="${major.getID_Major()}"
													${major.getID_Major() == selectedDept ? 'selected="selected"' : ''}>${major.getMajor_Name()}</option>
											</c:forEach>
										</select>
										<label> &nbsp;&nbsp;&nbsp; หมายเลขห้อง : </label>
										<select class="form-control" name="room_id" id="room_id"
											style="width: 200px;">
											<c:forEach var="room" items="${roomNumber}">
												<option value="${room}">${room}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<label>&nbsp;&nbsp;&nbsp; รหัสครุภัณฑ์ : </label>
										<input type="text" class="form-control" id="durable_code"
											name="durable_code" value="${ search_code }">
										<label>&nbsp;&nbsp;&nbsp; ชื่อครุภัณฑ์ : </label>
										<input type="text" class="form-control" id="durable_name"
											name="durable_name" value="${ durable_name }">
										<label> &nbsp;&nbsp;&nbsp; หมายเลขห้อง : </label>
										<select class="form-control" name="room_id" id="room_id"
											style="width: 200px;">
											<c:forEach var="rooms" items="${roomBeanAll}">
												<option value="${rooms}">${rooms}</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_search">
									<i class="fa fa-search"></i> ค้นหา
								</button>

							</form>
						</div>
						<c:choose>
							<c:when test="${listdurableall != null}">
								<div class="table-responsive">
									<form action="printQrCode" method="post">
										<div class="column">
											<button type="submit" class="btn btn-sky text-uppercase"
												id="btn_printqr" style="margin-bottom: 15px;">
												<i class="fa fa-qrcode"></i> สร้างรหัสคิวอาร์
											</button>
										</div>
										<br> <br> <br> <br>

										<table id="mytable" class="table table-bordered panel">
											<thead>
												<tr>
													<th style="text-align: center;" width="50">เลือก <input
														type="checkbox" id="checkAll"></th>
													<th style="text-align: center;">ลำดับที่</th>
													<th style="text-align: center;">รหัสครุภัณฑ์</th>
													<th style="text-align: center;" width="200">ชื่อครุภัณฑ์</th>
													<th style="text-align: center;">สถานะครุภัณฑ์</th>
													<th style="text-align: center;">ผู้ใช้</th>
													<th style="text-align: center;" width="100">ห้อง</th>
													<th style="text-align: center;">เพิ่มเติม</th>
													<!-- 												<th style="text-align: center;">ทะเบียนควบคุมทรัพย์สิน</th> -->
												</tr>
											</thead>
											<c:set var="i" value="1" />
											<c:forEach var="listdu" items="${listdurableall}">
												<thead>
													<tr>
														<td style="text-align: center;" width="50"><input
															type="checkbox" id="durablecode" name="durablecode"
															value="${listdu.durable_code}">
														<td style="text-align: center;">${i}</td>
														<td style="text-align: center;">${listdu.durable_code}</td>
														<td style="text-align: center;" width="200">${listdu.durable_name}</td>
														<td style="text-align: center;">${listdu.durable_statusnow}</td>
														<td style="text-align: center;">${listdu.responsible_person}</td>
														<td style="text-align: center;" width="100">${listdu.room.room_number}</td>
														<c:choose>
															<c:when test="${type == 0}">
																<td style="text-align: center;">
																	<button type="button"
																		value="${listdu.durable_code},${listdu.durable_number},${listdu.durable_name},${fn:replace(listdu.durable_brandname, ',', '/')},${listdu.durable_price},${listdu.durable_entrancedate},${fn:replace(listdu.durable_model, ',', '/')},${listdu.room.room_number},${listdu.responsible_person},${listdu.durable_statusnow},${listdu.note},${pageContext.request.contextPath}/file/durable_image/${listdu.durable_image}"
																		class="btn btn-fresh text-capitalize btn-xs"
																		id="btn_DurableDetail">รายละเอียดครุภัณฑ์</button>
																	<button type="submit"
																		value="${major_old},${room_id_old}"
																		class="btn btn-sunny text-capitalize btn-xs"
																		id="btn_editDurable"
																		formaction="do_loadEditDurableControll?did=${listdu.durable_code}">แก้ไขรายละเอียดครุภัณฑ์</button>
																	
																	<!--   formaction="do_loadEditDurableControll?did=${listdu.durable_code}" -->
																</td>
															</c:when>
															<c:when test="${type == 1}">
																<td style="text-align: center;">
																	<button type="button"
																		value="${listdu.durable_code},${listdu.durable_number},${listdu.durable_name},${fn:replace(listdu.durable_brandname, ',', '/')},${listdu.durable_price},${listdu.durable_entrancedate},${fn:replace(listdu.durable_model, ',', '/')},${listdu.room.room_number},${listdu.responsible_person},${listdu.durable_statusnow},${listdu.note},${pageContext.request.contextPath}/file/durable_image/${listdu.durable_image}"
																		class="btn btn-fresh text-capitalize btn-xs"
																		id="btn_DurableDetail">รายละเอียดครุภัณฑ์</button>
																</td>
															</c:when>
														</c:choose>
														<%-- 													<td style="text-align: center;"><button type="submit" class="btn btn-sunny text-capitalize btn-xs" formaction="do_loadDurableControllDetail?did=${ listdu.durable_code }">ทะเบียนคุมทรัพย์สิน</button></td> --%>
													</tr>
												</thead>
												<c:set var="i" value="${i+1}" />
											</c:forEach>
										</table>
									</form>
								</div>
							</c:when>
							<c:when test="${dataNotfound == 0}">
								<p align="center">-----------------------------------------------------------------------------------------------------------</p>
								<p align="center">ไม่พบข้อมูล</p>
							</c:when>
						</c:choose>
					</div>
				</div>
				<!-- 				End Panel Body -->
			</div>
			<!-- 			End Panel -->
		</div>
		<c:remove var="verify_durableprofiles" scope="session" />
		<c:remove var="status" scope="session" />
	</div>

	<!-- ****************************** dialog Detail durable ****************************** -->
	<div class="modal fade" id="myModalDetail" role="dialog">
		<div class="modal-dialog-scrollable">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>รายละเอียดครุภัณฑ์</h4>
				</div>
				<div class="modal-body">
					<form role="form" action="do_UpdateDurableDetail" method="POST">
						<div class="row">
							<div class="col-sm-6" style="text-align: center;">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> <b>ข้อมูลครุภัณฑ์</b>
										</legend>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>รหัสครุภัณฑ์ :: </label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1"
														id="durablecodeDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>ชื่อครุครุภัณฑ์ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1"
														id="namedurableDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>จำนวนครุภัณฑ์ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="numdurableDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>ยี่ห้อ :: </label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1"
														id="branddurableDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>ราคาต่อหน่วย :: </label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1"
														id="pricedurableDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>วันที่ได้รับ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="datepickerDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>รายละเอียด ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="detailDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
							<div class="col-sm-6" style="text-align: center;">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i> <b>สถานะ</b>
										</legend>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>ห้องที่ใช้ครุภัณฑ์ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="room_IdDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>ผู้ใช้ครุภัณฑ์ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="owner_idDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>สถานะครุภัณฑ์ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="statusDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>หมายเหตุ ::</label>
												</div>
												<div class="columnDetail2">
													<label for="exampleFormControlInput1" id="noteDetail"
														style="text-align: left; color: #000080;"></label>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="rowSubmit">
												<div class="columnDetail">
													<label>รูปภาพครุภัณฑ์ ::</label>
												</div>

												<div class="columnDetail2" style="width: 100%">
													<img id="durableImage" class="popup_image" width="100%">
												</div>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
						<!-- 						<div class="rowSubmit">
							<div class="columnSubmit2" style="text-align: center;">
								<button type="button" class="btn btn-success btn-block"
									style="width: 20%;" data-dismiss="modal">ตกลง</button>
							</div>
							<div class="columnSubmit" style="text-align: left;">
								<button type="submit" class="btn btn-success btn-block"
									style="width: 20%;" id="btn_submit_edit">บันทึก</button>
							</div>
						</div> -->
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- ************************************************************ -->

	<div id="myModal" class="modal">
		<span class="close">&times;</span>
		<div class="modal-content">
			<img class="" id="img01">
		</div>
	</div>

	<script>
		// Get the modal
		var modal = document.getElementById("myModal");

		// Get the image and insert it inside the modal - use its "alt" text as a caption
		var img = document.getElementById("durableImage");
		var modalImg = document.getElementById("img01");
		var captionText = document.getElementById("caption");
		img.onclick = function() {
			modal.style.display = "block";
			modalImg.src = this.src;
			captionText.innerHTML = this.alt;
		}

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}
	</script>

</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/dist/js/custom/js_checkall.js"></script>
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