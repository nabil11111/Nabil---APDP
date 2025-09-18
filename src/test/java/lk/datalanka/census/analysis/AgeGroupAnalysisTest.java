package lk.datalanka.census.analysis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.domain.UrbanRegion;
import org.junit.jupiter.api.Test;

class AgeGroupAnalysisTest {

    @Test
    void totalsAndPercentages() {
        List<AbstractRegion> regions = new ArrayList<>();
        regions.add(new UrbanRegion("A", 100, 80, 1000, ag(10, 20, 20, 20, 20, 10)));
        regions.add(new UrbanRegion("B", 200, 90, 1000, ag(20, 30, 40, 40, 50, 20)));

        AgeGroupAnalysis a = new AgeGroupAnalysis();
        a.analyze(regions);
        AgeGroupSummaryResult r = a.getResult();

        assertThat(r.getNationalTotalsByBand().get("0-14")).isEqualTo(30);
        assertThat(r.getRegionPercentByBand().get("A").values().stream().mapToDouble(Double::doubleValue).sum())
                .isLessThanOrEqualTo(100.0);
    }

    @Test
    void deterministicUnderShuffle() {
        List<AbstractRegion> regions = new ArrayList<>();
        regions.add(new UrbanRegion("A", 100, 80, 1000, ag(10, 20, 20, 20, 20, 10)));
        regions.add(new UrbanRegion("B", 200, 90, 1000, ag(20, 30, 40, 40, 50, 20)));

        AgeGroupAnalysis a1 = new AgeGroupAnalysis();
        a1.analyze(regions);
        AgeGroupSummaryResult r1 = a1.getResult();

        // shuffle copy
        List<AbstractRegion> shuffled = new ArrayList<>(regions);
        java.util.Collections.shuffle(shuffled, new Random(42));
        AgeGroupAnalysis a2 = new AgeGroupAnalysis();
        a2.analyze(shuffled);
        AgeGroupSummaryResult r2 = a2.getResult();

        assertThat(r2.getNationalTotalsByBand()).isEqualTo(r1.getNationalTotalsByBand());
    }

    private Map<String, Integer> ag(int a, int b, int c, int d, int e, int f) {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("0-14", a); m.put("15-24", b); m.put("25-34", c);
        m.put("35-44", d); m.put("45-64", e); m.put("65+", f);
        return m;
    }
}


