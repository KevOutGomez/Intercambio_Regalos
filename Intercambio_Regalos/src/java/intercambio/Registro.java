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
import javax.servlet.http.HttpSession;

public class Registro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String nombre = request.getParameter("nombre");
        String alias = request.getParameter("alias");
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");
        String query = "select alias from usuario where correo = '"+correo+"'";
        Conexion conexion = new Conexion(); 
        try {
            conexion.crearConexion("localhost","convivio","root","root");
            AccionesDB acciones = new AccionesDB(conexion);
            HttpSession session= request.getSession(true);
            ResultSet rs = null;
            rs = acciones.select(query);
            
            if( rs.next()){
                session.setAttribute("alias",rs.getString("alias"));
                System.out.println("Usuario ya creado");
                out.print("true");
            }else{
                query = "insert into usuario values('"+correo+"','"+nombre+"','"+alias+"','"+contrasenia+"')";
                acciones.insertDeleteUpdate(query);
                out.print("false");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
