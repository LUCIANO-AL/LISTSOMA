package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import java.io.Serializable;

public class Tarefa implements Serializable {

  public int id_tarefa;
  public int ID_T;
  public String Tarefa;
  public String Prioridade;
  public boolean check;

  public boolean getChecked() {
    return check;
  }

  public boolean setChecked(boolean checked) {
    check = checked;
    return checked;
  }


  public Tarefa(){
    id_tarefa = 0;
  }
}
