package vo.dao;

import vo.CompanyVO;
import vo.EmployeeVO;

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

public class EmployeeDAO
{
    private DataSource ds;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public EmployeeDAO()
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

    public boolean insertEmployee(CompanyVO companyVO, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            String role = request.getParameter("role");
            String comm = request.getParameter("comment");

            con = ds.getConnection();
            pstmt=con.prepareStatement("INSERT INTO EMPLOYEE (NAME, ROLE, COMMENT, COMPANY_ID) VALUES (?, ?, ?, ?);");

            pstmt.setString(1, name);
            pstmt.setString(2, role);
            pstmt.setString(3, comm);
            pstmt.setInt(4, companyVO.getId());

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
    public boolean updateEmployee(int employeeID, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            String name = request.getParameter("name");
            String role = request.getParameter("role");
            String comm = request.getParameter("comment");
            int company_id = Integer.parseInt(request.getParameter("companyID"));

            con = ds.getConnection();
            pstmt=con.prepareStatement("UPDATE EMPLOYEE SET NAME = ?, ROLE = ?, COMMENT = ?, COMPANY_ID = ? WHERE ID = ?;");
            pstmt.setString(1, name);
            pstmt.setString(2, role);
            pstmt.setString(3, comm);
            pstmt.setInt(4, company_id);
            pstmt.setInt(5, employeeID);

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

    public void deleteEmployees(int companyID, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            con = ds.getConnection();
            pstmt=con.prepareStatement("DELETE FROM EMPLOYEE WHERE COMPANY_ID = ?;");
            pstmt.setInt(1, companyID);
            pstmt.executeUpdate();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }
    }

    public boolean deleteEmployee(int employeeID, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            con = ds.getConnection();
            pstmt=con.prepareStatement("DELETE FROM EMPLOYEE WHERE ID = ?");
            pstmt.setInt(1, employeeID);
            return pstmt.executeUpdate() != 0;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }

        return false;
    }

    public Optional<String> verify(javax.servlet.http.HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if(Objects.isNull(session)) return Optional.empty();
        String userID = (String) session.getAttribute("userID");
        return Optional.ofNullable(userID);
    }

    public Optional<EmployeeVO> get(int employeeID, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            con = ds.getConnection();
            pstmt=con.prepareStatement("SELECT * FROM EMPLOYEE WHERE ID = ?;");
            pstmt.setInt(1, employeeID);
            rs = pstmt.executeQuery();

            EmployeeVO vo = null;
            if(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String role  = rs.getString("ROLE");
                String comment = rs.getString("COMMENT");
                int company_id = rs.getInt("COMPANY_ID");
                vo = new EmployeeVO(id, company_id, name, role, comment);
            }

            return Optional.ofNullable(vo);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }

        return Optional.empty();
    }

    public List<EmployeeVO> getAll(CompanyVO companyVO, javax.servlet.http.HttpServletRequest request)
    {
        try
        {
            String sql = "SELECT * FROM EMPLOYEE WHERE COMPANY_ID = ?;";

            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, companyVO.getId());
            rs = pstmt.executeQuery();

            List<EmployeeVO> list = new ArrayList<>();
            while(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String role  = rs.getString("ROLE");
                String comment = rs.getString("COMMENT");
                int company_id = rs.getInt("COMPANY_ID");

                EmployeeVO vo = new EmployeeVO(id, company_id, name, role, comment);
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
