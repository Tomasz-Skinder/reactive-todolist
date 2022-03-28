CREATE TABLE todolist
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(63) NOT NULL CHECK ( CHAR_LENGTH(name) > 2)
);