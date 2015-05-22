var myCenter = new google.maps.LatLng(20.6583075, -103.4420149);
var RootUrl = "http://localhost:50691/";


function initialize() {
    var mapProp = {
        center: myCenter,
        zoom: 1,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };   
}

function SetMarker(latitude, longitude) {

    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

    var myCenter = new google.maps.LatLng(latitude, longitude);

    var marker = new google.maps.Marker({
        position: myCenter,
    });

    marker.setMap(map);
}


function getMarkers() {
    $.ajax({
        url: RootUrl+"api/GeoData",
        type: "GET",
        cache: false,
        dataType: 'json',
        crossDomain: true,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            $.each($.parseJSON(data), function (index, item) {
                SetMarker(data.latitude, data.longitude);                
            });            
        },
        error: function (result, errorText, errorThrown) {
            
        },
        beforeSend: function () {
            
        },
        complete: function () {
        
        },
    });
}



google.maps.event.addDomListener(window, 'load', initialize);

getMarkers();