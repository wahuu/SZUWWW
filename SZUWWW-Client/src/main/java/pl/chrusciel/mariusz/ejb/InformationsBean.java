package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.Information;

public interface InformationsBean {

	public void add(Information information);

	public void delete(Information information);

	public List<Information> getAll();

	public void update(Information information);

}
