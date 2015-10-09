#!/bin/sh -e

APP_DB_USER=barganha
APP_DB_PASS=barganha
APP_DB_NAME=$APP_DB_USER

POSTGRES_VERSION=9.4

export DEBIAN_FRONTEND=noninteractive

PG_REPO_APT_SOURCE=/etc/apt/sources.list.d/pgdg.list
if [ ! -f "$PG_REPO_APT_SOURCE" ]
then
  # Add PG apt repo:
  echo "deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main" > "$PG_REPO_APT_SOURCE"

  # Add PGDG repo key:
  wget --quiet -O - https://apt.postgresql.org/pub/repos/apt/ACCC4CF8.asc | apt-key add -
fi

echo "Updating apt-get packages info..."
apt-get -qq update
echo "Upgrading linux packages to the latest..."
#apt-get -y -qq upgrade

apt-get -y install "postgresql-$POSTGRES_VERSION" "postgresql-contrib-$POSTGRES_VERSION"

PG_CONF="/etc/postgresql/$POSTGRES_VERSION/main/postgresql.conf"
PG_HBA="/etc/postgresql/$POSTGRES_VERSION/main/pg_hba.conf"
PG_DIR="/var/lib/postgresql/$POSTGRES_VERSION/main"

# Edit postgresql.conf to change listen address to '*':
sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" "$PG_CONF"

# Append to pg_hba.conf to add password auth:
echo "host    all             all             all                     md5" >> "$PG_HBA"

# Explicitly set default client_encoding
echo "client_encoding = utf8" >> "$PG_CONF"

# Restart so that all new config is loaded:
service postgresql restart

cat << EOF | su - postgres -c psql
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.utf8'
                                  LC_CTYPE='en_US.utf8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template0;
EOF

echo "Successfully created dev virtual machine."
echo "PostgreSQL database has been setup:"
echo "  Host: localhost"
echo "  Port: 15432"
echo "  Database: $APP_DB_NAME"
echo "  Username: $APP_DB_USER"
echo "  Password: $APP_DB_PASS"
