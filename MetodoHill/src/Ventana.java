
import javax.swing.JOptionPane;


/* @Integrantes
 * Paola Berenice Flores Monge
 * Henrry Eduardo Hernandez Pinela
 */
public class Ventana extends javax.swing.JFrame {

    public Ventana() {
        initComponents();
    }

    public void encripta() {

        String palabra = txtEncriptar.getText().replaceAll("\\s", "");
        int palabraLargo = ((int) Math.ceil((double) txtEncriptar.getText().replaceAll("\\s", "").length() / 3));

        int palabraAscii[][] = new int[palabraLargo][3];
        int palabraEncriptada[][];

        for (int i = 0; i < palabraAscii.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (palabra.length() <= i * 3 + j) {
                    break;
                }
                palabraAscii[i][j] = (int) palabra.toUpperCase().charAt(i * 3 + j);
            }
        }

        palabraEncriptada = multiplicarMatrices(obtenerMatrizClave(), palabraAscii);

        txtDesencriptado.setText("");
        for (int i = 0; i < palabraEncriptada.length; i++) {
            for (int j = 0; j < 3; j++) {
                txtDesencriptado.append(palabraEncriptada[i][j] + " ");
            }
        }

    }

    public void desencripta() {

        String[] texto = txtEncriptar.getText().split("\\s+");
        int palabraLargo = ((int) Math.ceil((double) texto.length / 3));

        int palabraAscii[][] = new int[palabraLargo][3];
        int palabraDesencriptada[][];

        for (int i = 0; i < palabraAscii.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (texto.length <= i * 3 + j) {
                    break;
                }
                palabraAscii[i][j] = Integer.parseInt(texto[i * 3 + j]);
            }
        }

        double matrizInversa[][] = obtenerInversa(obtenerMatrizClaveDesencriptar());
        palabraDesencriptada = multiplicarMatricesDesencriptar(matrizInversa, palabraAscii);

        txtDesencriptado.setText("");
        for (int i = 0; i < palabraDesencriptada.length; i++) {
            for (int j = 0; j < 3; j++) {
                txtDesencriptado.append((char) palabraDesencriptada[i][j] + " ");
            }
        }
    }

    double[][] obtenerInversa(double[][] inversa) {
        double matrizInversa[][] = new double[3][3];
        double determinante = ((inversa[0][0] * inversa[1][1] * inversa[2][2]) + (inversa[0][1] * inversa[1][2] * inversa[2][0]) + (inversa[1][0] * inversa[2][1] * inversa[0][2])) - ((inversa[0][2] * inversa[1][1] * inversa[2][0]) + (inversa[0][1] * inversa[1][0] * inversa[2][2]) + (inversa[0][0] * inversa[1][2] * inversa[2][1]));

        matrizInversa[0][0] = ((inversa[1][1] * inversa[2][2]) - (inversa[1][2] * inversa[2][1])) / determinante;
        matrizInversa[0][1] = -((inversa[1][0] * inversa[2][2]) - (inversa[1][2]) * inversa[2][0]) / determinante;
        matrizInversa[0][2] = ((inversa[1][0] * inversa[2][1]) - (inversa[1][1] * inversa[2][0])) / determinante;
        matrizInversa[1][0] = -((inversa[0][1] * inversa[2][2]) - (inversa[0][2] * inversa[2][1])) / determinante;
        matrizInversa[1][1] = ((inversa[0][0] * inversa[2][2]) - (inversa[0][2] * inversa[2][0])) / determinante;
        matrizInversa[1][2] = -((inversa[0][0] * inversa[2][1]) - (inversa[2][0]) * inversa[0][1]) / determinante;
        matrizInversa[2][0] = ((inversa[0][1] * inversa[1][2]) - (inversa[0][2] * inversa[1][1])) / determinante;
        matrizInversa[2][1] = -((inversa[0][0] * inversa[1][2]) - (inversa[1][0] * inversa[0][2])) / determinante;
        matrizInversa[2][2] = ((inversa[0][0] * inversa[1][1]) - (inversa[1][0] * inversa[0][1])) / determinante;

        matrizInversa = intercambiarLugares(matrizInversa, 0, 1, 1, 0);
        matrizInversa = intercambiarLugares(matrizInversa, 2, 0, 0, 2);
        matrizInversa = intercambiarLugares(matrizInversa, 2, 1, 1, 2);

        return matrizInversa;

    }

    double[][] intercambiarLugares(double[][] matrizCambio, int a1, int a2, int b1, int b2) {
        double temp = matrizCambio[a1][a2];
        matrizCambio[a1][a2] = matrizCambio[b1][b2];
        matrizCambio[b1][b2] = temp;
        return matrizCambio;

    }

    int[][] multiplicarMatrices(int matrizClave[][], int matrizAscii[][]) {
        int matrizEncriptada[][] = new int[matrizAscii.length][3];

        for (int i = 0; i < matrizAscii.length; i++) {
            for (int j = 0; j < matrizClave.length; j++) {
                matrizEncriptada[i][j] = matrizClave[j][0] * matrizAscii[i][0] + matrizClave[j][1] * matrizAscii[i][1] + matrizClave[j][2] * matrizAscii[i][2];
            }
        }

        return matrizEncriptada;
    }

    int[][] multiplicarMatricesDesencriptar(double matrizClave[][], int matrizAscii[][]) {
        int matrizEncriptada[][] = new int[matrizAscii.length][3];

        for (int i = 0; i < matrizAscii.length; i++) {
            for (int j = 0; j < matrizClave.length; j++) {
                matrizEncriptada[i][j] = (int) ((matrizClave[j][0] * (double) matrizAscii[i][0]) + (matrizClave[j][1] * (double) matrizAscii[i][1]) + (matrizClave[j][2] * (double) matrizAscii[i][2]));  //System.out.println(matrizEncriptada[i][j]);
            }
        }

        return matrizEncriptada;
    }

    int[][] obtenerMatrizClave() {
        int tresTres[][] = new int[3][3];

        tresTres[0][0] = Integer.parseInt(txt1.getText());
        tresTres[0][1] = Integer.parseInt(txt2.getText());
        tresTres[0][2] = Integer.parseInt(txt3.getText());

        tresTres[1][0] = Integer.parseInt(txt4.getText());
        tresTres[1][1] = Integer.parseInt(txt5.getText());
        tresTres[1][2] = Integer.parseInt(txt6.getText());

        tresTres[2][0] = Integer.parseInt(txt7.getText());
        tresTres[2][1] = Integer.parseInt(txt8.getText());
        tresTres[2][2] = Integer.parseInt(txt9.getText());

        return tresTres;
    }

    double[][] obtenerMatrizClaveDesencriptar() {
        double tresTres[][] = new double[3][3];

        tresTres[0][0] = Double.parseDouble(txt1.getText());
        tresTres[0][1] = Double.parseDouble(txt2.getText());
        tresTres[0][2] = Double.parseDouble(txt3.getText());

        tresTres[1][0] = Double.parseDouble(txt4.getText());
        tresTres[1][1] = Double.parseDouble(txt5.getText());
        tresTres[1][2] = Double.parseDouble(txt6.getText());

        tresTres[2][0] = Double.parseDouble(txt7.getText());
        tresTres[2][1] = Double.parseDouble(txt8.getText());
        tresTres[2][2] = Double.parseDouble(txt9.getText());

        return tresTres;
    }

    boolean malaClave(String clave) {
        for (int i = 0; i < clave.length(); i++) {
            if (Character.isLetter(clave.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnEncriptar = new javax.swing.JButton();
        btnDesencriptar = new javax.swing.JButton();
        txt1 = new javax.swing.JTextField();
        txt2 = new javax.swing.JTextField();
        txt3 = new javax.swing.JTextField();
        txt4 = new javax.swing.JTextField();
        txt5 = new javax.swing.JTextField();
        txt6 = new javax.swing.JTextField();
        txt7 = new javax.swing.JTextField();
        txt8 = new javax.swing.JTextField();
        txt9 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEncriptar = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDesencriptado = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Método Hill");

        btnEncriptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEncriptar.setText("Encriptar");
        btnEncriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncriptarActionPerformed(evt);
            }
        });

        btnDesencriptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDesencriptar.setText("Desencriptar");
        btnDesencriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesencriptarActionPerformed(evt);
            }
        });

        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });

        txtEncriptar.setColumns(20);
        txtEncriptar.setRows(5);
        jScrollPane1.setViewportView(txtEncriptar);

        txtDesencriptado.setColumns(20);
        txtDesencriptado.setRows(5);
        jScrollPane2.setViewportView(txtDesencriptado);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Ingresa la matriz");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Palabra a Encriptar");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Palabra Encriptada o Desencriptada");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt1)
                                            .addComponent(txt2)
                                            .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt7)
                                            .addComponent(txt8)
                                            .addComponent(txt9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(112, 112, 112)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(90, 90, 90)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnEncriptar)
                                                .addGap(11, 11, 11))
                                            .addComponent(btnDesencriptar, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(17, 17, 17)))
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEncriptar)
                        .addGap(37, 37, 37)
                        .addComponent(btnDesencriptar)))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void btnEncriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncriptarActionPerformed
        if (txt1.getText().equals("") || malaClave(txt1.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt2.getText().equals("") || malaClave(txt2.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt3.getText().equals("") || malaClave(txt3.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt4.getText().equals("") || malaClave(txt4.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt5.getText().equals("") || malaClave(txt5.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt6.getText().equals("") || malaClave(txt6.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt7.getText().equals("") || malaClave(txt7.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt8.getText().equals("") || malaClave(txt8.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt9.getText().equals("") || malaClave(txt9.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else {
            encripta();
        }
    }//GEN-LAST:event_btnEncriptarActionPerformed

    private void btnDesencriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesencriptarActionPerformed
        if (txt1.getText().equals("") || malaClave(txt1.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt2.getText().equals("") || malaClave(txt2.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt3.getText().equals("") || malaClave(txt3.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt4.getText().equals("") || malaClave(txt4.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt5.getText().equals("") || malaClave(txt5.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt6.getText().equals("") || malaClave(txt6.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt7.getText().equals("") || malaClave(txt7.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt8.getText().equals("") || malaClave(txt8.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else if (txt9.getText().equals("") || malaClave(txt9.getText())) {
            JOptionPane.showMessageDialog(null, "Primero rellene todos y cada uno de los espacios de la matriz con valores válidos");
        } else {
            desencripta();
        }
    }//GEN-LAST:event_btnDesencriptarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDesencriptar;
    private javax.swing.JButton btnEncriptar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    private javax.swing.JTextField txt3;
    private javax.swing.JTextField txt4;
    private javax.swing.JTextField txt5;
    private javax.swing.JTextField txt6;
    private javax.swing.JTextField txt7;
    private javax.swing.JTextField txt8;
    private javax.swing.JTextField txt9;
    private javax.swing.JTextArea txtDesencriptado;
    private javax.swing.JTextArea txtEncriptar;
    // End of variables declaration//GEN-END:variables
}
