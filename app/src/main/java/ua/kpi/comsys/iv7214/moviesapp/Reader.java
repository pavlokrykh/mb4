package ua.kpi.comsys.iv7214.moviesapp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Reader {

    public String fileToString(Context context, String file){
        String s = "";
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            s = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return "no file";
        }
        return s;
    }

}
