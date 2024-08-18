package project.vilsoncake.avgeekadmintelegrambot.service;

import project.vilsoncake.avgeekadmintelegrambot.dto.*;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.DailyTrafficDocument;

import java.util.List;

public interface TrafficService {
    WeeklyReportDto getWeeklyReport();
    GithubApiViewsResponse getWeeklyViews();
    GithubApiClonesResponse getWeeklyClones();
    List<ReferrerDto> getWeeklyReferrers();
    int getTodayViews(List<ViewDto> views);
    int getTodayUniqueVisitors(List<ViewDto> views);
    int getTodayClones(List<CloneDto> clones);
    int getTodayUniqueCloners(List<CloneDto> clones);
    List<DailyTrafficDocument> getAllTrafficDocuments();
    boolean addNewDailyTraffic();
    boolean changeViewsCount(DailyTrafficDocument dailyTrafficDocument, int count);
    boolean changeUniqueVisitorsCount(DailyTrafficDocument dailyTrafficDocument, int count);

}
