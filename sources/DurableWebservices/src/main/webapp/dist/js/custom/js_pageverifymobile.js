$(document).ready(function() {
	$("button[id*='btn_search']").click(function() {
		var term = new RegExp('^(?:(?:25[5-9][0-9]))$');
		
		if (!term.test($("#year").val())) {
			alert("กรุณากรอกข้อมูลปีการศึกษาให้ถูกต้อง");
			return false;
		}
	});
		
	return true;
});

$(document).ready(function(){
	$("button[id*='btn_save']").click(function(){
		var term = new RegExp('^(?:(?:25[5-9][0-9]))$');
		
		if (!term.test($("#year_save").val())) {
			alert("กรุณากรอกข้อมูลปีการศึกษาให้ถูกต้อง");
			return false;
		}
	});
	return true;	
});



