/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sabor.dao;

import br.com.sabor.model.Categoria;
import br.com.sabor.util.JPAUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author dutra
 */
public class CategoriaDAO {
    
    public void salvarCategoria(Categoria c){
        EntityManager em = JPAUtil.getEntityManager();
        
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }
    
    public Categoria buscarPorNome(String nome) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        // Criamos uma consulta que busca na classe Categoria onde o nome é igual ao parâmetro
        return em.createQuery("SELECT c FROM Categoria c WHERE c.nome = :nome", Categoria.class)
                 .setParameter("nome", nome)
                 .getSingleResult(); // Retorna o resultado único
    } catch (Exception e) {
        return null; // Se não encontrar nada ou der erro, retorna null
    } finally {
        em.close();
    }
}
}
