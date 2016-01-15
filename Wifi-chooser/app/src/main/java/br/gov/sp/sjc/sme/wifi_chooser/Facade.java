package br.gov.sp.sjc.sme.wifi_chooser;

import java.util.ArrayList;

public class Facade {

    ArrayList<Escola> escolas = new ArrayList<>();

    //Insere as escolas em um arraylist e o retona.
    public void inserirEscolas() {

        //Insere os ssids de cada escola com suas respectivas senhas.

        //Cadastrando SSids da escola EMEF VERA LUCIA CARNEVALLI BARRETO
        escolas.add(new Escola(1, "EMEF VERA LUCIA CARNEVALLI BARRETO"));

        escolas.get(0).adicionarWifi(new Wifi("SALA INTERATIVA 02", "senha"));
        escolas.get(1).adicionarWifi(new Wifi("SALA INTERATIVA 02", "senha"));
        //Cadastrando SSids da escola EMEF PROFA PALMYRA SANT'ANNA
        escolas.add(new Escola(2, "EMEF PROFA PALMYRA SANT'ANNA"));

        escolas.get(0).adicionarWifi(new Wifi("SALA INTERATIVA 01", "senha"));
        escolas.get(1).adicionarWifi(new Wifi("SALA INTERATIVA 01", "senha"));
        escolas.get(2).adicionarWifi(new Wifi("SALA INTERATIVA 01", "senha"));

    }

}
