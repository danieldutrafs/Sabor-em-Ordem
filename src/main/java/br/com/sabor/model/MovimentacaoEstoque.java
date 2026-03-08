package br.com.sabor.model;

import javax.persistence.*;

@Entity
@Table (name = "movimentacao_estoque")
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "produto_id") // Esta é a coluna na tabela de movimentação
    private Produto p;
    
    private int quantidade;
    private String tipo;

    public MovimentacaoEstoque(Long id, Produto p, int quantidade, String tipo) {
        this.id = id;
        this.p = p;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String gerarResumo() {
        return this.tipo + ": " + this.quantidade + " unidades de " + p.getNomeProduto();
    }
}
