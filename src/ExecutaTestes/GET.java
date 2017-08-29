/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExecutaTestes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import sql.injection.TelaInicial;
import static sql.injection.TelaInicial.val;
import static sql.injection.telaDebugger.valDebug;

public class GET {

    public GET() {

    }

    private static HttpClient client() {
        return new DefaultHttpClient();
    }
    private static HttpResponse response;
    private static boolean Encontrei;
    public static void enviaGet(String urlGet) {
        //System.err.println(response.getStatusLine() + " " + "url:" + urlGet);
        
        try {
            HttpGet request = new HttpGet(urlGet);

            response = client().execute(request);
            HttpEntity entity = response.getEntity();
            valDebug.addRow(new String[]{urlGet,response.getStatusLine().toString(),"GET"});
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line = "";
            Encontrei = false;
            while ((line = rd.readLine()) != null) {
                if (Encontrei != true) {
                    if (verifica(line)) {                        
                        val.addRow(new String[]{urlGet,payload,"Union"});
                        System.err.println("->  " + " " + response.getStatusLine() + " Vulneravel - MySqlUnion \n" + urlGet);
                        Encontrei = true;
                    }
                }
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
        }
    }
    private static String payload;

    public static boolean verifica(String recebeCodigoFonte) {
        String content = recebeCodigoFonte;
        boolean isMatch = false;
        int qntArquivo = 1;
        try {
            BufferedReader a = new BufferedReader(new FileReader("C:/SqlInjection/TCCPentest.txt"));
            while (a.ready()) {
                String pattern = ".*" + a.readLine() + ".*";

                isMatch = Pattern.matches(pattern, content);
                if (isMatch) {
                    payload = pattern;
                    //System.out.println(linha);
                    return isMatch;
                }
            }
            a.close();
        } catch (Exception e) {
        }
        return isMatch;
    }
}
