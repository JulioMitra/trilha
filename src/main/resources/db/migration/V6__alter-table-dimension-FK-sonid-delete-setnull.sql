ALTER TABLE dimension DROP CONSTRAINT fk_sonid_id;

ALTER TABLE dimension ADD CONSTRAINT fk_sonid_id FOREIGN KEY (sonid) REFERENCES dimension (id) ON DELETE SET NULL;