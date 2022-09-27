$("#chk_all").click(function() {
	$('input:checkbox').not(this).prop('checked', this.checked);
});

$(document).ready(function() {
	$("button[id*='btn-modal']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#myModal').modal('show');
		$('#DurableCode').val(text[0]);
		$('#yearVerify').val(text[1]);
		$('#verifyDate').val(text[2]);
		$('#staff').val(text[3]);
		$('#id_staff').val(text[4])
		$('#statusDurable').val(text[5]);
		$('#note').val(text[6]);
		$('#search_year').val(text[7]);
		$('#search_status').val(text[8]);
		$('#search_room').val(text[9]);
		$('#major_search').val(text[10]);
		$('#roomDurable').val(text[11]);
		$('#responsible_person').val(text[12]);
	});
});

function check_repair() {
	var statusDurable = document.getElementById("statusDurable");
	var note = document.getElementById("note");
	if (statusDurable.value == "ชำรุด") {
		note.disabled = false;
		
	} else {
		note.disabled = true;
		note.value = "";
	}
}

function verify_form_check(frm) {
	if (frm.statusDurable.value == "ชำรุด") {
		if (frm.note.value == null || frm.note.value == "") {
			alert("กรุณากรอกหมายเหตุอาการชํารุด");
			frm.note.focus();
			return false;
		}
		if (frm.note.length < 2 || frm.note.length > 255) {
			alert("หมายเหตุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และไม่่เกิน 255 ตัวอักษร");
			frm.note.focus();
			return false;
		}
	}
}

function verify_form_checkQR(frm){

}