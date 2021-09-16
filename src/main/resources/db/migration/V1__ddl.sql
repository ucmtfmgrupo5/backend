CREATE TABLE public.models (
	id UUID NOT NULL,
	"name" varchar NOT NULL,
	object_name varchar NOT NULL,
	repository_name varchar NOT NULL,
	CONSTRAINT models_pk PRIMARY KEY (id),
	CONSTRAINT models_un UNIQUE ("name")
);

CREATE TABLE public.dependencies (
	id uuid NOT NULL,
	model uuid NOT NULL,
	name varchar NOT NULL,
	CONSTRAINT dependencies_pk PRIMARY KEY (id),
	CONSTRAINT dependencies_fk FOREIGN KEY (model) REFERENCES public.models(id)
);

-- Column comments

COMMENT ON COLUMN public.models.id IS 'id of the model';
COMMENT ON COLUMN public.models."name" IS 'model name';
COMMENT ON COLUMN public.models.object_name IS 'model object name';
COMMENT ON COLUMN public.models.repository_name IS 'repository name';