package project.vilsoncake.avgeekadmintelegrambot.repository;

import project.vilsoncake.avgeekadmintelegrambot.entity.UserEntity;

import java.util.UUID;

public interface UserReadOnlyRepository extends ReadOnlyRepository<UserEntity, UUID> {
}
