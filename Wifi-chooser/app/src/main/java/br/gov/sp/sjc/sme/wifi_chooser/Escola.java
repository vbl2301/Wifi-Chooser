package br.gov.sp.sjc.sme.wifi_chooser;

import java.util.ArrayList;

/**
 * Created by vinicius.lima on 20/02/2015.
 */
public class Escola {

    private int id;
    private String nome;
    private ArrayList<Wifi> wifis = new ArrayList<>();

    public Escola(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Wifi> getWifis() {
        return wifis;
    }

    public void setWifis(ArrayList<Wifi> wifis) {
        this.wifis = wifis;
    }

    public void adicionarWifi(Wifi wifi){
        wifis.add(wifi);
    }

    @Override
    public String toString() {
        return nome;
    }
}