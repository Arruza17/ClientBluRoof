package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.Email;

/**
 * Entity representing users. Contains basic personal data, identification
 *
 * @author Yeray Sampedro
 */
@NamedQueries({
    @NamedQuery(
            name = "logInUser", query = "SELECT u FROM User u WHERE u.login= :login and u.password= :password")
    ,
    @NamedQuery(
            name = "changePassword", query = "UPDATE User u SET u.password=:newPass, u.lastPasswordChange = current_time() WHERE u.login= :login"),
     @NamedQuery(
            name = "findByLogin", query = "SELECT u FROM User u WHERE u.login= :login")
    ,
}
)

@Entity
@Table(schema = "bluroof")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String login;
    /**
     * User's full name.
     */
    private String fullName;
    /**
     * User's password.
     */

    private String password;
    /**
     * User's email.
     */
    @Column(unique = true)
    @Email(message = "Please provide a valid email address")
    private String email;
    /**
     * User's birth date.
     */
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    /**
     * User's status ENABLED/DISABLED.
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /**
     * User's privileges ADMIN/HOST/GUEST.
     */
    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;
    /**
     * User's last date of password change.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;
    /**
     * User's phone number.
     */
    private String phoneNumber;

    @OneToMany(cascade = ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<LastSignIn> lastSignIns;

    @XmlTransient
    public List<LastSignIn> getLastSignIns() {
        return lastSignIns;
    }

    public void setLastSignIns(List<LastSignIn> lastSignIns) {
        this.lastSignIns = lastSignIns;
    }

    /**
     * Method that gets the identification of the User
     *
     * @return id the identification number
     */
    public Long getId() {
        return id;
    }

    /**
     * Method that sets the identification of the user
     *
     * @param id the identification number to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method that gets the login information of the user
     *
     * @return login the user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Method that sets the users login
     *
     * @param login the user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Method that gets the user's full name
     *
     * @return fullName the user's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Method that sets the user's full name
     *
     * @param fullName the full name to be set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Method that gets the password of the user
     *
     * @return password user's password
     */
    @XmlTransient
    public String getPassword() {
        return password;
    }

    /**
     * Method that sets the password of the user
     *
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method that gets the email of a user
     *
     * @return email the email to get
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that sets the email of the user
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method that gets the user's birth date
     *
     * @return birthdate the birthdate of the user
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Method that sets the birthdate of the user
     *
     * @param birthDate the birthdate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Method that gets the status of the user
     *
     * @return status the user status to get
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Method that sets the status of the user
     *
     * @param status the status to set
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Method that gets the privilege of the user
     *
     * @return privilege the privilege to get
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Method that sets the privilege of the user
     *
     * @param privilege the privilege to set
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Method that gets the time of the last password change
     *
     * @return lastPasswordChange the last time the password was changed
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Method that sets the last time the password was changed
     *
     * @param lastPasswordChange the time the last password was changed
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * Method that gets the phone number of the user
     *
     * @return phoneNumber the phone number to get
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method that sets the phone number of the user
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Integer representation for User instance.
     *
     * @return the hashcode value
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two User objects for equality. This method consider a User equal
     * to another User if their id fields have the same value.
     *
     * @param object The other User object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the User id.
     *
     * @return The String representing the User id.
     */
    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }

}
