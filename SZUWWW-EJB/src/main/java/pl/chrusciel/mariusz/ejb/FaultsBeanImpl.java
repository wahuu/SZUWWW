package pl.chrusciel.mariusz.ejb;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.chrusciel.mariusz.entities.Fault;
import pl.chrusciel.mariusz.helper.Status;

@Named
@Stateless
public class FaultsBeanImpl implements FaultsBean {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Fault fault) {
		em.persist(fault);
	}

	@Override
	public void delete(Fault fault) {
		fault = em.merge(fault);
		em.remove(fault);
	}

	@Override
	public List<Fault> getAll() {
		List<Fault> resultList = em.createQuery("SELECT f from Fault f", Fault.class).getResultList();
		return resultList;
	}

	/**
	 * Update zgloszenia z sprawdzeniem statusu czy zakonczone - zmiana dany
	 * endDate
	 */
	@Override
	public void update(Fault fault) {
		if (Status.ZAMKNIETE.toString().equals(fault.getStatus()) && fault.getEndDate() == null) {
			fault.setEndDate(Calendar.getInstance().getTime());
		} else if (!Status.ZAMKNIETE.toString().equals(fault.getStatus()) && fault.getEndDate() != null) {
			fault.setEndDate(null);
		}
		em.merge(fault);
	}

	@Override
	public List<Fault> getByStatus(List<String> statusList) {
		TypedQuery<Fault> query = em.createQuery("Select f from Fault f where f.status in :status", Fault.class);
		query.setParameter("status", statusList);
		return query.getResultList();
	}

}
