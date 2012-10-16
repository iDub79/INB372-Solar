QUnit.begin = function() {
	console.log("Running Test Suite");
};


testPositiveNumericInputFields("Panel Quantity", "panelQty");
testPositiveNumericInputFields("Panel Efficiency", "panelEfficiency");
testPositiveNumericInputFields("Inverter Efficiency", "inverterEfficiency");
testPositiveNumericInputFields("Angle", "angle");
testPositiveNumericInputFields("Consumption", "consumption");
testAlphaNumericInputFields("Address", "address");
testNumericInputFields("Latitude", "latitude");
testNumericInputFields("Longitude", "longitude");

testAlphaNumericInputFields("Panel Manufacturer", "panelManufacturer");
testAlphaNumericInputFields("Panel Model", "panelModel");
testAlphaNumericInputFields("Inverter Manufacturer", "inverterManufacturer");
testAlphaNumericInputFields("Inverter Model", "inverterModel");


module("All input fields valid");
test("All input fields valid", function() {
	setupValidInputs();
	
	ok(validCalculateSolarForm(), "All input fields are valid");
});



module("Valid ajax response");
test("All input data submitted as valid parameters", function() {
	setupValidInputs();

	var input = "panelManufacturer=" + panelManufacturer + "&panelModel=" + panelModel + "&panelEfficiency=" + panelEfficiency + 
        		"&panelQty=" + panelQty + "&inverterManufacturer=" + inverterManufacturer + "&inverterModel=" + inverterModel +
        		"&inverterEfficiency=" + inverterEfficiency + "&orientation=" + orientation + "&angle=" + angle +
        		"&consumption=" + consumption + "&address=" + address + "&tariff=" + tariff + "&latitude=" + latitude + "&longitude=" + longitude;
	
	var options = null;
	$.ajax = function(param) {
		options = param;
	};
	calculateInput();
	options.success();
	equal(options.data, input);
});



function testPositiveNumericInputFields(fieldName, txtFieldName) {
	module(fieldName + " input fields");
	test(fieldName + " is undefined", function() {
		ok(invalidPostiveNumberField(txtFieldName), fieldName + " is undefined");
	});

	test(fieldName + " is empty", function() {
		txtFieldName = "";
		ok(invalidPostiveNumberField(txtFieldName), fieldName + " is empty");
	});

	test(fieldName + " is NaN", function() {
		txtFieldName = "a";
		ok(invalidPostiveNumberField(txtFieldName), fieldName + "  is not a number");
	});

	test(fieldName + " is less than 0", function() {
		txtFieldName = "-1";
		ok(invalidPostiveNumberField(txtFieldName), fieldName + "  is less than 0");
	});
	
	test(fieldName + " is valid", function() {
		txtFieldName = "5";
		ok(!invalidPostiveNumberField(txtFieldName), fieldName + "  is valid");
	});
}


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
	panelManufacturer = "ABC";
	panelModel = "CBA";
	panelQty = 8;
	panelEfficiency = 5;
	inverterManufacturer = "DEF";
	inverterModel = "FED";
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	latitude = 146.34;
	longitude = -28.67;
	orientation = "N";
	angle = 5;
	consumption = 5;
	tariff = 0.44;
	manufacturer = "Test manufacturer";
	model = "Test model"; 
	power = 200;
}

function clearInputFields() {
	panelManufacturer = "";
	panelModel = "";
	panelQty = "";
	panelEfficiency = "";
	inverterManufacturer = "";
	inverterModel = "";
	inverterEfficiency = "";
	address = "";
	latitude = "";
	longitude = "";
	orientation = "";
	angle = "";
	consumption = "";
	tariff = "";
	manufacturer = "";
	model = ""; 
	power = "";
}
	
