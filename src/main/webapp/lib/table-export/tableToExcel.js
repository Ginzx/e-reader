function getXlsFromTbl(inTblId,fileName,inWindow) {
	try {
		var allStr = "";
		var curStr = "";
		// alert("getXlsFromTbl");
		if (inTblId != null && inTblId != "" && inTblId != "null") {
			curStr = getTblData(inTblId, inWindow);
		}
		if (curStr != null) {
			allStr += curStr;
		} else {
			alert("你要导出的表不存在！");
			return;
		}
		doFileExport(fileName+".xls", allStr);
	} catch (e) {
		alert("导出发生异常:" + e.name + "->" + e.description + "!");
	}
}
function getTblData(inTbl, inWindow) {
	var rows = 0;
	// alert("getTblData is " + inWindow);
	var tblDocument = document;
	if (!!inWindow && inWindow != "") {
		if (!document.all(inWindow)) {
			return null;
		} else {
			tblDocument = eval(inWindow).document;
		}
	}
	var curTbl = tblDocument.getElementById(inTbl);
	var outStr = "";
	if (curTbl != null) {
		for (var j = 0; j < curTbl.rows.length; j++) {
			for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
				if (i == 0 && rows > 0) {
					outStr += " \t";
					rows -= 1;
				}
				outStr += curTbl.rows[j].cells[i].innerText + "\t";
				if (curTbl.rows[j].cells[i].colSpan > 1) {
					for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
						outStr += " \t";
					}
				}
				if (i == 0) {
					if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
						rows = curTbl.rows[j].cells[i].rowSpan - 1;
					}
				}
			}
			outStr += "\r\n";
		}
	} else {
		outStr = null;
		alert(inTbl + "不存在!");
	}
	return outStr;
}

function doFileExport(inName, inStr) {
	var xlsWin = null;
	if (!!document.all("glbHideFrm")) {
		xlsWin = glbHideFrm;
	} else {
		var width = 6;
		var height = 4;
		var openPara = "left=" + (window.screen.width / 2 - width / 2)
				+ ",top=" + (window.screen.height / 2 - height / 2)
				+ ",scrollbars=no,width=" + width + ",height=" + height;
		xlsWin = window.open("", "_blank", openPara);
	}
	xlsWin.document.write(inStr);
	xlsWin.document.close();
	xlsWin.document.execCommand('Saveas', true, inName);
	xlsWin.close();
}