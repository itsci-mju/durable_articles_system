<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/itemFormCheck.js"></script>
<meta charset="UTF-8">
<title>แก้ไขรายการวัสดุ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="do_updateItem" name="itemFrm" class="form-horizontal"
				method="post" onSubmit="return itemFrmCheck(itemFrm)">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">แก้ไขรายการวัสดุ</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3"></div>
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<fieldset>
										<legend style="font-size: 1em">
											<i class="fa fa-folder-o"></i> ข้อมูลวัสดุ
										</legend>
										<div class="form-group">
											<label class="col-sm-4 control-label">หมวดวัสดุ : </label>
											<div class="col-sm-5">
												<select id="item_category" name="item_category"
													class="form-control">
													<option value="วัสดุสํานักงาน"
														<c:if test="${ item.getItem_category() == 'วัสดุสํานักงาน' }">selected</c:if>>วัสดุสํานักงาน</option>
													<option value="วัสดุคอมพิวเตอร์"
														<c:if test="${ item.getItem_category() == 'วัสดุคอมพิวเตอร์' }">selected</c:if>>วัสดุคอมพิวเตอร์</option>
													<option value="วัสดุงานบ้านงานครัว"
														<c:if test="${ item.getItem_category() == 'วัสดุงานบ้านงานครัว' }">selected</c:if>>วัสดุงานบ้านงานครัว</option>
													<option value="วัสดุไฟฟ้า"
														<c:if test="${ item.getItem_category() == 'วัสดุไฟฟ้า' }">selected</c:if>>วัสดุไฟฟ้า</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">ชื่อวัสดุ : </label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="item_name"
													name="item_name" value="${ item.getItem_name() }">
												<input type="hidden" name="item_id"
													value="${ item.getItem_id() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">ราคากลาง :</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="item_price"
													name="item_price" value="${ item.getItem_price() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">หน่วยนับ :</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="item_unit"
													name="item_unit" value="${ item.getItem_unit() }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">หมายเหตุ :</label>
											<div class="col-sm-5">
												<textarea class="form-control" id="item_note"
													name="item_note">${ item.getItem_note() }</textarea>
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
								<button type="submit" class="btn btn-sky text-uppercase">
									<i class="fa fa-floppy-o"></i> บันทึก
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
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
</body>

</html>