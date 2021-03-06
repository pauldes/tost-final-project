function pushThePage(page, options){
    document.querySelector('#globalNavigator').pushPage('./html/'+page, options);
}

var favs = {}

var currentPlace;

var autocompletedUsers = {};
var selectedUsers = [];

var placeSearch, autocomplete, geocoder;

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

                    //console.log(place);

                    var address = place.formatted_address;
                    var googleDirectionLink = place.url;
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
                        "<ons-button modifier='quiet' style='font-size:inherit;padding-left:0' onclick='window.open(\"" +
                        googleDirectionLink +
                        "\");'>J'y vais <ons-icon icon=\"ion-log-out\"></ons-icon></ons-button>" +
                        "</div></ons-card>";

                    //myfavsdiv.appendChild(currentFavDiv);

                }

            }
        }
    }
}

function initAutocomplete() {

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

    var p = $('#which_tags');
    p.style.color = "#000";

    $('#cbbar').disabled = false;
    $('#cbbrunch').disabled = false;
    $('#cbcafe').disabled = false;
    $('#rhate').disabled = false;
    $('#rlike').disabled = false;
    $('#rlove').disabled = false;
    $('#validfav').disabled = false;
    $('#cbtag0').disabled = false;
    $('#cbtag1').disabled = false;
    $('#cbtag2').disabled = false;
    $('#cbtag3').disabled = false;
    $('#cbtag4').disabled = false;
    $('#cbtag5').disabled = false;
    $('#cbtag6').disabled = false;
    $('#cbtag7').disabled = false;
    $('#cbtag8').disabled = false;
}

function onError(error) {
    alert('code: '    + error.code    + '\n' + 'message: ' + error.message + '\n');
}

function getGeniusRecommendation(){

    var groupId = document.getElementById("genius-selector").value;

    theAxios().post('/genius/get', {
        group_id: groupId
    })
        .then(function (response) {
            drawGeniusRecommendation(response.data.place_name,response.data.categories,response.data.google_place_id, response.data.target_category);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function drawGeniusRecommendation(place_name,place_categories,google_place_id,target_category) {

    service = new google.maps.places.PlacesService(document.createElement('div'));
    service.getDetails({placeId: google_place_id}, callback);

    function callback(place, status) {

        if (status == google.maps.places.PlacesServiceStatus.OK) {
            var recommendationDiv = $('#recommendation');
            recommendationDiv.removeAttribute("hidden");
            $('#genius-filters').removeAttribute("hidden");

            //Get place attributes
            var address = place.formatted_address;

            var openNow = place.opening_hours.open_now;

            var googleDirectionLink = place.url;
            var formatted_place_categories = place_categories.replace(",", " | ");
            formatted_place_categories = "<div style='color:lightgray'>" + formatted_place_categories + "</div>";

            //Determine Transport Mode
            var transportMode = "WALKING";
            getPathDistance(address, transportMode);

            $('#rpath-bike').onclick = function() {
                transportMode = "BICYCLING";
                getPathDistance(address, transportMode);
            }

            $('#rpath-feet').onclick = function() {
                transportMode = "WALKING";
                getPathDistance(address, transportMode);
            }

            var innerRecommendationDiv = document.createElement('div');
            innerRecommendationDiv.className = 'row';

            //Fill card with place attributes
            $('#card-image-container').innerHTML = "<img src=" +
            place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 640}) +
            " style='width: 100%'>"

            $('#open-now').innerHTML =""
            $('#not-open-now').innerHTML = ""
            if(openNow)
                $('#open-now').innerHTML = "<i>Ouvert</i>";
            else
                $('#not-open-now').innerHTML = "<i>Fermé</i>";

            $('#genius-card-title').innerHTML = place_name;
            $('#genius-card-categories').innerHTML = formatted_place_categories;
            $('#genius-card-address').innerHTML = address;
            $('#genius-go-button').onclick = function(){
                window.open(googleDirectionLink);
            };

            //Determine which place categories was chosen
            if (target_category === "bar") {
                $('#cb-bar-genius').setAttribute("checked");
            } else if (target_category === "cafe") {
                $('#cb-cafe-genius').setAttribute("checked");
            } else if (target_category === "brunch") {
                $('#cb-brunch-genius').setAttribute("checked");
            }

            recommendationDiv.appendChild(innerRecommendationDiv);
        }
    }
}

function getPathDistance(destination, transportMode) {
    $('#path-time').innerHTML = "<ons-icon size='20px' spin icon='ion-load-c'></ons-icon>";

    //Get current position
    navigator.geolocation.getCurrentPosition(
        function (position){
            Latitude = position.coords.latitude;
            Longitude = position.coords.longitude;
            var currentPosition = new google.maps.LatLng(Latitude, Longitude);

            var service = new google.maps.DistanceMatrixService();
            service.getDistanceMatrix(
                {
                    origins : [currentPosition],
                    destinations : [destination],
                    travelMode : transportMode
                }, distanceCallback);
        },
        function (error) {
            console.log(error);
        }, { enableHighAccuracy: true });

    function distanceCallback(res, stat){
        if (stat == 'OK') {
            var origins = res.originAddresses;
            var pathTimeDiv = $('#path-time');

            for (var i = 0; i < origins.length; i++) {
                var results = res.rows[i].elements;
                for (var j = 0; j < results.length; j++) {
                    var element = results[j];
                    var distance = element.distance.text;
                    var duration = element.duration.text;
                    pathTimeDiv.innerHTML = "" +
                        distance +
                        "</br>" +
                        duration;
                }
            }
        }
    };
}

function postNewFav() {

    if ($('#cbbar').checked || $('#cbcafe').checked || $('#cbbrunch').checked) {
        var currentPlaceName = currentPlace.name;
        var currentPlaceId = currentPlace.place_id;
        var currentPlaceCategories = "";
        var userScore = 1.0;
        var addedAll = true;
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
                //console.log(response);
                if (response.data !== serverMessages['OK']) {
                    addedAll = false;
                    ons.notification.toast({message: serverMessages[response.data], timeout: 2000});
                }
            })
            .catch(function (error) {
                console.log(error);
            });

        //Add tag count in database
        var tagsContainer = $('#tags_checkbox');
        for (i=0; i<8; i++) {
            tag = tagsContainer.childNodes[i];
            var currentTagName = tag.innerText;

            if (tag.checked) {
                //Fill table "Tag used for place"
                theAxios().post('/placetag/add', {
                    place_tag_name: currentTagName,
                    place_id: currentPlaceId
                })
                    .then(function(res) {
                        if (response.data !== serverMessages['OK']) {
                            addedAll = false;
                            ons.notification.toast({message: serverMessages[response.data], timeout: 2000});
                        }
                    })
                    .catch(function(err) {
                        console.log(err);
                    });
                //Fill the table "User used tag"
                theAxios().post('/usertag/add', {
                    place_tag_name: currentTagName
                })
                    .then(function(res) {
                        if (response.data !== serverMessages['OK']) {
                            addedAll = false;
                            ons.notification.toast({message: serverMessages[response.data], timeout: 2000});
                        }
                    })
                    .catch(function(err) {
                        console.log(err);
                    });
            }
        }
        if (addedAll === true) {
            pushThePage("main.html");
        } else {
        ons.notification.toast({message: serverMessages[res.data], timeout: 2000});
        }
    }
}

function createTagsCheckbox() {
    var div = $('#tags_checkbox');
    div.innerHTML = '';

    theAxios().get('/randomtags/get')
        .then(function (response) {

            for (i=0; i<response.data.length; i++) {
                var placeTagName = response.data[i]['place_tag_name'];
                //Display nine randomly chosen tags
                div.innerHTML +=
                    "<ons-checkbox class=\"tag-cb\" disabled id=\"cbtag" +
                    i +
                    "\" float>" +
                    placeTagName +
                    "</ons-checkbox>";
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function drawProfilePage(){
    $('#profilename').innerText = username;
}

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

    chosenName = $('#group-name').value;

    if(chosenName=="" || chosenName==undefined){
        console.log("name empty")
    } else {

        theAxios().post('/groups/create', {
            members: selectedUsers,
            name: chosenName
        })
            .then(function (response) {
                console.log(response.data);
                if (response.data === serverMessages['OK']) {
                    pushThePage("main.html");
                    getGroups();
                } else {
                    ons.notification.toast({message: serverMessages[response.data], timeout: 2000});
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}

function getGroups(){
    theAxios().get('/groups/get')
        .then(function (response) {

            var div = $('#mygroups');
            div.innerHTML = '';

            for (i=0; i<response.data.length; i++) {
                div.innerHTML +=

                    "<ons-card>" +

                    "<div class='title'> " +
                    response.data[i].group_name +
                    "</div>" +

                    "<div class='content'> " +
                    response.data[i].members_name +
                    /*
                    "<br><ons-button modifier='quiet' style='padding: 0;'>" +
                        "<ons-icon icon='ion-ios-trash' style='color:crimson'>" +
                        "</ons-icon>" +
                    "</ons-button><br>" +
                    */
                    "</div>" +
                    "</ons-card>";
            }
            drawGeniusGroup(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
}

function drawGeniusGroup(groups){

    var selector = document.getElementById("genius-selector");
    selector.options.length = 0;

    var newoption = document.createElement("option");
    newoption.text = "moi";
    newoption.value = 0;
    selector.add(newoption);

    for (i = 0; i < groups.length; i++) {
        var newoption = document.createElement("option");
        newoption.text = groups[i].group_name;
        newoption.value = groups[i].id;
        selector.add(newoption);
    }
}