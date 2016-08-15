package pl.chrusciel.mariusz.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.chrusciel.mariusz.entities.Area;

@Named
@Stateless
public class AreaBeanImpl implements AreaBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Area area) {
		em.persist(area);
	}

	@Override
	public void delete(Area area) {
		area = em.merge(area);
		em.remove(area);
	}

	@Override
	public List<Area> getAll() {
		List<Area> resultList = em.createQuery("select a from Area a", Area.class).getResultList();
		return resultList;
	}

	@Override
	public void update(Area area) {
		em.merge(area);
	}

}
