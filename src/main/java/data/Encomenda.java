package data;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class Encomenda {
    private int id;
    private Clientes c;
    private List<ItemEncomenda> listaItens;
    private LocalDate dataEntrega;
    private String status;
    private double valorEntrada;
    private double frete;
    private String destinatario;
    private String endereco;
    private boolean isRetirada;
    private String formaPagamento;

    public Encomenda(int id, Clientes c, List<ItemEncomenda> listaItens, LocalDate dataEntrega, String status, double valorEntrada, double frete, String destinatario, String endereco, boolean isRetirada, String formaPagamento) {
        this.id = id;
        this.c = c;
        this.listaItens = listaItens;
        this.dataEntrega = dataEntrega;
        this.status = "Pendente";
        this.valorEntrada = valorEntrada;
        this.frete = frete;
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.isRetirada = isRetirada;
        this.formaPagamento = formaPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientes getC() {
        return c;
    }

    public void setC(Clientes c) {
        this.c = c;
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
    
    public double calcularTotalItens(){
        double soma = 0;
        for(ItemEncomenda item : listaItens){
            soma += item.calcularSubtotal();
        }
        return soma;
    }
    
    public double calcularValorFinal(){
        return calcularTotalItens() + this.frete;
    }
    
    public double calcularValorDevedor(){
        return calcularValorFinal() - this.valorEntrada;
    }
    
    public String gerarResumo() {
        String entregaStr = isRetirada ? "Retirada" : "Entrega em: " + this.endereco;
        return "Pedido #" + id + " | " + destinatario + " | " + entregaStr + " | Status: " + status;
    }
}
