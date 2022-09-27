function requisitionCheck(frm) {
	if (frm.re_id.value == null || frm.re_id.value == "") {
		alert("กรุณากรอกเลขที่เอกสาร");
		frm.re_id.focus();
		return false;
	}
	if (frm.re_id.value.length < 2 || frm.re_id.value.length > 10) {
		alert("เลขที่เอกสารต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 10 ตัวอักษร");
		frm.re_id.focus();
		return false;
	}

	if (frm.re_per.value == "-" || frm.re_per.value == "" || frm.re_per.value == null) {
		alert("กรุณาเลือกรายชื่อผู้เบิก");
		frm.re_per.focus();
		return false;
	}

	if (frm.requisition_date.value == "" || frm.requisition_date.value == null) {
		alert("กรุณากรอกวันที่เบิก");
		frm.requisition_date.focus();
		return false;
	}

	var amount_check = /^[0-9]+$/;
	var item_id, amount, note;
	var item_check = 0;
	item_id = document.getElementsByClassName("item_id");
	amount = document.getElementsByClassName("requisition_total");
	note = document.getElementsByName("re_note");
	for (var i = 0; i < item_id.length; i++) {
		if (item_id[i].checked) {
			if (amount[i].value == null || amount[i].value == "") {
				alert("กรุณากรอกจํานวนของวัสดุที่เบิก");
				amount[i].focus();
				return false;
			} else if (amount[i].value == "0") {
				alert("จํานวนของวัสดุที่เบิกต้องไม่เป็น 0");
				amount[i].focus();
				return false;
			}
			if (amount[i].value.includes("-")) {
				alert("จํานวนของวัสดุที่เบิกต้องไม่เป็นค่าติดลบ");
				amount[i].focus();
				return false;
			}

			if (!amount[i].value.match(amount_check)) {
				alert("กรุณากรอกจํานวนวัสดุที่เบิกเป็นตัวเลขเท่านั้น");
				amount[i].focus();
				return false;
			}
			if (note[i].value != "") {
				if (note[i].value.length < 2 || note[i].value.length > 50) {
					alert("ข้อมูลหมายเหตุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 50 ตัวอักษร");
					note[i].focus();
					return false;
				}
			}
		} else {
			item_check += 1;
		}
	}

	if (item_check == item_id.length)  {
		alert("กรุณาเลือกรายการวัสดุที่ต้องการจะเบิก");
		return false;
	}


}

function check_year(frm){
	var amount_check = /^[0-9]+$/;
	if(!frm.year_search.value.match(amount_check)){
		alert("กรุณากรอก ปี เป็นตัวเลขเท่านั้น");
		return false;
	}
}