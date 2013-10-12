package net.xpresstek.roster2.web;

import net.xpresstek.roster2.ejb.WeeklyHours;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;

@Named("weeklyHoursController")
@SessionScoped
public class WeeklyHoursController extends ControllerBase  {

    private WeeklyHours current;
    @EJB
    private net.xpresstek.roster2.web.WeeklyHoursFacade ejbFacade;


    public WeeklyHoursController() {
    }

    @Override
    public DataModel getItems() {
        if(ejbFacade!=null)
        {
            ejbFacade.refreshData();
        }
        recreatePagination();
        recreateModel();
        return super.getItems();
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
        current=(WeeklyHours)obj;
    }

    @Override
    void createNewCurrent() {
        current=new WeeklyHours();
    }

    public WeeklyHours getWeeklyHours(String id) {
        return (WeeklyHours)getObject(id);
    }

    @FacesConverter(forClass = WeeklyHours.class)
    public static class WeeklyHoursControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            WeeklyHoursController controller = (WeeklyHoursController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "weeklyHoursController");
            return controller.getWeeklyHours(value);
        }

  
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof WeeklyHours) {
                WeeklyHours o = (WeeklyHours) object;
                return o.getEmployee();
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + WeeklyHours.class.getName());
            }
        }
    }
}