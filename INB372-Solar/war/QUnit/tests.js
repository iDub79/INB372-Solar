QUnit.begin = function() {
	console.log("Running Test Suite");
};


testNumericInputFields("Panel Size", "panelSize")
testNumericInputFields("Panel Efficiency", "panelEfficiency")
testNumericInputFields("Inverter Efficiency", "inverterEfficiency")
testAlphaNumericInputFields("Orientation", "orientation")
testNumericInputFields("Angle", "angle")
testNumericInputFields("Sunlight", "sunlight")
testNumericInputFields("Consumption", "consumption")
testAlphaNumericInputFields("Address", "address")


module("All input fields valid");
test("All input fields valid", function() {
	panelSize = 5;
	panelEfficiency = 5;
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	orientation = "N";
	angle = 5;
	sunlight = 5;
	consumption = 5;
	
	ok(validForm(), "All input fields are valid");
});



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
		ok(invalidNumberField(txtFieldName), fieldName + "  is NaN");
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




function clearInputFields() {
	panelSize = "";
	panelEfficiency = "";
	inverterEfficiency = "";
	address = "";
	orientation = "";
	angle = "";
	sunlight = "";
	consumption = "";
}
	
