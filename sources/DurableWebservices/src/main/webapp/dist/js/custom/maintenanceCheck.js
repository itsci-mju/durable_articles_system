function maintenanceCheck(frm) {
	if (frm.datepicker.value == "" || frm.datepicker.value == null) {
		alert("กรุณากรอกข้อมูลวันที่ส่งซ่อม");
		frm.datepicker.focus();
		return false;
	}

	if (frm.maintenance_detail.value == null || frm.maintenance_detail.value == "") {
		alert("กรุณากรอกรายละเอียดการซ่อม");
		frm.maintenance_detail.focus();
		return false;
	}

	if (frm.maintenance_detail.value.length < 2 || frm.maintenance_detail.value.length > 100) {
		alert("รายละเอียดการซ่อม ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 100 ตัวอักษร");
		frm.maintenance_detail.focus();
		return false;
	}
	var n_check = /^[0-9]+$/;
	if (frm.maintenance_price.value == null || frm.maintenance_price.value == "") {
		alert("กรุณากรอกราคาการซ่อม");
		frm.maintenance_price.focus();
		return false;
	}
	if (!frm.maintenance_price.value.match(n_check)) {
		alert("กรุณากรอกราคาการซ่อมด้วยตัวเลขเท่านั้น");
		frm.maintenance_price.focus();
		return false;
	}


	if (frm.company.value == null || frm.company.value == "") {
		alert("กรุณากรอกชื่อบริษัทที่ส่งซ่อม");
		frm.company.focus();
		return false;
	}
	if (frm.company.value.length < 2 || frm.company.value.length > 255) {
		alert("ชื่อบริษัทที่ส่งซ่อม ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		frm.company.focus();
		return false;
	}

}