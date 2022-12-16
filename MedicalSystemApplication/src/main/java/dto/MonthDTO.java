package dto;

import helpers.DateUtil;

import java.util.Date;

public class MonthDTO {
    private String monthStart;
    private String monthEnd;

    public MonthDTO(Date Start, Date End) {
        this.monthStart = DateUtil.getInstance().getString(Start,"dd-MM-yyyy HH:mm");
        this.monthEnd = DateUtil.getInstance().getString(End,"dd-MM-yyyy HH:mm");
    }

    public String getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(String monthStart) {
        this.monthStart = monthStart;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
    }
}
