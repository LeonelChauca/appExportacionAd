package com.example.appexportar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ArrayList<String>> cod = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verificarPermisos();
    }

    public void verificarPermisos(){
        accessFile();
    }
    // Método para acceder al archivo después de que se conceda el permiso
    private void accessFile() {
        String estado = Environment.getExternalStorageState();
        if (!estado.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No se encuentra la tarjeta SD", Toast.LENGTH_SHORT).show();
            return;
        }

        File dir = Environment.getExternalStorageDirectory();
        File p = new File(dir.getAbsolutePath() + File.separator + "productos.csv");
        try {
            BufferedReader lector = new BufferedReader(new FileReader(p));
            StringBuilder res = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                cod.add(new ArrayList<>(List.of(linea.split(";"))));

            }
            lector.close();
        } catch (IOException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public int Tot(){
        int ct=0;
        for(int i=1;i<cod.size();i++){
            ct+=Integer.parseInt(cod.get(i).get(5));
        }
        return ct;
    }
    public void boton(View v){
        TextView tx=findViewById(R.id.textView2);
        EditText x=findViewById(R.id.editTextText);
        TextView ex=findViewById(R.id.textView);
        TextView to=findViewById(R.id.textView3);
        TextView to2=findViewById(R.id.textView4);

        int cont=0;
        int cont2=0;
        String h="";
        for(int i=0;i<cod.size();i++){
            if(cod.get(i).get(4).equals(x.getText().toString())) {
                h = h + cod.get(i).get(1) + " " + cod.get(i).get(5) + "\n";
                cont+=Integer.parseInt(cod.get(i).get(5));
                cont2+=1;
            }
        }
        Double tt= Double.valueOf((cont*100.0)/Tot());
        DecimalFormat d=new DecimalFormat("#.#####");
        ex.setText("Existencia : "+cont);
        tx.setText(h);
        to.setText("Porcentaje : "+String.format("%.5f",tt)+"%");
        to2.setText("Total de productos : " +cont2);
    }




    // Manejar el resultado de la solicitud de permisos

}
