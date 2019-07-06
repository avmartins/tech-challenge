package pixeon.tech.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import pixeon.tech.challenge.web.jsonview.Views;

@Entity
public class HealthCareInstitution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Public.class)
	Long id;

	@JsonView(Views.Public.class)
	private String name;

	@Column(unique = true, nullable = false)
	private String cnpj;

	@Column(nullable = false)
	private int moeda;

	@Column(nullable = false)
	private int qtdMoedaUsada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public int getMoeda() {
		return moeda;
	}

	public void setMoeda(int moeda) {
		this.moeda = moeda;
	}

	public int getQtdMoedaUsada() {
		return qtdMoedaUsada;
	}

	public void setQtdMoedaUsada(int qtdMoedaUsada) {
		this.qtdMoedaUsada = qtdMoedaUsada;
	}

	public int getQtdMoedaSaldo() {
		return moeda - qtdMoedaUsada;
	}

}
