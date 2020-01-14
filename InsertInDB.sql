USE gestione_centri;


INSERT INTO `AttivitaSportiva` (`codice`, `descrizione`, `durata_prevista`, `periodicita`) VALUES
(1, 'Corso di Karate', 6, 2),
(2, 'Palestra libera', NULL, NULL),
(3, 'Corso di Zumba Fitness', 3, 1),
(4, 'Corso di Calcetto a 5', 12, 4),
(5, 'Sollevamento pesi', NULL, NULL),
(1293, 'Corso di Pattinaggio', 3, 2),
(1930, 'Corso di Hip-hop', 12, 2);

INSERT INTO `Centro` (`nome`, `numero_di_telefono`, `fax`, `citta`, `via`, `cap`) VALUES
('Centro Sportivo Amalfitano', '+39 0811234567', NULL, 'Napoli', 'Via delle Ginestre 15', 80144),
('Centro Sportivo di Maio', '+39 3493020192', NULL, 'Agropoli', 'Via dei Pini 7', 80347),
('Centro Sportivo Graziano', '+39 3312504291', NULL, 'Sorrento', 'Via delle Rose 7', 80067),
('Centro Sportivo Martone', '+39 0811337405', NULL, 'Gragnano', 'Via dei Singoletti 17', 80054),
('Palestra Braccio di Ferro', '+39 0247930490', NULL, 'Milano', 'Piazza Gae Aulenti', 20154),
('Palestra Invictus', '+39 3201920343', NULL, 'Polignano', 'Via Napoli 11', 80300);

INSERT INTO `Struttura` (`codice`, `nome_centro`, `area_occupata`, `attrezzature`, `aperto_chiuso`) VALUES
(9, 'Palestra Braccio di Ferro', 100, NULL, 1),
(88, 'Centro Sportivo Graziano', 100, 1, NULL),
(1111, 'Centro Sportivo Amalfitano', 2500, 0, NULL),
(1111, 'Palestra Braccio di Ferro', 200, 1, NULL),
(1234, 'Centro Sportivo Martone', 1050, 0, NULL),
(1322, 'Centro Sportivo Martone', 2013, NULL, 1),
(1337, 'Palestra Braccio di Ferro', 1700, NULL, 1),
(1911, 'Palestra Invictus', 503, NULL, 1),
(1920, 'Centro Sportivo Graziano', 3023, 1, NULL),
(1920, 'Palestra Invictus', 2032, NULL, 0);
INSERT INTO `Dipendente` (`codice_fiscale`, `nome`, `cognome`, `numero_di_telefono`, `tipo_contratto`, `anni_esperienza`, `documento`, `tipo`) VALUES
('AGSJTF47T10B126Z', 'Augusta', 'Jotta', '+39 3730425071', 'Allenatore', 5, 'mioserver.io/documento/dipendente1.jpg', 'Karate'),
('BLLLND52M67D992N', 'Linda', 'Bellardito', '+393732607253', 'Segretario', 12, NULL, NULL),
('CRBGDU81D14L999Y', 'Guido', 'Carbe', '+39 3730821476', 'Direttore', 10, NULL, NULL),
('LKOFRZ79D22I310Q', 'Fabrizio', 'Oelke', '+39 3738204750', 'Segretario', 7, NULL, NULL),
('MLLLDN57C12L664G', 'Aladino', 'Maiullari', '+393517472037 ', 'Allenatore', 9, NULL, NULL),
('MNTGLN99T25I068F', 'Giuliano', 'Montalesi', '+39 3517472037', 'Segretario', 0, NULL, NULL),
('PDDGLN90E24A560T', 'Giuliano', 'Podda', '+39 058350399', 'Allenatore', 2, 'https://mioserver.io/documento2.jpg', 'Hip-hop'),
('PLMDNS96C51E031O', 'Dionisia', 'Polimeni', '+39 0439994042', 'Allenatore', 5, 'https://mioserver.io/documentodionisia.jpg', 'Pilates'),
('SJALNE54R61L486B', 'Lena', 'Saja', '+39 3505871430', 'Direttore', 2, NULL, NULL),
('SNTPFR78C12D227R', 'Pierferdinando', 'Santo', '+39 3738344207', 'Direttore', 3, NULL, NULL),
('TRDSLF47T10B536Z', 'Astolfo', 'Tarditi', '+39 3738047507', 'Direttore', 2, NULL, NULL),
('TRGTBR62C05E803R', 'Tiberio', 'Tergolli', '+39 3736586142', 'Direttore', 7, NULL, NULL);

INSERT INTO `Coinvolgimento` (`codice_corso`, `codice_allenatore`) VALUES
(1, 'AGSJTF47T10B126Z'),
(3, 'PDDGLN90E24A560T'),
(4, 'AGSJTF47T10B126Z'),
(1293, 'MLLLDN57C12L664G'),
(1930, 'PDDGLN90E24A560T');


INSERT INTO `Direzione` (`nome_centro`, `codice_direttore`) VALUES
('Centro Sportivo Amalfitano', 'TRDSLF47T10B536Z'),
('Centro Sportivo di Maio', 'TRDSLF47T10B536Z'),
('Centro Sportivo Graziano', 'CRBGDU81D14L999Y'),
('Centro Sportivo Graziano', 'SNTPFR78C12D227R'),
('Centro Sportivo Martone', 'CRBGDU81D14L999Y'),
('Palestra Braccio di Ferro', 'SJALNE54R61L486B'),
('Palestra Braccio di Ferro', 'SNTPFR78C12D227R'),
('Palestra Invictus', 'SNTPFR78C12D227R'),
('Palestra Invictus', 'TRDSLF47T10B536Z');


INSERT INTO `Prenotazione` (`ID`, `codice_struttura`, `nome_centro`, `ora`, `data`, `codice_segretario`) VALUES
(1, 1234, 'Centro Sportivo Martone', 12, '2020-01-06', 'MNTGLN99T25I068F'),
(2, 1322, 'Centro Sportivo Martone', 14, '2020-01-06', 'MNTGLN99T25I068F'),
(3, 1920, 'Palestra Invictus', 15, '2020-01-06', 'LKOFRZ79D22I310Q'),
(4, 1911, 'Palestra Invictus', 19, '2020-01-06', 'LKOFRZ79D22I310Q'),
(5, 1920, 'Centro Sportivo Graziano', 9, '2019-10-20', 'BLLLND52M67D992N'),
(6, 1911, 'Palestra Invictus', 13, '2019-11-11', 'BLLLND52M67D992N'),
(7, 1337, 'Palestra Braccio di Ferro', 23, '2020-01-09', 'BLLLND52M67D992N'),
(8, 1234, 'Centro Sportivo Martone', 11, '2019-11-13', 'BLLLND52M67D992N'),
(9, 1911, 'Palestra Invictus', 11, '2019-10-10', 'BLLLND52M67D992N'),
(10, 1920, 'Palestra Invictus', 22, '2020-01-08', 'BLLLND52M67D992N'),
(11, 1234, 'Centro Sportivo Martone', 16, '2019-09-15', 'BLLLND52M67D992N');

INSERT INTO `Svolgimento` (`ID`, `codice_attivita`, `nome_centro`, `codice_struttura`, `data`, `ora`, `durata`) VALUES
(1, 2, 'Centro Sportivo Graziano', 1920, '2020-01-06', 19, 90),
(2, 2, 'Centro Sportivo Martone', 1234, '2020-01-06', 14, 120),
(3, 4, 'Centro Sportivo Martone', 1322, '2020-01-06', 12, 60),
(4, 4, 'Palestra Braccio di Ferro', 9, '2020-01-02', 20, 90),
(5, 1930, 'Centro Sportivo Martone', 1234, '2020-01-09', 15, 120),
(6, 4, 'Palestra Invictus', 1920, '2019-12-10', 20, 60),
(7, 3, 'Palestra Braccio di Ferro', 9, '2020-01-10', 11, 75),
(8, 4, 'Centro Sportivo Martone', 1322, '2020-01-24', 13, 60),
(9, 1293, 'Palestra Braccio di Ferro', 1337, '2019-12-30', 1, 30),
(10, 5, 'Centro Sportivo Graziano', 88, '2019-10-10', 13, 60),
(11, 2, 'Palestra Invictus', 1920, '2020-01-04', 12, 60),
(12, 3, 'Palestra Braccio di Ferro', 1337, '2020-01-07', 14, 60),
(13, 2, 'Palestra Invictus', 1911, '2019-11-11', 20, 60);

INSERT INTO `Ospita` (`codice_attivita`, `nome_centro`) VALUES
(1, 'Centro Sportivo di Maio'),
(1, 'Palestra Invictus'),
(2, 'Centro Sportivo Graziano'),
(2, 'Centro Sportivo Martone'),
(2, 'Palestra Braccio di Ferro'),
(3, 'Palestra Braccio di Ferro'),
(4, 'Centro Sportivo Martone'),
(4, 'Palestra Invictus'),
(5, 'Palestra Invictus'),
(1293, 'Centro Sportivo Amalfitano');

