/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.sabor.view;

import br.com.sabor.controller.Navegador;
import br.com.sabor.dao.EncomendaDAO;
import br.com.sabor.model.Encomenda;
import br.com.sabor.model.ItemEncomenda;
import java.time.format.DateTimeFormatter;
import java.util.List;
import br.com.sabor.view.CadastrarVenda;
import br.com.sabor.view.EditarVenda;
import javax.swing.JOptionPane;

/**
 *
 * @author dutra
 */
public class TelaEncomendas extends javax.swing.JFrame {

    /**
     * Creates new form Encomendas
     */
    private br.com.sabor.model.Encomenda encomenda;

    public TelaEncomendas(br.com.sabor.model.Encomenda enco) {
        initComponents();
        atualizarListaCards();
        painelLista.setLayout(new javax.swing.BoxLayout(painelLista, javax.swing.BoxLayout.Y_AXIS));
        painelLista.revalidate();
        painelLista.repaint();

    }

    private void gerarComponenteCard(String nomeCliente, String pendencia, String dataEntrega, String tipoSaida, double valorFinal, String situacao, br.com.sabor.model.Encomenda enco) {
        // criação do Card
        javax.swing.JPanel containerCard = new javax.swing.JPanel();
        containerCard.setBackground(new java.awt.Color(255, 249, 230));
        containerCard.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 153, 0), 2, true));

        containerCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encomenda = enco; // sua variável global
                for (java.awt.Component c : painelLista.getComponents()) {
                    if (c instanceof javax.swing.JPanel) {
                        c.setBackground(new java.awt.Color(255, 249, 230));
                    }
                }
                containerCard.setBackground(new java.awt.Color(255, 215, 120));
            }
        });

        containerCard.setPreferredSize(new java.awt.Dimension(270, 150));
        containerCard.setMinimumSize(new java.awt.Dimension(270, 150));
        containerCard.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 150));
        containerCard.setLayout(null);

        javax.swing.JLabel txtCliente = new javax.swing.JLabel("Cliente: " + nomeCliente);
        txtCliente.setFont(new java.awt.Font("Segoe UI", 1, 14));
        txtCliente.setBounds(10, 5, 260, 25);
        containerCard.add(txtCliente);

        javax.swing.JSeparator linhaDivisora = new javax.swing.JSeparator();
        linhaDivisora.setBounds(5, 32, 270, 2);
        containerCard.add(linhaDivisora);

        containerCard.add(montarTextoLabel("A pagar: R$ " + pendencia, 10, 40));
        containerCard.add(montarTextoLabel("Data: " + dataEntrega, 10, 65));
        containerCard.add(montarTextoLabel("Saída: " + tipoSaida, 10, 90));

        javax.swing.JLabel txtStatus = new javax.swing.JLabel(situacao.toUpperCase());
        txtStatus.setFont(new java.awt.Font("Segoe UI", 1, 12));
        txtStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtStatus.setBounds(150, 40, 120, 20);
        txtStatus.setForeground(situacao.equalsIgnoreCase("Concluído") ? new java.awt.Color(0, 153, 51) : java.awt.Color.RED);
        containerCard.add(txtStatus);

        javax.swing.JPanel blocoPreco = new javax.swing.JPanel();
        blocoPreco.setBackground(java.awt.Color.WHITE);
        blocoPreco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 153, 0), 1));
        blocoPreco.setBounds(150, 60, 120, 65);
        blocoPreco.setLayout(new java.awt.GridLayout(2, 1));

        javax.swing.JLabel tituloValor = new javax.swing.JLabel("Valor total:", javax.swing.SwingConstants.CENTER);
        javax.swing.JLabel valorExibido = new javax.swing.JLabel(String.format("R$ %.2f", valorFinal), javax.swing.SwingConstants.CENTER);
        valorExibido.setFont(new java.awt.Font("Segoe UI", 1, 14));
        valorExibido.setForeground(new java.awt.Color(204, 153, 0));

        blocoPreco.add(tituloValor);
        blocoPreco.add(valorExibido);
        containerCard.add(blocoPreco);

        painelLista.add(containerCard);
        painelLista.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
    }

    // MÉTODO AUXILIAR PARA CRIAR AS LABELS MENORES (O que estava faltando)
    private javax.swing.JLabel montarTextoLabel(String texto, int x, int y) {
        javax.swing.JLabel label = new javax.swing.JLabel(texto);
        label.setBounds(x, y, 130, 20);
        label.setFont(new java.awt.Font("Segoe UI", 0, 13));
        return label;
    }

    public void atualizarListaCards() {
        painelLista.removeAll();
        painelLista.setLayout(new javax.swing.BoxLayout(painelLista, javax.swing.BoxLayout.Y_AXIS));

        java.util.List<br.com.sabor.model.Encomenda> encomendas = new br.com.sabor.dao.EncomendaDAO().listarTodos();
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (br.com.sabor.model.Encomenda e : encomendas) {
            double total = e.getValorTotalPedido().doubleValue();
            double entrada = e.getValorEntrada();
            String pendenciaStr = String.format("%.2f", (total - entrada));
            String dataStr = e.getDataEntrega().format(fmt);
            String tipoSaida = e.isIsRetirada() ? "RETIRADA" : "ENTREGA";
            String nome = (e.getCliente() != null) ? e.getCliente().getNomeCliente() : e.getDestinatario();

            gerarComponenteCard(nome, pendenciaStr, dataStr, tipoSaida, total, e.getStatus(), e);
        }
        painelLista.revalidate();
        painelLista.repaint();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            // Limpa e recarrega do banco assim que a tela de cadastro fecha
            painelLista.removeAll();
            atualizarListaCards();
            painelLista.revalidate();
            painelLista.repaint();
        }
    }

    private void exibirEncomendasFiltradas(String status) {
        painelLista.removeAll();
        br.com.sabor.dao.EncomendaDAO dao = new br.com.sabor.dao.EncomendaDAO();
        java.util.List<br.com.sabor.model.Encomenda> lista = dao.listarPorStatus(status);
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (br.com.sabor.model.Encomenda e : lista) {
            double total = e.getValorTotalPedido().doubleValue();
            double entrada = e.getValorEntrada();
            String pendenciaStr = String.format("%.2f", (total - entrada));
            String dataStr = e.getDataEntrega().format(fmt);
            String tipoSaida = e.isIsRetirada() ? "RETIRADA" : "ENTREGA";
            String nome = (e.getCliente() != null) ? e.getCliente().getNomeCliente() : e.getDestinatario();

            // Chama o seu método que desenha o card
            gerarComponenteCard(nome, pendenciaStr, dataStr, tipoSaida, total, e.getStatus(), e);
        }

        painelLista.revalidate();
        painelLista.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnTelaInicialTI = new javax.swing.JButton();
        btnEncomendaE = new javax.swing.JButton();
        btnEstoqueE = new javax.swing.JButton();
        btnClientesE = new javax.swing.JButton();
        btnRelatorioE = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarVenda = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        painelLista = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnProntos = new javax.swing.JButton();
        btnProducao = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        btnNovaVenda = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(197, 107, 107));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 160, 93), 2));
        jPanel1.setPreferredSize(new java.awt.Dimension(756, 508));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\dutra\\Documents\\PI\\Sabor-em-Ordem\\src\\main\\java\\br\\com\\sabor\\view\\logo3.png")); // NOI18N
        jLabel2.setText("jLabel2");

        btnTelaInicialTI.setBackground(new java.awt.Color(255, 153, 153));
        btnTelaInicialTI.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTelaInicialTI.setForeground(new java.awt.Color(0, 0, 0));
        btnTelaInicialTI.setText("Tela Inicial");
        btnTelaInicialTI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTelaInicialTIActionPerformed(evt);
            }
        });

        btnEncomendaE.setBackground(new java.awt.Color(191, 160, 93));
        btnEncomendaE.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEncomendaE.setForeground(new java.awt.Color(0, 0, 0));
        btnEncomendaE.setText("Encomendas");
        btnEncomendaE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncomendaEActionPerformed(evt);
            }
        });

        btnEstoqueE.setBackground(new java.awt.Color(255, 153, 153));
        btnEstoqueE.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEstoqueE.setForeground(new java.awt.Color(0, 0, 0));
        btnEstoqueE.setText("Estoque");
        btnEstoqueE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstoqueEActionPerformed(evt);
            }
        });

        btnClientesE.setBackground(new java.awt.Color(255, 153, 153));
        btnClientesE.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnClientesE.setForeground(new java.awt.Color(0, 0, 0));
        btnClientesE.setText("Clientes");
        btnClientesE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesEActionPerformed(evt);
            }
        });

        btnRelatorioE.setBackground(new java.awt.Color(255, 153, 153));
        btnRelatorioE.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRelatorioE.setForeground(new java.awt.Color(0, 0, 0));
        btnRelatorioE.setText("Relatórios");
        btnRelatorioE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(54, 54, 54)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(btnTelaInicialTI, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEncomendaE, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRelatorioE, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClientesE, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEstoqueE, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(btnTelaInicialTI)
                .addGap(18, 18, 18)
                .addComponent(btnEstoqueE)
                .addGap(18, 18, 18)
                .addComponent(btnEncomendaE)
                .addGap(18, 18, 18)
                .addComponent(btnClientesE)
                .addGap(18, 18, 18)
                .addComponent(btnRelatorioE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 226, 510));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Registro de Enocmendas");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 18, -1, -1));

        txtBuscarVenda.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscarVenda.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscarVenda.setText("Buscar Venda");
        txtBuscarVenda.setOpaque(true);
        jPanel2.add(txtBuscarVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 74, 223, 34));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        painelLista.setBackground(new java.awt.Color(204, 204, 204));
        painelLista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 160, 93)));
        painelLista.setLayout(new javax.swing.BoxLayout(painelLista, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(painelLista);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 114, 300, 390));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        btnProntos.setBackground(new java.awt.Color(255, 255, 255));
        btnProntos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnProntos.setForeground(new java.awt.Color(0, 0, 0));
        btnProntos.setText("Prontos");
        btnProntos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProntosActionPerformed(evt);
            }
        });

        btnProducao.setBackground(new java.awt.Color(255, 255, 255));
        btnProducao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnProducao.setForeground(new java.awt.Color(0, 0, 0));
        btnProducao.setText("Pendente");
        btnProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProducaoActionPerformed(evt);
            }
        });

        btnTodos.setBackground(new java.awt.Color(197, 107, 107));
        btnTodos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTodos.setForeground(new java.awt.Color(0, 0, 0));
        btnTodos.setText("Todos");
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(btnTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProducao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProntos)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProntos)
                    .addComponent(btnProducao)
                    .addComponent(btnTodos))
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 62, 520, -1));

        btnNovaVenda.setBackground(new java.awt.Color(255, 153, 153));
        btnNovaVenda.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnNovaVenda.setForeground(new java.awt.Color(0, 0, 0));
        btnNovaVenda.setText("Nova Venda");
        btnNovaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaVendaActionPerformed(evt);
            }
        });
        jPanel2.add(btnNovaVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, -1, -1));

        jButton5.setBackground(new java.awt.Color(255, 153, 153));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Editar Venda");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 380, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Concluir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTelaInicialTIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTelaInicialTIActionPerformed
        Navegador.navegar(this, new TelaInicial());
    }//GEN-LAST:event_btnTelaInicialTIActionPerformed

    private void btnEncomendaEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncomendaEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEncomendaEActionPerformed

    private void btnEstoqueEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstoqueEActionPerformed
        Navegador.navegar(this, new TelaEstoque());
    }//GEN-LAST:event_btnEstoqueEActionPerformed

    private void btnClientesEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesEActionPerformed
        Navegador.navegar(this, new TelaClientes());
    }//GEN-LAST:event_btnClientesEActionPerformed

    private void btnRelatorioEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioEActionPerformed
        Navegador.navegar(this, new Relatorio());
    }//GEN-LAST:event_btnRelatorioEActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        atualizarListaCards();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void btnProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProducaoActionPerformed
        exibirEncomendasFiltradas("Pendente");
    }//GEN-LAST:event_btnProducaoActionPerformed

    private void btnNovaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVendaActionPerformed
        CadastrarVenda dialog = new CadastrarVenda(new javax.swing.JFrame(), true, null);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNovaVendaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (this.encomenda != null) {
            EditarVenda ev = new EditarVenda();

            // 1. Preenche os campos
            ev.preencherCampos((br.com.sabor.model.Encomenda) this.encomenda);

            // 2. Adiciona o Listener ANTES de mostrar a tela
            ev.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    atualizarListaCards();
                }
            });

            // 3. Só agora mostra a tela
            ev.setVisible(true);

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, clique em um card amarelo antes de editar.");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (encomenda == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione uma encomenda na lista primeiro!");
            return;
        }

        try {
            br.com.sabor.dao.EncomendaDAO dao = new br.com.sabor.dao.EncomendaDAO();

            // Atualiza o status do objeto selecionado
            encomenda.setStatus("Concluído");

            // Salva a alteração no banco de dados
            dao.salvarAlteracoes(encomenda);

            javax.swing.JOptionPane.showMessageDialog(this, "Encomenda marcada como concluída!");

            // Limpa a seleção e recarrega a lista para mostrar a mudança
            encomenda = null;
            atualizarListaCards();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnProntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProntosActionPerformed
        exibirEncomendasFiltradas("Concluído");
    }//GEN-LAST:event_btnProntosActionPerformed

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
            java.util.logging.Logger.getLogger(TelaEncomendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEncomendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEncomendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEncomendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEncomendas(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesE;
    private javax.swing.JButton btnEncomendaE;
    private javax.swing.JButton btnEstoqueE;
    private javax.swing.JButton btnNovaVenda;
    private javax.swing.JButton btnProducao;
    private javax.swing.JButton btnProntos;
    private javax.swing.JButton btnRelatorioE;
    private javax.swing.JButton btnTelaInicialTI;
    private javax.swing.JButton btnTodos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel painelLista;
    private javax.swing.JTextField txtBuscarVenda;
    // End of variables declaration//GEN-END:variables
}
