package data;

public class ItemEncomenda {
    private Produto p;
    private int quantidadeItem;
    private double precoMomento;

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
    
    public double calcularSubtotal(){
        return this.quantidadeItem * this.precoMomento;
    }
}
