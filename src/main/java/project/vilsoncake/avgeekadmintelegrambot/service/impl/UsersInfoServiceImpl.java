package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.UsersInfoDocument;
import project.vilsoncake.avgeekadmintelegrambot.entity.jpa.UserEntity;
import project.vilsoncake.avgeekadmintelegrambot.repository.UserReadOnlyRepository;
import project.vilsoncake.avgeekadmintelegrambot.repository.UsersInfoRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersInfoServiceImpl implements UsersInfoService {

    private final UserReadOnlyRepository userReadOnlyRepository;
    private final UsersInfoRepository usersInfoRepository;

    @Override
    public List<UsersInfoDocument> findAllUsersInfo() {
        return usersInfoRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<UserEntity> findAllEntities() {
        return userReadOnlyRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public UserEntity getLastAddedUserEntity() {
        return userReadOnlyRepository.findFirstByOrderByCreatedAtDesc();
    }

    @Override
    public UsersInfoDocument getLastAddedUsersInfo() {
        return usersInfoRepository.findFirstByOrderByDateDesc();
    }

    @Override
    public boolean addNewDailyUsersInfo() {
        UsersInfoDocument usersInfo = new UsersInfoDocument();
        usersInfo.setId(UUID.randomUUID());
        usersInfo.setDate(new Date());
        usersInfo.setUsersCount(findAllEntities().size());

        usersInfoRepository.save(usersInfo);

        return true;
    }
}
