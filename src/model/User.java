package model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing users. Contains basic personal data, identification
 *
 * @author Yeray Sampedro
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for user.
     */

    private SimpleStringProperty id;

    private SimpleStringProperty login;
    /**
     * User's full name.
     */
    private SimpleStringProperty fullName;
    /**
     * User's password.
     */

    private SimpleStringProperty password;
    /**
     * User's email.
     */
    private SimpleStringProperty email;
    /**
     * User's birth date.
     */

    private SimpleObjectProperty<Date> birthDate;
    /**
     * User's status ENABLED/DISABLED.
     */
    private SimpleStringProperty status;

    /**
     * User's privileges ADMIN/HOST/GUEST.
     */
    private SimpleStringProperty privilege;
    /**
     * User's last date of password change.
     */
    private SimpleObjectProperty<Date> lastPasswordChange;
    /**
     * User's phone number.
     */
    private SimpleStringProperty phoneNumber;

    private List<LastSignIn> lastSignIns;

    public User(SimpleStringProperty id, SimpleStringProperty login, SimpleStringProperty fullName, SimpleStringProperty password, SimpleStringProperty email, SimpleObjectProperty<Date> birthDate, SimpleStringProperty status, SimpleStringProperty privilege, SimpleObjectProperty<Date> lastPasswordChange, SimpleStringProperty phoneNumber, List<LastSignIn> lastSignIns) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.status = status;
        this.privilege = privilege;
        this.lastPasswordChange = lastPasswordChange;
        this.phoneNumber = phoneNumber;
        this.lastSignIns = lastSignIns;
    }

    public User() {
         this.id = new SimpleStringProperty();
        this.login =  new SimpleStringProperty();
        this.fullName =  new SimpleStringProperty();
        this.password =  new SimpleStringProperty();
        this.email =  new SimpleStringProperty();
        this.birthDate =  new SimpleObjectProperty();
        this.status =  new SimpleStringProperty();
        this.privilege =  new SimpleStringProperty();
        this.lastPasswordChange =  new SimpleObjectProperty();
        this.phoneNumber = new SimpleStringProperty();;
        this.lastSignIns = new ArrayList<LastSignIn>();
    }

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
        return Long.parseLong(this.id.get());
    }

    /**
     * Method that sets the identification of the user
     *
     * @param id the identification number to be set
     */
    public void setId(Long id) {
        this.id.set(String.valueOf(id));
    }

    /**
     * Method that gets the login information of the user
     *
     * @return login the user's login
     */
    public String getLogin() {
        return this.login.get();
    }

    /**
     * Method that sets the users login
     *
     * @param login the user's login
     */
    public void setLogin(String login) {
        this.login.set(login);
    }

    /**
     * Method that gets the user's full name
     *
     * @return fullName the user's full name
     */
    public String getFullName() {
        return this.fullName.get();
    }

    /**
     * Method that sets the user's full name
     *
     * @param fullName the full name to be set
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * Method that gets the password of the user
     *
     * @return password user's password
     */
    @XmlTransient
    public String getPassword() {
        return this.password.get();
    }

    /**
     * Method that sets the password of the user
     *
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password.set(password);
    }

    /**
     * Method that gets the email of a user
     *
     * @return email the email to get
     */
    public String getEmail() {
        return this.email.get();
    }

    /**
     * Method that sets the email of the user
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Method that gets the user's birth date
     *
     * @return birthdate the birthdate of the user
     */
    public Date getBirthDate() {
        return this.birthDate.get();
    }

    /**
     * Method that sets the birthdate of the user
     *
     * @param birthDate the birthdate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate.set(birthDate);
    }

    /**
     * Method that gets the status of the user
     *
     * @return status the user status to get
     */
    public String getStatus() {
        return this.status.get();
    }

    /**
     * Method that sets the status of the user
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status.set(status);
    }

    /**
     * Method that gets the privilege of the user
     *
     * @return privilege the privilege to get
     */
    public String getPrivilege() {
        return this.privilege.get();
    }

    /**
     * Method that sets the privilege of the user
     *
     * @param privilege the privilege to set
     */
    public void setPrivilege(String privilege) {
        this.privilege.set(privilege);
    }

    /**
     * Method that gets the time of the last password change
     *
     * @return lastPasswordChange the last time the password was changed
     */
    public Date getLastPasswordChange() {
        return this.lastPasswordChange.get();
    }

    /**
     * Method that sets the last time the password was changed
     *
     * @param lastPasswordChange the time the last password was changed
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange.set(lastPasswordChange);
    }

    /**
     * Method that gets the phone number of the user
     *
     * @return phoneNumber the phone number to get
     */
    public String getPhoneNumber() {
        return this.phoneNumber.get();
    }

    /**
     * Method that sets the phone number of the user
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
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
