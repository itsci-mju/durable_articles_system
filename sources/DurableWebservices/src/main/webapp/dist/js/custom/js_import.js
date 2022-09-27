$(document).ready(function() {
	$("#btn_upload").click(function() {
		
		var file = new RegExp('^(xlsx)$');
		var file3 = $("#filedurable").val();
		var fileExtension = file3.split(".");
		
		if (!file.test(fileExtension[1])){
			alert("กรุณากรอกข้อมูลไฟล์ให้ถูกต้อง");
			return false;
		}
		
		return true;
	});
});