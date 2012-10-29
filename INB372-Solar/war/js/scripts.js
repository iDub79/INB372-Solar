// index.jsp user input fields.
var panelManufacturer;
var panelModel;
var panelEfficiency;
var panelQty;
var inverterManufacturer;
var inverterModel;
var inverterEfficiency;

var orientation = "N";
var angle;
var consumption;
var address;
var latitude;
var longitude;
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
		
		$("#chartdiv").empty();
		
		panelManufacturer = $("#ddlPanelManufacturer").val();
		panelModel = $("#ddlPanelModel").val();
		panelEfficiency = $("#txtPanelEfficiency").val();
		panelQty = $("#txtPanelQty").val();
		
		inverterManufacturer = $("#ddlInverterManufacturer").val();
		inverterModel = $("#ddlInverterModel").val();
		inverterEfficiency = $("#txtInverterEfficiency").val();		
		
		angle = $("#txtPanelAngle").val();
		consumption = $("#txtPowerConsumption").val();
		//orientation = $("#ddlPanelOrientation").val();				
		address = $("#searchTextField").val();
		latitude = parseFloat($("#txtLatitude").val());
		longitude = parseFloat($("#txtLongitude").val());
		tariff = $("#ddlTariff").val();
		
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
		$("#txtPanelEfficiency, #txtInverterEfficiency, #txtPanelAngle, #txtPowerConsumption, #searchTextField").val("");
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
			"#grpAddress, #grpTariff, #grpPanelManufacturer, #grpPanelModel, #grpPanelPower," +
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
 

function createDailyGraph(result) {
	
	var startDate = new Date();
	startDate = Date.parse("January 1st, 2012");
	
	var output = "<table class='table table-hover table-condensed table-bordered table-striped'>";
	output += "<tr><th>Date</th><th>Daily Generated Electricity</th><th>Amount Put Back To Grid</th>";
	
	var graphDataMade = new Array();
	var graphDataExcess = new Array();
	var dates = new Array();
	
	$.each(result.Savings.DailyGen, function (i) {
		output += "<tr><td>" + startDate.toString('d-MMM-yyyy') + "</td><td>" + result.Savings.DailyGen[i][0] + "</td><td>" + result.Savings.DailyGen[i][1] + "</td></tr>";
		
		// limit to only January (31 days)
		if (i < 31) {
			dates[i] = startDate.toString('d-MMM-yyyy');						
			graphDataMade[i] = result.Savings.DailyGen[i][0];
			graphDataExcess[i] = result.Savings.DailyGen[i][1];
		}
		
		startDate = startDate.addDays(1);
    });
	
	output += "</table>";
	
	createGraph("Date", dates, graphDataMade, graphDataExcess);	
}

function createMonthlyGraph(result) {
	
	var startDate = new Date();
	startDate = Date.parse("January 1st, 2012");
	
	var output = "<table class='table table-hover table-condensed table-bordered table-striped'>";
	output += "<tr><th>Month</th><th>Monthly Generated Electricity</th><th>Amount Put Back To Grid</th>";
	
	var graphDataMade = new Array();
	var graphDataExcess = new Array();
	var dates = new Array();
	
	$.each(result.Savings.MonthlyGen, function (i) {
		output += "<tr><td>" + startDate.toString('MMM') + "</td><td>" + result.Savings.MonthlyGen[i][0] + "</td><td>" + result.Savings.MonthlyGen[i][1] + "</td></tr>";
		
		dates[i] = startDate.toString('MMM');						
		graphDataMade[i] = result.Savings.MonthlyGen[i][0];
		graphDataExcess[i] = result.Savings.MonthlyGen[i][1];
		
		startDate = startDate.addMonths(1);
    });
	
	output += "</table>";
	
	createGraph("Month", dates, graphDataMade, graphDataExcess);
}


function createGraph(label, dates, graphDataMade, graphDataExcess) {
	
	$("#chartdiv").css("height", "400px");
	
	var plot1 = $.jqplot('chartdiv', [graphDataMade, graphDataExcess], {
		title: 'Electricity Generated',
		seriesDefaults:{
            rendererOptions: {fillToZero: true},
            markerOptions: {size: 12}
		},
		axesDefaults: {
	        tickRenderer: $.jqplot.CanvasAxisTickRenderer,
	        tickOptions: {
	          fontSize: '10pt'
	        }
	    },
		series: [
	         {label: 'Total Generated'},
	         {label: 'Put Back To Grid'}
		],
		legend: {
			show: true
		},
		axes: {
			xaxis: {
				label: label,
				renderer: $.jqplot.CategoryAxisRenderer,
                ticks: dates,
                tickOptions: {
		          angle: -45,
		        }
            },
            yaxis: {
            	label: 'kWh',
                pad: 1.05
            }
		},
		highlighter: {
	        show: true,
	        sizeAdjust: 7.5
        },
        cursor: {
        	show: false
        }
	});
}