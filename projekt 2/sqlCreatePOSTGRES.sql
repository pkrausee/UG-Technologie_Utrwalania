create table grade (
    id uuid SERIAL not null,
    created timestamp,
    description varchar(255),
    grade float8 not null,
    wage float8 not null,
    student_id uuid,
    subject_id int8,
    primary key (id)
);

create table student (
    id uuid SERIAL not null,
    birthday date,
    name varchar(255),
    removed boolean not null,
    second_name varchar(255),
    surname varchar(255),
    primary key (id)
);

create table subject (
    id int8 SERIAL not null,
    created timestamp,
    name varchar(255),
    range varchar(255),
    removed boolean not null,
    updated timestamp,
    primary key (id)
);

create table subject_students (
    subjects_id int8 not null,
    students_id uuid not null
);

alter table grade 
    add constraint FK5secqnjjwgh9wxk4h1xwgj1n0 
    foreign key (student_id)
    references student;

alter table grade 
    add constraint FKhhw6hbmiyabjlm1jghr00m5d8 
    foreign key (subject_id) 
    references subject;

alter table subject_students 
    add constraint FKshgoyyumnrcs5ymtdukfcuul3 
    foreign key (students_id) 
    references student;

alter table subject_students 
    add constraint FKkrdmpwnc5f3wbcbhrt308vt9y 
    foreign key (subjects_id) 
    references subject;