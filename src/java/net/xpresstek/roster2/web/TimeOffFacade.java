/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xpresstek.roster2.web;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import net.xpresstek.roster2.ejb.Employee;
import net.xpresstek.roster2.ejb.TimeOff;

/**
 *
 * @author apavlune
 */
@Stateless
public class TimeOffFacade extends AbstractFacade<TimeOff> {
    public TimeOffFacade() {
        super(TimeOff.class);
    }
    
     public List<TimeOff> findByEmployeeID(Employee empl) {
        TypedQuery<TimeOff> query = getEntityManager().
                createNamedQuery("TimeOff.findByEmployeeID", TimeOff.class);
        query.setParameter("employee_id", empl);
        return query.getResultList();
    }
    
}
