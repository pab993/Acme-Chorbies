package forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;


public class ChorbiForm{

	public ChorbiForm(){
		super();
	}
	
	private String		username;
	private String		password;
	private String		repeatPassword;
	private String		name;
	private String		surname;
	private String		phone;
	private String		email;
	
	private String relationship;
	private String gene;
	private String description;
	private String picture;
	private Date birth;
	
	
	
	private String city;
	
	private String province;
	
	private String country;
	private String state;
	
	private boolean		check;
	
	@Column(unique = true)
	@Size(min = 5, max = 32)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	@NotNull(message = "not match")
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Pattern(regexp = "^[(][+][0-9]{2,3}[)][0-9]{9}$")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Email
	@NotBlank
	@Column(unique = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getCheck(){
		return check;
	}
	public void setCheck(boolean check){
		this.check = check;
	}
	
	
	@NotBlank
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="you must to have +17")
	public Date getBirth(){
		return birth;
	}
	public void setBirth(Date birth){
		this.birth = birth;
	}
	
	
	@Pattern(regexp = "^ACTIVITIES|LOVE|FRIENDSHIP$", message ="necessary information")
	public String getRelationship(){
		return relationship;
	}
	public void setRelationship(String relationship){
		this.relationship = relationship;
	}
	
	@Pattern(regexp = "^MAN|WOMAN$", message="necessary information")
	public String getGene(){
		return gene;
	}
	public void setGene(String gene){
		this.gene = gene;
	}
	
	@URL
	@NotBlank
	public String getPicture(){
		return picture;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}
	
	
	
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	
	@NotBlank
	public String getCountry(){
		return country;
	}
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public String getProvince(){
		return province;
	}
	public void setProvince(String province){
		this.province = province;
	}
	
	
	@NotBlank
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	
	
}
