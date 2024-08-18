package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.avgeekadmintelegrambot.dto.*;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.DailyTrafficDocument;
import project.vilsoncake.avgeekadmintelegrambot.property.GithubApiProperties;
import project.vilsoncake.avgeekadmintelegrambot.repository.DailyTrafficRepository;
import project.vilsoncake.avgeekadmintelegrambot.service.TrafficService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static project.vilsoncake.avgeekadmintelegrambot.constant.ApiUrlConst.*;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final WebClient githubApiClient;
    private final DailyTrafficRepository dailyTrafficRepository;
    private final GithubApiProperties githubApiProperties;

    @Override
    public WeeklyReportDto getWeeklyReport() {

        GithubApiViewsResponse views = getWeeklyViews();
        GithubApiClonesResponse clones = getWeeklyClones();
        List<ReferrerDto> referrers = getWeeklyReferrers();

        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        weeklyReportDto.setViews(views.getCount());
        weeklyReportDto.setUniqueVisitors(views.getUniques());
        weeklyReportDto.setTodayUniqueViews(getTodayUniqueVisitors(views.getViews()));
        weeklyReportDto.setClones(clones.getCount());
        weeklyReportDto.setUniqueCloners(clones.getUniques());
        weeklyReportDto.setTodayUniqueCloners(getTodayUniqueCloners(clones.getClones()));
        weeklyReportDto.setReferrers(referrers);

        return weeklyReportDto;
    }

    @Override
    public GithubApiViewsResponse getWeeklyViews() {
        return githubApiClient.get()
                .uri(String.format(VIEWS_URL, githubApiProperties.getUsername(), githubApiProperties.getRepositoryName()))
                .retrieve()
                .bodyToMono(GithubApiViewsResponse.class)
                .block();
    }

    @Override
    public GithubApiClonesResponse getWeeklyClones() {
        return githubApiClient.get()
                .uri(String.format(CLONES_URL, githubApiProperties.getUsername(), githubApiProperties.getRepositoryName()))
                .retrieve()
                .bodyToMono(GithubApiClonesResponse.class)
                .block();
    }

    @Override
    public List<ReferrerDto> getWeeklyReferrers() {
        ParameterizedTypeReference<List<ReferrerDto>> typeReference = new ParameterizedTypeReference<>() {};

        return githubApiClient.get()
                .uri(String.format(REFERRERS_URL, githubApiProperties.getUsername(), githubApiProperties.getRepositoryName()))
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public int getTodayViews(List<ViewDto> views) {
        for (ViewDto view : views) {
            if (DateUtils.isSameDay(view.getTimestamp(), new Date())) {
                return view.getCount();
            }
        }

        return 0;
    }

    @Override
    public int getTodayUniqueVisitors(List<ViewDto> views) {
        for (ViewDto view : views) {
            if (DateUtils.isSameDay(view.getTimestamp(), new Date())) {
                return view.getUniques();
            }
        }

        return 0;
    }

    @Override
    public int getTodayClones(List<CloneDto> clones) {
        for (CloneDto clone : clones) {
            if (DateUtils.isSameDay(clone.getTimestamp(), new Date())) {
                return clone.getCount();
            }
        }

        return 0;
    }

    @Override
    public int getTodayUniqueCloners(List<CloneDto> clones) {
        for (CloneDto clone : clones) {
            if (DateUtils.isSameDay(clone.getTimestamp(), new Date())) {
                return clone.getUniques();
            }
        }

        return 0;
    }

    @Override
    public List<DailyTrafficDocument> getAllTrafficDocuments() {
        return dailyTrafficRepository.findAllByOrderByDateDesc();
    }

    @Override
    public boolean addNewDailyTraffic() {

        GithubApiViewsResponse weeklyViews = getWeeklyViews();
        GithubApiClonesResponse weeklyClones = getWeeklyClones();

        int views = getTodayViews(weeklyViews.getViews());
        int uniqueVisitors = getTodayUniqueVisitors(weeklyViews.getViews());

        int clones = getTodayClones(weeklyClones.getClones());
        int uniqueCloners = getTodayUniqueCloners(weeklyClones.getClones());

        DailyTrafficDocument dailyTraffic = new DailyTrafficDocument();
        dailyTraffic.setId(UUID.randomUUID());
        dailyTraffic.setDate(new Date());
        dailyTraffic.setViews(views);
        dailyTraffic.setUniqueVisitors(uniqueVisitors);
        dailyTraffic.setClones(clones);
        dailyTraffic.setUniqueCloners(uniqueCloners);

        dailyTrafficRepository.save(dailyTraffic);

        return true;
    }

    @Override
    public boolean changeViewsCount(DailyTrafficDocument dailyTrafficDocument, int count) {
        dailyTrafficDocument.setViews(count);
        dailyTrafficRepository.save(dailyTrafficDocument);

        return true;
    }

    @Override
    public boolean changeUniqueVisitorsCount(DailyTrafficDocument dailyTrafficDocument, int count) {
        dailyTrafficDocument.setUniqueVisitors(count);
        dailyTrafficRepository.save(dailyTrafficDocument);

        return true;
    }
}
