package server;

import java.util.ArrayList;
import java.util.List;

public class Methods {
    public String generateCurp(String name, String lastname, String surname, String genre, String state, String birthday) {
        String curp = lastname.substring(0, 2) + surname.charAt(0) + name.charAt(0) + birthday.substring(8, 10) +
                birthday.substring(3, 5) + birthday.substring(0, 2);
        return curp.toUpperCase();
    }

    public String searchPerson(String curp) {
        DaoPerson person = new DaoPerson();
        List<BeanPerson> people = person.showData();
        int findPerson = -1;

        do {
            for (int i = 0; i < people.size(); i++) {
                if (people.get(i).getCurp().equalsIgnoreCase(curp)) {
                    findPerson = i;
                    break;
                }
            }
        } while (findPerson == -1);

        String response = "";

        for (int i = 0; i < people.size(); i++) {
            response += "["+people.get(findPerson).getName()+" "+
                    people.get(findPerson).getLastname()+" "+
                    people.get(findPerson).getSurname()+" | "+
                    people.get(findPerson).getGenre()+" | "+
                    people.get(findPerson).getState()+" | "+
                    people.get(findPerson).getBirthDay()+" | "+
                    people.get(findPerson).getCurp()+"]";
        }
        return response;
    }
}
