package lk.datalanka.census.util;

/**
 * Common age band labels used throughout analyses.
 */
public final class AgeBands {
    public static final String BAND_0_14 = "0-14";
    public static final String BAND_15_24 = "15-24";
    public static final String BAND_25_34 = "25-34";
    public static final String BAND_35_44 = "35-44";
    public static final String BAND_45_64 = "45-64";
    public static final String BAND_65_PLUS = "65+";

    private AgeBands() {
    }

    /**
     * Maps an age to its band label.
     *
     * @param age non-negative age in years
     * @return band label
     */
    public static String of(int age) {
        Preconditions.check(age >= 0, "age must be >= 0");
        if (age <= 14)
            return BAND_0_14;
        if (age <= 24)
            return BAND_15_24;
        if (age <= 34)
            return BAND_25_34;
        if (age <= 44)
            return BAND_35_44;
        if (age <= 64)
            return BAND_45_64;
        return BAND_65_PLUS;
    }
}
