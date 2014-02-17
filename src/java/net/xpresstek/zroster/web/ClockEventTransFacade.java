/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xpresstek.zroster.web;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import net.xpresstek.zroster.ejb.ClockEvent;
import net.xpresstek.zroster.ejb.ClockEventTrans;
import net.xpresstek.zroster.ejb.Employee;
import net.xpresstek.zroster.web.ClockEventController.ClockEventControllerConverter;

/**
 *
 * @author apavlune
 */
@Stateless
public class ClockEventTransFacade extends AbstractFacade<ClockEventTrans> {

    public ClockEventTransFacade() {
        super(ClockEventTrans.class);
    }

    /**
     * Returns last clock event for specified employee
     *
     * @param employee_id Employee id.
     * @return last ClockEventTrans matching the id, null if none.
     */
    public ClockEventTrans getLastEvent(Employee employee_id) {
        TypedQuery<ClockEventTrans> query = getEntityManager().
                createNamedQuery("ClockEventTrans.findLastEvent", ClockEventTrans.class);

        query.setParameter("id", employee_id);
        query.setMaxResults(1);

        ClockEventTrans retval = null;
        try {
            retval = query.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UploadController.class.getName()).
                    log(Level.INFO, null, e);
        }
        return retval;
    }

    /**
     * Finds list clockin event for the employee
     *
     * @param employee_id Employee to search for
     * @param clock_out Clock out event
     * @return Clock in event for the employee
     */
    public ClockEventTrans getLastClockIn(Employee employee_id, ClockEventTrans clock_out) {
        ClockEvent clockin
                = ClockEventControllerConverter.getController().getClockInId();
        TypedQuery<ClockEventTrans> query = getEntityManager().
                createNamedQuery("ClockEventTrans.findLastClockIn",
                        ClockEventTrans.class);

        query.setParameter("employee", employee_id);
        query.setParameter("outpkid", clock_out.getPkid());
        query.setParameter("eventid", clockin);

        query.setMaxResults(1);

        ClockEventTrans retval = null;
        try {
            retval = query.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UploadController.class.getName()).
                    log(Level.INFO, null, e);
        }
        return retval;
    }

    /**
     * Retrieves clock events using employee id and specified start and end
     * interval.
     *
     * @param employee Employee
     * @param start Start interval.
     * @param end End interval.
     * @return List of the shifts that employee is schedules to work.
     */
    public List<ClockEventTrans> findClockEventsByEmployeeAndInterval(Employee employee,
            Date start,
            Date end) {

        TypedQuery<ClockEventTrans> query = getEntityManager().
                createNamedQuery("ClockEventTrans.findByEmployeeAndInterval",
                        ClockEventTrans.class);

        query.setParameter("employee", employee);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();

    }
    
     /**
     * Retrieves clock events specified start and end
     * interval.
     *
     * @param start Start interval.
     * @param end End interval.
     * @return List of the shifts for the specified interval.
     */
    public List<ClockEventTrans> findClockEventsByInterval(
            Date start,
            Date end) {

        TypedQuery<ClockEventTrans> query = getEntityManager().
                createNamedQuery("ClockEventTrans.findByInterval",
                        ClockEventTrans.class);

        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();

    }

}
