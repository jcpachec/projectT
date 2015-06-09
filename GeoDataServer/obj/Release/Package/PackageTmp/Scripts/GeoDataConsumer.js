var myCenter = new google.maps.LatLng(20.6583075, -103.4420149);
var RootUrl = ''
var Data1=null;


function initialize() {
    var mapProp = {
        center: myCenter,
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

   
    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

    var bounds = new google.maps.LatLngBounds();    

    map.setCenter(myCenter);


    function SetMarker(latitude, longitude, description, categoryId) {

        var myCenter = new google.maps.LatLng(latitude, longitude);
        var marker;
        if (categoryId == 1) {
            var image = 'images/ToroChido.png';

            marker = new google.maps.Marker({
                position: myCenter,
                icon: image,
                title: description
            });
        }
        else
        {
            marker = new google.maps.Marker({
                position: myCenter,               
                title: description
            });
        }


        marker.setMap(map);

        //you can have a loop here of all you marker points
        //begin loop
        bounds.extend(myCenter);
        //end loop
    }

    function SetMarkers(latitude, longitude, description) {

        $.ajax({
            url: RootUrl + "api/GeoData",
            type: "POST",
            cache: false,
            data: JSON.stringify({ description: description, latitude: latitude, longitude: longitude, time: "2015-05-20T23:24:27.279449-05:00", remarks: ".", categoryId:"1" }),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                
            },
            error: function (result, errorText, errorThrown) {
                alert(result.responseText + errorText + errorThrown);
            },
            beforeSend: function () {
                
            },
            complete: function () {
                
            },
        });
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
                                    
                    SetMarker(item.latitude, item.longitude, item.description, item.categoryId);
                });              

                var latlngbounds = new google.maps.LatLngBounds();

                map.setCenter(latlngbounds.getCenter());
                map.fitBounds(bounds);

                google.maps.event.addListenerOnce(map, 'bounds_changed', function (event) {
                    console.log("bounds_changed");
                    this.setZoom(this.getZoom() -1);        
                });

            },
            error: function (result, errorText, errorThrown) {
                alert(result.responseText + errorText + errorThrown);
            },
            beforeSend: function () {

            },
            complete: function () {

            },
        });
    }
  
    getMarkers();
     

    google.maps.event.addListener(map, 'click', function (event) {
        placeMarker(event.latLng);
    });

    function placeMarker(location) {
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });


        SetMarkers(location.lat(), location.lng(), "test");
        
    }
    
}


google.maps.event.addDomListener(window, 'load', initialize);




