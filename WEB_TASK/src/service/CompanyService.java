package service;

import vo.CompanyVO;
import vo.Response;
import vo.dao.CompanyDAO;
import vo.dao.EmployeeDAO;

import java.io.IOException;
import java.util.Optional;

public class CompanyService
{
    private CompanyDAO companyDAO;
    public CompanyService() { companyDAO = new CompanyDAO(); }

    public Response info(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<CompanyVO> optionalVO = companyDAO.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
                    return optionalVO.map(companyVO ->
                    {
                        response.setContentType("text/html; charset=UTF-8");
                        request.setAttribute("vo", companyVO);

                        EmployeeDAO employeeDAO = new EmployeeDAO();

                        request.setAttribute("list", employeeDAO.getAll(companyVO, request));
                        return new Response("company_view.jsp");
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response form(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<CompanyVO> optionalVO = companyDAO.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
                    return optionalVO.map(companyVO ->
                    {
                        request.setAttribute("vo", optionalVO.get());
                        return new Response("company_write.jsp");
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("company_write.jsp"));
    }

    public void insert(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        if(companyDAO.insertCompany(request)) Util.printMessage(response, "회사를 등록했습니다.", "/companyList.do");
        else Util.printMessage(response, "회사를 등록하는데 실패했습니다.", "/companyList.do");
    }

    public Response update(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    companyDAO.updateCompany(Integer.parseInt(optionalID.get()), request);
                    return new Response("/companyInfo.do?companyID=" + optionalID.get());
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response delete(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    if(!companyDAO.deleteCompany(id, request)) return new Response("/companyInfo.do?companyID="+id, true);
                    employeeDAO.deleteEmployees(id, request);
                    return new Response("/companyList.do", true);
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response list(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        request.setAttribute("list", companyDAO.getAll(request));
        return new Response("company_list.jsp");
    }
}
