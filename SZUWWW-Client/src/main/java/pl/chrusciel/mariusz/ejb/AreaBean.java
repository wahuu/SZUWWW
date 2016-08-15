package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.Area;

public interface AreaBean {
	public void add(Area area);

	public void delete(Area area);

	public List<Area> getAll();

	public void update(Area area);
}
