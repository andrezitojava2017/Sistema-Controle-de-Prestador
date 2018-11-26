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
public class Servicos_Model {

    private double sal_base;
    private int id, empenho;
    private String competencia, lotação;
    private int fonte;

    public int getFonte() {
        return fonte;
    }

    public void setFonte(int fonte) {
        this.fonte = fonte;
    }

    public double getSal_base() {
        return sal_base;
    }

    public void setSal_base(double sal_base) {
        this.sal_base = sal_base;
    }

//    public Pessoas_Model getId_pessoa() {
//        return id_pessoa;
//    }
//
//    public void setId_pessoa(Pessoas_Model id_pessoa) {
//        this.id_pessoa = id_pessoa;
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpenho() {
        return empenho;
    }

    public void setEmpenho(int empenho) {
        this.empenho = empenho;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getLotação() {
        return lotação;
    }

    public void setLotação(String lotação) {
        this.lotação = lotação;
    }

   

    /**
     * Calcula os valores do INSS patronal e INSS retido do prestador
     *
     * @param custoServicos
     * @return double[]
     */
    public double[] calcularImpostos(double custoServicos) {
        double impostos[] = new double[2];

        double retido = custoServicos * AliquotaInss.ALIQUOTA_3.getValor();
        double patronal = custoServicos * AliquotaInss.PATRONAL.getValor();
        
        if(retido >= 621.03){
            retido = 621.03;
        }
        impostos[0] = retido;
        impostos[1] = patronal;

        return impostos;
    }

    // criar metodo que ira retornar objeto tipo Pessoas_Model, com as informações para preencimento
    // do formulario de lançamento de serviços
}
