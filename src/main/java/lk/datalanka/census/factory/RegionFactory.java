package lk.datalanka.census.factory;

import java.util.Map;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.domain.RuralRegion;
import lk.datalanka.census.domain.UrbanRegion;
import lk.datalanka.census.util.Preconditions;

/**
 * Factory for creating region instances.
 */
public final class RegionFactory {

    /**
     * Creates a region instance based on the provided type.
     *
     * @param type          region type: "URBAN" or "RURAL" (case-insensitive)
     * @param name          region name
     * @param population    population size
     * @param literacyRate  literacy rate in percent [0,100]
     * @param averageIncome average monthly income in Rs.
     * @param ageGroups     age band label to count map
     * @return created region
     */
    public AbstractRegion createRegion(String type,
                                       String name,
                                       int population,
                                       double literacyRate,
                                       double averageIncome,
                                       Map<String, Integer> ageGroups) {
        Preconditions.check(type != null && !type.isBlank(), "type must be provided");
        String normalized = type.trim().toUpperCase();
        switch (normalized) {
            case "URBAN":
                return new UrbanRegion(name, population, literacyRate, averageIncome, ageGroups);
            case "RURAL":
                return new RuralRegion(name, population, literacyRate, averageIncome, ageGroups);
            default:
                throw new IllegalArgumentException("Unknown region type: " + type);
        }
    }
}


