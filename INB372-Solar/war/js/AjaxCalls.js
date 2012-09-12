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
        data : "option=addPanel&manufacturer=" + newPanelManufacturer.toUpperCase() + "&model=" + newPanelModel.toUpperCase() + "&power=" + newPanelPower +
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


function deletePanel(panelToDelete) {
	$.ajax({
        type : "POST",
        url : "panelServlet",
        data : "option=deletePanel&model=" + panelToDelete,
	    async: false,
        success : displayPanelResult
    });
}


function addNewInverter() {
	$.ajax({
        type : "POST",
        url : "inverterServlet",
        data : "option=addInverter&manufacturer=" + newInverterManufacturer.toUpperCase() + "&model=" + newInverterModel.toUpperCase() + "&efficiency=" + newInverterEfficiency,
	    async: false,
        success : displayInverterResult
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
        success : displayInverterResult
    });
}


function displayResult(result, status) {
	
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


function displayError(message) {
	$("#lblErrors").html(message);
	$("#pnlErrors").show();
}


