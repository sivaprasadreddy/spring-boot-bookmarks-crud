CREATE SEQUENCE IF NOT EXISTS bookmark_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE bookmarks
(
    id         BIGINT       NOT NULL default nextval('bookmark_id_seq'),
    title      VARCHAR(200) NOT NULL,
    url        VARCHAR(500) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_bookmarks PRIMARY KEY (id)
);

insert into bookmarks(title, url, created_at)
values ('JetBrains Blog', 'https://blog.jetbrains.com', current_timestamp),
       ('JetBrains Academy', 'https://www.jetbrains.com/academy', current_timestamp);