$(document).ready(function() {
	$("button[id*='btn_login']").click(function() {
		var username = new RegExp('^([A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64})$');
		var password = new RegExp('^([a-zA-Z0-9]{4,16})$');
		 if ($('#username').val() == "") {
			alert("กรุณากรอก username !!!!!");
			return false;
		} else if ($('#password').val() == "") {
			alert("กรุณากรอก password !!!!!");
			return false;
		}else if (!username.test($('#username').val())) {
			alert("กรุณากรอกชื่อผู้ใช้ให้ถูกต้อง !!!!!");
			return false;
		} else if (!password.test($('#password').val())) {
			alert("กรุณากรอกรหัสผ่านให้ถูกต้อง !!!!!");
			return false;
		} else  {
			return true;
		}
	});
});

$(document).ready(function() {
	$('#example').dataTable({
		"ajax": "data/arrays.txt"
	});
});

