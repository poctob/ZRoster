/* 
 * Copyright (C) 2014 Alex Pavlunenko <alexp at xpresstek.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.xpresstek.zroster.web;

import java.util.List;
import net.xpresstek.zroster.ejb.ClockEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("clockEventController")
@SessionScoped
public class ClockEventController extends ControllerBase {

    private ClockEvent current;
    @EJB
    private net.xpresstek.zroster.web.ClockEventFacade ejbFacade;

    public ClockEventController() {
    }

    @Override
    AbstractFacade getFacade() {
        return ejbFacade;
    }

    @Override
    Object getCurrent() {
        return current;
    }

    @Override
    void setCurrent(Object obj) {
        current = (ClockEvent) obj;
    }

    @Override
    void createNewCurrent() {
        current = new ClockEvent();
    }
    
    /**
     * Retrieves clock out event
     * @return Clock out event.
     */
    public ClockEvent getClockOutId()
    {
        return ejbFacade.getClockOutEvent();
    }
    
    /**
     * Retrieves all clock events from the database.
     * @return All clock events.
     */
    public List<ClockEvent> getAllItems()
    {
        return ejbFacade.findAll();
    }
    
     /**
     * Retrieves clock in event
     * @return Clock in event.
     */
    public ClockEvent getClockInId()
    {
        return ejbFacade.getClockInEvent();
    }


    public ClockEvent getClockEvent(Integer id) {
        return (ClockEvent) getObject(id);
    }

    @Override
    public List findAll() {
        return getAllItems();
    }

    @FacesConverter(forClass = ClockEvent.class)
    public static class ClockEventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClockEventController controller = (ClockEventController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clockEventController");
            return controller.getClockEvent(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ClockEvent) {
                ClockEvent o = (ClockEvent) object;
                return getStringKey(o.getPkid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ClockEvent.class.getName());
            }
        }
    }
}
