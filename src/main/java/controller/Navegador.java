package controller;

import java.util.Stack;
import javax.swing.JFrame;
import view.*;

public class Navegador {

    private static TelaInicial telaInicial;
    private static TelaEstoque telaEstoque;

    private static Stack<JFrame> historico = new Stack<>();

    private static void esconderAtual() {
        if (!historico.isEmpty()) {
            historico.peek().setVisible(false); // Esconde apenas a que está no topo agora
        }
    }

    public static void exibirTelaE() {
        esconderAtual();

        if (telaEstoque == null) {
            telaEstoque = new TelaEstoque();
        }

        telaEstoque.setVisible(true);
        historico.push(telaEstoque);

    }
    
    public static void exibirTelaI(){
        esconderAtual();
        if(telaInicial == null){
            telaInicial = new TelaInicial();
        }
        telaInicial.setVisible(true);
        historico.push(telaInicial);
    }
    
   

}
