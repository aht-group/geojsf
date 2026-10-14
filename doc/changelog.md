# Change Log

## 2026-10-14 – Build: JAXB variants as classifier artifacts (ADR-0004)

- What: Implemented ADR-0004. The `xml` module now uses `pom` packaging and builds both JAXB variants in one
  Maven run: `compile-javax` (`release` `8`) compiles `src/main/java` plus `src/main/javax` into
  `target/classes-javax` and `compile-jakarta` (`release` `11`) compiles `src/main/java` plus
  `src/main/jakarta` into `target/classes-jakarta`; `jar-javax` and `jar-jakarta` attach the classifier jars
  at `process-classes`. The JAXB model sources moved to the two variant roots (identical except the
  annotation package); the variant-independent sources stay in `src/main/java`. A `jakarta.xjb` and a
  `jakarta` profile were added for regeneration, `bom` manages both classifiers, and the consumers (`util`,
  `component`, `jsf`, `api`, `doc`) name the classifier `javax`; no artifact without a classifier exists. The
  module binds a JDK 11 toolchain (`maven-toolchains-plugin`), so the `jakarta` variant builds although the
  JDK that runs Maven may be older. Both profiles regenerate with a forked `cxf-xjc-plugin` pinned to the
  English locale, so the generated Javadoc is English and not the machine locale.
- Result: The module publishes exactly the POM and the `javax`, `jakarta`, and `tests` jars; the reactor
  builds and all test programs pass.
- Evidence: `mvn -pl xml -DskipTests -Djava.awt.headless=true clean install` – the local repository holds
  no jar without a classifier; `javap -v` – class-file version 52 for `javax` and 55 for `jakarta`;
  `mvn -DskipTests=false -Djava.awt.headless=true clean verify` – `xml` 81, `util` 14, `component` 1.
- Files: xml/pom.xml, xml/src/main/java, xml/src/main/javax, xml/src/main/jakarta,
  xml/src/main/xsd/jakarta.xjb, bom/pom.xml, util/pom.xml, component/pom.xml, jsf/pom.xml, api/pom.xml,
  doc/pom.xml, README.md, doc/decisions/ADR-0004-package-javax-jakarta-variants.md, doc/changelog.md.

## 2026-10-13 – Docs: ADR-0004 JAXB variants accepted

- What: Set ADR-0004 to `accepted` on the instruction of the responsible person; the decision is binding and
  its implementation may be based on it.
- Result: The review had recommended approval (0 findings against the recommendation, 2 notes); the notes
  were accepted.
- Evidence: The index (`doc/status.md`) lists ADR-0004 with the status `accepted`.
- Files: doc/decisions/ADR-0004-package-javax-jakarta-variants.md, doc/status.md, doc/changelog.md.

## 2026-10-13 – Docs: ADR-0004 JAXB variants prepared as proposed

- What: Adapted the copied JAXB-variant decision as ADR-0004 (`proposed`), removed the source-project
  references, related it to ADR-0003, and added the cross-references to ADR-0001, ADR-0002, and ADR-0003.
- Result: Review "Approval recommended" (0 findings against the recommendation, 2 notes).
- Evidence: The line-width, duplicate, display-location, meta-text, and check-run patterns – no match.
- Files: doc/decisions/ADR-0004-package-javax-jakarta-variants.md,
  doc/decisions/ADR-0001-java-compile-level.md, doc/decisions/ADR-0002-test-strategy.md,
  doc/decisions/ADR-0003-maven-build.md, doc/status.md, doc/changelog.md.

## 2026-10-12 – Build: Compiler compile level migrated to `release` 8 (ADR-0001)

- What: Implemented ADR-0001. The root `pom.xml` now manages `maven-compiler-plugin` in version 3.13.0 and
  configures the parameter `release` with the value `8` in place of `source` and `target` `1.8`; the build
  prerequisite rose to Maven 3.6.3 (required by the plugin), which the root `prerequisites`, `README.md`, and
  ADR-0003 follow. The redundant `<inherited>true</inherited>` element of the plugin was removed because `true`
  is its default.
- Result: `JAVA_HOME=<JDK 11> mvn -DskipTests clean install` ends with BUILD SUCCESS; the class files carry
  class-file version 52. The build must run on JDK 9 or newer because `--release` exists from JDK 9.
- Evidence: `mvn -q -DskipTests clean install` on JDK 11 – BUILD SUCCESS; `javap -v` – major version 52.
- Files: pom.xml, README.md, doc/decisions/ADR-0001-java-compile-level.md,
  doc/decisions/ADR-0003-maven-build.md, doc/changelog.md.

## 2026-10-11 – Docs: ADR-0003 Maven build tool as accepted

- What: Adapted the copied Maven-build decision as ADR-0003 (translated), replaced its source-project references
  with those of GeoJSF, and accepted it.
- Result: Review "Approval recommended" (0 findings against the recommendation, 3 notes); the notes were answered.
- Evidence: The line-width, duplicate, display-location, and meta-text checks – no match.
- Files: doc/decisions/ADR-0003-maven-build.md, doc/status.md, doc/requirements/architecture.md, doc/changelog.md.

## 2026-10-11 – Build: Repair the JUnit 5 test programs

- What: Repaired the test programs that the JUnit 5 migration activated. `GeoJsfNsPrefixMapper` now extends
  `com.sun.xml.bind.marshaller.NamespacePrefixMapper`, so the `instanceof` check in `JaxbUtil` no longer tries
  to load the absent `org.glassfish.jaxb.runtime` class; `AbstractGeoJsfUtilTest` initialises logging through
  `LoggerBootstrap` instead of the log4j1-based `LoggerInit`; `TestJavaScript` no longer evaluates the NYI
  `dmMap` script; the stale reference XML `Size.xml` and `SvgParameter.xml` were regenerated for the `sld`
  namespace and the missing `Coordinate.xml` was added.
- Result: `mvn -q -DskipTests=false verify` ends with the return value 0 without `testFailureIgnore`; the
  `testFailureIgnore` setting was removed again.
- Evidence: `clean test` of the reactor – xml 81 run / 0 errors / 1 skipped, util 14 / 0, component 1 / 0,
  ejb 0.
- Files: xml/src/main/java/org/geojsf/model/xml/GeoJsfNsPrefixMapper.java,
  util/src/test/java/org/geojsf/test/AbstractGeoJsfUtilTest.java,
  component/src/test/java/org/geojsf/test/TestJavaScript.java,
  xml/src/test/resources/geojsf/system/io/jaxb/specs/sld/Size.xml,
  xml/src/test/resources/geojsf/system/io/jaxb/specs/sld/SvgParameter.xml,
  xml/src/test/resources/geojsf/system/io/jaxb/geojsf/Coordinate.xml, pom.xml,
  doc/decisions/ADR-0002-test-strategy.md, doc/changelog.md.

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
