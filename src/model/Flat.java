package model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing the flat of the dwelling. It contains the following
 * fields: a short with the number of rooms of the flat, a short with the number
 * of bathrooms of the flat and a list of flat facilities.
 *
 * @author jorge
 */


@XmlRootElement
public class Flat extends Dwelling  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short nRooms;
    /**
     * number of bathrooms
     */

    private Short nBathrooms;
    /**
     * list of flat facilities
     */
 
    private List<FlatFacility> facilityList;

    /**
     *
     * @return number of rooms
     */
    public Short getnRooms() {
        return nRooms;
    }

    /**
     *
     * @param nRooms set number of rooms
     */
    public void setnRooms(Short nRooms) {
        this.nRooms = nRooms;
    }

    /**
     *
     * @return FlatFacility list
     */
    @XmlTransient
    public List<FlatFacility> getFacilityList() {
        return facilityList;
    }

    /**
     *
     * @param facilityList set FlatFacility list
     */
    public void setFacilityList(List<FlatFacility> facilityList) {
        this.facilityList = facilityList;
    }

    /**
     * Obtains a string representation of the Flat.
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + " Flat{" + "nRooms=" + nRooms + ", nBathrooms=" + nBathrooms + ", facilityList=" + facilityList + '}';
    }

    
}
