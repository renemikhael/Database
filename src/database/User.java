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
public class User {
    
    private int userID;         // User table
    private String userName;
    private int userComp;
    
    public User(int userID, String userName, int userComp)
    {
        this.userID = userID;
        this.userName = userName;
        this.userComp = userComp;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserComp() {
        return userComp;
    }
    
}
