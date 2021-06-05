import annotation.CustomController;
import annotation.EndPoint;
import service.Util;
import vo.CompanyVO;
import vo.EmployeeVO;
import vo.Response;
import vo.dao.CompanyDAO;
import vo.dao.EmployeeDAO;
import vo.dao.MemberDAO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@CustomController("EmployeeController")
public class EmployeeController
{
    /* [ Session 정보 ]
     * isValid : 로그인 유무 (true / null)
     * userID : 로그인된 계정의 아이디 (exist / null)
     * (userID는 해당 유저가 등록한 명함 등을 정보를 식별하는데 사용된다)
     */

    @EndPoint("/employeeForm.do")
    public Response companyForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeDAO dao = new EmployeeDAO();
        Response resp = new Response("/companyList.do", true);
        response.setContentType("text/html; charset=UTF-8");

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        if(optionalID.isPresent())
        {
            int employeeID = Integer.parseInt(optionalID.get());
            Optional<EmployeeVO> optionalVO = dao.get(employeeID, request);
            if(!optionalVO.isPresent()) return resp;

            EmployeeVO employeeVO =  optionalVO.get();
            request.setAttribute("vo", employeeVO);
        }

        return new Response("employee_write.jsp");
    }

    @EndPoint("/employeeInfo.do")
    public Response employeeInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeDAO dao = new EmployeeDAO();
        Response resp = new Response("/companyList.do", true);

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        if(!optionalID.isPresent()) return resp;

        int employeeID = Integer.parseInt(optionalID.get());
        Optional<EmployeeVO> optionalVO = dao.get(employeeID, request);
        if(!optionalVO.isPresent()) return resp;

        EmployeeVO employeeVO =  optionalVO.get();
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("vo", employeeVO);
        return new Response("employee_view.jsp");
    }

    @EndPoint("/insertEmployee.do")
    public Response insertEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        Response resp = new Response("/companyList.do", true);;
        try
        {
            Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
            if(!optionalID.isPresent()) return resp;
            int companyID = Integer.parseInt(optionalID.get());

            CompanyDAO companyDAO = new CompanyDAO();
            Optional<CompanyVO> optionalCompanyVO = companyDAO.verifyOwner(companyID, request, false);
            if(!optionalCompanyVO.isPresent()) return resp;
            CompanyVO companyVO = optionalCompanyVO.get();

            EmployeeDAO employeeDAO = new EmployeeDAO();
            if(!employeeDAO.insertEmployee(companyVO, request)) return new Response("/employeeForm.do?companyID="+companyID);
            return new Response("/companyInfo.do?companyID="+companyID, true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new Response("/companyList.do", true);
    }

    @EndPoint("/updateEmployee.do")
    public Response updateEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeDAO dao = new EmployeeDAO();
        Response resp = new Response("/companyList.do", true);

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        if(!optionalID.isPresent()) return resp;

        int employeeID = Integer.parseInt(optionalID.get());
        if(!dao.updateEmployee(employeeID, request)) return new Response("/employeeForm.do?employeeID="+employeeID, true);
        return new Response("/employeeInfo.do?employeeID="+employeeID, true);
    }

    @EndPoint("/deleteEmployee.do")
    public Response deleteEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeDAO dao = new EmployeeDAO();
        Response resp = new Response("/companyList.do", true);

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        if(!optionalID.isPresent()) return resp;
        int employeeID = Integer.parseInt(optionalID.get());

        Optional<EmployeeVO> optionalVO = dao.get(employeeID, request);
        if(!optionalVO.isPresent()) return resp;
        EmployeeVO employeeVO =  optionalVO.get();

        if(!dao.deleteEmployee(employeeID, request)) return new Response("/employeeInfo.do?employeeID="+employeeID, true);
        return new Response("/companyInfo.do?companyID="+employeeVO.getCompany_id(), true);
    }
}
