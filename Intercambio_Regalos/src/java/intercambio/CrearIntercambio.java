package intercambio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrearIntercambio extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String nombre = request.getParameter("nombre");
        String monto_maximo = request.getParameter("monto_maximo");
        String fecha_limite = request.getParameter("fecha_limite");
        String fecha_intercambio = request.getParameter("fecha_intercambio");
        String comentarios = request.getParameter("comentarios");
        String id_tema = request.getParameter("id_tema");
        String id_usuario = request.getParameter("id_usuario");
        String opcion = request.getParameter("opcion");
        String query;
        String respuesta ="" ;
        System.out.println(id_usuario);
        Conexion conexion = new Conexion();
        try {
            conexion.crearConexion("localhost","convivio","root","kev20165979");
            AccionesDB acciones = new AccionesDB(conexion);
            ResultSet rs;
            if (opcion.equals("1")) {
                query = "select * from tema";
                rs = acciones.select(query);
                respuesta = "<select id='id_tema'>";
                while(rs.next()){
                    respuesta +="<option value='"+rs.getInt("id")+"'>"+rs.getString("nombre_tema")+"</opiton>";
                }
                respuesta += "</select>";
                out.print(respuesta);
            }else{
                query = "insert into intercambio (nombre,id_tema,monto_maximo,fecha_limite,fecha_intercambio,comentarios) values ('"+nombre+"',"+id_tema+","+monto_maximo+",'"+fecha_limite+"','"+fecha_intercambio+"','"+comentarios+"')";
                acciones.insertDeleteUpdate(query);
                query = "select id from intercambio order by id desc limit 1";
                rs = acciones.select(query);
                int id_intercambio = 0;
                while(rs.next()){
                    id_intercambio = rs.getInt("id");
                }
                System.out.println(id_intercambio);
                query = "insert into usuario_intercambio (id_usuario,id_intercambio,estado_aceptacion,creador_intercambio) values ('"+id_usuario+"',"+id_intercambio+",true,true)";
                acciones.insertDeleteUpdate(query);
                respuesta = "con exito";
                out.print(respuesta);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CrearIntercambio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
