package Modules.Exceptions;

/**
 * A Exception thrown when a new instance of a class with a unique ID variable is attempted
 * to be created with a ID that already belongs to another instance of the class
 */
public class NonUniqueIdException extends RuntimeException{
}
