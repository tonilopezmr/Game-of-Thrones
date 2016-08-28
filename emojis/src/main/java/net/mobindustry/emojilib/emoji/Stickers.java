package net.mobindustry.emojilib.emoji;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Stickers {

    private List<String> ss = new ArrayList<>();

    public Stickers(Context context) {
        String[] fileList = null;
        try {
            fileList = context.getAssets().list("stickers");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                File f = new File(context.getCacheDir() + File.separator + fileList[i]);
                if (!f.exists()) try {

                    InputStream is = context.getAssets().open("stickers/" + fileList[i]);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(buffer);
                    fos.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ss.add(f.getAbsolutePath());
            }
        }
    }

    public List<String> getStickers() {
        return ss;
    }
}
