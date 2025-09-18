package lk.datalanka.census.analysis;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.util.Formatting;

/**
 * Computes population-weighted literacy and rankings.
 */
public final class LiteracyAnalysis implements AnalysisStrategy {

    private LiteracySummaryResult result;

    @Override
    public void analyze(List<AbstractRegion> regions) {
        double totalWeighted = 0.0;
        long totalPop = 0L;

        Map<String, Double> literacyByRegion = new LinkedHashMap<>();
        for (AbstractRegion r : regions) {
            literacyByRegion.put(r.getName(), Formatting.scale(r.getLiteracyRate(), 2));
            if (r.getPopulationSize() > 0) {
                totalWeighted += r.getLiteracyRate() * r.getPopulationSize();
                totalPop += r.getPopulationSize();
            }
        }

        double national = totalPop > 0 ? Formatting.scale(totalWeighted / totalPop, 2) : 0.0;

        List<String> ranked = literacyByRegion.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<String> top = ranked.stream().limit(5).collect(Collectors.toList());
        List<String> bottom = ranked.stream().skip(Math.max(0, ranked.size() - 5)).collect(Collectors.toList());

        this.result = new LiteracySummaryResult(national, literacyByRegion, top, bottom);
    }

    public LiteracySummaryResult getResult() {
        return result;
    }
}
