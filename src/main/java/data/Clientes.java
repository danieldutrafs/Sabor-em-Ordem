package data;

public class Clientes {
    private int id;
    private String nomeCliente;
    private int telefone;
    private String endereco;

    public Clientes(int id, String nomeCliente, int telefone, String endereco) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

   public String gerarResumo(){
       return "Cliente: " + this.nomeCliente + " | WhatsApp: " + this.telefone;
    }
}
