//populate form values with correct marker info
    let markers = [];
    let setOfMarkers = document.querySelectorAll('table[name="marker"]');

function getMyMarkers() {

    //if no markers were found -- "no marker message"
    if(setOfMarkers.length == 0) {
        document.querySelector("#viewMarkerMessage").innerHTML="You have no markers";
        return;
    }

    //if markers were found -- add them to the map
    setOfMarkers.forEach(mapMarker => {
        let lat = mapMarker.querySelector('td[name="y"]').innerHTML;
        let lng = mapMarker.querySelector('td[name="x"]').innerHTML;
        let myLatlng = new google.maps.LatLng(lat,lng);
        markers.push(
            new google.maps.Marker({
                markerId: mapMarker.id,
                title: mapMarker.querySelector('td[name="name"]').innerHTML,
                position: myLatlng,
                map
            })
        );
    });
};

window.addEventListener("load", getMyMarkers())

function addMarkerListener() {
    markers.forEach(mapMarker => {
        mapMarker.addListener("click",  () => {
            let activeMarker = mapMarker;
            let markerForm = document.querySelector("#update-marker");
            let formId = markerForm.querySelector("[name='id']")
            let formName = markerForm.querySelector("[name='markerName']")
            let formLat = markerForm.querySelector("[name='latitude']")
            let formLng = markerForm.querySelector("[name='longitude']")
            formId.value = activeMarker.markerId;
            formName.value = activeMarker.title;
            formLat.value = activeMarker.position.lat();
            formLng.value = activeMarker.position.lng();

            document.querySelector('#deleteMarkerId').value = activeMarker.markerId;

            let errorMessages = document.querySelectorAll("p[name='error']");
            Array.prototype.forEach.call(errorMessages, function( node ) {
                node.parentNode.removeChild( node );
            });
        });
    });
}
window.addEventListener("load", addMarkerListener());

