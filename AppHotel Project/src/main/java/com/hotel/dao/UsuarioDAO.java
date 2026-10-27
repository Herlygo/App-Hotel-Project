package com.hotel.dao;

import com.hotel.bd.Conexion;
import com.hotel.modelo.Usuario;
import com.hotel.util.HashUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** DAO de usuarios: valida las credenciales del inicio de sesión. */
public class UsuarioDAO {

    /**
     * Busca un usuario con ese nombre y contraseña.
     * @return el Usuario si las credenciales son correctas; null si no lo son
     */
    public Usuario autenticar(String usuario, String clave) throws SQLException {
        // Se compara el HASH de lo que escribió el usuario con el hash guardado
        String sql = "SELECT id, nombre, usuario, rol FROM usuarios "
                   + "WHERE usuario = ? AND clave = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, HashUtil.sha256(clave));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // encontró una fila: credenciales correctas
                    return new Usuario(rs.getInt("id"), rs.getString("nombre"),
                                       rs.getString("usuario"), rs.getString("rol"));
                }
                return null; // ninguna fila: usuario o contraseña incorrectos
            }
        }
    }
}