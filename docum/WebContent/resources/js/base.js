function togglePanels(f) {
	if (f) {
		jQuery(".ui-icon-plusthick").click();
		jQuery(".ui-icon-circle-triangle-e").click();
	} else {
		jQuery(".ui-icon-minusthick").click();
		jQuery(".ui-icon-circle-triangle-e").click();
	}
}

/*jQuery(document).ready(
		function() {			
			jQuery("select").addClass(
					"ui-inputfield ui-widget ui-state-default ui-corner-all");
		});
*/