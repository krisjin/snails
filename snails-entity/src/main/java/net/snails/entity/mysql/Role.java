package net.snails.entity.mysql;

/**
 * @author krisjin
 * @date 2014-10-31
 */
public class Role {
	
	private int id;
	private String roleName;
	private String roleDesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
