
CREATE TABLE users (
    id                VARCHAR(36)    NOT NULL,
    login             VARCHAR(40)    NOT NULL,
    email             VARCHAR(256)   NOT NULL,
    password          VARCHAR(256)   NOT NULL,
    enabled           BOOLEAN        NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_uniq_login UNIQUE (login),
    CONSTRAINT users_uniq_email UNIQUE (email)
);


CREATE TABLE authorities (
    user_id           VARCHAR(36)    NOT NULL,
    authority         VARCHAR(20)    NOT NULL,

    CONSTRAINT authorities_pk PRIMARY KEY (user_id, authority),
    CONSTRAINT authorities_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE companies (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(100)   NOT NULL,
    website           VARCHAR(256)   NOT NULL,
    plan_type         VARCHAR(20)    NOT NULL,
    slots             INTEGER        NOT NULL,
    active            BOOLEAN        NOT NULL,

    CONSTRAINT companies_pk PRIMARY KEY (id),
    CONSTRAINT companies_uniq_name UNIQUE (name)
);


CREATE TABLE company_users (
    user_id           VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,

    CONSTRAINT company_users_pk PRIMARY KEY (user_id, company_id),
    CONSTRAINT company_users_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT company_users_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);


CREATE TABLE transactions (
    id                VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,
    user_id           VARCHAR(36)    NOT NULL,
    description       VARCHAR(3000)  NOT NULL,
    created           VARCHAR(24)    NOT NULL,
    amount            FLOAT          NOT NULL,

    CONSTRAINT transactions_pk PRIMARY KEY (user_id, company_id),
    CONSTRAINT transactions_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    CONSTRAINT transactions_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE resumes (
    id                VARCHAR(36)    NOT NULL,
    user_id           VARCHAR(36)    NOT NULL,
    status            VARCHAR(20)    NOT NULL,
    created           VARCHAR(24)    NOT NULL,
    expiration        VARCHAR(24),
    lcat              VARCHAR(50)    NOT NULL,
    experience        INTEGER        NOT NULL,
    objective         VARCHAR(20000) NOT NULL,

    CONSTRAINT resumes_pk PRIMARY KEY (id),
    CONSTRAINT resumes_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE company_resumes (
    id                VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    purchaser_id      VARCHAR(36)    NOT NULL,
    purchased         VARCHAR(24)    NOT NULL,

    CONSTRAINT company_resumes_pk PRIMARY KEY (id),
    CONSTRAINT company_resumes_uniq UNIQUE (company_id, resume_id),
    CONSTRAINT company_resumes_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    CONSTRAINT company_resumes_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE,
    CONSTRAINT company_resumes_fk_purchaser_id FOREIGN KEY (purchaser_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE resume_exclusions (
    resume_id         VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,

    CONSTRAINT resume_exclusions_pk PRIMARY KEY (resume_id, company_id),
    CONSTRAINT resume_exclusions_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE,
    CONSTRAINT resume_exclusions_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);


CREATE TABLE contact_infos (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    type              VARCHAR(20)    NOT NULL,
    value             VARCHAR(100)   NOT NULL,

    CONSTRAINT contact_info_pk PRIMARY KEY (id),
    CONSTRAINT contact_info_uniq UNIQUE (resume_id, type, value),
    CONSTRAINT contact_info_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE work_locations (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    state             VARCHAR(50)    NOT NULL,
    region            VARCHAR(50)    NOT NULL,

    CONSTRAINT work_locations_pk PRIMARY KEY (id),
    CONSTRAINT work_locations_uniq UNIQUE (resume_id, state, region),
    CONSTRAINT work_locations_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE key_words (
    resume_id         VARCHAR(36)    NOT NULL,
    word              VARCHAR(40)    NOT NULL,

    CONSTRAINT key_words_pk PRIMARY KEY (resume_id, word),
    CONSTRAINT key_words_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);

CREATE TABLE clearances (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    type              VARCHAR(40)    NOT NULL,
    organization      VARCHAR(100)   NOT NULL,
    polygraph         VARCHAR(100)   NOT NULL,

    CONSTRAINT clearances_pk PRIMARY KEY (id),
    CONSTRAINT clearances_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);

CREATE TABLE work_summaries (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    job_title         VARCHAR(200)   NOT NULL,
    employer          VARCHAR(200)   NOT NULL,
    begin_date        DATE           NOT NULL,
    end_date          DATE,
    responsibilities  VARCHAR(20000) NOT NULL,
    accomplishments   VARCHAR(20000) NOT NULL,

    CONSTRAINT work_summaries_pk PRIMARY KEY (id),
    CONSTRAINT work_summaries_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE educations (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    institution       VARCHAR(100)   NOT NULL,
    field             VARCHAR(100)   NOT NULL,
    degree            VARCHAR(100)   NOT NULL,

    CONSTRAINT educations_pk PRIMARY KEY (id),
    CONSTRAINT educations_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE certifications (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    certificate       VARCHAR(100)   NOT NULL,

    CONSTRAINT certifications_pk PRIMARY KEY (id),
    CONSTRAINT certifications_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE contact_types (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(50)    NOT NULL,

    CONSTRAINT contact_types_pk PRIMARY KEY (id),
    CONSTRAINT contact_types_uniq UNIQUE (name)
);


CREATE TABLE clearance_types (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(50)    NOT NULL,

    CONSTRAINT clearance_types_pk PRIMARY KEY (id),
    CONSTRAINT clearance_types_uniq UNIQUE (name)
);


CREATE TABLE polygraph_types (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(50)    NOT NULL,

    CONSTRAINT polygraph_types_pk PRIMARY KEY (id),
    CONSTRAINT polygraph_types_uniq UNIQUE (name)
);


CREATE TABLE labor_categories (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(50)    NOT NULL,

    CONSTRAINT labor_categories_pk PRIMARY KEY (id),
    CONSTRAINT labor_categories_uniq UNIQUE (name)
);


CREATE TABLE states (
    id                VARCHAR(36)    NOT NULL,
    name              VARCHAR(50)    NOT NULL,

    CONSTRAINT states_pk PRIMARY KEY (id),
    CONSTRAINT states_uniq UNIQUE (name)
);


CREATE TABLE prices (
    type              VARCHAR(50)    NOT NULL,
    price             FLOAT          NOT NULL,

    CONSTRAINT pricing_pk PRIMARY KEY (type)
);

