/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniil
 */
public class ApatiteUpdater {

    private static String USER_AGENT = "Mozilla/5.0";
    private int version;
    private String projectname;
    private String saveFilename;
    public boolean isUpdated;

    public ApatiteUpdater(String projectname, int version, String saveFilename) {
        this.version = version;
        this.projectname = projectname;
        this.saveFilename = saveFilename;
    }

    public void saveUpdate() {
        try {
            if (new File(this.saveFilename).exists()) {
                new File(this.saveFilename).delete();
            }
            URL website = new URL("http://dev.apatiteservers.ru/applications/download.php?project=" + projectname);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(this.saveFilename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            this.isUpdated = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("[ApatiteControler] ApatiteUpdate service error! ");
            
        }
    }

    public void update() {
        if (this.needsUpdate()) {
            System.out.println("[ApatiteControler] Downloading update...");
            this.saveUpdate();
            System.out.println("[ApatiteControler] Downloading complete!");
        } else {
            System.out.println("[ApatiteControler] Can't find any updates!");
        }
    }

    public boolean needsUpdate() {
        try {
            String url = "http://dev.apatiteservers.ru/applications/checkUpdate.php?project=" + projectname + "&version=" + Integer.toString(version);

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);



            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String resp = response.toString();
            if (!resp.equalsIgnoreCase("error")) {
                if (resp.equalsIgnoreCase("YES")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("[ApatiteControler] ApatiteUpdate service error! ");
            }
        } catch (IOException ex) {
            System.out.println("[ApatiteControler] ApatiteUpdate service error! ");
        }
        return false;
    }
}
