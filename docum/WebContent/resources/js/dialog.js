function handleOKRequest(args, widgetVar, dialogId) {
	if (args.validationFailed || args.dontClose) {
		jQuery('#' + dialogId).parent().effect("shake", {
			times : 3
		}, 100);
	} else {
		widgetVar.hide();
	}
}

function beforeShow(widgetVar, args) {
	if (!args.dontShow) {
		widgetVar.show();	
	}	
}

function openWindow(args) {
	if (!args.dontShow) {
		window.open('/docum/reporting/showReport.jsf', 'reportWindow', 'width=1024,height=768');	
	}	
}

function setHeader(msg) {	  
	jQuery('.basic-dialog .ui-dialog-title').html(msg);
}

function checkIfArticleList(articleListPanelId) {
	var result=document.getElementById('featureIsList').checked;
	if (result == true) {
		document.getElementById(articleListPanelId).style.display = "block";
	} else {
		document.getElementById(articleListPanelId).style.display = "none";
	}
	document.getElementById('featuresGridDlgPanel').style.display = "none";
	document.getElementById('featuresGridDlgPanel').style.display = "block";
}
