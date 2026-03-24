package br.com.sabor.model;

import java.math.BigDecimal;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "encomenda")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id") // Relacionamento, não @Column!
    private Clientes cliente;
    @OneToMany(mappedBy = "encomenda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
private List<ItemEncomenda> listaItens = new ArrayList<>();
    @Column(name = "data_entrega")
    private LocalDate dataEntrega;
    @Column(name = "status_encomenda")
    private String status;
    @Column(name = "valor_entrada")
    private double valorEntrada;

    private double frete;
    private String destinatario;
    private String endereco;

    @Column(name = "is_retirada")
    private boolean isRetirada;
    @Column(name = "forma_pagamento")
    private String formaPagamento;
    
    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotalPedido;

    public Encomenda() {

    }

    public Encomenda(Long id, Clientes cliente, List<ItemEncomenda> listaItens, LocalDate dataEntrega, String status, double valorEntrada, double frete, String destinatario, String endereco, boolean isRetirada, String formaPagamento, BigDecimal valorTotalPedido) {
        this.id = id;
        this.cliente = cliente;
        this.listaItens = listaItens;
        this.dataEntrega = dataEntrega;
        this.status = status;
        this.valorEntrada = valorEntrada;
        this.frete = frete;
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.isRetirada = isRetirada;
        this.formaPagamento = formaPagamento;
        this.valorTotalPedido = valorTotalPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public List<ItemEncomenda> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<ItemEncomenda> listaItens) {
        this.listaItens = listaItens;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isIsRetirada() {
        return isRetirada;
    }

    public void setIsRetirada(boolean isRetirada) {
        this.isRetirada = isRetirada;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public BigDecimal getValorTotalPedido() {
        return valorTotalPedido;
    }

    public void setValorTotalPedido(BigDecimal valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }

    public double calcularTotalItens() {
        double soma = 0;
        if (this.listaItens != null && !this.listaItens.isEmpty()) {
            for (ItemEncomenda item : listaItens) {
                soma += item.calcularSubtotal();
            }
        }
        return soma;
    }

    public double getValorTotal() {
        return calcularTotalItens() + this.frete;
    }

    public double getValorAPagar() {
        return getValorTotal() - this.valorEntrada;
    }

    public String gerarResumo() {
        String entregaStr = isRetirada ? "Retirada" : "Entrega em: " + this.endereco;
        return "Pedido #" + id + " | " + destinatario + " | " + entregaStr + " | Status: " + status;
    }

}