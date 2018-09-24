package intercambio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccionesDB {
    private Conexion conexion;
    public AccionesDB(Conexion conexion){
        this.conexion = conexion;
    }
    public void insertDeleteUpdate(String query) throws SQLException{
        PreparedStatement ps = conexion.getConexion().prepareStatement(query);
        ps.execute();
    }
    
    public ResultSet select(String query) throws SQLException{
        PreparedStatement ps = conexion.getConexion().prepareStatement(query);
        return ps.executeQuery();
    }
}
