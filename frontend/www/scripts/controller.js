
function pushThePage(page){
    document.querySelector('#globalNavigator').pushPage('./html/'+page);
}

var favs = {
    'Aejnkjsdf6zd':
    {
        'name':'Oslow','price':2,'adress':'2 rue du Pape'
    },
    '8cdsqcpdf6zd':
    {
        'name':'Brasserie Georges','price':3,'adress':'3 rue Saint Georges'
    }
}

function fillMyFavs(category){

    var myfavsdiv = $('#myfavs');

    for (placeid in favs){
        var name = favs[placeid]['name'];
        var adress = favs[placeid]['adress'];
        var priceIcons = Array(favs[placeid]['price']).join("€");

        var currentfavdiv = document.createElement('div');
        currentfavdiv.className = 'row';
        currentfavdiv.innerHTML ="" +
            "<ons-card> " +
            "<img src='https://monaca.io/img/logos/download_image_onsenui_01.png' alt='Onsen UI' style='width: 100%'> " +
            "<div class='title'> " +
            name +
            "</div> " +
            "<div class='content'>" +
            adress +
            "<br>" +
            priceIcons +
            "</div> " +
            "</ons-card>";
        myfavsdiv.appendChild(currentfavdiv);
    }
}

function getFromGoogleImages(query){
    /*
    var search_keyword=str_replace(' ','+',$search_keyword);
    $newhtml =file_get_html("https://www.google.com/search?q=".$search_keyword."&tbm=isch");
    $result_image_source = $newhtml->find('img', 0)->src;
    */
}