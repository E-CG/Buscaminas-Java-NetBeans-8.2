
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Esteban Cossio Gonzalez
 */
public class Juego extends javax.swing.JFrame {

    int nroFilas = 8;
    int nroColumnas = 8;
    int nroMinas = 10;

    JButton[][] botonesTablero;

    Tablero tablero;

    /**
     * Creates new form Juego
     */
    public Juego() {
        initComponents();
        empezarJuegoNuevo();
    }

    private void empezarJuegoNuevo() {
        limpiarControles();
        iniciarControles();
        crearTrablero();
        repaint();       
    }

    void limpiarControles() {
        if (botonesTablero != null) {
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j] != null) {
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }
    }
    
    void deshabilitaControles() {
        if (botonesTablero != null) {
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j] != null) {
                        botonesTablero[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    private void crearTrablero() {
        tablero = new Tablero(nroFilas, nroColumnas, nroMinas);
        tablero.setEventoPartidaGanada(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaMinada : t) {
                    botonesTablero[casillaMinada.getPosFila()][casillaMinada.getPosColumna()].setText(":)");                 
                }
                JOptionPane.showMessageDialog(null, "¡Usted ha ganado!", "Mensaje victoria",JOptionPane.INFORMATION_MESSAGE);
                deshabilitaControles();
            }           
        });
        tablero.setEventoPartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for (Casilla casillaMinada : t) {
                    botonesTablero[casillaMinada.getPosFila()][casillaMinada.getPosColumna()].setText("*");
                }
                JOptionPane.showMessageDialog(null, "¡Usted ha perdido!", "Mensaje derrota",JOptionPane.INFORMATION_MESSAGE);
                deshabilitaControles();
            }
        });
        tablero.setEventoCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla t) { //Para que se puedan abrir en automatico todas las casillas que no tengan minas alrededor
                botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNroMinasAlrededor() == 0 ? "" : t.getNroMinasAlrededor() + "");
            }       
        });        
        tablero.mostrarTablero();
    }

    private void iniciarControles() {
        int posX = 25;
        int posY = 25;
        int anchoControl = 30;
        int altoControl = 30;

        botonesTablero = new JButton[nroFilas][nroColumnas];

        for (int i = 0; i < botonesTablero.length; i++) { //Instanciando botones recorriendo la Matriz
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i + "," + j);//Para saber las coordenadas del boton
                botonesTablero[i][j].setBorder(null);
                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setBounds(posX, posY, anchoControl, altoControl);
                } else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setBounds(
                            botonesTablero[i][j - 1].getX() + botonesTablero[i][j - 1].getWidth(),
                            posY, anchoControl, altoControl);
                } else {
                    botonesTablero[i][j].setBounds(
                            botonesTablero[i - 1][j].getX(),
                            botonesTablero[i - 1][j].getY() + botonesTablero[i - 1][j].getHeight(),
                            anchoControl, altoControl);
                }
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });

                getContentPane().add(botonesTablero[i][j]);
            }
        }
        this.setSize(botonesTablero[nroFilas-1][nroColumnas-1].getX()+botonesTablero[nroFilas-1][nroColumnas-1].getWidth()+45,
                botonesTablero[nroFilas-1][nroColumnas-1].getY()+botonesTablero[nroFilas-1][nroColumnas-1].getHeight()+90);
    }

    public void btnClick(ActionEvent e) { //Método para saber cual botón se presiona.
        JButton btn = (JButton) e.getSource();
        String[] coordenada = btn.getName().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);

        tablero.seleccionarCasilla(posFila, posColumna);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuPrincipante = new javax.swing.JMenuItem();
        menuIntermedio = new javax.swing.JMenuItem();
        menuExperto = new javax.swing.JMenuItem();
        menuPersonalizado = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(2, 83, 49));
        setForeground(new java.awt.Color(25, 84, 56));

        jMenu1.setText("Juego");

        menuPrincipante.setText("Principiante");
        menuPrincipante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrincipanteActionPerformed(evt);
            }
        });
        jMenu1.add(menuPrincipante);

        menuIntermedio.setText("Intermedio");
        menuIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIntermedioActionPerformed(evt);
            }
        });
        jMenu1.add(menuIntermedio);

        menuExperto.setText("Experto");
        menuExperto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExpertoActionPerformed(evt);
            }
        });
        jMenu1.add(menuExperto);

        menuPersonalizado.setText("Personalizado");
        menuPersonalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPersonalizadoActionPerformed(evt);
            }
        });
        jMenu1.add(menuPersonalizado);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuPrincipanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrincipanteActionPerformed
        empezarJuegoNuevo();
    }//GEN-LAST:event_menuPrincipanteActionPerformed

    private void menuPersonalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPersonalizadoActionPerformed
        int nro = Integer.parseInt(JOptionPane.showInputDialog("Digite el tamaño de la matriz [n*n]"));
        this.nroFilas = nro;
        this.nroColumnas = nro;
        int nroM = Integer.parseInt(JOptionPane.showInputDialog("Digite el número de minas [Recuerde que debe ser menor a la multiplicación de n*n]"));
        this.nroMinas = nroM;
        empezarJuegoNuevo();
    }//GEN-LAST:event_menuPersonalizadoActionPerformed

    private void menuIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIntermedioActionPerformed
        this.nroFilas = 16;
        this.nroColumnas = 16;
        this.nroMinas = 40;
        empezarJuegoNuevo();
    }//GEN-LAST:event_menuIntermedioActionPerformed

    private void menuExpertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExpertoActionPerformed
        this.nroFilas = 16;
        this.nroColumnas = 30;
        this.nroMinas = 99;
        empezarJuegoNuevo();
    }//GEN-LAST:event_menuExpertoActionPerformed

    
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
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuExperto;
    private javax.swing.JMenuItem menuIntermedio;
    private javax.swing.JMenuItem menuPersonalizado;
    private javax.swing.JMenuItem menuPrincipante;
    // End of variables declaration//GEN-END:variables
}
