package project.vilsoncake.avgeekadmintelegrambot.service;

import project.vilsoncake.avgeekadmintelegrambot.dto.*;

import java.util.List;

public interface TrafficService {
    WeeklyReportDto getWeeklyReport();
    GithubApiViewsResponse getWeeklyViews();
    GithubApiClonesResponse getWeeklyClones();
    List<ReferrerDto> getWeeklyReferrers();
    int getTodayViews(List<ViewDto> views);
    int getTodayClones(List<CloneDto> clones);
}
