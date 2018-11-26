/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Dotacao_Controller;
import Controller.Tabela_Prestadores_Control;
import Model.Dotacao_Model;
import Model.Localizar_Lotacao_Model;
import java.util.List;

/**
 *
 * @author rh
 */
public class Form_buscar_lotacao extends javax.swing.JDialog {

    // setando modelo da tabela
    Localizar_Lotacao_Model tabela = new Localizar_Lotacao_Model();

    // armazena secretaria escolhida
    public static Dotacao_Model lotacaoSelecionada = new Dotacao_Model();

    /**
     * Creates new form Form_buscar_lotacao
     */
    public Form_buscar_lotacao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabela_lotacao.setModel(tabela);
        carregarDadosTabela();
    }

    /**
     * Carrega a tabela com as lotaçoes cadastradas na base de dados
     */
    private void carregarDadosTabela() {
        Dotacao_Controller control = new Dotacao_Controller();
        List<Dotacao_Model> listaLotacao = control.carregarListaLotacoes();

        // preenche a tabela com a lista carregada
        control.preencherTabelaLocalizarLotacao(tabela, listaLotacao);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_lotacao = new javax.swing.JTable();
        btnSelecionar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Localizar uma secretaria");

        tabela_lotacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela_lotacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela_lotacaoMouseClicked(evt);
            }
        });
        tabela_lotacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabela_lotacaoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabela_lotacao);
        if (tabela_lotacao.getColumnModel().getColumnCount() > 0) {
            tabela_lotacao.getColumnModel().getColumn(0).setResizable(false);
            tabela_lotacao.getColumnModel().getColumn(1).setResizable(false);
        }

        btnSelecionar.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        btnSelecionar.setForeground(new java.awt.Color(255, 102, 0));
        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 102, 0));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelecionar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabela_lotacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabela_lotacaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabela_lotacaoKeyPressed

    private void tabela_lotacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela_lotacaoMouseClicked
        int linha = tabela_lotacao.getSelectedRow();
        this.lotacaoSelecionada.setCodigo(Integer.parseInt(tabela.getValueAt(linha, 0).toString()));
        this.lotacaoSelecionada.setDescricao(tabela.getValueAt(linha, 1).toString());

    }//GEN-LAST:event_tabela_lotacaoMouseClicked

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:
        int linha = tabela_lotacao.getSelectedRow();
        this.lotacaoSelecionada.setCodigo(Integer.parseInt(tabela.getValueAt(linha, 0).toString()));
        this.lotacaoSelecionada.setDescricao(tabela.getValueAt(linha, 1).toString());
        dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.lotacaoSelecionada.setCodigo(0);
        this.lotacaoSelecionada.setDescricao(null);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_buscar_lotacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_buscar_lotacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_buscar_lotacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_buscar_lotacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Form_buscar_lotacao dialog = new Form_buscar_lotacao(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_lotacao;
    // End of variables declaration//GEN-END:variables
}