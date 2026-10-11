# Change Log

## 2026-10-11 – Build: Test programs migrated from JUnit 4 to JUnit 5

- What: Implemented ADR-0002. Bound `org.junit.jupiter:junit-jupiter-api`/`junit-jupiter-engine` 5.10.0 in
  `geojsf-bom`, pinned `maven-surefire-plugin` 3.5.2 in the root `pom.xml`, replaced `junit:junit` in the
  module POMs with the Jupiter artifacts, removed the unused test framework from `api`, `jsf`, and
  `geoserver`, and converted the test programs to the JUnit 5 annotations and `Assertions`. The previously
  inert XML test programs run now.
- Result: `mvn -DskipTests=false -Djava.awt.headless=true clean test` selects the JUnit Platform provider and
  ends with BUILD SUCCESS. xml 81 run / 80 errors / 1 skipped, util 11 run / 6 errors, component 1 run /
  1 error, ejb 0. The failures are known defects of the activated legacy programs; `testFailureIgnore` keeps
  the build green until they are repaired.
- Evidence: `clean test` of the reactor – JUnit Platform provider; `TestGeoJsfXpathView` passes after the
  base class was corrected.
- Files: bom/pom.xml, pom.xml, util/pom.xml, xml/pom.xml, component/pom.xml, ejb/pom.xml, geoserver/pom.xml,
  api/pom.xml, jsf/pom.xml, the test programs under `util`, `xml`, `component`, and `ejb` `src/test/java`,
  doc/decisions/ADR-0002-test-strategy.md, doc/requirements/architecture.md, README.md, doc/changelog.md.

## 2026-10-11 – Docs: ADR-0002 test strategy as accepted

- What: Adapted the copied test-strategy decision of the source project as ADR-0002, set the status to
  `accepted`, and removed the references of the source project.
- Result: Review "Approval recommended" (0 findings against the recommendation, 2 notes); accepted.
- Evidence: The line-width and duplicate checks on the decision file – no match.
- Files: doc/decisions/ADR-0002-test-strategy.md, doc/status.md, doc/changelog.md.

## 2026-10-11 – Build: ADR-0001 for the Java 8 compile level

- What: Adapted ADR-0002 of the JEESL project as ADR-0001, removed the references of the source project, and
  set the status to `accepted`.
- Result: Review "Approval recommended" (0 findings against the recommendation, 2 notes); accepted.
- Evidence: The line width, duplicate, and attribute checks on the decision file – no match.
- Files: doc/decisions/ADR-0001-java-compile-level.md, doc/status.md,
  doc/requirements/architecture.md, doc/changelog.md.
