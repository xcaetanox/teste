package br.com.andreteste.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import br.com.andreteste.modelos.NodeMenu;

public class DAO<T> {

	private final Class<T> classe;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public int adiciona(T t) {

		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
		em.close();
		return getLastId();
	}
	
	
	public void verificaHasChild(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		List<NodeMenu> listaMenu = (List<NodeMenu>) listaTodos() ;
		
		for(NodeMenu menu :listaMenu){
			if(menu.getParentId()>0){
				Query query = em.createQuery("select n from NodeMenu n where id=:pId");
				query.setParameter("pId", menu.getParentId());
				NodeMenu menuPai = (NodeMenu) query.getSingleResult();
				menuPai.setHasChildren(true);
				atualiza((T) menuPai);
			}
		}
	}
	
	
	
	private int getLastId(){
		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select max(n.id) from NodeMenu n");

		
		try {
			int lastId = (int) query.getSingleResult();

			return lastId;
		} catch (NoResultException nre) {

			return 0;
		} finally {
			em.close();
		}
	}

	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
		em.close();
	}

	public void atualiza(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		em.close();
	}
	
	
	

	public List<T> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		em.close();
		return lista;
	}
	
	public List<NodeMenu> listaRaiz() {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select n from NodeMenu n where (hasChildren=1 and parentId=0) or (hasChildren=0 and parentId =0)  order by id asc");

		
		try {
			List<NodeMenu> listaMenu = query.getResultList();

			return listaMenu;
		} catch (NoResultException nre) {

			return null;
		} finally {
			em.close();
		}

	}
	
	
	public List<NodeMenu> listaArvore(int parentId) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select n from NodeMenu n where parentId=:pParentId order by id asc");

		query.setParameter("pParentId", parentId);
		try {
			List<NodeMenu> listaMenu = query.getResultList();

			return listaMenu;
		} catch (NoResultException nre) {

			return null;
		} finally {
			em.close();
		}

	}

	

	

}
