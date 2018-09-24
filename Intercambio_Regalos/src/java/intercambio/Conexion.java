package intercambio;
import java.sql.*;

public class Conexion {
    private static Connection conexion = null;
   public void crearConexion(String host,String db,String usuario,String contrasenia) throws SQLException, ClassNotFoundException {
      if (conexion == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+db, usuario, contrasenia);
             System.out.println("Conexion Establecida");
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
   }
   
   public Connection getConexion(){
       return conexion;
   }
   public static void cerrarConexion() throws SQLException {
      if (conexion != null) {
          System.out.println("Conexion Finalizada");
         conexion.close();
      }
   }
}
