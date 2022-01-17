package model;

import enumerations.ActualState;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing users. Contains data about their current professional
 * state as well as the comments they have made
 *
 * @author Yeray Sampedro
 */
@XmlRootElement
public class Guest extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Guest's state WORKER/STUDENT/BOTH/UNEMPLOYED.
     */

    private ActualState actualState;

    /**
     * Relational field that contains the comments made
     */
 
    private List<Comment> comments;

    @XmlTransient
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Gets the actual state of the guest
     *
     * @return actualState the current state
     */
    public ActualState getActualState() {
        return actualState;
    }

    /**
     * Sets the actual state of the guest
     *
     * @param actualState the actual state to be set
     */
    public void setActualState(ActualState actualState) {
        this.actualState = actualState;
    }

}
