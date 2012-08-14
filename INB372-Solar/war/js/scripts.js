var destinations = new Array();

$(function() {

    // initialise Google Map features
    initialiseMap();
    initialiseSearch();
    
    // no conflicts
    
    // add jQuery UI styling to buttons
    $("input:submit, input:button, a, button", ".MyTrip").button();
});



