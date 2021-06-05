package service;

import java.io.IOException;
import java.io.PrintWriter;

public class Util
{
    public static void printMessage(javax.servlet.http.HttpServletResponse response, String msg, String url) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + msg + "');");
        if(!url.equals("")) out.println("location.href = " + "'" + url + "'");
        out.println("</script>");
    }
}
