function checkBill(frm){
	if(frm.billOfLading_id.value == "" || frm.billOfLading_id.value == null){
		alert("กรุณากรอกเลขที่เอกสารใบเบิกวัสดุ");
		frm.billOfLading_id.focus();
		return false;
	}
	if(frm.billOfLading_id.value.length < 2 || frm.billOfLading_id.value.length > 255){
		alert("เลขที่เอกสารใบเบิกวัสดุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 255 ตัวอักษร");
		frm.billOfLading_id.focus();
		return false;
	}
	
	if(frm.doc_4_1.value == "-"){
		alert("กรุณาเลือกรายเซ็นผู้อนุญาติให้จ่าย");
		frm.doc_4_1.focus();
		return false;
	}
	
	if(frm.doc_4_2.value == "-"){
		alert("กรุณาเลือกรายเซ็นผู้เบิก");
		frm.doc_4_2.focus();
		return false;
	}
	
	if(frm.doc_4_3.value == "-"){
		alert("กรุณาเลือกรายเซ็นผู้รับของ");
		frm.doc_4_3.focus();
		return false;
	}
	
	if(frm.doc_4_4.value == "-"){
		alert("กรุณาเลือกรายเซ็นผู้จ่ายของ");
		frm.doc_4_4.focus();
		return false;
	}
	
	if(frm.doc_4_5.value == "-"){
		alert("กรุณาเลือกผู้บันทึกลงบัญชีวัสดุ");
		frm.doc_4_5.focus();
		return false;
	}
	
}