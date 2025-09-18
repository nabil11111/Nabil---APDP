package lk.datalanka.census.util;

/**
 * Lightweight preconditions utility to enforce method argument and state contracts.
 * This class is intentionally minimal to keep dependencies lean.
 */
public final class Preconditions {

    private Preconditions() {
        // Utility class
    }

    /**
     * Asserts that the provided condition is true.
     *
     * @param condition boolean condition to assert
     * @param message   message to include in the thrown exception if the condition is false
     * @throws IllegalArgumentException when the condition is false
     */
    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Asserts that the provided state condition is true.
     *
     * @param condition boolean condition to assert
     * @param message   message to include in the thrown exception if the condition is false
     * @throws IllegalStateException when the condition is false
     */
    public static void checkState(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}


