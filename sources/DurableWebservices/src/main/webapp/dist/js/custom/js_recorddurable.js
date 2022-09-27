$(document)
		.ready(
				function() {
					$('#btn_addrepair')
							.click(
									function() {
										var charges = new RegExp('^[0-9]+$');
										var numdurable = new RegExp('^[0-9/]+$');
										var namedurable = new RegExp('^[ก-๙a-zA-Z0-9-/., ]+$');
										var brand = new RegExp('^[ก-๙a-zA-Z0-9-/., ]+$');
										var du_code = new RegExp(
												'^[.\u0E01-\u0E5B0-9/-]+$');
										var error_char = new RegExp('^[0-9]+$');
										var term = new RegExp('^(?:(?:25[5-9][0-9]))$');
										var datepicker = new RegExp('^((0?[1-9]|(1|2)?[0-9]{1}|3[01])[- /](([ก-๙]?[ก-๙]*|[a-zA-Z]?[a-zA-Z]*|(0?[1-9]|1[012])*))[- /](25|26)?[0-9]{2})+$'); 
										if (!du_code.test($('#durablecode').val())) {
											alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้อง");
											return false;
										}else if (!numdurable.test($('#numdurable').val())) {
											alert("กรุณาระบุจำนวนครุภัณฑ์");
											return false;
										}else if(!namedurable.test($('#namedurable').val())) {
											alert("กรุณาระบุชื่อครุภัณฑ์");
											return false;
										}else if (!brand.test($('#brand').val())) {
											alert("กรุณาระบุยี่ห้อ");
											return false;
										}else if (!charges.test($('#pricedurable')
												.val())) {
												alert("ระบุราคาต่อหน่วยห้ถูกต้อง");
												return false;	
										}else if (!datepicker.test($('#datepicker').val())) {
											alert("กรุณาระบุวันที่ในการส่งซ่อม");
											return false;
										}else if ($('#room_id').val() == "-") {
											alert("กรุณาระบุห้อง");
											return false;
										}else if ($('#status').val() == "-") {
											alert("กรุณาสถาพครุภัณฑ์");
											return false;
										}else if ($('#owner_id').val() == "-") {
											alert("กรุณาระบุเจ้าของ");
											return false;
										}else if ($('#termyear').val() == "" || !term.test($('#termyear')
												.val())) {
											alert("ระบุปีการศึกษาให้ถูกต้อง");
											return false;	
										} else {
											return true;
										}
										 
										
									});
				});