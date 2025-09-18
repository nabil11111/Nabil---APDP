package lk.datalanka.census.analysis;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.util.Formatting;

/**
 * Computes population-weighted average income nationally and by-region ranking.
 */
public final class IncomeAnalysis implements AnalysisStrategy {

    private IncomeSummaryResult result;

    @Override
    public void analyze(List<AbstractRegion> regions) {
        double weightedSum = 0.0;
        long totalPop = 0L;

        Map<String, Double> incomeByRegion = new LinkedHashMap<>();
        for (AbstractRegion r : regions) {
            incomeByRegion.put(r.getName(), Formatting.scale(r.getAverageIncome(), 2));
            if (r.getPopulationSize() > 0) {
                weightedSum += r.getAverageIncome() * r.getPopulationSize();
                totalPop += r.getPopulationSize();
            }
        }

        double national = totalPop > 0 ? Formatting.scale(weightedSum / totalPop, 2) : 0.0;

        List<String> ranked = incomeByRegion.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        this.result = new IncomeSummaryResult(national, incomeByRegion, ranked);
    }

    public IncomeSummaryResult getResult() {
        return result;
    }
}


