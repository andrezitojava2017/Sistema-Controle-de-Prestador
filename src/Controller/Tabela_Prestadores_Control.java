/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Pessoas_Model;
import Model.Tbela_Lanc_Serv_Prestador;
import java.util.List;

/**
 *
 * @author rh
 */
public class Tabela_Prestadores_Control {
    
    /**
     * Envia a lista de prestadores para a classe abstrateTableModel, que é responsavelm
     * em preencher a tabela com informações dos prestadores
     * @param prestadores 
     * @param tabelaModel 
     */
    public void preencherTabelaPrestadores(List<Pessoas_Model>prestadores, Tbela_Lanc_Serv_Prestador tabelaModel){
        Tbela_Lanc_Serv_Prestador model = tabelaModel;
        model.addLinha(prestadores);
    }
    
    /**
     * Apos a inserção da prestação de servicos na base, é realizado a remoção da linha, da tabela que esta
     * no formulario de lançamento de serviços
     * @param numLinha
     * @param tabelaModel 
     */
    public void removerLinhaPrestadorServicos(int numLinha, Tbela_Lanc_Serv_Prestador tabelaModel){
        Tbela_Lanc_Serv_Prestador model = tabelaModel;
        model.removerLinha(numLinha);
    }
    
}
