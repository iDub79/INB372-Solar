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
        data : "manufacturer=" + manufacturer + "&model=" + model + "&power=" + power +
        	   "&newPanelLength=" + newPanelLength + "&newPanelWidth=" + newPanelWidth,
	    async: false,
        success : displayAddPanelResult
    });
}




