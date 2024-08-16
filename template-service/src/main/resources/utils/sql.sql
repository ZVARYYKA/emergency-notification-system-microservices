CREATE EXTENSION  IF NOT EXISTS "uuid-ossp";


create table message_templates (
                                   id uuid primary key default  uuid_generate_v4(),
                                   keycloak_user_id uuid NOT NULL,
                                   subject VARCHAR(255) NOT NULL,
                                   body TEXT NOT NULL,
                                   created_at timestamp with time zone default now(),
                                   updated_at timestamp with time zone default now()

)