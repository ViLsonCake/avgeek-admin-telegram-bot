package project.vilsoncake.avgeekadmintelegrambot.dto;

import lombok.Data;

import java.util.List;

@Data
public class GithubApiClonesResponse {
    private int count;
    private int uniques;
    private List<CloneDto> clones;
}
