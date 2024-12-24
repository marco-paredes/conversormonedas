import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcoparedes.conversormonedas.modelos.Moneda;
import com.marcoparedes.conversormonedas.modelos.MonedaExRate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[]args) throws IOException, InterruptedException{
        Scanner lectura = new Scanner(System.in);
        String menu = """
                1 - Dólar =>>> Peso Argentino
                2 - Peso Argentino =>>> Dólar
                3 - Dólar =>>> Real Brasilero
                4 - Real Brasilero =>>> Dólar
                5 - Dólar =>>> Peso Colombiano
                6 - Peso Colombiano =>>> Dólar
                7 - Dólar =>>> Sol
                8 - Sol =>>> Dólar
                0 - Salir
                """;
        int opcion = -1;
        double conversion = 0.0;
        var monedaIn = "USD";
        var monedaOut = "BOB";

        while (opcion != 0){
            System.out.println("*******************************************");
            System.out.println("Bienvenido/a al Conversor de Moneda -Eureka!-");
            System.out.println(menu);
            System.out.println("Escriba el número de opción para el cambio de moneda: ");
            opcion = lectura.nextInt();

            switch (opcion){
                case 1:
                    monedaIn = "USD";
                    monedaOut = "ARS";
                    System.out.println("Eligió la conversión Dólar =>>> Peso Argentino");
                    break;
                case 2:
                    monedaIn = "ARS";
                    monedaOut = "USD";
                    System.out.println("Eligió la conversión Peso Argentino =>>> Dólar");
                    break;
                case 3:
                    monedaIn = "USD";
                    monedaOut = "BRL";
                    System.out.println("Eligió la conversión Dólar =>>> Real Brasilero");
                    break;
                case 4:
                    monedaIn = "BRL";
                    monedaOut = "USD";
                    System.out.println("Eligió la conversión Real Brasilero =>>> Dólar");
                    break;
                case 5:
                    monedaIn = "USD";
                    monedaOut = "COP";
                    System.out.println("Eligió la conversión Dólar =>>> Peso Colombiano");
                    break;
                case 6:
                    monedaIn = "COP";
                    monedaOut = "USD";
                    System.out.println("Eligió la conversión Peso Colombiano =>>> Dólar");
                    break;
                case 7:
                    monedaIn = "USD";
                    monedaOut = "PEN";
                    System.out.println("Eligió la conversión Dólar =>>> Sol Peruano");
                    break;
                case 8:
                    monedaIn = "PEN";
                    monedaOut = "USD";
                    System.out.println("Eligió la conversión Sol Peruano =>>> Dólar");
                    break;
                case 0:
                    System.out.println("Gracias por usar el conversor de monedas Eureka!");
                    continue;
                default:
                    System.out.println("Opción no válida");
                    continue;
            }
            System.out.println("*******************************************");
            System.out.println("Qué monto desea convertir: ");
            conversion = lectura.nextDouble();

            //System.out.println("Escriba la moneda que desea convertir: ");
            //var monedaIn = lectura.nextLine();
            //System.out.println("Escriba la moneda a la que desea convertir: ");
            //var monedaOut = lectura.nextLine();


            String direccion = "https://v6.exchangerate-api.com/v6/3c11c1511fec02155c6c9fa6/pair/"+monedaIn+"/"+monedaOut;
            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        //.uri(URI.create("https://v6.exchangerate-api.com/v6/3c11c1511fec02155c6c9fa6/pair/USD/BOB"))
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);

                Gson gson = new Gson();
            /* Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();*/
                MonedaExRate miMonedaExRate = gson.fromJson(json, MonedaExRate.class);
                //System.out.println("Datos de conversión: " + miMonedaExRate);
                double monedaConvertida = miMonedaExRate.conversion_rate()*conversion;
                Moneda miMoneda = new Moneda(miMonedaExRate);
                System.out.println("Conversión: " + conversion + " " + miMonedaExRate.base_code() + " equivale a "
                        + monedaConvertida + " " + miMonedaExRate.target_code());
                //System.out.println("Moneda convertida: " + miMoneda);
            } catch (NumberFormatException e){
                System.out.println("Ocurrió un error: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verificar la dirección. ");
            } catch (Exception e){
                System.out.println("Ocurrió un error inesperado, revise si escribió correctamente la moneda");
            }
        }
        System.out.println("Finalizó la ejecución del programa");
        lectura.close();
    }

}
