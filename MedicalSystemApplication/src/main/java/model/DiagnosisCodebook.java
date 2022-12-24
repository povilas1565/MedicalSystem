package model;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class DiagnosisCodebook 
{
	 private Long id;  
	 private List<String> code;
	 
	 
	public DiagnosisCodebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiagnosisCodebook(Long id, String code) {
		super();
		this.id = id;
		this.code = new ArrayList<String>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getCode() {
		return code;
	}
	public void setCode(List<String> code) {
		this.code = code;
	}
	
	 
	 
}
