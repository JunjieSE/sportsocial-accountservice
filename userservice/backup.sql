--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer DEFAULT nextval('public.users_id_seq'::regclass) NOT NULL,
    username character varying(255),
    password character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password) FROM stdin;
0	user	password
1	newuser	$2a$10$05Juu7JlA21/XclzeL4Hj.T/CsTikLbd6BLZA.ABhUwsGO3MGXLqC
2	newuser	$2a$10$GTZPTPaBgQlIw4aDxINuY.SBYgy4P9WPO7Nfv3a.k/f9RBGxqz0Im
3	userPasswordIssecurepassword	$2a$10$EWHPuWwBCz0oIjqF.mT.2eq2NUhkuU8h6/66myJSNIaGxSpSkGWYG
4	1234	$2a$12$hTktQgBbgaLRRxsHGJLew.QrL/x/s9GhetBG.9TKq0k8V2wGRvrsC
5	5555	$2a$12$jo.dIiKd9XhI.A1jtESTkuvkbeFzgff9ESkLm.8RpqougnCJIWfwq
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 5, true);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

