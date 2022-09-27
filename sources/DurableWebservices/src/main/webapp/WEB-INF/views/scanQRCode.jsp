<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, ac.th.itsci.durable.entity.*,java.text.*"%>
<!DOCTYPE html>
<html>
<head>
<title>รายงานการตรวจสอบครุภัณฑ์ประจำปี</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name="viewport"
	content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta name="viewport" content="width=device-width">
<c:if test="${sessionScope.staffSession.staff_name == null}">
	<jsp:forward page="/durable"></jsp:forward>
</c:if>
<jsp:include page="/WEB-INF/views/common/importhead.jsp"></jsp:include>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!--  script scan -->
<script type="text/javascript" charset="UTF-8"
	src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>
<body>
	<jsp:include page="/WEB-INF/views/common/navbarmenu.jsp"></jsp:include>


	<div class="container" style="padding-bottom: 50px;">
		<div class="row" style="margin-top: 80px">
			<div class="container">
				<!-- 		Panel -->
				<div class="panel panel-info">
					<!-- 			Panel Heading -->
					<div class="panel-heading">
						<div class="row">
							<h3 class="panel-title col-sm-6">
								<i class="fa fa-list-alt"></i> แสกน QR Code
							</h3>
						</div>
					</div>
					<!-- 				End Panel Heading -->
					<!-- 				Panel Body -->
					<div class="panel-body">
						<div align="center">
							<!-- 				Table -->
							<div class="text-center" style="margin-buttom: 50px">
								<br />
							</div>
							<video id="preview" class="video-back"
								style="width: 50%; margin: 0 auto;">

							</video>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
        let scanner = new Instascan.Scanner(
            {
                video: document.getElementById('preview')
            }
        );
        scanner.addListener('scan', function(content) {
        	if (confirm("กรุณากดปุ่ม ok เพื่อดำเนินการต่อ!")) {
        		
        		window.open("do_loadeditVerifyDurableByQR?durable_code="+decodeURIComponent( escape( content ) ));
        	 } else {
        		 alert("no");
        	 }
        });
  		Instascan.Camera.getCameras().then(cameras => 
        {
            if(cameras.length == 1){
                scanner.start(cameras[0]);
            }else{
            	  scanner.start(cameras[1]);
            }
        }); 
        
         
    </script>
<script type="text/javascript"
	src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
<c:if test="${ message != '' &&  message != null}">
	<script type="text/javascript">
		var msg = '${message}';
		alert(msg);
	</script>
	<c:remove var="message" scope="session" />
	<c:remove var="value" scope="session" />
</c:if>
<c:if
	test="${ sessionScope.messages != null && sessionScope.messages != ''  }">
	<script type="text/javascript">
		var msg = '${messages}';
		alert(msg);
	</script>
	<c:remove var="messages" scope="session" />
	<c:remove var="value" scope="session" />
</c:if>
<c:if test="${ sessionScope.durable_name != null }">
	<c:remove var="durable_name" scope="session" />
</c:if>
<c:if test="${ sessionScope.search_code != null }">
	<c:remove var="search_code" scope="session" />
</c:if>
</html>