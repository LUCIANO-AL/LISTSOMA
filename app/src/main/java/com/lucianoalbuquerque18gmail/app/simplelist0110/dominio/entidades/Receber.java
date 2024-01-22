package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import java.io.Serializable;

public class Receber implements Serializable {

    public int ID;
    public String NOMEDIV_REC;
    public String VALORDIV_REC;
    public boolean PG_REC;
    public String DATAVENC_REC;
    public String DATADIV_REC;
    public String NOMECLIENTE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMEDIV_REC() {
        return NOMEDIV_REC;
    }

    public void setNOMEDIV_REC(String NOMEDIV_REC) {
        this.NOMEDIV_REC = NOMEDIV_REC;
    }

    public String getVALORDIV_REC() {
        return VALORDIV_REC;
    }

    public void setVALORDIV_REC(String VALORDIV_REC) {
        this.VALORDIV_REC = VALORDIV_REC;
    }

    public boolean getChecked() {
        return PG_REC;
    }

    public boolean setChecked(boolean checked) {
        PG_REC = checked;
        return checked;
    }

    public String getDATAVENC_REC() {
        return DATAVENC_REC;
    }

    public void setDATAVENC_REC(String DATAVENC_REC) {
        this.DATAVENC_REC = DATAVENC_REC;
    }

    public String getDATADIV_REC() {
        return DATADIV_REC;
    }

    public void setDATADIV_REC(String DATADIV_REC) {
        this.DATADIV_REC = DATADIV_REC;
    }

    public String getNOMECLIENTE() {
        return NOMECLIENTE;
    }

    public void setNOMECLIENTE(String NOMECLIENTE) {
        this.NOMECLIENTE = NOMECLIENTE;
    }

    public Receber(){ID = 0;}
}
