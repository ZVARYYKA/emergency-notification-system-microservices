CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE recipients
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    keycloak_user_id VARCHAR(255) NOT NULL UNIQUE,
    name             VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    phone_number     VARCHAR(20),
    created_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);
CREATE table devices
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    recipient_id UUID NOT NULL,
    device_name  varchar(255),
    device_type  VARCHAR(50),
    device_token VARCHAR(255),
    created_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_recipient
        FOREIGN KEY (recipient_id)
            REFERENCES recipients (id)
            ON DELETE CASCADE
);

create table contacts(
                         id uuid primary key default uuid_generate_v4(),
                         recipient_id UUID NOT NULL,
                         contact_email VARCHAR(255),
                         contact_phone_number VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_recipient_contact
                             FOREIGN KEY (recipient_id)
                                 REFERENCES recipients(id)
                                 ON DELETE CASCADE
);
