<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page
	import="java.util.*, java.text.*, ac.th.itsci.durable.controller.*, ac.th.itsci.durable.entity.*"%>
<%
	String DATE_FORMAT = "dd MMMM yyyy";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
Date date = new Date(System.currentTimeMillis());
%>
<!DOCTYPE html>
<html>
<script
	src="${pageContext.request.contextPath}/dist/js/custom/requisitionScript.js"></script>
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
				<form action="do_addRequistion" method="POST" name="requisition"
					class="form-horizontal">
					<div class="panel-body">
						<fieldset class="form-inline">
							<legend align="right" style="font-size: 1em">
								<label> &nbsp;&nbsp;&nbsp; ปี : </label> <input type="text"
									class="form-control" name="year_search" id="year_search"
									value="${ year_search }">
								<button type="submit" formaction="do_SearchRequisition"
									class="btn btn-sky text-uppercase" onClick="return check_year(requisition)">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
						<!-- <fieldset>
							<legend align="left" style="font-size: 1em">รายการบัญชีวัสดุ
							</legend>
						</fieldset> -->
						<c:if test="${ empty over_year }">
							<c:choose>
								<c:when test="${ requisition_item.size() != 0 }">
									<%-- 								<c:choose>
									<c:when test="${ empty all_gone }">
										<c:if test="${ empty not_current_year }">
											<div class="row">
												<div class="col-sm-6">
													<div class="row" style="margin: 10px">
														<div class="form-group">
															<label class="col-sm-5 control-label">เลขที่เอกสาร
																: </label>
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
																<input type="text" id="datepicker"
																	name="requisition_date" class="form-control"
																	value="<%=sdf.format(date)%>">
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:when>

								</c:choose> --%>
									<fieldset class="form-inline">
										<legend style="font-size: 1em">
											<i class="fa fa-file-o"></i> รายการวัสดุที่เบิกได้ปี ${ year_search }
										</legend>
									</fieldset>
									<div class="table-responsive">
										<table style="margin-top: 10px">
											<tr>
												<th style="text-align: center">ชื่อวัสดุ</th>
												<th style="text-align: center">ราคากลาง</th>
												<th style="text-align: center">ราคาที่รับมา</th>
												<th style="text-align: center">หน่วย</th>
												<!-- <th style="text-align: center">จํานวนทั้งหมด</th> -->
												<th style="text-align: center">ยอดรับในปี ${year_search }</th>
												<th style="text-align: center">ยอดยกมาคงเหลือ</th>
												<th style="text-align: center">คงเหลือรวม</th>
												<!-- <th style="text-align: center">จํานวนที่เคยทําการเบิก</th> -->
												<th style="text-align: center">เพิ่มเติม</th>
											</tr>
											<c:forEach var="requisition_item"
												items="${ requisition_item }" varStatus="loop">
												<tr>

													<td style="text-align: center">${ requisition_item.getPk().getRequestOrderItemList().getId().getItem().getItem_name() }</td>
													<td style="text-align: center"><fmt:formatNumber
															type="number" minFractionDigits="2" maxFractionDigits="2"
															value="${ requisition_item.getPk().getRequestOrderItemList().getId().getItem().getItem_price() }" />
													<td style="text-align: center"><fmt:formatNumber
															value="${ requisition_item.getPk().getRequestOrderItemList().getItem_price() }"
															type="number" minFractionDigits="2" maxFractionDigits="2" /></td>
													<td style="text-align: center">${ requisition_item.getPk().getRequestOrderItemList().getId().getItem().getItem_unit()  }</td>
													<%-- <td style="text-align: center;"><font color="">${ requisition_item.getPk().getRequestOrderItemList().getAmount_received() }</font></td> --%>
													<%-- <td style="text-align: center">${ requisition_item.getRequisition_total() }</td> --%>
													<td style="text-align: center; <c:if test="${ get_amount_received.get(loop.index) == 0 }">color:red</c:if>"><c:if
															test="${ get_amount_received.size() != 0 }">${ get_amount_received.get(loop.index) }</c:if>
														<c:if test="${ get_amount_received.size() == 0 }">
															<font style="color: red">0</font>
														</c:if></td>
													<td style="text-align: center"><font
														style="<c:if test="${requisition_item.getRequisition_total_balance() == 0 }">color:red</c:if>">${requisition_item.getRequisition_total_balance() }</font></td>
													<td style="text-align: center"><font
														style="<c:if test="${requisition_item.getPk().getRequestOrderItemList().getAmount_balance() == 0 }">color:red</c:if>">${ requisition_item.getPk().getRequestOrderItemList().getAmount_balance() }</font></td>
													<td style="text-align: center"><button
															formaction="do_loadRequisitionDetail?itemID=${ requisition_item.getPk().getRequestOrderItemList().getId().getItem().getItem_id() }&year=${year_search}&item_price=${ requisition_item.getPk().getRequestOrderItemList().getItem_price() }"
															class="btn btn-fresh btn-xs">ดูรายละเอียด</button></td>
													<%-- <td><input type="text"
													class="form-control requisition_total"
													name="requisition_total" onKeyUp="caltotalRe()" disabled><label
													style="color: red; margin-top: 10px;"
													id="warning_text${ loop.index }"></label></td>
												<td style="text-align: center"><label
													for="total_price_l" class="total_price_l"></label> บาท<input
													type="hidden" name="total_price_purchase"
													class="total_price_purchase" value="" disabled></td>
												<td style="text-align: center"><textarea
														class="form-control re_note" name="re_note" disabled></textarea></td>
												<td style="text-align: center"><button
														class="btn-xs btn btn-fresh"
														formaction="do_loadListRequisitionDetail?item_id=${ requisition_item.getId().getItem().getItem_id() }">รายละเอียดการเบิก</button> --%>
													<%-- <c:choose>
											<c:when test="${ remain[loop.index] == 0 }"></c:when>
											<c:otherwise>
												<c:if
													test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
													<button class="btn btn-xs btn-sunny" type="submit"
														onclick="location.href = 'do_loadAddRequisitionPage?item_id=${ request_item.getId().getItem().getItem_id() }&doc_id=${doc.getDoc_id()}'">บันทึกข้อมูลการเบิก</button>
												</c:if>
											</c:otherwise>
										</c:choose> --%>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:when>

								<c:otherwise>
									<h4 align="center" style="color: red">************
										ไม่พบข้อมูลบัญชีวัสดุในปี ${ year_search } ************</h4>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${not empty over_year }">
							<h4 align="center" style="color: red">************
								ไม่พบข้อมูลบัญชีวัสดุในปี ${ year_search } ************</h4>
						</c:if>
					</div>
					<div class="panel-footer" style="height: 65px" align="right">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<c:if
									test="${ sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง' }">
									<c:if test="${ empty all_gone }">
										<c:if test="${ empty previus_year && empty over_year }">
										<button type="submit" class="btn btn-sky text-uppercase"
											onClick="" formaction="do_loadAddRequisition">
											<i class="fa fa-floppy-o"></i> บันทึกการเบิกพัสดุ
										</button>
										</c:if>
									</c:if>
								</c:if>
								<button type="submit" class="btn btn-hot text-uppercase"
									formaction="backLoadListDocument">
									<i class="fa fa-close" aria-hidden="true"></i> ย้อนกลับ
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
						document.getElementById("warning_text" + (i)).innerHTML = "จํานวนการเบิกต้องไม่เกินจํานวนคงเหลือ";
						alert('จํานวนการเบิกเกินจํานวนของวัสดุที่เหลืออยู่');
						amount[i].value = '';
						totals[i].innerHTML = '';
						total[i].value = '';
					} else {
						document.getElementById("warning_text" + (i)).innerHTML = "";
					}
				}
			}
		}

		function check() {
			var item_id
			var amount, total, re_note, totals, remain, ip, receive_amount;
			item_id = document.getElementsByClassName("item_id");
			re_note = document.getElementsByClassName("re_note");
			amount = document.getElementsByClassName("requisition_total");
			total = document.getElementsByClassName("total_price_purchase");
			totals = document.getElementsByClassName("total_price_l");
			remain = document.getElementsByClassName("remain");
			ip = document.getElementsByClassName("ip");
			receive_amount = document.getElementsByClassName("receive_amount");
			for (var i = 0; i < item_id.length; i++) {
				if (item_id[i].checked) {

					remain[i].disabled = false;
					amount[i].disabled = false;
					re_note[i].disabled = false;
					total[i].disabled = false;
					ip[i].disabled = false;
					receive_amount[i].disabled = false;
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
					receive_amount[i].disabled = true;
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