$(document).ready(function(){
    $("#guardar").click(function(event){
        event.preventDefault();
        var correo = $("#correo").val();
        var contrasenia = $("#contrasenia").val();
        var nombre = $("#nombre").val();
        var alias = $("#alias").val();
        $.post("Registro",{alias: alias, nombre: nombre,correo: correo, contrasenia: contrasenia },function(result){
            if(result.toString() == "true"){
                $("p").remove();
                $("<p>El usuario ya existe</p>").insertAfter("#contrasenia");
            }else{
                localStorage.setItem("id_usuario",correo);
                window.location.href = "http://localhost:8080/Intercambio_Regalos/administrar_intercambio.html";
            }
        });
    });
    
    $("#correo").focus(function(){
        $("p").remove();
    });
    
    $("#contrasenia").focus(function(){
        $("p").remove();
    });
    
    $("#alias").focus(function(){
        $("p").remove();
    });
    
    $("#nombre").focus(function(){
        $("p").remove();
    });
    
    $("#salir").click(function(){
        window.location.href = "http://localhost:8080/Intercambio_Regalos/inicio.html";
    });
});

