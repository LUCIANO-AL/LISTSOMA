package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {

    public int id_item;
    public int ID;
    public String Nome;
    public String preco;
    public String quant;
    public boolean check;
    public String total;

    public Item (String Nome, String preco, String quant) {
        this.Nome = Nome;
        this.preco = preco;
        this.quant = quant;
    }


    public boolean getChecked() {
        return check;
    }

    public boolean setChecked(boolean checked) {
        check = checked;
        return checked;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Item(){
        id_item = 0;
    }

}
