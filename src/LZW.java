import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LZW {

    public static void compress(InputStream is, OutputStream os) throws Exception {
        Casilla diccionari[] = new Casilla[257];
        List<Byte> arrayList = new ArrayList<>();
        int letra;
        int cont = 1;
        int posicionEncontrado=0;

        int contieneCaracter ;


        StringBuilder caracteresParaComparar = new StringBuilder() ;
        Casilla caracters;
        while ((letra = is.read()) != (-1)) {
            if (diccionari[1] == null) {
                caracters = new Casilla(0, (byte) letra);
                diccionari[cont] = caracters;
                cont++;
            } else {
                arrayList.add((byte) letra);

                byte s1 = arrayList.get(arrayList.size()-1);
                caracteresParaComparar.append(s1);
                is.mark(letra);
                if ((letra = is.read())==-1){
                    rellenaDiccionario(cont,posicionEncontrado,diccionari, arrayList);

                }else {
                    is.reset();
                    contieneCaracter=devuelveCadena(diccionari,caracteresParaComparar.toString());
                    if (contieneCaracter!=0){
                        posicionEncontrado=contieneCaracter;
                    }else{
                        rellenaDiccionario(cont,posicionEncontrado,diccionari, arrayList);
                        if (cont==256){
                            escribe(diccionari,os);
                            cont=0;
                        }
                        arrayList.clear();
                        caracteresParaComparar.setLength(0);
                        posicionEncontrado=0;
                        cont++;
                    }
                }

            }
        }

        escribe(diccionari,os);
        os.flush();
        os.close();


    }

    private static void escribe (Casilla [] diccionari,OutputStream os ) throws IOException {
        for (int i = 1; i < diccionari.length; i++) {
            if (diccionari[i] == null) {
                break;
            } else {
                os.write(diccionari[i].indice);
                os.write(diccionari[i].letra);
                diccionari[i]=null;
            }
        }
    }

    private static void rellenaDiccionario (int cont, int posicionEncontrado, Casilla [] diccionari, List<Byte> arrayList){
        byte letraDiccionario = arrayList.get(arrayList.size() - 1);
        Casilla caracters = new Casilla(posicionEncontrado, letraDiccionario);
        diccionari[cont] = caracters;
    }
    private static int devuelveCadena(Casilla diccionari[], String s) {
        StringBuilder sb = new StringBuilder();
        int contieneCaracter =0;


        for (int i = diccionari.length - 1; i >= 1; i--) {
            if (diccionari[i] == null) {
                continue;
            } else {
                int Posicion = diccionari[i].indice;
                byte letraPosicion = diccionari[i].letra;
                sb.append(letraPosicion);

                while (Posicion != 0) {
                    letraPosicion = diccionari[Posicion].letra;
                    Posicion = diccionari[Posicion].indice;
                    sb.insert(0,letraPosicion);
                }

                if (sb.toString().contentEquals(s)) {
                    contieneCaracter= i;
                }
                sb.setLength(0);
            }

        }
        return contieneCaracter;
        //sb.append(letraPosicion);
        /*
        while (Posicion != 0) {
            letraPosicion = diccionari[Posicion].letra;
            Posicion = diccionari[Posicion].indice;
            sb.append(letraPosicion);

        }
        */
        //return sb.toString();
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {

    }
}

class Casilla {
    int indice;
    byte letra;

    public Casilla(int indice, byte letra) {
        this.indice = indice;
        this.letra = letra;
    }
}
