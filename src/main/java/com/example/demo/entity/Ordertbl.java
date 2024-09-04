package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordertbl")
public class Ordertbl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onum")
    private int onum;

    @Column(name = "odate")
    private Date odate;

    @Column(name = "id", length = 50)
    private String id;

    @OneToMany(mappedBy = "ordertbl")
    private List<Dorder> dorders;

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

    public List<Dorder> getDorders() {
        return this.dorders;
    }

    public void setDorders(List<Dorder> dorders) {
        this.dorders = dorders;
    }

}
