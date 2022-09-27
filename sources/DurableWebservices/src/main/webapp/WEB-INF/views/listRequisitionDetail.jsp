<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, java.text.*, ac.th.itsci.durable.controller.*, ac.th.itsci.durable.entity.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	RequisitionManager rqm = new RequisitionManager();
List<RequestOrderItemList> roil = (List<RequestOrderItemList>) request.getAttribute("year_count");
String DATE_FORMAT = "dd MMMM yyyy";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
%>

<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>บัญชีวัสดุ</title>
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
							<i class="fa fa-floppy-o"></i>บัญชีวัสดุ
						</h3>
					</div>
				</div>
				<div class="panel-body">
					<form action="do_searchItem" action="get" class="form-horizontal">
						
						<fieldset>
							<legend align="left" style="font-size: 1em">บัญชีวัสดุ </legend>
						</fieldset>

						<div class="row">
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">
									<div class="form-group">
										<label class="col-sm-5 control-label">ประเภท : </label>
										<div class="col-sm-5">
											<input type="text" id="" name="" class="form-control"
												readonly="readonly" value="${ item.getItem_category() }">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-5 control-label">ขนาดหรือลักษณะ :
										</label>
										<div class="col-sm-5">
											<textarea readonly="readonly" class="form-control">${ item.getItem_note()}</textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-5 control-label">หน่วยนับ : </label>
										<div class="col-sm-5">
											<input type="text" value="${ item.getItem_unit()}"
												readonly="readonly" class="form-control">
										</div>
									</div>
								</div>
							</div>

							<!--  end first row -->
							<div class="col-sm-6">
								<div class="row" style="margin: 10px">

									<div class="form-group">
										<label class="col-sm-5 control-label">ชื่อหรือชนิดวัสดุ
											: </label>
										<div class="col-sm-5">
											<input type="text" readonly="readonly" class="form-control"
												value="${ item.getItem_name()}">
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<c:if test="${ year_count.size() != 0 }">
						<div class="table-responsive">
							<table style="margin-top: 10px">
								<tr>
									<th style="text-align: center" rowspan="2">วันเดือนปี</th>
									<th style="text-align: center" rowspan="2">รับจาก/จ่ายให้</th>
									<th style="text-align: center" rowspan="2">เลขที่เอกสาร</th>
									<th style="text-align: center" rowspan="2">ราคาต่อหน่วย
										(บาท)</th>
									<th style="text-align: center" colspan="2">รับ</th>
									<th style="text-align: center" colspan="2">จ่าย</th>
									<th style="text-align: center" colspan="2">คงเหลือ</th>
									<th style="text-align: center" rowspan="2">หมายเหตุ</th>
								</tr>
								<tr>
									<th style="text-align: center">จํานวน</th>
									<th style="text-align: center">ราคา</th>
									<th style="text-align: center">จํานวน</th>
									<th style="text-align: center">ราคา</th>
									<th style="text-align: center">จํานวน</th>
									<th style="text-align: center">ราคา</th>
								</tr>
								<% for(RequestOrderItemList year_count : roil){ %>
								<tr style="background-color: black; color: white">
									<td style="text-align: right;"></td>
									<td style="text-align: left">ปีงบประมาณ <%= year_count.getBudget_year()+543 %></td>
									<td style="text-align: center">ยอดยกมา</td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= year_count.getItem_price() %>" /></td>
									<td style="text-align: center"><%=  year_count.getAmount() %></td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= year_count.getTotal_price() %>" /></td>
									<td style="text-align: center"></td>
									<td style="text-align: center"></td>
									<td style="text-align: center"><%= year_count.getAmount() %></td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= year_count.getTotal_price() %>" /></td>
									<td style="text-align: center"></td>
								</tr>
								<% for(RequisitionItem rqi: year_count.getRequisitionItem()){ %>
								<tr>
									<td style="text-align: right"><%= sdf.format(rqi.getPk().getRequisition().getRequisition_date()) %></td>
									<td style="text-align: left"><%=rqi.getPk().getPersonnel().getPersonnel_prefix()+""+ 
										 rqi.getPk().getPersonnel().getPersonnel_firstName() +" "+ rqi.getPk().getPersonnel().getPersonnel_lastName()  %></td>
									<td style="text-align: center"><%= rqi.getPk().getRequisition().getRequisition_id()  %></td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= rqi.getPk().getRequestOrderItemList().getItem_price() %>" /></td>
									<td style="text-align: center"></td>
									<td style="text-align: center"></td>
									<td style="text-align: center"><%= rqi.getRequisition_total() %></td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= rqi.getTotal_price_purchase() %>" /></td>
									<td style="text-align: center"><%= rqi.getRequisition_total_balance() %></td>
									<td style="text-align: center"><fmt:formatNumber
											type="number" minFractionDigits="2" maxFractionDigits="2"
											value="<%= rqi.getTotal_price_balance() %>" /></td>
									<td style="text-align: center"><%= rqi.getRequisition_note() %></td>
								</tr>
								<% } %>
								<% } %>
							</table>
						</div>
					</c:if>
					<c:if test="${ year_count.size() == 0 }">
						<div align="center">
							<h4 style="color: red">ยังไม่มีการบันทึกการเบิกพัสดุ</h4>
						</div>
					</c:if>
				</div>
				<form method="post">
				<input type="hidden" name="y" value = "${ year }">
					<input type="hidden" name="doc_id_back" value="${ doc_id }">
					<div class="panel-footer" style="height: 65px" align="right">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-hot text-uppercase"
									formaction="do_backRequistionToRequisitionList">
									<i class="fa fa-close" aria-hidden="true"></i> ย้อนกลับ
								</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
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