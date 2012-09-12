// index.jsp user input fields
var panelLength;
var panelWidth;
var panelQty;
var panelSize;
var panelEfficiency;
var inverterEfficiency;
var orientation = "N";
var angle = 45;
var consumption;
var address = "Dummy address";		// dummy address used until later iteration
var sunlight = "10";				// default value based on Brisbane average
var tariff;
var amountSavedNum = 0.00;

// admin.jsp user input fields
var manufacturer;
var model; 
var power;
var newPanelLength;
var newPanelWidth;


$(function() {

	$("#btnCalculate").click(function() {

		clearValidationMessages();
		
		panelLength = $("#txtPanelLength").val();
		panelWidth = $("#txtPanelWidth").val();
		panelQty = $("#txtPanelQty").val();
		panelEfficiency = $("#txtPanelEfficiency").val();
		inverterEfficiency = $("#txtInverterEfficiency").val();		
		//orientation = $("#listPanelOrientation").val();
		//angle = $("#txtPanelAngle").val();
		consumption = $("#txtPowerConsumption").val();		
		//sunlight = $("#txtDailySunlight").val();
		//address = $("#searchTextField").val();
		tariff = $("#listTariff").val();
		
		if (validForm()) {
			calculatePanelSize();
			calculateInput();
		}
		else {
			$("#pnlErrors").show();
		}
	});	
	
	
	
	$("#btnAddPanel").click(function() {
		clearValidationMessages();
		
		manufacturer = $("#txtPanelManufacturer").val();
		model = $("#txtPanelModel").val(); 
		power = $("#txtPanelPower").val();
		newPanelLength = $("#txtPanelNewLength").val();
		newPanelWidth = $("#txtPanelNewWidth").val();
		
		if (validAddPanel()) {
			addNewPanel();
		}
		else {
			$("#pnlErrors").show();
		}
	});
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


function toTitleCase(str) {
	return str.replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); });
}

