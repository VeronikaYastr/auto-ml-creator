-- -----------------------------------------------------
-- Table USERS
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS USERS
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(45) NULL UNIQUE,
    username VARCHAR(45) NULL UNIQUE,
    password VARCHAR(65) NOT NULL
);

-- -----------------------------------------------------
-- Table MODELS
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS MODELS
(
    id          SERIAL PRIMARY KEY,
    userId      INT                NOT NULL REFERENCES USERS,
    title       VARCHAR(100)       NOT NULL,
    createdAt   DATE DEFAULT NOW() NOT NULL,
    modifiedAt  DATE DEFAULT NOW() NOT NULL,
    description VARCHAR(250)       NOT NULL,
    type        VARCHAR(30)        NOT NULL
);

-- -----------------------------------------------------
-- Table DATA
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS DATA
(
    id          SERIAL PRIMARY KEY,
    filename    VARCHAR(100) NOT NULL,
    downloadUri VARCHAR(250) NOT NULL,
    modelId     INT          NULL REFERENCES MODELS
);