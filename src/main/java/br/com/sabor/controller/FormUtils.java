/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sabor.controller;

/**
 *
 * @author dutra
 */
public class FormUtils {
    
    //método para limpar campos
    public static void limparCampos(javax.swing.JTextField... campos) {
        for (javax.swing.JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    //método para verificar se algum campo esta vazio
    public static boolean camposVazios(javax.swing.JTextField... campos) {
        for (javax.swing.JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                campo.requestFocus(); 
                return true;
            }
        }
        return false; 
    }
}
