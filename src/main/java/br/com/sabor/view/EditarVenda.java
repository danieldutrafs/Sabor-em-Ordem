/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.sabor.view;

import br.com.sabor.controller.FormUtils;
import br.com.sabor.model.Encomenda;
import br.com.sabor.model.ItemEncomenda;
import br.com.sabor.model.Produto;
import br.com.sabor.dao.ProdutoDAO;
import br.com.sabor.dao.EncomendaDAO;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author dutra
 */
public class EditarVenda extends javax.swing.JFrame {

    private Encomenda encomendaAtual;

    public EditarVenda() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void preencherCampos(br.com.sabor.model.Encomenda enco) {
        // IMPORTANTE: Vincula a encomenda recebida à variável da classe
        // para que o botão SALVAR saiba quem editar.
        this.encomendaAtual = enco;

        // Carrega a lista de produtos no ComboBox
        produtosCombo();

        // 1. Preenche os campos de texto básicos
        txtDestinatario.setText(enco.getDestinatario());
        txtEndereco.setText(enco.getEndereco() != null ? enco.getEndereco() : "");

        // 2. Preenche Valores (Garante que não dê erro se o valor for nulo)
        double entradaValue = enco.getValorEntrada();
        double freteValue = enco.getFrete();

        // Formata para a tela com vírgula para o padrão brasileiro
        txtEntrada.setText(String.format("%.2f", entradaValue).replace(".", ","));
        txtFrete.setText(String.format("%.2f", freteValue).replace(".", ","));

        // 3. Preenche a Data
        if (enco.getDataEntrega() != null) {
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataEntrega.setText(enco.getDataEntrega().format(fmt));
        }

        // 4. Componentes de Seleção
        cbxPagamento.setSelectedItem(enco.getFormaPagamento());
        cbIsRetirada.setSelected(enco.isIsRetirada());

        // 5. Limpa e Preenche a Tabela de Produtos
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
        modelo.setNumRows(0);

        if (enco.getListaItens() != null) {
            for (br.com.sabor.model.ItemEncomenda item : enco.getListaItens()) {
                // Multiplicação Real: Preço x Quantidade
                double preco = item.getPrecoMomento();
                int qtd = item.getQuantidadeItem();
                double subtotal = preco * qtd;

                modelo.addRow(new Object[]{
                    item.getP().getNomeProduto(),
                    qtd,
                    String.format("%.2f", subtotal).replace(".", ",")
                });
            }
        }

        // 6. Atualiza a Label de Total com o valor que já veio do banco
        if (enco.getValorTotalPedido() != null) {
            lblTotal.setText(String.format("Total: R$ %.2f", enco.getValorTotalPedido()).replace(".", ","));
        }

        // 7. SINCRONIA VISUAL DA RETIRADA (O que faltava)
        // Isso garante que se for retirada, os campos fiquem bloqueados/cinzas ao abrir a tela
        boolean isRetirada = cbIsRetirada.isSelected();
        txtEndereco.setEditable(!isRetirada);
        txtFrete.setEditable(!isRetirada);
        txtEndereco.setEnabled(!isRetirada);
        txtFrete.setEnabled(!isRetirada);

        // 8. Força o recálculo final para garantir sincronia total
        calcularTotalGeral();
    }

    private void produtosCombo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Selecione...");
        for (Produto p : new ProdutoDAO().listarTodos()) {
            modelo.addElement(p);
        }
        cbxProduto.setModel(modelo);
    }

    private void calcularTotalGeral() {
        double somaProdutos = 0;

        for (int i = 0; i < tblProduto.getRowCount(); i++) {
            Object valorObjeto = tblProduto.getValueAt(i, 2); // Pega a coluna do Subtotal
            if (valorObjeto != null) {
                try {
                    // AQUI ENTRA A LINHA: 
                    // Ela limpa o "R$", espaços e troca a vírgula por ponto
                    String valorTexto = valorObjeto.toString()
                            .replace("R$", "")
                            .replace(" ", "")
                            .replace(",", ".");

                    // Agora o Double.parseDouble consegue entender que 15.00 é quinze reais
                    somaProdutos += Double.parseDouble(valorTexto);

                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter valor na linha " + i);
                }
            }
        }

        // Soma o frete no final
        double frete = 0;
        try {
            frete = Double.parseDouble(txtFrete.getText().replace(",", "."));
        } catch (Exception e) {
        }

        double totalFinal = somaProdutos + frete;
        lblTotal.setText(String.format("Total: R$ %.2f", totalFinal));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtData = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        cbIsRetirada = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEntrada = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtFrete = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxPagamento = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtDestinatario = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        txtDataEntrega = new javax.swing.JTextField();
        cbxProduto = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtData.setBackground(new java.awt.Color(204, 204, 204));
        txtData.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(197, 107, 107));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detalhes da Venda");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(187, 187, 187))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Adicioanr Itens:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Produto:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Quantidade:");

        btnAdicionar.setBackground(new java.awt.Color(197, 107, 107));
        btnAdicionar.setForeground(new java.awt.Color(0, 0, 0));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemover.setBackground(new java.awt.Color(197, 107, 107));
        btnRemover.setForeground(new java.awt.Color(0, 0, 0));
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Produto", "Quantidade", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tblProduto);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Detalhes da encomenda:");

        cbIsRetirada.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbIsRetirada.setForeground(new java.awt.Color(0, 0, 0));
        cbIsRetirada.setText("Retirada");
        cbIsRetirada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIsRetiradaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Endereço:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Entrada:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Data:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Frete:");

        txtFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFreteActionPerformed(evt);
            }
        });
        txtFrete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFreteKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Froma de pagamento:");

        cbxPagamento.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Pix", "Dinheiro", "Cartão", "Permulta" }));
        cbxPagamento.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Destinatário:");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 160, 93), 2));

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 0, 0));
        lblTotal.setText("Total: 00,00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal))
        );

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(197, 107, 107));
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(0, 0, 0));
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout txtDataLayout = new javax.swing.GroupLayout(txtData);
        txtData.setLayout(txtDataLayout);
        txtDataLayout.setHorizontalGroup(
            txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(txtDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtDataLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtDataLayout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(txtDataLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDestinatario))))
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addGroup(txtDataLayout.createSequentialGroup()
                                .addComponent(btnAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemover))
                            .addComponent(cbxProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addComponent(cbIsRetirada)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEndereco))
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFrete)))
                .addContainerGap())
        );
        txtDataLayout.setVerticalGroup(
            txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtDataLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txtDataLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionar)
                            .addComponent(btnRemover)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbIsRetirada)
                    .addComponent(jLabel9)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbxPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed

    }//GEN-LAST:event_btnRemoverActionPerformed

    private void cbIsRetiradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIsRetiradaActionPerformed
        boolean isRetirada = cbIsRetirada.isSelected();

// Bloqueia ou libera os campos conforme o clique
        txtEndereco.setEditable(!isRetirada);
        txtFrete.setEditable(!isRetirada);
        txtEndereco.setEnabled(!isRetirada);
        txtFrete.setEnabled(!isRetirada);

        if (isRetirada) {
            txtEndereco.setText("RETIRADA NO LOCAL");
            txtFrete.setText("0,00");
        } else {
            // Se desmarcar, limpa para o usuário digitar o novo endereço
            txtEndereco.setText("");
            txtFrete.setText("");
        }

// Chama o cálculo para atualizar o valor (sem o frete)
        calcularTotalGeral();
    }//GEN-LAST:event_cbIsRetiradaActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        //verificar se campo quantidade esta vazio
        if (FormUtils.camposVazios(txtQuantidade)) {
            JOptionPane.showMessageDialog(this, "Digite uma quantidade!");
            return;
        }

        //verificar se foi selecionado um produto
        if (cbxProduto.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        try {
            //pega o objeto produto 
            Produto p = (Produto) cbxProduto.getSelectedItem();
            int qtd = Integer.parseInt(txtQuantidade.getText().trim());

            // calcula o subtotal
            double subtotal = p.getValorUni() * qtd;

            //adiciona na tabela
            DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
            modelo.addRow(new Object[]{
                p.getNomeProduto(),
                qtd,
                subtotal
            });

            //atualiza o total geral e reseta os campos
            calcularTotalGeral();
            cbxProduto.setSelectedIndex(0);
            txtQuantidade.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser um número inteiro");
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtFreteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFreteKeyReleased
        calcularTotalGeral();
    }//GEN-LAST:event_txtFreteKeyReleased

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            // 1. VALIDAÇÃO DE CAMPOS BÁSICOS
            if (txtDestinatario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O Destinatário é obrigatório!");
                return;
            }
            if (tblProduto.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "A lista de produtos não pode estar vazia!");
                return;
            }

            // 2. ATUALIZANDO DADOS BÁSICOS
            encomendaAtual.setDestinatario(txtDestinatario.getText().trim());
            encomendaAtual.setFormaPagamento(cbxPagamento.getSelectedItem().toString());
            encomendaAtual.setIsRetirada(cbIsRetirada.isSelected());

            // 3. LÓGICA DE ENDEREÇO E FRETE
            double freteValue = 0.0;

            if (cbIsRetirada.isSelected()) {
                encomendaAtual.setEndereco("RETIRADA NO LOCAL");
                encomendaAtual.setFrete(0.0);
            } else {
                if (txtEndereco.getText().trim().isEmpty() || txtFrete.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe Endereço e Frete!");
                    return;
                }
                String fLimpo = txtFrete.getText().replaceAll("[^0-9,]", "").replace(",", ".");
                freteValue = Double.parseDouble(fLimpo.isEmpty() ? "0.0" : fLimpo);

                encomendaAtual.setEndereco(txtEndereco.getText().trim());
                encomendaAtual.setFrete(freteValue);
            }

// 4. ATUALIZANDO O VALOR TOTAL (Ajustado para não multiplicar)
// Usamos o replace que preserva o ponto se ele já existir (vencendo o erro das imagens)
            String valorLimpo = lblTotal.getText().replaceAll("[^0-9,.]", "");
            if (valorLimpo.contains(",") && valorLimpo.contains(".")) {
                valorLimpo = valorLimpo.replace(".", "").replace(",", ".");
            } else {
                valorLimpo = valorLimpo.replace(",", ".");
            }

            encomendaAtual.setValorTotalPedido(new java.math.BigDecimal(valorLimpo));

            // [NOVO] 4.5 SINCRONIZAR ITENS DA TABELA COM A LISTA DO OBJETO
            // Isso garante que o que você vê na tabela seja o que vai pro banco
            encomendaAtual.getListaItens().clear(); // Limpa a lista antiga na memória

            for (int i = 0; i < tblProduto.getRowCount(); i++) {
                // Aqui você deve pegar os dados da sua tabela e remontar os objetos ItemEncomenda
                // Exemplo (ajuste conforme suas colunas):
                // br.com.sabor.model.ItemEncomenda novoItem = new br.com.sabor.model.ItemEncomenda();
                // novoItem.setProduto(...);
                // novoItem.setQuantidade(...);
                // encomendaAtual.getListaItens().add(novoItem);
            }

            // 5. SALVANDO NO BANCO
            br.com.sabor.dao.EncomendaDAO dao = new br.com.sabor.dao.EncomendaDAO();

            // IMPORTANTE: Use 'merge' ou o seu método que trata o Cascade do Hibernate
            dao.salvarAlteracoes(encomendaAtual);

            JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!");
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro nos valores! Use apenas números e vírgula.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFreteActionPerformed

    }//GEN-LAST:event_txtFreteActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditarVenda.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarVenda.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarVenda.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarVenda.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox cbIsRetirada;
    private javax.swing.JComboBox<String> cbxPagamento;
    private javax.swing.JComboBox<String> cbxProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblProduto;
    private javax.swing.JPanel txtData;
    private javax.swing.JTextField txtDataEntrega;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextField txtFrete;
    private javax.swing.JTextField txtQuantidade;
    // End of variables declaration//GEN-END:variables
}
