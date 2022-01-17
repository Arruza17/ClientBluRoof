package model;

import java.io.Serializable;
import java.util.Date;
/**
 * Entity representing comments. It contains the following fields: comment id,
 * comment text, the date in which the comment was made, the commenter who made
 * the comment and the building to which the comment has been made.
 *
 * @author Ander Arruza
 */
public class CommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * Identification field for the comment
     */
    private CommentId commentId;
    /**
     * The text of the comment
     */
    private String text;
    /**
     * The rating of the comment [1-5]
     */
    private Short rating;
    /**
     * Current time in which the comment was made
     */
    private Date commentDate;
    /**
     * Relational field containing the commenter who made it

     */ 
    private Guest commenter;
    /**
     * Relational field containing the relation between comment and the dwelling
     */
    private DwellingBean dwelling;

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
     * Returns the text
     *
     * @return the text of the comment
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the rating
     *
     * @return the rating
     */
    public Short getRating() {
        return rating;
    }

    /**
     * Sets the rating
     *
     * @param rating the rating to get
     */
    public void setRating(Short rating) {
        this.rating = rating;
    }

    /**
     * Returns the date in which the comment was made
     *
     * @return the date of the comment
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Sets the date in which the comment was made
     *
     * @param commentDate the date of the comment
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Returns the commenter
     *
     * @return the author of the comment
     */
    public Guest getCommenter() {
        return commenter;
    }

    /**
     * Sets the commenter of the comment
     *
     * @param commenter the Guest(commenter) to set
     */
    public void setCommenter(Guest commenter) {
        this.commenter = commenter;
    }

    /**
     * Returns the dwelling
     *
     * @return the dwelling to get
     */
    public DwellingBean getDwelling() {
        return dwelling;
    }

    /**
     * Sets the dwelling
     *
     * @param dwelling the dwelling to set
     */
    public void setDwelling(DwellingBean dwelling) {
        this.dwelling = dwelling;
    }

    /**
     * Returns the CommentBean id
     *
     * @return the CommentId to get
     */
    public CommentId getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment id
     *
     * @param commentId
     */
    public void setCommentId(CommentId commentId) {
        this.commentId = commentId;
    }

    /**
     * Integer representation for CommentBean instance.
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
     * Compares two CommentBean objects for equality. This method consider a CommentBean
 equal to another CommentBean if their id fields have the same value.
     *
     * @param object The other CommentBean object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentBean)) {
            return false;
        }
        CommentBean other = (CommentBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the CommentBean.
     *
     * @return The String representing the CommentBean.
     */
    @Override
    public String toString() {
        return "Comment{" + "id=" + id + '}';
    }

}
