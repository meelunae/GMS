package me.eleuna.progetto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;


public class Main {

    static CentroSportivo centro;

    public static void main(String[] args) {

        // register JDBC driver, optional since java 1.6
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        System.out.println("Benvenuto nel gestionale per centri sportivi...");
        int MAX_MENU_CHOICE = 15;
        centro = new CentroSportivo();


        Scanner sc = new Scanner(System.in);
        String menu = "1.\tPrenotazione di una struttura;\r\n" +
                "2.\tVerifica della possibilita' di prenotare una struttura per un determinato giorno dell'anno ad una determinata ora;\r\n" +
                "3.\tVisualizzazione degli orari disponibili per prenotare una struttura in un determinato giorno;\r\n" +
                "4.\tVisualizzazione dei giorni disponibili per prenotare una struttura in un determinato orario;\r\n" +
                "5.\tSvolgimento di un'attivita';\r\n" +
                "6.\tVisualizzazione per ogni attivita' del numero di ore in cui sono state svolte in un anno;\r\n" +
                "7.\tAbilitazione di un nuovo centro allo svolgimento di un'attivita';\r\n" +
                "8.\tAssunzione di un nuovo allenatore;\r\n" +
                "9.\tVisualizzazione della struttura in cui sono state svolte il maggior numero di attivita' nell'anno corrente;\r\n" +
                "10.\tVisualizzazione di tutti gli allenatori specializzati in una determinata disciplina;\r\n" +
                "11.\tCancellazione di uno dei responsabili di un centro, con elezione di un nuovo responsabile;\r\n" +
                "12.\tModifica dell'orario della prenotazione di una struttura (se possibile);\r\n" +
                "13.\tCaricamento di un corso organizzato da un centro, con l'assegnazione di eventuali allenatori;\r\n" +
                "14.\tStampa annuale di un report che mostri i dati delle strutture, incluso il numero totale di giorni in cui e' stata libera;\r\n" +
                "15.\tStampa annuale di un report che mostri i dati delle strutture, incluso il numero di ore in cui sono state occupate negli ultimi due anni;\r\n"+
                "99.\tEsci dal programma...\r\n";

        while(true) {
            System.out.println(menu);
            System.out.print("Inserisci il numero del comando da eseguire: ");
            int choice = sc.nextInt();
            if(choice < 1 || choice > MAX_MENU_CHOICE) {
                if(choice == 99) {
                    System.out.println("Grazie per aver utilizzato il nostro servizio... Arrivederci! ");
                    System.exit(0);
                }
                clear();
                System.out.println("Inserire un valore in range tra 1 e "+MAX_MENU_CHOICE);
            } else {
                eseguiOp(choice);
                clear();

            }

        }


    }

    public static void eseguiOp(int choice) {
        switch(choice) {
            case 1:
                centro.prenotazioneStruttura();
                break;
            case 2:
                if(centro.checkPrenotazione()) {
                    System.out.println("Puoi effettuare la prenotazione!");
                }else {
                    System.out.println("Non puoi effettuare la prenotazione!");
                }
                break;
            case 3:
                centro.orariDisponibili();
                break;
            case 4:
                centro.giorniDisponibili();
                break;
            case 5:
                centro.svolgimentoAttivita();
                break;
            case 6:
                centro.oreSvolgimentoAttivita();
                break;
            case 7:
                centro.abilitaCentro();
                break;
            case 8:
                centro.aggiungiAllenatore();
                break;
            case 9:
                centro.stampaStruttura();
                break;
            case 10:
                centro.visualizzaAllenatoriSpecializzati();
                break;
            case 11:
                centro.cancellazioneDirettore();
                break;
            case 12:
                centro.modificaPrenotazione();
                break;
            case 13:
                centro.caricamentoCorso();
                break;
            case 14:
                centro.reportGiorniLiberi();
                break;
            case 15:
                centro.oreOccupate();
                break;

        }
    }


    public static void clear() {
        int BLANK_LINES = 30;
        for(int i = 0; i < BLANK_LINES; i++) {
            System.out.println("\n");
        }
    }

}
