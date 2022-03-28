CREATE TABLE item
(
    id                UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    content           VARCHAR(255) NOT NULL,
    status            VARCHAR(63)  NOT NULL,
    creation_date     TIMESTAMP    NOT NULL,
    modification_date TIMESTAMP,
    deadline          TIMESTAMP,
    todolist_id       INT          NOT NULL REFERENCES todolist
);