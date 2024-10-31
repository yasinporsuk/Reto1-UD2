package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BD_Agenda {
    private static final String URL = "jdbc:mysql://localhost:3306/adat2";
    private static final String USER = "dam2";
    private static final String PASSWORD = "asdf.1234";

    // Leer todos los contactos de la base de datos
    public List<Contacto> leeContactos() {
        List<Contacto> contactos = new ArrayList<>();
        String query = "SELECT nombre, telefono FROM contactos";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                contactos.add(new Contacto(nombre, telefono));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }

    // Escribir un nuevo registro de contacto en la base de datos
    public boolean escribeRegistro(Contacto contacto) {
        String query = "INSERT INTO contactos (nombre, telefono) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTeléfono());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Borrar un registro de contacto de la base de datos
    public boolean borraRegistro(Contacto contacto) {
        String query = "DELETE FROM contactos WHERE nombre = ? AND telefono = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTeléfono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

