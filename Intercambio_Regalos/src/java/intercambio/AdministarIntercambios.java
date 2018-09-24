package intercambio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdministarIntercambios extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        int idIntercambio = Integer.parseInt(request.getParameter("id"));
        String query ="";
        Conexion conexion = new Conexion(); 
        try {
            conexion.crearConexion("localhost","convivio","root","kev20165979");
            AccionesDB acciones = new AccionesDB(conexion);
            ResultSet rs;
            String respuesta = "";
            switch(opcion){
                case 0:
                    query = "select intercambio.id, intercambio.nombre from intercambio, usuario_intercambio where usuario_intercambio.creador_intercambio=1 and usuario_intercambio.id_intercambio = intercambio.id and usuario_intercambio.id_usuario='"+request.getParameter("id_usuario")+"'";
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta += "<div  id='"+rs.getInt("intercambio.id")+"'>"
                                    +"<label>"+rs.getString("intercambio.nombre")+"</label>"
                                    +"<button type='button' onclick='eliminar("+rs.getInt("intercambio.id")+")' class='btn btn-default eliminar'><span class='glyphicon glyphicon-remove' ></span></button>" 
                                    +"<button type='button' onclick='vista("+rs.getInt("intercambio.id")+")' class='btn btn-default vista'><span class='glyphicon glyphicon-eye-open'></span></button>"
                                    +"<button type='button' onclick='editar("+rs.getInt("intercambio.id")+")' class='btn btn-default editar'><span class='glyphicon glyphicon-pencil'></span></button>"
                                    +"</div>";
                    }
                    out.print(respuesta);
                    break;
                case 1:
                    query = "delete from intercambio where id ="+idIntercambio;
                    acciones.insertDeleteUpdate(query);
                    out.print("true");
                    break;
                case 2:
                    query = "select usuario.nombre from usuario, usuario_intercambio where usuario_intercambio.id_usuario = usuario.correo and usuario_intercambio.estado_aceptacion = 1 and usuario_intercambio.id_intercambio ="+idIntercambio;
                    rs = acciones.select(query);
                    respuesta = "<div class='vista1'>";
                    respuesta += "<h6>Invitados</h6>";
                    while(rs.next()){
                        respuesta += "<label>"+rs.getString("usuario.nombre")+"</label><br/>";
                    }
                    query = "select tema.nombre_tema, intercambio.monto_maximo, intercambio.fecha_limite, intercambio.fecha_intercambio, intercambio.comentarios from tema, intercambio where intercambio.id_tema = tema.id and intercambio.id = "+idIntercambio;
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta += "<label>Clave del intercambio: "+idIntercambio+"</label><br/>"
                                    + "<label>Tema: "+rs.getString("tema.nombre_tema")+"</label><br/>"
                                    +"<label>Monto maximo: $"+rs.getDouble("intercambio.monto_maximo")+"</label><br/>"
                                    +"<label>Fecha Limite: "+rs.getDate("intercambio.fecha_limite")+"</label><br/>"
                                    +"<label>Fecha del Intercambio: "+rs.getDate("intercambio.fecha_intercambio")+"</label><br/>"
                                    +"<label>Comentarios: "+rs.getString("intercambio.comentarios")+"</label><br/>";
                    }
                    respuesta += "<button class='cerrar' onclick='cerrar()'>Cerrar</button></div>";
                    out.print(respuesta);
                    break;
                case 3:
                    Calendar fecha = new GregorianCalendar();
                    String fecha_actual = fecha.get(Calendar.YEAR)+"-"+fecha.get(Calendar.MONTH)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                    query = "select usuario.nombre from usuario, usuario_intercambio where usuario_intercambio.id_usuario = usuario.correo and usuario_intercambio.estado_aceptacion = true and usuario_intercambio.id_intercambio ="+idIntercambio;
                    rs = acciones.select(query);
                    respuesta = "<div class='edicion'>";
                    respuesta += "<h6>Invitados</h6>";
                    while(rs.next()){
                        respuesta += "<label>"+rs.getString("usuario.nombre")+"</label><br/>";
                    }
                    
                    query = "select nombre_tema from tema";
                    respuesta += "<label>Clave del intercambio: "+idIntercambio+"</label><br/>";
                    respuesta += "<select id='tema'>";
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta += "<option value='"+rs.getString("nombre_tema")+"'>"+rs.getString("nombre_tema")+"</option>";
                    }
                    respuesta += "</select><br/>";
                    query = "select intercambio.monto_maximo, intercambio.fecha_limite, intercambio.fecha_intercambio, intercambio.comentarios from tema, intercambio where intercambio.id_tema = tema.id and intercambio.id = "+idIntercambio;
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta += "<label>Monto maximo: </label><input type='number' min='0' value='"+rs.getDouble("intercambio.monto_maximo")+"' id='monto_maximo'/><br/>"
                                    +"<label>Fecha Limite: </label><input type='date' value='"+rs.getDate("intercambio.fecha_limite")+"' id='fecha_limite'/><br/>"
                                    +"<label>Fecha del Intercambio: </label><input type='date' max='"+fecha_actual+"' value='"+rs.getDate("intercambio.fecha_intercambio")+"' id='fecha_intercambio'/><br/>"
                                    +"<label>Comentarios: </label><textarea rows='4' cols='50' id='comentarios'>"+rs.getString("intercambio.comentarios")+"</textarea><br/>";
                    }
                    respuesta += "<button id='guardar' onclick='actualizar("+idIntercambio+")'>Guardar</button></div>";
                    out.print(respuesta);
                    break;
                case 4:
                    String id = request.getParameter("id");
                    String tema = request.getParameter("tema");
                    String monto = request.getParameter("monto");
                    String fecha_limite = request.getParameter("fecha_limite");
                    String fecha_intercambio = request.getParameter("fecha_intercambio");
                    String comentarios = request.getParameter("comentarios");
                    query = "select id from tema where nombre_tema = '"+tema+"'";
                    rs = acciones.select(query);
                    while(rs.next()){
                        tema = rs.getString("id");
                    }
                    query = "update intercambio set id_tema = "+tema+", monto_maximo = "+monto+", fecha_limite = '"+fecha_limite+"', fecha_intercambio = '"+fecha_intercambio+"', comentarios = '"+comentarios+"' where id = "+id;
                    acciones.insertDeleteUpdate(query);
                    out.print("Intercambio modificado");
                    break;
                default:
                    break;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
