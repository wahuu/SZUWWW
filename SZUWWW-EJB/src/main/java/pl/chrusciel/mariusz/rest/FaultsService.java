package pl.chrusciel.mariusz.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.chrusciel.mariusz.ejb.FaultsBean;
import pl.chrusciel.mariusz.entities.Fault;

/**
 * SZU REST API which provide faults services
 * @author Wahuu
 *
 */
@RequestScoped
@Path("/faults")
@Produces("application/json")
@Consumes("application/json")
public class FaultsService {

	@Inject
	FaultsBean fb;

	/**
	 * Returns all faults from the system that the user has access to
	 * 
	 * @return
	 */
	@GET
	@Produces("application/json")
	public Response findFaults() {
		List<Fault> all = fb.getAll();
		return Response.ok(all).build();
	}

	/**
	 * Returns a fault based on a single ID
	 * 
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Path("/{id}")
	public Response findFaultById(@PathParam("id") String id) {
		return Response.ok(id).build();
	}
	
	/**
	 * Returns all faults by customer ID
	 * 
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Path("/customer/{id}")
	public Response findFaultsByCustomerId(@PathParam("id") String id) {
		return Response.ok(id).build();
	}

}
