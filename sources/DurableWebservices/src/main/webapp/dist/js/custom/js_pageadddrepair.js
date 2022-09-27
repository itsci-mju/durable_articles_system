$(document)
		.ready(
				function() {
					$('#btn_addrepair')
							.click(
									function() {
										var charges = new RegExp('^[0-9]+$');
										var datepicker = new RegExp('^((0?[1-9]|(1|2)?[0-9]{1}|3[01])[- /](([ก-๙]?[ก-๙]*|[a-zA-Z]?[a-zA-Z]*|(0?[1-9]|1[012])*))[- /](25|26)?[0-9]{2})+$');
										var du_code = new RegExp(
												'^[.\u0E01-\u0E5B0-9/-]+$');
										var titlename = new RegExp(
										'^[a-zA-Z0-9ก-๙ ]+$');
										var company = new RegExp(
										'^[a-zA-Z0-9ก-๙ ]+$');
										var pic = new RegExp(
												'^(([jJ][pP][gG])|([pP][nN][gG])|([gG][iI][fF])|([bB][mM][pP])|([jJ][pP][eE][gG]))$');

										if (!du_code.test($('#durablecode').val())) {
											alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้อง");
											return false;
										} else if (!titlename.test($('#titlename').val())) {
											alert("กรุณาระบุหัวข้อในการส่งซ่อมให้ถูกต้อง");
											return false;
										} else if (!datepicker.test($('#datepicker').val() )){
											alert("กรุณาระบุวันที่ในการส่งซ่อมให้ถูกต้อง");
											return false;
										}else if ($('#detail').val() == "") {
											alert("กรุณาระบุรายละเอียด");
											return false;
										} else if  (!company.test($('#company').val())){
											alert("กรุณาระบุบริษัทผู้รับผิดชอบให้ถูกต้อง");
											return false;
										} else if (!charges.test($('#charges').val())){
												alert("ระบุข้อมูลได้เฉพาะตัวเลขเท่านั้น");
												return false;
											
										} else if (!$('#pic_repairreport').is(
												":disabled")) {
											var fileExtension = $(
													'#pic_repairreport').val()
													.split(".");
											if (!pic.test(fileExtension)) {
												alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
												return false;
											}
										} else if (!$('#pic_quatation').is(
												":disabled")) {
											var fileExtension = $(
													'#pic_quatation').val()
													.split(".");
											if (!pic.test(fileExtension)) {
												alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
												return false;
											}
										} else if (!$('#pic_invoice').is(
												":disabled")) {
											var fileExtension = $(
													'#pic_invoice').val()
													.split(".");
											if (!pic.test(fileExtension)) {
												alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
												return false;
											}
										} else {
											return true;
										}
									});
				});
$(document)
.ready(
		function() {
			$('#btn_editrepair')
					.click(
							function() {
								var charges = new RegExp('^[0-9]+$');
								var datepicker = new RegExp('^((0?[1-9]|(1|2)?[0-9]{1}|3[01])[- /](([ก-๙]?[ก-๙]*|[a-zA-Z]?[a-zA-Z]*|(0?[1-9]|1[012])*))[- /](25|26)?[0-9]{2})+$');
								var du_code = new RegExp(
										'^[.\u0E01-\u0E5B0-9/-]+$');
								var titlename = new RegExp(
								'^[a-zA-Z0-9ก-๙ ]+$');
								var company = new RegExp(
								'^[a-zA-Z0-9ก-๙ ]+$');
								var pic = new RegExp(
										'^(([jJ][pP][gG])|([pP][nN][gG])|([gG][iI][fF])|([bB][mM][pP])|([jJ][pP][eE][gG]))$');

								if (!du_code.test($('#durablecode').val())) {
									alert("กรุณากรอกรหัสครุภัณฑ์ให้ถูกต้อง");
									return false;
								} else if (!titlename.test($('#titlename').val())) {
									alert("กรุณาระบุหัวข้อในการส่งซ่อมให้ถูกต้อง");
									return false;
								} else if (!datepicker.test($('#datepicker').val() )){
									alert("กรุณาระบุวันที่ในการส่งซ่อมให้ถูกต้อง");
									return false;
								}else if ($('#detail').val() == "") {
									alert("กรุณาระบุรายละเอียด");
									return false;
								} else if  (!company.test($('#company').val())){
									alert("กรุณาระบุบริษัทผู้รับผิดชอบให้ถูกต้อง");
									return false;
								} else if (!charges.test($('#charges').val())){
										alert("ระบุข้อมูลได้เฉพาะตัวเลขเท่านั้น");
										return false;
									
								} else if (!$('#pic_repairreport').is(
										":disabled")) {
									var fileExtension = $(
											'#pic_repairreport').val()
											.split(".");
									if (!pic.test(fileExtension)) {
										alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
										return false;
									}
								} else if (!$('#pic_quatation').is(
										":disabled")) {
									var fileExtension = $(
											'#pic_quatation').val()
											.split(".");
									if (!pic.test(fileExtension)) {
										alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
										return false;
									}
								} else if (!$('#pic_invoice').is(
										":disabled")) {
									var fileExtension = $(
											'#pic_invoice').val()
											.split(".");
									if (!pic.test(fileExtension)) {
										alert("กรุณาเลือกไฟล์ให้ถูกต้อง");
										return false;
									}
								} else {
									return true;
								}
							});
		});