let temp = []; //all markers
let circleMap; //a circle on the map
let isMenuVisible = false;
let isInfoVisible = false;
let selectedMarker;

window.addEventListener("load", () => {
    epicenter();
    document.querySelector("#markerList").addEventListener("click", updateInfoCard);



});

//toggle menu
function hMenuShow(){
    isMenuVisible = !isMenuVisible;
    let hMenu = document.getElementsByName("hMenu");
    let hMenuBtn = document.getElementById("hMenuBtn")
    hMenuBtn.classList.toggle("ml-64");
    for (let mDiv of hMenu) {
            mDiv.classList.toggle("hidden");
    }
};
//toggle tabs
function tabToggle(){
    isInfoVisible = !isInfoVisible;
    let tabs = document.getElementsByName("tab-toggle");
    let tab_mask = document.getElementById("tab_mask");
    tab_mask.classList.toggle("ml-32");
    for (let tab of tabs) {
            tab.classList.toggle("hidden");
    }
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
    document.querySelector("#markInfo_contents").classList.remove("hidden");
    document.querySelector("#miName").innerHTML = "";
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
        if(!isInfoVisible) {
            tabToggle();
        };
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
    let allMarkerURl = "/api/markers/search?lat=" + circleLat + "&lng="+ circleLng;
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
//             clean out old markers
             while(document.querySelector("#markerList").firstChild) {
                            document.querySelector("#markerList").removeChild(document.querySelector("#markerList").firstChild);
                            document.querySelector("#markInfo_contents").classList.toggle("hidden");
                            document.querySelector("#miName").innerHTML = "";
                        }

            temp.forEach(mapMarker => {

                let newB = document.createElement("button");
                document.querySelector("#markerList").append(newB);
                newB.innerHTML = mapMarker.title;
                newB.setAttribute('class', "bg-gray-100 pl-5 h-8 font-light text-left pr-5 hover:bg-blue-600 hover:text-white hover:font-medium");
                newB.setAttribute('id', mapMarker.markerId);
                mapMarker.addListener("click", () => updateMarkerInfo(mapMarker));
            });
            //opens the menu to see the list of markers
            if(! isMenuVisible) {
                hMenuShow();
            };
            //switch cards to see info
            if(isInfoVisible) {
                tabToggle();
            };

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
            strokeColor: "#FF0000",
            strokeOpacity: 0.75,
            strokeWeight: 2,
            draggable: true,
            geodesic: true,
            map,
            radius: 1000
        });
        markerSearch();
        circleMap.addListener("center_changed", markerSearch);
        } else {
          circleMap.setCenter(event.latLng);
        }
        map.panTo(event.latLng);
        if(map.getZoom() < 13) {
            map.setZoom(13);
        }
    });



}


