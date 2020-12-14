package modules.exceptions;

/**
 * A Exception thrown when a new User is being created with a username
 * that already belongs to another User
 */
public class NonUniqueUsernameException extends RuntimeException {
}
