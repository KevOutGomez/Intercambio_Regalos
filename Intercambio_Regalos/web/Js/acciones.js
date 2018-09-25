var correo = localStorage.getItem("id_usuario");
$(document).ready(function(){
    $("#agregar_amigo").click(function(){
        var agregar = "<div class='acciones'>"
                       +"<input type='email' id='correo_amigo' placeholder='Correo Electronico'focus/>"
                       +"<input type='button' onclick='enviar()' value='Agregar Amigo'/>"
                       +"<input type='button' onclick='cerrar()' value='cerrar'/>"
                       +"<div>";
        $("#agregar_amigo").parent().append(agregar);
        $.post("AccionesIntercambio",{opcion:1,correo:correo},function(result){
            $(".acciones").append(result);
        });
    });
    
    $("#intercambio_ver").click(function(){
        var agregar = "<div class='acciones'>"
                       +"<input type='number' id='id_intercambio' placeholder='Id del Intercambio'focus/>"
                       +"<input type='button' onclick='ver_intercambio()' value='Ver Intercambio'/>"
                       +"<input type='button' onclick='cerrar()' value='cerrar'/>"
                       +"<div>";
        $("#intercambio_ver").parent().append(agregar);
        $.post("AccionesIntercambio",{opcion:5,correo:correo},function(result){
            $(".acciones").append(result);
        });
    });
    
    $("#salir").click(function(){
        window.location.href = "http://localhost:8080/Intercambio_Regalos/inicio.html";
    });
    
});

function enviar(){
    var correo_amigo = $("#correo_amigo").val();
    $.post("AccionesIntercambio",{opcion:2,correo:correo,correo_amigo:correo_amigo},function(result){
        $(".acciones").append(result);
    });
}

function cerrar(){
    $(".acciones").remove();
}

function ver_intercambio(){
    var id_intercambio = $("#id_intercambio").val();
    
    $.post("AccionesIntercambio",{opcion:3,id_intercambio:id_intercambio,correo:correo},function(result){
        $(".acciones").append(result);
        console.log(result);
    });
}

function aceptar(id_intercambio){
    $(".acciones").remove();
    $.post("AccionesIntercambio",{opcion:4,id_intercambio,id_intercambio,correo:correo},function(result){
        console.log(result);
    });
}

function rechar(){
    $(".acciones").remove();
}


