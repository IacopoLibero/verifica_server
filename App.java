package server.verifica_server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class App 
{
    public static void main(String[] args) 
    {
        ArrayList<String> lista_parole = new ArrayList<String>();
        lista_parole.add("ciao");
        lista_parole.add("mela");
        lista_parole.add("palla");
        lista_parole.add("cane");
        lista_parole.add("gatto");
        int x=0;
        try 
        {
            System.out.println("Server in avvio!");
            ServerSocket server = new ServerSocket(4567); //crea socket su cui ricevere
            do 
            {
                Socket s = server.accept(); //accetta connessione
                MioThread m = new MioThread(s,lista_parole); //crea thread
                m.start(); //start processo
            } 
            while (x!=0);
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}