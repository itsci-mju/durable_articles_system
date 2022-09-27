function checkAssign(frm){
	if(frm.assing_id.value == "-" || frm.assing_id.value == ""){
		alert("กรุณาเลือกหน้าที่การเซ็นเอกสาร");
		frm.assing_id.focus();
		return false;
	}
	
	if(frm.personnel_id.value == "-" ||  frm.personnel_id.value == ""){
		alert("กรุณาเลือกรายชื่อ");
		frm.personnel_id.focus();
		return false;
	}
}