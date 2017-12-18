package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor{

	
	//Relationships 
	// ==========================================================
	
	private Collection<Dalem> dalems;
	
	@OneToMany(mappedBy = "administrator")
	public Collection<Dalem> getDalems() {
		return this.dalems;
	}

	public void setDalems(Collection<Dalem> dalems) {
		this.dalems = dalems;
	}
}
