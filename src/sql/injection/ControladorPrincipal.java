/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.injection;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPrincipal {

    static ArrayList<String> urls = new ArrayList<>();
    Boolean ativo = true;
    MySql mySql = new MySql();

    public ControladorPrincipal(boolean ativo) {
        this.ativo = ativo;
        new Thread(monitoraArray).start();
        mySql = new MySql();
    }

    public static void RecebeUrl(String url) {
        urls.add(url);
    }

    private Runnable monitoraArray = new Runnable() {
        @Override
        public void run() {
            while (ativo) {
                try {
                    if (urls.size() > 0) {
                        //System.out.println(urls.size());
                        for (int i = 0; i < urls.size(); i++) {
                            mySql.RecebeUrl(urls.get(i));
                            urls.remove(i);
                            Thread.sleep(1000);
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    //Logger.getLogger(ControladorMain.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, e);
                    //System.out.println(e);
                }
            }
        }
    };
}
