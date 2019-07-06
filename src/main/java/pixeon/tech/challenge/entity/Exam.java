package pixeon.tech.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

import pixeon.tech.challenge.web.jsonview.Views;

@Entity
public class Exam {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    Long id;
	
	@ManyToOne
    @JsonView(Views.Public.class)
    @JoinColumn(name = "idHealthCareInstitution")
    private HealthCareInstitution healthCareInstitution;
	
	@JsonView(Views.Public.class)
    private String patientName;
	
	@JsonView(Views.Public.class)
    private int patientAge;
	
	@JsonView(Views.Public.class)
    private String patientGender;
	
	@JsonView(Views.Public.class)
    private String physicianName;
	
	@JsonView(Views.Public.class)
    private String physicianCRM;
	
	@JsonView(Views.Public.class)
    private String procedureName;
	
	@JsonView(Views.Public.class)
    private boolean examRecovered;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HealthCareInstitution getHealthCareInstitution() {
		return healthCareInstitution;
	}

	public void setHealthCareInstitution(HealthCareInstitution healthCareInstitution) {
		this.healthCareInstitution = healthCareInstitution;
	}

	public boolean isExamRecovered() {
		return examRecovered;
	}

	public void setExamRecovered(boolean examRecovered) {
		this.examRecovered = examRecovered;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	public String getPhysicianCRM() {
		return physicianCRM;
	}

	public void setPhysicianCRM(String physicianCRM) {
		this.physicianCRM = physicianCRM;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

}
