$(function() {

	
});


function calculatePanelSize() {
	panelSize = (((panelLength / 1000) * (panelWidth / 1000)) * panelQty) ;
}


function clearValidationMessages() {
	$("#grpPanelLength, #grpPanelWidth, #grpPanelQty, #grpPanelEfficiency, #grpInverterEfficiency," +
			"#grpPanelOrientation, #grpPanelAngle, #grpPowerConsumption, #grpAddress, #grpDailySunlight," +
			"#grpTariff, #grpPanelManufacturer, #grpPanelModel, #grpPanelPower").removeClass("error");
	$("#pnlErrors, #pnlResults").hide();
}


function validForm() {	
	var validForm = true;
	
	if (invalidNumberField(panelLength)) {
		$("#grpPanelLength").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(panelWidth)) {
		$("#grpPanelWidth").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(panelQty)) {
		$("#grpPanelQty").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(panelEfficiency)) {
		$("#grpPanelEfficiency").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(inverterEfficiency)) {
		$("#grpInverterEfficiency").addClass("error");
		validForm = false;
	}		
	if (orientation == -1) {
		$("#grpPanelOrientation").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(angle)) {
		$("#grpPanelAngle").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(sunlight)) {
		$("#grpDailySunlight").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(consumption)) {
		$("#grpPowerConsumption").addClass("error");
		validForm = false;
	}
	if (invalidAlphaNumericField(address)) {
		$("#grpAddress").addClass("error");
		validForm = false;
	}
	if (tariff == -1) {
		$("#grpTariff").addClass("error");
		validForm = false;
	}
	
	return validForm;
}


function validAddPanel() {
	validForm = true;
	
	if (invalidAlphaNumericField(manufacturer)) {
		$("#grpPanelManufacturer").addClass("error");
		validForm = false;
	}
	if (invalidAlphaNumericField(model)) {
		$("#grpPanelModel").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(power)) {
		$("#grpPanelPower").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(newPanelLength)) {
		$("#grpPanelLength").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(newPanelWidth)) {
		$("#grpPanelWidth").addClass("error");
		validForm = false;
	}
	
	return validForm;
}


function invalidNumberField(field) {
	return ((field == "") || (isNaN(field)) || (typeof field === "undefined") || (field < 0));
}


function invalidAlphaNumericField(field) {
	return ((field == "") || (typeof field === "undefined"));
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
					displayError("There was an error in calculating the fields");
				}
			}
			else {
				displayError("The value calculated is below $0 which means that you are consuming more energy than what you're putting back into the grid.");
			}
		}
		else {
			displayError("There was an error in calculating the fields");
		}				
	}
}


function displayAddPanelResult(result, status) {
	$("#pnlResults").removeClass("alert-error").addClass("alert-success");
	
	if (status == 'success') {
		var output = "<table><tr><th>Manufacturer</th><th>Model</th><th>Power</th></tr>";
		
		$.each(result.Panels, function (i) {
			output += "<tr><td>" + result.Panels[i].manufacturer + "</td><td>" + result.Panels[i].model + "</td><td>" + result.Panels[i].power + "</td></tr>";	        
	    });
		
		output += "</table>";
		
		$("#lblSavings").html(output);
		$("#pnlResults").show();
	}
	else {
		displayError("There was an error trying to add the new panel to the database.");
	}				
}


function displayError(message) {
	$("#lblSavings").html(message);
	$("#pnlResults").removeClass("alert-success").addClass("alert-error").show();
}

