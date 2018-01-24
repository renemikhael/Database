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
public class Software {
    
    private int softwareID;     
    private String softwareName;
    private String version;
    private String aquired;
    private String license;
    
    public Software(int softwareID, String softwareName, String version, String aquired, String license)
    {
        this.softwareID = softwareID;
        this.softwareName = softwareName;
        this.version = version;
        this.aquired = aquired;
        this.license = license;
    }

    public int getSoftwareID() {
        return softwareID;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public String getVersion() {
        return version;
    }

    public String getAquired() {
        return aquired;
    }

    public String getLicense() {
        return license;
    }
    
}
