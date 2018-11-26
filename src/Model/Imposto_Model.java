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
public class Imposto_Model {

    private double inss_retido, inss_patronal;
    
//    private final int ALIQUOTA_1;
//    private final int ALIQUOTA_2;
//    private final int ALIQUOTA_3;
//
//    public Imposto_Model(int ALIQUOTA_1, int ALIQUOTA_2, int ALIQUOTA_3) {
//        this.ALIQUOTA_1 = ALIQUOTA_1;
//        this.ALIQUOTA_2 = ALIQUOTA_2;
//        this.ALIQUOTA_3 = ALIQUOTA_3;
//    }

    public Imposto_Model() {


    }

    public double getInss_retido() {
        return inss_retido;
    }

    public void setInss_retido(double inss_retido) {
        this.inss_retido = inss_retido;
    }

    public double getInss_patronal() {
        return inss_patronal;
    }

    public void setInss_patronal(double inss_patronal) {
        this.inss_patronal = inss_patronal;
    }

}
