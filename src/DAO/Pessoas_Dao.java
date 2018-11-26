/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pessoas_Model;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Pessoas_Dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * metodo que faz a gravção de um novo prestador de serviço na base de dados
     * tblPessoas
     *
     * @param pessoa
     */
    public boolean Gravar_Novo_Pesso(Pessoas_Model pessoa) {

        boolean salvo = false;
        try {
            String sql = "insert into prefeitura.tb_pessoas(nome, pis_pasep) values(?,?)";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            stm.setString(1, pessoa.getNome_pessoa());
            stm.setString(2, pessoa.getPisPasep());

            int retorno = stm.executeUpdate();

            if (retorno != 0) {
                salvo = true;
            } else {
                JOptionPane.showMessageDialog(null, "O prestador: " + pessoa.getNome_pessoa() + " não foi inserido na base", "Mensagem", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar novo prestador de serviço\n" + e);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

        return salvo;
    }

    /**
     * Metodo utilizdo para capturar informações de um determinado prestador
     *
     * @param codigo_pessoa
     * @return Pessoas_Model
     */
    public Pessoas_Model Buscar_Pessoa(int codigo_pessoa) {

        Pessoas_Model pessoa = new Pessoas_Model();

        try {
            String sql = "select * from prefeitura.tb_pessoas where codigo=" + codigo_pessoa;

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareCall(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                pessoa.setNome_pessoa(rs.getString("nome"));
                pessoa.setPisPasep(rs.getString("pis_pasep"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler dados do prestador de serviço\n" + e);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

        return pessoa;
    }

    /**
     * Recupera dados de um prestador cadastrado na base de dados
     *
     * @param pisPasep
     */
    public Pessoas_Model localizarPrestadorCadastrado(String pisPasep) {
        Pessoas_Model prestadorLocalizado = new Pessoas_Model();
        try {
            String sql = "select nome, pis_pasep "
                    + "from prefeitura.tb_pessoas "
                    + "where pis_pasep=" + pisPasep;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String pis = rs.getString("pis_pasep");
                String nome = rs.getString("nome");
                prestadorLocalizado.setPisPasep(pis);
                prestadorLocalizado.setNome_pessoa(nome);
//                System.out.println("Presador cadastrado: pis " + pis + " Nome: " + nome);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Pessoas_Dao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }
        return prestadorLocalizado;
    }
}
