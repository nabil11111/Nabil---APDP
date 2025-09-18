package lk.datalanka.census.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.datalanka.census.analysis.AgeGroupAnalysis;
import lk.datalanka.census.analysis.DataAnalyzer;
import lk.datalanka.census.analysis.IncomeAnalysis;
import lk.datalanka.census.analysis.LiteracyAnalysis;
import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.factory.RegionFactory;

/**
 * Coordinates regions, analyses, and reporting.
 */
public final class AppController {

    private final List<AbstractRegion> regions = new ArrayList<>();
    private final RegionFactory regionFactory = new RegionFactory();
    private final DataAnalyzer analyzer = new DataAnalyzer();
    private final ReportGenerator reportGenerator = new ReportGenerator();

    public List<AbstractRegion> getRegions() {
        return regions;
    }

    public void addRegion(String type, String name, int population, double literacy, double income, Map<String, Integer> ageGroups) {
        regions.add(regionFactory.createRegion(type, name, population, literacy, income, ageGroups));
    }

    public void addSampleData() {
        Map<String, Integer> a = new HashMap<>();
        a.put("0-14", 120000);
        a.put("15-24", 80000);
        a.put("25-34", 100000);
        a.put("35-44", 90000);
        a.put("45-64", 70000);
        a.put("65+", 30000);
        addRegion("URBAN", "Colombo", 490000, 92.5, 85000.0, a);

        Map<String, Integer> b = new HashMap<>();
        b.put("0-14", 50000);
        b.put("15-24", 40000);
        b.put("25-34", 55000);
        b.put("35-44", 45000);
        b.put("45-64", 35000);
        b.put("65+", 15000);
        addRegion("RURAL", "Matara", 245000, 88.0, 54000.0, b);
    }

    public void printAllRegions() {
        reportGenerator.update(regions);
    }

    public void runAgeGroupAnalysis() {
        AgeGroupAnalysis strategy = new AgeGroupAnalysis();
        analyzer.setStrategy(strategy);
        analyzer.executeAnalysis(regions);
        reportGenerator.render(strategy.getResult());
    }

    public void runLiteracyAnalysis() {
        LiteracyAnalysis strategy = new LiteracyAnalysis();
        analyzer.setStrategy(strategy);
        analyzer.executeAnalysis(regions);
        reportGenerator.render(strategy.getResult());
    }

    public void runIncomeAnalysis() {
        IncomeAnalysis strategy = new IncomeAnalysis();
        analyzer.setStrategy(strategy);
        analyzer.executeAnalysis(regions);
        reportGenerator.render(strategy.getResult());
    }

    public void exportAllReports() {
        printAllRegions();
        runAgeGroupAnalysis();
        runLiteracyAnalysis();
        runIncomeAnalysis();
    }
}


