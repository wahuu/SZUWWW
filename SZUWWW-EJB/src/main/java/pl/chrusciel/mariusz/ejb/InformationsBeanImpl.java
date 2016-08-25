package pl.chrusciel.mariusz.ejb;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.chrusciel.mariusz.entities.Information;

@Named
@Stateless
public class InformationsBeanImpl implements InformationsBean {

	@PersistenceContext
	EntityManager em;

	@Override
	public void add(Information information) {
		information.setDate(Calendar.getInstance().getTime());
		em.persist(information);
	}

	@Override
	public void delete(Information information) {
		information = em.merge(information);
		em.remove(information);
	}

	@Override
	public List<Information> getAll() {
		TypedQuery<Information> query = em.createQuery("Select i from Information i order by i.id desc", Information.class);
		query.setMaxResults(5);
		return query.getResultList();
	}

	@Override
	public void update(Information information) {
		em.merge(information);
	}

}
