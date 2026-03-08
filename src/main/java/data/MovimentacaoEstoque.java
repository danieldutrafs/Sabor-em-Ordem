package data;

public class MovimentacaoEstoque {
    private int id;
    private Produto p;
    private int quantidade;
    private String tipo;

    public MovimentacaoEstoque(int id, Produto p, int quantidade, String tipo) {
        this.id = id;
        this.p = p;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
