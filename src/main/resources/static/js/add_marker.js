//generate map function
// new location parameter needs to be used to generate a map
// add a marker on a map object
// from for adding markers (name and submit)
//TODO Map with add marker ability
//TODO Ablity to set location
let map;
function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {center: {lat: 38.624632, lng: -90.184770}, zoom: 4});
}

//helper function add a marker
function addMarker(clickLocation){
    let marker = new google.maps.Marker({
        position: clickLocation, map
    });
}

window.addEventListener("load", function () {
   google.maps.event.addListener(map, "click", function(event){ //or event =>
        clickLocation = event.latLng;
        alert(clickLocation);
        addMarker(clickLocation);
    })
})

