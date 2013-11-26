/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xpresstek.roster2.web;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import net.xpresstek.roster2.ejb.ClockEvent;

/**
 *
 * @author apavlune
 */
@Stateless
public class ClockEventFacade extends AbstractFacade<ClockEvent> {

    private static final String CLOCK_OUT_NAME="ClockOut";
    private static final String CLOCK_IN_NAME="ClockIn";
    
    public ClockEventFacade() {
        super(ClockEvent.class);
    }
    
    /**
     * Retrieve clock out event.
     * @return Clock out event.
     */
    public ClockEvent getClockOutEvent()
    {
        return getEventByName(CLOCK_OUT_NAME);
    }
    
    /**
     * Retrieve clock in event.
     * @return Clock in event.
     */
    public ClockEvent getClockInEvent()
    {
        return getEventByName(CLOCK_IN_NAME);
    }
    /**
     * Returns a ClockEvent matching specified name.
     * @param name Name to look for.
     * @return ClockEvent matching the name.
     */
    private ClockEvent getEventByName(String name)
    {
        TypedQuery<ClockEvent> query = getEntityManager().
                createNamedQuery("ClockEvent.findByName", ClockEvent.class);
        
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
