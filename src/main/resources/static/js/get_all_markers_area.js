let circleMap;
let setOfMarkers;
//get all objects( named marker)

window.addEventListener('load', getMyMarkers())

function getMyMarkers() {
    setOfMarkers = document.querySelectorAll('[name="marker"]');
    //so when the view is called it comes with insturctions, second time around there is nothing and the text is added
    if(setOfMarkers.length == 0 && document.querySelector("#message").innerHTML.length <1 ) {
        document.querySelector("#message").innerHTML="No markers found for that area. Select a new area and press Set epicenter";
        return;
    }

    setOfMarkers.forEach(mapMarker => {
        let myLatlng = new google.maps.LatLng(mapMarker.childNodes[5].value,mapMarker.childNodes[3].value);
        new google.maps.Marker({
            position: myLatlng, map
        });
    })
}


//Custom input for string -  format for (query string)
function generateQueryString() {
     let location = circleMap.getCenter();

     let locationField = document.querySelector("#location");
     locationField.value = `SRID=4326;POINT(${location.lng()} ${location.lat()})`;
                    //needs to match this 'SRID=4326;POINT(-90.350927 38.588407)'
}

//Add circle to illustrate search zone
function epicenter(event) {
//        if(setOfMarkers.length > 0) {
//            return;
//        }
        if(circleMap == null) {
            circleMap = new google.maps.Circle({
            center: new google.maps.LatLng(event.latLng.lat(),event.latLng.lng()),
            strokeColor: "#FF0000",
            strokeOpacity: 0.75,
            strokeWeight: 2,
            draggable: true,
            geodesic: true,
            map,
            radius: 1000
        });
        } else
        {
          circleMap.setCenter(event.latLng);
        }
        map.panTo(event.latLng);
        if(map.getZoom() < 13) {
            map.setZoom(13);
        }
}




//event listener for map
function mapRespondsToClick() {
    //map = document.querySelector("#map");
    google.maps.event.addListener(map, "click", event => {
        epicenter(event);
        })
}


mapRespondsToClick();



//set up to protect pushing API to get
function setLocationWithZipcode(key) {
// TODO add way to handle bad zipcode data this is repeated code
    let zipcode = document.getElementById("zip").value;
    let getZipURL = "https://maps.googleapis.com/maps/api/geocode/json?key=" + key + "&components=postal_code:" + zipcode;

    fetch(getZipURL)
    .then(response => response.json())
    .then(data => {
        map.setCenter(data.results[0].geometry.location);
        map.setZoom(15);
        }
    );

    event.preventDefault();
}
