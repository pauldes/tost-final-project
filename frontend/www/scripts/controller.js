
function pushThePage(page){
    document.querySelector('#globalNavigator').pushPage('./html/'+page);
}

var favs = {
    'Aejnkjsdf6zd':
    {
        'name':'Oslow','price':2,'address':'2 rue du Pape','category':'Bars'
    },
    '8cdsqcpdf6zd':
    {
        'name':'Brasserie Georges','price':3,'address':'3 rue Saint Georges','category':'Brunchs'
    }
}

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
    var place = autocomplete.getPlace();
    alert(place.place_id);
    console.log(place);
    window.open(place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 640}));
    codeAddress(document.getElementById('autocomplete').value);
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
            drawGeniusRecommendation(response.data.place_name,response.data.google_place_id);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function drawGeniusRecommendation(place_name,google_place_id){

    service = new google.maps.places.PlacesService(document.createElement('div'));
    service.getDetails({placeId: google_place_id}, callback);

    function callback(place, status) {

        if (status == google.maps.places.PlacesServiceStatus.OK) {

            console.log(place);

            var recommendationDiv = $('#recommendation');
            recommendationDiv.innerHTML = "";

            var address = place.formatted_address;
            var priceIcons = '€€€';

            var innerRecommendationDiv = document.createElement('div');
            innerRecommendationDiv.className = 'row';
            innerRecommendationDiv.innerHTML = "" +
                "<ons-card> " +
                "<img src="
                +place.photos[0].getUrl({'maxWidth': 640, 'maxHeight': 640})
                +" alt='Illustration' style='width: 100%'> " +
                "<div class='title'> " +
                place_name +
                "</div> " +
                "<div class='content'>" +
                address +
                "<br>" +
                priceIcons +
                "</div> " +
                "</ons-card>";
            recommendationDiv.appendChild(innerRecommendationDiv);
        }
    }

}