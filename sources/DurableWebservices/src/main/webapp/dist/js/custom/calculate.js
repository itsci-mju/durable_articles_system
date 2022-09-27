function ThaiNumberToText(Number) {
	Number = Number.replace(/๐/gi, '0');
	Number = Number.replace(/๑/gi, '1');
	Number = Number.replace(/๒/gi, '2');
	Number = Number.replace(/๓/gi, '3');
	Number = Number.replace(/๔/gi, '4');
	Number = Number.replace(/๕/gi, '5');
	Number = Number.replace(/๖/gi, '6');
	Number = Number.replace(/๗/gi, '7');
	Number = Number.replace(/๘/gi, '8');
	Number = Number.replace(/๙/gi, '9');
	return ArabicNumberToText(Number);
}

function ArabicNumberToText(Number) {
	var Number = CheckNumber(Number);
	var NumberArray = new Array("ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า", "สิบ");
	var DigitArray = new Array("", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", "ล้าน");
	var BahtText = "";
	if (isNaN(Number)) {
		return "ข้อมูลนำเข้าไม่ถูกต้อง";
	} else {
		if ((Number - 0) > 9999999.9999) {
			return "ข้อมูลนำเข้าเกินขอบเขตที่ตั้งไว้";
		} else {
			Number = Number.split(".");
			if (Number[1].length > 0) {
				Number[1] = Number[1].substring(0, 2);
			}
			var NumberLen = Number[0].length - 0;
			for (var i = 0; i < NumberLen; i++) {
				var tmp = Number[0].substring(i, i + 1) - 0;
				if (tmp != 0) {
					if ((i == (NumberLen - 1)) && (tmp == 1)) {
						BahtText += "เอ็ด";
					} else
						if ((i == (NumberLen - 2)) && (tmp == 2)) {
							BahtText += "ยี่";
						} else
							if ((i == (NumberLen - 2)) && (tmp == 1)) {
								BahtText += "";
							} else {
								BahtText += NumberArray[tmp];
							}
					BahtText += DigitArray[NumberLen - i - 1];
				}
			}
			BahtText += "บาท";
			if ((Number[1] == "0") || (Number[1] == "00")) {
				BahtText += "ถ้วน";
			} else {
				DecimalLen = Number[1].length - 0;
				for (var i = 0; i < DecimalLen; i++) {
					var tmp = Number[1].substring(i, i + 1) - 0;
					if (tmp != 0) {
						if ((i == (DecimalLen - 1)) && (tmp == 1)) {
							BahtText += "เอ็ด";
						} else
							if ((i == (DecimalLen - 2)) && (tmp == 2)) {
								BahtText += "ยี่";
							} else
								if ((i == (DecimalLen - 2)) && (tmp == 1)) {
									BahtText += "";
								} else {
									BahtText += NumberArray[tmp];
								}
						BahtText += DigitArray[DecimalLen - i - 1];
					}
				}
				BahtText += "สตางค์";
			}
			return BahtText;
		}
	}
}

function CheckNumber(Number) {
	var decimal = false;
	Number = Number.toString();
	Number = Number.replace(/ |,|บาท|฿/gi, '');
	for (var i = 0; i < Number.length; i++) {
		if (Number[i] == '.') {
			decimal = true;
		}
	}
	if (decimal == false) {
		Number = Number + '.00';
	}
	return Number
}

function repair_price_txt(price) {
	var thaibaths = ArabicNumberToText(price);
	document.getElementById("price_txt_repair").value = thaibaths;
}

function caltotal() {
	var sum = 0;
	var sum_t = 0;
	var amount, price, total, grand, totals;
	amount = document.getElementsByClassName("item_total");
	total = document.getElementsByClassName("item_total_price");
	price = document.getElementsByClassName("item_price");
	grand = document.getElementById("grand_total");
	totals = document.getElementsByClassName("item_total_price_l");
	for (var i = 0; i < amount.length; i++) {
		if (amount[i] != null) {
			totals[i].innerHTML = amount[i].value * price[i].value;
			total[i].value = amount[i].value * price[i].value;
			sum += amount[i].value * price[i].value;
		}
	}
	if (grand != null) {
		grand.innerHTML = sum;
	}
	var thaibath = ArabicNumberToText(sum);
	if (document.getElementById("price_txt") != null) {
		document.getElementById("price_txt").value = thaibath;
	}
}

