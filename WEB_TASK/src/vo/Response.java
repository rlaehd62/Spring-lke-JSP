package vo;

public class Response
{
    private boolean isRedirected;
    private String path;

    public Response(String path)
    {
        this(path, false);
    }

    public Response(String path, boolean isRedirected)
    {
        this.path = path;
        this.isRedirected = isRedirected;
    }

    public boolean isRedirected()
    {
        return isRedirected;
    }

    public void setRedirected(boolean redirected)
    {
        isRedirected = redirected;
    }

    public String getPath()
    {
        return path;
    }
}
