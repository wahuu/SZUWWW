package pl.chrusciel.mariusz.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.chrusciel.mariusz.entities.FaultType;

@Named
@Stateless
public class FaultTypeBeanImpl implements FaultTypeBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(FaultType faultType) {
		em.persist(faultType);
	}

	@Override
	public void delete(FaultType faultType) {
		faultType = em.merge(faultType);
		em.remove(faultType);
	}

	@Override
	public List<FaultType> getAll() {
		List<FaultType> resultList = em.createQuery("SELECT f from FaultType f", FaultType.class).getResultList();
		return resultList;
	}

	@Override
	public void update(FaultType faultType) {
		em.merge(faultType);
	}

}
