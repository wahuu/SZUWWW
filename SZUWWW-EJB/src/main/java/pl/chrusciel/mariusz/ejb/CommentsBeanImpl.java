package pl.chrusciel.mariusz.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.chrusciel.mariusz.entities.Comment;

@Named
@Stateless
public class CommentsBeanImpl implements CommentsBean {

	@PersistenceContext
	EntityManager em;

	@Override
	public Comment add(Comment comment) {
		em.persist(comment);
		return comment;
	}

	@Override
	public void delete(Comment comment) {
		comment = em.merge(comment);
		em.remove(comment);
	}

	@Override
	public List<Comment> getAll() {
		List<Comment> resultList = em.createQuery("Select c from Comment c", Comment.class).getResultList();
		return resultList;
	}

	@Override
	public void update(Comment comment) {
		em.merge(comment);
	}

	@Override
	public Comment getById(int id) {
		TypedQuery<Comment> query = em.createQuery("Select c from Comment c where c.id = :id", Comment.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
