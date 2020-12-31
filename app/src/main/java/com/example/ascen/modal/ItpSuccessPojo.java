package com.example.ascen.modal;

public class ItpSuccessPojo {
    private String Status;
    public String getStatus ()
    {
        return Status;
    }
    public void setStatus (String Status)
    {
        this.Status = Status;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+"]";
    }
}
