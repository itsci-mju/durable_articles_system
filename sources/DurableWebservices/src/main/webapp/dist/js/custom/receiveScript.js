function checkReceive(frm) {
	if (frm.receiveOrder_id.value == null || frm.receiveOrder_id.value == "")  {
		alert("กรุณากรอกเลขที่เอกสาร");
		frm.receiveOrder_id.focus();
		return false;
	}

	if (frm.receiveOrder_id.value.length < 2 || frm.receiveOrder_id.value.length > 50) {
		alert("เลขที่เอกสารต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 50 ตัวอักษร");
		frm.receiveOrder_id.focus();
		return false;
	}

	if (frm.date.value == "" || frm.date.value == null) {
		alert("กรุณากรอกวันที่");
		frm.date.focus();
		return false;
	}

	if (frm.receiveOrderDocument_from.value == null || frm.receiveOrderDocument_from.value == "") {
		alert("กรุณากรอกที่รับของมาจาก");
		frm.receiveOrderDocument_from.focus();
		return false;
	}

	if (frm.receiveOrderDocument_from.length < 2 || frm.receiveOrderDocument_from.value.length > 100) {
		alert("ที่รับของมาจากต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 100 ตัวอักษร");
		frm.receiveOrderDocument_from.focus();
		return false;
	}

	if (frm.invoice_number.value == null || frm.invoice_number.value == "") {
		alert("กรุณากรอกใบกํากับภาษี");
		frm.invoice_number.focus();
		return false;
	}

	if (frm.invoice_number.length < 2 || frm.invoice_number.value.length > 100) {
		alert("ใบกํากับภาษีต้องมีีความยาวตั้งแต่ 2 ตัวอักษร และไม่เกิน 100 ตัวอักษร");
		frm.invoice_number.focus();
		return false;
	}

	var invoice_amount = document.getElementsByName("invoice_amount");
	var n_check = /^[0-9]+$/;
	for (var i = 0; i < invoice_amount.length; i++) {
		if (invoice_amount[i].value == null || invoice_amount[i].value == "") {
			alert("กรุณากรอกจํานวนตามใบส่งของ ของวัสดุลําดับที่" + (i + 1));
			invoice_amount[i].focus();
			return false;
		}
		if (invoice_amount[i].value == "0") {
			alert("จํานวนตามใบส่งของ ของวัสดุชิ้นที่ " + (i + 1) + " ต้องไม่เป็น 0");
			invoice_amount[i].focus();
			return false;
		}
		if (invoice_amount[i].value.includes("-")) {
				alert("จํานวนตามใบส่งของ ของวัสดุชิ้นที่ "+ (i+1)+ " ต้องไม่เป็นค่าติดลบ");
				invoice_amount[i].focus();
				return false;
		}
		
		if (!invoice_amount[i].value.match(n_check)) {
			alert("กรุณากรอกจํานวนตามใบส่งของ ของวัสดุชิ้นที่ " + (i + 1) + " เป็นตัวเลขเท่านั้น");
			invoice_amount[i].focus();
			return false;
		}
	}
	
	var received_amount = document.getElementsByName("received_amount");
	for (var i = 0; i < received_amount.length; i++) {
		if (received_amount[i].value == null || received_amount[i].value == "") {
			alert("กรุณากรอกจํานวนตามที่รับจริง ของวัสดุลําดับที่" + (i + 1));
			received_amount[i].focus();
			return false;
		}
		if (received_amount[i].value == "0") {
			alert("จํานวนตามที่รับจริง ของวัสดุชิ้นที่ " + (i + 1) + " ต้องไม่เป็น 0");
			received_amount[i].focus();
			return false;
		}
		if (received_amount[i].value.includes("-")) {
				alert("จํานวนตามที่รับจริง ของวัสดุชิ้นที่ "+ (i+1)+ " ต้องไม่เป็นค่าติดลบ");
				received_amount[i].focus();
				return false;
		}
		if (!received_amount[i].value.match(n_check)) {
			alert("กรุณากรอกจํานวนตามที่รับจริง ของวัสดุชิ้นที่ " + (i + 1) + " เป็นตัวเลขเท่านั้น");
			received_amount[i].focus();
			return false;
		}
	}
	
	if(frm.doc_3_1.value == "-"){
		alert("กรุณาเลือกรายเซ็นคนที่ 1");
		frm.doc_3_1.focus();
		return false;
	}
	
	if(frm.doc_3_2.value == "-"){
		alert("กรุณาเลือกรายเซ็นผู้ทราบอนุมัติและเบิกจ่าย");
		frm.doc_3_2.focus();
		return false;
	}
}