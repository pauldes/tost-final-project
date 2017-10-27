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
    });

}

function getHome(){
    axios.get('http://localhost:8080/api/')
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}

function signin(){
    axios.post('http://localhost:8080/api/signin', {
        mail: 'Fred',
        cryptedPassword: 'Flintstone'
    })
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}
