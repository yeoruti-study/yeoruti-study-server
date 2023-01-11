package com.planner.server.domain.study_goal.controller;

import com.planner.server.domain.study_goal.dto.ResDto;
import com.planner.server.domain.study_goal.dto.SaveReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalDto;
import com.planner.server.domain.study_goal.service.StudyGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study_goal")
public class StudyGoalController {

    private final StudyGoalService studyGoalService;

    @PostMapping("")
    public StudyGoalDto save(@RequestBody SaveReqDto req){
        return studyGoalService.save(req);
    }

    @GetMapping("")
    public ResDto getbyId(@RequestParam String id){
        return studyGoalService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        studyGoalService.deleteById(id);
        return "SUCCESS";
    }

}
