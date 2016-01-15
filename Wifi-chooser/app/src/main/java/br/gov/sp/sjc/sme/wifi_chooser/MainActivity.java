package br.gov.sp.sjc.sme.wifi_chooser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Spinner spnEscolas;
    private Button btnCarregar;
    private TextView editText;

    private WifiManager w;
    private int wifiConected;

    private MainActivity reference = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnEscolas = (Spinner) findViewById(R.id.spnEscolas);
        btnCarregar = (Button) findViewById(R.id.btnCarregar);
        editText = (TextView) findViewById(R.id.textView);

        if (! isGaamInstaled()){
            fecharApp();
        }

        w = (WifiManager) getSystemService(WIFI_SERVICE);
        if (! w.isWifiEnabled()){
            w.setWifiEnabled(true);
            editText.setText(editText.getText() + " Wifi Habilitado! \n");
        }

        editText.setText(editText.getText() + " WIFI STATE = " + w.getWifiState() + "\n");

        Facade f = new Facade();
        f.inserirEscolas();
        carregarSpinner(f.escolas);

        btnCarregar.setOnClickListener(btnCarregarHandler);

        wifiConected = w.getConnectionInfo().getNetworkId();

    }

    View.OnClickListener btnCarregarHandler = new View.OnClickListener() {
        public void onClick(View v) {

            removeSsids();

            Escola e = (Escola) spnEscolas.getSelectedItem();

            for (Wifi wifi : e.getWifis()){
                editText.setText(editText.getText() + " WIFI STATE = " + w.getWifiState() + "\n");
                int status = addWifi(wifi.getSsid(),wifi.getPassword());
                editText.setText(editText.getText() + " Adicionado SSID " + wifi.getSsid() + " com status " + status + "\n");
            }

            w.saveConfiguration();
            editText.setText(editText.getText() + " WIFI STATE = " + w.getWifiState() + "\n");
            w.enableNetwork(wifiConected,true);

            editText.setVisibility(View.VISIBLE);

            new AlertDialog.Builder(reference)
                    .setMessage("Os SSIDs da escola " + spnEscolas.getSelectedItem() + " foram carregados para este dispositivo" +
                            " com sucesso! Gostaria de sair agora?")
                    .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            fecharApp();
                        }
                    })
                    .create().show();

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //imprime uma lista de wifis que ja estão salvas no dispositivo
    public void removeSsids(){

        List<WifiConfiguration> l = w.getConfiguredNetworks();

        editText.setText(editText.getText() + " COMEÇANDO VERIFICAÇÃO DAS CONEXÕES JA CONFIGURADAS!!! \n");
        editText.setText(editText.getText() + " WIFI STATE = " + w.getWifiState() + "\n");

        for (WifiConfiguration wfc : l) {
            editText.setText(editText.getText() + " Encontrado  " + wfc.SSID + " \n");
            if (wfc.SSID.contains("SALA INTERATIVA")){
                boolean netStatus = w.removeNetwork(wfc.networkId);
                editText.setText(editText.getText() + " Removido SSID " + wfc.SSID + " com status " + netStatus + "\n");
            }
        }
    }

    //adiciona um novo wifi no dispositivo
    public int addWifi(String ssid, String password){

        WifiConfiguration newWifi = new WifiConfiguration();
        newWifi.SSID = "\"".concat(ssid).concat("\"");
        newWifi.preSharedKey = "\"".concat(password).concat("\"");
        newWifi.allowedProtocols.set(WifiConfiguration.Protocol.RSN); // For WPA2
        newWifi.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        newWifi.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        newWifi.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        newWifi.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        newWifi.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        int netId = w.addNetwork(newWifi);

        w.enableNetwork(netId, true);

        return netId;

    }

    public void carregarSpinner(ArrayList<Escola> escolas){

        ArrayAdapter<Escola> escolasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, escolas);
        spnEscolas.setAdapter(escolasAdapter);

    }

    private boolean isGaamInstaled() {

        boolean resp = false;

        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if (p.packageName.equals("br.com.vertitecnologia.gaamclient")){
                resp = true;
            }
        }

        return resp;

    }

    public void fecharApp(){
        this.finish();
        System.exit(0);
    }

}