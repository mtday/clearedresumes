
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


CREATE TABLE user_invitations (
    id                VARCHAR(36)    NOT NULL,
    email             VARCHAR(256)   NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,
    created           VARCHAR(24)    NOT NULL,

    CONSTRAINT user_invitations_pk PRIMARY KEY (id),
    CONSTRAINT user_invitations_uniq UNIQUE (email, company_id),
    CONSTRAINT user_invitations_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
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


CREATE TABLE filters (
    id                VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,
    name              VARCHAR(100)   NOT NULL,
    email             BOOLEAN        NOT NULL,
    states            VARCHAR(2000)  NOT NULL,
    lcat_words        VARCHAR(40000) NOT NULL,
    content_words     VARCHAR(40000) NOT NULL,

    CONSTRAINT filters_pk PRIMARY KEY (id),
    CONSTRAINT filters_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);


CREATE TABLE resumes (
    id                VARCHAR(36)    NOT NULL,
    user_id           VARCHAR(36)    NOT NULL,
    status            VARCHAR(20)    NOT NULL,
    created           VARCHAR(24)    NOT NULL,
    expiration        VARCHAR(24),

    CONSTRAINT resumes_pk PRIMARY KEY (id),
    CONSTRAINT resumes_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE resume_reviews (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    company_id        VARCHAR(36)    NOT NULL,
    status            VARCHAR(20)    NOT NULL,
    reviewer_id       VARCHAR(36)    NOT NULL,
    review_time       VARCHAR(24)    NOT NULL,

    CONSTRAINT resume_reviews_pk PRIMARY KEY (id),
    CONSTRAINT resume_reviews_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE,
    CONSTRAINT resume_reviews_fk_company_id FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    CONSTRAINT resume_reviews_fk_reviewer_id FOREIGN KEY (reviewer_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE resume_introductions (
    resume_id         VARCHAR(36)    NOT NULL,
    full_name         VARCHAR(80)    NOT NULL,
    objective         VARCHAR(40000) NOT NULL,

    CONSTRAINT resume_introductions_pk PRIMARY KEY (resume_id),
    CONSTRAINT resume_introductions_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE resume_lcats (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    lcat              VARCHAR(80)    NOT NULL,
    experience        INTEGER        NOT NULL,

    CONSTRAINT resume_lcats_pk PRIMARY KEY (id),
    CONSTRAINT resume_lcats_uniq UNIQUE (resume_id, lcat),
    CONSTRAINT resume_lcats_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE contact_infos (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    value             VARCHAR(100)   NOT NULL,

    CONSTRAINT contact_info_pk PRIMARY KEY (id),
    CONSTRAINT contact_info_uniq UNIQUE (resume_id, value),
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
    polygraph         VARCHAR(40)    NOT NULL,

    CONSTRAINT clearances_pk PRIMARY KEY (id),
    CONSTRAINT clearances_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);

CREATE TABLE work_summaries (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    job_title         VARCHAR(200)   NOT NULL,
    employer          VARCHAR(200),
    begin_date        VARCHAR(10)    NOT NULL,
    end_date          VARCHAR(10),
    summary           VARCHAR(40000) NOT NULL,

    CONSTRAINT work_summaries_pk PRIMARY KEY (id),
    CONSTRAINT work_summaries_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE educations (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    institution       VARCHAR(100)   NOT NULL,
    field             VARCHAR(100)   NOT NULL,
    degree            VARCHAR(100)   NOT NULL,
    year              INTEGER        NOT NULL,

    CONSTRAINT educations_pk PRIMARY KEY (id),
    CONSTRAINT educations_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
);


CREATE TABLE certifications (
    id                VARCHAR(36)    NOT NULL,
    resume_id         VARCHAR(36)    NOT NULL,
    certificate       VARCHAR(100)   NOT NULL,
    year              INTEGER        NOT NULL,

    CONSTRAINT certifications_pk PRIMARY KEY (id),
    CONSTRAINT certifications_fk_resume_id FOREIGN KEY (resume_id) REFERENCES resumes (id) ON DELETE CASCADE
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
    name              VARCHAR(80)    NOT NULL,

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


CREATE TABLE configurations (
    key               VARCHAR(100)   NOT NULL,
    value             VARCHAR(100)   NOT NULL,

    CONSTRAINT configurations_pk PRIMARY KEY (key)
);

