package server.verifica_server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MioThread extends Thread
{

    Socket s;
    ArrayList<String> lista_parole = new ArrayList<String>();


    public MioThread(Socket s, ArrayList<String> lista_parole)
    {
        this.s = s;
        this.lista_parole = lista_parole;
    }

    public void run()
    {
        int ntentativi = 0;
        int x = (int) (Math.random() * 4);
        String parola = lista_parole.get(x);
        int n=parola.length();
        try 
        {
            int lettere=0;
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream())); //istanza per ricevere dati dal client
            DataOutputStream output = new DataOutputStream(s.getOutputStream()); //istanza per inviare dati al client
            String parolaIniziale = "";
            for(int i=0;i<n;i++)
            {
                parolaIniziale=parolaIniziale+("*");
            }
            output.writeBytes(parolaIniziale+"\n");
            char[] myChars = parolaIniziale.toCharArray();
            do 
            {
                String stringaRicevuta = input.readLine(); //riceve dati
                
                if(stringaRicevuta.equals("1"))//client sceglie una lettera
                {
                    output.writeBytes("S\n"); //scegli una lettera
                    String letteraRicevuta = input.readLine(); 
                    
                    for(int i=0;i<n;i++)
                    {
                        if(parola.charAt(i)==letteraRicevuta.charAt(0))
                        {
                            myChars[i]=letteraRicevuta.charAt(0);
                            lettere++;
                        }
                        else if(myChars[i]=='*')
                        {
                            myChars[i]='*';
                        }
                    }
                    ntentativi++;
                    String conversione = new String(myChars);
                    output.writeBytes(conversione+"\n");
                }
                if(stringaRicevuta.equals("2"))
                {
                    output.writeBytes("P\n"); //scegli una parola
                    ntentativi++;
                    String parolaRicevuta = input.readLine();
                    if(parolaRicevuta.equals(parola))
                    {
                        output.writeBytes(String.valueOf(ntentativi)+"\n");//indovinato
                    }
                    else
                    {
                        output.writeBytes("0\n"); //sbagliato
                    }
                }    
                if(stringaRicevuta.equals("3"))
                {
                    output.writeBytes("E\n"); //esci
                    s.close(); //chiude socket
                }
            } while (s.isConnected());
            
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}