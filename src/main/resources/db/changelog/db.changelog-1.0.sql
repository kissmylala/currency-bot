--liquibase sql formatted

--changeset adem:1
CREATE TABLE IF NOT EXISTS user_message(
id  SERIAL PRIMARY KEY ,
username VARCHAR(255) NOT NULL,
text VARCHAR(255) NOT NULL,
chat_id BIGINT NOT NULL,
message_id INT NOT NULL
);


