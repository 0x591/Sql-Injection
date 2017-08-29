/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.injection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import static sql.injection.ControladorPrincipal.RecebeUrl;

public class Validacao {

    private String urlGET;
    private String urlPOST;
    // Metodo construtor que recebe as urls

    public Validacao(String url) {
        if (url.contains("!_!")) {
            //System.out.println(url + " POST");
            this.urlGET = url;
            RecebeUrl(url);
        } else {
            //System.out.println(url + " GET");
            this.urlPOST = url;
            RecebeUrl(url);
        }
    }

    public Validacao() {

    }

    private void validaGet() {
        System.out.println(this.urlGET + " ur");
        RecebeUrl(urlGET);
    }

    private void validaPost() {
        RecebeUrl(urlPOST);
    }

    private int enviaGet(String url) {
        int codigoDeResposta = 0;
        return codigoDeResposta;
    }

    public static void main(String[] args) throws IOException {
        capturaHaders();
    }

    private static void capturaHaders() throws IOException {
        //https://hackertarget.com/
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        // http://testphp.vulnweb.com/userinfo.php
        // uname=123&pass=123
        HttpPost httpPost = new HttpPost("http://testphp.vulnweb.com/userinfo.php");
        StringEntity entitys = new StringEntity("uname=123&pass=123");
        httpPost.setEntity(entitys);

        //httpPost.setHeader(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));
        response = client.execute(httpPost);
        Header h[] = response.getAllHeaders();
        System.err.println(response.getStatusLine() + " " + Arrays.toString(h));
    }
}
