package com.example.relazioneingdelsoft;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteClientController extends DeletePersonController {

    @Override
    protected String getQuery() {
        return "SELECT C._id AS 'idClient', C.lastName AS 'lastname', C.firstName AS 'firstname', C.birthday AS 'birthday' " +
                "FROM boni_srl.Client AS C " +
                "LEFT JOIN boni_srl.Job AS J ON C._id = J.idClient " +
                "WHERE J.idClient IS NULL;";
    }

    @Override
    protected String getStringFromResultSet(ResultSet rs) throws SQLException {
        return rs.getInt("idClient") + "-" + rs.getString("lastname") + " " + rs.getString("firstname") + " " + rs.getString("birthday");
    }

    @Override
    protected String getDefaultSelection() {
        return "SELECT CLIENT";
    }
}
