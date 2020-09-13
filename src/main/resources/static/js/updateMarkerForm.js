//populate form values with correct marker info
    let markers = [];
    let myfile = new File([], "noUpdate.jpg");

window.addEventListener("load", getMyMarkers())

function getMyMarkers() {

    let allMarkerURl = "/api/markers/user/allMarkers";
        fetch(allMarkerURl)
        .then(response => response.json())
        .then(data => {
            data.forEach(marker => markers.push(marker));
            addMarkers();
            addMarkerListener();
        });


    //if no markers were found -- "no marker message"
    function addMarkers() {
    if(markers.length == 0) {
        document.querySelector("#viewMarkerMessage").innerHTML="You have no markers";
        return;
    }

    //if markers were found -- add them to the map
    markers = markers.map(mapMarker => {
        let lat = mapMarker.lat;
        let lng = mapMarker.lng;
        let myLatlng = new google.maps.LatLng(lat,lng);
        return new google.maps.Marker({
                markerId: mapMarker.id,
                title: mapMarker.name,
                position: myLatlng,
                description: mapMarker.description,
                imageName: mapMarker.imageName,
                map
                });
    });
    }
};

function addMarkerListener() {
    markers.forEach(mapMarker => {
        mapMarker.addListener("click",  () => {
            let markerForm = document.querySelector("#update-marker");
            let formId = markerForm.querySelector("[name='id']");
            let formName = markerForm.querySelector("[name='markerName']");
            let formDescription = markerForm.querySelector("[name='description']");
            let formLat = markerForm.querySelector("[name='latitude']");
            let formLng = markerForm.querySelector("[name='longitude']");
            let formImage = markerForm.querySelector("[name='image']");
            let previewImage = markerForm.querySelector("#updateImage");
            formId.value = mapMarker.markerId;
            formName.value = mapMarker.title;
            formDescription.value = mapMarker.description;
            formLat.value = mapMarker.position.lat();
            formLng.value = mapMarker.position.lng();
            if(mapMarker.imageName != "no_image_picked") {
                previewImage.src = "http://localhost:8080/api/download/" + mapMarker.imageName;
            }

            document.querySelector('#deleteMarkerId').value = mapMarker.markerId;

            let errorMessages = document.querySelectorAll("p[name='error']");
            Array.prototype.forEach.call(errorMessages, function( node ) {
                node.parentNode.removeChild( node );
            });
        });
    });
}

