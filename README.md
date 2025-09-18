## Population Analytics (Console)

Production-quality Java 17 console application for demographic analytics aligned with a strict class diagram: factory-created regions, strategy-based analyses, and a reporting façade.

### Requirements
- JDK 17+
- Maven 3.9+

### Build & Test
```bash
mvn -q clean verify
```

### Run
```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=lk.datalanka.census.app.Main
```

Alternatively, build a jar and run:
```bash
mvn -q -DskipTests package
java -jar target/population-analytics-1.0.0-SNAPSHOT.jar
```

### Project structure
- `lk.datalanka.census.app` – `Main`, `AppController`, `ReportGenerator`
- `lk.datalanka.census.analysis` – strategies and result DTOs
- `lk.datalanka.census.domain` – region model (`AbstractRegion`, `UrbanRegion`, `RuralRegion`)
- `lk.datalanka.census.factory` – `RegionFactory`
- `lk.datalanka.census.io` – `CsvRegionLoader`
- `lk.datalanka.census.util` – `AgeBands`, `Formatting`, `Preconditions`



