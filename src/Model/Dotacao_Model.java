/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author rh
 */
public class Dotacao_Model {

    private String descricao;
    private int codigo;
    private int id_dotacao;

    public int getId_dotacao() {
        return id_dotacao;
    }

    public void setId_dotacao(int id_dotacao) {
        this.id_dotacao = id_dotacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
