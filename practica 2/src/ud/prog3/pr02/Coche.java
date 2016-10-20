package ud.prog3.pr02;

public class Coche
{
  public static final double MASA = 1.0D;
  public static final double FUERZA_BASE_ADELANTE = 2000.0D;
  public static final double FUERZA_BASE_ATRAS = 1000.0D;
  public static final double COEF_RZTO_SUELO = 15.5D;
  public static final double COEF_RZTO_AIRE = 0.35D;
  protected double miVelocidad;
  protected double miDireccionActual;
  protected double posX;
  protected double posY;
  protected String piloto;
  
  public Coche()
  {
    this.miVelocidad = 0.0D;
    this.miDireccionActual = 0.0D;
    this.posX = 300.0D;
    this.posY = 300.0D;
  }
  
  public double getVelocidad()
  {
    return this.miVelocidad;
  }
  
  public void setVelocidad(double miVelocidad)
  {
    this.miVelocidad = miVelocidad;
  }
  
  public double getDireccionActual()
  {
    return this.miDireccionActual;
  }
  
  public void setDireccionActual(double dir)
  {
    if (dir > 360.0D) {
      dir -= 360.0D;
    }
    this.miDireccionActual = dir;
  }
  
  public double getPosX()
  {
    return this.posX;
  }
  
  public double getPosY()
  {
    return this.posY;
  }
  
  public void setPosicion(double posX, double posY)
  {
    setPosX(posX);
    setPosY(posY);
  }
  
  public void setPosX(double posX)
  {
    this.posX = posX;
  }
  
  public void setPosY(double posY)
  {
    this.posY = posY;
  }
  
  public String getPiloto()
  {
    return this.piloto;
  }
  
  public void setPiloto(String piloto)
  {
    this.piloto = piloto;
  }
  
  public void acelera(double aceleracion, double tiempo)
  {
    this.miVelocidad = MundoJuego.calcVelocidadConAceleracion(this.miVelocidad, aceleracion, tiempo);
  }
  
  public void gira(double giro)
  {
    setDireccionActual(this.miDireccionActual + giro);
    if (this.miDireccionActual > 360.0D) {
      this.miDireccionActual -= 360.0D;
    } else if (this.miDireccionActual < 0.0D) {
      this.miDireccionActual += 360.0D;
    }
  }
  
  public void mueve(double tiempoDeMovimiento)
  {
    setPosX(this.posX + MundoJuego.calcMovtoX(this.miVelocidad, this.miDireccionActual, tiempoDeMovimiento));
    setPosY(this.posY + MundoJuego.calcMovtoY(this.miVelocidad, this.miDireccionActual, tiempoDeMovimiento));
  }
  
  public double fuerzaAceleracionAdelante()
  {
    if (this.miVelocidad <= -150.0D) {
      return 2000.0D;
    }
    if (this.miVelocidad <= 0.0D) {
      return 2000.0D * (-this.miVelocidad / 150.0D * 0.5D + 0.5D);
    }
    if (this.miVelocidad <= 250.0D) {
      return 2000.0D * (this.miVelocidad / 250.0D * 0.5D + 0.5D);
    }
    if (this.miVelocidad <= 250.0D) {
      return 2000.0D * (this.miVelocidad / 250.0D * 0.5D + 0.5D);
    }
    if (this.miVelocidad <= 750.0D) {
      return 2000.0D;
    }
    return 2000.0D * (-(this.miVelocidad - 1000.0D) / 250.0D);
  }
  
  public double fuerzaAceleracionAtras()
  {
    if (this.miVelocidad <= -350.0D) {
      return 1000.0D * ((this.miVelocidad + 500.0D) / 150.0D);
    }
    if (this.miVelocidad <= -200.0D) {
      return 1000.0D;
    }
    if (this.miVelocidad <= 0.0D) {
      return 1000.0D * (-this.miVelocidad / 200.0D * 0.7D + 0.3D);
    }
    if (this.miVelocidad <= 250.0D) {
      return 1000.0D * (this.miVelocidad / 250.0D * 0.55D + 0.3D);
    }
    return 850.0D;
  }
  
  public String toString()
  {
    return 
      this.piloto + " (" + this.posX + "," + this.posY + ") - " + "Velocidad: " + this.miVelocidad + " ## Direcci�n: " + this.miDireccionActual;
  }
}
