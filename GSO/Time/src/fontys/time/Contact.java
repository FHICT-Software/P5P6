//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

//</editor-fold>
import java.util.ArrayList;
import java.util.Iterator;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Contact. //CHECK
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/13
 */
class Contact {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    /**
     * the name of the contact
     */
    private String name;
    /**
     * A list of Appointments that this contact is involved
     */
    private ArrayList<Appointment> agenda;
  //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * This is the constructor for Contact.
     *
     * @param name
     */
    public Contact(String name) {
        this.name = name;
        agenda = new ArrayList<Appointment>();
    }
  //</editor-fold>

    //<editor-fold desc="Getters & Setters">
    /**
     *
     * @return the name of the contact
     */
    public String getName() {
        return name;

    }
    //</editor-fold>
    /**
     * Add an appointment to the contacts list of appointments
     *
     * @param appointment
     *
     * @return false if there is a conflict with another appointment as part of the same time or if appointment is null.
     */
    boolean addAppointment(Appointment appointment) {
        if (appointment != null) {
            for (Appointment a : agenda) {
                if (a.getPeriod().intersectionWith(appointment.getPeriod()) != null) {
                    return false;
                }
            }
            agenda.add(appointment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove an appointment out of the conctacts list of appiontments
     *
     * @param appointment
     */
    void removeAppointment(Appointment appointment) {
        for (int i = 0; i < agenda.size(); i++) {
            if (agenda.get(i).equals(appointment)) {
                agenda.remove(i);
            }
        }
    }

    /**
     *
     * @return all appiontment in the list from the contact. Returns null if empty list
     */
    public Iterator<Appointment> allAppointments() {
        if (agenda.size() > 0) {
            return agenda.iterator();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() == this.getClass()) {
            return equals((Contact) object);
        } else {
            return false;
        }
    }

    public boolean equals(Contact contact) {
        boolean returner = this.agenda.equals(contact.agenda);
        returner = this.name.equals(contact.name);
        return returner;
    }
    //</editor-fold>

}
