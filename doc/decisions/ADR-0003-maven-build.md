---
id: ADR-0003
title: Maven as the build tool
status: accepted
date: 2026-10-11
related:
  - ADR-0001
---

# ADR-0003: Maven as the build tool

## Context

The open question was which build tool builds the modules of the reactor, resolves their dependencies, and binds
the parent, the BOMs, and the target platform; the build tool was named in `README.md`,
`## Technical Assumptions`.

## Decision

1. Maven builds the project; the root project `geojsf` with packaging `pom` leads the modules `bom`,
   `interfaces`, `api`, `ejb`, `jsf`, `component`, `theme`, `util`, `geoserver`, `module-sld`, `xml`, and `doc`
   in one reactor.
2. The packaging is `pom` for `bom` and `jar` for the other modules; the reactor contains no `war`.
3. The dependency versions come from the BOM `geojsf-bom`, which imports the JEESL BOM `org.jeesl.bom:bom-core`.
4. Maven enforces banned dependencies with `maven-enforcer-plugin` and generates the Javadoc with
   `maven-javadoc-plugin` in the `release` profile.
5. A fixed Maven version with its own build execution is not set; the root `pom.xml` names the minimum Maven 3.6.3
   under `prerequisites`.

## Rationale

- The parent and the BOMs come from the project itself and the JEESL environment and are Maven artifacts.
- The packagings `pom` and `jar` map the BOM and the reusable libraries to Maven modules.
- The minimum Maven 3.6.3 under `prerequisites` binds the Maven version sufficiently, so a fixed version with its
  own build execution is superfluous.

## Alternatives

- Another build tool (Gradle) – not selected; the parent, the BOMs, and the artifact types are designed for Maven.

## Impact

- Affected requirements: none.
- Files: `pom.xml` (root) and the module POMs of the named modules.

## Evidence

### Implementation

- `pom.xml` (root) – packaging `pom`, twelve `<module>` entries
- `pom.xml` (root) – `maven-enforcer-plugin` with `bannedDependencies`; `maven-javadoc-plugin` in the `release`
  profile
- `pom.xml` (root) – `prerequisites` names Maven 3.6.3
- `bom/pom.xml` – packaging `pom`; imports `org.jeesl.bom:bom-core`
- `interfaces/pom.xml`, `api/pom.xml`, `ejb/pom.xml`, `jsf/pom.xml`, `component/pom.xml`, `theme/pom.xml`,
  `util/pom.xml`, `geoserver/pom.xml`, `module-sld/pom.xml`, `xml/pom.xml`, `doc/pom.xml` – packaging `jar`

### Tests

- `mvn -q -DskipTests=false verify` (root) – the reactor resolves the BOMs and builds the twelve modules

### Documentation

- `README.md` (`## Technical Assumptions`) – names Maven (display location)
- `doc/requirements/architecture.md` – the technology list names Maven (display location)
