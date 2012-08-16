/*
 * Main function scripts
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
                success : displayResult
            });
        }
        else {
        	if (name == "") $("#reqPersonName").css("display", "inline");
        	if (age == "") $("#reqPersonAge").css("display", "inline");
        }
    });

	function displayResult(result, status) {
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

function clearValidationMessages() {
	$("#reqPersonName").hide();
	$("#reqPersonAge").hide();
}