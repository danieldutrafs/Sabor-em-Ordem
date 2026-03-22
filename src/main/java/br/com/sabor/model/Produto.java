package br.com.sabor.model;

import javax.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_produto")
    private String nomeProduto;

    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "preco")
    private double valorUni;

    private static final int LIMITE_MINIMO = 200; // Regra de negócio: todo produto deve ter um estoque mínimo de 200 unidades

    public Produto(Long id, String nomeProduto, int quantidade, Categoria categoria, double valorUni) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.valorUni = valorUni;
    }

    public Produto() {
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUni() {
        return valorUni;
    }

    public void setValorUni(double valorUni) {
        this.valorUni = valorUni;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = this.quantidade + quantidade;
        }
    }

    public void removerEstoque(int quantidade) {
        this.quantidade = this.quantidade - quantidade;
    }

    public boolean atingiuLimite() {
        return this.quantidade < LIMITE_MINIMO && this.quantidade >= 0;
    }

    public boolean estaNegativo() {
        return this.quantidade < 0;
    }

    @Override
    public String toString() {
        return this.nomeProduto;
    }
}
