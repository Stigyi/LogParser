CREATE TABLE logparser.log (
	id int8 NOT NULL,
	calling_class VARCHAR(255) NULL,
	log_level VARCHAR(255) NULL,
	log_time TIMESTAMP NULL,
	"text" VARCHAR NULL,
	CONSTRAINT log_pkey PRIMARY KEY (id)
);

CREATE TABLE logparser.non_parsable_log (
	id int8 NOT NULL,
	app_exception VARCHAR(100000) NULL,
	raw_text VARCHAR NULL,
	CONSTRAINT log_error_pkey PRIMARY KEY (id)
);

create sequence logparser.non_parsable_log_seq start 1 increment 50;
create sequence logparser.log_seq start 1 increment 50;