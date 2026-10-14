# GeoJSF

GeoJSF is a JSF mapping toolkit, a reusable Java EE library ("JSF Mapping Tool")
for building GIS and mapping web applications. It supplies JSF/PrimeFaces components and controllers,
EJB business logic and facades, XML/JSON models, and integrations for OGC services (WMS/WFS/WCS),
GeoServer, and SLD styling. Applications embed the GeoJSF modules as JARs; GeoJSF itself is not a
deployable application.

## Initial Situation

- Long-grown, historically developed multi-module Maven project (`org.geojsf:geojsf`, version
  `0.1.2-SNAPSHOT`).
- Provides GIS/JSF functionality as reusable JAR libraries that applications embed; GeoJSF has no WAR and no runnable application.
- Built on the internal `org.jeesl` framework and the `net.sf.exlp` utilities; the GIS functionality
  comes from GeoTools, GeoServer (administered over REST), Hibernate Spatial/PostGIS, and JTS.
- Module split into `interfaces`, `api`, `ejb`, `jsf`, `component`, `theme`, `util`, `geoserver`,
  `module-sld`, `xml`, and `doc`, with `geojsf-bom` as the central dependency and version BOM.
- The documentation scaffolding is introduced: the index (`doc/status.md`), the decision repository
  (`doc/decisions/`), the architecture file (`doc/requirements/architecture.md`), and the change log
  (`doc/changelog.md`).

## Relevant Values

Values and versions are maintained in exactly one authoritative location; this file only names these
locations.

- **Document language:** English.
- **Version (source of truth):** the root `pom.xml` (`0.1.2-SNAPSHOT`); all modules inherit it.
- **Coordinates:** group `org.geojsf`, artifacts `geojsf-*`.
- **License:** GNU General Public License v3 (GPL-3.0), declared in the root `pom.xml`.
- **Source repository:** `https://github.com/aht-group/geojsf.git`.
- **Dependencies and pinned versions:** `bom/pom.xml`; the JEESL BOM `org.jeesl.bom:bom-core`
  (`0.3.2-SNAPSHOT`) is imported there.

## Technical Assumptions

- **Language and build:** Java 8 API of the compilation (`maven-compiler-plugin` `release` `8`); Maven
  (>= 3.6.3) multi-module build; artifacts are published under the group `org.geojsf`.
- **Platform:** Java EE — JSF (`javax.faces-api`) with PrimeFaces for the web layer; EJB and JPA
  (`javax.persistence-api`) for the business and persistence layer.
- **Persistence and geometry:** Hibernate Spatial `5.3.18.Final` with JTS; PostgreSQL/PostGIS is the
  reference spatial database.
- **GIS:** GeoTools `28.0` (`gt-main`, `gt-geojson`, `gt-shapefile`, `gt-geotiff`, ...) and GeoServer
  administration over REST via `it.geosolutions:geoserver-manager 1.3.0`; geometries via
  `com.github.filosganga:geogson`.
- **Standards:** OGC WMS/WFS/WCS, SLD styles, GML, WKT, and GeoJSON.
- **XML and JSON:** JAXB in a `javax` and a `jakarta` variant, published as the classifier artifacts of the
  `xml` module (ADR-0004); and Jackson.
- **Tests:** JUnit 5 (Jupiter) test programs; per-module configuration under `src/test/resources/config.geojsf-*.test`.

## Open Fundamental Decisions

- Which requirements, constraints, and architecture decisions are recorded to fill the document
  repository?
- Whether the mixed `javax`/`jakarta` namespaces across the modules (for example `module-sld` uses
  `jakarta.persistence`) are unified.

## Decided Fundamental Decisions

- The module split (`geojsf-bom`, `interfaces`, `api`, `ejb`, `jsf`, `component`, `theme`, `util`,
  `geoserver`, `module-sld`, `xml`, `doc`) and the reusable-JAR approach without a deployable
  application.
- Java EE as the platform: JSF with PrimeFaces for the web layer and EJB/JPA for the business layer,
  built on the internal `org.jeesl` framework.
- GIS integration via GeoTools, GeoServer (`geoserver-manager`), and Hibernate Spatial/PostGIS.
- Java 8 as the compile level.
- The `xml` module publishes its JAXB model as the classifier artifacts `javax` and `jakarta` (ADR-0004).
- Architecture decisions are kept in the **Decision Repository** (`doc/decisions/`) and indexed in
  `doc/status.md`.

## Planned Milestones

- Populate the document repository: record requirements, constraints, and architecture decisions,
  indexed in `doc/status.md`.
- Unify the `javax`/`jakarta` namespace variants across the modules.

## Build and Start

GeoJSF is a library project: it builds and installs JARs, and there is no application to start.
Prerequisites are JDK 9 or newer and Maven 3.6.3 or newer with access to the Sonatype snapshot and OSGeo
repositories.

```bash
mvn clean install                 # build and install all modules
mvn -pl util test                 # run the tests of a single module
```

```bash
mvn -Pram -DskipTests -Djava.awt.headless=true clean install    # full build as used locally
```

The `xml` module builds its JAXB model in two variants and publishes them as classifier artifacts; a
consumer selects the variant by classifier (`org.geojsf:geojsf-xml:<version>:javax` or `:jakarta`), and no
artifact without a classifier exists (ADR-0004).

Applications embed the installed `geojsf-*` artifacts; the `jsf` and `component` modules contribute
the JSF tag library and views, and `theme` the PrimeFaces theme.

## Development Principles

- Follow the project instructions for the AI assistant in `doc/llm.md` and the **Requirements Rules** in `doc/requirements/requirements.md`.
- Write documents, source code, and new file names in **English**; use ASCII kebab-case for new file names.
- Cover new functionality with tests and run them (together with the Git diff) after each change.
- Never create a commit automatically; see the **Commit Message Rule**.

## Rules and Roles

- Project instructions for the AI assistant: `doc/llm.md`; rule files of the AI tool: `.clinerules/`.
- Commit Message Rule: `.clinerules/10-git-commit.md`.
- Requirements Rules: `doc/requirements/requirements.md`.
- Index File of requirements and architecture decisions including status: `doc/status.md`.
- Change Log (chronicle): `doc/changelog.md`.
- Architecture File: `doc/requirements/architecture.md`; Decision Repository: `doc/decisions/`.

