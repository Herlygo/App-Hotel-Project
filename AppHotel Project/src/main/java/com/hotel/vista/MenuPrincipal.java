package com.hotel.vista;

import com.hotel.util.Sesion;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/** Ventana principal: desde aquí se abren los demás módulos del sistema. */
public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Hotel Heben-ezer - Menú principal");
        setSize(350, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Muestra el nombre y el rol del usuario que inició sesión
        JLabel lblUsuario = new JLabel(
            Sesion.getUsuario().getNombre() + " (" + Sesion.getUsuario().getRol() + ")",
            SwingConstants.CENTER);

        JButton btnClientes = new JButton("Clientes");
        JButton btnHabitaciones = new JButton("Habitaciones");
        JButton btnReservas = new JButton("Reservaciones");
        JButton btnSalir = new JButton("Cerrar sesión");

        panel.add(lblUsuario);
        panel.add(btnClientes);
        panel.add(btnHabitaciones);
        panel.add(btnReservas);
        panel.add(btnSalir);
        add(panel);

        btnClientes.addActionListener(e -> new VentanaClientes().setVisible(true));
        btnHabitaciones.addActionListener(e -> new VentanaHabitaciones().setVisible(true));
        btnReservas.addActionListener(e -> new VentanaReservas().setVisible(true));

        // Cerrar sesión: borra la sesión y vuelve a la pantalla de login
        btnSalir.addActionListener(e -> {
            Sesion.cerrar();
            new VentanaLogin().setVisible(true);
            dispose();
        });
    }
}