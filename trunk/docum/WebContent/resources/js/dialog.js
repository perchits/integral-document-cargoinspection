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
