package controller;

import dto.HallDTO;
import filters.FilterFactory;
import filters.HallFilter;
import helpers.DateUtil;
import model.Appointment;
import model.Centre;
import model.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AppointmentService;
import service.CentreService;
import service.HallService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "api/hall")
@CrossOrigin
public class HallController {

    @Autowired
    private HallService hallService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CentreService centreService;

    @GetMapping(value = "/getHallBusyDays/{centreName}/{hallNumber}")
    public ResponseEntity<List<Date>> getHallBusyFromHallAndCentre(@PathVariable("centreName") String centreName, @PathVariable ("hallNumber") int hallNumber)
    {
        Centre c = centreService.findByName(centreName);
        List<Date> busyHall = new ArrayList<Date>();

        if (c == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Hall h = hallService.findByNumberAndCentre(hallNumber,c);
        if (h == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Appointment> app = appointmentService.findAllByHallAndCentre(h, c);
        if (app == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Appointment a : app)
        {
            busyHall.add(a.getDate());
        }
        return new ResponseEntity<>(busyHall,HttpStatus.OK);

    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<HallDTO>> getHalls()
    {
        List<Hall> halls = hallService.findAll();
        List<HallDTO> ret = new ArrayList<HallDTO>();
        if(halls == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Hall hall : halls)
        {
            if (!hall.getDeleted())
            {
                HallDTO dto = new HallDTO(hall);
                ret.add(dto);
            }
        }

        return new ResponseEntity<>(ret,HttpStatus.OK);
    }


    @PostMapping(value = "/getAllByFilter",consumes = "application/json")
    public ResponseEntity<HallDTO[]> getHallsFilter(@RequestBody HallDTO dto)
    {

        HttpHeaders header = new HttpHeaders();
        Centre c = centreService.findByName(dto.getCentreName());

        if (c == null)
        {
            header.set("responseText", "centre");
            return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
        }

        List<HallDTO> ret = new ArrayList<HallDTO>();
        HallFilter filter = (HallFilter) FilterFactory.getInstance().get("hall");

        for (Hall hall : c.getHalls())
        {
            if (dto.getDate() != null && dto.getDate() != "")
            {
                Date startDate = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy");
                long hours = 0;
                List<Appointment> appointments = appointmentService.findAllByHall(hall);

                for (Appointment app : appointments)
                {
                    if (DateUtil.getInstance().isSameDay(app.getDate(), startDate))
                    {
                        hours += DateUtil.getInstance().getTimeBetween(app.getDate(), app.getEndDate());
                    }
                }

                System.out.println(hall.getNumber() + " : " +hours);
                if (hours > 23 * DateUtil.HOUR_MILLIS)
                {
                    continue;
                }
            }

            if (!hall.getDeleted())
            {
                if (filter.test(hall, dto))
                {
                    ret.add(new HallDTO(hall));
                }
            }
        }

        HallDTO[] retArray = ret.toArray(new HallDTO[ret.size()]);

        return new ResponseEntity<>(retArray ,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllByCentre/{centreName}")
    public ResponseEntity<List<HallDTO>> getHalls(@PathVariable("centreName") String centreName)
    {
        HttpHeaders header = new HttpHeaders();
        Centre c = centreService.findByName(centreName);

        if (c == null)
        {
            header.set("responseText", "centre");
            return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
        }

        List<HallDTO> ret = new ArrayList<HallDTO>();
        for (Hall hall : c.getHalls())
        {
            if (!hall.getDeleted())
            {
                HallDTO dto = new HallDTO(hall);
                ret.add(dto);
            }
        }

        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    @DeleteMapping(value="/deleteHall/{number}/{centreName}")
    public ResponseEntity<Void> deleteHall(@PathVariable ("number") int number, @PathVariable("centreName") String centreName)
    {
        Centre centre = centreService.findByName(centreName);

        if (centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Hall hall = hallService.findByNumberAndCentre(number, centre);

        if (hall == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Appointment> list = appointmentService.findAllByHall(hall);

        if (list != null)
        {
            if(list.size() > 0)
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        hall.setDeleted(true);
        hall.setNumber(-1);
        hall.setName("");
        hallService.save(hall);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping(value="/changeHall/{oldNumber}/{newNumber}/{newName}/{centreName}")
    public ResponseEntity<Void> changeHall(@PathVariable("oldNumber") int oldNumber, @PathVariable("newNumber") int newNumber, @PathVariable("newName") String newName, @PathVariable("centreName") String centreName)
    {
        Centre centre = centreService.findByName(centreName);

        if (centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        Hall hall = hallService.findByNumberAndCentre(oldNumber, centre);

        if (hall == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Appointment> apps = appointmentService.findAllByHall(hall);

        if (apps != null)
        {
            if (apps.size() > 0)
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }


        hall.setNumber(newNumber);
        hall.setName(newName);
        hallService.save(hall);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/get/{number}/{centreName}")
    public ResponseEntity<HallDTO> getHallByNumber(@PathVariable("number") int number, @PathVariable("centreName") String centreName)
    {
        Centre centre = centreService.findByName(centreName);

        if (centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        Hall hall= hallService.findByNumberAndCentre(number, centre);
        if (hall == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (hall.getDeleted())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new HallDTO(hall),HttpStatus.OK);
    }


    @PostMapping(value ="/addHall", consumes = "application/json")
    public ResponseEntity<Void> add(@RequestBody HallDTO hall)
    {
        HttpHeaders header = new HttpHeaders();

        Centre centre = centreService.findByName(hall.getCentreName());

        if (centre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Hall h = hallService.findByNumberAndCentre(hall.getNumber(), centre);

        if (h == null) {
            Hall newHall = new Hall(centre,hall.getNumber(),hall.getName());
            hallService.save(newHall);
            centre.getHalls().add(newHall);
            centreService.save(centre);

        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
