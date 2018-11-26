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
public class Tbela_Lanc_Serv_Prestador extends AbstractTableModel {

    List<Pessoas_Model> prestadores = new ArrayList<>();
    String colunas[] = {"Prestador", "PIS/PASEP"};

    @Override
    public int getRowCount() {
        return prestadores.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return prestadores.get(linha).getNome_pessoa();

            case 1:
                return prestadores.get(linha).getPisPasep();

        }
        return null;
    }

    /**
     * Add um objeto Pessoas_Model a lista de prestadores
     *
     * @param prestador
     */
    public void addLinha(Pessoas_Model prestador) {
        this.prestadores.add(prestador);
        this.fireTableDataChanged();
    }

    /**
     * Add uma List<Pessoas_Model> ao objeto lista
     * @param prestadores 
     */
    public void addLinha(List<Pessoas_Model> prestadores) {
        this.prestadores = prestadores;
        this.fireTableDataChanged();
    }
    
    public void removerLinha(int linha){
        this.prestadores.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }
}
