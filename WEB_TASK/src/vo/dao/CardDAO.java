package vo.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardDAO
{
    DataSource ds;
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public CardDAO()
    {
        try
        {
            Context init = new InitialContext();
            ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
        } catch(Exception ex)
        {
            System.out.println("DB 연결 실패 : " + ex);
            return;
        }
    }
}
