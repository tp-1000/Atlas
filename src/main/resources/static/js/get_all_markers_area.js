function getMarkers() {
let allMarkers = document.querySelectorAll("object")

allMarkers.forEach(mapMarker => {
    let myLatlng = new google.maps.LatLng(mapMarker.childNodes[3].value,mapMarker.childNodes[5].value);
    new google.maps.Marker({
        position: myLatlng, map
    });
})
}
getMarkers();



