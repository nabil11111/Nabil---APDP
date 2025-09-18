package lk.datalanka.census.analysis;

import java.util.List;

import lk.datalanka.census.domain.AbstractRegion;

/**
 * Strategy for running a specific analysis over a list of regions.
 */
public interface AnalysisStrategy {

    /**
     * Performs analysis over the given regions.
     *
     * @param regions list of regions (non-null); an empty list is permitted
     */
    void analyze(List<AbstractRegion> regions);
}


