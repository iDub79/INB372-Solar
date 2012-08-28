$(function() {

	$("#btnCalculate").click(function() {
		
		clearValidationMessages();
		
		var panelSize = $("#txtPanelSize").val();
		var panelEfficiency = $("#txtPanelEfficiency").val();
		var inverterEfficiency = $("#txtInverterEfficiency").val();
		var address = $("#searchTextField").val();
		var orientation = $("#txtPanelOrientation").val();
		var angle = $("#txtPanelAngle").val();
		var sunlight = $("#txtDailySunlight").val();
		var consumption = $("#txtPowerConsumption").val();
		
		var validForm = true;
		
		if (panelSize == "") {
			$("#grpPanelSize").addClass("error");
			validForm = false;
		}
		if (panelEfficiency == "") {
			$("#grpPanelEfficiency").addClass("error");
			validForm = false;
		}
		if (inverterEfficiency == "") {
			$("#grpInverterEfficiency").addClass("error");
			validForm = false;
		}		
		if (orientation == "") {
			$("#grpPanelOrientation").addClass("error");
			validForm = false;
		}
		if (angle == "") {
			$("#grpPanelAngle").addClass("error");
			validForm = false;
		}
		if (sunlight == "") {
			$("#grpDailySunlight").addClass("error");
			validForm = false;
		}
		if (consumption == "") {
			$("#grpPowerConsumption").addClass("error");
			validForm = false;
		}
		if (address == "") {
			$("#grpAddress").addClass("error");
			validForm = false;
		}
		
		if (validForm) {
			$.ajax({
                type : "POST",
                url : "solarServlet",
                data : "panelSize=" + panelSize + "&panelEfficiency=" + panelEfficiency + "&inverterEfficiency=" + inverterEfficiency + "&orientation=" + orientation +
                			"&angle=" + angle + "&sunlight=" + sunlight + "&consumption=" + consumption + "&address=" + address,
                success : displayResult
            });
		}
	});
	
	
	function displayResult(result, status) {
		if (status == 'success') {		
			$("#lblSavings").html("Amount saved is: " + result.Savings.Amount);
			$("#pnlResults").show();
		}
		else {
			$("#lblSavings").html("<p>Error: " + result + "</p>");			
		}
	}
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