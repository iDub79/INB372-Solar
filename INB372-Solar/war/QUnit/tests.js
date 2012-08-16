var results = { total: 0, bad: 0 }

QUnit.begin = function() {
	console.log("Running Test Suite");
};

QUnit.begin = function() {
	console.log("Running Test Suite");
};
	
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
*/

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

