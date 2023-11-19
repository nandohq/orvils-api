CREATE TABLE IF NOT EXISTS country (
    id              UUID PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    code            VARCHAR(3) NOT NULL,
    language        VARCHAR(5) NULL,
    creation_date   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS publisher (
    id              UUID PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    description     TEXT NULL,
    creation_date   TIMESTAMP NOT NULL DEFAULT NOW(),

    country_id     UUID NULL,

    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE IF NOT EXISTS collection (
    id              UUID PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    description     TEXT NULL,
    creation_date   TIMESTAMP NOT NULL DEFAULT NOW(),

    publisher_id    UUID NOT NULL,

    CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(id)
);

CREATE TABLE IF NOT EXISTS author (
    id              UUID PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    birthday        DATE NULL,
    creation_date   TIMESTAMP NOT NULL DEFAULT NOW(),

    country_id      UUID NULL,

    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE IF NOT EXISTS book (
    id              UUID PRIMARY KEY,
    title           VARCHAR(200) NOT NULL,
    isbn            VARCHAR(13) NOT NULL,
    summary         TEXT NULL,
    edition_type    VARCHAR(20) NOT NULL,
    edition_number  INTEGER DEFAULT 1,
    creation_date   TIMESTAMP NOT NULL DEFAULT NOW(),

    author_id       UUID NOT NULL,
    publisher_id    UUID NOT NULL,
    collection_id   UUID NULL,

    UNIQUE (isbn),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id),
    CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(id),
    CONSTRAINT fk_collection FOREIGN KEY (collection_id) REFERENCES collection(id)
);