package features.clientService;

import features.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public ClientService() {
    }

    public long create(String name) {
        long id = 0;
        if (name == null || name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name should not be NULL and with length from 2 to 1000 characters");
        } else {
            String insertClientSQL = "INSERT INTO client (name) VALUES (?)";
            String getIdQuerySQL = "SELECT MAX(id) AS id FROM client WHERE name = ?";

            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement pstInsClient = conn.prepareStatement(insertClientSQL);
                 PreparedStatement pstGetId = conn.prepareStatement(getIdQuerySQL)) {

                pstInsClient.setString(1, name);
                int insResult = pstInsClient.executeUpdate();

                if (insResult == 1) {
                    pstGetId.setString(1, name);
                    ResultSet rs = pstGetId.executeQuery();
                    if (rs.next()) {
                        id = rs.getLong("id");
                    }
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }

    public String getById(long id) {
        String name = null;
        if (id <= 0) {
            throw new IllegalArgumentException("ID should be more than 0");
        } else {
            String getByIdSQL = "SELECT name FROM client WHERE id = ?";

            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement pstGetById = conn.prepareStatement(getByIdSQL)) {

                pstGetById.setLong(1, id);
                ResultSet rs = pstGetById.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                }
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return name;
    }

    public void setName(long id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID should be more than 0");
        } else if (name == null || name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name should not be NULL and with length from 2 to 1000 characters");
        } else {
            String setNewNameSQL = "UPDATE client SET name = ? WHERE id = ?";

            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement pstSetNewName = conn.prepareStatement(setNewNameSQL)) {

                pstSetNewName.setString(1, name);
                pstSetNewName.setLong(2, id);
                pstSetNewName.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID should be more than 0");
        } else {
            String setDeleteClientSQL = "DELETE FROM client WHERE id = ?";

            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement pstDeleteClient = conn.prepareStatement(setDeleteClientSQL)) {

                pstDeleteClient.setLong(1, id);
                pstDeleteClient.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String listAllSQL = "SELECT * FROM client";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstListAll = conn.prepareStatement(listAllSQL);
             ResultSet rs = pstListAll.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                clients.add(new Client(id, name));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return clients;
    }
}
