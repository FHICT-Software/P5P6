//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Appointment.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/12
 */
public class Appointment {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private String subject;
    private IPeriod period;
    private List<Contact> invitees;
  //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * This is the constructor for Appointment.
     *
     * @param subject is the subject of the appointment (can be empty).
     * @param period  is the period in which the appointment takes place.
     */
    public Appointment(String subject, IPeriod period) {
        this.subject = subject;
        this.period = period;

        this.invitees = new ArrayList<Contact>();
    }
    //</editor-fold>

    public String getSubject() {
        return this.subject;
    }

    public IPeriod getPeriod() {
        return this.period;
    }

    /**
     * This operation returns an Iterator of the invitees element.
     *
     * @return the Iterator of invitees. Returns null if empty list.
     */
    public Iterator<Contact> invitees() {
        if (invitees.size() > 0) {
            return invitees.iterator();
        } else {
            return null;
        }
    }

    /**
     * This operation adds a contact to the list of invitees if this appointment does not conflict with appointments of the
     * contact. There can be 0..* contacts in the invitees list.
     *
     * @param contact the contact that will be added to the list of invitees.
     */
    public void addContact(Contact contact) {
        if (invitees.size() > 0) {
            for (Contact c : invitees) {
                if (!c.equals(contact)) {
                    invitees.add(contact);
                }
            }
        } else {
            invitees.add(contact);
        }
    }

    /**
     * This operation removes a contact from the list. Note that this has to be the same contact instance as the one in the list.
     * There can be 0..* contacts in the invitees list.
     *
     * @param contact is the contact that will be removed from invitees.
     */
    public void removeContact(Contact contact) {
        int i = -1;
        for (Contact c : invitees) {
            if (c.equals(contact)) {
                i = invitees.indexOf(c);
            }
        }

        if (i != -1) {
            invitees.remove(i);
        }

    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass().equals(this.getClass())) {
            return equals((Appointment) object);
        } else {
            return false;
        }
    }

    public boolean equals(Appointment appointment) {
        boolean tmpA = this.invitees.equals(appointment.invitees);
        boolean tmpB = this.period.equals(appointment.period);
        boolean tmpC = this.subject.equals(appointment.subject);
        return (this.invitees.equals(appointment.invitees)
            && this.period.equals(appointment.period)
            && this.subject.equals(appointment.subject));
    }
    //</editor-fold>
}
