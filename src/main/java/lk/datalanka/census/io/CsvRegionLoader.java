package lk.datalanka.census.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.datalanka.census.domain.AbstractRegion;
import lk.datalanka.census.factory.RegionFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Loads regions from a CSV file with columns:
 * type,name,population,literacyRate,averageIncome,
 * age_0_14,age_15_24,age_25_34,age_35_44,age_45_64,age_65_plus.
 */
public final class CsvRegionLoader {

    private CsvRegionLoader() {
    }

    public static List<AbstractRegion> load(Path csv) throws IOException {
        List<AbstractRegion> regions = new ArrayList<>();
        RegionFactory factory = new RegionFactory();

        try (Reader reader = Files.newBufferedReader(csv);
                CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            for (CSVRecord r : parser) {
                String type = r.get("type");
                String name = r.get("name");
                int population = Integer.parseInt(r.get("population"));
                double literacy = Double.parseDouble(r.get("literacyRate"));
                double income = Double.parseDouble(r.get("averageIncome"));
                Map<String, Integer> ag = new HashMap<>();
                ag.put("0-14", Integer.parseInt(r.get("age_0_14")));
                ag.put("15-24", Integer.parseInt(r.get("age_15_24")));
                ag.put("25-34", Integer.parseInt(r.get("age_25_34")));
                ag.put("35-44", Integer.parseInt(r.get("age_35_44")));
                ag.put("45-64", Integer.parseInt(r.get("age_45_64")));
                ag.put("65+", Integer.parseInt(r.get("age_65_plus")));
                regions.add(factory.createRegion(type, name, population, literacy, income, ag));
            }
        }
        return regions;
    }
}
