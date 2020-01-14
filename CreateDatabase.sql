DROP SCHEMA IF EXISTS gestione_centri;
CREATE SCHEMA gestione_centri;
USE gestione_centri;

CREATE TABLE Centro(
	nome VARCHAR(50) PRIMARY KEY,
	numero_di_telefono VARCHAR(14) NOT NULL,
	fax VARCHAR(14),
	citta VARCHAR(30)  NOT NULL,
	via VARCHAR(50)  NOT NULL,
	cap INT NOT NULL
);

CREATE TABLE  AttivitaSportiva( 
	codice INT PRIMARY KEY,
	descrizione VARCHAR(100) NOT NULL,
	durata_prevista INT,
	periodicita INT
);

CREATE TABLE Dipendente(
	codice_fiscale VARCHAR(16) PRIMARY KEY,
	nome VARCHAR(30) NOT NULL,
	cognome VARCHAR(30) NOT NULL,
	numero_di_telefono VARCHAR(14) NOT NULL,
	tipo_contratto VARCHAR(30) NOT NULL,
	anni_esperienza INT,
	documento VARCHAR(100),
	tipo VARCHAR(20)
);



CREATE TABLE Struttura(
	codice INT NOT NULL,
	nome_centro VARCHAR(50) NOT NULL,
	area_occupata INT NOT NULL,
	attrezzature BOOLEAN,
	aperto_chiuso BOOLEAN,
	CONSTRAINT PK_Struttura PRIMARY KEY(codice, nome_centro),
	FOREIGN KEY (nome_centro) REFERENCES Centro(nome) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE
	);




CREATE TABLE Prenotazione(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	codice_struttura INT NOT NULL,
	nome_centro VARCHAR(50) NOT NULL,
	ora INT NOT NULL,
    data DATE NOT NULL,
    codice_segretario  VARCHAR(16) NOT NULL,
    FOREIGN KEY (nome_centro) REFERENCES Struttura(nome_centro) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (codice_struttura) REFERENCES Struttura(codice) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (codice_segretario) REFERENCES Dipendente(codice_fiscale)
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);


CREATE TABLE Direzione(
	nome_centro VARCHAR(50) NOT NULL,
    codice_direttore  VARCHAR(16) NOT NULL,
	CONSTRAINT PK_Direzione	PRIMARY KEY(nome_centro, codice_direttore),
    FOREIGN KEY (codice_direttore) REFERENCES Dipendente(codice_fiscale) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (nome_centro) REFERENCES Centro(nome) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);


CREATE TABLE Coinvolgimento(
	codice_corso INT NOT NULL,
    codice_allenatore VARCHAR(16) NOT NULL,
	CONSTRAINT PK_Corso PRIMARY KEY(codice_corso, codice_allenatore),
    FOREIGN KEY (codice_allenatore) REFERENCES Dipendente(codice_fiscale) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE,
    FOREIGN KEY (codice_corso) REFERENCES AttivitaSportiva(codice) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE
);

CREATE TABLE Ospita(
	codice_attivita INT NOT NULL,
	nome_centro VARCHAR(50) NOT NULL,
	CONSTRAINT PK_Ospita PRIMARY KEY(codice_attivita, nome_centro),
    FOREIGN KEY (codice_attivita) REFERENCES AttivitaSportiva(codice) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    FOREIGN KEY (nome_centro) REFERENCES Centro(nome) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);


CREATE TABLE Svolgimento(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	codice_attivita INT  NOT NULL,
	nome_centro VARCHAR(50) NOT NULL,
	codice_struttura INT NOT NULL,
	data DATE NOT NULL,
	ora INT NOT NULL,
	durata INT NOT NULL,
    FOREIGN KEY (nome_centro) REFERENCES Struttura(nome_centro) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (codice_struttura) REFERENCES Struttura(codice) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    FOREIGN KEY (codice_attivita) REFERENCES AttivitaSportiva(codice) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);

