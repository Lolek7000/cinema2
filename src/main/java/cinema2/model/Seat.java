package cinema2.model;

import java.io.Serializable;

public class Seat implements Serializable {

    private Integer nr;
    private String row;
    private boolean isReserved;

    public Seat() {
    }

    public Seat(Character row, Integer nr){
        this.nr = nr;
        this.row = row.toString();
        this.isReserved=false;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

}
