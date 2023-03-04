package com.planner.server.domain.room_user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.room_user.dto.RoomUserReqDto;
import com.planner.server.domain.room_user.dto.RoomUserResDto;
import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.room_user.repository.RoomUserRepository;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.study_room.repository.StudyRoomRepository;
import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.utils.SecurityContextHolderUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomUserService {
    private final RoomUserRepository roomUserRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    public void createOne(RoomUserReqDto roomUserDto) {
        UUID userId = SecurityContextHolderUtils.getUserId();
        UUID studyRoomId = roomUserDto.getStudyRoomId();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        
        if(userOpt.isPresent() && studyRoomOpt.isPresent()) {
            this.checkStudyRoomMaximumNumberOfPeople(studyRoomOpt.get());
            this.checkDuplicationJoin(roomUserDto);

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

    // 스터디룸 정원 확인
    public void checkStudyRoomMaximumNumberOfPeople(StudyRoom studyRoom) {
        int maximumNumberOfPeople = studyRoom.getMaximumNumberOfPeople();
        List<UserResDto> roomUserDtos = this.searchUserListByStudyRoomId(studyRoom.getId());

        if(!roomUserDtos.isEmpty() && (roomUserDtos.size() >= maximumNumberOfPeople)) {
            throw new RuntimeException("스터디룸 정원을 초과하였습니다.");
        }
    }
    
    // 가입 여부 확인
    public void checkDuplicationJoin(RoomUserReqDto roomUserDto) {
        List<StudyRoomResDto> studyRoomDtos = this.searchListJoinedStudyRoom();
        
        studyRoomDtos.forEach(studyRoom -> {
            if(studyRoom.getId().equals(roomUserDto.getStudyRoomId())) {
                throw new RuntimeException("이미 가입한 스터디룸입니다.");
            }
        });
    }

    public void deleteOne(UUID studyRoomId) {
        // studyRoomId와 userId로 roomUser를 조회
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<RoomUser> entityOpt = roomUserRepository.findByUserIdAndStudyRoomId(userId, studyRoomId);

        if(entityOpt.isPresent()) {
            roomUserRepository.delete(entityOpt.get());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    @Transactional(readOnly = true)
    public List<UserResDto> searchUserListByStudyRoomId(UUID studyRoomId) {
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);
        
        if(studyRoomOpt.isPresent()) {
            List<RoomUser> roomUsers = roomUserRepository.findListJoinFetchUserByStudyRoomId(studyRoomOpt.get().getId());
            List<UserResDto> roomUserDtos = roomUsers.stream().map(entity -> UserResDto.toDto(entity.getUser())).collect(Collectors.toList());
            return roomUserDtos;
        }else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<StudyRoomResDto> searchListJoinedStudyRoom() {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isPresent()) {
            List<RoomUser> roomUsers = roomUserRepository.findListJoinFetchUserByUserId(userOpt.get().getId());
            List<RoomUserResDto> roomUserResDtos = roomUsers.stream().map(r -> RoomUserResDto.toDto(r)).collect(Collectors.toList());
            List<StudyRoomResDto> studyRoomResDtos = roomUserResDtos.stream().map(r -> r.getStudyRoomDto()).collect(Collectors.toList());
            return studyRoomResDtos;
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }

    }
}
