package project.vilsoncake.avgeekadmintelegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.DailyTrafficDocument;

import java.util.List;
import java.util.UUID;

public interface DailyTrafficRepository extends MongoRepository<DailyTrafficDocument, UUID> {
    List<DailyTrafficDocument> findAllByOrderByDateDesc();
}
