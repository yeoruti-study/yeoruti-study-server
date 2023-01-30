package com.planner.server.domain.study_room.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.room_chat.dto.RoomChatRes;
import com.planner.server.domain.room_chat.entity.RoomChat;
import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_category.repository.StudyCategoryRepository;
import com.planner.server.domain.study_room.dto.StudyRoomReqDto;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.study_room.repository.StudyRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyCategoryRepository studyCategoryRepository;

    // TODO :: masterUserId - userService : getUserId 사용하기
    public void createOne(StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        UUID studyCategoryId = studyRoomDto.getStudyCategoryDto().getId();

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
    public List<StudyRoomResDto> searchAll() {
        List<StudyRoom> studyRooms = studyRoomRepository.findAll();
        List<StudyRoomResDto> studyRoomDtos = studyRooms.stream().map(entity -> StudyRoomResDto.toDto(entity)).collect(Collectors.toList());
    
        return studyRoomDtos;
    }

    public void updateOne(StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        Optional<StudyRoom> entityOpt = studyRoomRepository.findById(studyRoomDto.getId());
        Optional<StudyCategory> studyCategoryOpt = null;
        
        if(entityOpt.isPresent()) {
            UUID updatedStudyCategoryId = studyRoomDto.getStudyCategoryDto().getId();
            if(!entityOpt.get().getStudyCategory().getId().equals(updatedStudyCategoryId)){
                studyCategoryOpt = studyCategoryRepository.findById(updatedStudyCategoryId);

                if(studyCategoryOpt.isPresent()) {
                    throw new NullPointerException("존재하지 않는 데이터");
                }
            }

            StudyRoom entity = entityOpt.get();
            entity.setName(studyRoomDto.getName())
                .setStudyCategory(studyCategoryOpt.get())
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

    // TODO :: room chat 조회 확인
    public List<RoomChatRes> searchStudyRoomChat(UUID studyRoomId) {
        Optional<StudyRoom> entityOpt = studyRoomRepository.findById(studyRoomId);

        System.out.println(entityOpt);

        if(entityOpt.isPresent()) {
            StudyRoom entity = entityOpt.get();

            List<RoomChat> roomChats = entity.getRoomChats();
            List<RoomChatRes> roomChatDtos = roomChats.stream().map(chat -> RoomChatRes.toDto(chat)).collect(Collectors.toList());
            return roomChatDtos;
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
