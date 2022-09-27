<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>

</head>
<body>
	<div class="modal fade" id="modalDetail" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					รหัสครุภัณฑ์ : <h4 class="modal-title" id="txt_durablecode"></h4>
				</div>
				<div class="modal-body">
					<div style="margin: 10px">
						<div class="row">
							จำนวน = <span id="txt_durablenumber"></span>
						</div>
						<div class="row">
							รุ่น/รายละเอียด = <span id="txt_durablemodal"></span>
						</div>
						<div class="row">
							ราคา/หน่วย = <span id="txt_durableprice"></span>
						</div>
						<div class="row">
							วันที่ได้รับ = <span id="txt_durableendate"></span>
						</div>
						<div class="row">
							ผู้รับผิดชอบในการตรวจสอบ = <span id="txt_staff"></span>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-hot text-uppercase btn-sm"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include
		page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
</body>


</html>