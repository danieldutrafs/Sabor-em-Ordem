/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sabor.dao;

import br.com.sabor.model.Categoria;
import br.com.sabor.model.Produto;
import br.com.sabor.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author dutra
 */
public class ProdutoDAO {

    public void salvarProduto(Produto p) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // 1. Verifica se o produto tem uma categoria informada
            if (p.getCategoria() != null && p.getCategoria().getNome() != null) {

                // ATENÇÃO: Use "nomeCategoria" (o nome da variável na classe Java), não "nome_categoria"
                String hql = "SELECT c FROM Categoria c WHERE c.nome = :nome";

                TypedQuery<Categoria> query = em.createQuery(hql, Categoria.class);
                query.setParameter("nome", p.getCategoria().getNome());

                List<Categoria> categorias = query.getResultList();

                if (!categorias.isEmpty()) {
                    // Se achou no banco, usa a existente para não duplicar
                    p.setCategoria(categorias.get(0));
                } else {
                    // Se não achou, persiste a nova categoria primeiro
                    em.persist(p.getCategoria());
                }
            }

            // 2. Persiste o produto (agora com a categoria vinculada corretamente)
            em.persist(p);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Desfaz tudo se der erro
            }
            throw e; // Lança a exceção para o Controller exibir o JOptionPane
        } finally {
            em.close(); // Fecha a conexão
        }

    }

    public List<Produto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = em.createQuery("SELECT p FROM Produto p", Produto.class
        ).getResultList();

        em.close();
        return produtos;
    }
}
