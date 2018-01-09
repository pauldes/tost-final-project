
function pushThePage(page){
    document.querySelector('#globalNavigator').pushPage('./html/'+page);
}

var favs = {}

function getFavs() {

    theAxios().post('/favorites/get', {
        username: username
    })
        .then(function (response) {
            console.log(response.data);
            favs = response.data;
            fillMyFavs("Bar");
        })
        .catch(function (error) {
            console.log(error);
        });
}

function fillMyFavs(category) {

    var myfavsdiv = $('#myfavs');
    myfavsdiv.innerHTML = "";


    for (i = 0; i < favs.length; i++) {

        if (favs[i].place_categories.indexOf(category) !==-1 ) {

            var place_name = favs[i].place_name;
            var google_place_id = favs[i].google_place_id;
            var place_categories = favs[i].place_categories;

            service = new google.maps.places.PlacesService(document.createElement('div'));
            service.getDetails({placeId: google_place_id}, callback);

            function callback(place, status) {

                if (status == google.maps.places.PlacesServiceStatus.OK) {

                    console.log(place);

                    var address = place.formatted_address;
                    var googleDirectionLink = "\"" + place.url + "\"";
                    var formatted_place_categories = place_categories.replace(",", " | ");
                    var placeName = place.name;

                    formatted_place_categories = "<div style='color:lightgray'>" + formatted_place_categories + "</div>";

                    //var currentFavDiv = document.createElement('div');
                    //currentFavDiv.className = 'row';

                    //currentFavDiv.innerHTML =
                    myfavsdiv.innerHTML +=
                        "<ons-card> " +
                        //"<img src=" +
                        //place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 320}) +
                        //" alt='Illustration' style='width: 100%'> " +
                        "<div class='title'> " +
                        placeName +
                        "</div> " +
                        formatted_place_categories +
                        "<div class='content'>" +
                        address +
                        "<br>" +
                        "<ons-button modifier='quiet' style='font-size:inherit;padding-left:0' onclick='window.open(" +
                        googleDirectionLink +
                        ");'>Y aller</ons-button>" +
                        "</div></ons-card>";

                    //myfavsdiv.appendChild(currentFavDiv);

                }

            }
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

function drawProfilePage(){
    $('#profilename').innerText = username;
}

var autocompletedUsers = {};
var selectedUsers = [];

function addToGroup(userId){
    if(!selectedUsers.includes(userId)){
        selectedUsers.push(userId);
        var pseudo = autocompletedUsers[userId];
        $('#users-in-the-group').innerText += pseudo+", ";
    }
}

function autocompletePseudo(){
    var letters = $('#autocomplete-pseudo').value;

    theAxios().post('/groups/autocomplete', {
        input: letters
    })
        .then(function (response) {
            console.log(response.data);
            autocompletedUsers = response.data;
            drawAutocompletedUsers(autocompletedUsers);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function drawAutocompletedUsers(jsonUsers){
    $('#autocomplete-pseudo-results').innerHTML = "";
    for(userId in autocompletedUsers) {
        userPseudo = autocompletedUsers[userId];
        var line = "<ons-list-item onclick='addToGroup("+userId+")'>"+userPseudo+"</ons-list-item>";
        $('#autocomplete-pseudo-results').innerHTML += line;
    }
}

function postNewGroup(){

    theAxios().post('/groups/create', {
        members: selectedUsers,
        name : "El Famoso Grupo"
    })
        .then(function (response) {
            console.log(response.data);
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