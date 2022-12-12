package filters;

import dto.DoctorDTO;
import helpers.DateUtil;
import model.Doctor;

public class DoctorFilter implements Filter{

	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		try 
		{
			Boolean flag = true;
			Doctor d = (Doctor)o1;
			DoctorDTO dto = (DoctorDTO)o2;

			if(!dto.getUser().getUsername().equals(""))
			{
				if(!d.getUsername().toLowerCase().contains(dto.getUser().getUsername().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(!dto.getUser().getFirstname().equals(""))
			{
				if(!d.getFirstname().toLowerCase().contains(dto.getUser().getFirstname().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(!dto.getUser().getLastname().equals(""))
			{
				if(!d.getLastname().toLowerCase().contains(dto.getUser().getLastname().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(dto.getAverageRating() != 0)
			{
				if(Math.abs(d.getAverageRating() - dto.getAverageRating()) > 1f)
				{
					flag = false;
				}			
			}
			
			if(!dto.getType().equals(""))
			{
				if(!d.getType().toLowerCase().contains(dto.getType().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(dto.getShiftEnd() != null)
			{
				if(!d.IsFreeOn(DateUtil.getInstance().getDate(dto.getShiftEnd(),"dd-MM-yyyy")))
				{
					flag = false;
				}
			}
			
			return flag;
			
		}
		catch(Exception e) 
		{
			return false;
		}
		
	}

}
