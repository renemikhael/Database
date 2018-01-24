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
public class Computer {
    
    private int computerID;     
    private String computerName;
    private String computerModel;
    private String processor;
    private String ram;
    
    public Computer(int computerID, String computerName, String computerModel, String processor, String ram)
    {
        this.computerID = computerID;
        this.computerName = computerName;
        this.computerModel = computerModel;
        this.processor = processor;
        this.ram = ram;
    }

    public int getComputerID() {
        return computerID;
    }

    public String getComputerName() {
        return computerName;
    }

    public String getComputerModel() {
        return computerModel;
    }

    public String getProcessor() {
        return processor;
    }

    public String getRam() {
        return ram;
    }
    
    
}
