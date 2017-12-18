package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.SafeHtml;


public class SearchTemplateForm {

	//Attributes
	//=====================================================

	private String	relationship;
	private int		age;
	private String	genre;
	private String	keyword;
	private String	country;
	private String	state;
	private String	province;
	private String	city;

	
	//Constructor
	//=====================================================

	public SearchTemplateForm() {
		super();
	}
	
	
	//Getters & Setters
	//=====================================================
	
	
	@SafeHtml
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@Min(18)
	@Max(100)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@SafeHtml
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@SafeHtml
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@SafeHtml
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@SafeHtml
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@SafeHtml
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@SafeHtml
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}