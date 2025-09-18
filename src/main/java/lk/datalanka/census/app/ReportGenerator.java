package lk.datalanka.census.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lk.datalanka.census.analysis.AgeGroupSummaryResult;
import lk.datalanka.census.analysis.IncomeSummaryResult;
import lk.datalanka.census.analysis.LiteracySummaryResult;
import lk.datalanka.census.domain.AbstractRegion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Renders reports to console and exports to CSV/JSON under build/out/.
 */
public final class ReportGenerator {

    private static final Path OUT_DIR = Path.of("build", "out");
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private final ObjectMapper mapper = new ObjectMapper();

    public void update(List<AbstractRegion> regions) {
        System.out.println("\nRegions Snapshot");
        System.out.println("-----------------");
        for (AbstractRegion r : regions) {
            System.out.printf("%-20s pop=%d literacy=%.2f%% income=Rs. %.2f%n",
                    r.getName(), r.getPopulationSize(), r.getLiteracyRate(), r.getAverageIncome());
        }

        // Export
        try {
            Files.createDirectories(OUT_DIR);
            String ts = LocalDateTime.now().format(TS);
            Path csv = OUT_DIR.resolve("regions_snapshot-" + ts + ".csv");
            try (CSVPrinter p = new CSVPrinter(Files.newBufferedWriter(csv, StandardCharsets.UTF_8),
                    CSVFormat.DEFAULT.withHeader("name", "population", "literacyRate", "averageIncome", "ageGroups"))) {
                for (AbstractRegion r : regions) {
                    String ageGroups = r.getAgeGroupDistribution().entrySet().stream()
                            .map(e -> e.getKey() + ":" + e.getValue())
                            .collect(Collectors.joining("|"));
                    p.printRecord(r.getName(), r.getPopulationSize(), r.getLiteracyRate(), r.getAverageIncome(),
                            ageGroups);
                }
            }

            Path json = OUT_DIR.resolve("regions_snapshot-" + ts + ".json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), regionsToSerializable(regions));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void render(AgeGroupSummaryResult r) {
        System.out.println("\nAge Group Summary");
        System.out.println("------------------");
        System.out.println("National totals: " + r.getNationalTotalsByBand());
        System.out.println("Region % by band: " + r.getRegionPercentByBand());
        exportJsonCsv("age_group_summary", r);
    }

    public void render(LiteracySummaryResult r) {
        System.out.println("\nLiteracy Summary");
        System.out.println("-----------------");
        System.out.printf("National weighted literacy: %.2f%%%n", r.getNationalWeightedLiteracy());
        System.out.println("By region: " + r.getLiteracyByRegion());
        System.out.println("Top regions: " + r.getTopRegions());
        System.out.println("Bottom regions: " + r.getBottomRegions());
        exportJsonCsv("literacy_summary", r);
    }

    public void render(IncomeSummaryResult r) {
        System.out.println("\nIncome Summary");
        System.out.println("---------------");
        System.out.printf("National weighted avg income: Rs. %.2f%n", r.getNationalWeightedAverageIncome());
        System.out.println("By region: " + r.getAverageIncomeByRegion());
        System.out.println("Ranked regions: " + r.getRankedRegions());
        exportJsonCsv("income_summary", r);
    }

    private void exportJsonCsv(String baseName, Object obj) {
        try {
            Files.createDirectories(OUT_DIR);
            String ts = LocalDateTime.now().format(TS);
            Path json = OUT_DIR.resolve(baseName + "-" + ts + ".json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), obj);

            // Generic CSV: flatten map-like structures if possible
            Path csv = OUT_DIR.resolve(baseName + "-" + ts + ".csv");
            try (CSVPrinter p = new CSVPrinter(Files.newBufferedWriter(csv, StandardCharsets.UTF_8),
                    CSVFormat.DEFAULT)) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, obj);
                p.printRecord("json_payload");
                p.printRecord(mapper.writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<Map<String, Object>> regionsToSerializable(List<AbstractRegion> regions) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AbstractRegion r : regions) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("name", r.getName());
            m.put("population", r.getPopulationSize());
            m.put("literacyRate", r.getLiteracyRate());
            m.put("averageIncome", r.getAverageIncome());
            m.put("ageGroups", r.getAgeGroupDistribution());
            list.add(m);
        }
        return list;
    }
}
