/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rh
 */
public class Localizar_Arquivo_Prestador {



    /**
     * Recupera todas as linhas do arquivo selecionado
     *
     * @param caminho - Local do arquivo escolhido
     * @return
     */
    public List<String> lerArquivoGfp(String caminho) {

        List<String> conteudo = new ArrayList<>();
        try {
            Path arquivo = Paths.get(caminho);
            conteudo = Files.readAllLines(arquivo, StandardCharsets.ISO_8859_1);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura das linhas do aquivo selecionado \n" + ex);
        }
        return conteudo;
    }
}
