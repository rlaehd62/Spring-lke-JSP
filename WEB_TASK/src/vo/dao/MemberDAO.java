package vo.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class MemberDAO
{
    DataSource ds;
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public MemberDAO()
    {
        try
        {
            Context init = new InitialContext();
            ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
        } catch (Exception ex)
        {
            System.out.println("DB 연결 실패 : " + ex);
            return;
        }
    }

    private void close()
    {
        try
        {
            if (!rs.isClosed()) rs.close();
            if (!pstmt.isClosed()) pstmt.close();
            if (!con.isClosed()) con.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean insertMember(javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            String id=request.getParameter("id");
            String pass=request.getParameter("pass");
            String name=request.getParameter("name");
            int age=Integer.parseInt(request.getParameter("age"));
            String gender=request.getParameter("gender");
            String email=request.getParameter("email");

            con = ds.getConnection();
            pstmt=con.prepareStatement("INSERT INTO MEMBERS VALUES (?,?,?,?,?,?);");

            pstmt.setString(1,id);
            pstmt.setString(2,pass);
            pstmt.setString(3,name);
            pstmt.setInt(4,age);
            pstmt.setString(5,gender);
            pstmt.setString(6,email);
            int result=pstmt.executeUpdate();

            return (result != 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }
        return false;
    }

    public boolean verifyID(javax.servlet.http.HttpServletRequest request)
    {
        Optional<String> opID = Optional.ofNullable(request.getParameter("id"));
        if(!opID.isPresent()) return false;

        try
        {
            String ID = opID.get();

            con = ds.getConnection();
            String sql = "SELECT * FROM MEMBERS WHERE ID = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally { close(); }

        return false;
    }

    public boolean isValid(javax.servlet.http.HttpServletRequest request)
    {
        Optional<String> opID = Optional.ofNullable(request.getParameter("id"));
        Optional<String> opPW = Optional.ofNullable(request.getParameter("pw"));
        if(!opID.isPresent() || !opPW.isPresent()) return false;

        try
        {
            String ID = opID.get();
            String PW = opPW.get();

            con = ds.getConnection();
            String sql = "SELECT * FROM MEMBERS WHERE ID = ? AND PW = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, ID);
            pstmt.setString(2, PW);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally { close(); }

        return false;
    }
}
