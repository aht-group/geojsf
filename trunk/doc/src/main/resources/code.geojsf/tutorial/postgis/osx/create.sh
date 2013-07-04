sudo mkdir -p /opt/local/var/db/postgresql92/data
sudo chown postgres:postgres /opt/local/var/db/postgresql92/data
sudo su postgres -c '/opt/local/lib/postgresql92/bin/initdb -D /opt/local/var/db/postgresql92/data'