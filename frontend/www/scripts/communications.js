function checkConnection() {
    // Besoin de cordova-plugin-network-information pour ça
    var networkState = navigator.connection.type;
    return ('Connection type: ' + navigator.connection.type);
}

function theAxios() {
    var instance = axios.create({
        baseURL: 'http://localhost:8080/api',
        timeout: 1000,
        headers: {'Content-Type': 'application/json'}
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

    console.log($('#username').value +' HEYY')

    theAxios().post('/signin', {
        username: $("#username").value,
        password: $("#password").value
    })
        .then(function (response) {
            console.log(response);
            if(response.data==='INVALID_POST'){
                ons.notification.toast({message: 'Requête invalide', timeout: 2000})
            } else if(response.data==='BAD_CREDENTIALS'){
                ons.notification.toast({message: 'Mauvaise combinaison', timeout: 2000})
            } else {
                ons.notification.toast({message: 'Connecté avec succès!', timeout: 2000})
                pushThePage("home.html");
            }

        })
        .catch(function (error) {
            console.log(error);
        });
}

function signup(){

    if($("#password").value==$("#password2").value){
        ons.notification.toast('Les mots de passe sont différents!')
    } else {

        theAxios().post('/signup', {
            mail:     $("#mail").value,
            username: $("#username").value,
            password: $("#password").value
        }, {
            "mail": "fred@flintstone.com", "username": "john", "password": "Flintstone"
        })
            .then(function (response) {
                console.log(response);
                if (response.data === 'INVALID_POST') {
                    ons.notification.toast({message: 'Requête invalide', timeout: 2000})
                } else if (response.data === 'EXISTING_PSEUDO') {
                    ons.notification.toast({message: 'Ce pseudo est déja pris!', timeout: 2000})
                } else {
                    ons.notification.toast({message: 'Compte créé avec succès!', timeout: 2000})
                    pushThePage("signin.html");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}

/*
var http = new XMLHttpRequest();
var url = "get_data.php";
var params = "lorem=ipsum&name=binny";
http.open("POST", url, true);

//Send the proper header information along with the request
http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

http.onreadystatechange = function() {//Call a function when the state changes.
    if(http.readyState == 4 && http.status == 200) {
        alert(http.responseText);
    }
}
http.send(params);
 */