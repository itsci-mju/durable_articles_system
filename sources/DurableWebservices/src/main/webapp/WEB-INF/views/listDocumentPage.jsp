<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>

<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>รายการเอกสาร</title>
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
							<i class="fa fa-floppy-o"></i> รายการเอกสาร
						</h3>
					</div>
				</div>
				<div class="panel-body">
					<form action="do_SearchDocument" method="post" class="form-inline">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<%-- 								<c:choose>
									<c:when
										test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
										<label> &nbsp;&nbsp;&nbsp; สาขา : </label>
										<select name="major" id="major" class="form-control">
											<option value="-">-</option>
											<c:forEach items="${ major }" var="major">
												<option value="${ major.getID_Major() }"
													<c:if test="${ majorselect == major.getID_Major() }">selected</c:if>>${ major.getMajor_Name() }</option>
											</c:forEach>
										</select>
									</c:when>
								</c:choose> --%>
								<label> &nbsp;&nbsp;&nbsp; รหัสเอกสาร : </label> <input
									type="text" class="form-control" name="doc_id" id="doc_id"
									value="${ searchKey }"> <label>&nbsp;&nbsp;&nbsp;
									สถานะเอกสาร : </label> <select id="doc_status" name="doc_status"
									class="form-control">
									<option value="-"
										<c:if test="${ doc_status == '-' }">selected</c:if>>-</option>
									<option value="รอการอนุมัติ"
										<c:if test="${ doc_status == 'รอการอนุมัติ' }">selected</c:if>>รอการอนุมัติ</option>
									<option value="จัดทําเอกสารขอซื้อ/จ้าง สําเร็จ"
										<c:if test="${ doc_status == 'จัดทําเอกสารขอซื้อ/จ้าง สําเร็จ' }">selected</c:if>>จัดทําเอกสารขอซื้อ/จ้าง
										สําเร็จ</option>
									<option value="จัดทําใบตรวจรับพัสดุ สําเร็จ"
										<c:if test="${ doc_status == 'จัดทําใบตรวจรับพัสดุ สําเร็จ' }">selected</c:if>>จัดทําใบตรวจรับพัสดุ
										สําเร็จ</option>
									<option value="จัดทําใบเบิกพัสดุ สําเร็จ"
										<c:if test="${ doc_status == 'จัดทําใบเบิกพัสดุ สําเร็จ' }">selected</c:if>>จัดทําใบเบิกพัสดุ
										สําเร็จ</option>
								</select>
								<button type="submit" class="btn btn-sky text-uppercase">
									<i class="fa fa-search"></i> ค้นหา
								</button>

							</legend>
						</fieldset>
					</form>
					<!-- <form action="do_getSearchItem" action="get" class="form-inline">
						<fieldset>
							<legend align="right" style="font-size: 1em">
								<label>ชื่อวัสดุ : </label> <input class="form-control"
									type="text" id="item_name" name="item_name">
								<button type="submit" class="btn btn-sky text-uppercase"
									id="btn_search">
									<i class="fa fa-search"></i> ค้นหา
								</button>
							</legend>
						</fieldset>
					</form> -->
					<div class="table-responsive">
						<c:if test="${ documents.size() != 0 }">

							<table style="margin-top: 10px;">
								<tr>
									<th style="text-align: center;" rowspan="2" width="100">​รหัสเอกสาร</th>
									<th style="text-align: center;" rowspan="2" width="120">วันที่จัดทํา</th>
									<th style="text-align: center" rowspan="2">จัดทําเอกสารโดย</th>
									<th style="text-align: center;" rowspan="2" width="100">สถานะ</th>
									<th style="text-align: center;" colspan="4">พิมพ์เอกสาร</th>
									<th style="text-align: center;" rowspan="2">จัดทําเอกสาร</th>
									<th style="text-align: center;" rowspan="2">เพิ่มเติม</th>
								</tr>
								<tr>
									<th>เอกสารเสนอซื้อ/จ้าง</th>
									<th>เอกสารขอซื้อ/จ้าง</th>
									<th width="80">ใบตรวจรับพัสดุ</th>
									<th>ใบเบิกพัสดุ</th>
								</tr>

								<c:forEach var="documents" items="${ documents }"
									varStatus="loop">
									<tr>
										<td style="text-align: center;">${ documents.getDoc_id() }</td>
										<td style="text-align: center;">${ date.get(loop.index) }</td>
										<td style="text-align: center;">${ documents.getDepart_name() }</td>
										<td style="text-align: center;"><font
											<c:if test="${ documents.getStatus() == 'กรุณาแก้ไขเอกสาร'}"> color="red" </c:if>>${ documents.getStatus() }</font></td>
										<c:choose>
											<c:when test="${ documents.getStatus() == 'รอการอนุมัติ' }">
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_purchaseOrderRequestV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a> <c:if
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }"> / <a
															href="do_loadEditPurchaseOrderRequestPage?doc_id=${ documents.getDoc_id() }"
															style="color: #12893F;" class="intable"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if></td>

												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
											</c:when>

											<c:when
												test="${ documents.getStatus() == 'กรุณาแก้ไขเอกสาร' }">
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_purchaseOrderRequestV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a> <c:if
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }"> / <a
															href="do_loadEditPurchaseOrderRequestPage?doc_id=${ documents.getDoc_id() }"
															style="color: #12893F;" class="intable"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if></td>

												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
											</c:when>

											<c:when
												test="${ documents.getStatus() == 'จัดทําเอกสารขอซื้อ/จ้าง สําเร็จ' }">
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_purchaseOrderRequestV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>
												<%-- 	 <c:if
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }">  / <a
															href="do_loadEditPurchaseOrderRequestPage?doc_id=${ documents.getDoc_id() }"
															style="color: #12893F;" class="intable"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if> --%>
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_PurchaseOrderDocumentV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a> <c:if
														test="${'999' == sessionScope.staffSession.major.ID_Major }">  / <a
															href="do_loadEditPurchaseOrderPage?doc_id=${documents.getDoc_id() }"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
											</c:when>
											<c:when
												test="${ documents.getStatus() == 'จัดทําใบตรวจรับพัสดุ สําเร็จ' }">
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_purchaseOrderRequestV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>
												<%--  <c:if
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }">  / <a
															href="do_loadEditPurchaseOrderRequestPage?doc_id=${ documents.getDoc_id() }"
															style="color: #12893F;" class="intable"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if> --%>
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_PurchaseOrderDocumentV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>
												<%-- 	 <c:if
														test="${'999' == sessionScope.staffSession.major.ID_Major }">  / <a
															href="do_loadEditPurchaseOrderPage?doc_id=${documents.getDoc_id() }"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if> --%>
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_ReceiveOrderV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a> <c:if
														test="${'999' == sessionScope.staffSession.major.ID_Major }">  / <a
															href="do_loadEditReceiveOrderDocument?doc_id=${ documents.getDoc_id() }"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if></td>
												<td style="color: #f4ad49; text-align: center;"><i
													class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
											</c:when>
											<c:when
												test="${ documents.getStatus() == 'จัดทําใบเบิกพัสดุ สําเร็จ' }">
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_purchaseOrderRequestV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_PurchaseOrderDocumentV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>

												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_ReceiveOrderV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a></td>
												<td style="text-align: center;"><a
													style="color: #12893F;" class="intable"
													href="print_BillOfLadingV?doc_id=${ documents.getDoc_id() }"
													target="_blank"><i class="fa fa-print  fa-lg"
														aria-hidden="true"></i></a>
												<c:if
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }">  / <a
															href="do_loadEditBillOflading?doc_id=${ documents.getDoc_id() }"><i
															style="color: #f4ad49;" class="fa fa-wrench fa-lg"
															aria-hidden="true"></i></a>
													</c:if></td>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${ documents.getStatus() == 'รอการอนุมัติ' }">
												<c:if
													test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
													<td style="text-align: center;"><a class="intable"
														href="do_loadAddPurchaseOrderPage?doc_id=${ documents.getDoc_id() }">จัดทําเอกสารขอซื้อ/จ้าง</a></td>
												</c:if>
												<c:if
													test="${sessionScope.staffSession.staff_status != 'เจ้าหน้าที่ส่วนกลาง'}">
													<td style="text-align: center;"><p style="color: red">กรุณารอการจัดทําเอกสารจากทางคณะ</p></td>
												</c:if>
											</c:when>
											<c:when
												test="${ documents.getStatus() == 'จัดทําเอกสารขอซื้อ/จ้าง สําเร็จ' }">
												<c:if
													test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
													<td style="text-align: center;"><a class="intable"
														href="do_loadAddReceiveOrderDocument?doc_id=${ documents.getDoc_id() }">จัดทําใบตรวจรับพัสดุ</a></td>
												</c:if>
												<c:if
													test="${sessionScope.staffSession.staff_status != 'เจ้าหน้าที่ส่วนกลาง'}">
													<td style="text-align: center;"><p style="color: red">กรุณารอการจัดทําเอกสารจากทางคณะ</p></td>
												</c:if>
											</c:when>
											<c:when
												test="${ documents.getStatus() == 'จัดทําใบตรวจรับพัสดุ สําเร็จ' }">
												<c:choose>
													<c:when
														test="${documents.getDepart_name() == sessionScope.staffSession.major.major_Name }">
														<td style="text-align: center;"><a class="intable"
															href="do_loadAddBillOfLading?doc_id=${ documents.getDoc_id() }">จัดทําใบเบิกรับพัสดุ</a></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<td style="text-align: center"><c:if
														test="${ documents.getStatus() != 'กรุณาแก้ไขเอกสาร' }">
														<i class="fa fa-check" style="color: green"
															aria-hidden="true"></i>
													</c:if> <c:if
														test="${ documents.getStatus() == 'จัดทําใบตรวจรับพัสดุ สําเร็จ'}"></c:if></td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${ documents.getStatus() == 'รอการอนุมัติ' }">
												<c:if
													test="${ '999' == sessionScope.staffSession.major.ID_Major }">
													<td style="text-align: center"><a class="intable"
														href="do_ReportDocument?doc_id=${ documents.getDoc_id() }">แจ้งแก้ไขเอกสารเสนอซื้อ/จ้าง</a></td>
												</c:if>
												<c:if
													test="${ '999' != sessionScope.staffSession.major.ID_Major }">
													<td></td>
												</c:if>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${ documents.size() == 0 }">
							<h4 align="center">ไม่พบข้อมูลเอกสาร</h4>
						</c:if>
					</div>
				</div>

			</div>
		</div>
	</div>
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
	<c:if test="${ result_add_check == 1 }">
		<script type="text/javascript">
			<c:if test="${doc_type == '1'}">
			window.open("print_purchaseOrderRequestV?doc_id=${doc_id}");
			</c:if>
			<c:if test="${doc_type == '2'}">
			window.open("print_PurchaseOrderDocumentV?doc_id=${doc_id}");
			</c:if>
			<c:if test="${doc_type == '3'}">
			window.open("print_ReceiveOrderV?doc_id=${doc_id}");
			</c:if>
			<c:if test="${doc_type == '4'}">
			window.open("print_BillOfLadingV?doc_id=${doc_id}");
			</c:if>
		</script>
	</c:if>
	<c:remove var="item_search" scope="session" />
</body>
</html>