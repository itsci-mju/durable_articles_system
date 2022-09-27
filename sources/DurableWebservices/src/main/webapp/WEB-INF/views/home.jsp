<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>หน้าหลัก</title>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
	<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>

</head>
<body>
	
<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp" ></jsp:include>

</body>
<jsp:include page="/WEB-INF/views/common/importfooter.jsp"></jsp:include>
<c:if test="${empty sessionScope.staffSession}">
<jsp:forward page="/back_to_index"></jsp:forward>
</c:if>
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
<c:remove var="item_search" scope="session" />
</html>