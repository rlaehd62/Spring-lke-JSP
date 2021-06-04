package vo;

public class CompanyVO
{
    /*
     * name: 회사 이름
     * address: 회사 주소
     * comment: 주석 (특이사항)
     */

    private int id;
    private String name;
    private String address;
    private String comment;

    public CompanyVO(int id, String name, String address, String comment)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.comment = comment;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Override
    public String toString()
    {
        return "CompanyVO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
