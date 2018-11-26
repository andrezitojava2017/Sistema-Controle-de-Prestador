/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author rh
 */
public class impressao_controller {

    /**
     * 
     * @param caminhoRel
     * @param compt 
     */
    public void abrirRelatorio(String caminhoRel, String compt) {

        try {
            Connection con = Conexao.ConexaoDB.getconection();

            Map parametros = new HashMap();
            parametros.put("competencia", compt);
//            JasperReport jasperReport = JasperCompileManager.compileReport(caminhoRel);
            JasperPrint imprimir = JasperFillManager.fillReport(caminhoRel, parametros, con);

            JasperViewer visualizar = new JasperViewer(imprimir, false);
            visualizar.setVisible(true);

        } catch (JRException | SQLException ex) {
            Logger.getLogger(impressao_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
