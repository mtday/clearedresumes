
CREATE TABLE users (
    id         VARCHAR(36)  NOT NULL,
    login      VARCHAR(40)  NOT NULL,
    email      VARCHAR(256) NOT NULL,
    password   VARCHAR(256) NOT NULL,
    enabled    BOOLEAN,

    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_uniq_login UNIQUE (login),
    CONSTRAINT users_uniq_email UNIQUE (email)
);


CREATE TABLE authorities (
    user_id    VARCHAR(36)  NOT NULL,
    authority  VARCHAR(20)  NOT NULL,

    CONSTRAINT authorities_pk PRIMARY KEY (user_id, authority),
    CONSTRAINT authorities_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE companies (
    id         VARCHAR(36)  NOT NULL,
    name       VARCHAR(100) NOT NULL,
    website    VARCHAR(256) NOT NULL,

    CONSTRAINT companies_pk PRIMARY KEY (id),
    CONSTRAINT companies_uniq_name UNIQUE (name)
);


CREATE TABLE company_users (
    user_id    VARCHAR(36)  NOT NULL,
    company_id VARCHAR(36)  NOT NULL,

    CONSTRAINT company_users_pk PRIMARY KEY (user_id, company_id),
    CONSTRAINT company_users_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT company_users_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);


CREATE TABLE labor_categories (
    id         VARCHAR(36)  NOT NULL,
    name       VARCHAR(50)  NOT NULL,

    CONSTRAINT labor_categories_pk PRIMARY KEY (id)
);

