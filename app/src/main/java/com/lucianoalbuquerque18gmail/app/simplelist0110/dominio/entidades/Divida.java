package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades;

import java.io.Serializable;

public class Divida implements Serializable {

    public int ID;
    public int ID_LDIV;
    public String NOMEDIV;
    public String VALORDIV;
    public String DATAVENC;
    public String DATADIV;
    public boolean PG;
    public String NOMEDOCREDOR;


    public boolean getChecked() {
        return PG;
    }

    public boolean setChecked(boolean checked) {
        PG = checked;
        return checked;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_LDIV() {
        return ID_LDIV;
    }

    public void setID_LDIV(int ID_LDIV) {
        this.ID_LDIV = ID_LDIV;
    }

    public String getNOMEDIV() {
        return NOMEDIV;
    }

    public void setNOMEDIV(String NOMEDIV) {
        this.NOMEDIV = NOMEDIV;
    }

    public String getVALORDIV() {
        return VALORDIV;
    }

    public void setVALORDIV(String VALORDIV) {
        this.VALORDIV = VALORDIV;
    }

    public String getDATAVENC() {
        return DATAVENC;
    }

    public void setDATAVENC(String DATAVENC) {
        this.DATAVENC = DATAVENC;
    }

    public String getDATADIV() {
        return DATADIV;
    }

    public void setDATADIV(String DATADIV) {
        this.DATADIV = DATADIV;
    }

    public boolean isPG() {
        return PG;
    }

    public void setPG(boolean PG) {
        this.PG = PG;
    }

    public String getNOMEDOCREDOR() {
        return NOMEDOCREDOR;
    }

    public void setNOMEDOCREDOR(String NOMEDOCREDOR) {
        this.NOMEDOCREDOR = NOMEDOCREDOR;
    }

    public Divida(){ID = 0;}

}
