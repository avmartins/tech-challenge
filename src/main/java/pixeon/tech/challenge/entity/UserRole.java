package pixeon.tech.challenge.entity;

/**
 * @author Anderson Virginio Martins
 */

public enum UserRole {
	ADMIN("Administrador"), USER("User");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return this.role;
	}
}
