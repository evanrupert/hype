create table users
(
  id          uuid          not null constraint users_pkey primary key,
  email       varchar(255),
  password    varchar(255),
  firstName   varchar(255),
  lastName    varchar(255)
);

create unique index users_email_index
  on users (email);

create table items
(
  id     uuid          not null constraint items_pkey primary key,
  userId integer,
  brand  varchar(255),
  model  varchar(255),
  size   varchar(255)
);

create table transactions
(
  id               uuid          not null constraint transactions_pkey primary key,
  itemId           integer,
  userId           integer,
  itemState        varchar(255),
  purchaseDatetime timestamp,
  purchaseAmount   numeric,
  saleDatetime     timestamp,
  saleAmount       numeric
);

