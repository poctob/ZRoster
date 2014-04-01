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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import net.xpresstek.zroster.ejb.Configuration;
import net.xpresstek.zroster.web.util.DataChangeEventListener;
import net.xpresstek.zroster.web.util.DataChangedEvent;

/**
 *
 * @author apavlune
 */
@Stateless
public class ConfigurationFacade extends AbstractFacade<Configuration> {

    private List <DataChangeEventListener> listeners;
    public ConfigurationFacade() {
        
        super(Configuration.class);
        
        listeners = new ArrayList();
    }
    
    public void addListener(DataChangeEventListener l)
    {
        listeners.add(l);
    }
    
    @Override
     public void edit(Configuration entity) {
         
        DataChangedEvent event=new DataChangedEvent(this);
        for(DataChangeEventListener l : listeners)
        {
            l.updateData(event);
        }
        super.edit(entity);
    }
    
}
