/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Localizar_Arquivo_Prestador;
import java.util.List;

/**
 *
 * @author rh
 */
public class Localizar_Arquivo_Controller {
    
    /**
     * Faz a leitura do arquivo selecionado pelo usuario
     * @param caminho
     * @return List<String> Dados dos prestadores
     */
    public List<String> lerArquivoSelecionado(String caminho){
        Localizar_Arquivo_Prestador model = new Localizar_Arquivo_Prestador();
        List<String> listaPrestador = model.lerArquivoGfp(caminho);
        
        return listaPrestador;
    }
    
//    /**
//     * Chama JFiledialog para localizar o arquivo .RE dos prestadores de serviços
//     * @return String caminho do arquivo selecionado
//     */
//    public String localizarArquivoLayout(){
//        Localizar_Arquivo_Prestador model = new Localizar_Arquivo_Prestador();
//        String caminhoArquivo = model.localizarArquivoLayout();
//        
//        return caminhoArquivo;
//    }
}
