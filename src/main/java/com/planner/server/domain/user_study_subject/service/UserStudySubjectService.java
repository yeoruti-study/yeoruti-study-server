package com.planner.server.domain.user_study_subject.service;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectReqDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import com.planner.server.domain.user_study_subject.repository.UserStudySubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserStudySubjectService {

    private final UserStudySubjectRepository userStudySubjectRepository;
    private final StudyGoalRepository studyGoalRepository;
    private final UserService userService;

    public void save(UserStudySubjectReqDto.ReqCreateOne req, UUID userId) throws Exception {
        Optional<UserStudySubject> findEntity = userStudySubjectRepository.findByUserAndTitleJoinFetchUser(userId, req.getTitle());
        if(findEntity.isPresent()){
            throw new Exception("이미 존재하는 제목입니다. 다른 제목을 설정해주세요.");
        }
        User user = userService.findOne(userId);

        UserStudySubject userStudySubject = UserStudySubject.builder()
                .id(UUID.randomUUID())
                .title(req.getTitle())
                .user(user)
                .build();

        UserStudySubject save = userStudySubjectRepository.save(userStudySubject);
    }

    public UserStudySubjectResDto.ResSearchList findByUserId(UUID userId) throws Exception {
        List<UserStudySubject> subjectList = new ArrayList<>();
        try{
            subjectList = userStudySubjectRepository.findByUserJoinFetchUser(userId);
        }
        catch (Exception e){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }
        List<UserStudySubjectResDto> userStudySubjectResDtoList = new ArrayList<>();
        subjectList.forEach(s-> userStudySubjectResDtoList.add(UserStudySubjectResDto.toDto(s)));

        UserStudySubjectResDto.ResSearchList searchList = new UserStudySubjectResDto.ResSearchList(userId, userStudySubjectResDtoList);
        return searchList;
    }


    public UserStudySubjectResDto findById(UUID id) throws Exception {
        Optional<UserStudySubject> byId = userStudySubjectRepository.findByIdJoinFetchUser(id);
        if(!byId.isPresent())
            throw new Exception("parameter:[id] is wrong. There is no data for request id");

        UserStudySubject userStudySubject = byId.get();
        return UserStudySubjectResDto.toDto(userStudySubject);
    }

    public void deleteById(UUID id) throws Exception {
        Optional<UserStudySubject> findUserStudySubject = userStudySubjectRepository.findByIdJoinFetchUser(id);
        if(!findUserStudySubject.isPresent()){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }
        UserStudySubject userStudySubject = findUserStudySubject.get();

        List<StudyGoal> studyGoalList = studyGoalRepository.findByUserStudySubject(id);
        studyGoalList.forEach(s->{studyGoalRepository.delete(s);});

        userStudySubjectRepository.delete(userStudySubject);
    }

    public void deleteByUserId(UserStudySubjectReqDto req) throws Exception {
        List<UserStudySubject> subjectList;
        try{
            subjectList = userStudySubjectRepository.findByUserJoinFetchUser(req.getUserId());
        }
        catch (Exception e){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }

        subjectList.forEach(s -> userStudySubjectRepository.delete(s));
    }

}
