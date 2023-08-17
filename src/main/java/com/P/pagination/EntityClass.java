package com.P.pagination;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Pagination")
public class EntityClass {
	@Id
	@GeneratedValue
	private int id;
	private String task_name;
	private String task_description;
	private String task_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTask_description() {
		return task_description;
	}

	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}

	public String getTask_status() {
		return task_status;
	}

	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}

	public EntityClass(int id, String task_name, String task_description, String task_status) {
		super();
		this.id = id;
		this.task_name = task_name;
		this.task_description = task_description;
		this.task_status = task_status;
	}

	public EntityClass() {
		super();
		// TODO Auto-generated constructor stub
	}

}

