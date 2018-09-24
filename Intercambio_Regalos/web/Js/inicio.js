$(document).ready(function(){
    $("#inicio_sesion").click(function(event){
        event.preventDefault();
        var correo = $("#correo").val();
        var contrasenia = $("#contrasenia").val();
        $.post("Inicio",{correo: correo, contrasenia: contrasenia },function(result){
            if(result.toString() == "true"){
                localStorage.setItem("id_usuario",correo);
                window.location.href = "http://localhost:8080/Intercambio_Regalos/acciones.html";
            }else{
                $("p").remove();
                $("<p>Usuario o contraseña incorrecta</p>").insertAfter("#contrasenia");   
            }
        });
    });
    $("#correo").focus(function(){
        $("p").remove();
    });
    
    $("#contrasenia").focus(function(){
        $("p").remove();
    });
    
    $("#registro").click(function(){
        window.location.href = "http://localhost:8080/Intercambio_Regalos/registro.html";
    });
});

