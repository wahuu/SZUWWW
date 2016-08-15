/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.chrusciel.mariusz.SZUWWW_EJB;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import pl.chrusciel.mariusz.ejb.NewSessionBean;
import pl.chrusciel.mariusz.entities.Customer;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class NewSessionBeanImpl implements NewSessionBean {

	public void businessMethod() {
		Customer customer = new Customer("name", "lastName", "street", "City", "249182471", "ASB2314124");
	}

}
