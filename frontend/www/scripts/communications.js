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

function serverResponds(){

    var connects = false;

    theAxios().get('/')
        .then(function (response) {
            console.log(response.data);
            connects = true;
            connects = navigator.onLine;
            console.log(connects);
        })
        .then(function(){
            if(connects===false){
                ons.notification.alert('Impossible de contacter le serveur! Internet est-il activé?')
            }
            return connects;
        });
}

function signin(){

    console.log({
        username: $("#signin_usr").value,
        password: $("#signin_pwd").value
    });

    theAxios().post('/signin', {
        username: $("#signin_usr").value,
        password: $("#signin_pwd").value
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

    if($("#signup_pwd").value != $("#signup_pwd2").value){
        ons.notification.toast('Les mots de passe sont différents!')
    } else {
        theAxios().post('/signup', {
            mail:     $("#signup_mail").value,
            username: $("#signup_usr").value,
            password: $("#signup_pwd").value
        })
            .then(function (response) {
                console.log(response);
                if (response.data === 'INVALID_POST') {
                    ons.notification.toast({message: 'Requête invalide', timeout: 2000})
                } else if (response.data === 'EXISTING_PSEUDO') {
                    ons.notification.toast({message: 'Ce pseudo est déja pris!', timeout: 2000})
                } else if (response.data === 'EXISTING_MAIL') {
                    ons.notification.toast({message: 'Un compte a déjà été créé avec cette adresse mail!', timeout: 2000})
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