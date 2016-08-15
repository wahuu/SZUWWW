package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.FaultType;

public interface FaultTypeBean {
	public void add(FaultType faultType);

	public void delete(FaultType faultType);

	public List<FaultType> getAll();

	public void update(FaultType faultType);
}
