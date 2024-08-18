package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.avgeekadmintelegrambot.dto.*;
import project.vilsoncake.avgeekadmintelegrambot.property.GithubApiProperties;
import project.vilsoncake.avgeekadmintelegrambot.service.TrafficService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static project.vilsoncake.avgeekadmintelegrambot.constant.ApiUrlConst.*;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final WebClient githubApiClient;
    private final GithubApiProperties githubApiProperties;

    @Override
    public WeeklyReportDto getWeeklyReport() {

        GithubApiViewsResponse views = getWeeklyViews();
        GithubApiClonesResponse clones = getWeeklyClones();
        List<ReferrerDto> referrers = getWeeklyReferrers();

        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        weeklyReportDto.setViews(views.getCount());
        weeklyReportDto.setUniqueViews(views.getUniques());
        weeklyReportDto.setTodayUniqueViews(getTodayViews(views.getViews()));
        weeklyReportDto.setClones(clones.getCount());
        weeklyReportDto.setUniqueCloners(clones.getUniques());
        weeklyReportDto.setTodayUniqueCloners(getTodayClones(clones.getClones()));
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
        Date todayDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(todayDate);

        for (ViewDto view : views) {
            Calendar viewCalendar = Calendar.getInstance();
            viewCalendar.setTime(view.getTimestamp());

            if (todayCalendar.get(Calendar.YEAR) == viewCalendar.get(Calendar.YEAR) &&
                    todayCalendar.get(Calendar.DAY_OF_YEAR) == viewCalendar.get(Calendar.DAY_OF_YEAR)) {
                return view.getUniques();
            }
        }

        return 0;
    }

    @Override
    public int getTodayClones(List<CloneDto> clones) {
        Date todayDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(todayDate);

        for (CloneDto clone : clones) {
            Calendar cloneCalendar = Calendar.getInstance();
            cloneCalendar.setTime(clone.getTimestamp());

            if (todayCalendar.get(Calendar.YEAR) == cloneCalendar.get(Calendar.YEAR) &&
                    todayCalendar.get(Calendar.DAY_OF_YEAR) == cloneCalendar.get(Calendar.DAY_OF_YEAR)) {
                return clone.getUniques();
            }
        }

        return 0;
    }
}
