function checkConnection() {
    // Besoin de cordova-plugin-network-information pour ça
    var networkState = navigator.connection.type;
    return ('Connection type: ' + navigator.connection.type);
}

function theAxios() {
    var instance = axios.create({
        baseURL: 'http://localhost:8080/api',
        timeout: 1000,
        headers: {'Content-Type': 'text/plain'}
    });
    return instance;
}

function getHome(){
    theAxios().get('http://localhost:8080/api/')
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}

function signin(){

    theAxios().post('http://localhost:8080/api/signin', {
        username: 'Fred',
        password: 'Flintstone'
    })
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}

function signup(){
    theAxios().post('http://localhost:8080/api/signup', {
        mail: 'fred@flintstone.com',
        username: 'Fred',
        password: 'Flintstone'
    })
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}