$(document).ready(function(){
var flag = false;
var id_usuario = localStorage.getItem("id_usuario");
    if(!flag){
        $.post("CrearIntercambio",{opcion:1},function(request){
            $(request).insertAfter("#nombre");
        });
        flag = true;
    }
    
    $("#guardar").click(function(event){
        event.preventDefault();
        var nombre = $("#nombre").val();
        var monto_maximo = $("#monto_maximo").val();
        var fecha_limite = $("#fecha_limite").val();
        var fecha_intercambio = $("#fecha_intercambio").val();
        var comentarios = $("#comentarios").val();
        var id_tema = $("#id_tema").val();
        $.post("CrearIntercambio",{opcion:2,id_tema:id_tema,nombre:nombre,monto_maximo:monto_maximo,fecha_limite:fecha_limite,fecha_intercambio:fecha_intercambio,comentarios:comentarios,id_usuario:id_usuario},function(result){
            alert("El Intercambio fue creado "+result.toString());
            window.location.href = "http://localhost:8080/Intercambio_Regalos/administrar_intercambio.html";
        });
    });
    
    $("#salir").click(function(){
        window.location.href = "http://localhost:8080/Intercambio_Regalos/inicio.html";
    });
    
});

