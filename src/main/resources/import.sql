-- ============================================================
-- Dati di prova del Festival di Ferento.
-- Eseguito automaticamente da Hibernate a ogni avvio, subito dopo
-- la (ri)creazione delle tabelle (spring.jpa.hibernate.ddl-auto=create).
--
-- Nelle tabelle "padre" (utente, artista, settore) NON inseriamo l'id:
-- lo genera il database in sequenza (1, 2, 3... nell'ordine di
-- inserimento). Negli spettacoli usiamo quei numeri come artista_id.
--
-- Nota SQL: un apostrofo dentro una stringa si raddoppia ('' -> ').
-- ============================================================

-- ---------- UTENTI ----------
-- Le password sono hash BCrypt (mai in chiaro). In chiaro sono:
--   admin -> admin123     mario -> mario123
insert into utente (username, password, ruolo) values
    ('admin', '$2b$10$afy6WaIY0UmQrwVpKpyDcug3w76jbm0eFrDPf.K9mxbe.UM4n1z6O', 'ADMIN');
insert into utente (username, password, ruolo) values
    ('mario', '$2b$10$uYIzk4XjLWPBhoiq482Vgei88pqcLwiM0s5fSoXR8xlczCAcODw4y', 'USER');

-- ---------- ARTISTI (id 1..4) ----------
insert into artista (nome, descrizione) values
    ('Compagnia del Teatro Greco', 'Compagnia specializzata nella tragedia classica greca, con allestimenti fedeli alla tradizione.');
insert into artista (nome, descrizione) values
    ('Ensemble Corpo Libero', 'Collettivo di danza contemporanea che intreccia gesto, musica e spazio scenico.');
insert into artista (nome, descrizione) values
    ('Orchestra Sinfonica della Tuscia', 'Orchestra del territorio viterbese, dal repertorio sinfonico al grande spettacolo dal vivo.');
insert into artista (nome, descrizione) values
    ('Compagnia Lirica Volsinii', 'Compagnia lirica dedicata ai titoli più amati del melodramma.');

-- ---------- SETTORI (id 1..3) ----------
-- Condivisi tra tutti gli spettacoli, perché la location è fissa.
insert into settore (nome, prezzo, capienza) values ('Gradinata Alta', 15.00, 200);
insert into settore (nome, prezzo, capienza) values ('Gradinata Bassa', 25.00, 150);
insert into settore (nome, prezzo, capienza) values ('Orchestra', 40.00, 80);

-- ---------- SPETTACOLI ----------
-- artista_id fa riferimento all'ordine di inserimento degli artisti sopra.
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Edipo Re', 'La tragedia di Sofocle sul destino ineluttabile del re di Tebe.', '2026-07-12 21:00:00', 'TEATRO', null, 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Antigone', 'Il conflitto tra la legge degli uomini e quella degli dei.', '2026-07-19 21:00:00', 'TEATRO', null, 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Le Baccanti', 'Il dramma di Euripide sull''arrivo del culto di Dioniso a Tebe.', '2026-07-26 21:00:00', 'TEATRO', null, 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Corpi nello Spazio', 'Coreografia contemporanea che dialoga con le pietre del teatro antico.', '2026-08-02 21:30:00', 'DANZA', null, 2);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Notte di Mezza Estate', 'Un percorso di danza sotto il cielo stellato della Tuscia.', '2026-08-09 21:30:00', 'DANZA', null, 2);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Concerto sotto le Stelle', 'Grandi pagine sinfoniche eseguite dal vivo nel teatro romano.', '2026-08-16 21:00:00', 'MUSICA', null, 3);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Carmina Burana', 'La celebre cantata scenica di Carl Orff per coro e orchestra.', '2026-08-23 21:00:00', 'MUSICA', null, 3);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values
    ('Aida', 'Il capolavoro di Verdi in una messa in scena suggestiva e monumentale.', '2026-08-30 21:00:00', 'OPERA', null, 4);