/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Servicos_Controller;
import Controller.Tabela_Prestadores_Control;
import Model.Dotacao_Model;
import Model.Imposto_Model;
import Model.Pessoas_Model;
import Model.Servicos_Model;
import Model.Tbela_Lanc_Serv_Prestador;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author rh
 */
public class Form_Lanc_Novo_Servico extends javax.swing.JDialog {

    Tbela_Lanc_Serv_Prestador tabela = new Tbela_Lanc_Serv_Prestador();
    private List<Pessoas_Model> PrestadorCadastrados = new ArrayList<>();
    private static double inssPatronal = 0;
    private static double inssRetido = 0;
    private int id_lancamento_servico;
    private int numLinhaRemover;

    /**
     * Creates new form Form_Lanc_Servicos
     * @param parent
     * @param modal
     */
    public Form_Lanc_Novo_Servico(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        competencia();
    }

    /**
     * Construtor que inicializa os campos com dados de um prestador utilizado
     * para fazer alteração de lancamentos á inseridos na base de dados. chamado
     * pela tela de consulta
     * @param parent
     * @param modal
     * @param competencia
     * @param id_lancamento
     */
    public Form_Lanc_Novo_Servico(java.awt.Frame parent, boolean modal, String competencia, int id_lancamento) {
        super(parent, modal);
        initComponents();
        alterarDadosLancamento(competencia, id_lancamento);
        this.id_lancamento_servico = id_lancamento;
    }

    /**
     * Construtor que faz o preenchimento da tabela com informações dos
     * prestadores
     *
     * @param parent
     * @param modal
     * @param prestador
     */
    public Form_Lanc_Novo_Servico(java.awt.Frame parent, boolean modal, List<Pessoas_Model> prestador) {
        super(parent, modal);
        initComponents();
        this.PrestadorCadastrados = prestador;
        competencia();
    }

    /**
     * Remove a linha da tabela, qdo o prestador de serviços for inserido na
     * base de dados
     */
    private void removerLinhaPrestador() {

        // pergunta ao usuario se há novos serviços a serem inseridos pelo prestador selecionado, caso POSITIVO não exlui da lista
        if (JOptionPane.showConfirmDialog(null, "Incluir mais serviços deste prestador?", "Mensagem", JOptionPane.YES_NO_OPTION) == 0) {
            // limpa os campos do formulario
            campoEmpenho.setText("");
            campoFonteEmpenho.setText("");
            campoValor.setText("");
        } else {
            if (numLinhaRemover != 0 || numLinhaRemover == 0) {

                Tabela_Prestadores_Control removerLinha = new Tabela_Prestadores_Control();
                removerLinha.removerLinhaPrestadorServicos(numLinhaRemover, tabela);
            } else {
                JOptionPane.showMessageDialog(this, "Atenção: Selecione uma linha da tabela de prestadores!", "Mensagem", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Este metodo recupera as informações dos campos e envia para o metodo
     * verificarCamposPreenchidos() logo apos, se o retorno indicar que todos
     * foram preenchidos, fazemos a captura dos campos.
     *
     * Este metodo é utilizado para salvar um novo registro na base, como também
     * para autalizar registros da base.
     *
     * @return List<Object> de varios tipos: Pessoas_Model, Servicos_Model,
     * Impostos_Model.
     */
    private List<Object> salvarAtualizarDados() {

        List<Object> dadosPrestacao = new ArrayList();

        //List que ira capturar todos os campos do formulario, para verificaçao
        // se foram preenchidos
        List<Component[]> compt = new ArrayList<>();
        Component comp1[] = jPanel1.getComponents();
        Component comp2[] = jPanel2.getComponents();
        compt.add(comp1);
        compt.add(comp2);

        // verificando se os campos estão preenchidos
        int vazio = verificarCamposPreenchidos(compt);

        // caso true, podemos capturar os dados dos campos
        if (vazio == 0) {

            // lançar registro de serviço prestado
            Pessoas_Model prestador = new Pessoas_Model();
            Servicos_Model servicos = new Servicos_Model();
            Imposto_Model impostos = new Imposto_Model();
//            Dotacao_Model dotacao = new Dotacao_Model();

            prestador.setPisPasep(campoPisPasepPrestador.getText());
            servicos.setCompetencia(campoCompetencia.getText());
            servicos.setEmpenho(Integer.parseInt(campoEmpenho.getText()));
            servicos.setFonte(Integer.parseInt(campoFonteEmpenho.getText()));
            servicos.setLotação(campoCodLotacao.getText());
            servicos.setSal_base(Double.parseDouble(campoValor.getText().replace(",", ".")));

            impostos.setInss_retido(this.inssRetido);
            impostos.setInss_patronal(this.inssPatronal);

            dadosPrestacao.add(prestador);
            dadosPrestacao.add(servicos);
            dadosPrestacao.add(impostos);

//            int codLotacao = Integer.parseInt(campoCodLotacao.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Atenção: todos os campos devem ser preenchidos! Verifique", "Atenção", JOptionPane.INFORMATION_MESSAGE);
        }
        return dadosPrestacao;
    }

    /**
     * Recupera dados de um lançamento de sevicos, para preencher os formularios
     * deste frame. Utilizado para alteração de lancamentos
     *
     * @param competencia
     * @param id_lancamento
     */
    private void alterarDadosLancamento(String competencia, int id_lancamento) {
        Servicos_Controller control = new Servicos_Controller();
        List<Object> dadosLanc = control.recuperLancamentoServico(competencia, id_lancamento);

        // recuperamos os objetos
        Servicos_Model servicos = (Servicos_Model) dadosLanc.get(0);
        Pessoas_Model prestador = (Pessoas_Model) dadosLanc.get(1);
        Imposto_Model imposto = (Imposto_Model) dadosLanc.get(2);
        Dotacao_Model dotacao = (Dotacao_Model) dadosLanc.get(3);

        // agora iremos preencher os campos do formulario com as informações recuperadas da base
        campoCompetencia.setText(servicos.getCompetencia());
        campoNomePrestador.setText(prestador.getNome_pessoa());
        campoPisPasepPrestador.setText(prestador.getPisPasep());
        campoEmpenho.setText(String.valueOf(servicos.getEmpenho()));
        campoCodLotacao.setText(String.valueOf(dotacao.getCodigo()));
        campoDescricaoLotacao.setText(dotacao.getDescricao());
        campoFonteEmpenho.setText(String.valueOf(servicos.getFonte()));
        campoValor.setText(String.valueOf(servicos.getSal_base()));

        // Ativa/Desativa botoes
        btnSalvarRegistro.setEnabled(false);
        btn_salvar_alteracao.setEnabled(true);
    }

    /**
     * Verifica se todos os campos foram devidament preenchidos pelo usuario
     *
     * @param componentes
     * @return
     */
    private int verificarCamposPreenchidos(List<Component[]> componentes) {

        int vazio = 0;

        // recuperamos um vetor do tipo component
        for (Component cmpt[] : componentes) {
            // recuperamos o tamanho, para controlar no contador
            for (int i = 0; i < cmpt.length; i++) {
                // um component em uma posição do vetor
                Component comp1 = cmpt[i];
                // verificamos se é uma instancia de JTextField
                if (comp1 instanceof JTextField) {
                    // realizando cast e logo, se não estiver preenchido exibe uma msg ao usuario
                    JTextField campo = (JTextField) comp1;
                    if (campo.getText().isEmpty()) {
                        campo.setBorder(new LineBorder(Color.red));
                        vazio++;
                    } else {
                        campo.setBorder(new LineBorder(Color.darkGray));
                    }
                }
            }
        }
        return vazio;
    }

    /**
     * Preenche os labels com valores formatados
     *
     * @param servicos
     */
    private void preencherCampos(Servicos_Model servicos) {
        Servicos_Controller control = new Servicos_Controller();

        // calcula os impostos RETIDO e PATRONAL
        double impostos[] = control.calcularImpostos(servicos.getSal_base());

        // formata os valores com R$
        DecimalFormat formatar = new DecimalFormat("R$#,##0.00");

        // preenchendo labels
        lbl_custo_servicos.setText(String.valueOf(formatar.format(servicos.getSal_base())));
        lbl_inss_retido.setText(String.valueOf(formatar.format(impostos[0])));
        lbl_inss_patronal.setText(String.valueOf(formatar.format(impostos[1])));
        lbl_soma_retencao.setText(String.valueOf(formatar.format(impostos[0] + impostos[1])));

        // atribuindo valores aos atributos inssPatronal e inssRedito. 
        //Esses serão enviados para gravar na base
        this.inssPatronal = impostos[1];
        this.inssRetido = impostos[0];
    }

    /**
     * Preenche o campo competencia no formato correto
     */
    private void competencia() {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern("MM/yyyy");
        campoCompetencia.setText(formatar.format(data));
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoNomePrestador = new javax.swing.JTextField();
        campoPisPasepPrestador = new javax.swing.JTextField();
        campoCompetencia = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        campoEmpenho = new javax.swing.JTextField();
        campoFonteEmpenho = new javax.swing.JTextField();
        campoValor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        campoCodLotacao = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        campoDescricaoLotacao = new javax.swing.JTextField();
        btnBuscarLotacao = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_custo_servicos = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_inss_retido = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_inss_patronal = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_soma_retencao = new javax.swing.JLabel();
        btnSalvarRegistro = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_cad_nova_lotacao = new javax.swing.JButton();
        btn_salvar_alteracao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lançamento de serviços empenhados");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Prestador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("COMPETENCIA:");

        jLabel2.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("PRESTADOR:");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 255));
        jLabel3.setText("PIS/PASEP:");

        campoNomePrestador.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoNomePrestador.setForeground(new java.awt.Color(0, 102, 102));
        campoNomePrestador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoNomePrestadorKeyPressed(evt);
            }
        });

        campoPisPasepPrestador.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoPisPasepPrestador.setForeground(new java.awt.Color(0, 102, 102));
        campoPisPasepPrestador.setEnabled(false);
        campoPisPasepPrestador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoPisPasepPrestadorKeyPressed(evt);
            }
        });

        try {
            campoCompetencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButton1.setText("buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(campoPisPasepPrestador, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addComponent(campoNomePrestador, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoNomePrestador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoPisPasepPrestador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Prestador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        jLabel4.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 255));
        jLabel4.setText("EMPENHO:");

        jLabel5.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 255));
        jLabel5.setText("FONTE EMPENHADA:");

        jLabel6.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 255));
        jLabel6.setText("CUSTOS SERVICO:");

        campoEmpenho.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoEmpenho.setForeground(new java.awt.Color(0, 102, 102));
        campoEmpenho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoEmpenhoKeyPressed(evt);
            }
        });

        campoFonteEmpenho.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoFonteEmpenho.setForeground(new java.awt.Color(0, 102, 102));
        campoFonteEmpenho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoFonteEmpenhoKeyPressed(evt);
            }
        });

        campoValor.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoValor.setForeground(new java.awt.Color(0, 102, 102));
        campoValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoValorKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 255));
        jLabel15.setText("DESCRIÇÃO:");

        campoCodLotacao.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoCodLotacao.setForeground(new java.awt.Color(0, 102, 102));

        jLabel16.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 255));
        jLabel16.setText("COD. LOTAÇÃO");

        campoDescricaoLotacao.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        campoDescricaoLotacao.setForeground(new java.awt.Color(0, 102, 102));

        btnBuscarLotacao.setText("buscar");
        btnBuscarLotacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLotacaoActionPerformed(evt);
            }
        });
        btnBuscarLotacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBuscarLotacaoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoEmpenho, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(campoFonteEmpenho)
                    .addComponent(campoValor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDescricaoLotacao)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscarLotacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoCodLotacao))
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoEmpenho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodLotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(campoFonteEmpenho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(btnBuscarLotacao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(campoValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDescricaoLotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        jLabel7.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText("VALOR EMPENHADO:");

        lbl_custo_servicos.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 14)); // NOI18N
        lbl_custo_servicos.setForeground(new java.awt.Color(255, 102, 0));
        lbl_custo_servicos.setText("valor");

        jLabel9.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("INSS RETIDO:");

        lbl_inss_retido.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 14)); // NOI18N
        lbl_inss_retido.setForeground(new java.awt.Color(255, 102, 0));
        lbl_inss_retido.setText("retido");

        jLabel11.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("INSS PATRONAL:");

        lbl_inss_patronal.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 14)); // NOI18N
        lbl_inss_patronal.setForeground(new java.awt.Color(255, 102, 0));
        lbl_inss_patronal.setText("patronal");

        jLabel13.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 102, 0));
        jLabel13.setText("TOTAL:");

        lbl_soma_retencao.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 14)); // NOI18N
        lbl_soma_retencao.setForeground(new java.awt.Color(255, 102, 0));
        lbl_soma_retencao.setText("somaRetençãoInss");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lbl_custo_servicos)
                    .addComponent(jLabel9)
                    .addComponent(lbl_inss_retido)
                    .addComponent(lbl_inss_patronal)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_soma_retencao, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_custo_servicos)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_inss_retido)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_inss_patronal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_soma_retencao))
                .addContainerGap())
        );

        btnSalvarRegistro.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        btnSalvarRegistro.setForeground(new java.awt.Color(255, 102, 0));
        btnSalvarRegistro.setText("Salvar lançamento");
        btnSalvarRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarRegistroActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 102, 0));
        jButton2.setText("Limpar campos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_cad_nova_lotacao.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        btn_cad_nova_lotacao.setForeground(new java.awt.Color(255, 102, 0));
        btn_cad_nova_lotacao.setText("Cad. lotação");
        btn_cad_nova_lotacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cad_nova_lotacaoActionPerformed(evt);
            }
        });

        btn_salvar_alteracao.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        btn_salvar_alteracao.setForeground(new java.awt.Color(255, 102, 0));
        btn_salvar_alteracao.setText("Salvar alteração");
        btn_salvar_alteracao.setEnabled(false);
        btn_salvar_alteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_alteracaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvarRegistro)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cad_nova_lotacao))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salvar_alteracao))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalvarRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_cad_nova_lotacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_salvar_alteracao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarRegistroActionPerformed
        // O metodo salvarAtualizarDados() chama outro metodo que ira verificar se todos os campos foram preenchidos
        // este também é responsavel em capturar os dados dos campos, caso estejam preenchidos, logo retorna uma Lista de objetos
        // que será utilizada, tanto para salvar novo registro, como para Atualizar um registro
        List<Object> dados = salvarAtualizarDados();

        if (!dados.isEmpty()) {

            // CONFIRMAÇÃO DE INCLUSÃO
            int ret = JOptionPane.showConfirmDialog(this, "Confirma a inclusão deste serviço?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (ret == 0) {

                // realizando cast dos objetos contidos na lista
                // para passarmos como parametro as instancias dos objetos
                Pessoas_Model prestador = (Pessoas_Model) dados.get(0);
                Servicos_Model servicos = (Servicos_Model) dados.get(1);
                Imposto_Model impostos = (Imposto_Model) dados.get(2);

                // chamando metodo responsavel em realizar a gravação na base de dados
                // como retorno recebemos o ID gerado para o lançamento
                // caso retorno um valor diferente de zero iremos exiber a mensagem
                Servicos_Controller control = new Servicos_Controller();
                int idRetorno = control.gravar_novo_servico(servicos, impostos, prestador);

                // msg ao usuario - gravação com sucesso
                if (idRetorno != 0) {
                    JOptionPane.showMessageDialog(this, "Prestação de serviços salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        }

    }//GEN-LAST:event_btnSalvarRegistroActionPerformed

    private void campoValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoValorKeyPressed
        // ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            List<Component[]> comp = new ArrayList<>();
            Component campos[] = jPanel2.getComponents();
            comp.add(campos);

            // verifica o campo esta prenchido
            int vazio = verificarCamposPreenchidos(comp);

            if (vazio != 0) {
                JOptionPane.showMessageDialog(this, "Verifique se todos os campos estão preenchidos", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

                // Faz o preenchiimento, pois os campos estarão sinalizados com bordas vermelhas
                Servicos_Model servicos = new Servicos_Model();
                servicos.setSal_base(Double.parseDouble(campoValor.getText().replace(",", ".")));
                preencherCampos(servicos);
            } else {

                Servicos_Model servicos = new Servicos_Model();
                servicos.setSal_base(Double.parseDouble(campoValor.getText().replace(",", ".")));
                preencherCampos(servicos);
            }
        }
    }//GEN-LAST:event_campoValorKeyPressed

    private void btnBuscarLotacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLotacaoActionPerformed
        // TODO add your handling code here:
        Form_buscar_lotacao buscar = new Form_buscar_lotacao(null, true);
        buscar.setVisible(true);
        if (buscar.lotacaoSelecionada.getCodigo() != 0) {
            campoCodLotacao.setText(String.valueOf(buscar.lotacaoSelecionada.getCodigo()));
            campoDescricaoLotacao.setText(buscar.lotacaoSelecionada.getDescricao());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma secretaria foi selecionada, por favor verifique!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarLotacaoActionPerformed

    private void campoNomePrestadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNomePrestadorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            campoPisPasepPrestador.requestFocus();
        }
    }//GEN-LAST:event_campoNomePrestadorKeyPressed

    private void campoPisPasepPrestadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoPisPasepPrestadorKeyPressed
        // TODO add your handling code here:    
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            campoEmpenho.requestFocus();
        }
    }//GEN-LAST:event_campoPisPasepPrestadorKeyPressed

    private void campoEmpenhoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoEmpenhoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            campoFonteEmpenho.requestFocus();
        }
    }//GEN-LAST:event_campoEmpenhoKeyPressed

    private void campoFonteEmpenhoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoFonteEmpenhoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            btnBuscarLotacao.requestFocus();
        }
    }//GEN-LAST:event_campoFonteEmpenhoKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        campoCodLotacao.setText(null);
        campoDescricaoLotacao.setText(null);
        campoEmpenho.setText(null);
        campoFonteEmpenho.setText(null);
        campoValor.setText(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBuscarLotacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBuscarLotacaoKeyPressed
        // ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            Form_buscar_lotacao buscar = new Form_buscar_lotacao(null, true);
            buscar.setVisible(true);

            if (buscar.lotacaoSelecionada.getCodigo() != 0) {
                campoCodLotacao.setText(String.valueOf(buscar.lotacaoSelecionada.getCodigo()));
                campoDescricaoLotacao.setText(buscar.lotacaoSelecionada.getDescricao());
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma secretaria foi selecionada, por favor verifique!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBuscarLotacaoKeyPressed

    private void btn_cad_nova_lotacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_nova_lotacaoActionPerformed
        // Tela para cadastro de nova lotação
        Form_configuracao newLotacao = new Form_configuracao(null, true, 1);
        newLotacao.setVisible(true);
    }//GEN-LAST:event_btn_cad_nova_lotacaoActionPerformed

    private void btn_salvar_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_alteracaoActionPerformed
        List<Object> dados = salvarAtualizarDados();

        if (dados.size() != 0) {

            // CONFIRMAÇÃO DE INCLUSÃO
            int ret = JOptionPane.showConfirmDialog(this, "Confirma a alteracao deste serviço?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (ret == 0) {

                // realizando cast dos objetos contidos na lista
                // para passarmos como parametro as instancias dos objetos
                Pessoas_Model prestador = (Pessoas_Model) dados.get(0);
                Servicos_Model servicos = (Servicos_Model) dados.get(1);
                Imposto_Model impostos = (Imposto_Model) dados.get(2);

                // chamando metodo responsavel em realizar a atualização na base de dados
                Servicos_Controller control = new Servicos_Controller();
                control.atualizarLancamentoServicos(this.id_lancamento_servico, servicos, impostos, prestador);

            }
        }

    }//GEN-LAST:event_btn_salvar_alteracaoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Form_consultar_prestador buscar = new Form_consultar_prestador(null, true);
        buscar.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Lanc_Novo_Servico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Lanc_Novo_Servico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Lanc_Novo_Servico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Lanc_Novo_Servico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Form_Lanc_Novo_Servico dialog = new Form_Lanc_Novo_Servico(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarLotacao;
    private javax.swing.JButton btnSalvarRegistro;
    private javax.swing.JButton btn_cad_nova_lotacao;
    private javax.swing.JButton btn_salvar_alteracao;
    private javax.swing.JTextField campoCodLotacao;
    private javax.swing.JFormattedTextField campoCompetencia;
    private javax.swing.JTextField campoDescricaoLotacao;
    private javax.swing.JTextField campoEmpenho;
    private javax.swing.JTextField campoFonteEmpenho;
    private javax.swing.JTextField campoNomePrestador;
    private javax.swing.JTextField campoPisPasepPrestador;
    private javax.swing.JTextField campoValor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_custo_servicos;
    private javax.swing.JLabel lbl_inss_patronal;
    private javax.swing.JLabel lbl_inss_retido;
    private javax.swing.JLabel lbl_soma_retencao;
    // End of variables declaration//GEN-END:variables
}
