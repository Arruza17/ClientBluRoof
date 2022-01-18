package model;

import java.io.Serializable;
import java.util.Date;

import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
@XmlRootElement
public class DwellingBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the dwelling
     */
    private SimpleLongProperty id;
    /**
     * Where the DwellingBean is located, the structure should be: STREET,
     * FLOOR-LETTER, CP example: Altzaga Kalea 1-A, 48950
     */
    private SimpleStringProperty address;
    /**
     * If the DwellingBean has WiFi or not
     */
    private SimpleBooleanProperty hasWiFi;
    /**
     * Square meters of the dwelling saved as m^2
     */
    private SimpleDoubleProperty squareMeters;

    /**
     * Relational field containing Neighbourhood of the dwelling
     */
    private SimpleObjectProperty<Neighbourhood> neighbourhood;
    /**
     * Date in which the dwelling was made
     */
    private SimpleObjectProperty<Date> constructionDate;
    /**
     * Relational field containing the host of the dwelling
     */
    private SimpleObjectProperty<Owner> host;
    /**
     * Rating of the dwelling. It is set the 0 when a new dwelling is created.
     * It will contain the average rating given by the users
     */
    private SimpleFloatProperty rating;
    /**
     * List of the comments made by the users about the dwelling.
     */
    private List<CommentBean> comments;

    /**
     * 
     * @param id
     * @param address
     * @param hasWiFi
     * @param squareMeters
     * @param neighbourhood
     * @param constructionDate
     * @param host
     * @param rating
     * @param comments 
     */
    public DwellingBean(SimpleLongProperty id, SimpleStringProperty address, SimpleBooleanProperty hasWiFi, SimpleDoubleProperty squareMeters, SimpleObjectProperty<Neighbourhood> neighbourhood, SimpleObjectProperty<Date> constructionDate, SimpleObjectProperty<Owner> host, SimpleFloatProperty rating, List<CommentBean> comments) {
        this.id = id;
        this.address = address;
        this.hasWiFi = hasWiFi;
        this.squareMeters = squareMeters;
        this.neighbourhood = neighbourhood;
        this.constructionDate = constructionDate;
        this.host = host;
        this.rating = rating;
        this.comments = comments;
    }
    
    //GETTERS AND SETTERS
    /**
     * Returns the id
     *
     * @return the id to get
     */
    public Long getId() {
        return this.id.get();
    }

    /**
     * The id to set
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id.set(id);
    }

    /**
     * Returns the address
     *
     * @return the address to get
     */
    public String getAddress() {
        return this.address.get();
    }

    /**
     * The address to set
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Returns if hasWiFi
     *
     * @return true if has WiFi, false if not
     */
    public Boolean getHasWiFi() {
        return this.hasWiFi.get();
    }

    /**
     * Sets if the dwelling has WiFi
     *
     * @param hasWiFi true if has WiFi, false if not
     */
    public void setHasWiFi(Boolean hasWiFi) {
        this.hasWiFi.set(hasWiFi);
    }

    /**
     * Return the square meters in m^2
     *
     * @return the square meters
     */
    public Double getSquareMeters() {
        return this.squareMeters.get();
    }

    /**
     * The square meters to set
     *
     * @param squareMeters the square meters to set
     */
    public void setSquareMeters(Double squareMeters) {
        this.squareMeters.set(squareMeters);
    }

    /**
     * Return the date in which the dwelling was made
     *
     * @return the date
     */
    public Date getConstructionDate() {
        return this.constructionDate.get();
    }

    /**
     * The date of the construction date
     *
     * @param constructionDate the construction date to set
     */
    public void setConstructionDate(Date constructionDate) {
        this.constructionDate.set(constructionDate);
    }

    /**
     * Returns all the ratings average
     *
     * @return the average rating
     */
    public Float getRating() {
        return this.rating.get();
    }

    /**
     * Sets the rating
     *
     * @param rating the rating to set
     */
    public void setRating(Float rating) {
        this.rating.set(rating);
    }

    /**
     * Returns the Neighbourhood of the dwelling
     *
     * @return the Neighbourhood
     */
    @XmlTransient
    public Neighbourhood getNeighbourhood() {
        return this.neighbourhood.get();
    }

    /**
     * Sets the Neighbourhood
     *
     * @param neighbourhood the Neighbourhood to set
     */
    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood.set(neighbourhood);
    }

    /**
     * Returns the owner/host of the dwelling
     *
     * @return the owner
     */
    @XmlTransient
    public Owner getHost() {
        return this.host.get();
    }

    /**
     * Sets the host/owner
     *
     * @param host the host to set
     */
    public void setHost(Owner host) {
        this.host.set(host);
    }

    /**
     * Returns a list of all the comments of the DwellingBean made by different
     * guests
     *
     * @return all the comments
     */
    public List<CommentBean> getComments() {
        return comments;
    }

    /**
     * Sets a list of all the comments of the DwellingBean
     *
     * @param comments the list to set
     */
    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    /**
     * Integer representation for DwellingBean instance.
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
     * Compares two DwellingBean objects for equality. This method consider a
     * DwellingBean equal to another DwellingBean if their id fields have the
     * same value.
     *
     * @param object The other DwellingBean object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DwellingBean)) {
            return false;
        }
        DwellingBean other = (DwellingBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the DwellingBean.
     *
     * @return The String representing the DwellingBean.
     */
    @Override
    public String toString() {
        return "Dwelling{" + "id=" + id + '}';
    }

}
