package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Services. Contains personal data, identification data and
 * relational data for accessing neighborhood data. data.
 *
 * @author Adrián
 */
@NamedQueries({
    @NamedQuery(name = "findServiceByType", query = "SELECT s FROM Service s WHERE s .type=:serviceType")
    ,
      @NamedQuery(name = "findServiceByAddress", query = "SELECT s FROM Service s WHERE s.address=:serviceAddress")
    ,
      @NamedQuery(name = "findServiceByName", query = "SELECT s FROM Service s WHERE s.name=:serviceName")
})

@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  * Identification field for service
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * enumeration that contains different types of services.
     */
    @Enumerated(EnumType.STRING)
    private ServiceType type;
    /**
     * Service address.
     */
    @NotNull
    private String address;
    /**
     * Service name.
     */
    @NotNull
    private String name;
    /**
     * Service neighborhood.
     */

    @XmlTransient
    @ManyToOne
    private Neighbourhood neighbourhood;

    /**
     *
     * @return id of the service.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id service id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return type of service.
     */
    public ServiceType getType() {
        return type;
    }

    /**
     *
     * @param type service type.
     */
    public void setType(ServiceType type) {
        this.type = type;
    }

    /**
     *
     * @return address of the service.
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address service address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return name of the service.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name service name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the neighborhood of the service.
     */
    @XmlTransient
    public Neighbourhood getNeighborhood() {
        return neighbourhood;
    }

    /**
     *
     * @param neighborhood service neighborhood.
     */
    public void setNeighborhood(Neighbourhood neighborhood) {
        this.neighbourhood = neighborhood;
    }

    /**
     * Compares two Service objects for equality. This method consider a Service
     * equal to another Service if their id fields have the same value.
     *
     * @param object The other Facility object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Service objects for equality. This method consider a Service
     * equal to another Service if their id fields have the same value.
     *
     * @param object The other Service object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", type=" + type + ", address=" + address + ", name=" + name + ", neighborhood=" + neighbourhood + '}';
    }

}
