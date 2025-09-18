package lk.datalanka.census.domain;

import java.util.Map;

/**
 * Rural region implementation. Final to preserve immutability guarantees.
 */
public final class RuralRegion extends AbstractRegion {

    public RuralRegion(String name,
                       int population,
                       double literacyRate,
                       double averageIncome,
                       Map<String, Integer> ageGroups) {
        super(name, population, literacyRate, averageIncome, ageGroups);
    }
}


