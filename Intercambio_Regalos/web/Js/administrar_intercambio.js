$(document).ready(function(){
    var id_usuario = localStorage.getItem('id_usuario');
    alert(id_usuario);
    var flag = false;
    
    $('.guardar').click(function(){
        var id = $($(this).parent()).parent().attr('id');
        var tema = $('#tema').val();
        var monto = $('#monto_maximo').val();
        var fecha_limite = $('#fecha_limite').val();
        var fecha_intercambio = $('#fecha_intercambio').val();
        var comentarios = $('#comentarios').val();
        $.post('AdministarIntercambios',{
            
            opcion:4,
            id:id,
            tema: tema,
            monto: monto,
            fecha_limite: fecha_limite,
            fecha_intercambio: fecha_intercambio,
            comentarios: comentarios
        
        });
        $('.edicion').remove();
    });
    
    $('.cerrar').click(function(){
        $('.vista1').remove();
    });
    
    $('#salir').click(function(){
        window.location.href = 'http://localhost:8080/Intercambio_Regalos/inicio.html';
    });
    
    $('#regresar').click(function(){
        window.location.href = 'http://localhost:8080/Intercambio_Regalos/acciones.html';
    });
    
    $('#crear').click(function(){
        window.location.href = 'http://localhost:8080/Intercambio_Regalos/crear.html';
    });
    
    if(!flag){
        $.post('AdministarIntercambios',{opcion:0,id_usuario:id_usuario,id:0},function(result){
            $('.intercambio').append(result);
        });
        flag = true;
    }
    
});
function eliminar(id){
        $.post('AdministarIntercambios',{opcion:1,id:id},function(result){
            if(result.toString() == 'true'){
                $('#'+id).remove();
            }
        });
}
function vista(id){
        $.post('AdministarIntercambios',{opcion:2,id:id},function(result){
            $('.edicion').remove();
            $('.vista1').remove();
            $('#'+id).append(result);
        });
}

function editar(id){
        $.post('AdministarIntercambios',{opcion:3,id:id},function(result){
            $('.vista1').remove();
            $('.edicion').remove();
            $('#'+id).append(result);
        });
}

