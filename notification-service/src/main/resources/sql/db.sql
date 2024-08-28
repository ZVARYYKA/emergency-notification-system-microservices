CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE notification_statuses (
                                       id SERIAL PRIMARY KEY,
                                       status_name VARCHAR(50) NOT NULL UNIQUE -- Название статуса (например, "CREATED", "SENT", "FAILED")
);

CREATE TABLE notification_types (
                                    id SERIAL PRIMARY KEY,
                                    type_name VARCHAR(50) NOT NULL UNIQUE -- Название типа (например, "Email", "SMS", "Device")
);

CREATE TABLE notifications (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(), -- Уникальный идентификатор, генерируемый автоматически
                               type_id INT NOT NULL, -- Ссылка на таблицу notification_types
                               status_id INT NOT NULL, -- Ссылка на таблицу notification_statuses
                               sent_at TIMESTAMPTZ NOT NULL DEFAULT NOW(), -- Дата и время отправки
                               destination VARCHAR(255) NOT NULL, -- Адрес назначения (email, номер телефона, токен устройства и т.д.)
                               subject VARCHAR(255), -- Тема сообщения (для email)
                               message_body TEXT NOT NULL, -- Содержание сообщения
                               FOREIGN KEY (type_id) REFERENCES notification_types(id), -- Связь с таблицей типов уведомлений
                               FOREIGN KEY (status_id) REFERENCES notification_statuses(id) -- Связь с таблицей статусов
);
