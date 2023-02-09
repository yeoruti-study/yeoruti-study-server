package com.planner.server.domain.study_goal.service;

import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.study_goal.dto.StudyGoalReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalResDto;
import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import com.planner.server.domain.user_study_subject.service.UserStudySubjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
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
    private final UserStudySubjectService userStudySubjectService;

    Logger logger = LoggerFactory.getLogger(FriendService.class);

    public StudyGoalReqDto save(StudyGoalReqDto req) throws Exception{

        UUID userId = req.getUserId();
        User user = null;

        try{
            Optional<User> byId = userRepository.findById(userId);
            user = byId.get();
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

        StudyGoal savedStudyGoal = studyGoalRepository.save(studyGoal);
        user.addStudyGoal(savedStudyGoal);

        return StudyGoalReqDto.toDto(studyGoal);
    }

    public StudyGoalResDto findById(UUID id) throws Exception {
        StudyGoal studyGoal;
        try{
            Optional<StudyGoal> byId = studyGoalRepository.findById(id);
            studyGoal = byId.get();
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        logger.info("studyGoalService.findById 호출 끝");

        return StudyGoalResDto.toDto(studyGoal);
    }

    public List<StudyGoalResDto> findByUserId(UUID id) throws Exception {
        userService.findById(id); // 유저의 존재 여부 확인
        List<StudyGoal> studyGoals;
        try{
            studyGoals = studyGoalRepository.findByUserId(id);
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }logger.info("findByUserId 끝");

        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        studyGoals.forEach(studyGoal -> studyGoalResDtos.add(StudyGoalResDto.toDto(studyGoal)));

        return studyGoalResDtos;
    }

    public List<StudyGoalResDto> findByUserStudySubjectId(UUID userStudySubjectId) throws Exception {
        try { // 존재유무 확인
            userStudySubjectService.findById(userStudySubjectId);
        } catch (Exception e) {
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        List<StudyGoal> studyGoals;
        try{
            studyGoals = studyGoalRepository.findByUserStudySubjectId(userStudySubjectId);
        }catch (Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        studyGoals.forEach(studyGoal -> studyGoalResDtos.add(StudyGoalResDto.toDto(studyGoal)));

        return studyGoalResDtos;
    }

    public void deleteById(StudyGoalReqDto req){
        UUID id = req.getId();
        Optional<StudyGoal> studyGoal = studyGoalRepository.findById(id);
        if(!studyGoal.isPresent()){
            throw new NoSuchElementException("[id] 확인 요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        studyGoalRepository.delete(studyGoal.get());
    }

}
