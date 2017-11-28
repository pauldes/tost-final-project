
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
                "<img src='https://monaca.io/img/logos/download_image_onsenui_01.png' alt='Onsen UI' style='width: 100%'> " +
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

function getFromGoogleImages(query){
    /*
    var search_keyword=str_replace(' ','+',$search_keyword);
    $newhtml =file_get_html("https://www.google.com/search?q=".$search_keyword."&tbm=isch");
    $result_image_source = $newhtml->find('img', 0)->src;
    */
}