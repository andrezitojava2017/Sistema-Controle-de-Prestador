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
public enum AliquotaInss {

    ALIQUOTA_1(0.08), ALIQUOTA_2(0.09), ALIQUOTA_3(0.11), PATRONAL(0.20), LIMITE_DESCONTO(621.03);

    private final double valor;

    AliquotaInss(double valor) {
        this.valor = valor;
    }

    public double getValor(){
        return this.valor;
    }
}
