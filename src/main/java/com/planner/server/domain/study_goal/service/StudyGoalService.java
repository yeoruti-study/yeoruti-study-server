package com.planner.server.domain.study_goal.service;

import com.planner.server.domain.study_goal.dto.StudyGoalReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalResDto;
import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudyGoalService {

    private final StudyGoalRepository studyGoalRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public StudyGoalReqDto save(StudyGoalReqDto req) throws Exception{
        User user = null;
        UUID userId = SecurityContextHolderUtils.getUserId();
        try{
            Optional<User> findUser = userRepository.findById(userId);
            user = findUser.get();
        }
        catch (Exception e){
            throw new Exception("[userId] 확인요망. id 값과 일치하는 유저 데이터가 존재하지 않습니다.");
        }
        // goalTime 형식 = "PT8H30M" // PT(시간)H(분)M
        Duration goalTime = req.getGoalTime();

        LocalDateTime startDateTime = req.getStartDate();
        LocalDateTime endDateTime = req.getEndDate();

        StudyGoal studyGoal = StudyGoal.builder()
                .id(UUID.randomUUID())
                .goalTitle(req.getTitle())
                .goalDetail(req.getDetail())
                .goalTime(goalTime)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .user(user)
                .userStudySubjectId(req.getUserStudySubjectId())
                .build();

        studyGoalRepository.save(studyGoal);

        return StudyGoalReqDto.toDto(studyGoal);
    }

    public StudyGoalResDto findById(UUID id) throws Exception {
        StudyGoal studyGoal = null;
        try{
            Optional<StudyGoal> byId = studyGoalRepository.findByIdJoinFetchUser(id);
            studyGoal = byId.get();
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        return StudyGoalResDto.toDto(studyGoal);
    }

    public List<StudyGoalResDto> findByUserId() throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        userService.findOne(userId); // 유저의 존재 여부 확인
        List<StudyGoal> studyGoals = new ArrayList<>();
        try{
            studyGoals = studyGoalRepository.findByUserJoinFetchUser(userId);
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        studyGoals.forEach(studyGoal -> studyGoalResDtos.add(StudyGoalResDto.toDto(studyGoal)));

        return studyGoalResDtos;
    }

    public List<StudyGoalResDto> findByUserStudySubjectId(UUID userStudySubjectId) throws Exception {
        List<StudyGoal> studyGoals = new ArrayList<>();
        try{
            studyGoals = studyGoalRepository.findByUserStudySubject(userStudySubjectId);
        }catch (Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        studyGoals.forEach(studyGoal -> studyGoalResDtos.add(StudyGoalResDto.toDto(studyGoal)));

        return studyGoalResDtos;
    }

    public void deleteById(UUID id){
        Optional<StudyGoal> studyGoal = studyGoalRepository.findByIdJoinFetchUser(id);
        if(!studyGoal.isPresent()){
            throw new NoSuchElementException("[id] 확인 요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        studyGoalRepository.delete(studyGoal.get());
    }

}
