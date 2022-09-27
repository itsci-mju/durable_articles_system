<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta name="viewport" content="width=device-width">
<%
	String rm = (String) request.getAttribute("rm");
%>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
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
<meta charset="UTF-8">
<title>รายละเอียดสินทรัพย์ ประจําปีงบประมาณ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<input type="hidden" value="${roomBeanAll}" id="jsonRoom">
	<div class="row" style="margin-top: 80px">
		<div class="container-fluid">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-floppy-o"></i> รายละเอียดสินทรัพย์
							ประจําปีงบประมาณ
						</h3>
					</div>
				</div>
				<div class="panel-body">
					<form action="do_getDurableControllByYear" method="post"
						class="form-inline" name="searchDurableControll">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<c:choose>
									<c:when
										test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
										<label> &nbsp;&nbsp;&nbsp; สาขาวิชา : </label>
										<select class="form-control" name="major_id" id="major_id"
											style="margin-bottom: 15px; margin-top: 15px; width: 200px;"
											onchange="myFunction()">
											<c:forEach var="major" items="${listMajor}">
												<option value="${major.getID_Major()}"
													${major.getID_Major() == selectedDept ? 'selected="selected"' : ''}
													<c:if test="${ mid == major.getID_Major() }">selected</c:if>>${major.getMajor_Name()}</option>
											</c:forEach>
										</select>
									</c:when>
								</c:choose>
								<label>&nbsp;&nbsp;&nbsp; ปี : </label> <input
									class="form-control" type="text" id="year" name="year"
									value="${ year }">
								<!-- check_search_durable_control(searchDurableControll) -->
								<button type="submit" class="btn btn-sky text-uppercase"
									onClick="">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>

						<div class="table-responsive">
							<table style="margin-top: 10px;">
								<tr>
									<th style="text-align:center">ประเภท</th>
									<th style="text-align:center">รายการ</th>
									<th style="text-align:center">รายละเอียดสินทรัพย์</th>
									<th style="text-align:center">Serial Number</th>
									<th style="text-align:center">รหัสสินทรัพย์</th>
									<th style="text-align:center">จํานวน</th>
									<th style="text-align:center">ราคา/หน่วย</th>
									<th style="text-align:center">มูลค่าทุน(สะสม)ของสินทรัพย์เมื่อวันที่ได้มา</th>
									<th style="text-align:center">อายุการใช้งาน (ปี)</th>
									<th style="text-align:center">ว/ด/ป ที่ได้มา</th>
									<th style="text-align:center">หน่วยงาน</th>
									<th style="text-align:center">เลขที่ฎีกา</th>
									<th style="text-align:center">แหล่งเงิน</th>
									<th style="text-align:center">ผู้ขาย</th>
									<th style="text-align:center">ทะเบียนคุมทรัพย์สิน</th>
								</tr>
								<c:forEach items="${ durableControll }" var="durableControll"
									varStatus="loop">
									<tr>
										<td style="text-align:center">${ durableControll.getDurable_type() }</td>
										<td style="text-align:center">${ durableControll.getDurable_name() }</td>
										<td style="text-align:center">${ durableControll.getDurable_model() }</td>
										<td style="text-align:center">${ durableControll.getDurable_serial_number() }</td>
										<td style="text-align:center">${ durableControll.getDurable_code() }</td>
										<td style="text-align:center">${ durableControll.getDurable_number() }</td>
										<td style="text-align:center">${ durableControll.getDurable_price() }</td>
										<td style="text-align:center">${ durableControll.getDurable_price() }</td>
										<td style="text-align:center">${ durableControll.getDurable_life_time() }</td>
										<td style="text-align:center">${ durableControll.getDurable_entrancedate() }</td>
										<td style="text-align:center">${ durableControll.getMajor().getMajor_Name() }</td>
										<td style="text-align:center">${ durableControll.getDurable_petition_number() }</td>
										<td style="text-align:center">${ durableControll.getDurable_money_type() }</td>
										<td style="text-align:center">${ durableControll.getCompany().getCompanyname() }</td>
										<td style="text-align: center;"><c:if
												test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
												<c:if
													test="${ durableControll.getMajor().getID_Major() ==  sessionScope.staffSession.major.ID_Major}">
													<button type="submit"
														class="btn <c:if test="${ durableControll.getDurable_life_time() !=0 }">btn-fresh</c:if><c:if test="${ durableControll.getDurable_life_time() == 0 }">btn-sunny</c:if> text-capitalize btn-xs"
														formaction="do_loadDurableControllDetail?did=${ durableControll.getDurable_code() }">
														<c:if
															test="${ durableControll.getDurable_life_time() !=0 }">ทะเบียนคุมทรัพย์สิน</c:if>
														<c:if
															test="${ durableControll.getDurable_life_time() == 0 }">แก้ไขข้อมูล</c:if>
													</button>

												</c:if>
												<c:if
													test="${ durableControll.getMajor().getID_Major() !=  sessionScope.staffSession.major.ID_Major}">
													<c:if
														test="${ durableControll.getDurable_life_time() == 0 }">
														<p style="color: red">กรุณาแจ้งเจ้าหน้าที่สาขาให้แก้ไขข้อมูลให้ถูกต้อง</p>
													</c:if>
													<c:if
														test="${ durableControll.getDurable_life_time() !=0 }">
														<button type="submit"
															class="btn btn-fresh  text-capitalize btn-xs"
															formaction="do_loadDurableControllDetail?did=${ durableControll.getDurable_code() }">ทะเบียนคุมทรัพย์สิน</button>
													</c:if>
												</c:if>
												<button type="submit" class="btn btn-danger btn-xs"
													formaction="do_loadAddMaintenance?durable_code=${ durableControll.getDurable_code() }">เพิ่มประวัติการซ่อมบํารุง</button>
											</c:if> <c:if
												test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่สาขา'}">
												<button type="submit"
													class="btn <c:if test="${ durableControll.getDurable_life_time() !=0 }">btn-fresh</c:if><c:if test="${ durableControll.getDurable_life_time() == 0 }">btn-sunny</c:if> text-capitalize btn-xs"
													formaction="do_loadDurableControllDetail?did=${ durableControll.getDurable_code() }">
													<c:if
														test="${ durableControll.getDurable_life_time() !=0 }">ทะเบียนคุมทรัพย์สิน</c:if>
													<c:if
														test="${ durableControll.getDurable_life_time() == 0 }">แก้ไขข้อมูล</c:if>
												</button>

											</c:if></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
</body>
<c:if test="${not empty sessionScope.messages}">
	<script type="text/javascript">
		var msg = '${messages}';
		alert(msg);
	</script>
	<c:remove var="messages" scope="session" />
</c:if>
<c:if test="${message != null }">
	<script type="text/javascript">
		var msg = '${message}';
		alert(msg);
	</script>
	<c:remove var="message" scope="session" />
	<c:remove var="value" scope="session" />
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
</c:if>
</html>