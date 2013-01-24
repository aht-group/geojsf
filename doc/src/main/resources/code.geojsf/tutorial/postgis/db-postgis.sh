psql -U postgres -d geo \\
     -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis.sql
psql -U postgres -d geo \\
     -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis_comments.sql
psql -U postgres -d geo \\
     -f /usr/share/postgresql/9.1/contrib/postgis-2.0/spatial_ref_sys.sql
