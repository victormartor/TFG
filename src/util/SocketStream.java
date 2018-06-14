package util;

 import java.net.*;
 import java.io.*;

/**
 * Clase encargada de conectar con la aplicación android
 * 
 * @author Víctor Martín Torres - 12/06/2018
 */
 public class SocketStream extends Socket 
 {
    private final Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    /**
     * Constructor a partir de un socket Servidor
     * @param socket el ServerSocket
     * @throws IOException Error al establacer el servidor
     */
    SocketStream(Socket socket) throws IOException 
    {
      this.socket = socket;
      establecerFlujos( );
    }

    /**
     * Establecer los flujos de entrada y salida para el socket
     * @throws IOException Error al establecer los flujos
     */
    private void establecerFlujos( ) throws IOException
    {
      // obtiene un flujo de salida para leer del socket de datos
      InputStream flujoEntrada = socket.getInputStream();
      entrada = new BufferedReader(new InputStreamReader(flujoEntrada,"UTF-8"));
      OutputStream flujoSalida = socket.getOutputStream();
      // crea un objeto PrintWriter para salida en modo car�cter
      salida = new PrintWriter(new OutputStreamWriter(flujoSalida));
    }

    /**
     * Enviar mensaje a través del socket
     * @param mensaje el mensaje a enviar
     * @throws IOException Error al enviar el mensaje
     */
    public void enviaMensaje(String mensaje) throws IOException 
    {
      salida.println(mensaje);
      // La subsiguiente llamada al m�todo flush es necesaria para que
      // los datos se escriban en el flujo de datos del socket antes
      // de que se cierre el socket.
      salida.flush();
    } // fin de enviaMensaje

    /**
     * Recibir un mensaje del cliente
     * @return el mensaje en forma de String.
     * @throws IOException Error en la conexión.
     */
    public String recibeMensaje( )  throws IOException 
    {
      String sMensaje = entrada.readLine();
      return sMensaje;
    } // fin de recibeMensaje
    
    /**
     * Cerrar el servidor
     * @throws IOException Error al cerrar
     */
    public void closeServer() throws IOException 
    {
        if(salida!=null)
          salida.close();
        if(entrada!=null)
          entrada.close();
    }
  } //fin de class
