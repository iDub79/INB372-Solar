QUnit.begin = function() {
	console.log("Running Test Suite");
};


testNumericInputFields("Panel Size", "panelSize")
testNumericInputFields("Panel Efficiency", "panelEfficiency")
testNumericInputFields("Inverter Efficiency", "inverterEfficiency")
testNumericInputFields("Angle", "angle")
testNumericInputFields("Sunlight", "sunlight")
testNumericInputFields("Consumption", "consumption")
testAlphaNumericInputFields("Address", "address")


module("All input fields valid");
test("All input fields valid", function() {
	setupValidInputs();
	
	ok(validForm(), "All input fields are valid");
});


module("Valid ajax response");
test("All input data submitted as valid parameters", function() {
	setupValidInputs();
	
	var input = "panelSize=" + panelSize + "&panelEfficiency=" + panelEfficiency + "&inverterEfficiency=" + inverterEfficiency +
	   "&orientation=" + orientation + "&angle=" + angle + "&sunlight=" + sunlight + "&consumption=" + consumption + "&address=" + address + "&tariff=" + tariff;
	
	var options = null;
	$.ajax = function(param) {
		options = param;
	};
	calculateInput();
	options.success();
	equal(options.data, input);
});

/*
test("Valid calculation response received", function() {
setupValidInputs();
	
	var input = "panelSize=" + panelSize + "&panelEfficiency=" + panelEfficiency + "&inverterEfficiency=" + inverterEfficiency +
	   "&orientation=" + orientation + "&angle=" + angle + "&sunlight=" + sunlight + "&consumption=" + consumption + "&address=" + address + "&tariff=" + tariff;
	
	var options = null;
	$.ajax = function(param) {
		options = param;
	};
	calculateInput();
	options.success();
	equal($("#lblSavings").html(), "Based on your input, the annual savings will be <strong>$100.16</strong>");
});
*/


function testNumericInputFields(fieldName, txtFieldName) {
	module(fieldName + " input fields");
	test(fieldName + " is undefined", function() {
		ok(invalidNumberField(txtFieldName), fieldName + " is undefined");
	});

	test(fieldName + " is empty", function() {
		txtFieldName = "";
		ok(invalidNumberField(txtFieldName), fieldName + " is empty");
	});

	test(fieldName + " is NaN", function() {
		txtFieldName = "a";
		ok(invalidNumberField(txtFieldName), fieldName + "  is not a number");
	});

	test(fieldName + " is less than 0", function() {
		txtFieldName = "-1";
		ok(invalidNumberField(txtFieldName), fieldName + "  is less than 0");
	});
	
	test(fieldName + " is valid", function() {
		txtFieldName = "5";
		ok(!invalidNumberField(txtFieldName), fieldName + "  is valid");
	});
}


function testAlphaNumericInputFields(fieldName, txtFieldName) {
	module(fieldName + " input fields");
	test(fieldName + " is undefined", function() {
		ok(!invalidAlphaNumericField(txtFieldName), fieldName + " is undefined");
	});

	test(fieldName + " is empty", function() {
		txtFieldName = "";
		ok(invalidAlphaNumericField(txtFieldName), fieldName + " is empty");
	});

	test(fieldName + " is valid", function() {
		txtFieldName = "ABC12";
		ok(!invalidAlphaNumericField(txtFieldName), fieldName + "  is valid");
	});
}


function setupValidInputs() {
	panelSize = 5;
	panelEfficiency = 5;
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	orientation = "N";
	angle = 5;
	sunlight = 5;
	consumption = 5;
	tariff = 0.44;
}

function clearInputFields() {
	panelSize = "";
	panelEfficiency = "";
	inverterEfficiency = "";
	address = "";
	orientation = "";
	angle = "";
	sunlight = "";
	consumption = "";
	tariff = "";
}
	
