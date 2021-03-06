/**
 * Created by C311939 on 01.08.2016.
 */
$('#submitAdd').on("click", function () {
    var room = $('#selectRoomForm').val();
    if(room == 1) {
        room = "baie";
    } else {
        room = "wc";
    }
    var cold = $('#addColdForm').val();
    var warm = $('#addWarmForm').val();
    var url = 'http://localhost:8080/contorAPI/service/contor/add/' + room + "/" + cold + "/" + warm;
    $.ajax({
        url: url,
        jsonp: "callback",
        dataType: "jsonp",
    }).then(function (response) {
        Materialize.toast(response, 2000, 'rounded');
    });
});
$('#submitRemove').on("click", function () {
    var id = $('#removeIdForm').val();
    var url = 'http://localhost:8080/contorAPI/service/contor/remove/' + id;

    $.ajax({
        url: url,
        jsonp: "callback",
        dataType: "jsonp",
    }).then(function (response) {
        Materialize.toast(response, 2000, 'rounded');
    });
});

function clearParallax() {
    $('.parallax-content').remove();
    $('.parallax-index').remove();
    $('.page-footer').remove();
}

$('.addBtn').on("click", function () {
    $('.centerDiv').show();
    $('.centerDiv').load('pages/add.html');
    clearParallax();
});

$('.removeBtn').on("click", function () {
    $('.centerDiv').show();
    $('.centerDiv').load('pages/remove.html');
    clearParallax();
});

$('.printBtn').on("click", function () {
    $('.centerDiv').show();
    $('.centerDiv').empty();
    $('.centerDiv').load('pages/print.html');
    clearParallax();

});

$('.chartsBtn').on("click", function () {
    $('.centerDiv').show();
    $('.centerDiv').empty();
    $('.centerDiv').load('pages/charts.html');
    clearParallax();
});

$('.home').on("click", function () {
    location.reload();
});

function registerForm() {
    var firstName = $('#firstName-register').val();
    var lastName = $('#lastName-register').val();
    var email = $('#email-register').val();
    var password = $('#password-register').val();
    var user = new Object();

    user.username = email;
    user.password = password;
    user.firstName = firstName;
    user.lastName = lastName;

    var data = JSON.stringify(user);

    var url = 'http://localhost:8080/contorAPI/service/user/add';

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: url,
        data: data,
        dataType: "json"
    }).then(function (response) {
        if(response == true) {
            Materialize.toast("Registered successfully", 3000, 'rounded');
            setTimeout(function () {
                window.location.href = "/contorAPI/pages/login.html";
            }, 2000);

        } else {
            Materialize.toast("This e-mail is already registered", 3000, 'rounded');
        }
    });
}

function loginForm() {
    var email = $('#email-login').val();
    var password = $('#password-login').val();

    var user = new Object();
    user.username = email;
    user.password = password;

    var data = JSON.stringify(user);

    var url = 'http://localhost:8080/contorAPI/service/user/login';

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: url,
        data: data,
        dataType: "json"
    }).then(function (response) {
        if(response == true) {
            window.location.href = "/contorAPI/";
        } else if(response == false) {
            Materialize.toast("Invalid e-mail or password", 3000, 'rounded');
        } else {
            alert("Problem");
        }
    });
}
$('#loginBtn').on("click", function () {
    loginForm();
});

$('.form-input').keypress(function (e) {
    if(e.which == 13) {
        loginForm();
    }
});

$("#registerBtn").on("click", function () {
    registerForm();
});

$('.input-register').keypress(function (e) {
   if(e.which == 13) {
       registerForm();
   }
});