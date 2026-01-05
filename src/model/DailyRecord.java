package model;

import java.util.ArrayList;

public class DailyRecord {
    public int date; // YYYYMMDD
    public ArrayList<Operation> operations = new ArrayList<>();

    public DailyRecord(int date) {
        this.date = date;
    }
}
