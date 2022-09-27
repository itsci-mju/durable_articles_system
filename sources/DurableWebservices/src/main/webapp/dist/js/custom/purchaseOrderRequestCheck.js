

$(document).ready(function() {
	$("button[id*='btn_edit']").click(function() {
		var text = $(this).attr('value').split(',');
		$('#editModal').modal('show');
		$('#position_edit').val(text[0]);
		$('#repair_detail_edit').val(text[1]);
		$('#repair_amount_edit').val(text[2]);
		$('#repair_unit_edit').val(text[3]);
		$('#repair_price_edit').val(text[4]);
	});
});

function purchaseOrderRequestCheck(frm) {

	if (frm.doc_id.value == null || frm.doc_id.value == "") {
		alert("กรุณากรอกเลขที่เอกสาร");
		frm.doc_id.focus();
		return false;
	}

	if (frm.doc_id.value.length > 50 || frm.doc_id.value.length <= 1) {
		alert("เลขที่เอกสารขอซื้อ/จ้าง ต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 50 ตัวอักษร");
		frm.doc_id.focus();
		return false;
	}


	if (frm.money_used.value == null || frm.money_used.value == "") {
		alert("กรุณากรอกข้อมูลการใช้เงิน");
		frm.money_used.focus();
		return false;
	}

	if (frm.budget.value == null || frm.budget.value == "") {
		alert("กรุณากรอกข้อมูลงบ");
		frm.budget.focus();
		return false;
	}


	if (frm.doc_title.value == null || frm.doc_title.value == "") {
		alert("กรุณากรอกข้อมูลเรื่อง");
		frm.doc_title.focus();
		return false;
	}

	var plan_name_check = /[A-Za-zก-๙]$/;
	if (frm.plan_name.value == null || frm.plan_name.value == "") {
		alert("กรุณากรอกข้อมูลแผนงาน");
		frm.plan_name.focus();
		return false;
	}
	if (!frm.plan_name.value.match(plan_name_check)) {
		alert("ข้อมูลแผนงานต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		frm.plan_name.focus();
		return false;
	}
	if (frm.plan_name.value.length < 2 || frm.plan_name.value.length > 255) {
		alert("ข้อมูลแผนงานต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		frm.plan_name.focus();
		return false;
	}

	var depart_name_check = /[A-Za-zก-๙]$/;
	if (frm.depart_name.value == null || frm.depart_name.value == "") {
		alert("กรุณากรอกข้อมูลหน่วยงาน");
		frm.depart_name.focus();
		return false;
	}
	if (!frm.depart_name.value.match(depart_name_check)) {
		alert("ข้อมูลหน่วยงานต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		frm.depart_name.focus();
		return false;
	}
	if (frm.depart_name.value.length < 2 || frm.depart_name.value.length > 255) {
		alert("ข้อมูลหน่วยงานต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		frm.depart_name.focus();
		return false;
	}

	var fund_name_check = /[A-Za-zก-๙]$/;
	if (frm.fund_name.value == null || frm.fund_name.value == "") {
		alert("กรุณากรอกข้อมูลกองทุน");
		frm.fund_name.focus();
		return false;
	}
	if (!frm.fund_name.value.match(fund_name_check)) {
		alert("ข้อมูลกองทุนต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		frm.fund_name.focus();
		return false;
	}
	if (frm.fund_name.value.length < 2 || frm.fund_name.value.length > 255) {
		alert("ข้อมูลกองทุนต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		frm.fund_name.focus();
		return false;
	}

	if (frm.work_name.value == null || frm.work_name.value == "") {
		alert("กรุณากรอกข้อมูลงาน");
		frm.work_name.focus();
		return false;
	}

	if (frm.date.value == null || frm.date.value == "") {
		alert("กรุณากรอกวันที่จัดทําเอกสาร");
		frm.date.focus();
		return false;
	}

	if (frm.doc_dear.value == null || frm.doc_dear.value == "") {
		alert("กรุณากรอกข้อมูลเรียน");
		frm.doc_dear.focus();
		return false;
	}

	if (frm.doc_title_describe.value == null || frm.doc_title_describe.value == "") {
		alert("กรุณากรอกข้อมูลรายละเอียด");
		frm.doc_title_describe.focus();
		return false;
	}

	if (frm.doc_title_describe.value.length < 2 || frm.doc_title_describe.value.length > 255) {
		alert("ข้อมูลรายละเอียดต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		frm.doc_title_describe.focus();
		return false;
	}


	if (frm.doc_reason_describe.value == null || frm.doc_reason_describe.value == "") {
		alert("กรุณากรอกข้อมูลเหตุผล/ความจําเป็น");
		frm.doc_reason_describe.focus();
		return false;
	}
	if (frm.doc_reason_describe.value.length < 2 || frm.doc_reason_describe.value.length > 255) {
		alert("ข้อมูลเหตุผล/ความจําเป็นต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		frm.doc_reason_describe.focus();
		return false;
	}

	if (frm.check_type.value == "" || frm.check_type.value == null) {
		alert("กรุณาเลือกรายการที่ต้องการจัดทํา");
		return false;
	}

	if (frm.check_type.value == "i") {
		var item_cart_check = document.getElementsByName("item_cart_check");
		if (item_cart_check.length == 0 || item_cart_check.length == null) {
			alert("กรุณาเลือกรายการวัสดุที่จะทําการเสนอซื้อ/จ้าง");
			return false;
		}

		var amount_check = /^[0-9]+$/;
		var item_price = document.getElementsByName("item_price");
		var item_total = document.getElementsByName("item_total");
		var i;
		for (i = 0; i < item_total.length; i++) {
			if (item_price[i].value == "" || item_price[i].value == null) {
				alert("กรุณากรอกราคาของวัสดุชิ้นที่ " + (i + 1));
				item_price[i].focus();
				return false;
			}

			if (item_total[i].value == "" || item_total[i].value == null) {
				alert("กรุณากรอกจํานวนของวัสดุชิ้นที่ " + (i + 1));
				item_total[i].focus();
				return false;
			} else if (item_total[i].value == "0") {
				alert("จํานวนของวัสดุชิ้นที่ " + (i + 1) + " ต้องไม่เป็น 0 ");
				item_total[i].focus();
				return false;
			}

			if (item_total[i].value.includes("-")) {
				alert("จํานวนของวัสดุชิ้นที่ " + (i + 1) + " ต้องไม่เป็นค่าติดลบ");
				item_total[i].focus();
				return false;
			}

			if (!item_total[i].value.match(amount_check)) {
				alert("กรุณากรอกจํานวนของวัสดุชิ้นที่ " + (i + 1) + " เป็นตัวเลขเท่านั้น");
				item_total[i].focus();
				return false;
			}
		}
		
	} else {
		var r = document.getElementsByName("r_check");
		if (r.length == 0 || r.length == null) {
			alert("กรุณาเสนอรายการเสนอซ่อม");
			return false;
		}
	}

	var committee = document.getElementsByName("committees_check");
	if (committee.length == 0 || committee.length == null) {
		alert("กรุณาเลือกกรรมการ");
		return false;
	}

	if (frm.doc_1_1.value == "-") {
		alert("กรุณาเลือกผู้เสนอซื้อ/จ้าง");
		frm.doc_1_1.focus();
		return false;
	}

	if (frm.doc_1_2.value == "-") {
		alert("กรุณาเลือกหัวหน้างานคลังและพัสดุ");
		frm.doc_1_2.focus();
		return false;
	}
	if (frm.doc_1_3.value == "-") {
		alert("กรุณาเลือกเจ้าหน้าที่ลงบัญชี");
		frm.doc_1_3.focus();
		return false;
	}

	if (frm.doc_1_4.value == "-") {
		alert("กรุณาเลือก ผู้เห็นชอบ/อนุมัติ เพื่อดําเนินการ");
		frm.doc_1_4.focus();
		return false;
	}
}

function check_repair(frm) {
	if (frm.repair_detail.value == "" || frm.repair_detail.value == null) {
		alert("กรุณากรอกรายละเอียดการซ่อม");
		frm.repair_detail.focus();
		return false;
	}

	var amount_check = /^[0-9]+$/;
	if (frm.repair_amount.value == "" || frm.repair_amount.value == null) {
		alert("กรุณากรอกจํานวน");
		frm.repair_amount.focus();
		return false;
	}

	if (!frm.repair_amount.value.match(amount_check)) {
		alert("กรุณากรอกจํานวนเป็นตัวเลขเท่านั้น");
		frm.repair_amount.focus();
		return false;
	}

	if (frm.repair_unit.value == "" || frm.repair_unit.value == null) {
		alert("กรุณากรอกหน่วย");
		frm.repair_unit.focus();
		return false;
	}

	var price_check = /^((\d+(\.\d*)?)|(\.\d+))$/;
	if (frm.repair_price.value == "" || frm.repair_price.value == null) {
		alert("กรุณากรอกราคา");
		frm.repair_price.focus();
		return false;
	}

	if (!frm.repair_price.value.match(price_check)) {
		alert("กรุณากรอกกรอกราคาเป็นตัวเลขเท่านั้น");
		frm.repair_price.focus();
		return false;
	}
}

function openAdd(check_type) {
	var i;
	var x = document.getElementsByClassName("tabcontent");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	document.getElementById(check_type).style.display = "block";
}
function checkType() {
	var v = document.getElementById("vats");
	var v_c = document.getElementById("item_vat");
	if (document.getElementById("1").checked == true
		&& document.getElementById("2").checked == false) {
		v.disabled = true;
		v.checked = false;
		v_c.checked = false;
		v_c.disabled = true;
	} else if (document.getElementById("1").checked == false
		&& document.getElementById("2").checked == true) {
		v.disabled = false;

	} else {
		v.disabled = true;
		v.checked = false;
	}
}

function openVatCheck() {
	var v = document.getElementById("vats");
	var v_c = document.getElementById("item_vat");
	if (v.checked == true) {
		v_c.disabled = false;
	} else {
		v_c.disabled = true;
		v_c.checked = false;
	}
}

function checkDupCommittee() {
	var committee = document.getElementsByClassName("c_id_dup_check");
	var i;
	var k;
	var committee_id = document.getElementById("committee");
	var price_check = /^((\d+(\.\d*)?)|(\.\d+))$/;

	var item_price = document.getElementsByName("item_price");
	var item_total = document.getElementsByName("item_total");

	if (committee_id.value != "-") {
		for (i = 0; i < committee.length; i++) {
			if (committee[i].value == committee_id.value) {
				alert("รายชื่อกรรมการต้องไม่ซํ้ากัน");
				return false;
			}
		}
	} else {
		alert("กรุณาเลือกรายชื่อกรรมการ");
		committee_id.focus();
		return false;
	}
	if (committee.length == 3) {
		alert("กรรมการต้องมีจํานวนไม่เกิน 3 คน");
		return false;
	}

}

function check_dup_item(io) {
	var item_id_cart = document
		.getElementsByClassName("item_check_dup");
	var i;
	var c = io;
	for (i = 0; i < item_id_cart.length; i++) {
		if (item_id_cart.value[i] == c) {
			alert("มีวัสดุชิ้นนี้อยู่ในรายการแล้ว");
			return false;
		}
	}
}
/**
 *
 */