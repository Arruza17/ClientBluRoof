package model;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Neighborhoods. Contains personal data, identification
 * data and relational data for accessing neighborhood services and dwellings
 * data.
 *
 * @author Adrián
 */
@NamedQueries({
    @NamedQuery(name = "findNeighbourhoodDwelligs", query = "SELECT d FROM Dwelling d WHERE d.neighbourhood=(Select n.id from Neighbourhood n where n.name=:neighbourhoodName)")
    ,
   @NamedQuery(name = "findNeighbourhoodServices", query = "SELECT s FROM Service s, Neighbourhood n WHERE s.neighbourhood=(Select n.id from Neighbourhood n where n.name=:neighbourhoodName)")
})

@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class Neighbourhood implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identification field for neighborhood.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Neighborhood name.
     */
    @NotNull
    private String name;
    /**
     * Neighborhood post code.
     */
    @NotNull
    private Integer postCode;
    /**
     * List of neighborhood dwellings.
     */
    @OneToMany(cascade = ALL, mappedBy = "neighbourhood", fetch = FetchType.EAGER)
    private List<DwellingBean> dwellings;
    /**
     * List of neighborhood services.
     */
 
    @OneToMany(cascade = ALL, mappedBy = "neighbourhood", fetch = FetchType.EAGER)

    private List<Service> services;

    /**
     *
     * @return the id of the neighborhood.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id Neighborhood id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the name of the neighborhood.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name Neighborhood name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Neighborhood post code.
     */
    public Integer getPostCode() {
        return postCode;
    }

    /**
     *
     * @param postCode Neighborhood post code
     */
    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    /**
     *
     * @return a list with neighborhood dwellings.
     */
    @XmlTransient
    public List<DwellingBean> getDwellings() {
        return dwellings;
    }

    /**
     *
     * @param dwellings Neighborhood dwellings list.
     */
    public void setDwellings(List<DwellingBean> dwellings) {
        this.dwellings = dwellings;
    }

    /**
     *
     * @return a list with neighborhood dwellings.
     */
    @XmlTransient
    public List<Service> getServices() {
        return services;
    }

    /**
     *
     * @param services Neighborhood services list.
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * Integer representation for Neighborhood instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Neighborhood objects for equality. This method consider a
     * Neighborhood equal to another Neighborhood if their id fields have the
     * same value.
     *
     * @param object The other Neighborhood object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Neighbourhood)) {
            return false;
        }
        Neighbourhood other = (Neighbourhood) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Neighborhood.
     *
     * @return The String representing the Customer.
     */
    @Override
    public String toString() {
        return "Neighborhood{" + "id=" + id + ", name=" + name + ", postCode=" + postCode + ", dwellings=" + dwellings + ", services=" + services + '}';
    }

}
