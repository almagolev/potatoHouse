drop table public.workouts;
CREATE TABLE public.workouts (
	id serial primary key,
	title text not null unique,
	max_trainers integer not null  check(max_trainers > 0 and max_trainers <= 50),
	min_age integer default 14,
	length_min integer not null check (length_min = any(array[45,50,55,60])),
	difficulty text,
	many_times integer default 0 check(many_times > -1),
	is_active boolean default true,
	added_date date default now(),
	unique(id,title,max_trainers,length_min)
);