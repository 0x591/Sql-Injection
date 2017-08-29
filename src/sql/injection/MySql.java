/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.injection;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import MontaUrls.UnionBased;

public class MySql {

    ArrayList<String> urlTeste = new ArrayList<>(); // ArrayList que salva as urls temporariamente
    private boolean ativo = true; // Variavel do monitoramento  True = Ativo / False = Desativado
    UnionBased unionBased = new UnionBased();

    public MySql() {
        new Thread(monitora).start();
    }

    public void RecebeUrl(String url) {
        //System.out.println("Chego " + url);
        urlTeste.add(url);
    }

    private Runnable monitora = new Runnable() {
        @Override
        public void run() {
            while (ativo) {
                try {
                    //System.out.println();
                    if (urlTeste.size() > 0) {
                        for (int i = 0; i < urlTeste.size(); i++) { // Entrando em loop se i for menor que o tamanho do ArrayList         
                            new UnionBased(urlTeste.get(i));
                            urlTeste.remove(i);
                            Thread.sleep(1000); // Espera 2 segundo pra enviar a proxima url                            
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    };
}
