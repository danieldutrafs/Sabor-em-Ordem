package data;

public class Produto {
    private int id;
    private String nomeProduto;
    private int quantidade;
    private String categoria;
    private double valorUni;
    private static final int LIMITE_MINIMO = 200; // Regra de negócio: todo produto deve ter um estoque mínimo de 200 unidades
    
    public Produto(int id, String nomeProduto, int quantidade, String categoria, double valorUni) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.valorUni = valorUni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValorUni() {
        return valorUni;
    }

    public void setValorUni(double valorUni) {
        this.valorUni = valorUni;
    }
    
    public void adicionarEstoque(int quantidade){
        if(quantidade > 0){
            this.quantidade = this.quantidade + quantidade;
        }
    }
    
    public void removerEstque(int quanitdade){
       this.quantidade = this.quantidade - quantidade;
       if(this.quantidade<0){
           System.out.println("Atenção: Produto com estoque negativo");
       }
    }
    
    public boolean atingiuLimite(){
        return this.quantidade < LIMITE_MINIMO;
    }
}
