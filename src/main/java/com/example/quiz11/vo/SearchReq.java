package com.example.quiz11.vo;

import java.time.LocalDate;

public class SearchReq {

    private String name;

    //	@JsonProperty("start_date")
    private LocalDate startDate;

    private LocalDate endDate;

    public SearchReq() {
    }

    public SearchReq(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
