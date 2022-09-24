package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import server.BeanPerson;
import server.DaoPerson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        String name, lastname, surname, genre, state, birthday;
        BeanPerson person = new BeanPerson();
        DaoPerson daoPerson = new DaoPerson();

        System.out.println("Acción a realizar");
        System.out.println("1. Registrar persona");
        System.out.println("2. Consultar persona");
        System.out.print("Selecciona una opción: ");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("~ Generar CURP ~");

                System.out.print("Nombre(s): ");
                name = sc.next();
                person.setName(name);

                System.out.print("Apellido paterno: ");
                lastname = sc.next();
                person.setLastname(lastname);

                System.out.print("Apellido materno: ");
                surname = sc.next();
                person.setSurname(surname);

                System.out.print("Sexo: ");
                genre = sc.next();
                person.setGenre(genre);

                System.out.print("Estado de nacimiento: ");
                state = sc.next();
                person.setState(state);

                System.out.print("Fecha de nacimiento (DD-MM-YYYY): ");
                birthday = sc.next();
                person.setBirthDay(birthday);

                Object[] data = {name, lastname, surname, genre, state, birthday};
                String response = (String) client.execute("Methods.generateCurp", data);
                System.out.println("Result -> " + response);
                person.setCurp(response);

                daoPerson.saveData(person);
                break;
            case 2:
                System.out.println("~ Buscar persona");
                System.out.print("Ingresa el curp de la persona a buscar: ");
                String search = sc.next();

                Object[] searchData = {search};
                String responseSearchData = (String) client.execute("Methods.searchPerson", searchData);
                System.out.println("Datos: " + responseSearchData);
                break;
            default:
                System.out.println("Ingresa una opción valida.");
                break;
        }
    }
}
