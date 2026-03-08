package data;

import java.util.List;
import java.util.ArrayList;

public class Estoque {
    private List<Produto> listaP;

    public Estoque() {
        this.listaP = new ArrayList<>(); 
    }
    
    public void adicionarProduto(Produto p){
        this.listaP.add(p);
    }
   
    public List<Produto> getListP(){
        return this.listaP;
    }
    
    public Produto buscarPorNome(String nome){
        for(Produto p : listaP){
            if(p.getNomeProduto().equalsIgnoreCase(nome)){
                return p;
            }
        }
        return null;
    }

   
}
