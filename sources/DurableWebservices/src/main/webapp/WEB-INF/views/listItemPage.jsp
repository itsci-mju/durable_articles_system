<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>รายการวัสดุ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 80px">
		<div class="container">
			<div class="panel panel-info">
				<!-- 			Panel Heading -->
				<div class="panel-heading">
					<div class="row">
						<h3 class="panel-title col-sm-6">
							<i class="fa fa-floppy-o"></i> ค้นหารายการวัสดุ
						</h3>
					</div>
				</div>
				<div class="panel-body">
					<form action="do_searchItem" action="post" class="form-inline">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<label>หมวดวัสดุ : </label> <select id="item_category_search"
									name="item_category_search" class="form-control">
									<option value="-"
										<c:if test="${ item_search.item_category == '-' }">selected</c:if>>-</option>
									<option value="วัสดุสํานักงาน"
										<c:if test="${ item_search.item_category == 'วัสดุสํานักงาน' }">selected</c:if>>วัสดุสํานักงาน</option>
									<option value="วัสดุคอมพิวเตอร์"
										<c:if test="${ item_search.item_category == 'วัสดุคอมพิวเตอร์' }">selected</c:if>>วัสดุคอมพิวเตอร์</option>
									<option value="วัสดุงานบ้านงานครัว"
										<c:if test="${ item_search.item_category == 'วัสดุงานบ้านงานครัว' }">selected</c:if>>วัสดุงานบ้านงานครัว</option>
									<option value="วัสดุไฟฟ้า"
										<c:if test="${ item_search.item_category == 'วัสดุไฟฟ้า' }">selected</c:if>>วัสดุไฟฟ้า</option>
								</select> <label>ชื่อวัสดุ : </label> <input class="form-control"
									type="text" id="item_name_search" name="item_name_search"
									value="${ item_search.item_name }">
								<button type="submit" class="btn btn-sky text-uppercase">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form>
					<c:choose>
						<c:when test="${ items.size() != 0 }">
							<table style="margin-top: 10px">
								<tr>
									<th style="text-align:center">ลําดับ</th>
									<th style="text-align:center">หมวดวัสดุ</th>
									<th style="text-align:center">ชื่อวัสดุ</th>
									<th style="text-align:center">ราคากลางวัสดุ</th>
									<th style="text-align:center">หน่วย</th>
									<th style="text-align:center">หมายเหตุ</th>
									<th style="text-align:center">แก้ไข</th>
								</tr>
								<c:forEach var="items" items="${ items }" varStatus="loop">
									<tr>
										<td style="text-align:center">${ loop.index+1 }</td>
										<td style="text-align:center">${ items.getItem_category() }</td>
										<td style="text-align:center">${ items.getItem_name() }</td>
										<td style="text-align:center"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ items.getItem_price() }" /></td>
										<td style="text-align:center">${ items.getItem_unit() }</td>
										<td style="text-align:center">${ items.getItem_note() }</td>
										<td style="text-align:center"><button class="btn btn-xs btn-sunny"
												onClick="location.href='do_getItemByID?item_id=${ items.getItem_id() }'"><i class="fa fa-wrench" aria-hidden="true"></i> แก้ไข</button></td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							<h3 align="center" style="color: red;">ไม่พบข้อมูลวัสดุ</h3>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<c:if
		test="${ sessionScope.messages != null && sessionScope.messages != ''  }">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
	<c:if test="${not empty remove_session_check }">
		<c:remove var="item_search" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
</body>
</html>