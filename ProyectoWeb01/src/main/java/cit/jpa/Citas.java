package cit.jpa;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Citas")
public class Citas implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Citas() {
		super();
	}
   
}
