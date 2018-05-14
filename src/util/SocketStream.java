package util;

 import java.net.*;
 import java.io.*;
 public class SocketStream extends Socket {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    SocketStream(String maquinaAceptadora,int puertoAceptador ) throws SocketException,IOException{
      socket = new Socket(maquinaAceptadora, puertoAceptador );
      establecerFlujos( );
    }

    SocketStream(Socket socket) throws IOException {
      this.socket = socket;
      establecerFlujos( );
    }

    private void establecerFlujos( ) throws IOException{
      // obtiene un flujo de salida para leer del socket de datos
      InputStream flujoEntrada = socket.getInputStream();
      entrada = new BufferedReader(new InputStreamReader(flujoEntrada,"UTF-8"));
      OutputStream flujoSalida = socket.getOutputStream();
      // crea un objeto PrintWriter para salida en modo car�cter
      salida = new PrintWriter(new OutputStreamWriter(flujoSalida));
    }

    public void enviaMensaje(String mensaje) throws IOException {
      salida.println(mensaje);
      // La subsiguiente llamada al m�todo flush es necesaria para que
      // los datos se escriban en el flujo de datos del socket antes
      // de que se cierre el socket.
      salida.flush();
    } // fin de enviaMensaje

    public String recibeMensaje( )  throws IOException {
      
      String mensaje = "";//entrada.readLine();
      String lectura;
      // lee una l�nea del flujo de datos
      //try{lectura = entrada.readLine();}catch(IOException e){lectura = null;}
      while(!(lectura = entrada.readLine()).equals("FinTicket"))
      {
        mensaje = mensaje+lectura+"\n";
        //mensaje = mensaje+entrada.readLine( );
        //System.out.println(mensaje);

        //try{lectura = entrada.readLine();}catch(IOException e){lectura = null;}
      }
      return mensaje;
    } // fin de recibeMensaje
    
    public void closeServer() throws IOException {
    try {
      if(salida!=null)
        salida.close();
      if(entrada!=null)
        entrada.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
}

  } //fin de class
