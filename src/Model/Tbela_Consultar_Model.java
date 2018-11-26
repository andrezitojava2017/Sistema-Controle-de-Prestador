/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rh
 */
public class Tbela_Consultar_Model extends AbstractTableModel {

    List<Object[]> listaPrestador = new ArrayList<>();
    private final String colunas[] = {"Cod","Compt.", "Prestador","Fonte", "Sal. Base", "INSS Retido", "INSS Patronal", "Lotacao"};

    @Override
    public int getRowCount() {
        return listaPrestador.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return recuperarValor(linha, coluna);
            case 1:
                return recuperarValor(linha, coluna);
            case 2:
                return recuperarValor(linha, coluna);
            case 3:
                return recuperarValor(linha, coluna);
            case 4:
                return recuperarValor(linha, coluna);
            case 5:
                return recuperarValor(linha, coluna);
            case 6:
                return recuperarValor(linha, coluna); 
            case 7:
                return recuperarValor(linha, coluna); 
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return this.colunas[column];
    }

    /**
     * Add elementos na tabela
     *
     * @param informacoes
     */
    public void addLinha(Object informacoes[]) {
        this.listaPrestador.add(informacoes);
        this.fireTableDataChanged();
    }

    /**
     * Recebe uma Lista de vetores com os objetos para preencher a tabela
     * @param informacoes 
     */
    public void addListaServicos(List<Object[]> informacoes) {
        this.listaPrestador = informacoes;
        this.fireTableDataChanged();
    }

    /**
     * Recupera os objetos que esta na listaPrestador e os valores de cada
     * objeto indicado, preenchendo as colunas a cada iteração
     *
     * @param linha
     * @param coluna
     * @return
     */
    private String recuperarValor(int linha, int coluna) {

        String retornoValor = null;

        Object objetos[] = listaPrestador.get(linha);
        Servicos_Model servicos = (Servicos_Model) objetos[0];
        Pessoas_Model pessoas = (Pessoas_Model) objetos[1];
        Imposto_Model imposto = (Imposto_Model) objetos[2];
        Dotacao_Model lotacao = (Dotacao_Model) objetos[3];

        switch (coluna) {
            case 0:
                retornoValor = String.valueOf(servicos.getId());
                break;
            case 1:
                retornoValor = servicos.getCompetencia();
                break;
            case 2:
                retornoValor = pessoas.getNome_pessoa();
                break;
            case 3:
                retornoValor = String.valueOf(servicos.getFonte());
                break;
            case 4:
                retornoValor = formatarValor(servicos.getSal_base());
                break;
            case 5:
                retornoValor = formatarValor(imposto.getInss_retido());
                break;
            case 6:
                retornoValor = formatarValor(imposto.getInss_patronal());
                break;
            case 7:
                retornoValor = lotacao.getDescricao();
                break;
        }

        return retornoValor;
    }

    /**
     * Formata um valor para o padrão BR (R$123.0123,00)
     * @param valor
     * @return 
     */
    private String formatarValor(double valor){
        DecimalFormat decimal = new DecimalFormat("R$#,##0.00");
        String retorno = decimal.format(valor);
        return retorno;
    }
}
