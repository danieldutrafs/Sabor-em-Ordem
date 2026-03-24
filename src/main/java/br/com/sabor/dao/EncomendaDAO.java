package br.com.sabor.dao;

import br.com.sabor.model.Encomenda;
import br.com.sabor.model.ItemEncomenda;
import br.com.sabor.model.Produto;
import br.com.sabor.util.JPAUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JTable;

public class EncomendaDAO {

    //metodo para saver venda completa ou nada
    // Método para salvar venda completa ou nada
    public void salvarTudoOuNada(Encomenda enco, JTable tabela, boolean novoCliente) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // 1. TRATAMENTO DO CLIENTE
            if (novoCliente) {
                // Se for novo, apenas persiste
                em.persist(enco.getCliente());
            } else {
                // Se for cliente antigo, atualizamos o endereço dele com o que está na tela
                // Isso evita que o endereço do cliente fique 'null' nos cards
                enco.getCliente().setEndereco(enco.getEndereco());
                enco.setCliente(em.merge(enco.getCliente()));
            }

            // 2. SALVAMENTO DA ENCOMENDA
            // O valorTotalPedido já deve ter sido setado no botão Salvar da tela
            em.persist(enco);

            // 3. SALVAMENTO DOS ITENS E BAIXA DE ESTOQUE
            for (int i = 0; i < tabela.getRowCount(); i++) {
                // Pega os dados da linha da tabela
                String nomeProd = tabela.getValueAt(i, 0).toString();
                int qtd = Integer.parseInt(tabela.getValueAt(i, 1).toString());

                // Busca o produto no banco pelo nome para garantir que temos o objeto atualizado
                Produto prodBanco = em.createQuery("SELECT p FROM Produto p WHERE p.nomeProduto = :nome", Produto.class)
                        .setParameter("nome", nomeProd)
                        .getSingleResult();

                // Cria o vínculo entre o Item e a Encomenda
                ItemEncomenda item = new ItemEncomenda();
                item.setEncomenda(enco);
                item.setP(prodBanco);
                item.setQuantidadeItem(qtd);

                // Salva o preço que o produto custa hoje (importante para relatórios futuros)
                item.setPrecoMomento(prodBanco.getValorUni());

                em.persist(item);

                // 4. ATUALIZAÇÃO DE ESTOQUE
                int estoqueAtual = prodBanco.getQuantidade();
                prodBanco.setQuantidade(estoqueAtual - qtd);
                em.merge(prodBanco);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    //metodo para listar enoomendas
    public List<br.com.sabor.model.Encomenda> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.clear();
            // O "LEFT JOIN FETCH" garante que os itens venham junto na mesma consulta
            String jpql = "SELECT DISTINCT e FROM Encomenda e LEFT JOIN FETCH e.listaItens";
            return em.createQuery(jpql, br.com.sabor.model.Encomenda.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    //metodo para enoomendas do mes
    public List<br.com.sabor.model.Encomenda> listarVendasDoMes(int mes, int ano) {
        EntityManager em = br.com.sabor.util.JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT v FROM Encomenda v "
                    + "JOIN FETCH v.listaItens "
                    + "WHERE MONTH(v.dataEntrega) = :mes AND YEAR(v.dataEntrega) = :ano";

            return em.createQuery(jpql, br.com.sabor.model.Encomenda.class)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    //metodo para buscar produto mais vendido
    public List<Object[]> buscarProdutosMaisVendidos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT i.p.nomeProduto, SUM(i.quantidadeItem), SUM(i.quantidadeItem * i.precoMomento) "
                    + "FROM ItemEncomenda i "
                    + "GROUP BY i.p.nomeProduto "
                    + "ORDER BY SUM(i.quantidadeItem) DESC";
            return em.createQuery(jpql).setMaxResults(10).getResultList();
        } finally {
            em.close();
        }
    }

    //metodo auxiliar
    public void registrarVendaCompleta(Encomenda v, javax.swing.JTable tabelaDeProdutos) throws Exception {
        salvarTudoOuNada(v, tabelaDeProdutos, false);
    }

    //metodo para buscar os iens
    public List<ItemEncomenda> buscarItensDaEncomenda(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Busca a encomenda e já "toca" na lista para forçar o carregamento antes de fechar o EM
            Encomenda e = em.find(Encomenda.class, id);
            if (e != null) {
                e.getListaItens().size(); // Isso força o Hibernate a buscar os itens agora
                return e.getListaItens();
            }
            return null;
        } finally {
            em.close();
        }
    }

    public void salvarAlteracoes(Encomenda enco) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // O merge atualiza os dados da encomenda que já existe no banco
            em.merge(enco);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public double consultarFaturamentoSemanal() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Mudamos para Object.class para aceitar o que o banco mandar
            String jpql = "SELECT SUM(e.valorTotalPedido) FROM Encomenda e "
                    + "WHERE e.dataEntrega >= :seteDiasAtras";

            java.time.LocalDate seteDiasAtras = java.time.LocalDate.now().minusDays(7);

            Object resultado = em.createQuery(jpql)
                    .setParameter("seteDiasAtras", seteDiasAtras)
                    .getSingleResult();

            if (resultado != null) {
                // Se for BigDecimal (comum no SUM), converte. Se for Double, também funciona.
                return ((Number) resultado).doubleValue();
            }
            return 0.0;
        } catch (Exception e) {
            System.err.println("Erro na query de faturamento: " + e.getMessage());
            return 0.0;
        } finally {
            em.close();
        }
    }

    public List<br.com.sabor.model.Encomenda> listarPorStatus(String status) {
        EntityManager em = br.com.sabor.util.JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT e FROM Encomenda e LEFT JOIN FETCH e.listaItens WHERE e.status = :status";
            return em.createQuery(jpql, br.com.sabor.model.Encomenda.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
