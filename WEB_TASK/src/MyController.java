import annotation.CustomController;
import annotation.EndPoint;
import vo.CompanyVO;
import vo.Response;
import vo.dao.CompanyDAO;
import vo.dao.MemberDAO;

import javax.jms.Session;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@CustomController("MyController")
public class MyController
{
    /* [ Session 정보 ]
     * isValid : 로그인 유무 (true / null)
     * userID : 로그인된 계정의 아이디 (exist / null)
     * (userID는 해당 유저가 등록한 명함 등을 정보를 식별하는데 사용된다)
     */

    @EndPoint("/verifyID.do")
    public void verifyID(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        MemberDAO dao = new MemberDAO();
        if(dao.verifyID(request)) printMessage(response, "[중복] 아이디가 이미 존재합니다.", "");
        else printMessage(response, "[Verified] 이 아이디는 사용 가능합니다.", "");
    }

    @EndPoint("/joinForm.do")
    public Response registerForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");
        return new Response("joinForm.jsp");
    }

    @EndPoint("/join.do")
    public void join(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        MemberDAO dao = new MemberDAO();
        if(dao.insertMember(request)) printMessage(response, "회원가입에 성공했습니다, 환영합니다!", "/loginForm.do");
        else printMessage(response, "가입에 실패했습니다, 다시 시도해주세요.", "/joinForm.do");
    }

    @EndPoint("/loginForm.do")
    public Response loginForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        return new Response("loginForm.jsp");
    }

    @EndPoint("/login.do")
    public void login(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        MemberDAO dao = new MemberDAO();
        if(!dao.isValid(request)) printDenialMessage(response);
        else
        {
            Optional<String> opID = Optional.ofNullable(request.getParameter("id"));
            opID.ifPresent(id ->
            {
                HttpSession session = request.getSession(true);
                session.removeAttribute("isValid");
                session.removeAttribute("userID");
                session.setAttribute("isValid", true);
                session.setAttribute("userID", id);
            });

            printAcceptMessage(response);

        }
    }

    @EndPoint("/companyInfo.do")
    public Response companyInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        Response result = new Response("/companyList.do", true);

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return result;

        Optional<CompanyVO> optionalVO = dao.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
        if(!optionalVO.isPresent()) return result;

        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("vo", optionalVO.get());
        return new Response("company_view.jsp");
    }

    @EndPoint("/companyForm.do")
    public Response companyForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("company_write.jsp", true);

        Optional<CompanyVO> optionalVO = dao.verifyOwner(Integer.parseInt(optionalID.get()), request, false);
        if(!optionalVO.isPresent()) return new Response("/companyList.do", true);

        request.setAttribute("vo", optionalVO.get());
        return new Response("company_write.jsp");
    }

    @EndPoint("/insertCompany.do")
    public void insertCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyDAO dao = new CompanyDAO();
        if(dao.insertCompany(request)) printMessage(response, "회사를 등록했습니다.", "/companyList.do");
        else printMessage(response, "회사를 등록하는데 실패했습니다.", "/companyList.do");
    }

    @EndPoint("/updateCompany.do")
    public Response updateCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException
    {
        CompanyDAO dao = new CompanyDAO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("/companyList.do", true);

        if(!dao.updateCompany(Integer.parseInt(optionalID.get()), request)) printMessage(response, "수정에 실패했습니다.", "");
        return new Response("/companyInfo.do?companyID=" + optionalID.get());
    }

    @EndPoint("/deleteCompany.do")
    public Response deleteCompany(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        Optional<String> optionalID = Optional.ofNullable(request.getParameter("companyID"));
        if(!optionalID.isPresent()) return new Response("/companyList.do", true);
        if(!dao.deleteCompany(Integer.parseInt(optionalID.get()), request)) return new Response("/companyInfo.do?companyID=" + optionalID.get(), true);
        return new Response("/companyList.do", true);
    }

    @EndPoint("/companyList.do")
    public Response companyList(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    {
        CompanyDAO dao = new CompanyDAO();
        request.setAttribute("list", dao.getAll(request));
        return new Response("index.jsp");
    }

    private void printAcceptMessage(javax.servlet.http.HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('작업을 성공적으로 수행했습니다!');");
        out.println("location.href = '/companyList.do'");
        out.println("</script>");
    }

    private void printDenialMessage(javax.servlet.http.HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('해당 작업에 대한 권한이 존재하지 않습니다.');");
        out.println("history.back();");
        out.println("</script>");
    }

    private void printMessage(javax.servlet.http.HttpServletResponse response, String msg, String url) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + msg + "');");
        if(!url.equals("")) out.println("location.href = " + "'" + url + "'");
        out.println("</script>");
    }
}
