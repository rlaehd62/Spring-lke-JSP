import annotation.CustomController;
import annotation.EndPoint;
import service.Util;
import vo.CompanyVO;
import vo.Response;
import vo.dao.CompanyDAO;
import vo.dao.EmployeeDAO;

import java.io.IOException;
import java.util.Optional;

@CustomController("CompanyController")
public class CompanyController
{
    @EndPoint("/companyInfo.do")
    public Response companyInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        Response result = new Response("/companyList.do", true);

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return result;

        Optional<CompanyVO> optionalVO = dao.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
        if(!optionalVO.isPresent()) return result;

        CompanyVO companyVO =  optionalVO.get();
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("vo", companyVO);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        request.setAttribute("list", employeeDAO.getAll(companyVO, request));
        return new Response("company_view.jsp");
    }

    @EndPoint("/companyForm.do")
    public Response companyForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("company_write.jsp");

        Optional<CompanyVO> optionalVO = dao.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
        if(!optionalVO.isPresent()) return new Response("/companyList.do", true);

        request.setAttribute("vo", optionalVO.get());
        return new Response("company_write.jsp");
    }

    @EndPoint("/insertCompany.do")
    public void insertCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyDAO dao = new CompanyDAO();
        if(dao.insertCompany(request)) Util.printMessage(response, "회사를 등록했습니다.", "/companyList.do");
        else Util.printMessage(response, "회사를 등록하는데 실패했습니다.", "/companyList.do");
    }

    @EndPoint("/updateCompany.do")
    public Response updateCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyDAO dao = new CompanyDAO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("/companyList.do", true);

        if(!dao.updateCompany(Integer.parseInt(optionalID.get()), request)) Util.printMessage(response, "수정에 실패했습니다.", "");
        return new Response("/companyInfo.do?companyID=" + optionalID.get());
    }

    @EndPoint("/deleteCompany.do")
    public Response deleteCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("/companyList.do", true);
        int companyID = Integer.parseInt(optionalID.get());

        if(!dao.deleteCompany(companyID, request)) return new Response("/companyInfo.do?companyID=" + optionalID.get(), true);
        employeeDAO.deleteEmployees(companyID, request);
        return new Response("/companyList.do", true);
    }

    @EndPoint("/companyList.do")
    public Response companyList(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        request.setAttribute("list", dao.getAll(request));
        return new Response("company_list.jsp");
    }
}
