
var serverMessages ={
    'OK'                : 'OK',
    ''                  : 'Warning: unexpected',
    'INVALID_POST'      : 'Requête invalide',
    'BAD_CREDENTIALS'   : 'Mauvaise combinaison',
    'EXISTING_PSEUDO'   : 'Ce pseudo est déja pris!',
    'EXISTING_MAIL'     : 'Un compte a déjà été créé avec cette adresse mail!'
}

var username = "";

function theAxios() {
    var instance = axios.create({
        baseURL: 'http://localhost:8080/api',
        //baseURL: 'http://192.168.0.16:8080/api',
        timeout: 1000,
        headers: {'Content-Type': 'application/json'}
    });
    return instance;
}

function contactServerAndUnlockApp(){
    theAxios().get('/')
        .then(function (response) {
            var connects = true;
            connects = navigator.onLine;
            console.log(connects);
            if (connects) {
                document.getElementById('checkingconnection').setAttribute('style', 'display:none;');
                document.getElementById('mainapp').setAttribute('style', 'display:block;');
            }
        })
        .catch(function (error) {
            ons.notification.alert('Impossible de contacter le serveur! Internet est-il activé?')
        });
}

function signin(){

    theAxios().post('/signin', {
        username: $("#signin_usr").value,
        password: $("#signin_pwd").value
    })
        .then(function (response) {
            console.log(response);
            if(response.data===serverMessages['OK']){
                ons.notification.toast({message: 'Connecté avec succès!', timeout: 500});
                username = $("#signin_usr").value;
                pushThePage("main.html");
            } else {
                ons.notification.toast({message:serverMessages[response.data],timeout:2000});
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function signup(){

    if($("#signup_pwd").value != $("#signup_pwd2").value){
        ons.notification.toast({message:'Les mots de passe sont différents!',timeout:2000})
    } else {
        theAxios().post('/signup', {
            mail:     $("#signup_mail").value,
            username: $("#signup_usr").value,
            password: $("#signup_pwd").value
        })
            .then(function (response) {
                console.log(response);
                if (response.data === serverMessages['OK']) {
                    ons.notification.toast({message: 'Compte créé avec succès!', timeout: 2000})
                    pushThePage("signin.html");
                } else {
                    ons.notification.toast({message:serverMessages[response.data],timeout:2000});
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}
