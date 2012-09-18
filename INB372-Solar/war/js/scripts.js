// index.jsp user input fields.
var panelManufacturer;
var panelModel;
var panelEfficiency;
var panelQty;
var inverterManufacturer;
var inverterModel;
var inverterEfficiency;

var orientation;
var angle = 45;
var consumption;
var address;
var latitude;
var longitude;
var sunlight;
var tariff;
var amountSavedNum = 0.00;

// admin.jsp user input fields
var newPanelManufacturer;
var newPanelModel; 
var newPanelPower;
var newInverterManufacturer;
var newInverterModel;
var newInverterEfficiency


$(function() {
	
	$("#ddlPanelManufacturer").focus();

	$("#btnCalculate").click(function() {

		clearValidationMessages();
		
		panelManufacturer = $("txtPanelManufacturer").val();
		panelModel = $("txtPanelModel").val();
		panelEfficiency = $("#txtPanelEfficiency").val();
		panelQty = $("#txtPanelQty").val();
		
		inverterManufacturer = $("ddlInverterManufacturer").val();
		inverterModel = $("ddlInverterModel").val();
		inverterEfficiency = $("#txtInverterEfficiency").val();		
		
		angle = $("#txtPanelAngle").val();
		consumption = $("#txtPowerConsumption").val();
		orientation = $("#ddlPanelOrientation").val();				
		sunlight = $("#txtDailySunlight").val();
		address = $("#searchTextField").val();
		latitude = $("#txtLatitude").val();
		longitude = $("#txtLongitude").val();
		tariff = $("#listTariff").val();
		
		if (validCalculateSolarForm()) {
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
		$("#txtPanelEfficiency, #txtInverterEfficiency," +
				"#txtPanelAngle, #txtPowerConsumption, #txtDailySunlight, #searchTextField").val("");
		$("#ddlPanelManufacturer, #ddlPanelModel, #ddlInverterManufacturer, #ddlInverterModel, #listPanelOrientation, #listTariff").val("-1");
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
	
	
	$("#btnSearchAddress").click(function(){
		$("#addressModal").modal();
		$("#addressModal").on("shown", function () {
			google.maps.event.trigger(map, "resize");
		});
		$("#searchTextField").focus();
	});
	
});



function showMissingFieldsError() {
	$("#pnlErrors").show();
	$("#lblErrors").html("Please correct the fields highlighted in red.");
}


function clearValidationMessages() {
	$("#grpPanelManufacturer, #grpPanelModel, #grpPanelQty, #grpPanelEfficiency, #grpInverterManufacturer," +
			"#grpInverterModel, #grpInverterEfficiency, #grpPanelOrientation, #grpPanelAngle, #grpPowerConsumption," +
			"#grpAddress, #grpDailySunlight, #grpTariff, #grpPanelManufacturer, #grpPanelModel, #grpPanelPower," +
			"#grpInverterNewManufacturer, #grpInverterNewModel, #grpInverterNewEfficiency, #grpLatitude, #grpLongitude")
		.removeClass("error");
	$("#pnlErrors, #pnlResults").hide();
}


function validCalculateSolarForm() {	
	var validForm = true;

	if (invalidPostiveNumberField(panelQty)) {
		$("#grpPanelQty").addClass("error");
		validForm = false;
	}
	if (invalidPostiveNumberField(panelEfficiency)) {
		$("#grpPanelEfficiency").addClass("error");
		validForm = false;
	}
	if (invalidPostiveNumberField(inverterEfficiency)) {
		$("#grpInverterEfficiency").addClass("error");
		validForm = false;
	}		
	if (orientation == -1) {
		$("#grpPanelOrientation").addClass("error");
		validForm = false;
	}
	if ((invalidPostiveNumberField(angle)) || (angle > 90)) {
		$("#grpPanelAngle").addClass("error");
		validForm = false;
	}
	if (invalidPostiveNumberField(sunlight)) {
		$("#grpDailySunlight").addClass("error");
		validForm = false;
	}
	if (invalidPostiveNumberField(consumption)) {
		$("#grpPowerConsumption").addClass("error");
		validForm = false;
	}
	if (invalidAlphaNumericField(address)) {
		$("#grpAddress").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(latitude)) {
		$("#grpLatitude").addClass("error");
		validForm = false;
	}
	if (invalidNumberField(longitude)) {
		$("#grpLongitude").addClass("error");
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
	if (invalidPostiveNumberField(newPanelPower)) {
		$("#grpPanelPower").addClass("error");
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
	if (invalidPostiveNumberField(newInverterEfficiency)) {
		$("#grpInverterNewEfficiency").addClass("error");
		validForm = false;
	}
	
	return validForm;
}


function invalidNumberField(field) {
	return ((field == "") || (isNaN(field)) || (typeof field === "undefined"));
}

function invalidPostiveNumberField(field) {
	return ((field == "") || (isNaN(field)) || (typeof field === "undefined") || (field < 0));
}


function invalidAlphaNumericField(field) {
	return ((field == "") || (typeof field === "undefined"));
}


function toTitleCase(str) {
	return str.replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); });
}


function reloadPage() {
	location.reload();
}
 
