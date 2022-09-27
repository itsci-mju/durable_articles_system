$(document)
		.ready(
				function() {
					$("#btn_borrow")
							.click(
									function() {

										var du_code = new RegExp(
												'^[.\u0E01-\u0E5B0-9/-]+$');
										var id_card = new RegExp('^[0-9]{13}$');
										var stu_tel = new RegExp('^0[0-9]{9}$');
										var datepicker = new RegExp('^((0?[1-9]|(1|2)?[0-9]{1}|3[01])[- /](([ก-๙]?[ก-๙]*|[a-zA-Z]?[a-zA-Z]*|(0?[1-9]|1[012])*))[- /](25|26)?[0-9]{2})+$');		
										var borrowname = new RegExp('^[ก-๙a-zA-Z0-9 ]+$');
										var file = new RegExp(
												'^(([jJ][pP][gG])|([pP][nN][gG])|([gG][iI][fF])|([bB][mM][pP])|([jJ][pP][eE][gG]))$');

										var file3 = $("#borrowpicture").val();
										var fileExtension = file3.split(".");

										if (!du_code.test($('#durablecode')
												.val())) {
											alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้อง");
											return false;
										} else if(!datepicker.test($('#datepicker').val())) {
											alert("กรุณากรอกวันที่ยืม");
											return false;
										}else if(!datepicker.test($('#datepicker2').val())) {
											alert("กรุณากรอกวันที่คืน");
											return false;
										} else if (!id_card.test($(
												'#idcard').val())) {
											alert("กรุณากรอกข้อมูลรหัสนักศึกษาให้ถูกต้อง");
											return false;
										} else if (!borrowname.test($('#borrowname').val())) {
											alert("กรุณากรอกข้อมูลชื่อผู้ยืม");
											return false;
										} else if (!stu_tel
												.test($('#borrowtel').val())) {
											alert("กรุณากรอกเบอร์โทรศัพท์ให้ถูกต้อง");
											return false;
										} else if (!file3.equls("") && !file.test(fileExtension[1])) {
											alert("กรุณากรอกข้อมูลรูปภาพให้ถูกต้อง");
											return false;
										}
									});

					return true;

				});