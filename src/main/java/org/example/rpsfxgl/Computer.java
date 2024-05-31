package org.example.rpsfxgl;

import javafx.beans.property.*;

public class Computer {
    private final IntegerProperty comId;
    private String comName;
    private int comHp;
    private int comDamage;
    private int comXp;
    private int comStage;

    public Computer(int comId, String comName, int comHp, int comDamage, int comXp, int comStage) {
        this.comId = new SimpleIntegerProperty(comId);
        this.comName = comName;
        this.comHp = comHp;
        this.comDamage = comDamage;
        this.comXp = comXp;
        this.comStage = comStage;
    }

    public int getId() {
        return comId.get();
    }

    public IntegerProperty idProperty() {
        return comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public int getComHp() {
        return comHp;
    }

    public void setComHp(int comHp) {
        this.comHp = comHp;
    }

    public int getComDamage() {
        return comDamage;
    }

    public void setComDamage(int comDamage) {
        this.comDamage = comDamage;
    }

    public int getComXp() {
        return comXp;
    }

    public void setComXp(int comXp) {
        this.comXp = comXp;
    }


    public int getComStage() {
        return comStage;
    }

    public void setComStage(int comLevel) {
        this.comStage = comLevel;
    }
}
