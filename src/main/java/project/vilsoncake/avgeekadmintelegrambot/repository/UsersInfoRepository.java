package project.vilsoncake.avgeekadmintelegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.UsersInfoDocument;

import java.util.UUID;

public interface UsersInfoRepository extends MongoRepository<UsersInfoDocument, UUID> {
}
