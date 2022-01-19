package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing the last time the user has signIn into the application.
 * It contains the date of the last signIn
 *
 * @author Yeray Sampedro
 */
@NamedQueries({
    @NamedQuery(
            name = "findByUserLogin", query = "SELECT l FROM LastSignIn l WHERE l.user =:user ORDER BY l.lastSignIn ASC")
}
)

@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class LastSignIn implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for teh last signIn.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The date of the last SignIn
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSignIn;
    /**
     * The user
     */
    @ManyToOne
    private User user;

    /**
     * Returns the user
     *
     * @return the user to get
     */
    @XmlTransient
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the lastSignIn
     *
     * @return the lastSignIn to get
     */
    public Date getLastSignIn() {
        return lastSignIn;
    }

    /**
     * The lastSignIn date to set
     *
     * @param lastSignIn the lastSignIn LocalDateTime
     */
    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

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
     * Integer representation for LastSignIn instance.
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
     * Compares two LastSignIn objects for equality. This method consider a
     * LastSignIn equal to another one if their id fields have the same value.
     *
     * @param object The other LastSignIn object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LastSignIn)) {
            return false;
        }
        LastSignIn other = (LastSignIn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the lastSignIn.
     *
     * @return The String representing the Dwelling.
     */
    @Override
    public String toString() {
        return "entities.LastSignIn[ id=" + id + " ]";
    }

}
