package project.vilsoncake.avgeekadmintelegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.avgeekadmintelegrambot.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
}
