function durableControlFrmChk(frm) {
	if (frm.durablecode.value == null || frm.durablecode.value == "") {
		alert("กรุณากรอกข้อมูลรหัสครุภัณฑ์");
		return false;
	}
	if (frm.durablecode.value.length < 2 || frm.durablecode.value.length > 60) {
		alert("รหัสครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 60 ตัวอักษร");
		return false;
	}

	if (frm.numdurable.value == null || frm.numdurable.value == "") {
		alert("กรุณากรอกข้อมูลจำนวนครุภัณฑ์");
		return false;
	}

	if (frm.namedurable.value == null || frm.namedurable.value == "") {
		alert("กรุณากรอกข้อมูลชื่อครุภัณฑ์");
		return false;
	}
	if (frm.namedurable.value.length < 2 || frm.namedurable.value.length > 1000) {
		alert("ชื่อครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		return false;
	}

	if (frm.pricedurable.value == null || frm.pricedurable.value == ""){
		alert("กรุณากรอกราคาต่อหน่วย");
		return false;
	}

	if (frm.brand.value == null || frm.brand.value == "") {
		alert("กรุณากรอกข้อมูลยี่ห้อ");
		return false;
	}
	if (frm.brand.value.length < 2 || frm.brand.value.length > 1000) {
		alert("ยี่ห้อครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		return false;
	}

	if (frm.datepicker.value == null || frm.datepicker.value == "") {
		alert("กรุณากรอกวันที่ได้รับ");
		return false;
	}

	if (frm.detail.value == null || frm.detail.value == "") {
		alert("กรุณากรอกข้อมูลรายละเอียด");
		return false;
	}
	if (frm.detail.value != "-" && frm.detail.value.length < 2 || frm.detail.value.length > 1000) {
		alert("รายละเอียดครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		return false;
	}

	if (frm.room_id.value == null || frm.room_id.value == '-') {
		alert("กรุณากรอกข้อมูลห้องที่ใช้ครุภัณฑ์");
		return false;
	}

	if (frm.owner_id.value == null || frm.owner_id.value == '-') {
		alert("กรุณากรอกข้อมูลผู้ใช้ครุภัณฑ์");
		return false;
	}

	if (frm.status.value == null || frm.status.value == '-') {
		alert("กรุณากรอกข้อมูลสถาพครุภัณฑ์");
		return false;
	}

	if (frm.durable_type.value == null || frm.durable_type.value == "") {
		alert("กรุณากรอกข้อมูลประเภท");
		return false;
	}
	if (frm.durable_type.value.length < 1 || frm.durable_type.value.length > 255) {
		alert("ข้อมูลประเภทครุภัณฑ์ต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		return false;
	}

	if (frm.note.value != "-" || frm.note.value != "" || frm.note.value != null)  {
		if (frm.note.value.length < 1 || frm.note.value.length > 255) {
			alert("ข้อมูลหมายเหตุครุภัณฑ์ต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
			return false;
		}
	}

	var n_check = /\d/;
	if (frm.durable_life_time.value == null || frm.durable_life_time.value == "") {
		alert("กรุณากรอกข้อมูลอายุการใช้");
		return false;
	} 
	
	if (frm.durable_life_time.value == "0") {
		alert("อายุการใช้ต้องไม่เป็น 0");
		return false;
	}
	
	if (!frm.durable_life_time.value.match(n_check)) {
		alert("กรุณากรอกข้อมูลอายุการใช้เป็นตัวเลขเท่านั้น");
		return false;
	}


	if (frm.durable_petition_number.value == null || frm.durable_petition_number.value == "") {
		alert("กรุณากรอกข้อมูลเลขที่ฎีกา");
		return false;
	}
	if (frm.durable_petition_number.value != "-" && frm.durable_petition_number.value.length < 1 || frm.durable_petition_number.value.length > 255) {
		alert("ข้อมูลเลขที่ฎีกาต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		return false;
	}

	if (frm.durable_serial_number.value == null || frm.durable_serial_number.value == "") {
		alert("กรุณากรอกข้อมูล Serial Number");
		return false;
	}
	if (frm.durable_serial_number.value != "-" && frm.durable_serial_number.value.length < 1 || frm.durable_serial_number.value.length > 255) {
		alert("ข้อมูลเลขที่ฎีกาต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		return false;
	}

	if (frm.depreciation_rate.value == null || frm.depreciation_rate.value == "") {
		alert("กรุณากรอกข้อมูลอัตราเสื่อมราคา");
		return false;
	}
}