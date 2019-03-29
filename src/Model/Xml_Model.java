/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author André
 */
public class Xml_Model {

    /**
     * Metodo construtor com parametros para verificar a existencia do arquivo
     * XML
     *
     * @param caminhoXml - nome/caminho do arquivo XML
     * @param anoReferencia
     * @param mesReferencia
     */
    public Xml_Model(String caminhoXml, String anoReferencia, String mesReferencia) {
        this.verificarExistenciaXML(caminhoXml, anoReferencia, mesReferencia);
    }

    /**
     * Metodo construtor vazio
     */
    public Xml_Model() {

    }

    /**
     * Metodo que verifica a existencia do arquivo XML dentor do pacote do
     * sistema
     *
     * @param caminhoXml - nome/caminho do arquivo xml a ser verificado
     */
    private void verificarExistenciaXML(String caminhoXml, String anoReferencia, String mesReferencia) {

        File arq = new File(caminhoXml);
        if (arq.exists()) {
            System.out.println("Arquivo xml localizado..." + caminhoXml);
            carregarXml(caminhoXml, anoReferencia, mesReferencia);
        } else {
            System.out.println("Arquivo não foi encontrato");
        }

    }

    /**
     * Carrega as informações contidas dentro do XML informado, recuperando
     * valores referente ao anoReferencia e mesReferencia fornecidos como
     * parametros
     *
     * @param caminhoXml - nome/caminho xml para leitura
     * @param anoReferencia - ano base das informações
     * @param mesReferencia - mes de referencia
     */
    private void carregarXml(String caminhoXml, String anoReferencia, String mesReferencia) {

        try {

            // intanciando objeto necessarios a leitura do arquivo XML
            SAXReader reader = new SAXReader();
            Document doc = reader.read(caminhoXml);
            // recuperando o elemento PAI do arquivo XML 
            // <base></base>
            Element elementoPai = doc.getRootElement();

            // aqui fazermos a recuperação de todos os elementos filhos pertecendes ao elemento PAI
            // para percorrer um a um, e capturar os dados necessários
            // <anos></anos>
            for (Iterator iterator = elementoPai.elementIterator(); iterator.hasNext();) {
                Element e1 = (Element) iterator.next();

                // verificamos se o atributo ano é igual ao fornecido ao parametro
                // <anos ano="2018">
                if (e1.attributeValue("ano").equals("2018")) {

                    // interação sobre o elemento filho de <anos>, neste caso é um mesReferencia
                    // <janeiro><fevereiro>...
                    for (Iterator iterator1 = e1.elementIterator(); iterator1.hasNext();) {
                        Element e2 = (Element) iterator1.next();

                        // recuperação dos valores de patronal e maximo desconto contidos no XML
                        // <patronal> e <maximoDesconto>
                        // atribuimos a variaveis para retorno
                        if (e2.getName().equals(mesReferencia)) {
                            double patronal = Double.parseDouble(e2.element("patronal").getText());
                            double maximoDesconto = Double.parseDouble(e2.element("maximoDesconto").getText());

                            /*
                            System.out.println(e2.element("patronal").getText());
                            System.out.println(e2.element("maximoDesconto").getText());
                             */
                        }

                    }
                }
            }

        } catch (DocumentException ex) {
            Logger.getLogger(Xml_Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
