package dto;

public class ReviewDTO {

	private int rating;
	private String patientEmail;
	private String doctorEmail;
	private String centreName;
	
	
	public ReviewDTO(int rating, String patientEmail, String doctorEmail, String centreName) {
		super();
		this.rating = rating;
		this.patientEmail = patientEmail;
		this.doctorEmail = doctorEmail;
		this.centreName = centreName;
	}

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getDoctorEmail() {
		return doctorEmail;
	}
	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}
	
	
}
