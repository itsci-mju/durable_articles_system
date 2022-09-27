<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>หน้าหลัก</title>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<c:if test="${not empty sessionScope.staffSession}">
	<jsp:forward page="/home"></jsp:forward>
</c:if>
<jsp:include
	page="/WEB-INF/views/common/importhead.jsp"></jsp:include>

</head>
<body>

	<jsp:include
		page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>
	<!-- content -->
	<div class="container">
		<div class="col-md-4 col-md-offset-4" style="padding-top: 150px">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">เข้าสู่ระบบ</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">
					<form id="loginform" class="form-horizontal" role="form"
						action="loginweb" method="post" id="login">
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i>
							</span> <input id="username" type="text" class="form-control"
								name="username" placeholder="อีเมล">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input id="password" type="password" class="form-control"
								name="password" placeholder="รหัสผ่าน">
						</div>
						<div class="form-group" align="center">
							<button type="submit"
								class="btn btn-sky text-uppercase ladda-button"
								data-style="expand-right" id="btn_login" name="btn_login"
								style="width: 120px">
								<span class="ladda-label"> เข้าสู่ระบบ </span>
							</button>
						</div>
						<!-- 		</form> -->
				</div>
			</div>
		</div>
	</div>
</body>

<jsp:include
	page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>

<script type="text/javascript"
	src="/dist/js/custom/js_pagelogin.js"></script>

<c:if test="${value == 1}">
	<c:if test="${message != null }">
		<script type="text/javascript">
			var msg = '${message}';
			alert(msg);
		</script>
		<c:remove var="message" scope="session" />
		<c:remove var="value" scope="session" />
	</c:if>
</c:if>

</html>