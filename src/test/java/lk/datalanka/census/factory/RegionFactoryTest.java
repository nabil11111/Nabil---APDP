package lk.datalanka.census.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.domain.RuralRegion;
import lk.datalanka.census.domain.UrbanRegion;
import org.junit.jupiter.api.Test;

class RegionFactoryTest {

    @Test
    void createsCorrectSubtype() {
        RegionFactory f = new RegionFactory();
        Map<String, Integer> ag = sampleAg();
        AbstractRegion urban = f.createRegion("URBAN", "City", 100, 90, 1000, ag);
        AbstractRegion rural = f.createRegion("RURAL", "Village", 100, 85, 800, ag);
        assertThat(urban).isInstanceOf(UrbanRegion.class);
        assertThat(rural).isInstanceOf(RuralRegion.class);
    }

    @Test
    void rejectsInvalidInputs() {
        RegionFactory f = new RegionFactory();
        Map<String, Integer> ag = sampleAg();
        assertThatThrownBy(() -> f.createRegion("URBAN", "Bad", -1, 10, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> f.createRegion("URBAN", "Bad", 1, 101, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validatesAgeGroupsSum() {
        RegionFactory f = new RegionFactory();
        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", 90);
        assertThatThrownBy(() -> f.createRegion("URBAN", "X", 50, 50, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Map<String, Integer> sampleAg() {
        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", 10);
        return ag;
    }
}
