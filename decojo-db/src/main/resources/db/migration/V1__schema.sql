
CREATE TABLE users (
    username   VARCHAR(40)  NOT NULL,
    password   VARCHAR(256) NOT NULL,
    enabled    BOOLEAN,

    CONSTRAINT users_pk PRIMARY KEY (username)
);


CREATE TABLE authorities (
    username   VARCHAR(40)  NOT NULL,
    authority  VARCHAR(20)  NOT NULL,

    CONSTRAINT authorities_pk PRIMARY KEY (username, authority),
    CONSTRAINT authorities_fk_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);


CREATE TABLE labor_categories (
    id         VARCHAR(36)  NOT NULL,
    name       VARCHAR(50)  NOT NULL,

    CONSTRAINT labor_categories_pk PRIMARY KEY (id)
);

