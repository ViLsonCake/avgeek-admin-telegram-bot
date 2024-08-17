package project.vilsoncake.avgeekadmintelegrambot.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CloneDto {
    private Date timestamp;
    private int count;
    private int uniques;
}
