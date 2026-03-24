/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sabor.dao;

import br.com.sabor.model.Clientes;
import br.com.sabor.util.JPAUtil;
import br.com.sabor.view.TelaClientes;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author dutra
 */
public class ClienteDAO {

    public void salvar(br.com.sabor.model.Clientes cliente) {
        javax.persistence.EntityManager em = br.com.sabor.util.JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public java.util.List<br.com.sabor.model.Clientes> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Clientes c", br.com.sabor.model.Clientes.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            return new java.util.ArrayList<>();
        } finally {
            em.close();
        }
    }

    public br.com.sabor.model.Clientes buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(br.com.sabor.model.Clientes.class, id);
        } finally {
            em.close();
        }
    }

// Método para editar
    public void editar(br.com.sabor.model.Clientes cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<br.com.sabor.model.Clientes> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            
            String jpql = "SELECT c FROM br.com.sabor.model.Clientes c WHERE c.nomeCliente LIKE :nome";

            return em.createQuery(jpql, br.com.sabor.model.Clientes.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erro na busca: " + e.getMessage());
            return new java.util.ArrayList<>();
        } finally {
            em.close();
        }
    }
}
