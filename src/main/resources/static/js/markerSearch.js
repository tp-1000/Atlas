let temp = []; //all markers
let circleMap; //a circle on the map
let isMenuVisible = false;
let selectedMarker;

window.addEventListener("load", () => {
    epicenter();
    document.querySelector("#tabList").addEventListener("click", updateInfoCard);
    let radioTabs = document.querySelectorAll("input[name='menu']");
    radioTabs.forEach(radio => radio.addEventListener('change', tabToggle));


});

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

    //hide all tabs , if it matches unhide
    let tabs = document.getElementsByName("tab-toggle");
    for(let tab of tabs) {
       tab.classList.add("hidden");
       if(("tab" + tabId).toLowerCase() === tab.id.toLowerCase()) {
            tab.classList.toggle("hidden");
       }
    }
//
//    //check for target match and toggle hidden
//    markerInfo
//    tabMark
//    setLocation
    //show active tab (which is target
//
//    isInfoVisible = !isInfoVisible;
//    //let tab_mask = document.getElementById("tab_mask");
//   // tab_mask.classList.toggle("ml-32");
//    for (let tab of tabs) {
//            tab.classList.toggle("hidden");
//    }
};

//update card with clicked marker from list
function updateInfoCard(event) {
    //event target's id is used to find a marker match
    let markerId = event.target.id;
    let marker;
    for(let i=0; i < temp.length; i++) {
        if(temp[i].markerId == markerId) {
            marker = temp[i];
            break;
        }
    }
    //update infocard with new info
    updateMarkerInfo(marker);

}

//update marker info
function updateMarkerInfo(marker) {
    if(selectedMarker){
        selectedMarker.setAnimation(null);
    }
    selectedMarker = marker;
    selectedMarker.setAnimation(google.maps.Animation.BOUNCE);
    //document.querySelector("#markInfo_contents").classList.remove("hidden");
    //set mark info page with new marker info
//    document.querySelector("#miName").innerHTML = "";
    document.querySelector("#miId").innerHTML = " ID: " + marker.markerId;
    document.querySelector("#miName").innerHTML = marker.title;
    document.querySelector("#miDescription").innerHTML = marker.description;
    if(marker.imageName !== "no_image_picked") {
        document.querySelector("#miImage").src = "http://localhost:8080/api/download/" + marker.imageName;
    } else {
                document.querySelector("#miImage").src ="";
    }

    //make sure menu is visible
    if(!isMenuVisible) {
        hMenuShow();
    };
    //switch cards to see info
    document.querySelector("input[id='mark']").checked="checked";
    tabToggle();
}




//Click map event needed to trigger function
function markerSearch() {
//context no longer requires the null check
//    if(circleMap == null) {
//        document.querySelector("#viewMarkerMessage").innerHTML = "Set search radius on map";
//        return;
//    }
    temp.forEach(x => x.setMap(null));
    temp = [];


//clear old message
document.querySelector("#viewMarkerMessage").innerHTML="";
//when a point on the map is clicked, its latlag is passed via API path and those markers are returned
// request resources with formatted string -- uses circlecenter
    let circleLat = circleMap.getCenter().lat();
    let circleLng = circleMap.getCenter().lng();
    let radius = circleMap.getRadius();
    let allMarkerURl = "/api/markers/search?lat=" + circleLat + "&lng="+ circleLng + "&radius="+ radius;
        fetch(allMarkerURl)
        .then(response => response.json())
        .then(data => {
            //let temp = [];
            //set marker objects from data
            for(let i = 0; i < data.length; i++){
                let lat = data[i].lat;
                let lng = data[i].lng;
                let myLatlng = new google.maps.LatLng(lat,lng);
                temp.push(new google.maps.Marker({
                    markerId: data[i].id,
                    title: data[i].name,
                    description: data[i].description,
                    position: myLatlng,
                    imageName: data[i].imageName,
                    map
                }));
            };
//             clean out old markers info
                            let oldList = document.querySelectorAll("button[name='listedMarker']");
                            oldList.forEach(entry => entry.remove());
                            document.querySelector("#miId").innerHTML = "";
                            document.querySelector("#miName").innerHTML = "";
                            document.querySelector("#miDescription").innerHTML = "";
                            document.querySelector("#miImage").src ="";
//

            temp.forEach(mapMarker => {

                let newB = document.createElement("button");
                document.querySelector("#tabList").append(newB);
                newB.innerHTML = mapMarker.title;
                newB.setAttribute('class', "bg-white pl-5 h-6 w-full font-light text-left pr-5 hover:bg-blue-600 hover:text-white hover:font-medium last:mb-1");
                newB.setAttribute('id', mapMarker.markerId);
                newB.setAttribute('name', "listedMarker");
                mapMarker.addListener("click", () => updateMarkerInfo(mapMarker));
            });
            //opens the menu to see the list of markers
            if(! isMenuVisible) {
                hMenuShow();
            };
            //switch cards to see info
            document.querySelector("input[id='list']").checked="checked";
            tabToggle();

            if(temp.length == 0) {
                document.querySelector("#viewMarkerMessage").innerHTML="No markers found for that area.";
                return;
            }
        });

}

//Add circle to illustrate search zone
function epicenter() {
    google.maps.event.addListener(map, "click", (event) => {
        if(circleMap == null) {
            circleMap = new google.maps.Circle({
            center: new google.maps.LatLng(event.latLng.lat(),event.latLng.lng()),
            strokeColor: "#3366ff",
            strokeOpacity: 0.75,
            strokeWeight: 2,
            editable: true,
            draggable: true,
            geodesic: true,
            map,
            radius: 1000
        });
        markerSearch();
        circleMap.addListener("center_changed", markerSearch);
        circleMap.addListener("radius_changed", markerSearch);
        } else {
          circleMap.setCenter(event.latLng);
        }
        map.panTo(event.latLng);
        if(map.getZoom() < 13) {
            map.setZoom(13);
        }
    });



}


