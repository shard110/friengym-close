package com.example.demo.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int onum;
    private Date odate;
    private String id;
    private List<DorderDTO> dorders;

    public int getOnum() {
        return this.onum;
    }

    public void setOnum(int onum) {
        this.onum = onum;
    }

    public Date getOdate() {
        return this.odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DorderDTO> getDorders() {
        return this.dorders;
    }

    public void setDorders(List<DorderDTO> dorders) {
        this.dorders = dorders;
    }

}
