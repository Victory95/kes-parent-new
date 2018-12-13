package com.fingertech.kes.Service;

public class Country {
    private long id;
    private String negara;

    public Country()
    {

    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the nama_barang
     */
    public String getNegara() {
        return negara;
    }

    public void setNegara(String nama_barang) {
        this.negara = nama_barang;
    }

    @Override
    public String toString()
    {
        return "Negara "+ negara ;
    }
}
