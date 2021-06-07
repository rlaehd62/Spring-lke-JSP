package service;

import annotation.EndPoint;
import vo.CompanyVO;
import vo.EmployeeVO;
import vo.Response;
import vo.dao.CompanyDAO;
import vo.dao.EmployeeDAO;

import java.io.IOException;
import java.util.Optional;

public class EmployeeService
{
    private EmployeeDAO employeeDAO;
    public EmployeeService() { employeeDAO = new EmployeeDAO(); }

    public Response employeeForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        response.setContentType("text/html; charset=UTF-8");
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<EmployeeVO> optionalVO = employeeDAO.get(id, request);
                    return optionalVO.map(employeeVO ->
                    {
                        CompanyDAO companyDAO = new CompanyDAO();
                        Optional<CompanyVO> optionalCompanyVO = companyDAO.verifyOwner(employeeVO.getCompany_id(), request, false);
                        return optionalCompanyVO.map(companyVO ->
                        {
                            request.setAttribute("vo", employeeVO);
                            return new Response("employee_write.jsp");
                        }).orElseGet(() -> new Response("/companyList.do", true));
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("employee_write.jsp"));
    }

    public Response employeeInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<EmployeeVO> optionalVO = employeeDAO.get(id, request);
                    return optionalVO.map(employeeVO ->
                    {
                        CompanyDAO companyDAO = new CompanyDAO();
                        Optional<CompanyVO> optionalCompanyVO = companyDAO.verifyOwner(employeeVO.getCompany_id(), request, false);
                        return optionalCompanyVO.map(companyVO ->
                        {
                            response.setContentType("text/html; charset=UTF-8");
                            request.setAttribute("vo", employeeVO);
                            return new Response("employee_view.jsp");
                        }).orElseGet(() -> new Response("/companyList.do", true));
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response insertEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    CompanyDAO companyDAO = new CompanyDAO();
                    Optional<CompanyVO> optionalCompanyVO = companyDAO.verifyOwner(id, request, false);
                    return optionalCompanyVO.map(companyVO ->
                    {
                        if(!employeeDAO.insertEmployee(companyVO, request)) return new Response("/employeeForm.do?companyID="+id);
                        return new Response("/companyInfo.do?companyID="+id, true);
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response updateEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<EmployeeVO> optionalVO = employeeDAO.get(id, request);
                    return optionalVO.map(employeeVO ->
                    {
                        CompanyDAO companyDAO = new CompanyDAO();
                        Optional<CompanyVO> optional = companyDAO.verifyOwner(employeeVO.getCompany_id(), request, false);
                        return optional
                                .filter(vo -> employeeDAO.updateEmployee(id, request))
                                .map(vo -> new Response("/employeeInfo.do?employeeID="+id, true))
                                .orElseGet(() -> new Response("/employeeForm.do?employeeID="+id, true));
                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("/companyList.do", true));
    }

    public Response deleteEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("employeeID"));
        return optionalID
                .map(Integer::parseInt)
                .map(id ->
                {
                    Optional<EmployeeVO> optionalVO = employeeDAO.get(id, request);
                    return optionalVO.map(employeeVO ->
                    {
                        CompanyDAO companyDAO = new CompanyDAO();
                        Optional<CompanyVO> optional = companyDAO.verifyOwner(employeeVO.getCompany_id(), request, false);
                        return optional
                                .filter(vo -> employeeDAO.deleteEmployee(id, request))
                                .map(vo ->  new Response("/companyInfo.do?companyID="+employeeVO.getCompany_id(), true))
                                .orElseGet(() -> new Response("/employeeInfo.do?employeeID="+id, true));

                    }).orElseGet(() -> new Response("/companyList.do", true));
                }).orElseGet(() -> new Response("/companyList.do", true));
    }
}
