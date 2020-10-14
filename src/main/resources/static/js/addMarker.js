let myMarker;
let isMenuVisible = false;


window.addEventListener("load", function () {
   document.querySelector("#create-marker [name = 'latitude']").value = "";
   document.querySelector("#create-marker [name = 'longitude']").value = "";

   google.maps.event.addListener(map, "click", event => {
        //switch cards to see info
        addMarker(event.latLng);
        document.querySelector("input[id='add']").checked="checked";
        if(! isMenuVisible){
            hMenuShow();
        } else {
            tabToggle();
        }
    })

    let radioTabs = document.querySelectorAll("input[name='menu']");
    radioTabs.forEach(radio => radio.addEventListener('change', tabToggle));

    if(document.getElementsByName("error").length > 0){
            //show update error
            hMenuShow();
            document.querySelector("input[id='add']").checked="checked";
            tabToggle();
     }
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
            document.querySelector("#addMarkerMessage").innerHTML="Click map to set marker location"
            event.preventDefault();
        return;
        }
        document.getElementById("latitude").value = myMarker.getPosition().lat();
        document.getElementById("longitude").value = myMarker.getPosition().lng();
}

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

    //hide all tabs - if it matches unhide
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