/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.Pessoas_Dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author rh
 */
public class Pessoas_Model {

    private int id;
    private String nome_pessoa, pisPasep;

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Verifica a existencia do pis/pasep na base de dados
     *
     * @param linhas
     * @return Pessoas_Model()
     */
    public List<Object> buscarPisPasepBaseDados(List<String> linhas) {

        List<Object> listaPrestador = new ArrayList<>();
        List<Pessoas_Model> prestadorCadastrados = new ArrayList<>();
        List<Pessoas_Model> prestadorNaoCadastrados = new ArrayList<>();

        // capturando as linhas que foram lidas do arquivo
        for (Iterator<String> iterator = linhas.iterator(); iterator.hasNext();) {
            String linha = iterator.next();

            String pis = linha.substring(32, 43);
            String nome = linha.substring(53, 123);

            // Passando pis para metodo DAO, que retorna um objeto tipo Pessoas_Model,
            // com as infomrações do pis
            Pessoas_Dao dao = new Pessoas_Dao();
            Pessoas_Model prest = dao.localizarPrestadorCadastrado(pis);

            // Verificamos retorno é diferente de nulo, indicando que o prestador existe na base de dados
            // logo fazemos a remoção deste iterator
            if (prest.getPisPasep() != null) {
                prestadorCadastrados.add(prest);
                iterator.remove();

            } else {
                Pessoas_Model prestador = new Pessoas_Model();
                prestador.setPisPasep(pis);
                prestador.setNome_pessoa(nome.trim());
                prestadorNaoCadastrados.add(prestador);
                iterator.remove();
            }
        }

        // add listas dos prestadores cadastrados e não cadastrados na listaPrestadores
        listaPrestador.add(prestadorCadastrados); // posicao 0 
        listaPrestador.add(prestadorNaoCadastrados); // posicao 1

//        for (Pessoas_Model prestadorCadastrado : prestadorCadastrados) {
//            System.out.println("Cadastrados: " + prestadorCadastrado.getNome_pessoa());
//        }
//        for (Pessoas_Model prestadorNotCadastrado : prestadorNaoCadastrados) {
//            System.out.println("Não Cadastrados: " + prestadorNotCadastrado.getNome_pessoa());
//        }
        return listaPrestador;
    }

    
    /**
     * Faz a inserção dos prestadores na base de dados
     * @param prestador List<>
     * @return 
     */
    public boolean gravarPrestador(List<Pessoas_Model> prestador) {

        boolean prestGravados[] = new boolean[prestador.size()]; // prestadores que foram inseridos na base
        List<Pessoas_Model> errorPrestNaoGravado = new ArrayList<>(); // possivels prestadores que não foi inserido
        int contador = 0;
        boolean retorno = false;

        Pessoas_Dao gravar = new Pessoas_Dao();
        
        for (Pessoas_Model prestador1 : prestador) {
            
            // metodo gravar retorna verdadeiro, caso a gravação seja completada
            boolean salvo = gravar.Gravar_Novo_Pesso(prestador1);
            
            // add na lista de prestadores gravados, caso contrario criamos outra
            // lista para armazenar os q não puderam ser inseridos na base
            if(salvo){
                prestGravados[contador] = salvo;
//                System.out.println("Prestador inserido: " + prestador1.getNome_pessoa());
                contador ++;
                retorno = true;
            } else {
                errorPrestNaoGravado.add(prestador1);
//                System.out.println("Prestador NÃO INSERIDO: " + prestador1.getNome_pessoa());
                contador++;
            }
        }
        return retorno;
    }
    
    /**
     * Faz a inserção do novo prestador na base de dados,
     * tbl_pessoas
     * @param prestador
     * @return boolean retorno  - indica se foi inserido com sucesso
     */
    public boolean gravarNovoPrestador(Pessoas_Model prestador){
        Pessoas_Dao dao = new Pessoas_Dao();
        boolean retorno = dao.Gravar_Novo_Pesso(prestador);
        
        return retorno;        
    }
}
