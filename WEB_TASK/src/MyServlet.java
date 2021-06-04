import annotation.CustomController;
import annotation.EndPoint;
import bean.BeanManager;
import bean.ControllerBean;
import org.reflections.Reflections;
import vo.Response;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@WebServlet(urlPatterns = "*.do")
public class MyServlet extends javax.servlet.http.HttpServlet
{
    private void doProcess(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        String RequestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = RequestURI.substring(contextPath.length());

        BeanManager manager = BeanManager.getInstance();
        manager.findBean("MyController").ifPresent(bean ->
        {
            bean.getMethod(command).ifPresent(method ->
            {
                try
                {
                    Object obj = bean.getClassObject();
                    Response res = (Response) method.invoke(obj, request, response);

                    if(Objects.isNull(res)) return;
                    else if(res.isRedirected())
                    {
                        response.sendRedirect(res.getPath());
                        return;
                    }

                    RequestDispatcher dispatcher = request.getRequestDispatcher(res.getPath());
                    dispatcher.forward(request, response);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        });
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        doProcess(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        doProcess(request, response);
    }
}
