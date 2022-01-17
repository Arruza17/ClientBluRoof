package model;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing users. Contains data about their current professional
 * state as well as the comments they have made
 *
 * @author Yeray Sampedro
 */
@Entity
@Table(schema = "bluroof", name = "guest")
@XmlRootElement
public class Guest extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Guest's state WORKER/STUDENT/BOTH/UNEMPLOYED.
     */
    @Enumerated(EnumType.STRING)
    private ActualState actualState;

    /**
     * Relational field that contains the comments made
     */
    @OneToMany(cascade = ALL, mappedBy = "commenter")
    private List<CommentBean> comments;

    @XmlTransient
    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
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
