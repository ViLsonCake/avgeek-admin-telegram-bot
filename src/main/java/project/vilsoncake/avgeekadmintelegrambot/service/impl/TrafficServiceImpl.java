package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.avgeekadmintelegrambot.dto.GithubApiClonesResponse;
import project.vilsoncake.avgeekadmintelegrambot.dto.GithubApiViewsResponse;
import project.vilsoncake.avgeekadmintelegrambot.dto.ReferrerDto;
import project.vilsoncake.avgeekadmintelegrambot.dto.WeeklyReportDto;
import project.vilsoncake.avgeekadmintelegrambot.property.GithubApiProperties;
import project.vilsoncake.avgeekadmintelegrambot.service.TrafficService;

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
        weeklyReportDto.setClones(clones.getCount());
        weeklyReportDto.setUniqueCloners(clones.getUniques());
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
}
