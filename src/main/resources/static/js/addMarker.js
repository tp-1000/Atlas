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

//called from change (html-file-input)
function preview(input) {
    //make new reader if there is a file to work with
    if(input.files && input.files[0]) {
        let reader = new FileReader();

        //set up reader --- triggered each time the loading event completes
        reader.onload = function (event){
            //file->blob->image->resize->canvas->compressed image
            // image is created from file, think invisible preview (properties but unrednered)
            let blob = new Blob([event.target.result]);
            //create url Handle
            let blobURLHandle = window.URL.createObjectURL(blob);

            //may not be needed, if i only want to display the compressed image
            let workingImg = new Image(); //empty constructor
            workingImg.src = blobURLHandle; // same process will be done on the final preview with the rendered src #updateImage
            workingImg.onload = function() {
                let previewImage = document.querySelector("#updateImage")
                previewImage.src = resizeImg(workingImg);

                // add to form as (raw) DTO takes a raw and converts to image file.
                let newinput = document.createElement("input");
                newinput.type = 'hidden';
                newinput.name = 'imageAdd';
                newinput.value = previewImage.src; // put result from canvas into new hidden input
                document.querySelector('#create-marker').appendChild(newinput);
            }

            //
//            //set src of image tag with result of uploaded image event(uploaded image, not sure
//            document.querySelector("#updateImage").setAttribute("src", event.target.result);
        };

        //once the file is uploaded onload event handler, read it. once its read, set it for image scr
        reader.readAsArrayBuffer(input.files[0])

        //reader.readAsDataURL(input.files[0]);
    }
};

function resizeImg(img) {
    let canvas = document.createElement('canvas');

    canvas.height = img.height;
    canvas.width = img.width;

    let ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0, img.width, img.height);

    let stepAmount = 0;
    let long, short, canvasNew;

    if(img.width > img.height) {
       long = "width";
       short = "height";
       stepAmount = Math.round((img.width-300)*.1);
    } else {
       long = "height";
       short = "width";
       stepAmount = Math.round((img.height-300)*.1)
    }

    //initial draw.. pass into function (it will be a non scaled canvas"image")
    //we will also know the increment amount at this time and pass it in as well
    function lessDestructionResize(canvas, longestSide, shortestSide, amount) {
        canvasNew = document.createElement('canvas');
//this does nothing because conditional set to true.
//if run it will step down the size slowly (see above -- set stepAmount -- result is blurry and maybe not an improvement
        if(canvas[longestSide] - amount <= 300 || amount <= 30 || true) {
            //final setting of sides (remember the ratio)
            canvasNew[shortestSide] = Math.round(canvas[shortestSide] * (300/canvas[longestSide]));
            canvasNew[longestSide] = 300;

            //set canvas for drawing
            let ctx = canvasNew.getContext("2d");
            ctx.drawImage(canvas, 0, 0, canvasNew.width, canvasNew.height);

            return canvasNew.toDataURL("image/jpeg", 0.8);
        }

        canvasNew[shortestSide] = Math.round(canvas[shortestSide] * ((canvas[longestSide]-amount)/canvas[longestSide]));
//        canvasNew[shortestSide] = canvas.250 * (400-30)/400);
        canvasNew[longestSide] = canvas[longestSide] - amount;
        let ctx = canvasNew.getContext("2d");
        ctx.drawImage(canvas, 0, 0, canvasNew.width, canvasNew.height);





        return lessDestructionResize(canvasNew, longestSide, shortestSide, amount);
    }


    return lessDestructionResize(img, long, short ,stepAmount);
}

//
//
//    //ratio resize but incrementally to preserve quality
//    // find out amount = 10% of size,
//    //loop lower current size-amount
//    //redraw on canvas
//    //stop if the next iteration would be below 300 and set height/width to 300
//
////    if(width > height) {
////        //only resize if original is bigger
////        if (width > 300) {
////            height = Math.round(height *= 300/width);
////            width = 300;
////        }
////    } else {
////        //only resize if original is bigger
////        if(height > 300) {
////            width = Math.round(width *= 300/height)
////            height = 300;
////        }
////    }
////
////    //draw image on canvas
////    canvas.width = width;
////    canvas.height = height;
////    //set canvas for drawing
////    let ctx = canvas.getContext("2d");
////    ctx.drawImage(img, 0, 0, width, height);
////
////    //set type , compression is recommended 6-8
////    //96 DPI
////    return canvas.toDataURL("image/jpeg",0.92);
//
//
//
//function dataURLtoBlob(dataurl) {
//    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
//        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
//    while(n--){
//        u8arr[n] = bstr.charCodeAt(n);
//    }
//    return new Blob([u8arr], {type:mime});
//}