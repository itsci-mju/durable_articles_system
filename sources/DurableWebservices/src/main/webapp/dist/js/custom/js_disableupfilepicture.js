$(document).ready(function() {
	$("#checkbox1").change(function() {
		if ($("#checkbox1").is(':checked')) {
			$("#pic_repairreport").prop('disabled', false);
			$("#checkbox1").checked == true;
		} else {
			$("#pic_repairreport").prop('disabled', true);
			$("#checkbox1").checked == false;
		}
	});
	$("#checkbox2").change(function() {
		if ($("#checkbox2").is(':checked')) {
			$("#pic_quatation").prop('disabled', false);
			$("#checkbox2").checked == true;
		} else {
			$("#pic_quatation").prop('disabled', true);
			$("#checkbox2").checked == false;
		}
	});
	$("#checkbox3").change(function() {
		if ($("#checkbox3").is(':checked')) {
			$("#pic_invoice").prop('disabled', false);
			$("#checkbox3").checked == true;
		} else {
			$("#pic_invoice").prop('disabled', true);
			$("#checkbox3").checked == false;
		}
	});
	$("#checkbox4").change(function() {
		if ($("#checkbox4").is(':checked')) {
			$("#borrowpicture").prop('disabled', false);
			$("#checkbox4").checked == true;
		} else {
			$("#borrowpicture").prop('disabled', true);
			$("#checkbox4").checked == false;
		}
	});
});
