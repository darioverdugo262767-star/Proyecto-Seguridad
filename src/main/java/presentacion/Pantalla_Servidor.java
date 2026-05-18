package presentacion;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.cert.X509Certificate;
import java.time.format.DateTimeFormatter;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;

/**
 * Interfaz gráfica de administración para el servidor de chat.
 */
public class Pantalla_Servidor extends javax.swing.JFrame {

    private PrintWriter salidaComandos;
    private javax.swing.table.DefaultTableModel modeloTabla;
    
    /**
     * Constructor de la interfaz del servidor.
     */
    public Pantalla_Servidor() {
        initComponents();
        modeloTabla = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        modeloTabla.setRowCount(0);
        jLabel2.setText("Estado: Conectado"); 
        jLabel2.setForeground(new java.awt.Color(0, 153, 51));
        conectarAlServidorPython();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor - Chat");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("SERVIDOR");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Estado:");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Desconectar cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mensajes del chat");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel5.setText("Servidor iniciado en el puerto: ");

        jButton2.setForeground(new java.awt.Color(204, 0, 0));
        jButton2.setText("Detener servidor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Clientes Conectados"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(166, 166, 166)
                                .addComponent(jLabel7))
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 183, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Expulsa al usuario seleccionado en la tabla mediante el comando /kick.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int filaSeleccionada = jTable2.getSelectedRow();
    
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario de la tabla 'Clientes Conectados'.", "Ningún usuario seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String usuarioAKick = jTable2.getValueAt(filaSeleccionada, 0).toString();

        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas expulsar a '" + usuarioAKick + "'?", "Confirmar expulsión", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            if (salidaComandos != null) {
                String jsonComando = "{\"type\":\"message\",\"from\":\"SERVER_GUI\",\"to\":\"ALL\",\"text\":\"/kick " + usuarioAKick + "\",\"timestamp\":\"\"}\n";
                salidaComandos.print(jsonComando);
                salidaComandos.flush();

                modeloTabla.removeRow(filaSeleccionada);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Apaga el servidor backend por completo mediante el comando /shutdown.
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (salidaComandos != null) {
            int seguro = JOptionPane.showConfirmDialog(this, 
                    "¿Deseas detener el servidor Python por completo?\nEsto cerrará la sala para todos los clientes conectados.", 
                    "Detener Servidor", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

            if (seguro == JOptionPane.YES_OPTION) {
                String jsonComando = "{\"type\":\"message\",\"from\":\"SERVER_GUI\",\"to\":\"ALL\",\"text\":\"/shutdown\",\"timestamp\":\"\"}\n";
                salidaComandos.print(jsonComando);
                salidaComandos.flush();

                jLabel2.setText("Estado: Desconectado");
                jLabel2.setForeground(java.awt.Color.RED);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El servidor ya se encuentra detenido.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Conecta al backend vía SSL/TLS y procesa los mensajes entrantes en segundo plano.
     */
    private void conectarAlServidorPython() {
        Thread hiloAdmin = new Thread(() -> {
            while (true) {
                try {
                    TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() { return null; }
                            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                        }
                    };

                    SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    SSLSocketFactory factory = sc.getSocketFactory();

                    Socket socketControl = factory.createSocket("127.0.0.1", 9009);
                    
                    this.salidaComandos = new java.io.PrintWriter(socketControl.getOutputStream(), true);
                    BufferedReader entrada = new java.io.BufferedReader(new java.io.InputStreamReader(socketControl.getInputStream()));

                    String jsonRegistroAdmin = "{\"type\":\"register\",\"from\":\"SERVER_GUI\",\"to\":\"ALL\",\"text\":\"Admin\",\"timestamp\":\"\"}\n";
                    salidaComandos.print(jsonRegistroAdmin);
                    salidaComandos.flush();

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        jLabel2.setText("Estado: Conectado"); 
                        jLabel2.setForeground(new java.awt.Color(0, 153, 51));
                    });

                    DateTimeFormatter formateador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    
                    String jsonRecibido;
                    while ((jsonRecibido = entrada.readLine()) != null) {
                        final String rawJson = jsonRecibido;
                        System.out.println("DEBUG SERVIDOR RECIBIÓ: " + rawJson);
                        
                        try {
                            final String jsonLimpio = rawJson.trim();
                            dto.MensajeDTO mensaje = mappers.MensajeMapper.toMensajeDTO(jsonLimpio);

                            if (mensaje != null) {
                                String emisor = mensaje.getEmisor() != null ? mensaje.getEmisor().getNombre() : "Desconocido";
                                String contenido = mensaje.getContenido() != null ? mensaje.getContenido() : "";
                                String lowerContenido = contenido.toLowerCase();

                                String prefijoHora = "";
                                if (mensaje.getFecha() != null) {
                                    prefijoHora = "[" + mensaje.getFecha().format(formateador) + "] ";
                                } else {
                                    prefijoHora = "[" + java.time.LocalDateTime.now().format(formateador) + "] ";
                                }

                                if (!emisor.equals("server") && !emisor.equals("SERVER_GUI")) {
                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        if (!existeUsuarioEnTabla(emisor)) {
                                            modeloTabla.addRow(new Object[]{emisor});
                                        }
                                    });
                                }

                                if (emisor.equals("server") && lowerContenido.contains("se ha unido al chat")) {
                                    String nuevoUsuario = contenido.split(" ")[0].trim();
                                    if (!nuevoUsuario.isEmpty() && !nuevoUsuario.equals("SERVER_GUI")) {
                                        javax.swing.SwingUtilities.invokeLater(() -> {
                                            if (!existeUsuarioEnTabla(nuevoUsuario)) {
                                                modeloTabla.addRow(new Object[]{nuevoUsuario});
                                            }
                                        });
                                    }
                                }

                                if (lowerContenido.contains("se ha desconectado") || lowerContenido.contains("expulsado")) {
                                    String usuarioSaliente = emisor;
                                    if (emisor.equals("server") || emisor.equals("SERVER_GUI")) {
                                        usuarioSaliente = contenido.split(" ")[0].trim(); 
                                    }
                                    final String userToRemove = usuarioSaliente;
                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        removerUsuarioDeTabla(userToRemove);
                                    });
                                }

                                final String mensajeFinal = prefijoHora + "[" + emisor + "]: " + contenido + "\n";
                                javax.swing.SwingUtilities.invokeLater(() -> {
                                    jTextArea2.append(mensajeFinal);
                                });
                                
                            } else {
                                String tiempoLocal = "[" + java.time.LocalDateTime.now().format(formateador) + "] ";
                                javax.swing.SwingUtilities.invokeLater(() -> {
                                    jTextArea2.append(tiempoLocal + "[RAW ALERTA]: " + rawJson + "\n");
                                });
                            }
                        } catch (Exception e) {
                            String tiempoLocal = "[" + java.time.LocalDateTime.now().format(formateador) + "] ";
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                jTextArea2.append(tiempoLocal + "[RAW ERROR]: " + rawJson + "\n");
                            });
                        }
                    }
                } catch (Exception e) {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        jLabel2.setText("Estado: Desconectado");
                        jLabel2.setForeground(java.awt.Color.RED);
                    });
                }

                try { Thread.sleep(3000); } catch (InterruptedException ex) { break; }
            }
        });
        hiloAdmin.setDaemon(true);
        hiloAdmin.start();
    }

    /**
     * Verifica si un usuario ya se encuentra listado en la tabla.
     */
    private boolean existeUsuarioEnTabla(String usuario) {
        if (usuario == null) return false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorCelda = modeloTabla.getValueAt(i, 0);
            if (valorCelda != null && valorCelda.toString().trim().equals(usuario.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Quita a un usuario de la tabla de conectados.
     */
    private void removerUsuarioDeTabla(String usuario) {
        if (usuario == null) return;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorCelda = modeloTabla.getValueAt(i, 0);
            if (valorCelda != null && valorCelda.toString().trim().equals(usuario.trim())) {
                modeloTabla.removeRow(i);
                break;
            }
        }
    }
    
    /**
     * Extrae un campo específico de una estructura JSON de texto.
     */
    private String extraerCampoJson(String json, String clave) {
        if (json == null || !json.contains(clave)) return null;
        try {
            int inicio = json.indexOf(clave) + clave.length();
            int fin = json.indexOf("\"", inicio);
            if (fin > inicio) {
                return json.substring(inicio, fin).trim();
            }
        } catch (Exception e) {
            System.err.println("Error extrayendo campo: " + e.getMessage());
        }
        return null;
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
