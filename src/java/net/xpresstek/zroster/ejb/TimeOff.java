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
package net.xpresstek.zroster.ejb;

import com.gzlabs.utils.DateUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apavlune
 */
@Entity
@Table(name = "TimeOff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TimeOff.findAll", query = "SELECT t FROM TimeOff t"),
    @NamedQuery(name = "TimeOff.findByPkid", query = "SELECT t FROM TimeOff t WHERE t.pkid = :pkid"),
    @NamedQuery(name = "TimeOff.findByStart", query = "SELECT t FROM TimeOff t WHERE t.start = :start"),
    @NamedQuery(name = "TimeOff.findBeforeNow", query = "SELECT t FROM TimeOff t WHERE t.start < :start"),
    @NamedQuery(name = "TimeOff.findAfterAndNow", query = "SELECT t FROM TimeOff t WHERE t.start >= :start"),
    @NamedQuery(name = "TimeOff.findByEmployeeID", query = "SELECT t FROM TimeOff t WHERE t.employeeid = :employee_id"),
    @NamedQuery(name = "TimeOff.findByEnd", query = "SELECT t FROM TimeOff t WHERE t.end = :end"),
    @NamedQuery(name = "TimeOff.findByStatus", query = "SELECT t FROM TimeOff t"
        + " WHERE t.timeOffStatusid = :timeOffStatusid AND t.start >= :start")})
public class TimeOff implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pkid")
    private Integer pkid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @JoinColumn(name = "TimeOffStatus_id", referencedColumnName = "pkid")
    @ManyToOne(optional = false)
    private TimeOffStatus timeOffStatusid;
    @JoinColumn(name = "Employee_id", referencedColumnName = "pkID")
    @ManyToOne(optional = false)
    private Employee employeeid;

    public TimeOff() {
    }

    public TimeOff(Integer pkid) {
        this.pkid = pkid;
    }

    public TimeOff(Integer pkid, Date start, Date end) {
        this.pkid = pkid;
        this.start = start;
        this.end = end;
    }

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public TimeOffStatus getTimeOffStatusid() {
        return timeOffStatusid;
    }

    public void setTimeOffStatusid(TimeOffStatus timeOffStatusid) {
        this.timeOffStatusid = timeOffStatusid;
    }

    public Employee getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employee employeeid) {
        this.employeeid = employeeid;
    }

    public TimeZone getTimezone() {
        return TimeZone.getDefault();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkid != null ? pkid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimeOff)) {
            return false;
        }
        TimeOff other = (TimeOff) object;
        if ((this.pkid == null && other.pkid != null) || (this.pkid != null && !this.pkid.equals(other.pkid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.xpresstek.zroster.ejb.TimeOff[ pkid=" + pkid + " ]";
    }

    /**
     * Checks if supplied dates conflict with this object
     *
     * @param start Beginning of the period
     * @param end End of the period
     * @return True if there is a conflict, false otherwise
     */
    public boolean isConflicting(String start, String end) {
        if (timeOffStatusid != null) {
            return timeOffStatusid.getName().equals("Approved")
                    && DateUtils.isCalendarBetween
                    (this.start, this.end, start, end, true);
        }
        return false;

    }

    @Override
    public int compareTo(Object o) {
        return start.compareTo(((TimeOff)o).getStart());
    }
}
