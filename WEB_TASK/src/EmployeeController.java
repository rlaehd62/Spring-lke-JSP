import annotation.CustomController;
import annotation.EndPoint;
import service.EmployeeService;
import vo.Response;

import java.io.IOException;

@CustomController("EmployeeController")
public class EmployeeController
{
    /* [ Session 정보 ]
     * isValid : 로그인 유무 (true / null)
     * userID : 로그인된 계정의 아이디 (exist / null)
     * (userID는 해당 유저가 등록한 명함 등을 정보를 식별하는데 사용된다)
     */

    @EndPoint("/employeeForm.do")
    public Response employeeForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeService service = new EmployeeService();
        return service.employeeForm(request, response);
    }

    @EndPoint("/employeeInfo.do")
    public Response employeeInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeService service = new EmployeeService();
        return service.employeeInfo(request, response);
    }

    @EndPoint("/insertEmployee.do")
    public Response insertEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        EmployeeService service = new EmployeeService();
        return service.insertEmployee(request, response);
    }

    @EndPoint("/updateEmployee.do")
    public Response updateEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeService service = new EmployeeService();
        return service.updateEmployee(request, response);
    }

    @EndPoint("/deleteEmployee.do")
    public Response deleteEmployee(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        EmployeeService service = new EmployeeService();
        return service.deleteEmployee(request, response);
    }
}
