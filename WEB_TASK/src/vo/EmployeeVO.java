package vo;

public class EmployeeVO
{
    /*
     * name : 이름
     * role : 직위/직급
     * comment : 주석 (추가 정보)
     * company_id : 소속 회사
     */

    private int id;
    private int company_id;
    private String name;
    private String role;
    private String comment;

    public EmployeeVO(int id, int company_id, String name, String role, String comment)
    {
        this.id = id;
        this.company_id = company_id;
        this.name = name;
        this.role = role;
        this.comment = comment;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCompany_id()
    {
        return company_id;
    }

    public void setCompany_id(int company_id)
    {
        this.company_id = company_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
