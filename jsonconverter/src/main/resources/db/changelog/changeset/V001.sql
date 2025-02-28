create table if not exists xslt(
    id bigint primary key generated always as identity unique,
    content varchar(16384)
)