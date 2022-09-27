<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- ****************************** dialog edite durable ****************************** -->
	<div class="modal fade" id="myModalEdit" role="dialog">
		<div class="modal-dialog-scrollable">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>แก้ไขรายละเอียดครุภัณฑ์</h4>
				</div>
				<div class="modal-body">
					<form role="form" action="do_UpdateDurableDetail" method="POST">
						<div class="row">
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> ข้อมูลครุภัณฑ์
										</legend>
										<div class="form-g
										roup">
											<label class="col-sm-5">รหัสครุภัณฑ์ :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="durablecode"
													name="durablecode" readonly>
											</div>

										</div>
										<br> <br>
										<div class="form-group">
											<label class="col-sm-5 control-label"
												style="margin-top: 5px;">จำนวนครุภัณฑ์ :: :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="numdurable"
													style="margin-top: 5px;" name="numdurable">
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ชื่อครุภัณฑ์ ::
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="namedurable"
													name="namedurable">
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ยี่ห้อ :: :: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="brand"
													name="brand">
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ราคาต่อหน่วย ::
												:: </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="pricedurable"
													name="pricedurable">
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ได้รับ ::</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="datepicker"
													name="datepicker">
											</div>
											<br> <br>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">รายละเอียด :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" rows="3" id="detail"
													name="detail"> - </textarea>
											</div>
											<br> <br>
										</div>

									</fieldset>
								</div>
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
												<select class="form-control" name="room_Id" id="room_Id">
													<c:forEach var="rooms" items="${roomSelectEdit}">
														<option value="${rooms}">${rooms}</option>
													</c:forEach>
												</select>
											</div>
											<br> <br>
										</div>

										<div class="form-group">
											<label class="col-sm-5 control-label">ผู้ใช้ครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" id="owner_id" name="owner_id">
													<c:forEach var="owners" items="${ownerBeanAll}">
														<option value="${owners}">${owners}</option>
													</c:forEach>
												</select>
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">สถานะครุภัณฑ์
												:: </label>
											<div class="col-sm-5">
												<select class="form-control" id="status" name="status">
													<option value="ดี">ดี</option>
													<option value="ชำรุด">ชำรุด</option>
													<option value="รอซ่อม">รอซ่อม</option>
													<option value="แทงจำหน่าย">แทงจำหน่าย</option>
												</select>
											</div>
											<br> <br>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หมายเหตุ :: </label>
											<div class="col-sm-5">
												<textarea class="form-control" id="note" name="note"> </textarea>
											</div>
											<br> <br>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
						<br> <input type="hidden" id="roomSelect" name="roomSelect"
							readonly="readonly"> <input type="hidden"
							id="majorSelect" name="majorSelect" readonly="readonly">
						<div class="rowSubmit">
							<div class="columnSubmit" style="text-align: right;">
								<button type="button" class="btn btn-danger" style="width: 20%;"
									data-dismiss="modal">ยกเลิก</button>
							</div>
							<div class="columnSubmit" style="text-align: left;">
								<button type="submit" class="btn btn-success btn-block"
									style="width: 20%;" id="btn_submit_edit">บันทึก</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- ************************************************************ -->
</body>
</html>