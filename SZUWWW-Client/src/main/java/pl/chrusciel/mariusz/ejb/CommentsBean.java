package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.Comment;

public interface CommentsBean {

	public Comment add(Comment comment);

	public void delete(Comment comment);

	public List<Comment> getAll();

	public void update(Comment comment);

	public Comment getById(int id);
}
