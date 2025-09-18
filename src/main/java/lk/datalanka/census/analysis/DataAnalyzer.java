package lk.datalanka.census.analysis;

import java.util.List;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.util.Preconditions;

/**
 * Façade for executing analyses via a configured strategy.
 */
public final class DataAnalyzer {

    private AnalysisStrategy strategy;

    /**
     * Injects the analysis strategy to use.
     */
    public void setStrategy(AnalysisStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Executes the configured strategy on the provided regions.
     *
     * @param regions list of regions to analyze
     * @throws IllegalStateException if a strategy has not been set
     */
    public void executeAnalysis(List<AbstractRegion> regions) {
        Preconditions.checkState(strategy != null, "Analysis strategy not set");
        strategy.analyze(regions);
    }
}


