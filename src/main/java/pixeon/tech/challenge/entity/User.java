package pixeon.tech.challenge.entity;

import pixeon.tech.challenge.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Anderson Virginio Martins
 */
@Entity
@Table(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Public.class)
	private Long id;

	@JsonView(Views.Public.class)
	private String nome;

	@Column(unique = true, nullable = false)
	private String cpf;

	@JsonView(Views.Public.class)
	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	@JsonView(Views.Public.class)
	private String userRole;

	private LocalDate dataDeAtivacao;
	private LocalDate dataDeDesativacao;

	private LocalDateTime dataUltimoLogin;

	private Boolean isActive;

	@ManyToOne
	@JsonView(Views.Public.class)
	@JoinColumn(name = "idHealthCareInstitution")
	private HealthCareInstitution healthCareInstitution;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public LocalDate getDataDeAtivacao() {
		return dataDeAtivacao;
	}

	public void setDataDeAtivacao(LocalDate dataDeAtivacao) {
		this.dataDeAtivacao = dataDeAtivacao;
	}

	public LocalDate getDataDeDesativacao() {
		return dataDeDesativacao;
	}

	public void setDataDeDesativacao(LocalDate dataDeDesativacao) {
		this.dataDeDesativacao = dataDeDesativacao;
	}

	public LocalDateTime getDataUltimoLogin() {
		return dataUltimoLogin;
	}

	public void setDataUltimoLogin(LocalDateTime dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean active) {
		isActive = active;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(nome, user.nome) && Objects.equals(cpf, user.cpf)
				&& Objects.equals(email, user.email) && Objects.equals(password, user.password)
				&& Objects.equals(userRole, user.userRole) && Objects.equals(dataDeAtivacao, user.dataDeAtivacao)
				&& Objects.equals(dataDeDesativacao, user.dataDeDesativacao)
				&& Objects.equals(dataUltimoLogin, user.dataUltimoLogin) && Objects.equals(isActive, user.isActive);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, cpf, email, password, userRole, dataDeAtivacao, dataDeDesativacao,
				dataUltimoLogin, isActive);
	}

	public User(String nome, String cpf, String email, String password, String userRole, LocalDate dataDeAtivacao,
			Boolean isActive, HealthCareInstitution healthCareInstitution) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
		this.dataDeAtivacao = dataDeAtivacao;
		this.isActive = isActive;
		this.healthCareInstitution = healthCareInstitution;
	}

	public User() {
		super();
	}

	public HealthCareInstitution getHealthCareInstitution() {
		return healthCareInstitution;
	}

	public void setHealthCareInstitution(HealthCareInstitution healthCareInstitution) {
		this.healthCareInstitution = healthCareInstitution;
	}

}
