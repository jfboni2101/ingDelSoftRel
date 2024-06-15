package com.example.relazioneingdelsoft;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteEmployeeController extends DeletePersonController {

    @Override
    protected String getQuery() {
        return "SELECT E._id AS 'idPerson', E.lastName AS 'lastname', E.firstName AS 'firstname', E.birthday AS 'birthday' " +
                "FROM boni_srl.Employee AS E " +
                "LEFT JOIN boni_srl.Job AS J ON E._id = J.idEmployee " +
                "WHERE J.idEmployee IS NULL;";
    }

    @Override
    protected String getStringFromResultSet(ResultSet rs) throws SQLException {
        return rs.getInt("idPerson") + "-" + rs.getString("lastname") + " " + rs.getString("firstname") + " " + rs.getString("birthday");
    }

    @Override
    protected String getDefaultSelection() {
        return "SELECT EMPLOYEE";
    }
}
