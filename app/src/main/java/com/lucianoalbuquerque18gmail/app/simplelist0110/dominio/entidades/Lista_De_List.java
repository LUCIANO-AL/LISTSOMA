package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import java.io.Serializable;

public class Lista_De_List implements Serializable {

  public int ID;
  public String NOMELISTA;
  public String DATALISTA;

  public int getID() {return ID;}

  public void setID(int ID) {this.ID = ID;}

 public String getNOMELISTA() {
    return NOMELISTA;
  }

  public void setNOMELISTA(String NOMELISTA) {
    this.NOMELISTA = NOMELISTA;
  }

  public String getDATALISTA() {
    return DATALISTA;
  }

  public void setDATALISTA(String DATALISTA) {
    this.DATALISTA = DATALISTA;
  }

    public Lista_De_List (String NOMELISTA, String DATALISTA) {
        this.NOMELISTA = NOMELISTA;
        this.DATALISTA = DATALISTA;
    }

  public Lista_De_List(){ ID = 0; }

  @Override
  public String toString() {
    return "Lista_De_List{" +
            "NOMELISTA='" + NOMELISTA + '\'' +
            ", DATALISTA='" + DATALISTA + '\'' +
            '}';
  }




}
