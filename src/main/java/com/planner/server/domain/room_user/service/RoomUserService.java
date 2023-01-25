package com.planner.server.domain.room_user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.room_user.dto.RoomUserResDto;
import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.room_user.repository.RoomUserRepository;
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

    public void createOne(RoomUserResDto roomUserDto) {
        UUID userId = roomUserDto.getUserDto().getId();
        UUID studyRoomId = roomUserDto.getStudyRoomDto().getId();
        
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        
        if(userOpt.isPresent() && studyRoomOpt.isPresent()) {
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

    @Transactional(readOnly = true)
    public List<RoomUserResDto> searchListByStudyRoomId(UUID studyRoomId) {
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        
        if(studyRoomOpt.isPresent()) {
            List<RoomUser> roomUsers = roomUserRepository.findByStudyRoom(studyRoomOpt.get());
            List<RoomUserResDto> roomUserDtos = roomUsers.stream().map(entity -> RoomUserResDto.toDto(entity)).collect(Collectors.toList());
            return roomUserDtos;
        }else {
            return null;
        }
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
