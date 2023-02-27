CREATE DATABASE trilhamitratest;

create table dimension(

    id SERIAL not null PRIMARY KEY,
    name varchar(100) not null

);

ALTER TABLE dimension ADD sonid INT;

ALTER TABLE dimension ADD CONSTRAINT fk_sonid_id FOREIGN KEY (sonid) REFERENCES dimension (id) ON DELETE SET NULL;