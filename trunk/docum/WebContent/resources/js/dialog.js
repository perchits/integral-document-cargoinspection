function handleOKRequest(args, widgetVar, dialogId) {
	if (args.validationFailed) {
		jQuery('#' + dialogId).parent().effect("shake", {
			times : 3
		}, 100);
	} else {
		widgetVar.hide();
	}
}

function beforeShow(widgetVar, tableId) {
	if (jQuery('#' + tableId).find(".ui-selected").length > 0) {
		widgetVar.show();
	}
}
