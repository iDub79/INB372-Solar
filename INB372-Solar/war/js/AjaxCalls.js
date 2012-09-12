function calculateInput() {
	$.ajax({
        type : "POST",
        url : "solarServlet",
        data : "panelSize=" + panelSize + "&panelEfficiency=" + panelEfficiency + "&inverterEfficiency=" + inverterEfficiency +
        	   "&orientation=" + orientation + "&angle=" + angle + "&sunlight=" + sunlight + "&consumption=" + consumption +
        	   "&address=" + address + "&tariff=" + tariff,
	    async: false,
        success : displayResult
    });
}


function addNewPanel() {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=addPanel&manufacturer=" + manufacturer.toUpperCase() + "&model=" + model.toUpperCase() + "&power=" + power +
        	   "&newPanelLength=" + newPanelLength + "&newPanelWidth=" + newPanelWidth,
	    async: false,
        success : displayPanelResult
    });
}


function getPanelList() {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=getPanels",
	    async: false,
        success : displayPanelResult
    });
}


function displayResult(result, status) {
	$("#pnlResults").removeClass("alert-error").addClass("alert-success");
	
	if (status == 'success') {
		if (result.Savings.Success == true) {
			var amountSaved = result.Savings.Amount;
			
			if (amountSaved > 0) {
				amountSavedNum = parseFloat(amountSaved);
				try {
					amountSavedNum = amountSavedNum.toFixed(2);
					$("#lblSavings").html("Based on your input, the annual savings will be <strong>$" + amountSavedNum + "</strong>");
					$("#pnlResults").show();
				}
				catch (Error) {
					displayError("There was an error in calculating the fields"), "#lblSavings";
				}
			}
			else {
				displayError("The value calculated is below $0 which means that you are consuming more energy than what you're putting back into the grid.", "#lblSavings");
			}
		}
		else {
			displayError("There was an error in calculating the fields", "#lblSavings");
		}				
	}
}


function displayPanelResult(result, status) {
	$("#pnlResults").removeClass("alert-error").addClass("alert-success");
	
	if (status == 'success') {
		if (result.Success == true) {
			var output = "<table class='table table-hover'><tr><th>Manufacturer</th><th>Model</th><th>Power</th></tr>";
			
			$.each(result.Panels, function (i) {
				output += "<tr><td>" + result.Panels[i].manufacturer + "</td><td>" + result.Panels[i].model + "</td><td>" + result.Panels[i].power + "</td></tr>";	        
		    });
			
			output += "</table>";
			
			$("#lblPanel").html(output);
			$("#pnlResults").show();
		}
		else if (result.Success == false) {
			displayError("There was an error trying to add the new panel to the database.", "#lblPanel");
		}
		else if (result.Success == "empty") {
			
		}
	}
	else {
		displayError("There was an error trying to add the new panel to the database.", "#lblPanel");
	}				
}


function displayError(message, control) {
	$(control).html(message);
	$("#pnlResults").removeClass("alert-success").addClass("alert-error").show();
}


