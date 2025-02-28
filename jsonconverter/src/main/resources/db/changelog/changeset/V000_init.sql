create table if not exists json(
    id bigint primary key generated always as identity unique,
    content varchar(16384)
)