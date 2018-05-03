import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class RLE {


    public static void compress(InputStream is, OutputStream os) throws Exception {
        int caracter_actual;
        int caracter1_antiguo = 0;
        int cont = 0;

        //LinkedList <Integer> lista = new LinkedList<>();
        while ((caracter_actual = is.read()) != (-1)) {
            if (cont == 0) {
                cont++;
            } else {
                if (cont == 257) {
                    escribir(cont, caracter1_antiguo, os);
                    cont = 1;
                    continue;
                }
                if (caracter1_antiguo == caracter_actual) {
                    cont++;
                } else {
                    escribir(cont, caracter1_antiguo, os);
                    cont = 1;
                }
            }
            caracter1_antiguo = caracter_actual;
        }

            escribir(cont, caracter1_antiguo, os);


    }

    private static void escribir(int cont, int caracter, OutputStream os) throws IOException {
        if (cont >= 2) {
            os.write(caracter);
            os.write(caracter);
            os.write((cont - 2));

        } else {
            os.write(caracter);
        }
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {
        int caracter_actual;
        int caracter1_antiguo = 0;
        int repeticiones;
        while ((caracter_actual = is.read()) != (-1)) {

            if (caracter1_antiguo == caracter_actual) {
                if ((repeticiones = is.read()) != (-1)) {
                    os.write(caracter_actual);
                    for (int i = 0; i < repeticiones; i++) {
                        os.write(caracter_actual);
                    }
                }
            } else {
                os.write(caracter_actual);
            }
            caracter1_antiguo = caracter_actual;

        }
    }

}
