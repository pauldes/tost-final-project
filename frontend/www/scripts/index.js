(function () {
    "use strict";

    document.addEventListener('deviceready', onDeviceReady.bind(this), false);
    var mainElement = document.getElementById('mainapp');
    mainElement.setAttribute('style', 'display:none;');

    function onDeviceReady() {
        // Gérer les événements de suspension et de reprise Cordova
        document.addEventListener( 'pause', onPause.bind( this ), false );
        document.addEventListener( 'resume', onResume.bind( this ), false );
        var openingElement = document.getElementById('loadingscreen');
        var mainElement = document.getElementById('checkingconnection');
        openingElement.setAttribute('style', 'display:none;');
        mainElement.setAttribute('style', 'display:block;');

    };

    function onPause() {
        // TODO: cette application a été suspendue. Enregistrez l'état de l'application ici.
    };

    function onResume() {
        // TODO: cette application a été réactivée. Restaurez l'état de l'application ici.
    };
} )();

