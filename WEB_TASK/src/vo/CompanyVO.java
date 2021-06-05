package vo;

public class CompanyVO
{
    /*
     * name: 회사 이름
     * owner: 데이터 소유자
     * address: 회사 주소
     * comment: 주석 (추가정보)
     */

    private int id;
    private String owner;
    private String name;
    private String address;
    private String comment;

    public CompanyVO(int id, String owner, String name, String address, String comment)
    {
        this.id = id;
        this.owner = owner;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyVO companyVO = (CompanyVO) o;
        return id == companyVO.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }
}
