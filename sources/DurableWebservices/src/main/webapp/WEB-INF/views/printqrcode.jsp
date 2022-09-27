<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>

	<div class="row" style="margin-top: 100px">
		<div class="container">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-print"></i> รายการพิมพ์รหัสคิวอาร์
					</h3>
				</div>
				<div class="panel-body">
					<form>
						<div align="right" style="margin-bottom: 5px">
							<button type="button" onclick="printDiv('printableArea')"
								type="submit" class="btn btn-sky text-uppercase">
								<i class="fa fa-print"></i> พิมพ์รหัสคิวอาร์
							</button>
						</div>
						<div id="printableArea">
							<c:forEach var="list" items="${picpathlist}" varStatus="loop">
								<div class="col-xs-4 col-sm-2"
									style="border: 1px; border-style: solid; padding: 20px; margin: 10px"
									align="center">
									<div>
										<img height="150px" width="150px"
											src="${pageContext.request.contextPath}/file/qrcode/${list.value}">
									</div>
									<div>${list.key}</div>
								</div>
							</c:forEach>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function printDiv(divName) {
			var printContents = document.getElementById(divName).innerHTML;
			var originalContents = document.body.innerHTML;

			document.body.innerHTML = printContents;

			window.print();

			document.body.innerHTML = originalContents;
		}
	</script>
</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
</html>