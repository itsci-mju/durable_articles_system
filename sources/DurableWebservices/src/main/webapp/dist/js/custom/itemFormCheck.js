function itemFrmCheck(frm) {

	if (frm.item_category.value == "" || frm.item_category.value == null || frm.item_category.value == "-") {
		alert("กรุณาเลือกหมวดวัสดุ");
		frm.item_category.focus();
		return false;
	}

	if (frm.item_name.value == "" || frm.item_name.value == null) {
		alert("กรุณากรอกข้อมูลชื่อวัสดุ");
		frm.item_name.focus();
		return false;
	}
	if (frm.item_name.value.length < 2 || frm.item_name.value.length > 100)  {
		alert("ชื่อวัสดุุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 100 ตัวอักษร");
		frm.item_name.focus();
		return false;
	}

	if (frm.item_unit.value == "" || frm.item_unit.value == null ) {
		alert("กรุณากรอกข้อมูลหน่วยนับ");
		frm.item_unit.focus();
		return false;
	}
	if (frm.item_unit.value.length < 2 || frm.item_unit.value.length > 100) {
		alert("หน่วยนับวัสดุุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 100 ตัวอักษร");
		frm.item_unit.focus();
		return false;
	}

	if (frm.item_note.value != null || frm.item_note.value != "") {
		if (frm.item_note.value.length < 1 || frm.item_note.value.length > 255) {
			alert("ข้อมูลหมายเหตุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
			frm.item_note.focus();
			return false;
		}
	}


	var price_check = /^((\d+(\.\d*)?)|(\.\d+))$/;
	if (frm.item_price.value == "" || frm.item_price.value == null) {
		alert("กรุณากรอกข้อมูลราคา");
		frm.item_price.focus();
		return false;
	} else {
		if (!frm.item_price.value.match(price_check)) {
			alert("กรุณากรอกข้อมูลราคาเป็นตัวเลขเท่านั้น");
			frm.item_price.focus();
			return false;
		}
	}



}
