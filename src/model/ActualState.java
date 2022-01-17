package model;

/**
 * Enumeration used to know the Guest's possible professional states
 *
 * @author Yeray Sampedro
 */
public enum ActualState {

    /**
     * The person is currently working
     */
    WORKER,
    /**
     * The person is currently studying
     */
    STUDENT,
    /**
     * The person is currently working and studying
     */
    BOTH,
    /**
     * The person is currently unemployed and is not studying
     */
    UNEMPLOYED
}
