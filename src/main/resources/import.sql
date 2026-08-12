-- Dati di prova del Festival di Ferento.
-- Eseguito da Hibernate a ogni avvio (ddl-auto=create), dopo la creazione
-- delle tabelle. IMPORTANTE: ogni istruzione deve stare su UNA SOLA RIGA,
-- perche' Hibernate legge import.sql una riga = una istruzione.
-- Nelle tabelle padre non inseriamo l'id: lo genera il DB in sequenza.
-- Un apostrofo dentro una stringa si raddoppia ('' -> ').

insert into utente (username, password, ruolo) values ('admin', '$2b$10$afy6WaIY0UmQrwVpKpyDcug3w76jbm0eFrDPf.K9mxbe.UM4n1z6O', 'ADMIN');
insert into utente (username, password, ruolo) values ('mario', '$2b$10$uYIzk4XjLWPBhoiq482Vgei88pqcLwiM0s5fSoXR8xlczCAcODw4y', 'USER');

insert into artista (nome, descrizione) values ('Compagnia del Teatro Greco', 'Compagnia specializzata nella tragedia classica greca, con allestimenti fedeli alla tradizione.');
insert into artista (nome, descrizione) values ('Ensemble Corpo Libero', 'Collettivo di danza contemporanea che intreccia gesto, musica e spazio scenico.');
insert into artista (nome, descrizione) values ('Orchestra Sinfonica della Tuscia', 'Orchestra del territorio viterbese, dal repertorio sinfonico al grande spettacolo dal vivo.');
insert into artista (nome, descrizione) values ('Compagnia Lirica Volsinii', 'Compagnia lirica dedicata ai titoli più amati del melodramma.');

insert into settore (nome, prezzo, capienza) values ('Gradinata Alta', 15.00, 200);
insert into settore (nome, prezzo, capienza) values ('Gradinata Bassa', 25.00, 150);
insert into settore (nome, prezzo, capienza) values ('Orchestra', 40.00, 80);

insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Edipo Re', 'Signore di Tebe, Edipo scopre a poco a poco di essere lui stesso l''assassino che cerca, colpevole senza saperlo del delitto che ha gettato la citta'' nella peste. La tragedia di Sofocle intreccia indagine e destino in un meccanismo implacabile, dove ogni passo verso la verita'' e'' un passo verso la rovina. Un capolavoro assoluto sul limite della conoscenza umana e sul peso del fato.', '2026-07-12 21:00:00', 'TEATRO', 'edipo-re.svg', 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Antigone', 'Contro l''ordine del re Creonte, Antigone sceglie di dare sepoltura al fratello ribelle, opponendo la legge non scritta degli affetti e degli dei a quella dello Stato. Da questo gesto nasce uno scontro insanabile tra due idee di giustizia, entrambe convinte di aver ragione. Sofocle firma una delle piu'' celebri riflessioni di sempre sull''obbedienza, la coscienza e il prezzo della fedelta'' ai propri principi.', '2026-07-19 21:00:00', 'TEATRO', 'antigone.svg', 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Le Baccanti', 'Il dio Dioniso torna a Tebe per farsi riconoscere, e la citta'' che lo rifiuta viene travolta dalla forza indomabile del suo culto. Tra estasi, illusione e furore, Euripide mette in scena il conflitto tra ragione e istinto, ordine e liberazione, con un finale di sconvolgente potenza. Un dramma antichissimo eppure modernissimo sul divino che non si lascia negare.', '2026-07-26 21:00:00', 'TEATRO', 'le-baccanti.svg', 1);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Corpi nello Spazio', 'Una coreografia contemporanea che trasforma la scena in un dialogo tra il corpo e la pietra antica del teatro. I danzatori disegnano nello spazio linee di tensione e abbandono, cercando l''equilibrio tra gravita'' e volo. Uno spettacolo che intreccia gesto, musica e architettura in un''unica, ininterrotta respirazione.', '2026-08-02 21:30:00', 'DANZA', 'corpi-nello-spazio.svg', 2);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Notte di Mezza Estate', 'Sotto il cielo stellato della Tuscia, la danza si fa rito notturno e sogno collettivo. Ispirato all''atmosfera incantata della notte di mezza estate, lo spettacolo alterna momenti lirici e slanci corali, tra luce lunare e ombre danzanti. Un invito a smarrirsi, per una sera, nella magia del teatro all''aperto.', '2026-08-09 21:30:00', 'DANZA', 'notte-di-mezza-estate.svg', 2);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Concerto sotto le Stelle', 'Le grandi pagine del repertorio sinfonico risuonano dal vivo tra le gradinate del teatro romano, in una serata che unisce musica e memoria del luogo. Dall''intensita'' dei classici alle melodie piu'' amate, l''orchestra guida il pubblico in un viaggio d''ascolto sotto il cielo aperto. Un''esperienza in cui l''acustica millenaria del sito diventa parte dello spettacolo.', '2026-08-16 21:00:00', 'MUSICA', 'concerto-sotto-le-stelle.svg', 3);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Carmina Burana', 'La celebre cantata scenica di Carl Orff prende vita con coro e orchestra, aprendo e chiudendo con il travolgente ''O Fortuna''. Canti medievali di fortuna, primavera, amore e convivio si susseguono in un crescendo di energia ritmica e vocale. Uno degli spettacoli musicali piu'' potenti e coinvolgenti di sempre, nella cornice ideale del teatro antico.', '2026-08-23 21:00:00', 'MUSICA', 'carmina-burana.svg', 3);
insert into spettacolo (titolo, descrizione, data_ora, genere, immagine, artista_id) values ('Aida', 'Il capolavoro di Giuseppe Verdi rivive in una messa in scena suggestiva e monumentale, tra l''amore impossibile della schiava Aida e il richiamo del dovere e della patria. Grandi cori, arie immortali e un dramma di passioni contrapposte si intrecciano sullo sfondo dell''antico Egitto. Un''opera di travolgente bellezza che chiude in grande il cartellone del festival.', '2026-08-30 21:00:00', 'OPERA', 'aida.svg', 4);

insert into recensione (testo, voto, utente_id, spettacolo_id) values ('Interpretazione potente, un''ora e mezza di pura tensione.', 5, 2, 1);
insert into recensione (testo, voto, utente_id, spettacolo_id) values ('Regia essenziale ma molto efficace, consigliato.', 4, 1, 1);
insert into recensione (testo, voto, utente_id, spettacolo_id) values ('Serata magica sotto le stelle, orchestra impeccabile.', 5, 2, 6);

insert into biglietto (quantita, data_acquisto, utente_id, spettacolo_id, settore_id) values (3, '2026-06-01 10:00:00', 2, 1, 1);
insert into biglietto (quantita, data_acquisto, utente_id, spettacolo_id, settore_id) values (2, '2026-06-02 15:30:00', 2, 1, 3);