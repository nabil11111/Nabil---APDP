package lk.datalanka.census.analysis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.util.Formatting;

/**
 * Analyzes age band totals nationally and percentages per region.
 */
public final class AgeGroupAnalysis implements AnalysisStrategy {

    private AgeGroupSummaryResult result;

    @Override
    public void analyze(List<AbstractRegion> regions) {
        // Maintain insertion order for predictable output
        Map<String, Long> national = new LinkedHashMap<>();

        // Aggregate national totals by band
        for (AbstractRegion region : regions) {
            for (Map.Entry<String, Integer> e : region.getAgeGroupDistribution().entrySet()) {
                national.merge(e.getKey(), e.getValue().longValue(), Long::sum);
            }
        }

        // Percentages per region (region -> band -> % w/ 2dp)
        Map<String, Map<String, Double>> regionPercents = new LinkedHashMap<>();
        for (AbstractRegion region : regions) {
            int pop = region.getPopulationSize();
            Map<String, Double> bandToPercent = new TreeMap<>();
            if (pop > 0) {
                for (Map.Entry<String, Integer> e : region.getAgeGroupDistribution().entrySet()) {
                    double pct = (e.getValue() * 100.0) / pop;
                    bandToPercent.put(e.getKey(), Formatting.scale(pct, 2));
                }
            } else {
                // zero-population region -> all 0%
                for (String band : region.getAgeGroupDistribution().keySet()) {
                    bandToPercent.put(band, 0.0);
                }
            }
            // keep a deterministic ordering by band name
            Map<String, Double> ordered = bandToPercent.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (a, b) -> a, LinkedHashMap::new));
            regionPercents.put(region.getName(), ordered);
        }

        this.result = new AgeGroupSummaryResult(national, regionPercents);
    }

    public AgeGroupSummaryResult getResult() {
        return result;
    }
}


