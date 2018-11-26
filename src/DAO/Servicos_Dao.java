/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Dotacao_Model;
import Model.Imposto_Model;
import Model.Pessoas_Model;
import Model.Servicos_Model;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Servicos_Dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * Metod para ler dados da tabela serviços
     *
     * @param id - do serviço
     * @return
     */
    public Object[] Capturar_Dados_Servicos(int id) {

        Servicos_Model servico = new Servicos_Model();
        Imposto_Model imposto = new Imposto_Model();
        Object dados[] = new Object[2];

        try {

            String sql = "select * from prefeitura.tbl_servicos where id=" + id;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

//                servico.setId(rs.getInt("id"));
                imposto.setInss_patronal(rs.getDouble("inss_patronal"));
                imposto.setInss_retido(rs.getDouble("inss_retido"));
                servico.setSal_base(rs.getDouble("salario_base"));
                servico.setCompetencia(rs.getString("competencia"));
                servico.setEmpenho(rs.getInt("empenho"));
                servico.setLotação(rs.getString("cod_dotacao"));

                dados[0] = servico;
                dados[1] = imposto;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na leitura da tabela servicos\n" + e);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dados;
    }

    /**
     * Metodo que grava novo serviço
     *
     * @param servicos
     * @param pisPasep
     * @param impostos
     * @return int id_servico
     */
    public int Gravar_Novo_Servico(Servicos_Model servicos, Imposto_Model impostos, Pessoas_Model pisPasep) {

        int id_sevico = 0;

        try {
            String sql = "insert into prefeitura.tbl_servicos(competencia, empenho, fonte, pisPasep, inss_retido, inss_patronal, salario_base, cod_dotacao) values(?,?,?,?,?,?,?,?)";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // retorna o id gerado

            stm.setString(1, servicos.getCompetencia());
            stm.setInt(2, servicos.getEmpenho());
            stm.setInt(3, servicos.getFonte());
            stm.setString(4, pisPasep.getPisPasep());
            stm.setDouble(5, impostos.getInss_retido());
            stm.setDouble(6, impostos.getInss_patronal());
            stm.setDouble(7, servicos.getSal_base());
            stm.setInt(8, Integer.parseInt(servicos.getLotação()));

            stm.execute();
            ResultSet keys = stm.getGeneratedKeys();
            
            if(keys.next()){
                id_sevico = keys.getInt(1); // captura o ID gerado para este lançamento
            }
            
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar dados na tabela serviços\n" + e);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

        return id_sevico;
    }

    /**
     * Metodo que captura o ID de um serviço
     *
     * @return
     */
    public int ler_id_servico() {
        int id_sevico = 0;

        try {
            String sql = "select max(id) from prefeitura.tbl_servicos";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                id_sevico = rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar capturar ID da tabela servicos\n" + e);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return id_sevico;
    }

    /**
     * Mostra todos prestadores lançados na competencia informada
     * @param competencia
     * @return List<Obejct[]>
     */
    public List<Object[]> relatorioPrestador(String competencia) {

//        Object[][] prestador = new Object[4][4];
        List<Object[]> ex = new ArrayList<>();
//        Object[] dados = new Object[4];

        try {

            String sql = "select id, competencia,empenho,fonte,salario_base,inss_retido,inss_patronal,nome,descricao from prefeitura.tbl_servicos inner join prefeitura.tb_pessoas on tb_pessoas.pis_pasep = tbl_servicos.pisPasep inner join prefeitura.tbl_lotacao on tbl_lotacao.codigo_dotacao = tbl_servicos.cod_dotacao where tbl_servicos.competencia ='" + competencia + "'";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                Dotacao_Model lotacao = new Dotacao_Model();
                Servicos_Model servicos = new Servicos_Model();
                Imposto_Model imposto = new Imposto_Model();
                Pessoas_Model pessoas = new Pessoas_Model();
                Object[] dados = new Object[4];

                servicos.setId(rs.getInt(1));
                servicos.setCompetencia(rs.getString(2));
                servicos.setEmpenho(rs.getInt(3));
                servicos.setFonte(rs.getInt(4));
                servicos.setSal_base(rs.getDouble(5));
                imposto.setInss_retido(rs.getDouble(6));
                imposto.setInss_patronal(rs.getDouble(7));
                pessoas.setNome_pessoa(rs.getString(8));
                lotacao.setDescricao(rs.getString(9));

                dados[0] = servicos;
                dados[1] = pessoas;
                dados[2] = imposto;
                dados[3] = lotacao;

                ex.add(dados);
//                System.out.println(pessoas.getNome_pessoa());

            }

        } catch (SQLException e) {
            System.out.println("Erro ao tentar conectar a base de dados!\n" + e);
        } finally{
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return ex;
    }
    
    /**
     * Atualiza dados de um sevicos prestado
     * @param id_lancamento
     * @param servicos
     * @param inss
     * @throws SQLException 
     */
    public int atualizarLancamentoPrestacao(int id_lancamento, Servicos_Model servicos, Imposto_Model inss, Pessoas_Model prestador) throws SQLException{
        int retorno = 0;
        try {
            String sql = "UPDATE prefeitura.tbl_servicos SET "
                    + "competencia =?, "
                    + "empenho =?, "
                    + "fonte=?, "
                    + "pisPasep =?, "
                    + "inss_retido=?, "
                    + "inss_patronal=?, "
                    + "salario_base=?, "
                    + "cod_dotacao=? "
                    + "WHERE id="+ id_lancamento;
            
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            
            stm.setString(1, servicos.getCompetencia());
            stm.setInt(2, servicos.getEmpenho());
            stm.setInt(3, servicos.getFonte());
            stm.setString(4, prestador.getPisPasep());
            stm.setDouble(5, inss.getInss_retido());
            stm.setDouble(6, inss.getInss_patronal());
            stm.setDouble(7, servicos.getSal_base());
            stm.setString(8, servicos.getLotação());
            
            retorno = stm.executeUpdate();
            
        } finally{
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }
        return retorno;
    }

    
    public List<Object> recuperaLancamentoServicos(String competencia, int id_lancamento){
        List<Object> dadosLancamento = new ArrayList();
        try {
            String sql = "SELECT prefeitura.tbl_servicos.competencia,"
                    + "prefeitura.tbl_servicos.empenho,"
                    + "prefeitura.tbl_servicos.inss_retido,"
                    + "prefeitura.tbl_servicos.inss_patronal,"
                    + "prefeitura.tbl_servicos.salario_base,"
                    + "prefeitura.tbl_servicos.cod_dotacao,"
                    + "prefeitura.tbl_servicos.fonte,"
                    + "prefeitura.tb_pessoas.nome,"
                    + "prefeitura.tb_pessoas.pis_pasep,"
                    + "prefeitura.tbl_lotacao.descricao"
                    + " FROM prefeitura.tbl_servicos"
                    + " INNER JOIN prefeitura.tb_pessoas ON "
                    + "prefeitura.tb_pessoas.pis_pasep = prefeitura.tbl_servicos.pisPasep "
                    + "INNER JOIN prefeitura.tbl_lotacao ON "
                    + "prefeitura.tbl_servicos.cod_dotacao = prefeitura.tbl_lotacao.codigo_dotacao "
                    + "WHERE competencia='" + competencia + "' and id ='" + id_lancamento + "'";
            
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            
            while(rs.next()){
                Servicos_Model servico = new Servicos_Model();
                Pessoas_Model prestador = new Pessoas_Model();
                Imposto_Model imposto = new Imposto_Model();
                Dotacao_Model dotacao = new Dotacao_Model();
                
                servico.setCompetencia(rs.getString(1));
                servico.setEmpenho(rs.getInt(2));
                imposto.setInss_retido(rs.getDouble(3));
                imposto.setInss_patronal(rs.getDouble(4));
                servico.setSal_base(rs.getDouble(5));
                dotacao.setCodigo(rs.getInt(6));
                servico.setFonte(rs.getInt(7));
                prestador.setNome_pessoa(rs.getString(8));
                prestador.setPisPasep(rs.getString(9));
                dotacao.setDescricao(rs.getString(10));
                
                // add elementos na lista
                dadosLancamento.add(servico);
                dadosLancamento.add(prestador);;
                dadosLancamento.add(imposto);
                dadosLancamento.add(dotacao);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de leitura de informações da tabela de servicos!\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally{
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }
        
        return dadosLancamento;
    }
    
    /**
     * Deleta um registro da tabela de servicos
     * @param idRegistro
     * @return 
     */
    public int exluirRegistro(int idRegistro){
        int linha = 0;
        try {
            String sql = "DELETE FROM prefeitura.tbl_servicos WHERE (id= '" + idRegistro + "')";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            linha = stm.executeUpdate();
//            System.out.println("Linha deletada: " + linha);
            
        } catch (SQLException ex) {
            Logger.getLogger(Servicos_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linha;
    }
}
