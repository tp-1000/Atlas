// TODO add way to handle bad zipcode data
function setLocationWithZipcode(key) {
    let zipcode = document.getElementById("zip").value
    let getZipURL = "https://maps.googleapis.com/maps/api/geocode/json?key=" + key + "&components=postal_code:" + zipcode;

    fetch(getZipURL)
    .then(response => response.json())
    .then(data => {
        map.setCenter(data.results[0].geometry.location);
        map.setZoom(15);
        }
    );

    event.preventDefault();
}