$(document).ready(function() {
	$("button[id*='btn-edit']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#modalDetail').modal('show');
		$('#txt_durablecode').text(text[0]);
		$('#txt_durablenumber').text(text[1]);
		$('#txt_durablemodal').text(text[2]);
		$('#txt_durableprice').text(text[4]);
		$('#txt_durableendate').text(text[5]);
		$('#txt_staff').text(text[6]);
	});
});