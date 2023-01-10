package com.planner.server.domain.study_room.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_category.repository.StudyCategoryRepository;
import com.planner.server.domain.study_room.dto.StudyRoomDto;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.study_room.repository.StudyRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyCategoryRepository studyCategoryRepository;

    // TODO :: masterUserId
    public void createOne(StudyRoomDto studyRoomDto) {
        UUID studyCategoryId = studyRoomDto.getStudyCategory().getId();

        // 1. study category 조회
        Optional<StudyCategory> studyCategoryOpt = studyCategoryRepository.findById(studyCategoryId);

        if(studyCategoryOpt.isPresent()) {
            // 2. study room 생성
            StudyRoom studyRoom = StudyRoom.builder()
                .id(UUID.randomUUID())
                .name(studyRoomDto.getName())
                .maximumNumberOfPeople(studyRoomDto.getMaximumNumberOfPeople())
                .studyCategory(studyCategoryOpt.get())
                .studyGoalTime(studyRoomDto.getStudyGoalTime())
                .roomPassword(studyRoomDto.getRoomPassword())
                .createdAt(LocalDateTime.now())
                .masterUserId(studyRoomDto.getMasterUserId())
                .build();

            studyRoomRepository.save(studyRoom);
        } else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    @Transactional(readOnly = true)
    public List<StudyRoomDto> searchAll() {
        List<StudyRoom> studyRooms = studyRoomRepository.findAll();
        List<StudyRoomDto> studyRoomDtos = studyRooms.stream().map(entity -> {
            StudyRoomDto dto = StudyRoomDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .maximumNumberOfPeople(entity.getMaximumNumberOfPeople())
                .studyCategory(entity.getStudyCategory())
                .studyGoalTime(entity.getStudyGoalTime())
                .roomPassword(entity.getRoomPassword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .masterUserId(entity.getMasterUserId())
                // .roomUsers(entity.getRoomUsers())
                // .roomChats(entity.getRoomChats())
                .build();

            return dto;
        }).collect(Collectors.toList());
    
        return studyRoomDtos;
    }

    public void updateOne(StudyRoomDto studyRoomDto) {
        Optional<StudyRoom> entityOpt = studyRoomRepository.findById(studyRoomDto.getId());

        if(entityOpt.isPresent()) {
            StudyRoom entity = entityOpt.get();

            entity.setName(studyRoomDto.getName())
                .setMaximumNumberOfPeople(studyRoomDto.getMaximumNumberOfPeople())
                .setStudyGoalTime(studyRoomDto.getStudyGoalTime())
                .setRoomPassword(studyRoomDto.getRoomPassword())
                .setMasterUserId(studyRoomDto.getMasterUserId())
                .setUpdatedAt(LocalDateTime.now());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    public void deleteOne(UUID studyRoomId) {
        Optional<StudyRoom> entityOpt = studyRoomRepository.findById(studyRoomId);

        if(entityOpt.isPresent()) {
            studyRoomRepository.delete(entityOpt.get());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
