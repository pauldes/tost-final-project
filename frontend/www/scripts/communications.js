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
    theAxios().get('/')
        .then(function (response) {
            console.log(response);
            ons.notification.toast({message: response.data, timeout: 2000})
        })
        .catch(function (error) {
            console.log(error);
        });
}

function signin(){

    theAxios().post('/signin', {
        username: 'Fred',
        password: 'Flintstone'
    })
        .then(function (response) {
            console.log(response);
            if(response.data==='INVALID_CREDENTIALS'){
                ons.notification.toast({message: 'Requête invalide', timeout: 2000})
            } else if(response.data==='BAD_CREDENTIALS'){
                ons.notification.toast({message: 'Mauvaise combinaison', timeout: 2000})
            } else {
                ons.notification.toast({message: 'Connecté avec succès!', timeout: 2000})
                pushThePage("homepage.html");
            }

        })
        .catch(function (error) {
            console.log(error);
        });
}

function signup(){
    theAxios().post('/signup', {
        mail: 'fred@flintstone.com',
        username: 'Fred',
        password: 'Flintstone'
    })
        .then(function (response) {
            console.log(response);
            if(response.data==='EXISTING_PSEUDO') {
                ons.notification.toast({message: 'Ce pseudo est déja pris!', timeout: 2000})
            } else {
                ons.notification.toast({message: 'Compte créé avec succès!', timeout: 2000})
                pushThePage(signin.html);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}