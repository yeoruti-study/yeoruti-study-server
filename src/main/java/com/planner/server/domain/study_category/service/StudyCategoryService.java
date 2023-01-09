package com.planner.server.domain.study_category.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.study_category.dto.StudyCategoryDto;
import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_category.repository.StudyCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyCategoryService {
    private final StudyCategoryRepository studyCategoryRepository;

    public void createOne(StudyCategoryDto studyCategoryDto) {
        StudyCategory studyCategory = StudyCategory.builder()
            .id(UUID.randomUUID())
            .name(studyCategoryDto.getName())
            .description(studyCategoryDto.getDescription())
            .build();

        studyCategoryRepository.save(studyCategory);
    }
    
    @Transactional(readOnly = true)
    public List<StudyCategoryDto> searchAll() {
        List<StudyCategory> studyCategories = studyCategoryRepository.findAll();
        List<StudyCategoryDto> studyCategoryDtos = studyCategories.stream().map(entity -> {
            StudyCategoryDto dto = StudyCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();

            return dto;
        }).collect(Collectors.toList());
    
        return studyCategoryDtos;
    }

    public void updateOne(StudyCategoryDto studyCategoryDto) {
        Optional<StudyCategory> entityOpt = studyCategoryRepository.findById(studyCategoryDto.getId());

        if(entityOpt.isPresent()) {
            StudyCategory entity = entityOpt.get();

            entity.setName(studyCategoryDto.getName())
                .setDescription(studyCategoryDto.getDescription());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    public void deleteOne(UUID studyCategoryId) {
        Optional<StudyCategory> entityOpt = studyCategoryRepository.findById(studyCategoryId);

        if(entityOpt.isPresent()) {
            studyCategoryRepository.delete(entityOpt.get());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
