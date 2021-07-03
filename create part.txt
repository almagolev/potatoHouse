drop table public.part;
CREATE TABLE public.part (
	id serial primary key,
	c_id integer,
	w_id integer,
	s_id integer,
	w_title text,
	w_date text,
	w_start text,
	w_end text,
	c_came text check (c_came = any(array['Y','N',''])),
	c_phone text,
	c_gender text,
	c_first text,
	c_last text,
	unique (c_id,w_id,s_id),
	foreign key (c_id) references public.clients(id) on update cascade on delete cascade,
	foreign key (w_id) references public.workouts(id) on update cascade on delete cascade,
	foreign key (s_id) references public.schedule(id) on update cascade on delete cascade,
	foreign key (c_phone) 		references public.clients(phone_number) on update cascade on delete cascade,
	foreign key (c_id,c_first) 	references public.clients(id,first_name) on update cascade on delete cascade,
	foreign key (c_id,c_last) 	references public.clients(id,last_name) on update cascade on delete cascade,
	foreign key (c_id,c_gender) references public.clients(id,gender) on update cascade on delete cascade
	);