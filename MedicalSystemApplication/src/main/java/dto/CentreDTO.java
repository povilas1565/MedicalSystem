package dto;

import model.Centre;

public class CentreDTO {

    private String name;
    private String address;
    private String city;
    private String state;
    private String description;
    private float rating;

    public CentreDTO()
    {
    	super();
    }
    
    public CentreDTO(String name, String address, String city, String state, String description, float rating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.description = description;
        this.rating = rating;
    }
    
    public CentreDTO(Centre centre)
    {
    	this.name = centre.getName();
        this.address = centre.getAddress();
        this.city = centre.getCity();
        this.state = centre.getState();
        this.description = centre.getDescription();
        
        this.rating = centre.calculateRating();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
    
    
}
