function checkConnection() {
    // Besoin de cordova-plugin-network-information pour ça
    var networkState = navigator.connection.type;
    return ('Connection type: ' + navigator.connection.type);
}