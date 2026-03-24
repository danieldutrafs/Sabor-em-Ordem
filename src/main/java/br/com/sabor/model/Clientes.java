package br.com.sabor.model;

import javax.persistence.*;

@Entity
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_cliente")
    private String nomeCliente;
    private String telefone;
    private String endereco;

    public Clientes() {

    }

    public Clientes(Long id, String nomeCliente, String telefone, String endereco) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String gerarResumo() {
        return "Cliente: " + this.nomeCliente + " | WhatsApp: " + this.telefone;
    }

    @Override
    public String toString() {
        return this.nomeCliente; // Isso faz o nome aparecer no ComboBox
    }
}