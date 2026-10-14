---
id: ADR-0004
title: Build and publish the JAXB variants as classifier artifacts
status: accepted
date: 2026-10-13
related:
  - ADR-0003
---

# ADR-0004: Build and publish the JAXB variants as classifier artifacts

## Context

The project overview (`README.md`, `## Open Fundamental Decisions`) leaves the mixed `javax`/`jakarta`
namespaces of the modules open, and its milestones (`README.md`, `## Planned Milestones`) name their
unification. The open question is how the `xml` module produces its two JAXB variants and which artifacts
it publishes.

- The generated sources of the two variants declare the **same** fully qualified type names; only the
  annotation packages differ (`javax.xml.bind` and `jakarta.xml.bind`).
- A Maven module compiles one set of source roots into one output directory, so a single run cannot carry
  both variants.

## Decision

The `xml` module builds both JAXB variants in one Maven run and publishes exactly two classifier artifacts.

- The module uses `<packaging>pom</packaging>`: it publishes its POM and the two classifier jars, and no
  artifact without a classifier exists.
- `maven-compiler-plugin` (3.13.0) compiles both variants in one run, each with its own source roots and
  its own output directory; the properties `xml.classes.javax`
  (`${project.build.directory}/classes-javax`) and `xml.classes.jakarta`
  (`${project.build.directory}/classes-jakarta`) name them:
  - `compile-javax` at phase `compile` compiles the `javax` source roots with `--release 8` into
    `xml.classes.javax`.
  - `compile-jakarta` at phase `compile` compiles the `jakarta` source roots into `xml.classes.jakarta`.
- `maven-jar-plugin` creates the artifacts at phase `process-classes`: the classifier `javax` from
  `xml.classes.javax` and the classifier `jakarta` from `xml.classes.jakarta`.
- Consumers select the variant by classifier: `org.geojsf:geojsf-xml:<version>:javax` or `:jakarta`;
  `bom/pom.xml` manages both, and `util/pom.xml` names `javax`.
- The profiles `javax` and `jakarta` only regenerate the JAXB sources from `xml/src/main/xsd/`
  (`cxf-xjc-plugin` with `javax.xjb` and `jakarta.xjb`); they do not select the build variant.

## Rationale

- One command on one source revision produces both variants:
  `mvn -DskipTests -Djava.awt.headless=true clean install`.
- Without an artifact that carries no classifier, a consumer cannot bind a variant by accident.
- Separate output directories are what make both variants possible in one module run: the two source
  trees declare the same type names and cannot share one output directory.
- Both variants compile into their own directory, so neither variant is privileged.
- The classifier jars remain ordinary attached artifacts, so `install`, `deploy`, and the signatures of
  the release profile need no handling of their own.
- Attaching the classifier jars at `process-classes`, before the `test` phase, keeps a plain `mvn test` on
  the repository working: a module that depends on a classifier resolves it from the reactor, and the jars
  of `xml` must exist before the `compile` of that module; the default `package` phase would leave them
  missing in a `test` run and `util` would not compile.
- Classifier jars keep the coordinates of the module, so `bom/pom.xml` and the consumers change only by
  the classifier.

## Alternatives

### Two build commands with the variant selected by the JDK

Keeps one module with jar packaging, but needs two runs and two JDK switches.

### `packaging=jar` with a disabled main jar and `install-file`/`deploy-file`

Reaches the same repository content, but the deployment needs its own path, because `deploy-file` does
not sign the artifacts of the release profile.

### A JDK toolchain per compile execution

Compiles the `javax` artifact with a genuine JDK 8 and the `jakarta` artifact with a genuine JDK 11 in
one run. Requires an entry in the toolchain file of every build machine, and the toolchain does not pin
the API level.

### Two modules with distinct artifact IDs

Keeps ordinary jar packaging per variant, but changes the artifact ID and therefore the dependency of
every consumer.

### Eclipse Transformer

Compiles once and rewrites the bytecode of the `javax` jar into a `jakarta` jar. Requires one external
component, which needs a decision of its own, and the `jakarta` artifact would not be the product of the
Jakarta compiler.

## Impact

- Affected requirements: none.
- `xml/pom.xml` holds the POM packaging, the two compile executions, and the two jar executions.
- `util/pom.xml` names the classifier `javax`, and `bom/pom.xml` manages both classifiers.
- `README.md` (`## Build and Start`) and the index (`doc/status.md`) name the decision.

## Open Points

- The concrete source-root paths of the two variants are the business of the implementation and are not
  regulated here.
- The `--release` value of the `jakarta` variant is not regulated; ADR-0001 fixes `8` for the modules and
  leaves a higher value to the module.
- A later `src/main/resources` of the `xml` module is copied into the module output directory only; it
  needs a copy into both variant directories.
- Which sources and javadoc artifacts a release publishes per classifier; `maven-source-plugin` skips a
  POM packaging.
- Unit tests for the `xml` module: POM packaging binds no test phases, so a later test compilation and
  the test goal need their own executions.

## Evidence

### Implementation

- `xml/pom.xml` – packaging `pom`; the properties `xml.classes.javax` and `xml.classes.jakarta`; the
  compile executions `compile-javax` (`release` `8`) and `compile-jakarta` (`release` `11`), each from
  `src/main/java` plus its own variant root; the jar executions `jar-javax` and `jar-jakarta` at
  `process-classes`; the module output directory is `xml.classes.javax`
- `xml/src/main/javax` and `xml/src/main/jakarta` – the two JAXB model source roots, identical except the
  annotation package; the variant-independent sources (`org/geojsf/model/component`, `org/geojsf/model/json`,
  and `GeoJsfNsPrefixMapper`) stay under `xml/src/main/java`
- `xml/src/main/xsd/jakarta.xjb` – the binding for the `jakarta` profile
- `xml/pom.xml` – the `javax` and `jakarta` profiles regenerate the sources with `cxf-xjc-plugin` and do not
  select the build variant
- `bom/pom.xml` – manages `geojsf-xml` with the classifiers `javax` and `jakarta`
- `util/pom.xml`, `component/pom.xml`, `jsf/pom.xml`, `api/pom.xml`, and `doc/pom.xml` – name the classifier
  `javax`

### Tests

- `mvn -pl xml -DskipTests -Djava.awt.headless=true clean install` – the local repository holds
  `geojsf-xml-0.1.2-SNAPSHOT.pom`, `geojsf-xml-0.1.2-SNAPSHOT-javax.jar`,
  `geojsf-xml-0.1.2-SNAPSHOT-jakarta.jar`, and `geojsf-xml-0.1.2-SNAPSHOT-tests.jar`, and no jar without a
  classifier; `javap -v` reports class-file version 52 for `javax` and 55 for `jakarta`
- `mvn -DskipTests=false -Djava.awt.headless=true clean verify` – the reactor builds and the test programs of
  `xml` (81), `util` (14), and `component` (1) pass

### Documentation

- `README.md` (`## Build and Start`) – names the classifier artifacts of the `xml` module
- `doc/status.md` – lists ADR-0004
