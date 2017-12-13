
function pushThePage(page){
    document.querySelector('#globalNavigator').pushPage('./html/'+page);
}

var favs = {}

function fillMyFavs(category){

    var myfavsdiv = $('#myfavs');
    myfavsdiv.innerHTML = "";

    for (placeid in favs) {

        if (favs[placeid]['category'] == category){

            var name = favs[placeid]['name'];
            var address = favs[placeid]['address'];
            var priceIcons = Array(favs[placeid]['price']).join("€");

            var currentfavdiv = document.createElement('div');
            currentfavdiv.className = 'row';
            currentfavdiv.innerHTML = "" +
                "<ons-card> " +
                "<img src='http://www.gqmagazine.fr/uploads/images/thumbs/201512/57/hipster_sait_faire_un_bon_caf___5108.jpeg_north_640x425_transparent.jpg' alt='Onsen UI' style='width: 100%'> " +
                "<div class='title'> " +
                name +
                "</div> " +
                "<div class='content'>" +
                address +
                "<br>" +
                priceIcons +
                "</div> " +
                "</ons-card>";
            myfavsdiv.appendChild(currentfavdiv);
        }
    }
}

var placeSearch, autocomplete, geocoder;

function initAutocomplete() {

    // What about using Places instead of Maps?

    navigator.geolocation.getCurrentPosition(function(position){
        var position = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    }, onError);

    geocoder = new google.maps.Geocoder();
    var input = document.getElementById('autocomplete');
    //var options = {types:['cafe','bar','bakery','restaurant']};
    var options = {types:['establishment']};
    autocomplete = new google.maps.places.Autocomplete(input,options);
    autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {

    var div =$('#placenameaddfav');
    div.innerHTML = "<h2><ons-icon icon='caret-up'></ons-icon>";

    var place = autocomplete.getPlace();
    currentPlace = place;

    //alert(place.place_id);
    //window.open(place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 640}));
    //codeAddress(document.getElementById('autocomplete').value);

    var div =$('#placenameaddfav');
    div.innerHTML = "<h2>"+place.name+"</h2>";

    $('#cbbar').disabled = false;
    $('#cbbrunch').disabled = false;
    $('#cbcafe').disabled = false;
    $('#rhate').disabled = false;
    $('#rlike').disabled = false;
    $('#rlove').disabled = false;
    $('#validfav').disabled = false;

}

function codeAddress(address) {
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == 'OK') {
            alert(results[0].geometry.location);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

function onError(error) {
    alert('code: '    + error.code    + '\n' + 'message: ' + error.message + '\n');
}

function getGeniusRecommendation(){

    theAxios().post('/genius/get', {
        username: username
    })
        .then(function (response) {
            console.log("RESPONSE ");
            console.log(response.data);
            drawGeniusRecommendation(response.data.place_name,response.data.categories,response.data.google_place_id);
        })
        .catch(function (error) {
            console.log(error);
        });
}

var currentPlace;

function drawGeniusRecommendation(place_name,place_categories,google_place_id) {

    service = new google.maps.places.PlacesService(document.createElement('div'));
    service.getDetails({placeId: google_place_id}, callback);

    function callback(place, status) {

        if (status == google.maps.places.PlacesServiceStatus.OK) {

            console.log(currentPlace);
            var recommendationDiv = $('#recommendation');
            recommendationDiv.innerHTML = "";

            var address = place.formatted_address;

            var googleDirectionLink = "\"" + place.url + "\"";
            var formatted_place_categories = place_categories.replace(",", " | ");
            formatted_place_categories = "<div style='color:lightgray'>" + formatted_place_categories + "</div>";


            var innerRecommendationDiv = document.createElement('div');
            innerRecommendationDiv.className = 'row';
            innerRecommendationDiv.innerHTML = "" +
                "<ons-card> <img src=" +
                place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 640}) +
                " alt='Illustration' style='width: 100%'> " +
                "<div class='title'> " +
                place_name +
                "</div> " +
                formatted_place_categories +
                "<div class='content'>" +
                address +
                "<br>" +
                "<ons-button modifier='quiet' style='font-size:inherit;padding-left:0' onclick='window.open(" +
                googleDirectionLink +
                ");'>Y aller</ons-button>" +
                "</div></ons-card>";

            recommendationDiv.appendChild(innerRecommendationDiv);
        }
    }
}

function postNewFav() {

    if ($('#cbbar').checked || $('#cbcafe').checked || $('#cbbrunch').checked) {

        console.log(currentPlace);
        var currentPlaceName = currentPlace.name;
        var currentPlaceId = currentPlace.place_id;
        var currentPlaceCategories = "";
        var userScore = 1.0;
        if ($('#cbbar').checked) {
            currentPlaceCategories += 'Bar,';
        }
        if ($('#cbcafe').checked) {
            currentPlaceCategories += 'Café,';
        }
        if ($('#cbbrunch').checked) {
            currentPlaceCategories += 'Brunch,';
        }
        if ($('#rhate').checked) {
            userScore = -1.0
        } else if ($('#rlove').checked) {
            userScore = 3.0
        }

        //Remove last coma
        currentPlaceCategories = currentPlaceCategories.slice(0, -1);

        theAxios().post('/favorites/add', {
            place_name: currentPlaceName,
            google_place_id: currentPlaceId,
            place_categories: currentPlaceCategories,
            user_like: userScore
        })
            .then(function (response) {
                console.log(response);
                if (response.data === serverMessages['OK']) {
                    pushThePage("main.html");
                } else {
                    ons.notification.toast({message: serverMessages[response.data], timeout: 2000});
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    }
}