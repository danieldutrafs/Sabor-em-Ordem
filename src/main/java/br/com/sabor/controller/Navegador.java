package br.com.sabor.controller;

import javax.swing.JFrame;

public class Navegador {

    /**
     * @param telaAtual A tela que o usuário está agora (você passa 'this')
     * @param proximaTela A nova tela que você quer abrir
     */
    public static void navegar(JFrame telaAtual, JFrame proximaTela) {
        // 1. Esconde a tela atual
        if (telaAtual != null) {
            telaAtual.setVisible(false);
        }

        // 2. Exibe a próxima
        proximaTela.setVisible(true);
        proximaTela.setLocationRelativeTo(null); // Centraliza na tela
    }
}