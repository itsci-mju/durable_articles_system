<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.text.*,ac.th.itsci.durable.entity.*"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<meta charset="UTF-8">
<title>ใบตรวจรับพัสดุ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<div class="row" style="margin-top: 70px">
		<div class="container">
			<form action="print_ReceiveOrder"
				name="purchaseOrderRequestFrm" class="form-horizontal" method="post">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">ใบตรวจรับพัสดุ</h3>
					</div>
					<div class="panel-body">
						<fieldset>
							<legend style="font-size: 1em">
								<i class="fa fa-file-o"></i> ข้อมูลเอกสาร
							</legend>
							<div class="row">
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<input type="hidden" name="doc_id" value="${ receiveDocument.getDoc_id() }">
										<div class="form-group">
											<label class="col-sm-5 control-label"> </label>
											<div class="col-sm-5"></div>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสแผนงาน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getPlan_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสหน่วยงาน :
											</label> <label class="col-sm-5 control-label">${ receiveDocument.getDepart_id() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รหัสกองทุน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getFund_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใช้เงิน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getMoney_used() }
											</label>
										</div>
									</div>
								</div>
								<!--  end first row -->
								<div class="col-sm-6">
									<div class="row" style="margin: 10px">
										<div class="form-group">
											<label class="col-sm-5 control-label">เลขที่เอกสาร :
											</label><label class="col-sm-5 control-label">${ receiveDocument.getReceiveOrderDocument_id() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">แผนงาน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getPlan_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">หน่วยงาน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getDepart_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">กองทุน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getFund_name() }
											</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งาน : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getWork_name() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">งบ : </label> <label
												class="col-sm-5 control-label">${ receiveDocument.getBudget() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">วันที่ : </label> <label
												class="col-sm-5 control-label">${ date }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">รับของมาจาก : </label>
											<label class="col-sm-5 control-label">${ receiveDocument.getReceiveOrderDocument_from() }</label>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">ใบกํากับภาษีเลขที่
												: </label><label class="col-sm-5 control-label">${ receiveDocument.getInvoice_number() }</label>
										</div>
									</div>
								</div>
							</div>

						</fieldset>
						<!--  item_cart table -->
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="table-responsive">
									<table border="1" id="tableform">
										<tr align="center">
											<th style="text-align: center" rowspan="2">ลําดับที่</th>
											<th style="text-align: center" rowspan="2">รายการ</th>
											<th style="text-align: center" rowspan="2">ใบสั่งซื้อ/จ้าง
												เลขที่</th>
											<th style="text-align: center" rowspan="2">หน่วยนับ</th>
											<th style="text-align: center" colspan="3">จํานวนหน่วย</th>
											<th style="text-align: center" rowspan="2">เป็นเงิน</th>
										</tr>
										<tr align="center">
											<th style="text-align: center">ตามใบสั่งซื้อ/จ้าง</th>
											<th style="text-align: center">ตามใบส่งของ</th>
											<th style="text-align: center">ตามที่รับจริง</th>
										</tr>
										<c:forEach
											items="${ receiveDocument.getRequisitionItemLists() }"
											var="item_cart" varStatus="loop">
											<tr>
												<td style="text-align: center">${ loop.index+1 }</td>
												<td>${ item_cart.getId().getItem().getItem_name() }</td>
												<td style="text-align: center">-</td>
												<td style="text-align: center">${ item_cart.getId().getItem().getItem_unit() }</td>
												<td style="text-align: center">${ item_cart.getAmount() }</td>
												<td style="text-align: center">${ item_cart.getAmount_in_invoice() }</td>
												<td style="text-align: center">${ item_cart.getAmount_received() }</td>
												<td style="text-align: center"><fmt:formatNumber
														type="number" minFractionDigits="2" maxFractionDigits="2"
														value="${ item_cart.getTotal_price() }" /></td>
											</tr>
										</c:forEach>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td style="text-align: center" colspan="2">จํานวนเงินก่อนภาษีมูลค่าเพิ่ม</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ receiveDocument.getPrice_with_out_tax() }" /></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td style="text-align: center" colspan="2">ภาษีมูลค่าเพิ่ม
												7%</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ receiveDocument.getTax() }" /></td>
										</tr>
										<tr>
											<td style="text-align: center" colspan="5"
												style="text-align:center">${ receiveDocument.getPrice_txt() }</td>
											<td style="text-align: center" colspan="2">รวมเป็นเงินทั้งสิ้น</td>
											<td style="text-align: center"><fmt:formatNumber
													type="number" minFractionDigits="2" maxFractionDigits="2"
													value="${ receiveDocument.getTotal_price() }" /></td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<!--  end item_cart -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<label>เห็นว่าปริมาณและคุณภาพถูกต้องครบถ้วนตามใบสั่งซื้อ
									/ จ้าง หรือสัญญา หรือข้อตกลงเป็นหนังสือ ตั้งแต่วันที่</label> <label
									for="date_label" id="date_label"></label><br> <label>และได้ส่งมอบแก่
									เจ้าหน้าที่พัสดุประจําหน่วยงาน สํานักงานเลขานุการ
									คณะวิทยาศาสตร์ รับไว้เป็นการถูกค้องแล้วในวันนี้</label><br> <label>คณะกรรมการตรวจรับพัสดุ
									จึงหร้อมกันลงลายมือชื่อไว้เป็นการหลักฐานและเสนอเพื่อทราบ</label>
							</div>
						</div>

						<!--  start committee -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-12 col-md-12 col-lg-12"
								style="text-align: center;">
								<c:forEach items="${ receiveDocument.getCommittee() }"
									var="committees">
									<label>ลงชื่อ
										.............................................. กรรมการ</label>
									<br>
									<label>${ committees.getCommittee().getPersonnel().getPersonnel_prefix() }
										${ committees.getCommittee().getPersonnel().getPersonnel_firstName() }
										${ committees.getCommittee().getPersonnel().getPersonnel_lastName() }</label>
									<br>
								</c:forEach>
							</div>
						</div>

						<!--  end committee -->
						<!-- signature -->
						<div class="row" style="margin-top: 20px">
							<div class="col-sm-6 col-md-6 col-lg-6"
								style="text-align: center;">
								<div class="form-group">
									<br> <br> <label>..............................................</label><br>
									<label>${ purchase_person.getPersonnel_prefix() } ${ purchase_person.getPersonnel_firstName() }
										${ purchase_person.getPersonnel_lastName() }</label>
								</div>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6"
								style="text-align: center;">
								<div class="form-group">
									<label>ทราบและอนุมัติเบิกจ่าย</label><br> <br> <label>..............................................</label><br>
									<label>${ dean.getPersonnel_prefix() } ${ dean.getPersonnel_firstName() }
										${ dean.getPersonnel_lastName() }</label>
								</div>
							</div>
						</div>
						<!--  end signature -->
					</div>



					<div class="panel-footer" style="height: 65px">
						<div class="form-group">
							<div align="right" style="margin-right: 15px">
								<button type="submit" class="btn btn-sky text-uppercase" id="" formtarget="_blank">>
									<i class="fa fa-print"></i> พิมพ์
								</button>
								<button type="submit" formaction="backLoadListDocument" class="btn btn-danger text-uppercase"
									id="">ย้อนกลับ</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script src="/dist/js/custom/calculate.js"></script>
	<script>
		function openPurchase(purchase) {
			var i;
			var x = document.getElementsByClassName("purchase");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(purchase).style.display = "block";
		}

		function openDean(rp) {
			var i;
			var x = document.getElementsByClassName("dean");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(rp).style.display = "block";
		}
	</script>
	<c:if test="${not empty sessionScope.messages}">
		<script type="text/javascript">
			var msg = '${messages}';
			alert(msg);
		</script>
		<c:remove var="messages" scope="session" />
	</c:if>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
	</c:if>
	<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>


</body>
</html>