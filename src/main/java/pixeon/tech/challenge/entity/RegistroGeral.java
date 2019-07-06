package pixeon.tech.challenge.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RegistroGeral {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private User user;

	private String userRole;

	private LocalDate data;

	private BigDecimal saldoAnterior;

	private BigDecimal saldoAtual;

	private BigDecimal saldoTxt;

	private BigDecimal somaDepositos;

	private double taxaRendimento;

	private double cotaAnterior;

	private double cotaAtual;

	public Long getId() {
		return id;
	}

	public BigDecimal getSomaDepositos() {
		return somaDepositos;
	}

	public void setSomaDepositos(BigDecimal somaDepositos) {
		this.somaDepositos = somaDepositos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public BigDecimal getSaldoTxt() {
		return saldoTxt;
	}

	public void setSaldoTxt(BigDecimal saldoTxt) {
		this.saldoTxt = saldoTxt;
	}

	public double getTaxaRendimento() {
		return taxaRendimento;
	}

	public void setTaxaRendimento(double taxaRendimento) {
		this.taxaRendimento = taxaRendimento;
	}

	public double getCotaAnterior() {
		return cotaAnterior;
	}

	public void setCotaAnterior(double cotaAnterior) {
		this.cotaAnterior = cotaAnterior;
	}

	public double getCotaAtual() {
		return cotaAtual;
	}

	public void setCotaAtual(double cotaAtual) {
		this.cotaAtual = cotaAtual;
	}

	public RegistroGeral() {
	}

	public RegistroGeral(User user, String userRole, LocalDate data, BigDecimal saldoAnterior, BigDecimal saldoAtual,
			double cotaAnterior, double cotaAtual, double taxaRendimento) {
		this.user = user;
		this.userRole = userRole;
		this.data = data;
		this.saldoAnterior = saldoAnterior;
		this.saldoAtual = saldoAtual;
		this.cotaAnterior = cotaAnterior;
		this.cotaAtual = cotaAtual;
		this.taxaRendimento = taxaRendimento;

	}

	public RegistroGeral(User user, String userRole, LocalDate data, BigDecimal saldoAnterior, BigDecimal saldoAtual,
			double taxaRendimento) {
		this.user = user;
		this.userRole = userRole;
		this.data = data;
		this.saldoAnterior = saldoAnterior;
		this.saldoAtual = saldoAtual;
		this.taxaRendimento = taxaRendimento;
	}
}
