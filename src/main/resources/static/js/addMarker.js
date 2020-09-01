let myMarker;

window.addEventListener("load", function () {
   google.maps.event.addListener(map, "click", event => {
        addMarker(event.latLng);
    })
})

//helper function add a marker - if marker is present just move it
function addMarker(clickLocation){
    //if marker not present add a new one
    if(myMarker == null) {
        myMarker = new google.maps.Marker({
            position: clickLocation,
            map,
            draggable: true
        });
        return;
    }
    //if marker present just move it
    myMarker.setPosition(clickLocation);

}

//called from submit event
function setLngLat() {
        if(myMarker == null){
            let event = window.event;
            document.querySelector("#addMarkerMessage").innerHTML="Click a location on the map to set marker."
            event.preventDefault();
        return;
        }
        document.getElementById("latitude").value = myMarker.getPosition().lat();
        document.getElementById("longitude").value = myMarker.getPosition().lng();
}