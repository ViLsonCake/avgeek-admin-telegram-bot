package project.vilsoncake.avgeekadmintelegrambot.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeeklyReportDto {
    private int views;
    private int uniqueViews;
    private int clones;
    private int uniqueCloners;
    private List<ReferrerDto> referrers;
}
