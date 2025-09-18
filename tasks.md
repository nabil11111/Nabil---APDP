Population Analytics – Master Task Plan

Legend: [ ] = pending, [x] = done, [~] = in progress

0. Planning and repo bootstrap
   - [~] 0.1 Create this tasks.md master plan and initialize Git repository

1. Project setup
   - [ ] 1.1 Scaffold Maven project (JDK 17, group/artifact, jar packaging)
   - [ ] 1.2 Add dependencies (JUnit 5, Mockito, AssertJ, jqwik, Jackson, Commons CSV)
   - [ ] 1.3 Configure plugins (Surefire, Failsafe, JaCoCo, Checkstyle, SpotBugs, PMD)
   - [ ] 1.4 Repo hygiene: .editorconfig, .gitignore, README.md, LICENSE

2. Codebase structure
   - [ ] 2.1 Create packages under `lk.datalanka.census.*` and placeholder directories

3. Domain model
   - [ ] 3.1 Implement immutable `AbstractRegion` with validations and overrides
   - [ ] 3.2 Implement final `UrbanRegion` and `RuralRegion`
   - [ ] 3.3 Implement `RegionFactory` with type routing and input checks

4. Utilities
   - [ ] 4.1 Implement `AgeBands` helper with ordered bands and `of(int)`
   - [ ] 4.2 Implement `Formatting` (percent to 2dp, rounding helpers)
   - [ ] 4.3 Implement `Preconditions` (check, checkState)

5. Analysis framework
   - [ ] 5.1 Define `AnalysisStrategy` interface
   - [ ] 5.2 Implement `DataAnalyzer` façade with strategy injection

6. Analyses & DTOs
   - [ ] 6.1 Implement `AgeGroupAnalysis` and `AgeGroupSummaryResult`
   - [ ] 6.2 Implement `LiteracyAnalysis` and `LiteracySummaryResult`
   - [ ] 6.3 Implement `IncomeAnalysis` and `IncomeSummaryResult`

7. Reporting & app
   - [ ] 7.1 Implement `ReportGenerator` (console + CSV/JSON exports to target/out)
   - [ ] 7.2 Implement `AppController` (tiny coordinator)
   - [ ] 7.3 Implement interactive `Main` menu and wiring

8. IO
   - [ ] 8.1 Implement optional `CsvRegionLoader.load(Path)`

9. Tests (JUnit 5)
   - [ ] 9.1 `RegionFactoryTest` – subtype selection and input validation
   - [ ] 9.2 `AbstractRegionInvariantTest` – immutability and equality
   - [ ] 9.3 `AgeGroupAnalysisTest` – totals and percentages determinism
   - [ ] 9.4 `LiteracyAnalysisTest` – weighted formula, bounds, zero-pop handling

10. Automation polish
   - [ ] 10.1 Verify Maven lifecycle (`mvn -q -DskipTests=false verify`) and update README

Conventions
- Conventional Commits for every logical step
- Keep tasks atomic; split when needed
- Always update this file with progress checkmarks


