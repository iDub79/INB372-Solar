function calculateInput() {
	$.ajax({
        type : "POST",
        url : "solarServlet",
        data : "panelManufacturer=" + panelManufacturer + "&panelModel=" + panelModel + "&panelEfficiency=" + panelEfficiency + 
        		"&panelQty=" + panelQty + "&inverterManufacturer=" + inverterManufacturer + "&inverterModel=" + inverterModel +
        		"&inverterEfficiency=" + inverterEfficiency + "&orientation=" + orientation + "&angle=" + angle + 
        		"&consumption=" + consumption + "&address=" + address + "&tariff=" + tariff + "&latitude=" + latitude + "&longitude=" + longitude,
	    async: false,
        success : displayResult
    });
}


function addNewPanel() {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=addPanel&manufacturer=" + newPanelManufacturer.toUpperCase() + "&model=" + newPanelModel.toUpperCase() + "&power=" + newPanelPower,
	    async: false,
        success : reloadPage
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


function getPanelManufacturers() {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=getPanelManufacturers",
	    async: false,
        success : buildPanelManufacturerDropDownList
    });
}


function getPanelModels(manufacturer) {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=getPanelModels&manufacturer=" + manufacturer,
	    async: false,
        success : buildPanelModelDropDownList
    });
}

function getPanelPower(model) {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=getPanelPower&model=" + model,
	    async: false,
        success : addPanelPower
    });
}


function deletePanel(panelToDelete) {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=deletePanel&model=" + panelToDelete,
	    async: false,
        success : reloadPage
    });
}


function addNewInverter() {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=addInverter&manufacturer=" + newInverterManufacturer.toUpperCase() + "&model=" + newInverterModel.toUpperCase() + "&efficiency=" + newInverterEfficiency,
	    async: false,
        success : reloadPage
    });
}


function getInverterList() {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=getInverters",
	    async: false,
        success : displayInverterResult
    });
}

function deleteInverter(inverterToDelete) {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=deleteInverter&model=" + inverterToDelete,
	    async: false,
        success : reloadPage
    });
}

function getInverterManufacturers() {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=getInverterManufacturers",
	    async: false,
        success : buildInverterManufacturerDropDownList
    });
}


function getInverterModels(manufacturer) {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=getInverterModels&manufacturer=" + manufacturer,
	    async: false,
        success : buildInverterModelDropDownList
    });
}

function getInverterEfficiency(model) {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=getInverterEfficiency&model=" + model,
	    async: false,
        success : addInverterEfficiency
    });
}


function displayResult(result, status) {
	
	if (status == 'success') {
		if (result.Savings.Success == true) {
			var amountSaved = result.Savings.Amount;
			
			//if (amountSaved > 0) {
				amountSavedNum = parseFloat(amountSaved);
				
				try {
					amountSavedNum = amountSavedNum.toFixed(2);
					
					var startDate = new Date();
					startDate = Date.parse("January 1st, 2012");
					
					var output = "<table class='table table-hover table-condensed table-bordered table-striped'>";
					output += "<tr><th>Date</th><th>Daily Generated Electricity</th><th>Amount Put Back To Grid</th>";
					
					var graphDataMade = new Array();
					var graphDataExcess = new Array();
					var dates = new Array();
					
					$.each(result.Savings.ReturnTable, function (i) {
						output += "<tr><td>" + startDate.toString('d-MMM-yyyy') + "</td><td>" + result.Savings.ReturnTable[i][0] + "</td><td>" + result.Savings.ReturnTable[i][1] + "</td></tr>";
						
						// limit to only January (31 days)
						if (i < 31) {
							dates[i] = startDate.toString('d-MMM-yyyy');						
							graphDataMade[i] = result.Savings.ReturnTable[i][0];
							graphDataExcess[i] = result.Savings.ReturnTable[i][1];
						}
						
						startDate = startDate.addDays(1);
				    });
					
					output += "</table>";

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
								label: 'Date',
								renderer: $.jqplot.CategoryAxisRenderer,
				                ticks: dates,
				                tickOptions: {
						          angle: -45,
						        }
				            },
				            yaxis: {
				            	label: '$',
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
					
					$("#chartdiv").css("visibility", "visible");

					//$("#lblSavings").html("Based on your input, the annual savings will be <strong>$" + amountSavedNum + "</strong><br /><br /><br />" + output);
					$("#lblSavings").html("Based on your input, the annual savings will be <strong>$" + amountSavedNum + "</strong>");
					$("#pnlResults").show();
					
					$("html, body").animate({scrollTop: "+=" + $("#lblSavings").offset().top + "px"}, "fast");

				}
				catch (Error) {
					//displayError("There was an error in calculating the fields");
				}
			//}
			//else {
				//displayError("The value calculated is below $0 which means that you are consuming more energy than what you're putting back into the grid.");
			//}
		}
		else {
			displayError("There was an error in calculating the fields");
		}				
	}
}


function displayPanelResult(result, status) {

	if (status == 'success') {
		if (result.Success == true) {
			var output = "<table class='table table-hover table-condensed table-bordered table-striped'><tr><th>Manufacturer</th><th>Model</th><th>Power</th><th></th></tr>";
			
			$.each(result.Panels, function (i) {
				output += "<tr><td>" + result.Panels[i].manufacturer + "</td><td>" + result.Panels[i].model + "</td><td>" + result.Panels[i].power + "</td><td style='text-align:center;'><a class='deleteSpec' onclick='deletePanel(&quot;" + result.Panels[i].model + "&quot;); return false;'><i class='icon-remove'></a></i></td></tr>";	        
		    });
			
			output += "</table>";
			
			$("#lblPanel").html(output);
			$("#pnlPanelResults").show();			
		}
		else if (result.Success == false) {
			displayError("There was an error trying to add the new panel to the database.");
		}
		else if (result.Success == "empty") {
			
		}
	}
	else {
		displayError("There was an error trying to add the new panel to the database.");
	}				
}


function buildPanelManufacturerDropDownList(result, status) {
	if (status == 'success') {
		if (result.Success == true) {
			var output = "";
			
			$.each(result.Panels, function (i) {
				output += "<option val='" + result.Panels[i].manufacturer + "'>" + result.Panels[i].manufacturer + "</option>";	        
		    });
			
			$("#ddlPanelManufacturer").append(output);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}				
}


function buildPanelModelDropDownList(result, status) {
	if (status == 'success') {
		if (result.Success == true) {
			var output = "";
			
			$.each(result.Panels, function (i) {
				output += "<option val='" + result.Panels[i].model + "'>" + result.Panels[i].model + "</option>";	        
		    });
			
			$("#ddlPanelModel").append(output);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}
}


function addPanelPower(result, status) {
	if (status == 'success') {
		if (result.Success == true) {			
			$("#txtPanelEfficiency").val(result.Panels[0].power);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}
}


function displayInverterResult(result, status) {

	if (status == 'success') {
		if (result.Success == true) {
			var output = "<table class='table table-hover table-condensed table-bordered table-striped'><tr><th>Manufacturer</th><th>Model</th><th>Efficiency</th><th></th></tr>";
			
			$.each(result.Inverters, function (i) {
				output += "<tr><td>" + result.Inverters[i].manufacturer + "</td><td>" + result.Inverters[i].model + "</td><td>" + result.Inverters[i].efficiency + "</td><td><a class='deleteSpec' onclick='deleteInverter(&quot;" + result.Inverters[i].model + "&quot;); return false;'><i class='icon-remove'></a></i></td></tr>";	        
		    });
			
			output += "</table>";
			
			$("#lblInverter").html(output);
			$("#pnlInverterResults").show();			
		}
		else if (result.Success == false) {
			displayError("There was an error trying to add the new panel to the database.");
		}
		else if (result.Success == "empty") {
			
		}
	}
	else {
		displayError("There was an error trying to add the new panel to the database.");
	}				
}


function buildInverterManufacturerDropDownList(result, status) {
	if (status == 'success') {
		if (result.Success == true) {
			var output = "";
			
			$.each(result.Inverters, function (i) {
				output += "<option val='" + result.Inverters[i].manufacturer + "'>" + result.Inverters[i].manufacturer + "</option>";	        
		    });
			
			$("#ddlInverterManufacturer").append(output);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}				
}


function buildInverterModelDropDownList(result, status) {
	if (status == 'success') {
		if (result.Success == true) {
			var output = "";
			
			$.each(result.Inverters, function (i) {
				output += "<option val='" + result.Inverters[i].model + "'>" + result.Inverters[i].model + "</option>";	        
		    });
			
			$("#ddlInverterModel").append(output);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}
}


function addInverterEfficiency(result, status) {
	if (status == 'success') {
		if (result.Success == true) {			
			$("#txtInverterEfficiency").val(result.Inverters[0].efficiency);
		}
		else if (result.Success == false) {
			displayError("There was an error trying to retrieve the results.");
		}
		else if (result.Success == "empty") {
			displayError("No data");
		}
	}
	else {
		displayError("There was an error trying to retrieve the results.");
	}
}


function displayError(message) {
	$("#lblErrors").html(message);
	$("#pnlErrors").show();
}


