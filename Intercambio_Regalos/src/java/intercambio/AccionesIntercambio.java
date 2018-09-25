package intercambio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
            String query = "";
            if(opcion == 1){
                
                String correo = request.getParameter("correo");
                String correo_amigo = request.getParameter("correo_amigo");
            
            }else{
            
            }
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
