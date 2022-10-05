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
 * @author andre
 */
public class TableBuscarPrestadorAbstract extends AbstractTableModel {

    List<Pessoas_Model> listPrestador = new ArrayList<>();
    String colunas[] = {"PIS/PASEP", "NOME"};

    @Override
    public int getRowCount() {
        return listPrestador.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    

    @Override
    public Object getValueAt(int linha, int coluna) {

        switch (coluna) {
            case 0:
                return listPrestador.get(linha).getPisPasep();
            case 1:
                return listPrestador.get(linha).getNome_pessoa();
        }

        return null;

    }
    
    /**
     * Add um objeto Pessoas_Model a lista de prestadores
     *
     * @param prestador
     */
    public void addLinha(Pessoas_Model prestador) {
        this.listPrestador.add(prestador);
        this.fireTableDataChanged();
    }

    /**
     * Add uma List<Pessoas_Model> ao objeto lista
     * @param prestadores 
     */
    public void addLinha(List<Pessoas_Model> prestadores) {
        this.listPrestador = prestadores;
        this.fireTableDataChanged();
    }
    
    public void removerLinha(int linha){
        this.listPrestador.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

}
