<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.text.*"%>
<%
	String DATE_FORMAT = "dd MMMM yyyy";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
Date date = new Date(System.currentTimeMillis());
%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>รายการบัญชีวัสดุ</title>
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
							<i class="fa fa-floppy-o"></i> รายการบัญชีวัสดุ
						</h3>
					</div>
				</div>
				<form action="do_addRequistion" method="POST"
					class="form-horizontal">
					<div class="panel-body">
						<input type="hidden" name="doc_id" value="${ doc.getDoc_id() }">
						<fieldset>
							<legend align="left" style="font-size: 1em">รายการบัญชีวัสดุ
							</legend>
						</fieldset>

						<c:if
							test="${ sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง' }">
							<c:if test="${ empty all_gone }">
								<div class="row">
									<div class="col-sm-6">
										<div class="row" style="margin: 10px">
											<div class="form-group">
												<label class="col-sm-5 control-label">เลขที่เอกสาร :
												</label>
												<div class="col-sm-5">
													<input type="text" id="re_id" name="re_id"
														class="form-control" value="">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-5 control-label">รายชื่อผู้เบิก
													: </label>
												<div class="col-sm-5">
													<select name="re_per" id="re_per" class="form-control">
														<option value="-">-</option>
														<c:forEach items="${ personnel }" var="personnel">
															<option value="${ personnel.getPersonnel_id() }">${ personnel.getPersonnel_prefix() }${ personnel.getPersonnel_firstName() }
																${ personnel.getPersonnel_lastName() }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									<!--  end first row -->
									<div class="col-sm-6">
										<div class="row" style="margin: 10px">

											<div class="form-group">
												<label class="col-sm-5 control-label">วันที่ : </label>
												<div class="col-sm-5">
													<input type="text" id="datepicker" name="requisition_date"
														class="form-control" value="<%=sdf.format(date)%>">
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</c:if>

						<table style="margin-top: 10px">
							<tr>
								<c:if test="${ sessionScope.staffSession.major.ID_Major == 999 }">
								<th style="text-align: center">เลือก</th>
								</c:if>
								<th style="text-align: center">ชื่อวัสดุ</th>
								<th style="text-align: center">ราคากลางวัสดุ</th>
								<th style="text-align: center">หน่วย</th>
								<th style="text-align: center">จํานวนคงเหลือ</th>
								<th style="text-align: center">จํานวนที่ต้องการเบิก</th>
								<th style="text-align: center">ราคา</th>
								<th style="text-align: center">หมายเหตุ</th>
								<th style="text-align: center">เพิ่มเติม</th>
							</tr>
							<c:forEach var="request_item" items="${ request_item }"
								varStatus="loop">
								<tr>
								<c:if test="${ sessionScope.staffSession.major.ID_Major == 999 }">
									<td style="text-align: center">​<input type="checkbox"
										class="item_id" name="item_id"
										value="${ request_item.getId().getItem().getItem_id() }"
										onClick="check()"
										<c:if test="${ remain[loop.index] == '0' }">disabled</c:if>></td>
								</c:if>
									<td style="text-align: center">${ request_item.getId().getItem().getItem_name() }</td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${ request_item.getId().getDocument().getRequisitionItemLists().get(loop.index).getItem_price() }" /><label
										style="display: none" for="price" class="price">${ request_item.getId().getDocument().getRequisitionItemLists().get(loop.index).getItem_price() }</label><input
										type="hidden" name="ip" class="ip"
										value="${ request_item.getId().getDocument().getRequisitionItemLists().get(loop.index).getItem_price() }"
										disabled></td>
									<td style="text-align: center">${ request_item.getId().getItem().getItem_unit() }</td>
									<td style="text-align: center">${ remain[loop.index] }<input
										type="hidden" value="${ remain[loop.index] }" name="remain"
										class="remain" disabled></td>
									<td><input type="text"
										class="form-control requisition_total"
										name="requisition_total" onKeyUp="caltotalRe()"></td>
									<td style="text-align: center"><label for="total_price_l"
										class="total_price_l"></label> บาท<input type="hidden"
										name="total_price_purchase" class="total_price_purchase"
										value="" disabled></td>
									<td style="text-align: center"><textarea
											class="form-control re_note" name="re_note"></textarea></td>
									<td style="text-align: center"><button
											class="btn-xs btn btn-fresh"
											formaction="do_loadListRequisitionDetail?item_id=${ request_item.getId().getItem().getItem_id() }&doc_id=${doc.getDoc_id()}">รายละเอียดการเบิก</button>
										<%-- <c:choose>
											<c:when test="${ remain[loop.index] == 0 }"></c:when>
											<c:otherwise>
												<c:if
													test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
													<button class="btn btn-xs btn-sunny" type="submit"
														onclick="location.href = 'do_loadAddRequisitionPage?item_id=${ request_item.getId().getItem().getItem_id() }&doc_id=${doc.getDoc_id()}'">บันทึกข้อมูลการเบิก</button>
												</c:if>
											</c:otherwise>
										</c:choose> --%></td>
								</tr>
							</c:forEach>
						</table>

					</div>
					<div class="panel-footer" style="height: 65px" align="right">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<c:if
									test="${ sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง' }">
									<c:if test="${ empty all_gone }">
										<button type="submit" class="btn btn-sky text-uppercase">
											<i class="fa fa-floppy-o"></i> บันทึก
										</button>
									</c:if>
								</c:if>
								<button type="reset" class="btn btn-hot text-uppercase"
									onClick="history.back(-1)">
									<i class="fa fa-backward" aria-hidden="true"></i> ย้อนกลับ
								</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		function caltotalRe() {
			var sum = 0;
			var sum_t = 0;
			var amount, price, total, totals, remain;
			amount = document.getElementsByClassName("requisition_total");
			price = document.getElementsByClassName("price");
			totals = document.getElementsByClassName("total_price_l");
			total = document.getElementsByClassName("total_price_purchase");
			remain = document.getElementsByClassName("remain");
			for (var i = 0; i < amount.length; i++) {
				if (amount[i] != null) {
					totals[i].innerHTML = amount[i].value
							* price[i].textContent;
					total[i].value = amount[i].value * price[i].textContent;
					if (parseInt(amount[i].value) > parseInt(remain[i].value)) {
						alert('จํานวนการเบิกเกินจํานวนของวัสดุที่เหลืออยู่');
						amount[i].value = '';
						totals[i].innerHTML = '';
						total[i].value = '';
					}
				}
			}
		}

		function check() {
			var item_id
			var amount, total, re_note, totals, remain, ip;
			item_id = document.getElementsByClassName("item_id");
			re_note = document.getElementsByClassName("re_note");
			amount = document.getElementsByClassName("requisition_total");
			total = document.getElementsByClassName("total_price_purchase");
			totals = document.getElementsByClassName("total_price_l");
			remain = document.getElementsByClassName("remain");
			ip = document.getElementsByClassName("ip");
			for (var i = 0; i < item_id.length; i++) {
				if (item_id[i].checked) {
					remain[i].disabled = false;
					amount[i].disabled = false;
					re_note[i].disabled = false;
					total[i].disabled = false;
					ip[i].disabled = false;
				} else {
					ip[i].disabled = true;
					amount[i].disabled = true;
					amount[i].value = '';
					re_note[i].disabled = true;
					re_note[i].value = '';
					total[i].disabled = true;
					total[i].value = '';
					totals[i].innerHTML = '';
					remain[i].disabled = true;
				}
			}
		}
	</script>
	<c:if test="${ sessionScope.messages != null }">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
	<c:if test="${ sessionScope.durable_name != null }">
		<c:remove var="durable_name" scope="session" />
	</c:if>
	<c:if test="${ sessionScope.search_code != null }">
		<c:remove var="search_code" scope="session" />
	</c:if>
</body>
</html>