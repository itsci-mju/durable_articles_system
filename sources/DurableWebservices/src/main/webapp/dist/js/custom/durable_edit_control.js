$(document).ready(function() {
	$("button[id*='btn_editDurable']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#DurableCode').val(text[0]);
		$('#yearVerify').val(text[1]);
	});
});