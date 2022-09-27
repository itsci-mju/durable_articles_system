function checkimport(frm){
	if(frm.filedurable.value != "" || frm.filedurable.value != null){
		if(frm.filedurable.value.split('.').pop().toLowerCase() != "xlxs"){
			alert("ไฟล์ที่ทําการอัพโหลดต้องเป็นไฟล์นามสกุล .xlxs เท่านั้น");
			return false;
		}
	}
	
	if(frm.filedurable.value == null ||frm.filedurable.value == "" ){
		alert("กรุณาอัพโหลดไฟล์");
		return false;
	}
}