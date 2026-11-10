-- For Clarity's change capture (Debezium, pgoutput), run once when the data directory is new, in
-- bialydunajec-postgres-db. The app's tables are made later by Hibernate; the publication covers them too.
CREATE USER diamond WITH REPLICATION PASSWORD 'diamond';
GRANT pg_read_all_data TO diamond;
CREATE PUBLICATION dbz_publication FOR ALL TABLES;
