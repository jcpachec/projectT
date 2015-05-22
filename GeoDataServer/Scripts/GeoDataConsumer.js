var myCenter = new google.maps.LatLng(20.6583075, -103.4420149);
var RootUrl = "http://localhost:50691/";
var Data1=null;


function initialize() {
    var mapProp = {
        center: myCenter,
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

   
    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

    var bounds = new google.maps.LatLngBounds();

    function SetMarker(latitude, longitude) {

        var myCenter = new google.maps.LatLng(latitude, longitude);

        var image = 'images/ToroChido.png';

        var marker = new google.maps.Marker({
            position: myCenter,
            icon: image
        });


        marker.setMap(map);

        //you can have a loop here of all you marker points
        //begin loop
        bounds.extend(myCenter);
        //end loop


    }

    function getMarkers() {
        $.ajax({
            url: RootUrl + "api/GeoData",
            type: "GET",
            cache: false,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {

                Data1 = data;
                console.log(data);

                $.each(data, function (index, item) {
                    //console.log("Add Marker");
                    //console.log(item);

                    SetMarker(item.latitude, item.longitude);
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
  
    getMarkers();

    map.setZoom(24);
    map.fitBounds(bounds);    
    
    google.maps.event.addListenerOnce(map, 'bounds_changed', function (event) {        
        this.setZoom(this.getZoom() -1);        
    });

    google.maps.event.addListener(map, 'click', function (event) {
        placeMarker(event.latLng);
    });

    function placeMarker(location) {
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });

        //map.setCenter(location);
    }
    
}


google.maps.event.addDomListener(window, 'load', initialize);




