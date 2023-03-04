package com.planner.server.domain.user_study_subject.service;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectReqDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import com.planner.server.domain.user_study_subject.repository.UserStudySubjectRepository;
import com.planner.server.utils.SecurityContextHolderUtils;
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

    public void save(UserStudySubjectReqDto.ReqCreateOne req) throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        User user = userService.findOne(userId);

        UserStudySubject userStudySubject = UserStudySubject.builder()
                .id(UUID.randomUUID())
                .title(req.getTitle())
                .user(user)
                .build();

        userStudySubjectRepository.save(userStudySubject);
    }

    public List<UserStudySubjectResDto> findByUser() throws Exception {
        List<UserStudySubject> subjectList = new ArrayList<>();
        UUID userId = SecurityContextHolderUtils.getUserId();

        try{
            subjectList = userStudySubjectRepository.findByUserJoinFetchUser(userId);
        }
        catch (Exception e){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }
        List<UserStudySubjectResDto> userStudySubjectResDtoList = new ArrayList<>();
        subjectList.forEach(s-> userStudySubjectResDtoList.add(UserStudySubjectResDto.toDto(s)));

        return userStudySubjectResDtoList;
    }


    public UserStudySubjectResDto findById(UUID id) throws Exception {
        Optional<UserStudySubject> findUserStudySubject = userStudySubjectRepository.findByIdJoinFetchUser(id);
        if(!findUserStudySubject.isPresent())
            throw new Exception("parameter:[id] is wrong. There is no data for request id");

        UserStudySubject userStudySubject = findUserStudySubject.get();
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

}
