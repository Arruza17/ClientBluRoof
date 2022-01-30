package model;

/**
 * Enumeration used to know the type of user
 *
 * @author Yeray Sampedro
 */
public enum UserPrivilege {

    /**
     * The user is an Administrator
     */
    ADMIN,

    /**
     * The user is a host
     */
    HOST,

    /**
     * The guest that is going to comment a dwelling
     */
    GUEST
}
