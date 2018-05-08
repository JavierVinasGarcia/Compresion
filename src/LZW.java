import java.io.InputStream;
import java.io.OutputStream;

public class LZW {

    public static void compress(InputStream is, OutputStream os) throws Exception {
        Casilla diccionari [] = new Casilla [256];
        int letra;
        int cont=1;
        int index=1;
        StringBuilder sb = new StringBuilder();
        StringBuilder hola = new StringBuilder();

        while ((letra = is.read()) != (-1)){

            if (diccionari[1]==null){
                Casilla caracters = new Casilla(0, (byte) letra);
                diccionari[cont]= caracters;
                os.write(diccionari[cont].indice);
                os.write(diccionari[cont].letra);
                cont++;
            }else {
                sb.append(letra);
                for (int i = diccionari.length; i <1 ; i--) {
                    if (diccionari[i]==null){
                        continue;
                    }else {
                        hola.append(diccionari[i].letra);
                    }
                    hola.append(diccionari[i].letra);
                }
            }
        }
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {

    }
}
class Casilla {
    int indice;
    byte letra;
    public Casilla(int indice, byte letra){
        this.indice = indice;
        this.letra = letra;
    }
}
