package br.com.sabor.model;

import javax.persistence.*;

@Entity
@Table(name = "itens_venda")
public class ItemEncomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto p;

    @Column(name = "quantidade") 
    private int quantidadeItem;

    @Column(name = "preco_momento") 
    private double precoMomento;

    public ItemEncomenda() {

    }

    public ItemEncomenda(Produto p, int quantidadeItem, double precoMomento) {
        this.p = p;
        this.quantidadeItem = quantidadeItem;
        this.precoMomento = precoMomento;
    }

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;
    }

    public int getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(int quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public double getPrecoMomento() {
        return precoMomento;
    }

    public void setPrecoMomento(double precoMomento) {
        this.precoMomento = precoMomento;
    }

    public double calcularSubtotal() {
        return this.quantidadeItem * this.p.getValorUni();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

}