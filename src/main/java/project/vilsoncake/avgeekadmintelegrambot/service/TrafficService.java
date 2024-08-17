package project.vilsoncake.avgeekadmintelegrambot.service;

import project.vilsoncake.avgeekadmintelegrambot.dto.GithubApiClonesResponse;
import project.vilsoncake.avgeekadmintelegrambot.dto.GithubApiViewsResponse;
import project.vilsoncake.avgeekadmintelegrambot.dto.ReferrerDto;
import project.vilsoncake.avgeekadmintelegrambot.dto.WeeklyReportDto;

import java.util.List;

public interface TrafficService {
    WeeklyReportDto getWeeklyReport();
    GithubApiViewsResponse getWeeklyViews();
    GithubApiClonesResponse getWeeklyClones();
    List<ReferrerDto> getWeeklyReferrers();
}
