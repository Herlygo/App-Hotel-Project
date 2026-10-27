package com.hotel.vista;

import com.hotel.dao.UsuarioDAO;
import com.hotel.modelo.Usuario;
import com.hotel.util.Sesion;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/** Pantalla de inicio de sesión: es lo primero que ve el usuario. */
public class VentanaLogin extends JFrame {

    private JTextField txtUsuario = new JTextField();
    // JPasswordField oculta lo que se escribe con puntos
    private JPasswordField txtClave = new JPasswordField();
    private UsuarioDAO dao = new UsuarioDAO();

    public VentanaLogin() {
        setTitle("Hotel Heben-ezer - Iniciar sesión");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Usuario:"));     panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));  panel.add(txtClave);

        JButton btnEntrar = new JButton("Entrar");
        panel.add(new JLabel(""));             // celda vacía para alinear
        panel.add(btnEntrar);
        add(panel);

        btnEntrar.addActionListener(e -> iniciarSesion());
        // Permite entrar presionando Enter, sin tener que hacer clic
        getRootPane().setDefaultButton(btnEntrar);
    }

    /** Valida las credenciales y, si son correctas, abre el menú principal. */
    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        // getPassword() devuelve char[]; se convierte a String para compararla
        String clave = new String(txtClave.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe tu usuario y contraseña.");
            return;
        }
        try {
            Usuario u = dao.autenticar(usuario, clave);
            if (u == null) {
                // Mensaje genérico: no revela si falló el usuario o la contraseña
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                txtClave.setText("");
                return;
            }
            Sesion.iniciar(u);                    // guarda quién inició sesión
            new MenuPrincipal().setVisible(true); // abre el menú
            dispose();                            // cierra la ventana de login
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }
}