/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rh
 */
public class Localizar_Lotacao_Model extends AbstractTableModel {

    private static List<Dotacao_Model> lotacao = new ArrayList<>();
    private final String colunas[] = {"Cod", "Descrição"};
    
    @Override
    public int getRowCount() {
        return lotacao.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        
        switch(coluna){
            case 0: 
                return recuperarValor(linha, coluna);
            case 1: 
                return recuperarValor(linha, coluna);
        }
        return null;
    }
    
    @Override
    public String getColumnName(int coluna){
        return this.colunas[coluna];
    }
    
    /**
     * Adiciona linhas a tabela
     * @param lotacao 
     */
    public void addLinha(List<Dotacao_Model> lotacao1){
        this.lotacao = lotacao1;
        this.fireTableDataChanged();
    }
    
    /**
     * Remove linhas da tabela
     * @param linha 
     */
    public void removerLinha(int linha){
        this.lotacao.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }
    
    /**
     * Recupera um valor do da List de objetos
     * @param linha
     * @param coluna
     * @return 
     */
    private String recuperarValor(int linha, int coluna){
        String retornoValor = null;
        
        Dotacao_Model lot = lotacao.get(linha);
        
        switch(coluna){
            case 0: retornoValor = String.valueOf(lot.getCodigo());
                break;
            case 1: retornoValor = lot.getDescricao();
                break;
        }
        return retornoValor;
    }
    
}
