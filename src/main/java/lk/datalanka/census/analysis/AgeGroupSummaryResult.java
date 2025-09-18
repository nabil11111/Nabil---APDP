package lk.datalanka.census.analysis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Result DTO for age group analysis.
 */
public final class AgeGroupSummaryResult {
    private final Map<String, Long> nationalTotalsByBand;
    private final Map<String, Map<String, Double>> regionPercentByBand; // region -> band -> %

    public AgeGroupSummaryResult(Map<String, Long> nationalTotalsByBand,
                                 Map<String, Map<String, Double>> regionPercentByBand) {
        this.nationalTotalsByBand = Collections.unmodifiableMap(new LinkedHashMap<>(nationalTotalsByBand));
        LinkedHashMap<String, Map<String, Double>> copy = new LinkedHashMap<>();
        regionPercentByBand.forEach((k, v) -> copy.put(k, Collections.unmodifiableMap(new LinkedHashMap<>(v))));
        this.regionPercentByBand = Collections.unmodifiableMap(copy);
    }

    public Map<String, Long> getNationalTotalsByBand() {
        return nationalTotalsByBand;
    }

    public Map<String, Map<String, Double>> getRegionPercentByBand() {
        return regionPercentByBand;
    }
}


