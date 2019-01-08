/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.Pessoas_Dao;
import Model.Pessoas_Model;
import View.Form_Lanc_Servicos;
import View.Form_Lancar_Prest;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Pessoas_Controller {

    /**
     * Metodo para salvar cadastro de um novo prestador de serviço
     *
     * @param prestador
     * @return
     */
    public boolean gravar_Novo_Prestador(List<Pessoas_Model> prestador) {
        boolean retorno;
        Pessoas_Model salvarPrest = new Pessoas_Model();
        return retorno = salvarPrest.gravarPrestador(prestador);
    }

    
    /**
     * Metodo para salar cadastro de um novo prestador de serviços, caso não
     * tenha no arquivo .RE
     * @param prestador
     * @return 
     */
    public boolean gravar_Novo_Prestador(Pessoas_Model prestador) {
        boolean retorno;
        Pessoas_Model salvarPrest = new Pessoas_Model();
        return retorno = salvarPrest.gravarNovoPrestador(prestador);
    }

    /**
     * Metodo utilizdo para capturar informações de um determinado prestador
     *
     * @param codigo_prestador
     * @return Pessoas_Model
     */
    public Pessoas_Model Buscar_Prestador(int codigo_prestador) {

        Pessoas_Dao dao = new Pessoas_Dao();
        Pessoas_Model pessoa = new Pessoas_Model();

        pessoa = dao.Buscar_Pessoa(codigo_prestador);

        return pessoa;
    }

    /**
     * Recupera dados de um determinado prestador, e verifica se o mesmo esta
     * cadastrado ou nao na base de dados
     *
     * @param pisPasep
     * @param caminhoArquivo
     */
    public void buscarPisPasepBaseDados(List<String> pisPasep, String caminhoArquivo) {

        Pessoas_Model model = new Pessoas_Model();

        // verificamos se há o pis/pasep cadastrado na base de dados, retornando uma lista
        // contendo os CADASTRADOS e NÃO CADASTRADOS
        List<Object> listaPrestador = model.buscarPisPasepBaseDados(pisPasep);

        //Passando lista de prestadores que não estão na base de dados,
        // para mostrar na view Form_lancar_prest
        verificaListaPrestadorNaoCadastrado(listaPrestador, caminhoArquivo);
    }

    /**
     * Verifica se há elementos na Lista prestadorNaoCadastrados que retornou do
     * metodo buscarPisPasepBaseDados()
     */
    private void verificaListaPrestadorNaoCadastrado(List<Object> listaPrestador, String caminhoArquivo) {

        // realizando cast()
        List<Pessoas_Model> NaoCadastrado = (List<Pessoas_Model>) listaPrestador.get(1);

        if (NaoCadastrado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum prestador localizado na lista de NÃO CADASTRADOS", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            // recuperar a lista dos prestadores que ESTÃO CADASTRADOS e passa para a view que ira
            // capturar os valores de cada prestador
            List<Pessoas_Model> cadastrados = (List<Pessoas_Model>) listaPrestador.get(0);
            Form_Lanc_Servicos control = new Form_Lanc_Servicos(null, true, cadastrados);
            control.setVisible(true);

        } else {

            // Enviando para a tela que exibira os prestadores que NÃO ESTÃO CADASTRADOS NA BASE
            Form_Lancar_Prest exibir = new Form_Lancar_Prest(null, true, NaoCadastrado, caminhoArquivo);
            exibir.setVisible(true);

        }
    }
}
