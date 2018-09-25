$(document).ready(function(){
    $("#agregar_admigo").click(function(){
        var agregar = "<div class='acciones'>"
                       +"<input type='email' id='correo' placeholder='Correo Electronico'focus/>"
                       +"<input type='button' onclick='enviar()' value='Agregar Amigo'/>"
                       +"<input type='button' onclick='cerrar()' value='cerrar'/>"
                       +"<div>";
        $("#agregar_admigo").parent().append(agregar);
    });
    
    $("#ver_intercambio").click(function(){
        alert("Ver Intercambios");
    });
    
    $("#salir").click(function(){
        window.location.href = "http://localhost:8080/Intercambio_Regalos/inicio.html";
    });
    
});

function enviar(){
    var correo = $("#correo").val();
    alert(correo);
}

function cerrar(){
    $(".acciones").remove();
}


