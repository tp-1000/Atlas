function setLocationWithZipcode(key, textInput) {
    event.preventDefault();
    let zipcode = document.getElementById("zip").value
    let getZipURL = "https://maps.googleapis.com/maps/api/geocode/json?key=" + key + "&components=postal_code:" + zipcode;

    fetch(getZipURL)
    .then(response => response.json())
    .then(data => {
        if(data.status == "ZERO_RESULTS"){
            document.getElementById("zip").value = "Not a valid USA zipcode";
        } else {
            map.setCenter(data.results[0].geometry.location);
            map.setZoom(15);
        }
    });

}
