//populate form values with correct marker info
    let markers = [];
//    let myfile = new File([], "noUpdate.jpg");
    let isMenuVisible = false;
    let selectedMarker;


window.addEventListener("load", () => {
    getMyMarkers();
    document.querySelector("#tabList").addEventListener("click", updateInfoCard);



    let radioTabs = document.querySelectorAll("input[name='menu']");
        radioTabs.forEach(radio => radio.addEventListener('change', tabToggle));

    })

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
        reGrabImageIfErrors();
    }
};


function addMarkerListener() {
    markers.forEach(mapMarker => {
       mapMarker.addListener("click", () => updateMarkerInfo(mapMarker));
    }); //use
        //mapMarker.addListener("click",  () => {
//            let markerForm = document.querySelector("#update-marker");
//            let formId = markerForm.querySelector("[name='id']");
//            let formName = markerForm.querySelector("[name='markerName']");
//            let formDescription = markerForm.querySelector("[name='description']");
//            let formLat = markerForm.querySelector("[name='latitude']");
//            let formLng = markerForm.querySelector("[name='longitude']");
//            let formImage = markerForm.querySelector("[name='image']");
//            let previewImage = markerForm.querySelector("#updateImage");
//            formId.value = mapMarker.markerId;
//            formName.value = mapMarker.title;
//            formDescription.value = mapMarker.description;
//            formLat.value = mapMarker.position.lat();
//            formLng.value = mapMarker.position.lng();
//            if(mapMarker.imageName != "no_image_picked") {
//                previewImage.src = "http://localhost:8080/api/download/" + mapMarker.imageName;
//            }

    //document.querySelector('#deleteMarkerId').value = mapMarker.markerId;


};
//    });
//}


//toggle menu
function hMenuShow(){
    isMenuVisible = !isMenuVisible;
    let hMenu = document.getElementById("hMenu");
    hMenu.classList.toggle("hidden");
    if(isMenuVisible) {
       tabToggle();
    } else {
        let tabs = document.getElementsByName("tab-toggle");
        for(let tab of tabs) {
           tab.classList.add("hidden");
           }
    }

};
//toggle tabs
function tabToggle(){
    //hide all tabs
    //use radio input
    let tabId = document.querySelector("input[name='menu']:checked").id;

    //hide all tabs if its matches unhide
    let tabs = document.getElementsByName("tab-toggle");
    for(let tab of tabs) {
       tab.classList.add("hidden");
       if(("tab" + tabId).toLowerCase() === tab.id.toLowerCase()) {
            tab.classList.toggle("hidden");
       }
    }
};

function preview(input) {
    //make new reader if there is a file to work with
    if(input.files && input.files[0]) {
        let reader = new FileReader();

        //set up reader --- triggered each time the loading event completes
        reader.onload = function (event){
            //set src of image tag with result of uploaded image event(uploaded image, not sure
            document.querySelector("#updateImage").setAttribute("src", event.target.result);
        };

        //once the file is uploaded, read it. once its read, set it for image scr
        reader.readAsDataURL(input.files[0]);
    }
};

//update card with clicked marker from list
function updateInfoCard(event) {
    //event target's id is used to find a marker match
    let markerId = event.target.id;
    let marker;
    for(let i=0; i < markers.length; i++) {
        if(markers[i].markerId == markerId) {
            marker = markers[i];
            break;
        }
    }
    //update infocard with new info
    updateMarkerInfo(marker);

}

//activated from a click
function updateMarkerInfo(mapMarker) {
//active marker bounce
    let errorMessages = document.querySelectorAll("p[name='error']");
    Array.prototype.forEach.call(errorMessages, function( node ) {
        node.parentNode.removeChild( node );
    });

    if(selectedMarker){
        selectedMarker.setAnimation(null);
    }
    selectedMarker = mapMarker;
    selectedMarker.setAnimation(google.maps.Animation.BOUNCE);

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
    if(mapMarker.imageName !== "no_image_picked") {
      previewImage.src = "http://localhost:8080/api/download/" + mapMarker.imageName;
    } else {
      previewImage.src = "";
    }

   //make sure menu is visible
    if(!isMenuVisible) {
        hMenuShow();
    };
    //switch cards to see info
    document.querySelector("input[id='mark']").checked="checked";
    tabToggle();
}

//get name off map marker and grabs image
function reGrabImageIfErrors() {
    if(document.getElementsByName("error").length > 0){
        //show update error
        hMenuShow();
        document.querySelector("input[id='mark']").checked="checked";
        tabToggle();

        //id from form is used to find the image name off the loaded map marker
        let id = document.getElementById("id").value;

        let tempMarker;

        for(let j = 0; j < markers.length; j++) {
                if(markers[j].markerId == id) {
                    tempMarker = markers[j];
                    break;
                }
            }

        let errorPreviewImage = document.getElementById("updateImage");

        if(tempMarker.imageName !== "no_image_picked") {
              errorPreviewImage.src = "http://localhost:8080/api/download/" + tempMarker.imageName;
            } else {
              previewImage.src = "";
            }

        //selected marker needs to bounce
        selectedMarker = tempMarker;
        selectedMarker.setAnimation(google.maps.Animation.BOUNCE);
    }
}
