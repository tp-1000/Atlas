//generate map function
// new location parameter needs to be used to generate a map
// add a marker on a map object
// from for adding markers (name and submit)
//TODO Map with add marker ability
//TODO Ablity to set location
let map, marker, clickLocation;
function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {center: {lat: 38.624632, lng: -90.184770}, zoom: 4});
}

//helper function add a marker - if marker is present just move it
function addMarker(clickLocation){
    if(marker == null) {
        marker = new google.maps.Marker({
            position: clickLocation,
            map,
            draggable: true
        });
        return;
    }
    marker.setPosition(clickLocation);

}

//set up to protect pushing API to get
function setLocationWithZipcode(key) {

// TODO add way to handle bad zipcode data
    let zipcode = document.getElementById("zip").value
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

window.addEventListener("load", function () {
   google.maps.event.addListener(map, "click", function(event){ //or event => .. { for multi line
        clickLocation = event.latLng;
        addMarker(clickLocation);
    })
})


function setLngLat() {
        if(clickLocation == null){
            let event = window.event;
            document.querySelector("#message").innerHTML="Click a location on the map to set marker."
            event.preventDefault();
        return;
        }
        document.getElementById("latitude").value = clickLocation.lat();
        document.getElementById("longitude").value = clickLocation.lng();
}
