//@author Jake Cox

package com.nicodangelo.pantrie.database;

import java.lang.String;public class User
{
    int id;
    String username;
    int status;
    String created_at;

    //Constructors
    public User(){}

    public User(String username, int statux)
    {
        this.username = username;
        this.status = statux;
    }

    public User(int id, String username, int status)
    {
        this.id = id;
        this.username = username;
        this.status = status;
    }

    public User(int id, String username, int status, String created_at)
    {
        this.id = id;
        this.username = username;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }
}
