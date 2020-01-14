package me.eleuna.progetto;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class CentroSportivo {

    Scanner sc;
    DbHandler db;

    public CentroSportivo() {
        sc = new Scanner(System.in);
        String username = "root";
        String password = "";
        String host = "127.0.0.1:3306/gestione_centri";

        try{
            db = new DbHandler(host, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void avanti() {
        System.out.println("\nOperazione terminata, premere invio per continuare...");
        sc.nextLine();
        sc.nextLine();
    }

    private String getAllenatore(String specializzazione) {
        String codice_allenatore = "";
        int index = 1;
        ResultSet result = null;
        try {
            if(specializzazione != null) {
                String query = "Select * From Dipendente where tipo_contratto = 'Allenatore' AND tipo = ?";
                PreparedStatement statement = db.getConnection().prepareStatement(query);
                statement.setString(1, specializzazione);
                result = statement.executeQuery();
            } else {
                String query = "Select * From Dipendente where tipo_contratto = 'Allenatore'";
                PreparedStatement statement = db.getConnection().prepareStatement(query);
                result = statement.executeQuery();
            }

            if (!result.isBeforeFirst()) {
                //ritorno "" per far capire che non ci sono allenatori disponibili per quel corso
                //come da requisito non funzionale
                return codice_allenatore;
            } else {
                result.next();
                do {
                    System.out.println("\n******** DIPENDENTE ********\n");
                    System.out.println("Indice per selezionare: " + index);
                    System.out.println("CF: " + result.getString("codice_fiscale"));
                    System.out.println("Nome: " + result.getString("nome"));
                    System.out.println("Cognome: " + result.getString("cognome"));
                    System.out.println("Num. telefono: " + result.getString("numero_di_telefono"));
                    System.out.println("Anni di esperienza: " + result.getInt("anni_esperienza"));
                    System.out.println("Tipo specializzazione: " + result.getString("tipo"));
                    System.out.println("Riferimento al documento: " + result.getString("documento"));
                    index++;
                } while (result.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int selectedIndex = sc.nextInt();
        while(selectedIndex > index || selectedIndex < 1) {
            System.out.println("Scelta non valida");
            System.out.println("Inserisci l'indice dell'allenatore da selezionare: ");
            selectedIndex = sc.nextInt();
        }
        try {
            if(result != null) {
                result.absolute(selectedIndex);
                return result.getString("codice_fiscale");
            } else {
                return "";
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private Corso getInfoCorso(){


        System.out.println("Inserisci codice corso: ");
        int codice = sc.nextInt();

        sc.nextLine();

        System.out.println("Inserisci la descrizione: ");
        String descrizione = sc.nextLine();

        System.out.println("Inserisci la durata prevista: ");
        int durata_prevista = sc.nextInt();

        System.out.println("Inserisci la periodicità: ");
        int periodicita = sc.nextInt();


            return new Corso(codice, descrizione, durata_prevista, periodicita);
    }

    // lo lasciamo per fini di debug
    final private static void printResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    private String getSegretario() {
        String segretario = "";
        ResultSet segretari;
        try {
            segretari = db.executeQuery("Select codice_fiscale from dipendente where tipo_contratto='segretario'");
            segretari.absolute(1);
            segretario = segretari.getString("codice_fiscale");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return segretario;
    }

    private String getDataFromInput() {
        String data = "";
        do {
            System.out.println("Inserisci una data (AAAA-MM-GG): ");
            data = sc.nextLine();
            if(!dataValida(data) && data != "") {
                System.out.println("Errore, formatta correttamente la data...");
            }

        }while(!dataValida(data));
        return data;
    }

    private boolean dataValida(String data) {
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private String getCfDipendente() {
        System.out.print("Inserire il CF del dipendente: ");
        return sc.nextLine();
    }

    private int getOraFromInput() {
        int ora;
        do {
            System.out.println("Inserisci l'ora (hh): ");
            ora = sc.nextInt();
            if(ora < 0 || ora > 23) {
                System.out.println("Errore, formatta correttamente l'ora (0-23) ...");
            }

        }while(ora < 0 || ora > 23);
        return ora;
    }

    private void cancellaDirettore(String nome_centro, String codice_direttore) {

        try {
            String query = "DELETE FROM Direzione WHERE nome_centro = ? AND codice_direttore = ?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, nome_centro);
            statement.setString(2, codice_direttore);

            statement.executeUpdate();

            System.out.println("Direttore e' stato rimosso con successo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void promuoviDipendente(String nome_centro, String codice_dipendente) {
        try {
            String query = "INSERT INTO Direzione(nome_centro, codice_direttore) VALUES(?,?) ON DUPLICATE KEY UPDATE ID = ID ;";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, nome_centro);
            statement.setString(2, codice_dipendente);
            statement.executeUpdate();

            query = "UPDATE Dipendente SET tipo_contratto = 'Direttore' WHERE codice_fiscale = ?;";
            PreparedStatement updateStatement = db.getConnection().prepareStatement(query);
            statement.setString(1, codice_dipendente);

            System.out.println("Nuovo direttore aggiunto...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getAttivitaFromInput() {
        ResultSet attivita;
        int attivitaIndex = 1;
        int attivitaNum = -1;
        try {
            attivita = db.executeQuery("Select codice, descrizione from attivitasportiva");
            do {
                while(attivita.next()) {
                    System.out.println(attivitaIndex + ": "+ attivita.getInt("codice") + " | " + attivita.getString("descrizione"));
                    attivitaIndex++;
                }
                System.out.print("Inserisci il num. dell'attivita' da svolgere: ");
                attivitaNum = sc.nextInt();
                sc.nextLine();
                if(attivitaNum < 0 || attivitaNum > attivitaIndex) {
                    System.out.println("Errore... inserire valore valido");
                }
            }while(attivitaNum < 0 || attivitaNum > attivitaIndex);

            attivita.absolute(attivitaNum);
            return attivita.getInt("codice");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;


    }

    private String getCentroFromInput() {
        try {
            ResultSet centro = db.executeQuery("Select nome from centro");
            int centroIndex = 1;
            while(centro.next()) {
                System.out.println(centroIndex + ": "+ centro.getString("nome"));
                centroIndex++;
            }
            System.out.print("Inserisci il nome del centro: ");
            int centroNum = sc.nextInt();
            centro.absolute(centroNum);
            String nome_centro = centro.getString("nome");
            return nome_centro;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return "";
    }



    private HashMap<String, String> getStrutturaFromInput(){
        HashMap<String, String> struttura = new HashMap<String, String>();
        struttura.put("codice","");
        struttura.put("nome_centro","");

        String query = "";

        System.out.println("Ti interessa una sala o un campo? \n 0. Sala \n 1. Campo");
        int sceltaCampo = sc.nextInt();
        switch(sceltaCampo) {
            case 0:
                System.out.println("Ti interessano le attrezzature? \n 0. No \n 1. Si");
                int sceltaAttrezzature = sc.nextInt();
                if(sceltaAttrezzature == 0) {
                    query = "Select codice, nome_centro from struttura WHERE attrezzature = 0";
                } else if(sceltaAttrezzature == 1) {
                    query = "Select codice, nome_centro from struttura WHERE attrezzature = 1";
                } else {
                    System.out.println("Scelta non valida, per favore riprovare.");
                    sceltaAttrezzature = sc.nextInt();
                }
                break;
            case 1:
                System.out.println("Ti interessa il campo all'aperto o al chiuso? \n 0. Chiuso \n 1. Aperto");
                int sceltaAperto = sc.nextInt();
                if(sceltaAperto == 0) {
                    query = "Select codice, nome_centro from struttura WHERE aperto_chiuso = 0";
                } else if(sceltaAperto == 1) {
                    query = "Select codice, nome_centro from struttura WHERE aperto_chiuso = 1";
                } else {
                    System.out.println("Scelta non valida, per favore riprovare.");
                    sceltaAperto = sc.nextInt();
                }
                break;
            default:
                System.out.println("Scelta non valida! Inserire un valore tra 0 e 1.");
                break;

        }
        System.out.println("\nScegli la struttura: ");

        try {
            ResultSet res = db.executeQuery(query);
            int indexStruttura = 0, index = -1;

            do {
                if(index != -1) {
                    System.out.println("Errore... inserire valore valido");
                }
                index = 1;
                if(!res.isBeforeFirst()) {
                    System.out.println("Non ho trovato strutture compatibili con la tua ricerca");
                    return null;
                }
                while(res.next()) {

                    System.out.println(index + ": "+ res.getInt("codice") + " | " + res.getString("nome_centro"));
                    index++;
                }

                System.out.print("Inserisci il num. della struttura: ");
                indexStruttura = sc.nextInt();
                sc.nextLine(); // pulisco il buffer

                if(indexStruttura > 0 && indexStruttura <= index) {
                    res.absolute(indexStruttura);

                    struttura.put("codice", String.valueOf(res.getInt("codice")));
                    struttura.put("nome_centro", res.getString("nome_centro"));
                }

            }while(indexStruttura < 0 || indexStruttura > index);

        }catch(Exception e) {
            e.printStackTrace();
        }

        return struttura;
    }
    /**
     * @return true se sono riuscito a prenotale la struttura, false altrimenti*/
    public boolean prenotazioneStruttura() { //1 -
        System.out.println("");

        String codice_segretario = getSegretario();

        HashMap<String, String> struttura = getStrutturaFromInput();
        if (struttura != null) {

        try {

                String data = getDataFromInput();
                int ora = getOraFromInput();

                String query = "INSERT INTO  prenotazione(codice_struttura, nome_centro, codice_segretario, data, ora) VALUES "
                        + "(?,?,?,?,?)";

                PreparedStatement statement = db.getConnection().prepareStatement(query);
                statement.setInt(1, Integer.parseInt(struttura.get("codice")));
                statement.setString(2, struttura.get("nome_centro"));
                statement.setString(3, codice_segretario);
                statement.setDate(4, java.sql.Date.valueOf(data));
                statement.setInt(5, ora);

                statement.executeUpdate();
                avanti();
            } catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPrenotazione() { //2 -
        System.out.println("");
        try {
            HashMap<String, String> struttura = getStrutturaFromInput();
            //System.out.println("Struttura selezionata: (codice struttura: " + struttura.get("codice") + " | nome centro: "+ struttura.get("nome_centro") + ")" );

            String data = getDataFromInput();
            int ora = getOraFromInput();

            String query = "SELECT * FROM prenotazione WHERE codice_struttura=? AND nome_centro=? AND data=? AND ora=?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(struttura.get("codice")));
            statement.setString(2, struttura.get("nome_centro"));
            statement.setDate(3, java.sql.Date.valueOf(data));
            statement.setInt(4, ora);
            //System.out.println(statement);

            ResultSet result = statement.executeQuery();

            if(result.next() == false) {
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void orariDisponibili() { //3 -
        //Dato che non sono stati forniti gli orari di apertura e chiusira, mi limito a stampare gli orari prenotati

        System.out.println("");
        try {
            HashMap<String, String> struttura = getStrutturaFromInput();
            //System.out.println("Struttura selezionata: (codice struttura: " + struttura.get("codice") + " | nome centro: "+ struttura.get("nome_centro") + ")" );

            String data = getDataFromInput();

            String query = "SELECT * FROM prenotazione WHERE codice_struttura=? AND nome_centro=? AND data=?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(struttura.get("codice")));
            statement.setString(2, struttura.get("nome_centro"));
            statement.setDate(3, java.sql.Date.valueOf(data));

            ResultSet result = statement.executeQuery();
            System.out.println("Ecco le ore occupate, tutte le altre sono libere: ");
            while(result.next()) {
                System.out.println(result.getInt("ora"));
            }

            avanti();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giorniDisponibili() { //4 -
        //Non avendo a disposizione i giorni di chiusura delle strutture, non posso sottrarli a quelli gia' prenotati per ottenere i prenotabili
        System.out.println("");
        try {
            HashMap<String, String> struttura = getStrutturaFromInput();

            int ora = getOraFromInput();

            String query = "SELECT data FROM prenotazione WHERE codice_struttura=? AND nome_centro=? AND ora=?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(struttura.get("codice")));
            statement.setString(2, struttura.get("nome_centro"));
            statement.setInt(3, ora);
            //System.out.println(statement);
            ResultSet result = statement.executeQuery();

            System.out.println("Ecco le date occupate, tutte le altre sono libere: ");
            while(result.next()) {
                System.out.println(result.getDate("data"));
            }

            //System.out.println(formattedQuery);
            avanti();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean svolgimentoAttivita() { //5 -
        System.out.println("");
        try {
            HashMap<String, String> struttura = getStrutturaFromInput();
            int codice_attivita = getAttivitaFromInput();

            String data = getDataFromInput();
            int ora = getOraFromInput();

            System.out.print("Aggiungi la durata (in minuti): ");
            int durata = sc.nextInt();

            String query = "INSERT INTO  svolgimento(codice_struttura, nome_centro, codice_attivita, data, ora, durata) VALUES "
                    + "(?,?,?,?,?,?)";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(struttura.get("codice")));
            statement.setString(2, struttura.get("nome_centro"));
            statement.setInt(3, codice_attivita);
            statement.setDate(4, java.sql.Date.valueOf(data));
            statement.setInt(5, ora);
            statement.setInt(6, durata);

            statement.executeUpdate();

            avanti();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void oreSvolgimentoAttivita() { //6 -
        System.out.println("");
        try {

            System.out.print("Inserisci l'anno da cercare (AAAA): ");
            int anno = sc.nextInt();
            String data = String.valueOf(anno) + "-01-01";
            String dataAnnoDopo = String.valueOf(anno+1) + "-01-01";

            String query = "Select sum(durata) as durata, descrizione " +
                    "from attivitasportiva JOIN svolgimento ON codice_attivita = attivitasportiva.codice " +
                    " where data >= ? AND data < ? group by attivitasportiva.codice";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(data));
            statement.setDate(2, java.sql.Date.valueOf(dataAnnoDopo));
            //System.out.println(statement);
            ResultSet result = statement.executeQuery();
            System.out.println("");

            while(result.next()) {
                System.out.println("Nome attivita' "+result.getString("descrizione") + " | Durata (in ore): " + (result.getInt("durata")/60));
            }

            avanti();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abilitaCentro() { //7 -
        System.out.println("");
        try {
            String nome_centro = getCentroFromInput();

            int codice_attivita = getAttivitaFromInput();

            String query = "INSERT INTO ospita(nome_centro, codice_attivita) VALUES (?,?)";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, nome_centro);
            statement.setInt(2, codice_attivita);

            statement.executeUpdate();

            System.out.println("Centro abilitato...");
            avanti();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void aggiungiAllenatore() { // 8-
        String query = "";
        System.out.print("Inserire il codice fiscale: ");
        String cf_allenatore = sc.nextLine();
        System.out.print("Inserire il nome: ");
        String nome = sc.nextLine();
        System.out.print("Inserire il cognome: ");
        String cognome = sc.nextLine();
        System.out.print("Inserisci il numero di telefono (formato es.: +393312345678) ");
        String numero_telefono = sc.nextLine();
        System.out.print("Inserisci gli anni di esperienza");
        int anni_esperienza = sc.nextInt();
        System.out.print("L'allenatore � specializzato? \n 0 per no \n 1 per si :");
        int spec = sc.nextInt();
        sc.nextLine(); // pulisco il buffer
        String documento = null;
        String tipo_specializzazione = null;

        if(spec == 1) {

            System.out.println("Inserisci il tipo di specializzazione: ");
            tipo_specializzazione = sc.nextLine();
            System.out.println("Inserisci il riferimento al documento della specializzazione: ");
            documento = sc.nextLine();
        }
        if(spec != 0 || spec != 1){
            System.out.println("Input per la specializzazione non valido! \n Per favore inserire solo 0 o 1");
        }
        try {
            query = "INSERT INTO Dipendente(codice_fiscale, nome, cognome, numero_di_telefono, "
                    + "anni_esperienza, tipo_contratto, tipo, documento) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, cf_allenatore);
            statement.setString(2, nome);
            statement.setString(3, cognome);
            statement.setString(4, numero_telefono);
            statement.setInt(5, anni_esperienza);
            statement.setString(6, "allenatore");
            statement.setString(7, tipo_specializzazione);
            statement.setString(8, documento);

            statement.executeUpdate();
            System.out.println("Allenatore aggiunto...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stampaStruttura() { //9
        System.out.println("Inserisci l'anno sul quale eseguire la ricerca: ");
        int anno = sc.nextInt();
        String annoCorrente = anno + "-01-01";
        String annoDopo = anno + "-12-31";

        String query = "SELECT `codice_struttura`,`nome_centro` , NUM_ATT\r\n" +
                "    from(SELECT `codice_struttura`,`nome_centro`, count(*) as NUM_ATT\r\n" +
                "    FROM `svolgimento`\r\n" +
                "    WHERE data >= '"+annoCorrente+"' AND data <= '"+annoDopo+"'\r\n" +
                "    GROUP BY `nome_centro`,`codice_struttura`) AS T\r\n" +
                "    order by NUM_ATT DESC";

        PreparedStatement statement;
        try {
            statement = db.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if(result.next() != false) {
                System.out.println("Id struttura: " + result.getString("codice_struttura") + " |  Nome centro: " + result.getString("nome_centro"));
            }else {
                System.out.println("Non ho trovato la struttura per quest'anno...");
            }
            avanti();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void visualizzaAllenatoriSpecializzati() { //10 -
        System.out.print("Inserire la disciplina di cui vuoi vedere gli allenatori: ");
        String specializzazione = sc.nextLine();

        String query = "SELECT * FROM Dipendente WHERE tipo =?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, specializzazione);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                System.out.println("\n******** DIPENDENTE ********\n");
                System.out.println("CF: "+ result.getString("codice_fiscale"));
                System.out.println("Nome: " + result.getString("nome"));
                System.out.println("Cognome: " + result.getString("cognome"));
                System.out.println("Num. telefono: " + result.getString("numero_di_telefono"));
                System.out.println("Anni di esperienza: " + result.getInt("anni_esperienza"));
                System.out.println("Tipo specializzazione: " + result.getString("tipo"));
                System.out.println("Riferimento al documento: " + result.getString("documento"));
            }
            avanti();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void cancellazioneDirettore() { //11
        String nome_centro = getCentroFromInput();

        sc.nextLine();

        System.out.println("DIRETTORE DA RIMUOVERE: ");
        String codice_direttore = getCfDipendente();

        cancellaDirettore(nome_centro, codice_direttore);

        System.out.println("DIPENDENTE DA PROMUOVERE A DIRETTORE: ");
        String codice_dipendente = getCfDipendente();

        promuoviDipendente(nome_centro, codice_dipendente);
        avanti();
    }


    public boolean modificaPrenotazione() { //12

        System.out.println("Ricerca la prenotazione:");
        String vecchiaData = getDataFromInput();
        int vecchiaOra = getOraFromInput();
        System.out.println("Valore di vecchiaData: " + vecchiaData + "\n Valore di vecchiaOra: " + vecchiaOra + "\n");
        HashMap<String, String> struttura = getStrutturaFromInput();

        System.out.print("Inserisci il CF di chi ha riservato la prenotazione: ");
        String codice_segretario = sc.nextLine();

        try {
            String query = "SELECT * FROM Prenotazione WHERE data = ? AND ora =? AND nome_centro = ? AND codice_struttura = ? AND codice_segretario = ?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(vecchiaData));
            statement.setInt(2, vecchiaOra);
            statement.setString(3, struttura.get("nome_centro"));
            statement.setInt(4, Integer.parseInt(struttura.get("codice")));
            statement.setString(5, codice_segretario);
            System.out.println(Integer.parseInt(struttura.get("codice")));
            ResultSet results = statement.executeQuery();
            //Assumo che la prenotazione esista
            if(results.next() == true) {
                System.out.println(String.format("Prenotazione corrente: \n " +
                                "Nome centro: %s\n " +
                                "Codice struttura: %s \n " +
                                "Data: %s \n " +
                                "Ora: %d \n " +
                                "CF Segretario: %s \n", results.getString("nome_centro"),
                        results.getString("codice_struttura"),
                        results.getString("data"),
                        results.getInt("ora"),
                        results.getString("codice_segretario")));

            } else {
                System.out.println("\n Nessuna prenotazione trovata coi dati inseriti");
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("---------------");
        System.out.print("Inserisci la data in cui vuoi spostare la prenotazione (AAAA-MM-GG) :");
        String nuovaData = sc.nextLine();
        System.out.print("Inserisci l'ora in cui vuoi spostare la prenotazione :");
        int nuovaOra = sc.nextInt();

        try {
            String query = "SELECT * FROM Prenotazione WHERE data = ? AND ora = ? AND nome_centro = ? AND codice_struttura = ? AND codice_segretario = ?";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(nuovaData));
            statement.setInt(2, nuovaOra);
            statement.setString(3, struttura.get("nome_centro"));
            statement.setInt(4, Integer.parseInt(struttura.get("codice")));
            statement.setString(5, codice_segretario);

            ResultSet results = statement.executeQuery();


            if(results.next() == false) {  //presumo che non ci siano prenotazioni corrispondenti ai nuovi dati
                query = "UPDATE Prenotazione SET data = ?, ora = ? WHERE codice_struttura = ? AND nome_centro = ? AND codice_segretario = ? AND data = ? AND ora = ?";

                statement = db.getConnection().prepareStatement(query);
                statement.setDate(1, java.sql.Date.valueOf(nuovaData));
                statement.setInt(2, nuovaOra);
                statement.setInt(3, Integer.parseInt(struttura.get("codice")));
                statement.setString(4, struttura.get("nome_centro"));
                statement.setString(5, codice_segretario);
                statement.setDate(6, java.sql.Date.valueOf(vecchiaData));
                statement.setInt(7, vecchiaOra);
                statement.executeUpdate();

                System.out.println("Prenotazione aggiornata con successo!");
                return true;
            }
            else {
                System.out.println("Mi spiace, ma abbiamo gia una prenotazione nell'orario indicato.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void caricamentoCorso() { // 13
        //Caricamento di un corso organizzato da un centro, con l’assegnazione di eventuali allenatori;
        //il corso è un'attività sportiva dove "periodicita" e "durata_prevista" sono diversi da null
        Corso corsoAgg = getInfoCorso();

        System.out.println("Il corso prevede allenatori specializzati? (0,1)");
        int spec = sc.nextInt();

        sc.nextLine();
        if(spec == 0 || spec == 1) {
            String specializzazione = null;
            if(spec == 1) {
                System.out.println("Inserisci la specializzazione: ");
                specializzazione = sc.nextLine();
            }
            //se gli passo null se la vede lui
            String codice_allenatore = getAllenatore(specializzazione);

            if(!codice_allenatore.isEmpty()) { //se ha trovato l'allenatore
                try {
                    String query = "INSERT INTO AttivitaSportiva (codice, descrizione, durata_prevista, periodicita) VALUES (?, ?, ?, ?)";

                    PreparedStatement statement = db.getConnection().prepareStatement(query);
                    System.out.println(corsoAgg.descrizione);
                    System.out.println(corsoAgg.durata_prevista);
                    System.out.println(corsoAgg.periodicita);
                    System.out.println(corsoAgg.codice);
                    statement.setInt(1, corsoAgg.codice);
                    statement.setString(2, corsoAgg.descrizione);
                    statement.setInt(3, corsoAgg.durata_prevista);
                    statement.setInt(4, corsoAgg.periodicita);
                    statement.executeUpdate();

                    query = "INSERT INTO Coinvolgimento(codice_corso, codice_allenatore) VALUES(?, ?)";
                    statement = db.getConnection().prepareStatement(query);
                    statement.setInt(1, corsoAgg.codice);
                    statement.setString(2, codice_allenatore);
                    statement.executeUpdate();

                    System.out.println("Operazione eseguita...");
                    avanti();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Non ho trovato allenatori disponibili...");
                System.out.println("Il corso non può essere realizzato");
            }
        } else {
            System.out.println("Errore nell'inserimento dei dati...");
        }
        avanti();
    }

    public void reportGiorniLiberi() { // 14
        System.out.print("Inserisci l'anno di cui vuoi il report: ");
        int year = sc.nextInt();
        String query = "SELECT * \n" +
                "FROM struttura JOIN (\n" +
                "    SELECT `nome_centro`,`codice_struttura`, COUNT(*) AS giorni_occupati\n" +
                "    From \n" +
                "        (SELECT `nome_centro`,`codice_struttura`, COUNT(*) AS orari_occupati, `data`\n" +
                "        FROM `svolgimento` \n" +
                "        WHERE `data` >= '" + year + "-01-01' AND  `data`<= '" + year + "-12-31' \n" +
                "        GROUP BY  `nome_centro`,`codice_struttura`,`data`) AS F\n" +
                "\n" +
                "        GROUP BY `nome_centro`,`codice_struttura`) AS F2 \n" +
                "       \n" +
                "    on struttura.nome_centro = F2.nome_centro AND struttura.codice = F2.codice_struttura";
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                System.out.println("\n\nSTRUTTURA: \n");
                System.out.println("Nome centro: " + rs.getString("nome_centro"));
                System.out.println("Codice struttura: " + rs.getInt("codice"));
                System.out.println("Area occupata: " + rs.getInt("area_occupata"));
                System.out.println("Giorni liberi:  " + (360 - rs.getInt("giorni_occupati")));
                System.out.println("Giorni totali: 360");
            }
            avanti();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void oreOccupate() { // 15 -
        String query = "Select * \r\n" +
                "    From Struttura Join (\r\n" +
                "    SELECT `codice_struttura`,`nome_centro`, SUM(`durata`)/60 AS ore\r\n" +
                "    FROM svolgimento\r\n" +
                "    GROUP BY `codice_struttura`, `nome_centro` ) As F\r\n" +
                "    on F.codice_struttura = struttura.codice AND F.nome_centro = struttura.nome_centro";
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                System.out.println("\n\nSTRUTTURA: \n");
                System.out.println("Nome centro: " + rs.getString("nome_centro"));
                System.out.println("Codice struttura: " + rs.getInt("codice"));
                System.out.println("Ore occupate: " + (int)rs.getDouble("ore"));
            }
            avanti();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private class Corso {
        private Corso (int codice, String descrizione, int durata_prevista, int periodicita){
            this.codice = codice;
            this.descrizione = descrizione;
            this.durata_prevista = durata_prevista;
            this.periodicita = periodicita;
        }
        private int codice;
        private String descrizione;
        private int durata_prevista;
        private int periodicita;
    }
}














