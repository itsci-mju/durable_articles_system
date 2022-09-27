$(document).ready(function() {
					$('#search').click(function() {	
										var du_code = new RegExp(
												'^[.\u0E01-\u0E5B0-9/-]+$');
										
										if (!du_code.test($('#durablecode').val())) {
											alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้อง");
											return false;
										} else {
											return true;
										}
									});
				});