package server;

import utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPerson {
    Connection conn;
    PreparedStatement pstm;
    CallableStatement cstm;
    ResultSet rs;

    public List<BeanPerson> showData() {
        List<BeanPerson> people = new ArrayList<>();
        BeanPerson person = null;
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM person;");
            rs = pstm.executeQuery();
            while (rs.next()) {
                person = new BeanPerson();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name_person"));
                person.setLastname(rs.getString("lastname"));
                person.setSurname(rs.getString("surname"));
                person.setGenre(rs.getString("genre"));
                person.setState(rs.getString("state"));
                person.setBirthDay(rs.getString("birth_day"));
                person.setCurp(rs.getString("curp"));
                people.add(person);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, "Error ->", e);
        } finally {
            closeConnection();
        }
        return people;
    }

    public boolean saveData(BeanPerson person) {
        try {
            conn = new MySQLConnection().getConnection();
            pstm = conn.prepareStatement("INSERT INTO person (name_person, lastname, surname, genre, state, birth_day, curp)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstm.setString(1, person.getName());
            pstm.setString(2, person.getLastname());
            pstm.setString(3, person.getSurname());
            pstm.setString(4, person.getGenre());
            pstm.setString(5, person.getState());
            pstm.setString(6, person.getBirthDay());
            pstm.setString(7, person.getCurp());
            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoPerson.class.getName()).log(Level.SEVERE, "Error saveOperation", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (cstm != null) {
                cstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {

        }
    }
}
