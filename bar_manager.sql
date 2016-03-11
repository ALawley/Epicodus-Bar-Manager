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
    amount numeric,
    info character varying
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
    price double precision,
    priceperoz numeric
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
    prep_time character varying,
    directions text
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

COPY ingredients (id, type_id, recipe_id, amount, info) FROM stdin;
15	4	9	1.5	light
16	12	9	0.75	fresh 
17	14	9	0.25	
18	4	10	2	light rum
19	4	10	2	dark rum
20	16	10	2	passion juice
21	12	10	0.25	fresh
22	14	10	0.25	
23	2	11	2	
24	17	11	4	
25	12	11	0.5	
26	1	12	2	blended
27	12	12	0.5	
28	6	13	0.75	red wine
29	12	13	0.25	
30	5	8	3	
31	12	8	2	freshly squeezed
32	14	8	1	
33	10	8	1	
34	1	6	1	
35	10	6	1	
36	18	6	4	
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 36, true);


--
-- Data for Name: item_types; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY item_types (id, type) FROM stdin;
1	Whiskey
2	Gin
3	Vodka
4	Rum
5	Tequila
6	Wine
7	Beer
8	Brandy
9	Scotch
10	Liqueur
11	Juice
12	Lime Juice
13	Orange Juice
14	Simply Siryp
15	Coke
16	Fruit Juice
17	Tonic
18	Cranberry Juice
\.


--
-- Name: item_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('item_types_id_seq', 18, true);


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY items (id, name, type_id, amount, price, priceperoz) FROM stdin;
12	Johnnie Walker Blue Label	1	25.36	199.969999999999999	7.885252
13	Johnnie Walker Green Label	9	25.36	59.9699999999999989	2.364747
14	Smirnoff No.21	3	59.17	20.9899999999999984	0.35474
16	Grey Goose	3	59.17	59.990000000000002	0.986331
17	Grey Goose La Poire	3	59.17	59.990000000000002	0.986331
20	Roca Patron Silver	5	25.36	59.990000000000002	2.364747
21	Patron Tequila Gran Patron Platinum	5	25.36	199.990000000000009	7.886041
19	Captain Morgan White Rum	4	25.36	16.9899999999999984	0.669953
18	Original Spiced Rum Captain Morgan	4	25.36	13.9900000000000002	0.551656
15	Smirnoff Raspberry	3	25.36	13.9900000000000002	0.551656
22	Jose Cuervo Especial Gold	5	59.17	29.9699999999999989	0.506507
23	Jose Cuervo Especial Silver	5	25.36	19.9899999999999984	0.788249
24	Fireball Cinnamon Whiskey	10	25.36	19.9899999999999984	0.788249
25	Rhum Barbancourt	4	25.36	19.9899999999999984	0.788249
26	Bacardi Classic Cocktails Pina Colada Rum	4	59.17	17.9899999999999984	0.304039
27	Boulard Calvados	8	25.36	30	1.182965
28	Louis Royer Cognac Extra Grand Champagne	8	23.66	395	16.694844
29	Tanqueray London Dry Gin	2	25.36	20	0.788644
30	The Botanist	2	25.36	39.990000000000002	1.576893
31	Santa Cristina	6	25.36	12	0.473186
11	Johnnie Walker Black Label	1	27.81	49.9699999999999989	1.477965
\.


--
-- Name: items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('items_id_seq', 31, true);


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes (id, name, notes, rating, creator, prep_time, directions) FROM stdin;
7	Jack on Rocks	good	4	idrink.com	4 minutes	Jack on Rocks is made from whiskey and ice
9	Daiquiri	easy to make	\N	Sharron Graham	3 minutes	1.Pour the rum, lime juice and sugar syrup into a cocktail shaker with ice cubes. Shake well. Strain into a chilled cocktail glass and enjoy!
10	Hurricane	two types of rum	\N	Tarick Foteh 	5 minutes	Pour lime juice into cocktail shaker over ice. Pour the remaining ingredients into the shaker. Shake well. Strain into a hurricane glass. Garnish with a cherry and an orange slice.
11	Gin and Tonic	"Nothing says summer like the good ol' G&T!"	\N	Cubby	3 minutes	Place the ice cubes in a tall, narrow glass with the ice coming near the top. Pour gin, tonic water, and lime juice over the ice. Stir well with a long-necked spoon. Garnish with lime wedge, and serve immediately.
12	Whiskey Sour		\N	Kenn Wilson	5 minutes	Add whiskey and lime juice. Shake with ice. Strain into ice-filled old-fashioned glass to serve on the rocks.
13	Red Hot Summer		\N	www.velvetpalate.com	2 minutes	Combine ingredients in a glass filled with ice. Stir and garnish as desired.
8	Margarita	delicious and easy 	5	epicurious.com	6	Combine tequilla, and lime juice in cocktail shaker filled with ice.Shake and strain drink into glass and serve
6	Red Snapper	easy	13	CUK	1	Fill a tall glass with ice. Pour in the whiskey and amaretto, then fill to the top with cranberry juice. Stir and enjoy.
\.


--
-- Name: recipes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_id_seq', 13, true);


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

