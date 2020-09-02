    let markers = [];
    let setOfMarkers = document.querySelectorAll('[name="marker"]');
function getMyMarkers() {

    //if no markers were found -- "no marker message"
    if(setOfMarkers.length == 0) {
        document.querySelector("#viewMarkerMessage").innerHTML="No markers found for that area. Select a new area and press Set epicenter";
        return;
    }

    //if markers were found -- add them to the map
    setOfMarkers.forEach(mapMarker => {
        let lat = mapMarker.querySelector('[name="y"]').value;
        let lng = mapMarker.querySelector('[name="x"]').value;
        let myLatlng = new google.maps.LatLng(lat,lng);
        markers.push(
            new google.maps.Marker({
                markerId: mapMarker.querySelector('[name="id"]').value,
                title: mapMarker.querySelector('[name="name"]').value,
                position: myLatlng,
                map
            })
        );
    });
};
//    TODO make it so map doesn't reload, only request of Markers is completed. Call it on the end of the "Fetch"


window.addEventListener("load", getMyMarkers())

//Add circle to illustrate search zone
let circleMap;
function epicenter(event) {

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
    google.maps.event.addListener(map, "click", event => {
        epicenter(event);
        })
}


mapRespondsToClick();



//Custom input for string -  format for (query string) (called with button click)
function generateQueryString() {
     let location = circleMap.getCenter();

     //needs to match this format 'SRID=4326;POINT(-90.350927 38.588407)'
     document.querySelector("#location").value= `SRID=4326;POINT(${location.lng()} ${location.lat()})`;
}

//marker info helper window -- add click listener that displays the info for the marker
//to be called as they are created
function addMarkerListener() {
    markers.forEach(mapMarker => {
        mapMarker.addListener("click",  () => {
            let activeMarker = event.target;
            document.querySelector("#markerInfo").innerHTML = activeMarker.title;
        });
    });
}
window.addEventListener("load", addMarkerListener());
