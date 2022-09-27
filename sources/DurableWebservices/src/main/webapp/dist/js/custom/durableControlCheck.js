function durableTypeChange() {
	var durable_types = document.getElementById("durable_types").value;
	var jsonDurableType = document.getElementById("jsonDurableType").value;
	var replacJsonDurableType = jsonDurableType.replace(/'/g, '"');

	var values = null;
	var durable_life_time = document.getElementById("durable_life_time");
	var depreciation_rate = document.getElementById("depreciation_rate");

	obj = JSON.parse(replacJsonDurableType);
	values = obj.durable_type[durable_types];

	if (durable_types === "-") {
		durable_life_time.value = "";
		depreciation_rate.value = "";
	} else {
		if (values.length > 0) {
			durable_life_time.value = values[0];
			depreciation_rate.value = values[1];
		} else {
			durable_life_time.value = "";
			depreciation_rate.value = "";
		}
	}
}

function companyChange() {
	var company_name = document.getElementById("company_name").value;
	var jsonCompany = document.getElementById("jsonCompany").value;
	var replaceJsonCompany = jsonCompany.replace(/'/g, '"');

	var values = null;
	var tell = document.getElementById("tell");
	var address = document.getElementById("address");

	obj = JSON.parse(replaceJsonCompany);
	values = obj.company_add[company_name];

	if (company_name === "") {
		tell.value = "";
		address.value = "";
	} else {
		if (values.length > 0) {
			tell.value = values[1];
			address.value = values[0];
		} else {
			tell.value = "";
			address.value = "";
		}
	}
}

function openGroupGenerated() {
	var group_add_check = document.getElementById("group_add_check");
	var generated_btn = document.getElementById("generate_durable_code");
	if (group_add_check.checked == true) {
		generated_btn.disabled = false;
	} else {
		generated_btn.disabled = true;
	}
}

function checkDurableNum() {
	var numdurable = document.getElementById("numdurable");
	var d_c = document.getElementsByName("durablecode");
	var n_check = /^[1-9]+$/;
	var dcode_check = /^[ก-๙]{2}\.[0-9]{4}-[0-9]{3}-[0-9]{4}\/[0-9]{2}/;
	if (d_c[0].value == null || d_c[0].value == "") {
		alert("กรุณากรอกรหัสครุภัณฑ์ก่อนทําการสร้างรหัสครุภัณฑ์แบบกลุ่ม");
		d_c[0].focus();
		return false;
	} else {
		if (!d_c[0].value.match(dcode_check)) {
			alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้องตามรูปแบบ (วท.0000-000-0000/00)");
			d_c[0].focus();
			return false;
		}
	}
	
	if (numdurable.value == null || numdurable.value == "") {
		alert("กรุณากรอกจํานวนก่อนทําการสร้างรหัสครุภัณฑ์แบบกลุ่ม");
		numdurable.focus();
		return false;
	}else{
		if(!numdurable.value.match(n_check)){
			if(numdurable.value == "0"){
				alert("กรุณากรอกจํานวนให้มากกว่า 0");
				numdurable.focus();
				return false;
			}
		}
	}
}

function durableControlFrmChk(frm) {

	var dcode_check = /^[ก-๙]{2}\.[0-9]{4}-[0-9]{3}-[0-9]{4}\/[0-9]{2}/;

	if (frm.group_add_check.checked == false) {
		if (frm.durablecode.value == null || frm.durablecode.value == "") {
			alert("กรุณากรอกข้อมูลรหัสครุภัณฑ์");
			frm.durablecode.focus();
			return false;
		}
		if (frm.durablecode.value.length < 2 || frm.durablecode.value.length > 60) {
			alert("รหัสครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 60 ตัวอักษร");
			frm.durablecode.focus();
			return false;
		}
		if (!frm.durablecode.value.match(dcode_check)) {
			alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้องตามรูปแบบ (วท.0000-000-0000/00)");
			frm.durablecode.focus();
			return false;
		}
	} else {
		var d_c = document.getElementsByName("durablecode");
		if (d_c.length == 1) {
			alert("กรุณากดสร้างรหัสครุภัณฑ์และตรวจสอบให้เรียบร้อยก่อนทําการบันทึก");
			frm.durablecode.focus();
			return false;
		}

	}


	if (frm.numdurable.value == null || frm.numdurable.value == "") {
		alert("กรุณากรอกข้อมูลจำนวนครุภัณฑ์");
		frm.numdurable.focus();
		return false;
	}

	if (frm.namedurable.value == null || frm.namedurable.value == "") {
		alert("กรุณากรอกข้อมูลชื่อครุภัณฑ์");
		frm.namedurable.focus();
		return false;
	}
	if (frm.namedurable.value.length < 2 || frm.namedurable.value.length > 1000) {
		alert("ชื่อครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		frm.namedurable.focus();
		return false;
	}

	if (frm.pricedurable.value == null || frm.pricedurable.value == "") {
		alert("กรุณากรอกราคาต่อหน่วย");
		frm.pricedurable.focus();
		return false;
	}

	if (frm.brand.value == null || frm.brand.value == "") {
		alert("กรุณากรอกข้อมูลยี่ห้อ");
		frm.brand.focus();
		return false;
	}
	if (frm.brand.value.length < 2 || frm.brand.value.length > 1000) {
		alert("ยี่ห้อครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		frm.brand.focus();
		return false;
	}

	if (frm.datepicker.value == null || frm.datepicker.value == "") {
		alert("กรุณากรอกวันที่ได้รับ");
		frm.datepicker.focus();
		return false;
	}

	if (frm.detail.value == null || frm.detail.value == "") {
		alert("กรุณากรอกข้อมูลรายละเอียด");
		frm.detail.focus();
		return false;
	}
	if (frm.detail.value != "-" && frm.detail.value.length < 2 || frm.detail.value.length > 1000) {
		alert("รายละเอียดครุภัณฑ์ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 1000 ตัวอักษร");
		frm.detail.focus()
		return false;
	}

	if (frm.room_id.value == null || frm.room_id.value == '-') {
		alert("กรุณากรอกข้อมูลห้องที่ใช้ครุภัณฑ์");
		frm.room_id.focus();
		return false;
	}

	if (frm.owner_id.value == null || frm.owner_id.value == '-') {
		alert("กรุณากรอกข้อมูลผู้ใช้ครุภัณฑ์");
		frm.owner_id.focus();
		return false;
	}

	if (frm.status.value == null || frm.status.value == '-') {
		alert("กรุณากรอกข้อมูลสถาพครุภัณฑ์");
		frm.status.focus();
		return false;
	}

	if (frm.durable_type.value == null || frm.durable_type.value == "") {
		alert("กรุณากรอกข้อมูลประเภท");
		frm.durable_type.focus();
		return false;
	}
	if (frm.durable_type.value.length < 1 || frm.durable_type.value.length > 255) {
		alert("ข้อมูลประเภทครุภัณฑ์ต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		frm.durable_type.focus();
		return false;
	}

	if (frm.note.value != "-" || frm.note.value != "" || frm.note.value != null) {
		if (frm.note.value.length < 1 || frm.note.value.length > 255) {
			alert("ข้อมูลหมายเหตุครุภัณฑ์ต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
			frm.note.focus();
			return false;
		}
	}

	var n_check = /^[0-9]+$/;
	if (frm.durable_life_time.value == null || frm.durable_life_time.value == "") {
		alert("กรุณากรอกข้อมูลอายุการใช้");
		frm.durable_life_time.focus();
		return false;
	}

	if (frm.durable_life_time.value == "0") {
		alert("อายุการใช้ต้องไม่เป็น 0");
		frm.durable_life_time.focus();
		return false;
	}

	if (!frm.durable_life_time.value.match(n_check)) {
		alert("กรุณากรอกข้อมูลอายุการใช้เป็นตัวเลขเท่านั้น");
		frm.durable_life_time.focus();
		return false;
	}


	if (frm.durable_petition_number.value == null || frm.durable_petition_number.value == "") {
		alert("กรุณากรอกข้อมูลเลขที่ฎีกา");
		frm.durable_petition_number.focus();
		return false;
	}
	if (frm.durable_petition_number.value != "-" && frm.durable_petition_number.value.length < 1 || frm.durable_petition_number.value.length > 255) {
		alert("ข้อมูลเลขที่ฎีกาต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		frm.durable_petition_number.focus();
		return false;
	}

	if (frm.durable_serial_number.value == null || frm.durable_serial_number.value == "") {
		alert("กรุณากรอกข้อมูล Serial Number");
		frm.durable_serial_number.focus();
		return false;
	}
	if (frm.durable_serial_number.value != "-" && frm.durable_serial_number.value.length < 1 || frm.durable_serial_number.value.length > 255) {
		alert("ข้อมูล Serial Number ต้องมีความยาวตั้งแต่ 1 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		frm.durable_serial_number.focus();
		return false;
	}

	if (frm.depreciation_rate.value == null || frm.depreciation_rate.value == "") {
		alert("กรุณากรอกข้อมูลอัตราเสื่อมราคา");
		frm.depreciation_rate.focus();
		return false;
	}
	if (!frm.depreciation_rate.value.match(n_check)) {
		alert("กรุณากรอกข้อมูลอัตราเสื่อมราคาเป็นตัวเลขเท่านั้น");
		frm.depreciation_rate.focus();
		return false;
	}
	if (frm.depreciation_rate.value == "0") {
		alert("ข้อมูลอัตราเสื่อมราคาต้องไม่เป็น 0");
		frm.depreciation_rate.focus();
		return false;
	}


	if (frm.tell.value != "-") {
		if (!frm.tell.value.match(n_check)) {
			alert("กรุณากรอกเบอร์โทรศัพท์ด้วยตัวเลขเท่านั้น");
			frm.tell.focus();
			return false;
		}
	}

	if (frm.address.value != "-" && frm.address.value != "") {
		if (frm.address.value.length < 2 || frm.address.value.length > 255) {
			alert("ข้อมูลที่อยู่ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
			frm.address.focus();
			return false;
		}
	}

	if (frm.durable_money_type.value == "อื่นๆ") {
		if (frm.durable_money_type_text.value == "" || frm.durable_money_type_text.value == null) {
			alert("กรุณากรอกข้อมูลประเภทเงิน");
			frm.durable_money_type.focus();
			return false;
		}
	}
}