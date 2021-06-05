package vo.dao;

import com.oreilly.servlet.MultipartRequest;
import vo.CompanyVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompanyDAO
{
    private DataSource ds;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

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
            if(Objects.nonNull(rs) && !rs.isClosed()) rs.close();
            if(Objects.nonNull(pstmt) && !pstmt.isClosed()) pstmt.close();
            if(Objects.nonNull(con) && !con.isClosed()) con.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean insertCompany(javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            Optional<String> verification = verify(request);
            if(!verification.isPresent()) return false;

            request.setCharacterEncoding("UTF-8");
            String userID = verification.get();
            String name = request.getParameter("name");
            String addr = request.getParameter("address");
            String comm = request.getParameter("comment");

            con = ds.getConnection();
            pstmt=con.prepareStatement("INSERT INTO COMPANY (NAME, OWNER, ADDRESS, COMMENT) VALUES (?, ?, ?, ?);");

            pstmt.setString(1, name);
            pstmt.setString(2, userID);
            pstmt.setString(3, addr);
            pstmt.setString(4, comm);

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

    public boolean updateCompany(int companyID, javax.servlet.http.HttpServletRequest request)
    {
        AtomicBoolean result = new AtomicBoolean(false);
        try
        {
            String name = request.getParameter("name");
            String addr = request.getParameter("address");
            String comm = request.getParameter("comment");

            con = ds.getConnection();

            System.out.println("NAME >>  " + name);
            System.out.println("COMMENT >> " + comm);

            pstmt=con.prepareStatement("UPDATE COMPANY SET NAME = ?, ADDRESS = ?, COMMENT = ? WHERE ID = ?;");
            pstmt.setString(1, name);
            pstmt.setString(2, addr);
            pstmt.setString(3, comm);
            pstmt.setInt(4, companyID);
            result.set(pstmt.executeUpdate() != 0);

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }

        return result.get();
    }

    public boolean deleteCompany(int companyID, javax.servlet.http.HttpServletRequest request)
    {
        Optional<CompanyVO> optional = verifyOwner(companyID, request, false);
        AtomicBoolean result = new AtomicBoolean(false);

        optional.ifPresent(vo ->
        {
            try
            {
                pstmt=con.prepareStatement("DELETE FROM COMPANY WHERE ID = ? AND OWNER = ?;");
                pstmt.setInt(1, companyID);
                pstmt.setString(2, vo.getOwner());
                result.set(pstmt.executeUpdate() != 0);
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                close();
            }
        });

        return result.get();
    }

    public Optional<CompanyVO> verifyOwner(int companyID, javax.servlet.http.HttpServletRequest request, boolean isConnected)
    {
        try
        {
            Optional<String> verification = verify(request);
            if(!verification.isPresent()) return Optional.empty();
            String userID = verification.get();

            request.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            if(!isConnected) con = ds.getConnection();
            pstmt=con.prepareStatement("SELECT * FROM COMPANY WHERE ID = ? AND OWNER = ?;");
            pstmt.setInt(1, companyID);
            pstmt.setString(2, userID);
            rs = pstmt.executeQuery();

            CompanyVO vo = null;
            if(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String owner = rs.getString("OWNER");
                String address = rs.getString("ADDRESS");
                String comment = rs.getString("COMMENT");
                vo = new CompanyVO(id, owner, name, address, comment);
            }

            return Optional.ofNullable(vo);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<String> verify(javax.servlet.http.HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if(Objects.isNull(session)) return Optional.empty();
        String userID = (String) session.getAttribute("userID");
        return Optional.ofNullable(userID);
    }

    public List<CompanyVO> getAll(javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            Optional<String> verification = verify(request);
            if(!verification.isPresent()) return Collections.emptyList();
            String userID = verification.get();

            String sql = "SELECT * FROM COMPANY WHERE OWNER = ?;";

            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            List<CompanyVO> list = new ArrayList<>();
            while(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String owner = rs.getString("OWNER");
                String address = rs.getString("ADDRESS");
                String comment = rs.getString("COMMENT");

                CompanyVO vo = new CompanyVO(id, owner, name, address, comment);
                list.add(vo);
            }

            return list;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }
        return Collections.emptyList();
    }

}
