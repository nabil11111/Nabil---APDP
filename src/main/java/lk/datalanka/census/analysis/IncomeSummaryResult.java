package lk.datalanka.census.analysis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Result DTO for income analysis.
 */
public final class IncomeSummaryResult {
    private final double nationalWeightedAverageIncome;
    private final Map<String, Double> averageIncomeByRegion;
    private final List<String> rankedRegions;

    public IncomeSummaryResult(double nationalWeightedAverageIncome,
            Map<String, Double> averageIncomeByRegion,
            List<String> rankedRegions) {
        this.nationalWeightedAverageIncome = nationalWeightedAverageIncome;
        this.averageIncomeByRegion = Collections.unmodifiableMap(new LinkedHashMap<>(averageIncomeByRegion));
        this.rankedRegions = List.copyOf(rankedRegions);
    }

    public double getNationalWeightedAverageIncome() {
        return nationalWeightedAverageIncome;
    }

    public Map<String, Double> getAverageIncomeByRegion() {
        return averageIncomeByRegion;
    }

    public List<String> getRankedRegions() {
        return rankedRegions;
    }
}
