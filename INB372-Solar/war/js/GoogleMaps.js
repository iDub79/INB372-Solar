$(function() {
	initialiseMap();
    initialiseSearch();
});    


/*
 * Initialise Google Map global variables
 */
var map;
var geocoder;
var autocomplete;
var infowindow;
var myOptions = { mapTypeId: google.maps.MapTypeId.SATELLITE };
var center;


/*
 * initialise Google Maps features
 */
function initialiseMap() {
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    // add Event Listeners for resizing of window
    google.maps.event.addDomListener(map, 'idle', function () {
        center = map.getCenter();
    });
    
    google.maps.event.addDomListener(window, 'resize', function () {
        map.setCenter(center);
    });
}


/*
 * adds and initialises Google Places Autocomplete search
 */
function initialiseSearch() {
    var input = document.getElementById("searchTextField");
    autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.bindTo('bounds', map);
    infowindow = new google.maps.InfoWindow();

    var marker = new google.maps.Marker({
        map: map
    });
    
    // adds Event Listener so that when a Place is selected, it is automatically shown on the Map
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
            	
    	infowindow.close();        
        
        var place = autocomplete.getPlace();

        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        }
        else {
            map.setCenter(place.geometry.location);
            map.setZoom(18);
        }

        // sets a marker to the searched location
        var image = new google.maps.MarkerImage(
              place.icon,
              new google.maps.Size(71, 71),
              new google.maps.Point(0, 0),
              new google.maps.Point(17, 34),
              new google.maps.Size(35, 35));

        marker.setIcon(image);
        marker.setPosition(place.geometry.location);

        infowindow.open(map, marker);
        
        // display the latitude of the searched location
        displayCoordinates();
    });
}


/*
 * obtains the geo location and displays the lat value if successful
 */
function displayCoordinates() {
	var address = $("#searchTextField").val();
	
    geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'address': address }, function (results, status) {
    	if (status == google.maps.GeocoderStatus.OK) {
    		var lat = getLat(results[0].geometry.location.toString());
    		var long = getLong(results[0].geometry.location.toString());
    		$("#txtLatitude").val(lat.toString());
    		$("#txtLongitude").val(long.toString());
    		$("#txtDailySunlight").focus();
    		
    		var state = results[0].address_components[results[0].address_components.length - 3].long_name;
    		getTariffsForState(state);
        }
    	else {
        	$("#lblCoordinates").html("Geocode was not successful for the following reason: " + status);
        }
    });
}


/*
 * splits the location string into lat and long values then returns the latitude value
 */
function getLat(latLong) {	
	var latlngStr = latLong.split(",", 2);
	return latlngStr[0].substr(1, latlngStr[0].length);    	
}

/*
 * splits the location string into lat and long values then returns the longitude value
 */
function getLong(latLong) {	
	var latlngStr = latLong.split(",", 2);
	return latlngStr[1].substr(1, latlngStr[1].length - 2);    	
}