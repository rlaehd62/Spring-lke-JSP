package vo.dao;

import vo.CompanyVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompanyDAO
{
    DataSource ds;
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public CompanyDAO()
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

    private void close()
    {
        try
        {
            if(!rs.isClosed()) rs.close();
            if(!pstmt.isClosed()) pstmt.close();
            if(!con.isClosed()) con.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<CompanyVO> getAll()
    {
        try
        {
            con = ds.getConnection();
            String sql = "SELECT * FROM COMPANY;";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<CompanyVO> list = new ArrayList<>();
            while(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String address = rs.getString("ADDRESS");
                String comment = rs.getString("COMMENT");

                CompanyVO vo = new CompanyVO(id, name, address, comment);
                list.add(vo);
            }

            return list;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally { close(); }
        return Collections.emptyList();
    }
}
