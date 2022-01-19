package model;

import java.io.Serializable;
import java.util.Date;

import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing dwellings. It contains the following fields: dwelling id,
 * dwelling address, if the dwelling hasWiFi, the squareMeters of the dwelling,
 * the different dwelling tags, in which neighbourhood is located, the creation
 * date of the dwelling, who is the owner of the dwelling,the average rating of
 * the dwelling based on, the rating given by the users, and all the comments
 * made by the users.
 *
 * @author Ander Arruza
 */
@NamedQueries({
    @NamedQuery(
            name = "findByMinRating", query = "SELECT d FROM Dwelling d WHERE d.rating >= :rating ORDER BY d.rating DESC"
    )
    ,
        @NamedQuery(
            name = "findByMinConstructionDate", query = "SELECT d FROM Dwelling d WHERE d.constructionDate >= :date ORDER BY d.constructionDate DESC"
    )
    ,
        @NamedQuery(
            name = "updateRating", query = "UPDATE Dwelling d SET d.rating = ( SELECT SUM(c.rating)/COUNT(c.rating) FROM Comment c WHERE dwellingId=:dwellingId) WHERE id=:dwellingId")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "bluroof", name = "dwelling")
@XmlRootElement
public class Dwelling implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the dwelling
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Where the Dwelling is located, the structure should be: STREET,
     * FLOOR-LETTER, CP example: Altzaga Kalea 1-A, 48950
     */
    @NotNull
    private String address;
    /**
     * If the Dwelling has WiFi or not
     */
    @NotNull
    private Boolean hasWiFi;
    /**
     * Square meters of the dwelling saved as m^2
     */
    private Double squareMeters;

    /**
     * Relational field containing Neighbourhood of the dwelling
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Neighbourhood neighbourhood;
    /**
     * Date in which the dwelling was made
     */
    @Temporal(TemporalType.DATE)
    private Date constructionDate;
    /**
     * Relational field containing the host of the dwelling
     */

    @ManyToOne(cascade = CascadeType.ALL)
    private Owner host;
    /**
     * Rating of the dwelling. It is set the 0 when a new dwelling is created.
     * It will contain the average rating given by the users
     */
    @NotNull
    private Float rating;
    /**
     * List of the comments made by the users about the dwelling.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dwelling", fetch = FetchType.EAGER)
    private List<Comment> comments;

    //GETTERS AND SETTERS
    /**
     * Returns the id
     *
     * @return the id to get
     */
    public Long getId() {
        return id;
    }

    /**
     * The id to set
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the address
     *
     * @return the address to get
     */
    public String getAddress() {
        return address;
    }

    /**
     * The address to set
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns if hasWiFi
     *
     * @return true if has WiFi, false if not
     */
    public Boolean getHasWiFi() {
        return hasWiFi;
    }

    /**
     * Sets if the dwelling has WiFi
     *
     * @param hasWiFi true if has WiFi, false if not
     */
    public void setHasWiFi(Boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    /**
     * Return the square meters in m^2
     *
     * @return the square meters
     */
    public Double getSquareMeters() {
        return squareMeters;
    }

    /**
     * The square meters to set
     *
     * @param squareMeters the square meters to set
     */
    public void setSquareMeters(Double squareMeters) {
        this.squareMeters = squareMeters;
    }

    /**
     * Return the date in which the dwelling was made
     *
     * @return the date
     */
    public Date getConstructionDate() {
        return constructionDate;
    }

    /**
     * The date of the construction date
     *
     * @param constructionDate the construction date to set
     */
    public void setConstructionDate(Date constructionDate) {
        this.constructionDate = constructionDate;
    }

    /**
     * Returns all the ratings average
     *
     * @return the average rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Sets the rating
     *
     * @param rating the rating to set
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * Returns the Neighbourhood of the dwelling
     *
     * @return the Neighbourhood
     */
    @XmlTransient
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Sets the Neighbourhood
     *
     * @param neighbourhood the Neighbourhood to set
     */
    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Returns the owner/host of the dwelling
     *
     * @return the owner
     */
    @XmlTransient
    public Owner getHost() {
        return host;
    }

    /**
     * Sets the host/owner
     *
     * @param host the host to set
     */
    public void setHost(Owner host) {
        this.host = host;
    }

    /**
     * Returns a list of all the comments of the Dwelling made by different
     * guests
     *
     * @return all the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets a list of all the comments of the Dwelling
     *
     * @param comments the list to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Integer representation for Dwelling instance.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Dwelling objects for equality. This method consider a
     * Dwelling equal to another Dwelling if their id fields have the same
     * value.
     *
     * @param object The other Dwelling object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dwelling)) {
            return false;
        }
        Dwelling other = (Dwelling) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Dwelling.
     *
     * @return The String representing the Dwelling.
     */
    @Override
    public String toString() {
        return "Dwelling{" + "id=" + id + '}';
    }

}
