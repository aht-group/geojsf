# Technical Architecture

## Structure

GeoJSF is a multi-module Maven build with the group `org.geojsf`.
The repository root holds one aggregator POM that lists every module.
Each module keeps its own POM, sources, and resources.

The modules are shipped as reusable JAR libraries that an application embeds; the repository contains no
deployable application.

## Modules

| Artifact | Role |
|---|---|
| `geojsf-bom` | Central dependency and version management; imports the JEESL BOM `org.jeesl.bom:bom-core` |
| `geojsf-interfaces` | Interfaces and model contracts |
| `geojsf-xml` | XML and JSON model |
| `geojsf-api` | Facade and bean contracts |
| `geojsf-ejb` | EJB business logic and facades |
| `geojsf-jsf` | JSF layer and views |
| `geojsf-component` | JSF and PrimeFaces component library |
| `geojsf-theme` | PrimeFaces theme |
| `geojsf-util` | Utilities |
| `geojsf-geoserver` | GeoServer administration over REST |
| `geojsf-module-sld` | SLD module |
| `geojsf-doc` | Documentation generation |

## Technology

- Language: Java 8 API; the compile level is maintained by the decision on the Java compile level
  (`doc/decisions/ADR-0001-java-compile-level.md`).
- Platform: Java EE with JSF and PrimeFaces, EJB, and JPA.
- Persistence and geometry: Hibernate Spatial with JTS and PostgreSQL/PostGIS.
- GIS: GeoTools and GeoServer administration over REST.
- XML and JSON: JAXB and Jackson.
- Test: JUnit 5.
- Build: Apache Maven.

The versions of the platform, the language, the frameworks, and the build environment are maintained in the
authoritative location named below.

## Authoritative Locations

- Platform, language, frameworks, build environment, and pinned versions: **constraint**
  (`doc/requirements/constraints/`) or **decision** (`doc/decisions/`).
- Selecting a technology, a protocol, or a storage format: **decision** (`doc/decisions/`).
- Behavior and values: the responsible **requirement** (`doc/requirements/`).
