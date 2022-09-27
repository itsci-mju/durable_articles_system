$(document).ready(function() {
	$("#yearsearch").click(function() {
		var term = new RegExp('^(?:(?:25[5-9][0-9]))$');

		if (!term.test($("#year").val())) {
			alert("กรุณากรอกข้อมูลปีการศึกษาให้ถูกต้อง");
			return false;
		} else if ($('#major_id').val() == "99") {
			alert("กรุณาเลือกสาขา");
			return false;
		} else {
			return true;
		}
	});
});


$(document).ready(function() {
	$("button[id*='btn_modal']").click(function() {
		var text = $(this).attr('value').split(',');
		var major_id = document.getElementById('major_id').value;
		var statusSelect = document.getElementById('statusSelect').value;
		var year = document.getElementById('year').value;
		var room_ID = document.getElementById('room_ID').value;
		$('#myModal').modal('show');
		$('.modal-body #DurableCode').val(text[0]);
		$('.modal-body #yearVerify').val(text[1]);
		$('.modal-body #verifyDate').val(text[2]);
		$('.modal-body #staff').val(text[3]);
		$('.modal-body #id_staff').val(text[4]);
		$('.modal-body #statusDurable').val(text[5]);
		$('.modal-body #note').val(text[6]);
		$('.modal-body #major_id').val(major_id);
		$('.modal-body #statusSelect').val(statusSelect);
		$('.modal-body #yearsearch').val(year);
		$('.modal-body #room_ID').val(room_ID);
	});
});

$(document).ready(function() {
	$("button[id*='btn_Notification']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#showNotifications').modal('show');
		$('.modal-body #textNotifications').text(text[0]);
		$('.modal-body #textNotificationsHeader').text(text[1]);
	});
});