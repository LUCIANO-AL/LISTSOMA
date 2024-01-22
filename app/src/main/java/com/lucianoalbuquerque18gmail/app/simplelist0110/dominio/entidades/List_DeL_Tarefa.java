package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import java.io.Serializable;

public class List_DeL_Tarefa implements Serializable {

  public int ID_T;
  public String LISTATAREFA;
  public String DATALISTA;


  public int getID_T() {
    return ID_T;
  }

  public void setID_T(int ID_T) {
    this.ID_T = ID_T;
  }


  public List_DeL_Tarefa(){
    ID_T = 0;
  }
}
