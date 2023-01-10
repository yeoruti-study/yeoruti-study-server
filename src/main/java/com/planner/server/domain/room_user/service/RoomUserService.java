package com.planner.server.domain.room_user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.room_user.dto.RoomUserDto;
import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.room_user.repository.RoomUserRepository;
import com.planner.server.domain.study_room.dto.StudyRoomDto;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.study_room.repository.StudyRoomRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomUserService {
    private final RoomUserRepository roomUserRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    public void createOne(RoomUserDto roomUserDto) {
        UUID userId = roomUserDto.getUserDto().getId();
        UUID studyRoomId = roomUserDto.getStudyRoomDto().getId();
        
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        // if(uesrOpt.isPresent() && studyRoomOpt.isPresent()) {
        if(studyRoomOpt.isPresent()) {
            // 2. study room 생성
            RoomUser roomUser = RoomUser.builder()
                .id(UUID.randomUUID())
                .user(userOpt.get())
                .studyRoom(studyRoomOpt.get())
                .build();

            roomUserRepository.save(roomUser);
        } else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    // @Transactional(readOnly = true)
    // public List<RoomUserDto> searchListByUserId(UUID userId) {
    //     List<RoomUserDto> roomUserDtos = new ArrayList<>();
    //     Optional<User> userOpt = userRepository.findById(userId);
        
    //     if(userOpt.isPresent()) {
    //         List<RoomUser> roomUsers = roomUserRepository.findByUser(userOpt.get());
    //         roomUsers.forEach(entity -> {
    //             // UserDto user = UserDto.builder()
    //             //     .id(entity.getUser().getId())
    //             //     .build();
    
    //             // TODO :: study category는..?
    //             StudyRoomDto studyRoomDto = StudyRoomDto.builder()
    //                 .id(entity.getStudyRoom().getId())
    //                 .name(entity.getStudyRoom().getName())
    //                 .maximumNumberOfPeople(entity.getStudyRoom().getMaximumNumberOfPeople())
    //                 .studyGoalTime(entity.getStudyRoom().getStudyGoalTime())
    //                 .roomPassword(entity.getStudyRoom().getRoomPassword())
    //                 .masterUserId(entity.getStudyRoom().getMasterUserId())
    //                 .createdAt(entity.getStudyRoom().getCreatedAt())
    //                 .updatedAt(entity.getStudyRoom().getUpdatedAt())
    //                 .build();
    
    //             RoomUserDto dto = RoomUserDto.builder()
    //                 .id(entity.getId())
    //                 // .userDto(userDto)
    //                 .studyRoomDto(studyRoomDto)
    //                 .build();
    
    //             roomUserDtos.add(dto);
    //         });
    //     }
    //     return roomUserDtos;
    // }

    @Transactional(readOnly = true)
    public List<RoomUserDto> searchListByStudyRoomId(UUID studyRoomId) {
        List<RoomUserDto> roomUserDtos = new ArrayList<>();
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        
        if(studyRoomOpt.isPresent()) {
            List<RoomUser> roomUsers = roomUserRepository.findByStudyRoom(studyRoomOpt.get());
            roomUsers.forEach(entity -> {
                // UserDto user = UserDto.builder()
                //     .id(entity.getUser().getId())
                //     .build();
    
                // TODO :: study category는..?
                StudyRoomDto studyRoomDto = StudyRoomDto.builder()
                    .id(entity.getStudyRoom().getId())
                    .name(entity.getStudyRoom().getName())
                    .maximumNumberOfPeople(entity.getStudyRoom().getMaximumNumberOfPeople())
                    .studyGoalTime(entity.getStudyRoom().getStudyGoalTime())
                    .roomPassword(entity.getStudyRoom().getRoomPassword())
                    .masterUserId(entity.getStudyRoom().getMasterUserId())
                    .createdAt(entity.getStudyRoom().getCreatedAt())
                    .updatedAt(entity.getStudyRoom().getUpdatedAt())
                    .build();
    
                RoomUserDto dto = RoomUserDto.builder()
                    .id(entity.getId())
                    // .userDto(userDto)
                    .studyRoomDto(studyRoomDto)
                    .build();
    
                roomUserDtos.add(dto);
            });
        }
        return roomUserDtos;
    }

    public void deleteOne(UUID roomUserId) {
        Optional<RoomUser> entityOpt = roomUserRepository.findById(roomUserId);

        if(entityOpt.isPresent()) {
            roomUserRepository.delete(entityOpt.get());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
