DROP TABLE IF EXISTS diaries CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS auth CASCADE;

CREATE TABLE auth
(
  id VARCHAR NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  user_role VARCHAR(32) NOT NULL DEFAULT 'ROLE_USER',
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE users
(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id VARCHAR NOT NULL REFERENCES auth(id) ON DELETE CASCADE,
  name VARCHAR(64) NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE diaries
(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id BIGSERIAL NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  title VARCHAR(64) NOT NULL,
  content VARCHAR NOT NULL,
  created_date TIMESTAMP NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);