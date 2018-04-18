CREATE USER geo WITH PASSWORD 'geo';  

# This is helpful if your want to drop a existing databse
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity
       WHERE procpid <> pg_backend_pid() AND datname = 'geo';
DROP DATABASE geo;

# Create the database
CREATE DATABASE geo OWNER geo ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE geo TO geo;
createlang plpgsql geo;