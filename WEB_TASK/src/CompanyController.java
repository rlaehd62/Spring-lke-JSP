import annotation.CustomController;
import annotation.EndPoint;
import service.CompanyService;
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
    public Response companyInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        return service.info(request, response);
    }

    @EndPoint("/companyForm.do")
    public Response companyForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        return service.form(request, response);
    }

    @EndPoint("/insertCompany.do")
    public void insertCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        service.insert(request, response);
    }

    @EndPoint("/updateCompany.do")
    public Response updateCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        return service.update(request, response);
    }

    @EndPoint("/deleteCompany.do")
    public Response deleteCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        return service.delete(request, response);
    }

    @EndPoint("/companyList.do")
    public Response companyList(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyService service = new CompanyService();
        return service.list(request, response);
    }
}
