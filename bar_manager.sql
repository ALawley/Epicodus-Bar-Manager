--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE ingredients (
    id integer NOT NULL,
    type_id integer,
    recipe_id integer,
    info character varying,
    amount numeric
);


ALTER TABLE ingredients OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ingredients_id_seq OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ingredients_id_seq OWNED BY ingredients.id;


--
-- Name: item_types; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE item_types (
    id integer NOT NULL,
    type character varying
);


ALTER TABLE item_types OWNER TO "Guest";

--
-- Name: item_types_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE item_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE item_types_id_seq OWNER TO "Guest";

--
-- Name: item_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE item_types_id_seq OWNED BY item_types.id;


--
-- Name: items; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE items (
    id integer NOT NULL,
    name character varying,
    type_id integer,
    amount numeric,
    price double precision
);


ALTER TABLE items OWNER TO "Guest";

--
-- Name: items_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE items_id_seq OWNER TO "Guest";

--
-- Name: items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE items_id_seq OWNED BY items.id;


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes (
    id integer NOT NULL,
    name character varying,
    notes character varying,
    rating integer,
    creator character varying,
    prep_time character varying
);


ALTER TABLE recipes OWNER TO "Guest";

--
-- Name: recipes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_id_seq OWNER TO "Guest";

--
-- Name: recipes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_id_seq OWNED BY recipes.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients ALTER COLUMN id SET DEFAULT nextval('ingredients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY item_types ALTER COLUMN id SET DEFAULT nextval('item_types_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY items ALTER COLUMN id SET DEFAULT nextval('items_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes ALTER COLUMN id SET DEFAULT nextval('recipes_id_seq'::regclass);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients (id, type_id, recipe_id, info, amount) FROM stdin;
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 1, false);


--
-- Data for Name: item_types; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY item_types (id, type) FROM stdin;
\.


--
-- Name: item_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('item_types_id_seq', 1, false);


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY items (id, name, type_id, amount, price) FROM stdin;
\.


--
-- Name: items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('items_id_seq', 1, false);


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes (id, name, notes, rating, creator, prep_time) FROM stdin;
\.


--
-- Name: recipes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_id_seq', 1, false);


--
-- Name: ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: item_types_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY item_types
    ADD CONSTRAINT item_types_pkey PRIMARY KEY (id);


--
-- Name: items_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);


--
-- Name: recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

