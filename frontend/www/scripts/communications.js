function checkConnection() {
    // Besoin de cordova-plugin-network-information pour ça
    var networkState = navigator.connection.type;
    return ('Connection type: ' + navigator.connection.type);
}

function initAxios() {
    var instance = axios.create({
        baseURL: 'http://localhost:8080/api',
        timeout: 1000,
        headers: {}
    })
}

function getHome(){
    axios.get('http://localhost:8080/api/home') //TODO: it should be only /home
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}