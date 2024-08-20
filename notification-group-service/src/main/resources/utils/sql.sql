CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE recipient_groups
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    keycloak_user_id UUID         NOT NULL,
    name             VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipient_group_members
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_id     UUID NOT NULL references recipient_groups (id) ON DELETE CASCADE,
    recipient_id UUID NOT NULL,
    added_at     TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES recipient_groups (id) ON DELETE CASCADE
);