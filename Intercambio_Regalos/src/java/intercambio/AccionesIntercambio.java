package intercambio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccionesIntercambio extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        Conexion conexion = new Conexion();
        
        try{
            conexion.crearConexion("localhost", "convivio", "root", "kev20165979");
            AccionesDB acciones = new AccionesDB(conexion);
            ResultSet rs, rs2;
            String query = "";
            String respuesta = "";
            String correo = "";
            String correo_amigo = "";
            String id_intercambio = "";
            switch(opcion){
                case 1:
                    correo = request.getParameter("correo");
                    query = "select usuario.alias, usuario.correo from usuario,amigos where amigos.correo_amigo=usuario.correo and amigos.correo_usuario='"+correo+"'";
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta += "<h5>Amigos</h5>"
                                    +"<label>Alias: "+rs.getString("usuario.alias")+"    Correo: "+rs.getString("usuario.correo")+"</label><br/>";
                    }
                    out.print(respuesta);
                    break;
                case 2:
                    correo = request.getParameter("correo");
                    correo_amigo = request.getParameter("correo_amigo");
                    query = "insert into amigos values ('"+correo+"','"+correo_amigo+"')";
                    acciones.insertDeleteUpdate(query);
                    
                    query = "select usuario.alias, usuario.correo from usuario,amigos where amigos.correo_amigo=usuario.correo and amigos.correo_amigo='"+correo_amigo+"' and amigos.correo_usuario='"+correo+"'";
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta = "<label>Alias: "+rs.getString("usuario.alias")+"    Correo: "+rs.getString("usuario.correo")+"</label><br/>";
                    }
                    out.print(respuesta);
                    break;
                case 3:
                    correo = request.getParameter("correo");
                    id_intercambio = request.getParameter("id_intercambio");
                    query = "select tema.nombre_tema, intercambio.monto_maximo, intercambio.fecha_limite, intercambio.fecha_intercambio, intercambio.comentarios from tema, intercambio where intercambio.id_tema = tema.id and intercambio.id = "+id_intercambio;
                    rs = acciones.select(query);
                    while(rs.next()){
                        respuesta = "<label>Clave del intercambio: "+id_intercambio+"</label><br/>"
                                  + "<label>Tema: "+rs.getString("tema.nombre_tema")+"</label><br/>"
                                  +"<label>Monto maximo: $"+rs.getDouble("intercambio.monto_maximo")+"</label><br/>"
                                  +"<label>Fecha Limite: "+rs.getDate("intercambio.fecha_limite")+"</label><br/>"
                                  +"<label>Fecha del Intercambio: "+rs.getDate("intercambio.fecha_intercambio")+"</label><br/>"
                                  +"<label>Comentarios: "+rs.getString("intercambio.comentarios")+"</label><br/>";
                    }
                    respuesta += "<button  onclick='aceptar("+id_intercambio+")'>Aceptar</button><br/>"
                                 +"<button  onclick='rechar()'>Rechazar</button><br/>";
                    query = "select intercambio.nombre, tema.nombre_tema, intercambio.fecha_intercambio, usuario_intercambio.id_intercambiar, usuario.alias "
                            + "from usuario, intercambio, tema, usuario_intercambio where usuario_intercambio.estado_aceptacion=1 and tema.id = intercambio.id_tema and intercambio.id = usuario_intercambio.id_intercambio "
                            + "and usuario.correo = usuario_intercambio.id_intercambiar and usuario_intercambio.id_usuario = '"+correo+"'";
                    rs = acciones.select(query);
                    respuesta += "<h5>Tus Intercambios</h5><br/>";
                    while(rs.next()){
                        respuesta += "<div class='acciones'"
                                    + "<label>Nombre del Intercambio: "+rs.getString("intercambio.nombre")+"</label>"
                                    +"<label>Tema del Intercambio: "+rs.getString("tema.nombre_tema")+"</label>"
                                    +"<label>Fecha del Intercambio: "+rs.getDate("intercambio.fecha_intercambio")+"</label>"
                                    +"<label>Te toco: "+rs.getString("usuario.alias")+"    Su correo es: "+rs.getString("usuario_intercambio.id_intercambiar")+"   </label>"
                                    +"<div>";
                    }
                    System.out.println(respuesta);
                    out.print(respuesta);
                    break;
                case 4:
                    correo = request.getParameter("correo");
                    id_intercambio = request.getParameter("id_intercambio");
                    query = "select estado_aceptacion from usuario_intercambio where id_usuario = '"+correo+"' and id_intercambio="+id_intercambio;
                    rs = acciones.select(query);
                    if(rs.next()){
                        query = "update usuario_intercambio set estado_aceptacion = 1 where id_usuario = '"+correo+"' and id_intercambio="+id_intercambio;
                        acciones.insertDeleteUpdate(query);
                    }else{
                        query = "insert into usuario_intercambio (id_usuario,id_intercambio,estado_aceptacion,creador_intercambio) values('"+correo+"',"+id_intercambio+",true,false)";
                        acciones.insertDeleteUpdate(query);
                    }
                    query = "select id_usuario,id_intercambiar from usuario_intercambio where id_intercambio="+id_intercambio;
                    rs = acciones.select(query);
                    ArrayList<String> al = new ArrayList<String>();
                    while(rs.next()){
                        al.add(rs.getString("id_usuario"));
                    }
                    int temp = (int) (Math.random() * al.size()) + 1;
                    int temp2 = 0;
                    while(temp2 < al.size()){
                        if(al.size() == 2){
                            temp = 1;
                        }else if(temp >= al.size()){
                            temp = 0;
                        }
                        query = "update usuario_intercambio set id_intercambiar='"+al.get(temp)+"' where id_usuario='"+al.get(temp2)+"' and id_intercambio="+id_intercambio;
                        acciones.insertDeleteUpdate(query);
                        temp2++;
                        temp++;
                    }
                    out.print("Te han agregado al intercambio "+query);
                    break;
                case 5:
                    correo = request.getParameter("correo");
                    query = "select intercambio.nombre, tema.nombre_tema, intercambio.fecha_intercambio, usuario_intercambio.id_intercambiar "
                            + "from intercambio, tema, usuario_intercambio where usuario_intercambio.estado_aceptacion=1 and tema.id = intercambio.id_tema and intercambio.id = usuario_intercambio.id_intercambio "
                            + "and usuario_intercambio.id_usuario = '"+correo+"'";
                    rs = acciones.select(query);
                    respuesta += "<h5>Tus Intercambios</h5><br/>";
                    while(rs.next()){
                        respuesta += "<div class='acciones'"
                                    + "<label>Nombre del Intercambio: "+rs.getString("intercambio.nombre")+"</label>"
                                    +"<label>Tema del Intercambio: "+rs.getString("tema.nombre_tema")+"</label>"
                                    +"<label>Fecha del Intercambio: "+rs.getDate("intercambio.fecha_intercambio")+"</label>"
                                    +"<label>Te toco: "+rs.getString("usuario_intercambio.id_intercambiar")+"</label>"
                                    +"<div>";
                    }
                    out.print(respuesta);
                    break;
                default:
                    break;
            }
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
