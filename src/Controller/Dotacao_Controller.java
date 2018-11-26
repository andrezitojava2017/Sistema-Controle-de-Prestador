/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.Dotacao_Dao;
import Model.Dotacao_Model;
import Model.Localizar_Lotacao_Model;
import java.util.List;

/**
 *
 * @author rh
 */
public class Dotacao_Controller {
    
    
    /**
     * Buscar uma dotacao pre cadastrada na base de dados
     * @param codigo - dotacao fornecida pelo user
     * @return Dotacao_Model
     */
    public Dotacao_Model buscar_dotacao(int codigo){
        
        Dotacao_Dao dao = new Dotacao_Dao();
        Dotacao_Model model;
        
        model = dao.buscar_dotacao(codigo);
        
        return model;
    }
    
    /**
     * Gravação de uma nova dotação na base de dados
     * @param dotacao 
     */
    public void gravar_novo_dotacao(Dotacao_Model dotacao){
        
        Dotacao_Dao dao = new Dotacao_Dao();
        dao.gravar_nova_dotacao(dotacao);
    }
    
    /**
     * Preenche a tabale com informações das lotações cadastradas na base de dados
     * @param modelo
     * @param lotacao 
     */
    public void preencherTabelaLocalizarLotacao(Localizar_Lotacao_Model modelo, List<Dotacao_Model> lotacao){
        Localizar_Lotacao_Model model = modelo;
        model.addLinha(lotacao);
    }
    
    /**
     * Carrega uma lista com todas as lotaçoes cadastradas na base de dados
     * @return 
     */
    public List<Dotacao_Model> carregarListaLotacoes(){
        Dotacao_Dao dao = new Dotacao_Dao();
        List<Dotacao_Model> lotacao = dao.carregarListaLotacao();
        return lotacao;
    }
    
    /**
     * Atualiza uma determinada lotação
     */
    public int atualizarDadosLotacao(Dotacao_Model infoLotacao){
        Dotacao_Dao dao = new Dotacao_Dao();
        int retorno = dao.atualizarDadosLotacao(infoLotacao);
        return retorno;
    }
}
