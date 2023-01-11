package com.planner.server.domain.study_goal.service;

import com.planner.server.domain.study_goal.dto.ResDto;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyGoalService {

    private final StudyGoalRepository studyGoalRepository;
    private final UserRepository userRepository;

    public StudyGoalDto save(SaveReqDto req){

        String userId = req.getUserId();
        User user = userRepository.findById(UUID.fromString(userId)).get();

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

        return StudyGoalDto.toDto(studyGoal, user.getProfileName());
    }

    public ResDto findById(String sid){
        UUID id = UUID.fromString(sid);
        StudyGoal studyGoal = studyGoalRepository.findById(id).get();

        return ResDto.toDto(studyGoal);
    }

    public void deleteById(String sid) {
        UUID id = UUID.fromString(sid);
        StudyGoal studyGoal = studyGoalRepository.findById(id).get();
        studyGoalRepository.delete(studyGoal);
    }
}
