package BeanClasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({@NamedNativeQuery(name="selectDepartments", query="SELECT * from Departments dept", resultClass = Departments.class), @NamedNativeQuery(name = "getDepartmentID", query="SELECT * from departments dept where dept.departmentName=?1", resultClass = Departments.class)})
public class Departments {
	@Id
	private int departmentID;
	private String departmentName;
	
	
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
