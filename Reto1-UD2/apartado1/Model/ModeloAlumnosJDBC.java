package Model;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloAlumnosJDBC implements IModeloAlumnos {

	private static String cadenaConexion =  "jdbc:mysql://localhost:3306/adat2";
	private static String user = "dam2";
	private static String pass = "asdf.1234";
	private static Connection conexion;
	
	
	public ModeloAlumnosJDBC()  {
		
		try {
			conexion = DriverManager.getConnection(cadenaConexion,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<String> getAll() {
		 List<String> alumnos = new ArrayList<>();
		    String consulta = "SELECT * FROM alumnos";

		    try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);
		         PreparedStatement ps = con.prepareStatement(consulta);
		         ResultSet res = ps.executeQuery()) {

		        while (res.next()) {
		            String alumno = res.getString("DNI") + " -- " +
		                            res.getString("nombre") + " -- " +
		                            res.getString("apellidos") + " -- " +
		                            res.getString("CP");
		            alumnos.add(alumno);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return alumnos;
		}


	@Override
	public Alumno getAlumnoPorDNI(String DNI) {
		String consulta ="select * from alumnos where dni like '"+DNI+"';";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(consulta);
			ResultSet resultado = sentencia.executeQuery();
			
			Alumno alumno = new Alumno();
			while(resultado.next()) {
				alumno.setDNI(resultado.getString(1));
				alumno.setNombre(resultado.getString(2));
				alumno.setApellidos(resultado.getString(3));
				alumno.setCP(resultado.getString(4));
			}
			//if(alumno.toString())
			return alumno;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public boolean modificarAlumno(Alumno alumno) {
        String consulta = "UPDATE alumnos SET nombre = ?, apellidos = ?, cp = ? WHERE dni = ?";
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2, alumno.getApellidos());
            sentencia.setString(3, alumno.getCP());
            sentencia.setString(4, alumno.getDNI());
            
            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0; // devuelve true si modifica algo
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public boolean eliminarAlumno(String DNI) {
		String consulta ="delete from alumnos where dni like '"+DNI+"';";
		
		try {
			PreparedStatement sentencia = conexion.prepareStatement(consulta);
			sentencia.executeQuery();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean crear(Alumno alumno) {
		String consulta = "INSERT INTO alumnos (dni, nombre, apellidos,cp)"
				+ "VALUES('"+alumno.getDNI()+"','"+alumno.getNombre()
				+"','"+alumno.getApellidos()+"','"+alumno.getCP()+"')";
		System.out.println(consulta);
		try {
			PreparedStatement sentencia = conexion.prepareStatement(consulta);
			sentencia.executeQuery();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return false;
	}
	
	

}
