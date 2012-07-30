/*
 * Main function scripts
 */

$(function() {

	$("#btnSubmit").click(function() {
        
        var name = $("#name").val();
        var age = $("#age").val();
        
        $.ajax({
            type : "POST",
            url : "inb372_solar",
            data : "name=" + name + "&age=" + age,
            success : displayResult
        });
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
	
	
	
	/*
	 * jQuery UI scripts
	 */
		
	// Buttons
	$( "input:submit, a, button", "body" ).button();
	
});