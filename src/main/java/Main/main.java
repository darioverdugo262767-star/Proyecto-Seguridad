/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import daoMock.UsuarioDAOMock;
import presentacion.Login;

/**
 *
 *
 */
public class main {
    public static void main(String[] args) {
      UsuarioDAOMock usuarioDAO = new UsuarioDAOMock();
    java.awt.EventQueue.invokeLater(() -> {
        new Login(usuarioDAO).setVisible(true);
    });
    
    }
}
