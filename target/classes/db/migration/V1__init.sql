-- USERS
create table if not exists users (
  id          bigserial primary key,
  full_name   varchar(255) not null,
  email       varchar(255) not null unique,
  password    varchar(255) not null,
  role_group  varchar(16)  not null,   -- SUPER | ADMIN | LOJA
  enabled     boolean      not null default true,
  created_at  timestamptz  not null default now()
);

-- (Opcional) TOKENS DE CADASTRO
create table if not exists registration_tokens (
  id                 bigserial primary key,
  token              varchar(128) not null unique,
  role_group_allowed varchar(16)  not null,  -- ADMIN | LOJA
  expires_at         timestamptz  not null,
  used               boolean      not null default false
);

create index if not exists idx_users_email on users(email);
create index if not exists idx_tokens_used on registration_tokens(used);
