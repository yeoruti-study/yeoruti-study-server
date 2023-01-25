package com.planner.server.domain.study_goal.service;

import com.planner.server.domain.study_goal.dto.DeleteReqDto;
import com.planner.server.domain.study_goal.dto.SaveReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalDto;
import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.study_goal.repository.StudyGoalRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyGoalService {

    private final StudyGoalRepository studyGoalRepository;
    private final UserRepository userRepository;

    public StudyGoalDto save(SaveReqDto req) throws Exception{

        UUID userId = req.getUserId();
        User user = null;

        try{
            user = userRepository.findById(userId).get();
        }
        catch (Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        // goalTime 형식 = "PT8H30M" // PT(시간)H(분)M
        Duration goalTime = Duration.parse(req.getGoalTime());

        // startDate, endDate 형식 = "2021-11-05 13:47" // 년도-월-일 시:분
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(req.getStartDate(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(req.getEndDate(), formatter);

        UUID id = UUID.randomUUID();

        StudyGoal studyGoal = StudyGoal.builder()
                .id(id)
                .goalTitle(req.getTitle())
                .goalDetail(req.getDetail())
                .goalTime(goalTime)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .user(user)
                .build();

        StudyGoal savedStudyGoal = studyGoalRepository.save(studyGoal);

        return StudyGoalDto.toDto(studyGoal);
    }

    public StudyGoalDto findById(UUID id) throws Exception {
        StudyGoal studyGoal;
        try{
            studyGoal = studyGoalRepository.findById(id).get();
        }catch(Exception e){
            throw new Exception("[id] 확인요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }

        return StudyGoalDto.toDto(studyGoal);
    }

    public void deleteById(DeleteReqDto req){
        UUID id = req.getId();
        Optional<StudyGoal> studyGoal;

        studyGoal = studyGoalRepository.findById(id);
        if(!studyGoal.isPresent()){
            throw new NoSuchElementException("[id] 확인 요망. id 값과 일치하는 데이터가 존재하지 않습니다.");
        }
        studyGoalRepository.delete(studyGoal.get());
    }
}
