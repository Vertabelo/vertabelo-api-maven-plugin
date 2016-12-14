package com.vertabelo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Mojo used to download XML file from Vertabelo API based on following parameters:
 * - destXMLFile - name of XML destination file
 * - apiToken: User's Vertabelo API Token
 * - modelId: Id of the Vertabelo model
 * - modelTag (optional): version tag of the model
 *
 * @author Krzysztof Waraksa
 */
@Mojo(name = "vertabeloXML")
public class VertabeloXMLMojo extends AbstractMojo {

    public final String API_ADDR = "https://my.vertabelo.com/api/xml";

    @Parameter
    String apiToken;

    @Parameter
    String modelId;

    @Parameter
    String modelTag;

    @Parameter(defaultValue = "model.xml")
    String destXMLFile;

    @Override
    public void execute() throws MojoExecutionException
    {
        if (apiToken == null) {
            throw new MojoExecutionException("Vertabelo api token not set");
        }
        if (modelId == null) {
            throw new MojoExecutionException("Vertabelo model id not set");
        }

        URL url;
        try {
            if (modelTag != null) {
                url = new URL(API_ADDR + "/" + modelId + "/" + modelTag);
            } else {
                url = new URL(API_ADDR + "/" + modelId);
            }
        } catch (MalformedURLException ex) {
            throw new MojoExecutionException("Incorrect URL parameters", ex);
        }

        try {
            HttpURLConnection con = (HttpURLConnection)(url.openConnection());
            String basicAuth = "Basic " + new String(new Base64().encode((apiToken + ":").getBytes()));
            con.setRequestProperty("Authorization", basicAuth);
            File file = new File("./" + destXMLFile);
            file.getParentFile().mkdirs();
            file.createNewFile();
            BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            bos.flush();
            bis.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
