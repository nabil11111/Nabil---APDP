package lk.datalanka.census.analysis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.domain.UrbanRegion;
import org.junit.jupiter.api.Test;

class LiteracyAnalysisTest {

    @Test
    void weightedLiteracyAndBounds() {
        List<AbstractRegion> regions = new ArrayList<>();
        regions.add(new UrbanRegion("A", 100, 60, 0, ag(10,10,10,10,10,50)));
        regions.add(new UrbanRegion("B", 300, 90, 0, ag(10,10,10,10,10,250)));

        LiteracyAnalysis a = new LiteracyAnalysis();
        a.analyze(regions);
        LiteracySummaryResult r = a.getResult();

        double expected = (60*100 + 90*300) / 400.0;
        assertThat(r.getNationalWeightedLiteracy()).isEqualTo(Math.round(expected*100.0)/100.0);
        double min = regions.stream().mapToDouble(AbstractRegion::getLiteracyRate).min().orElse(0);
        double max = regions.stream().mapToDouble(AbstractRegion::getLiteracyRate).max().orElse(0);
        assertThat(r.getNationalWeightedLiteracy()).isBetween(min, max);
    }

    @Test
    void handlesZeroPopulation() {
        List<AbstractRegion> regions = new ArrayList<>();
        regions.add(new UrbanRegion("A", 0, 100, 0, ag(0,0,0,0,0,0)));
        LiteracyAnalysis a = new LiteracyAnalysis();
        a.analyze(regions);
        assertThat(a.getResult().getNationalWeightedLiteracy()).isEqualTo(0.0);
    }

    private Map<String, Integer> ag(int a, int b, int c, int d, int e, int f) {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("0-14", a); m.put("15-24", b); m.put("25-34", c);
        m.put("35-44", d); m.put("45-64", e); m.put("65+", f);
        return m;
    }
}


