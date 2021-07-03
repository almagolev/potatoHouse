drop table public.schedule;
CREATE TABLE public.schedule (
	id serial primary key,
	w_id integer not null,
	w_title text not null,
	w_date text not null,
	w_max integer not null,
	w_signed integer default 0 check(w_signed > -1),
	w_start text check (w_start ~~ '%_%_:%_%_'),
	w_end text,
	w_length integer,
	w_canceled integer  default 0  check(w_canceled > -1),
	w_done integer  default 0 check(w_done > -1),
	unique (w_id, w_date, w_start, w_end),
	foreign key (w_title) references public.workouts(title) on update cascade
	);