drop table public.clients;
CREATE TABLE public.clients (
	id serial primary key,
	id_client text not null unique CHECK (char_length(id_client) = 9),
	first_name text not null CHECK (first_name ~* '[A-Za-z]'),
	last_name text not null CHECK (last_name ~* '[A-Za-z]'),
	phone_number text not null unique CHECK (phone_number ~* '[0-9]'),
	email text not null unique CHECK (email ~* '[A-Za-z0-9._%-]+[@]+[A-Za-z0-9.-]+[.]+[A-Za-z]'),
	home_address text,
	is_active Boolean default true,
	has_health boolean default true,
	gender text not null CHECK (gender = ANY(ARRAY['M','F'])),
	w_sign integer default 0 CHECK (w_canceled > '-1'),
	w_canceled integer default 0 CHECK (w_canceled > '-1'),
	join_date date,
	birthday text,
	last_b_wish text,
	w_done integer default 0 CHECK (w_canceled > '-1'),
	unique (id,gender),
	unique(id,first_name),
	unique(id,last_name)
);