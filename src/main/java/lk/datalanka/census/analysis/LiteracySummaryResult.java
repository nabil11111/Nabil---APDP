package lk.datalanka.census.analysis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Result DTO for literacy analysis.
 */
public final class LiteracySummaryResult {
    private final double nationalWeightedLiteracy;
    private final Map<String, Double> literacyByRegion;
    private final List<String> topRegions;
    private final List<String> bottomRegions;

    public LiteracySummaryResult(double nationalWeightedLiteracy,
            Map<String, Double> literacyByRegion,
            List<String> topRegions,
            List<String> bottomRegions) {
        this.nationalWeightedLiteracy = nationalWeightedLiteracy;
        this.literacyByRegion = Collections.unmodifiableMap(new LinkedHashMap<>(literacyByRegion));
        this.topRegions = List.copyOf(topRegions);
        this.bottomRegions = List.copyOf(bottomRegions);
    }

    public double getNationalWeightedLiteracy() {
        return nationalWeightedLiteracy;
    }

    public Map<String, Double> getLiteracyByRegion() {
        return literacyByRegion;
    }

    public List<String> getTopRegions() {
        return topRegions;
    }

    public List<String> getBottomRegions() {
        return bottomRegions;
    }
}
