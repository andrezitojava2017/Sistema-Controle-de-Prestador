/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.ConexaoDB;
import Model.Dotacao_Model;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Dotacao_Dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * Faz a leitura de uma determinada dotacao pelo codigo fornecido como
     * parametro retorna um dotacao_model para preenchimento de formularios
     *
     * @param codigo
     * @return
     */
    public Dotacao_Model buscar_dotacao(int codigo) {

        Dotacao_Model dotacao = new Dotacao_Model();;
        try {
            String sql = "select * from prefeitura.tbl_lotacao where codigo_dotacao =" + codigo;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                dotacao.setCodigo(rs.getInt("codigo_dotacao"));
                dotacao.setDescricao(rs.getString("descricao"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela Dotacao\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dotacao;
    }

    /**
     * Grava uma nova dotacao na base de dados
     *
     * @param dotacao
     */
    public void gravar_nova_dotacao(Dotacao_Model dotacao) {
        try {
            String sql = "insert into prefeitura.tbl_lotacao(codigo_dotacao, descricao) values(?,?)";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, dotacao.getCodigo());
            stm.setString(2, dotacao.getDescricao());

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Nova dotacao foi gravada com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela Dotacao\n" + e, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }
    }

    /**
     * Captura as lotações(secretarias e dept) cadastra na base de dados
     *
     * @return List<Dotacao_Model>
     */
    public List<Dotacao_Model> carregarListaLotacao() {
        List<Dotacao_Model> lotacoes = new ArrayList<>();
        try {
            String sql = "select * from prefeitura.tbl_lotacao";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Dotacao_Model lot = new Dotacao_Model();
                lot.setCodigo(rs.getInt("codigo_dotacao"));
                lot.setDescricao(rs.getString("descricao"));
                lotacoes.add(lot);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de capturar dados da tabela Lotações\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return lotacoes;
    }

    
    /**
     * Faz atualização de um determinada lotação
     * @param infoLotacao
     * @return 
     */
    public int atualizarDadosLotacao(Dotacao_Model infoLotacao) {
        int retorno = 0;
        try {
            String sql = "update prefeitura.tbl_lotacao set codigo_dotacao=?, descricao=? where codigo_dotacao=" + infoLotacao.getCodigo();
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, infoLotacao.getCodigo());
            stm.setString(2, infoLotacao.getDescricao());
            stm.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de ataulizar dados de lotação\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConexaoDB.fecharConexao(con, stm);
        }
        return retorno;
    }
}
