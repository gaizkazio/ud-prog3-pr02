package ud.prog3.pr02;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaJuego
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  JPanel pPrincipal;
  JLabel lMensaje;
  MundoJuego miMundo;
  CocheJuego miCoche;
  MiRunnable miHilo = null;
  static Integer[] codsTeclasControladas = { Integer.valueOf(38), Integer.valueOf(40), Integer.valueOf(37), Integer.valueOf(39) };
  static List<Integer> listaTeclas = Arrays.asList(codsTeclasControladas);
  boolean[] teclasPulsadas;
  
  public VentanaJuego()
  {
    setDefaultCloseOperation(2);
    
    this.pPrincipal = new JPanel();
    JPanel pBotonera = new JPanel();
    
    this.lMensaje = new JLabel(" ");
    
    this.pPrincipal.setLayout(null);
    this.pPrincipal.setBackground(Color.white);
    
    add(this.pPrincipal, "Center");
    
    pBotonera.add(this.lMensaje);
    add(pBotonera, "South");
    
    setSize(1000, 750);
    setResizable(false);
    
    this.teclasPulsadas = new boolean[codsTeclasControladas.length];
    this.pPrincipal.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        if (VentanaJuego.listaTeclas.contains(Integer.valueOf(e.getKeyCode()))) {
          VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(e.getKeyCode()))] = true;
        }
      }
      
      public void keyReleased(KeyEvent e)
      {
        if (VentanaJuego.listaTeclas.contains(Integer.valueOf(e.getKeyCode()))) {
          VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(e.getKeyCode()))] = false;
        }
      }
    });
    this.pPrincipal.setFocusable(true);
    this.pPrincipal.requestFocus();
    this.pPrincipal.addFocusListener(new FocusAdapter()
    {
      public void focusLost(FocusEvent e)
      {
        VentanaJuego.this.pPrincipal.requestFocus();
      }
    });
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        if (VentanaJuego.this.miHilo != null) {
          VentanaJuego.this.miHilo.acaba();
        }
      }
    });
  }
  
  public static void main(String[] args)
  {
    try
    {
      VentanaJuego miVentana = new VentanaJuego();
      SwingUtilities.invokeAndWait(new Runnable()
      {
        public void run()
        {
          VentanaJuego.this.setVisible(true);
        }
      });
      miVentana.miMundo = new MundoJuego(miVentana.pPrincipal);
      miVentana.miMundo.creaCoche(150, 100);
      miVentana.miCoche = miVentana.miMundo.getCoche();
      miVentana.miCoche.setPiloto("Fernando Alonso"); 
      VentanaJuego  tmp72_71 = miVentana;tmp72_71.getClass();
        miVentana.miHilo = new MiRunnable(tmp72_71);
      Thread nuevoHilo = new Thread(miVentana.miHilo);
      nuevoHilo.start();
    }
    catch (Exception e)
    {
      System.exit(1);
    }
  }
  
  class MiRunnable
    implements Runnable
  {
    boolean sigo = true;
    
    MiRunnable() {}
    
    public void run()
    {
      while (this.sigo)
      {
        if (VentanaJuego.this.miMundo.hayChoqueHorizontal(VentanaJuego.this.miCoche)) {
          VentanaJuego.this.miMundo.rebotaHorizontal(VentanaJuego.this.miCoche);
        }
        if (VentanaJuego.this.miMundo.hayChoqueVertical(VentanaJuego.this.miCoche)) {
          VentanaJuego.this.miMundo.rebotaVertical(VentanaJuego.this.miCoche);
        }
        double fuerzaAceleracion = 0.0D;
        if (VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(38))] != false) {
          fuerzaAceleracion = VentanaJuego.this.miCoche.fuerzaAceleracionAdelante();
        }
        if (VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(40))] != false) {
          fuerzaAceleracion = -VentanaJuego.this.miCoche.fuerzaAceleracionAtras();
        }
        MundoJuego.aplicarFuerza(fuerzaAceleracion, VentanaJuego.this.miCoche);
        if (VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(37))] != false) {
          VentanaJuego.this.miCoche.gira(10.0D);
        }
        if (VentanaJuego.this.teclasPulsadas[VentanaJuego.listaTeclas.indexOf(Integer.valueOf(39))] != false) {
          VentanaJuego.this.miCoche.gira(-10.0D);
        }
        VentanaJuego.this.miCoche.mueve(0.04D);
        
        int estrellasPerdidas = VentanaJuego.this.miMundo.quitaYRotaEstrellas(6000L);
        if (estrellasPerdidas > 0)
        {
          String mensaje = "Puntos: " + VentanaJuego.this.miMundo.getPuntuacion();
          mensaje = mensaje + "  -  ESTRELLAS PERDIDAS: " + VentanaJuego.this.miMundo.getEstrellasPerdidas();
          VentanaJuego.this.lMensaje.setText(mensaje);
        }
        VentanaJuego.this.miMundo.creaEstrella();
        int choquesEstrellas = VentanaJuego.this.miMundo.choquesConEstrellas();
        if (choquesEstrellas > 0)
        {
          String mensaje = "Puntos: " + VentanaJuego.this.miMundo.getPuntuacion();
          VentanaJuego.this.lMensaje.setText(mensaje);
        }
        if (VentanaJuego.this.miMundo.finJuego())
        {
          this.sigo = false;
          VentanaJuego.this.lMensaje.setText("SE ACAB� EL JUEGO!!! Has sacado " + 
            VentanaJuego.this.miMundo.getPuntuacion() + " puntos.");
          try
          {
            Thread.sleep(3000L);
          }
          catch (Exception localException) {}
          VentanaJuego.this.dispose();
        }
        try
        {
          Thread.sleep(40L);
        }
        catch (Exception localException1) {}
      }
    }
    
    public void acaba()
    {
      this.sigo = false;
    }
  }
}
