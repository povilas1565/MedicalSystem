package filters;

import dto.UserDTO;
import model.Patient;

public class PatientFilter implements Filter{

	
	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		try {
			Patient p = (Patient)o1;
			UserDTO d = (UserDTO)o2;
			Boolean flag = true;

			if(!d.getUsername().equals(""))
			{
				if(!p.getUsername().toLowerCase().contains(d.getUsername().toLowerCase()))
				{
					flag = false;
				}
			}
			if(!d.getFirstname().equals(""))
			{
				if(!p.getFirstname().toLowerCase().contains(d.getFirstname().toLowerCase()))
				{
					flag = false;
				}
			}
			if(!d.getLastname().equals(""))
			{
				if(!p.getLastname().toLowerCase().contains(d.getLastname().toLowerCase()))
				{
					flag = false;
				}				
			}
			
			return flag;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
