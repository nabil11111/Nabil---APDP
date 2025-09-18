package lk.datalanka.census.domain;

import java.util.Map;

/**
 * Urban region implementation. Final to preserve immutability guarantees.
 */
public final class UrbanRegion extends AbstractRegion {

    public UrbanRegion(String name,
            int population,
            double literacyRate,
            double averageIncome,
            Map<String, Integer> ageGroups) {
        super(name, population, literacyRate, averageIncome, ageGroups);
    }
}
