CREATE TABLE appuser
(
    id       uuid PRIMARY KEY,
    username text UNIQUE NOT NULL,
    password text        NOT NULL,
    scope    text        NOT NULL
);
