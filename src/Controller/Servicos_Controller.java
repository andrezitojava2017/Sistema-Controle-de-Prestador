/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.Servicos_Dao;
import Model.Imposto_Model;
import Model.Pessoas_Model;
import Model.Servicos_Model;
import Model.Tbela_Consultar_Model;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Servicos_Controller {

    /**
     * Metodo utilizado para preencher a tabela do formulario
     *
     * @param id_servico
     * @return
     */
    public Object[] Capturar_Dados_Servicos(int id_servico) {

        Object dados[];
//        Servicos_Model servico = new Servicos_Model();
        Servicos_Dao dao = new Servicos_Dao();
        dados = dao.Capturar_Dados_Servicos(id_servico);

        return dados;
    }

    /**
     * Metodo que grava novo serviço
     *
     * @param servico
     * @param pisPasep
     * @param impostos
     * @return
     */
    public int gravar_novo_servico(Servicos_Model servico, Imposto_Model impostos, Pessoas_Model pisPasep) {

        int id_servico = 0;
        Servicos_Dao novo_servico = new Servicos_Dao();
        id_servico = novo_servico.Gravar_Novo_Servico(servico, impostos, pisPasep);

        return id_servico;
    }

    /**
     * Calcula o INSS retido e INSS patronal
     *
     * @param custoServicos
     */
    public double[] calcularImpostos(double custoServicos) {
        Servicos_Model servicos = new Servicos_Model();
        double impostos[] = servicos.calcularImpostos(custoServicos);
        return impostos;
    }

    /**
     * Recupera informações de servicos lançados na base de dados, de uma determinada competencia
     * e faz o preenchimento do formulario Form_consulta
     * @param competencia
     * @param modelo 
     */
    public void consultarLancamentoServicos(String competencia, Tbela_Consultar_Model modelo) {
        
        // recuperamos os dados da base
        Servicos_Dao dao = new Servicos_Dao();
        List<Object[]> dados = dao.relatorioPrestador(competencia);

        // aqui passamos o modelo da tabela para o Tbela_Model
        Tbela_Consultar_Model preencherTabela = modelo;
        preencherTabela.addListaServicos(dados);
    }
    
    /**
     * Atualiza infomaçes de um determinado registro da tabela servicos
     * @param id_lancamento
     * @param servicos
     * @param inss 
     */
    public void atualizarLancamentoServicos(int id_lancamento, Servicos_Model servicos, Imposto_Model inss, Pessoas_Model prestador){
        try {
            Servicos_Dao dao = new Servicos_Dao();
            int retorno = dao.atualizarLancamentoPrestacao(id_lancamento, servicos, inss, prestador);
            if(retorno != 0){
                JOptionPane.showMessageDialog(null, "As informações foram atualizadas com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de atualizar registro\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Recupera os dados de um determinado lancamento de servicos
     * utilizado para alterar determinado lançamento
     * @param competencia
     * @param id_lancamento
     * @return List<Object> - dados recuperados da base, com objetos Pessoas_Model, Servicos_Model, Impostos_Model, Dotacao_Model
     */
    public List<Object> recuperLancamentoServico(String competencia, int id_lancamento){
        Servicos_Dao dao = new Servicos_Dao();
        List<Object> dadosLanc = dao.recuperaLancamentoServicos(competencia, id_lancamento);
        
        return dadosLanc;
    }
    
    
    /**
     * Faz exclusão de um determinado registro selecionado na tabela de servicos
     * @param idRegistro 
     */
    public int excluirRegistro(int idRegistro){
        Servicos_Dao del = new Servicos_Dao();
        int linha = del.exluirRegistro(idRegistro);
        return linha;
    }
}
