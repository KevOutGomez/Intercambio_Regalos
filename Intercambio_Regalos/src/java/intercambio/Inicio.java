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

public class Inicio extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");
        String query = "select * from usuario where correo = '"+correo+"' and contrasenia = '"+contrasenia+"'";
        Conexion conexion = new Conexion(); 
        try {
            conexion.crearConexion("localhost","convivio","root","root");
            AccionesDB acciones = new AccionesDB(conexion);
            ResultSet rs = null;
            rs = acciones.select(query);
            if( rs.next()){
                out.print("true");
            }else{
                out.print("false");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
