

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
		return false;
	}

	if (frm.doc_id.value.length > 50 || frm.doc_id.value.length <= 1) { 
		alert("เลขที่เอกสารต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 50 ตัวอักษร");
		return false;
	}


	if (frm.money_used.value == null || frm.money_used.value == "") {
		alert("กรุณากรอกข้อมูลการใช้เงิน");
		return false;
	}

	if (frm.budget.value == null || frm.budget.value == "") {
		alert("กรุณากรอกข้อมูลงบ");
		return false;
	}


	if (frm.doc_title.value == null || frm.doc_title.value == "") {
		alert("กรุณากรอกข้อมูลเรื่อง");
		return false;
	}

	var plan_name_check = /[A-Za-zก-๙]$/;
	if (frm.plan_name.value == null || frm.plan_name.value == "") {
		alert("กรุณากรอกข้อมูลแผนงาน");
		return false;
	}
	if (!frm.plan_name.value.match(plan_name_check)) {
		alert("ข้อมูลแผนงานต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		return false;
	}
	if (frm.plan_name.value.length < 2 || frm.plan_name.value.length > 255) {
		alert("ข้อมูลแผนงานต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		return false;
	}

	var depart_name_check = /[A-Za-zก-๙]$/;
	if (frm.depart_name.value == null || frm.depart_name.value == "") {
		alert("กรุณากรอกข้อมูลหน่วยงาน");
		return false;
	}
	if (!frm.depart_name.value.match(depart_name_check)) {
		alert("ข้อมูลหน่วยงานต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		return false;
	}
	if (frm.depart_name.value.length < 2 || frm.depart_name.value.length > 255) {
		alert("ข้อมูลหน่วยงานต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		return false;
	}

	var fund_name_check = /[A-Za-zก-๙]$/;
	if (frm.fund_name.value == null || frm.fund_name.value == "") {
		alert("กรุณากรอกข้อมูลกองทุน");
		return false;
	}
	if (!frm.fund_name.value.match(fund_name_check))  {
		alert("ข้อมูลกองทุนต้องเป็นภาษาไทยหรืออังกฤษเท่านั้น");
		return false;
	}
	if (frm.fund_name.value.length < 2 || frm.fund_name.value.length > 255) {
		alert("ข้อมูลกองทุนต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		return false;
	}

	if (frm.work_name.value == null || frm.work_name.value == "") {
		alert("กรุณากรอกข้อมูลงาน");
		return false;
	}

	if (frm.date.value == null || frm.date.value == "") {
		alert("กรุณากรอกวันที่จัดทําเอกสาร");
		return false;
	}

	if (frm.doc_dear.value == null || frm.doc_dear.value == "") {
		alert("กรุณากรอกข้อมูลเรียน");
		return false;
	}

	if (frm.doc_title_describe.value == null || frm.doc_title_describe.value == "") {
		alert("กรุณากรอกข้อมูลรายละเอียด");
		return false;
	}
	
	if(frm.doc_title_describe.value.length < 2 || frm.doc_title_describe.value.length > 255) {
		alert("ข้อมูลรายละเอียดต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
		return false;
	}


	if (frm.doc_reason_describe.value == null || frm.doc_reason_describe.value == "") {
		alert("กรุณากรอกข้อมูลเหตุผล/ความจําเป็น");
		return false;
	}
	if(frm.doc_reason_describe.value.length < 2 || frm.doc_reason_describe.value.length > 255) {
		alert("ข้อมูลเหตุผล/ความจําเป็นต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 255 ตัวอักษร");
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

		var amount_check = /\d/;
		var item_total = document.getElementsByName("item_total");
		var i;
		for (i = 0; i < item_total.length; i++) {
			if (item_total[i].value == "" || item_total[i].value == null) {
				alert("กรุณากรอกจํานวนของวัสดุชิ้นที่ " + (i + 1));
				return false;
			} else if (item_total[i].value == "0") {
				alert("จํานวนของวัสดุชิ้นที่ " + (i + 1) + " ต้องไม่เป็น 0 ");
				item_total[i].value == "";
				return false;
			}

			if (!item_total[i].value.match(amount_check)) {
				alert("กรุณากรอกจํานวนเป็นตัวเลขเท่านั้น");
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

	if (frm.requisition_old.value == "-") {
		alert("กรุณาเลือกผู้เสนอซื้อ/จ้าง");
		return false;
	}

	if (frm.acc_old.value == "-") {
		alert("กรุณาเลือกเจ้าหน้าที่ลงบัญชี");
		return false;
	}

	if (frm.old_secretary.value == "-") {
		alert("กรุณาเลือก ผู้เห็นชอบ/อนุมัติ เพื่อดําเนินการ");
		return false;
	}
}

function check_repair(frm) {
	if (frm.repair_detail.value == "" || frm.repair_detail.value == null) {
		alert("กรุณากรอกรายละเอียดการซ่อม");
		return false;
	}

	var amount_check = /\d/;
	if (frm.repair_amount.value == "" || frm.repair_amount.value == null) {
		alert("กรุณากรอกจํานวน");
		return false;
	}

	if (!frm.repair_amount.value.match(amount_check)) {
		alert("กรุณากรอกจํานวนเป็นตัวเลขเท่านั้น");
		return false;
	}

	if (frm.repair_unit.value == "" || frm.repair_unit.value == null) {
		alert("กรุณากรอกหน่วย");
		return false;
	}

	var price_check = /^((\d+(\.\d*)?)|(\.\d+))$/;
	if (frm.repair_price.value == "" || frm.repair_price.value == null) {
		alert("กรุณากรอกราคา");
		return false;
	}

	if (!frm.repair_price.value.match(price_check)) {
		alert("กรุณากรอกกรอกราคาเป็นตัวเลขเท่านั้น");
		return false;
	}
}
/**
 * 
 */