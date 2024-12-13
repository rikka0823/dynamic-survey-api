package com.example.quiz11.vo;

import java.util.List;

public class StatisticsRes extends BasicRes {

    private List<StatisticsVo> statisticVoList;

    public StatisticsRes() {
    }

    public StatisticsRes(int code, String message) {
        super(code, message);
    }

    public StatisticsRes(int code, String message, List<StatisticsVo> statisticVoList) {
        super(code, message);
        this.statisticVoList = statisticVoList;
    }

    public List<StatisticsVo> getStatisticVoList() {
        return statisticVoList;
    }
}
