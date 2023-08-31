create or replace function random_uuid() returns varchar AS $$
begin
return uuid_in(md5(random()::text || clock_timestamp()::text)::cstring);
end;
$$ language plpgsql;

create table if not exists users (
     id             varchar(40)  not null primary key,
     subscriptionId varchar(40)  not null,
     email          varchar(255) not null,
     username       varchar(255),
     first_name     varchar(255),
     last_name      varchar(255),
     password       varchar(255),
     registered_at  timestamp
);

create table if not exists roles (
    id        varchar(40)  not null primary key,
    name      varchar(255) not null unique,
    "system"  boolean      not null
);

create table if not exists permissions (
    id          varchar(40)  not null primary key,
    name        varchar(255) not null unique,
    description varchar(255) not null
);

create table if not exists role_permissions (
    fk_role       varchar(40) references roles(id),
    fk_permission varchar(40) references permissions(id)
);

create table if not exists user_roles (
    fk_user varchar(40)  references users(id),
    fk_role varchar(40) references roles(id)
);

insert into permissions
values (random_uuid(), 'COURSE_GET', 'Доступ к просмотру записей курсов'),
       (random_uuid(), 'COURSE_MANAGE', 'Доступ к созданию и удалению записей курсов'),
       (random_uuid(), 'COURSE_EDIT', 'Доступ к редактированию записей курсов'),
       (random_uuid(), 'COURSE_MATERIALS_EDIT', 'Доступ к редактированию содержимого курсов'),
       (random_uuid(), 'COURSE_ENROLL_USERS', 'Доступ к добавлению пользователей на курс'),
       (random_uuid(), 'USER_GET', 'Доступ к просмотру записей пользователей'),
       (random_uuid(), 'USER_EDIT', 'Доступ к редактированию записей пользователей'),
       (random_uuid(), 'USER_ROLE_ASSIGNMENT', 'Доступ к изменению ролей пользователей'),
       (random_uuid(), 'ROLE_GET', 'Доступ к просмотру записей ролей'),
       (random_uuid(), 'ROLE_EDIT', 'Доступ к редактированию записей ролей'),
       (random_uuid(), 'PAYMENT_INIT', 'Доступ к инициализации процесса оплаты'),
       (random_uuid(), 'PAYMENT_PROCEED_STATUS', 'Доступ к изменению статуса оплаты');

insert into roles
values (random_uuid(), 'STUDENT', true),
       (random_uuid(), 'TUTOR', true),
       (random_uuid(), 'SUBSCRIPTION_ADMIN', true);

insert into role_permissions
values ((select id from roles where name = 'STUDENT'), (select id from permissions where name = 'COURSE_GET')),
       ((select id from roles where name = 'STUDENT'), (select id from permissions where name = 'USER_GET')),
       ((select id from roles where name = 'TUTOR'), (select id from permissions where name = 'COURSE_GET')),
       ((select id from roles where name = 'TUTOR'), (select id from permissions where name = 'COURSE_MATERIALS_EDIT')),
       ((select id from roles where name = 'TUTOR'), (select id from permissions where name = 'USER_GET')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'COURSE_GET')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'COURSE_EDIT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'COURSE_MANAGE')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'COURSE_MATERIALS_EDIT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'COURSE_ENROLL_USERS')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'USER_GET')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'USER_EDIT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'USER_ROLE_ASSIGNMENT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'ROLE_GET')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'ROLE_EDIT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'PAYMENT_INIT')),
       ((select id from roles where name = 'SUBSCRIPTION_ADMIN'), (select id from permissions where name = 'PAYMENT_PROCEED_STATUS'));
