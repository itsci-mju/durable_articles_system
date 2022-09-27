$(document).ready(function(){
	$('#search').click(function(){
		var stu_code = new RegExp('^[0-9]{13}$');
		
		if(!stu_code.test($('#idcode').val())){
			alert("กรูณากรอกข้อมูลให้ถูกต้อง");
			return false;
		}
	});
});