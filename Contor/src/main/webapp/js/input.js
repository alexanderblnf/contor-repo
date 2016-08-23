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
    console.log("id=" + id);
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
})
