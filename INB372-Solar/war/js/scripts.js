var destinations = new Array();

$(function() {

    // initialise Google Map features
    initialiseMap();
    initialiseSearch();    

    // add jQuery UI styling to buttons
    $("input:submit, input:button, a, button", ".MyTrip").button();
});



