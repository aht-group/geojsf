CREATE USER geo WITH PASSWORD 'geo';  

SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE procpid <> pg_backend_pid() AND datname = 'geo';
DROP DATABASE geo;
CREATE DATABASE geo OWNER lis ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE geo TO geo;
createlang plpgsql geo;

psql -U postgres -d geo -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis.sql
psql -U postgres -d geo -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis_comments.sql
psql -U postgres -d geo -f /usr/share/postgresql/9.1/contrib/postgis-2.0/spatial_ref_sys.sql
