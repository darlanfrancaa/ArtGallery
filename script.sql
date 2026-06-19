SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';
SET default_table_access_method = heap;

CREATE TABLE public.obras (
    id integer NOT NULL,
    titulo character varying(150) NOT NULL,
    autor character varying(150) NOT NULL,
    ativa boolean DEFAULT true,
    tipo character varying(50) NOT NULL
);

CREATE SEQUENCE public.obras_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.obras_id_seq OWNED BY public.obras.id;

ALTER TABLE ONLY public.obras ALTER COLUMN id SET DEFAULT nextval('public.obras_id_seq'::regclass);

CREATE TABLE public.pinturas_digitais (
    obra_id integer NOT NULL,
    resolucao character varying(50) NOT NULL,
    software character varying(100) NOT NULL
);

CREATE TABLE public.artes_generativas (
    obra_id integer NOT NULL,
    algoritmo character varying(100) NOT NULL,
    seed bigint NOT NULL
);

CREATE TABLE public.modelagens_3d (
    obra_id integer NOT NULL,
    poligonos integer NOT NULL,
    engine character varying(100) NOT NULL
);

CREATE TABLE public.avaliacoes (
    id integer NOT NULL,
    obra_id integer NOT NULL,
    usuario character varying(150) NOT NULL,
    nota integer NOT NULL,
    comentario text,
    CONSTRAINT avaliacoes_nota_check CHECK (((nota >= 0) AND (nota <= 10)))
);

CREATE SEQUENCE public.avaliacoes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.avaliacoes_id_seq OWNED BY public.avaliacoes.id;

ALTER TABLE ONLY public.avaliacoes ALTER COLUMN id SET DEFAULT nextval('public.avaliacoes_id_seq'::regclass);

CREATE TABLE public.exposicoes (
    id integer NOT NULL,
    nome character varying(255) NOT NULL
);

CREATE SEQUENCE public.exposicoes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.exposicoes_id_seq OWNED BY public.exposicoes.id;

ALTER TABLE ONLY public.exposicoes ALTER COLUMN id SET DEFAULT nextval('public.exposicoes_id_seq'::regclass);

CREATE TABLE public.exposicoes_obras (
    exposicao_id integer NOT NULL,
    obra_id integer NOT NULL
);

ALTER TABLE ONLY public.obras
    ADD CONSTRAINT obras_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.pinturas_digitais
    ADD CONSTRAINT pinturas_digitais_pkey PRIMARY KEY (obra_id);

ALTER TABLE ONLY public.artes_generativas
    ADD CONSTRAINT artes_generativas_pkey PRIMARY KEY (obra_id);

ALTER TABLE ONLY public.modelagens_3d
    ADD CONSTRAINT modelagens_3d_pkey PRIMARY KEY (obra_id);

ALTER TABLE ONLY public.avaliacoes
    ADD CONSTRAINT avaliacoes_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.exposicoes
    ADD CONSTRAINT exposicoes_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.exposicoes_obras
    ADD CONSTRAINT exposicoes_obras_pkey PRIMARY KEY (exposicao_id, obra_id);

ALTER TABLE ONLY public.pinturas_digitais
    ADD CONSTRAINT pinturas_digitais_obra_id_fkey FOREIGN KEY (obra_id) REFERENCES public.obras(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.artes_generativas
    ADD CONSTRAINT artes_generativas_obra_id_fkey FOREIGN KEY (obra_id) REFERENCES public.obras(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.modelagens_3d
    ADD CONSTRAINT modelagens_3d_obra_id_fkey FOREIGN KEY (obra_id) REFERENCES public.obras(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.avaliacoes
    ADD CONSTRAINT avaliacoes_obra_id_fkey FOREIGN KEY (obra_id) REFERENCES public.obras(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.exposicoes_obras
    ADD CONSTRAINT exposicoes_obras_exposicao_id_fkey FOREIGN KEY (exposicao_id) REFERENCES public.exposicoes(id);

ALTER TABLE ONLY public.exposicoes_obras
    ADD CONSTRAINT exposicoes_obras_obra_id_fkey FOREIGN KEY (obra_id) REFERENCES public.obras(id);
