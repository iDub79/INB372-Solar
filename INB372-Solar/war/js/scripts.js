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
var newPanelManufacturer;
var newPanelModel; 
var newPanelPower;
var newPanelLength;
var newPanelWidth;
var newInverterManufacturer;
var newInverterModel;
var newInverterEfficiency


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
		
		if (validCalculateSolarForm()) {
			calculatePanelSize();
			calculateInput();
		}
		else {
			showMissingFieldsError();
		}
	});	
	
	
	
	$("#btnAddPanel").click(function() {
		clearValidationMessages();
		
		newPanelManufacturer = $("#txtPanelManufacturer").val();
		newPanelModel = $("#txtPanelModel").val(); 
		newPanelPower = $("#txtPanelPower").val();
		newPanelLength = $("#txtPanelNewLength").val();
		newPanelWidth = $("#txtPanelNewWidth").val();
		
		if (validAddPanel()) {
			addNewPanel();
		}
		else {
			showMissingFieldsError();
		}
	});
	
	
	
	$("#btnAddInverter").click(function() {
		clearValidationMessages();
		
		newInverterManufacturer = $("#txtInverterManufacturer").val();
		newInverterModel = $("#txtInverterModel").val(); 
		newInverterEfficiency = $("#txtInverterEfficiency").val();
		
		if (validAddInverter()) {
			addNewInverter();
		}
		else {
			showMissingFieldsError();
		}
	});
	
	
	$("#btnResetCalculate").click(function() {
		$("#txtPanelLength, #txtPanelWidth, #txtPanelQty, #txtPanelEfficiency, #txtInverterEfficiency," +
				"#txtPanelAngle, #txtPowerConsumption, #txtDailySunlight, #searchTextField").val("");
		$("#listPanelOrientation").val("-1");
		$("#listTariff").val("-1");
		clearValidationMessages();
	});
	
	
	$("#btnResetPanel").click(function() {
		$("#txtPanelManufacturer, #txtPanelModel, #txtPanelPower, #txtPanelNewLength, #txtPanelNewWidth").val("");
		clearValidationMessages();
	});
	

	$("#btnResetInverter").click(function() {
		$("#txtInverterManufacturer, #txtInverterModel, #txtInverterEfficiency").val("");
		clearValidationMessages();
	});
	
	
	$("#ddlPanelManufacturer").change(function() {
		$("#ddlPanelModel").find("option").remove().end().append("<option value='-1'>-- Select Panel Model --</option>");
		getPanelModels($("#ddlPanelManufacturer").val());
	});
	
	
	$("#ddlPanelModel").change(function() {
		getPanelPower($("#ddlPanelModel").val())
	});
	
	
	$("#ddlInverterManufacturer").change(function() {
		$("#ddlInverterModel").find("option").remove().end().append("<option value='-1'>-- Select Inverter Model --</option>");
		getInverterModels($("#ddlInverterManufacturer").val());
	});
	
	
	$("#ddlInverterModel").change(function() {
		getInverterEfficiency($("#ddlInverterModel").val())
	});
});


function calculatePanelSize() {
	panelSize = (((panelLength / 1000) * (panelWidth / 1000)) * panelQty) ;
}


function showMissingFieldsError() {
	$("#pnlErrors").show();
	$("#lblErrors").html("Please correct the fields highlighted in red.");
}


function clearValidationMessages() {
	$("#grpPanelLength, #grpPanelWidth, #grpPanelQty, #grpPanelEfficiency, #grpInverterEfficiency," +
			"#grpPanelOrientation, #grpPanelAngle, #grpPowerConsumption, #grpAddress, #grpDailySunlight," +
			"#grpTariff, #grpPanelManufacturer, #grpPanelModel, #grpPanelPower, #grpInverterNewManufacturer," +
			"#grpInverterNewModel, #grpInverterNewEfficiency").removeClass("error");
	$("#pnlErrors, #pnlResults").hide();
}


function validCalculateSolarForm() {	
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
	
	if (invalidAlphaNumericField(newPanelManufacturer)) {
		$("#grpPanelManufacturer").addClass("error");
		validForm = false;
	}
	if (invalidAlphaNumericField(newPanelModel)) {
		$("#grpPanelModel").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(newPanelPower)) {
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


function validAddInverter() {
	validForm = true;
	
	if (invalidAlphaNumericField(newInverterManufacturer)) {
		$("#grpInverterNewManufacturer").addClass("error");
		validForm = false;
	}
	if (invalidAlphaNumericField(newInverterModel)) {
		$("#grpInverterNewModel").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(newInverterEfficiency)) {
		$("#grpInverterNewEfficiency").addClass("error");
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


 
