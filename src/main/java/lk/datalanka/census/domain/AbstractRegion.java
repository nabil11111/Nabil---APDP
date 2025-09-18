package lk.datalanka.census.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import lk.datalanka.census.util.Preconditions;

/**
 * Immutable base type for a geographical region in the population analytics domain.
 * Instances are immutable: all fields are {@code private final} and exposed via accessors only.
 */
public abstract class AbstractRegion {

    private final String name;
    private final int population;
    private final double literacyRate; // percentage [0,100]
    private final double averageIncome; // monthly Rs.
    private final Map<String, Integer> ageGroups; // label -> count

    /**
     * Creates a region after validating domain invariants.
     *
     * @param name           non-null, non-blank region name
     * @param population     population size (>= 0)
     * @param literacyRate   literacy rate in percent within [0, 100]
     * @param averageIncome  average monthly income in Rs. (>= 0)
     * @param ageGroups      non-null map of age band label to count; sum(values) <= population
     */
    protected AbstractRegion(String name,
                             int population,
                             double literacyRate,
                             double averageIncome,
                             Map<String, Integer> ageGroups) {
        Preconditions.check(name != null && !name.isBlank(), "name must be provided");
        Preconditions.check(population >= 0, "population must be >= 0");
        Preconditions.check(literacyRate >= 0.0 && literacyRate <= 100.0,
                "literacyRate must be within [0,100]");
        Preconditions.check(averageIncome >= 0.0, "averageIncome must be >= 0");
        Preconditions.check(ageGroups != null, "ageGroups must not be null");

        long totalAges = ageGroups.values().stream()
                .mapToLong(Integer::intValue)
                .sum();
        Preconditions.check(totalAges <= (long) population,
                "sum(ageGroups) must be <= population");

        this.name = name.trim();
        this.population = population;
        this.literacyRate = literacyRate;
        this.averageIncome = averageIncome;
        this.ageGroups = Map.copyOf(ageGroups);
    }

    public final String getName() {
        return name;
    }

    public final int getPopulationSize() {
        return population;
    }

    public final double getLiteracyRate() {
        return literacyRate;
    }

    public final double getAverageIncome() {
        return averageIncome;
    }

    /**
     * Returns an unmodifiable view of the age group map.
     */
    public final Map<String, Integer> getAgeGroupDistribution() {
        return Collections.unmodifiableMap(ageGroups);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRegion that = (AbstractRegion) o;
        return population == that.population
                && Double.compare(that.literacyRate, literacyRate) == 0
                && Double.compare(that.averageIncome, averageIncome) == 0
                && name.equals(that.name)
                && ageGroups.equals(that.ageGroups);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name, population, literacyRate, averageIncome, ageGroups);
    }

    @Override
    public String toString() {
        return "AbstractRegion{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", literacyRate=" + literacyRate +
                ", averageIncome=" + averageIncome +
                ", ageGroups=" + ageGroups +
                '}';
    }
}


