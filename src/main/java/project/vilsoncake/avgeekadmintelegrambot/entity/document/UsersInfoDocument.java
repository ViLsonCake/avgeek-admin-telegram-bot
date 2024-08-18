package project.vilsoncake.avgeekadmintelegrambot.entity.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document
public class UsersInfoDocument {

    @Id
    private UUID id;
    private Date date;
    private int usersCount;
}
