package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import static javax.persistence.CascadeType.ALL;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Facilities. Contains personal data, identification data
 * and relational data for accessing flat facilities.
 *
 * @author jorge
 */

@XmlRootElement
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleLongProperty id;

    private SimpleStringProperty type;

    private SimpleObjectProperty<Date> adquisitionDate;

    private List<FlatFacility> flatFacilities;

    @XmlTransient
    public List<FlatFacility> getFlatFacilities() {
        return flatFacilities;
    }

    public void setFlatFacilities(List<FlatFacility> flatFacilities) {
        this.flatFacilities = flatFacilities;
    }

    public Facility() {
        this.adquisitionDate=new SimpleObjectProperty();
        this.id= new SimpleLongProperty();
        this.type=new SimpleStringProperty();
    }

    public Long getId() {
         return this.id.get();
    }

    /**
     *
     * @param id facility id.
     */
    public void setId(Long id) {
        this.id.set(id);
    }

    /**
     *
     * @return the type of the facility.
     */
    public String getType() {
        return this.type.get();

    }

    /**
     *
     * @param type Facility type.
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     *
     * @return Adquisition date of the facility.
     */
    public Date getAdquisitionDate() {
        return this.adquisitionDate.get();
    }

    /**
     *
     * @param adquisitionDate Facility adquisition date.
     */
    public void setAdquisitionDate(Date adquisitionDate) {
        this.adquisitionDate.set(adquisitionDate);
    }

   
    
    @Override
    public String toString() {
        return "Facility{" + "id=" + id + ", type=" + type + ", adquisitionDate=" + adquisitionDate + ", flatFacilities=" + flatFacilities + '}';
    }

}
