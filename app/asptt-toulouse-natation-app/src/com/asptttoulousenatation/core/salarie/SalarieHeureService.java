package com.asptttoulousenatation.core.salarie;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.asptttoulousenatation.core.server.dao.entity.salarie.SalarieHeureEntity;


@Path("/salarie/heure")
@Produces("application/json")
public class SalarieHeureService {

	@GET
	public List<SalarieHeureDay> loadWeek() {
		List<SalarieHeureDay> days = new ArrayList<>();
		LocalDate begin = LocalDate.now().withDayOfWeek(DateTimeConstants.MONDAY);
		for(int i = 0; i < 7; i++) {
			SalarieHeureDay day = new SalarieHeureDay();
			day.setDay(begin.plusDays(i).toDate());
			day.addHeure(new SalarieHeureEntity());
			day.addHeure(new SalarieHeureEntity());
			day.addHeure(new SalarieHeureEntity());
			days.add(day);
		}
		return days;
	}
}
