/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Rene
 */
public class Equipment {
    
    private int equipmentID;    
    private String equipmentName;
    private int equipmentComp;
    
    public Equipment(int equipmentID, String equipmentName, int equipmentComp)
    {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.equipmentComp = equipmentComp;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public int getEquipmentComp() {
        return equipmentComp;
    }
    
}
