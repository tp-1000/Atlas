let markers = []; //all markers
let circleMap; //a circle on the map


window.addEventListener("load", () => {
    epicenter();
    document.querySelector("#search").addEventListener("click", (event) => event.preventDefault());
});

//Click map event needed to trigger function
function markerSearch () {
    let temp = [];

//when a point on the map is clicked, its latlag is passed via API path and those markers are returned
// request resources with formatted string -- uses circlecenter
    let circleLat = circleMap.getCenter().lat();
    let circleLng = circleMap.getCenter().lng();
    let allMarkerURl = "/api/markers/search?lat=" + circleLat + "&lng="+ circleLng;
        fetch(allMarkerURl)
        .then(response => response.json())
        .then(data => {
            let temp = [];
            //set marker objects from data
            for(let i = 0; i < data.length; i++){
                //if the data.id(a marker) isn't present in markers[] add it or break loop if it exists
                block_1: {
                    for(let y = 0; y < markers.length; y++) {
                        if(markers[y].markerId == data[i].id){
                            break block_1;
                         };
                    };
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
            };

            temp.forEach(mapMarker => {
                markers.push(mapMarker);
                mapMarker.addListener("click",  () => {
                    document.querySelector("#miId").innerHTML = " ID: " + mapMarker.markerId;
                    document.querySelector("#miName").innerHTML = " Name: " + mapMarker.title;
                    document.querySelector("#miDescription").innerHTML = "Description: " + mapMarker.description;
                    document.querySelector("#miImage").src = "http://localhost:8080/api/download/" + mapMarker.imageName;
                });
            });
            if(temp.length == 0) {
                document.querySelector("#viewMarkerMessage").innerHTML="No markers found for that area. Select a new area and press Set epicenter";
                return;
            }
        });

}

function continueProcessingSearchRequest(temp){
//    if no markers were found -- "no marker message"
    if(temp.length == 0) {
        document.querySelector("#viewMarkerMessage").innerHTML="No markers found for that area. Select a new area and press Set epicenter";
        return;
    }

//    if markers were found -- add them to the map
    temp = markers.map(mapMarker => {
        let lat = mapMarker.lat;
        let lng = mapMarker.lng;
        let myLatlng = new google.maps.LatLng(lat,lng);
        temp.push(
            new google.maps.Marker({
                markerId: mapMarker.id,
                title: mapMarker.name,
                description: mapMarker.description,
                position: myLatlng,
                imageName: mapMarker.imageName,
                map
            })
        );
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
        } else
        {
          circleMap.setCenter(event.latLng);
        }
        map.panTo(event.latLng);
        if(map.getZoom() < 13) {
            map.setZoom(13);
        }
    });

}


