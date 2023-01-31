package com.planner.server.domain.study_goal.service;

import com.planner.server.domain.study_goal.dto.StudyGoalReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalResDto;
import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
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

    public StudyGoalReqDto save(StudyGoalReqDto req) throws Exception{

        UUID userId = req.getUserId();
        User user = null;

        try{
            user = userRepository.findById(userId).get();
        }
        catch (Exception e){
            throw new Exception("[userId] 확인요망. id 값과 일치하는 유저 데이터가 존재하지 않습니다.");
        }

        // goalTime 형식 = "PT8H30M" // PT(시간)H(분)M
        Duration goalTime = req.getGoalTime();

        // startDate, endDate 형식 = "2021-11-05 13:47" // 년도-월-일 시:분
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime startDateTime = LocalDateTime.parse(req.getStartDate(), formatter);
//        LocalDateTime endDateTime = LocalDateTime.parse(req.getEndDate(), formatter);

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
                .build();

        StudyGoal savedStudyGoal = studyGoalRepository.save(studyGoal);
        return StudyGoalReqDto.toDto(studyGoal);
    }

    public StudyGoalResDto findById(UUID id) throws Exception {
        StudyGoal studyGoal;
        try{
            studyGoal = studyGoalRepository.findById(id).get();
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        return StudyGoalResDto.toDto(studyGoal);
    }

    public List<StudyGoalResDto> findByUserId(UUID id) throws Exception {
        userService.findById(id);
        List<StudyGoal> studyGoals;
        try{
            studyGoals = studyGoalRepository.findByUserId(id);
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        studyGoals.forEach(studyGoal -> studyGoalResDtos.add(StudyGoalResDto.toDto(studyGoal)));

        return studyGoalResDtos;
    }

    public void deleteById(StudyGoalReqDto req){
        UUID id = req.getId();
        Optional<StudyGoal> studyGoal;

        studyGoal = studyGoalRepository.findById(id);
        if(!studyGoal.isPresent()){
            throw new NoSuchElementException("[id] 확인 요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        studyGoalRepository.delete(studyGoal.get());
    }
}
