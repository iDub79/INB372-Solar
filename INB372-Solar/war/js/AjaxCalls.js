var panelSize;
var panelEfficiency;
var inverterEfficiency;
var address;
var orientation;
var angle;
var sunlight;
var consumption;

$(function() {

	$("#btnCalculate").click(function() {

		clearValidationMessages();
		
		panelSize = $("#txtPanelSize").val();
		panelEfficiency = $("#txtPanelEfficiency").val();
		inverterEfficiency = $("#txtInverterEfficiency").val();
		address = $("#searchTextField").val();
		orientation = $("#txtPanelOrientation").val();
		angle = $("#txtPanelAngle").val();
		sunlight = $("#txtDailySunlight").val();
		consumption = $("#txtPowerConsumption").val();
		
		if (validForm()) {
			$.ajax({
                type : "POST",
                url : "solarServlet",
                data : "panelSize=" + panelSize + "&panelEfficiency=" + panelEfficiency + "&inverterEfficiency=" + inverterEfficiency +
                	   "&orientation=" + orientation + "&angle=" + angle + "&sunlight=" + sunlight + "&consumption=" + consumption + "&address=" + address,
                success : displayResult
            });
		}
		else {
			$("#pnlErrors").show();
		}
	});	
});

function clearValidationMessages() {
	$("#grpPanelSize").removeClass("error");
	$("#grpPanelEfficiency").removeClass("error");
	$("#grpInverterEfficiency").removeClass("error");
	$("#grpAddress").removeClass("error");
	$("#grpPanelOrientation").removeClass("error");
	$("#grpPanelAngle").removeClass("error");
	$("#grpDailySunlight").removeClass("error");
	$("#grpPowerConsumption").removeClass("error");
	$("#pnlErrors").hide();
	$("#pnlResults").hide();
}


function validForm() {	
	var validForm = true;
	
	if (invalidNumberField(panelSize)) {
		$("#grpPanelSize").addClass("error");
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
	if (invalidAlphaNumericField(orientation)) {
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
	
	return validForm;
}


function invalidNumberField(field) {
	return ((field == "") || (isNaN(field)) || (typeof field === "undefined"));
}


function invalidAlphaNumericField(field) {
	return ((field == "") || (typeof field === "undefined"));
}


function displayResult(result, status) {
	if (status == 'success') {
		if (result.Savings.Success == true) {
			$("#lblSavings").html("Amount saved is: " + result.Savings.Amount);
			$("#pnlResults").show();
		}
		else {
			$("#lblSavings").html("There was an error in calculating the fields");
			$("#pnlResults").show();
		}				
	}
}



/*
 *  Below is just a test
 */	
$(function() {
	
	$("#btnSubmitPersonTest").click(function() {
        
		clearValidationMessages();
		
		var name = $("#personName").val();
        var age = $("#personAge").val();
        
        if ((name != "") && (age != "")) {
        	$.ajax({
                type : "POST",
                url : "personServlet",
                data : "name=" + name + "&age=" + age,
                success : displayPersonResult
            });
        }
        else {
        	if (name == "") $("#reqPersonName").css("display", "inline");
        	if (age == "") $("#reqPersonAge").css("display", "inline");
        }
    });

	function displayPersonResult(result, status) {
		if (status == 'success') {
			var output = "";
	
			$.each(result.Persons, function (i) {
				output += "<p>Name = " + result.Persons[i].name + " & Age = " + result.Persons[i].age + "</p>";	        
		    });
			
			$("#lblResult").html(output);
		}
		else {
			$("#lblResult").html("<p>Error: " + result + "</p>");			
		}
	}	
});