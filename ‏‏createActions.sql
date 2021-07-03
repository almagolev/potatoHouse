drop table public.actions;
CREATE TABLE public.actions (
	id serial,
	sub_do_id integer,
	sub_do text,
	sub_on_id integer,
	sub_on text,
	type text,
	info text,
	date timestamp without time zone
);	