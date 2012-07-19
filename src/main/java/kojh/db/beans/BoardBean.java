package kojh.db.beans;

public class BoardBean
{	
	int id;	
	String name;
	String mail;	
	String subject;
	String created_date;
	int hits;
	String memo;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getMail()
	{
		return mail;
	}
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public int getHits()
	{
		return hits;
	}
	public void setHits(int hits)
	{
		this.hits = hits;
	}
	public String getMemo()
	{
		return memo;
	}
	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	
	public String getCreated_date()
	{
		return created_date;
	}
	public void setCreated_date(String created_date)
	{
		this.created_date = created_date;
	}
}
