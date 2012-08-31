QUnit.begin = function() {
	console.log("Running Test Suite");
};


test("Panel Size is undefined", function() {
	panelEfficiency = 5;
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	orientation = "N";
	angle = 5;
	sunlight = 5;
	consumption = 5;
	
	ok(!checkValidForm(), "Fields are valid - Undefined fields checked");
});

test("Panel Size is empty", function() {
	panelEfficiency = 5;
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	orientation = "N";
	angle = 5;
	sunlight = 5;
	consumption = 5;
	
	ok(!checkValidForm(), "Fields are valid - Empty fields checked");
});


test("Test form fields are valid", function() {
	panelSize = 5;
	panelEfficiency = 5;
	inverterEfficiency = 5;
	address = "1 Test Street Brisbane Queensland";
	orientation = "N";
	angle = 5;
	sunlight = 5;
	consumption = 5;
	
	ok(checkValidForm(), "Fields are valid");
});


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
	
/*
$(function() {

	$("#btnSubmitPersonTest").click(function() {
		test("Submit Person Tests", 3, function() {
			ok(testForEmptyFields("#personName", "Name"), "Person field is not empty");
			ok(testForEmptyFields("#personAge", "Age"), "Age field is not empty");
		});
				
		console.log("Of " + results.total + " tests, " + results.bad + " failed, " + (results.total - results.bad) + " passed.");
	});
	
});


test("Test form fields are not filled out", function() {
	$("#btnSubmitPersonTest").click();
	equal($("#personName").val(), "", "Person field is empty");
	console.log("Of " + results.total + " tests, " + results.bad + " failed, " + (results.total - results.bad) + " passed.");
});


function testForEmptyFields(selector, fieldName) {
	results.total++;
	
	var field = $(selector).val();
	var result = (field == "") ?  false : true;
	
	if (result == false) {
		results.bad++;
		console.log(fieldName + " is empty");
		return false;
	}
	else {
		console.log(fieldName + " contains value: " + field);
		return true;
	}	
}

*/