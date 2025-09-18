package lk.datalanka.census.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Formatting helpers.
 */
public final class Formatting {

    private Formatting() {
    }

    /**
     * Formats a fractional value as percentage string with two decimals.
     *
     * @param value value in [0,100]
     * @return e.g., "12.34%"
     */
    public static String percent(double value) {
        return scale(value, 2) + "%";
    }

    /**
     * Scales a double to given decimal places using HALF_UP.
     */
    public static double scale(double value, int places) {
        return BigDecimal.valueOf(value)
                .setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
