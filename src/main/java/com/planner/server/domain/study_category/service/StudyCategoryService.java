package com.planner.server.domain.study_category.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.study_category.dto.StudyCategoryReqDto;
import com.planner.server.domain.study_category.dto.StudyCategoryResDto;
import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_category.repository.StudyCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyCategoryService {
    private final StudyCategoryRepository studyCategoryRepository;

    public void createOne(StudyCategoryReqDto studyCategoryDto) {
        StudyCategory studyCategory = StudyCategory.builder()
            .id(UUID.randomUUID())
            .name(studyCategoryDto.getName())
            .description(studyCategoryDto.getDescription())
            .build();

        studyCategoryRepository.save(studyCategory);
    }
    
    @Transactional(readOnly = true)
    public List<StudyCategoryResDto> searchAll() {
        List<StudyCategory> studyCategories = studyCategoryRepository.findAll();
        List<StudyCategoryResDto> studyCategoryDtos = studyCategories.stream().map(entity -> StudyCategoryResDto.toDto(entity)).collect(Collectors.toList());
        return studyCategoryDtos;
    }

    public void updateOne(StudyCategoryReqDto studyCategoryDto) {
        Optional<StudyCategory> entityOpt = studyCategoryRepository.findById(studyCategoryDto.getId());

        if(entityOpt.isPresent()) {
            StudyCategory entity = entityOpt.get();

            entity.setName(studyCategoryDto.getName()).setDescription(studyCategoryDto.getDescription());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    
    public void deleteOne(UUID studyCategoryId) {
        // TODO :: 이미 스터디룸에 부여된 스터디카테고리를 제거하려고 할 때 예외처리
        Optional<StudyCategory> entityOpt = studyCategoryRepository.findById(studyCategoryId);

        if(entityOpt.isPresent()) {
            studyCategoryRepository.delete(entityOpt.get());
        } else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
