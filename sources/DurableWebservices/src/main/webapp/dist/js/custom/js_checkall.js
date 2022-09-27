$(document).ready(function() {
	$("#btn_printqr").click(function() {
		checkboxes = document.getElementsByName('durablecode');
		var boo = false;
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].checked == true) {
				boo = true;
				break;
			}
		}

		if (boo == true) {
			return boo;
		} else {
			alert("กรูณาระบุรหัสครุภัณฑ์");
			return boo;
		}
	});
});

$("#checkAll").click(function() {
	$('input:checkbox').not(this).prop('checked', this.checked);
});

$(document).ready(function() {
	$("button[id*='btn_editDurable']").click(function() {
		var text = $(this).attr('value').split(',');
		var roomSelect = document.getElementById('room_id').value;
		var majorSelect = document.getElementById('major_id').value;
		$('#myModalEdit').modal('show');
		$('.modal-body #durablecode').val(text[0]);
		$('.modal-body #numdurable').val(text[1]);
		$('.modal-body #namedurable').val(text[2]);
		$('.modal-body #brand').val(text[3]);
		$('.modal-body #pricedurable').val(text[4]);
		$('.modal-body #datepicker').val(text[5]);
		$('.modal-body #detail').val(text[6]);
		$('.modal-body #room_Id').val(text[7]);
		$('.modal-body #owner_id').val(text[8]);
		$('.modal-body #status').val(text[9]);
		$('.modal-body #note').val(text[10]);
		$('.modal-body #roomSelect').val(roomSelect);
		$('.modal-body #majorSelect').val(majorSelect);
	});
});


$(document).ready(function() {
	$("button[id*='btn_DurableDetail']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#myModalDetail').modal('show');
		$('.modal-body #durablecodeDetail').text(text[0]);
		$('.modal-body #numdurableDetail').text(text[1]);
		$('.modal-body #namedurableDetail').text(text[2]);
		$('.modal-body #branddurableDetail').text(text[3]);
		$('.modal-body #pricedurableDetail').text(text[4]);
		$('.modal-body #datepickerDetail').text(text[5]);
		$('.modal-body #detailDetail').text(text[6]);
		$('.modal-body #room_IdDetail').text(text[7]);
		$('.modal-body #owner_idDetail').text(text[8]);
		$('.modal-body #statusDetail').text(text[9]);
		$('.modal-body #noteDetail').text(text[10]);
		if(text[11] != '-'){
			$('.modal-body #durableImage').attr('src',text[11]);
		}else{
			$('.modal-body #durableImage').attr('src',text[11]);
		}
	});
});

$(document)
	.ready(
		function() {
			$('#btn_submit_edit')
				.click(
					function() {
						var charges = new RegExp('^[0-9]+$');
						var numdurable = new RegExp('^[0-9/]+$');
						var namedurable = new RegExp('^[ก-๙a-zA-Z0-9-/., ]+$');
						var brand = new RegExp('^[ก-๙a-zA-Z0-9-/., ]+$');
						var error_char = new RegExp('^[0-9]+$');
						var datepicker = new RegExp('^((0?[1-9]|(1|2)?[0-9]{1}|3[01])[- /](([ก-๙]?[ก-๙]*|[a-zA-Z]?[a-zA-Z]*|(0?[1-9]|1[012])*))[- /](25|26)?[0-9]{2})+$');
						if (!numdurable.test($('#numdurable').val())) {
							alert("กรุณาระบุจำนวนครุภัณฑ์");
							return false;
						} else if (!namedurable.test($('#namedurable').val())) {
							alert("กรุณาระบุชื่อครุภัณฑ์");
							return false;
						} else if (!brand.test($('#brand').val())) {
							alert("กรุณาระบุยี่ห้อ");
							return false;
						} else if (!charges.test($('#pricedurable')
							.val())) {
							alert("ระบุราคาต่อหน่วยให้ถูกต้อง");
							return false;
						} else if ($('#status').val() == "-") {
							alert("กรุณาเลือกสถานะครุภัณฑ์");
							return false;
						} else if ($('#owner_id').val() == "-") {
							alert("กรุณาระบุเจ้าของ");
							return false;
						} else {
							return true;
						}
					});
		});
		
		
		
		
		
		
/*		if ($('#room_id').val() == "-") {
							alert("กรุณาระบุห้อง");
							return false;
						} else */