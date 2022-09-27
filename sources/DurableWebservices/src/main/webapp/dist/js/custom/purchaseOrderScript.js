function trimfield(str) 
{ 
    return str.replace(/^\s+|\s+$/g,''); 
}

function checkPurchase(frm) {
	if (frm.purchaseOrder_id.value == null || frm.purchaseOrder_id.value == "") {
		alert("กรุณากรอกเลขที่เอกสาร");
		frm.purchaseOrder_id.focus();
		return false;
	}

	if (frm.purchaseOrder_id.value.length < 2 || frm.purchaseOrder_id.value.length > 50) {
		alert("เลขที่เอกสารต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 50 ตัวอักษร");
		frm.purchaseOrder_id.focus();
		return false;
	}
	var orderType_check = /[A-Za-zก-๙/\s]$/;
	if (frm.purchase_orderType.value == null || frm.purchase_orderType.value == "") {
		alert("กรุณากรอกประเภทการจัดซื้อจัดจ้าง");
		frm.purchase_orderType.focus();
		return false;
	}

	if (!frm.purchase_orderType.value.match(orderType_check)) {
		alert("ประเภทการจัดซื้อจัดจ้างต้องเป็นตัวอักษรภาษาไทยหรือภาษาอังกฤษเท่านั้น");
		frm.purchase_orderType.focus();
		return false;
	}

	if (frm.purchase_orderType.value.length < 2 || frm.purchase_orderType.value.length > 100) {
		alert("ประเภทการจัดซื้อ/จ้างต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 100 ตัวอักษร");
		frm.purchase_orderType.focus();
		return false;
	}
	
	if(frm.doc_2_1.value == "-"){
		alert("กรุณาเลือกรายชื่ผู้จัดทําเอกสาร");
		frm.doc_2_1.focus();
		return false;
	}
	if (frm.doc_2_2.value == "-") {
		alert("กรุณาเลือกผู้จัดซื้อจัดจ้าง");
		frm.doc_2_2.focus();
		return false;
	}
	if (frm.doc_2_3.value == "-") {
		alert("กรุณาเลือกผู้ตรวจสอบบัญชีงบประมาณอนุมัติ");
		frm.doc_2_3.focus();
		return false;
	}
	
	if (frm.doc_2_4.value == "-") {
		alert("กรุณาเลือกหัวหน้างานคลังและพัสดุ");
		frm.doc_2_4.focus();
		return false;
	}
	
	if (frm.doc_2_5.value == "-") {
		alert("กรุณาเลือกผู้เห็นชอบ/อนุมัติ");
		frm.doc_2_5.focus();
		return false;
	}

	if (frm.doc_2_6.value == "-") {
		alert("กรุณาเลือกคณบดีคณะวิทยาศาสตร์");
		frm.doc_2_6.focus();
		return false;
	}
	
	if(frm.date.value == "" || frm.date.value == null){
		alert("กรุณาเลือกวันที่");
		frm.date.focus();
		return false;
	}
	
	var note = document.getElementsByName("note");
	var note_check = /[A-Za-zก-๙/\s]$/;
	for(var i=0; i<note.length; i++){
		if(note[i].value != ""){
			if(!note[i].value.match(note_check)){
				alert("ข้อมูลหมายเหตุต้องเป็นตัวอักษรภาษาไทยหรืออังกฤษเท่านั้น");
				note[i].focus();
				return false;
			}
			if(note[i].value.length < 2 || note[i].value.length > 50){
				alert("ข้อมูลหมายเหตุต้องมีความยาวตั้งแต่ 2 ตัวอักษร และต้องไม่เกิน 50 ตัวอักษร");
				note[i].focus();
				return false;
			}
		}
	}
}