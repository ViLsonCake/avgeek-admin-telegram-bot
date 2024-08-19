package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import project.vilsoncake.avgeekadmintelegrambot.entity.document.UsersInfoDocument;
import project.vilsoncake.avgeekadmintelegrambot.entity.jpa.UserEntity;

import java.util.List;

public interface UsersInfoService {
    List<UsersInfoDocument> findAllUsersInfo();
    List<UserEntity> findAllEntities();
    UserEntity getLastAddedUserEntity();
    UsersInfoDocument getLastAddedUsersInfo();
    boolean addNewDailyUsersInfo();
}
