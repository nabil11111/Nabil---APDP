package lk.datalanka.census.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AbstractRegionInvariantTest {

    @Test
    void ageGroupMapIsUnmodifiable() {
        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", 10);
        AbstractRegion r = new UrbanRegion("X", 10, 50, 0, ag);
        assertThatThrownBy(() -> r.getAgeGroupDistribution().put("15-24", 1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void equalsAndHashCodeConsistent() {
        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", 10);
        AbstractRegion a = new UrbanRegion("X", 10, 50, 0, ag);
        AbstractRegion b = new UrbanRegion("X", 10, 50, 0, ag);
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    void rejectsInvalidConstructorArgs() {
        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", 10);
        assertThatThrownBy(() -> new UrbanRegion("", 10, 50, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UrbanRegion("X", -1, 50, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UrbanRegion("X", 1, -0.1, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UrbanRegion("X", 1, 100.1, 0, ag))
                .isInstanceOf(IllegalArgumentException.class);
        Map<String, Integer> tooBig = new HashMap<>();
        tooBig.put("0-14", 100);
        assertThatThrownBy(() -> new UrbanRegion("X", 10, 50, 0, tooBig))
                .isInstanceOf(IllegalArgumentException.class);
    }
}


