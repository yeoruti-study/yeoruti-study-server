package com.planner.server.domain.study_room.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.room_user.repository.RoomUserRepository;
import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_category.repository.StudyCategoryRepository;
import com.planner.server.domain.study_room.dto.StudyRoomReqDto;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.study_room.repository.StudyRoomRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyCategoryRepository studyCategoryRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;

    private final int DEFAULT_MAXIMUM_NUMBER_OF_PEOPLE = 30;

    // 1. studyRoom생성, roomUser생성
    public void createStudyRoomAndRoomUser(StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        UUID studyCategoryId = studyRoomDto.getStudyCategoryDto().getId();
        StudyRoom newStudyRoom = new StudyRoom();

        // 1. study category 조회
        Optional<StudyCategory> studyCategoryOpt = studyCategoryRepository.findById(studyCategoryId);
        Optional<User> userOpt = userRepository.findById(studyRoomDto.getMasterUserId());

        if(studyCategoryOpt.isPresent() && userOpt.isPresent()) {
            // 2. study room 생성
            newStudyRoom = StudyRoom.builder()
                .id(UUID.randomUUID())
                .name(studyRoomDto.getName())
                .maximumNumberOfPeople(studyRoomDto.getMaximumNumberOfPeople() == 0 ? DEFAULT_MAXIMUM_NUMBER_OF_PEOPLE : studyRoomDto.getMaximumNumberOfPeople())
                .studyCategory(studyCategoryOpt.get())
                .studyGoalTime(studyRoomDto.getStudyGoalTime())
                .roomPassword(studyRoomDto.getRoomPassword())
                .createdAt(LocalDateTime.now())
                .masterUserId(studyRoomDto.getMasterUserId())
                .build();

            studyRoomRepository.save(newStudyRoom);
        } else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
        
        // 3. room user 생성
        RoomUser roomUser = RoomUser.builder()
            .id(UUID.randomUUID())
            .user(userOpt.get())
            .studyRoom(newStudyRoom)
            .build();

        roomUserRepository.save(roomUser);
    }

    @Transactional(readOnly = true)
    public List<StudyRoomResDto> searchAll() {
        List<StudyRoom> studyRooms = studyRoomRepository.findAllAndRelatedStudyCategory();
        List<StudyRoomResDto> studyRoomDtos = studyRooms.stream().map(entity -> StudyRoomResDto.toDto(entity)).collect(Collectors.toList());
    
        return studyRoomDtos;
    }

    // TODO :: room password 변경 분리한거 api docs에 반영하자
    public void updateOne(StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        Optional<StudyRoom> entityOpt = studyRoomRepository.findById(studyRoomDto.getId());
        Optional<StudyCategory> studyCategoryOpt = null;
        
        if(entityOpt.isPresent()) {
            UUID updatedStudyCategoryId = studyRoomDto.getStudyCategoryDto().getId();
            if(!entityOpt.get().getStudyCategory().getId().equals(updatedStudyCategoryId)){
                studyCategoryOpt = studyCategoryRepository.findById(updatedStudyCategoryId);
                if(!studyCategoryOpt.isPresent()) {
                    throw new NullPointerException("존재하지 않는 데이터");
                }
            }

            StudyRoom entity = entityOpt.get();
            entity.setName(studyRoomDto.getName())
                .setStudyCategory(studyCategoryOpt.get())
                .setMaximumNumberOfPeople(studyRoomDto.getMaximumNumberOfPeople() == 0 ? DEFAULT_MAXIMUM_NUMBER_OF_PEOPLE : studyRoomDto.getMaximumNumberOfPeople())
                .setStudyGoalTime(studyRoomDto.getStudyGoalTime())
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

    public List<StudyRoomResDto> searchListByStudyCategory(UUID studyCategoryId) {
        Optional<StudyCategory> studyCategoryOpt = studyCategoryRepository.findById(studyCategoryId);

        if(studyCategoryOpt.isPresent()) {
            List<StudyRoom> studyRooms = studyRoomRepository.findListByStudyCategoryId(studyCategoryId);
            List<StudyRoomResDto> studyRoomResDtos = studyRooms.stream().map(r -> StudyRoomResDto.toDto(r)).collect(Collectors.toList());
            return studyRoomResDtos;
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    // Room Password는 master user만이 변경할 수 있다
    public void changeRoomPassword(StudyRoomReqDto.ReqChangePassword studyRoomDto) {
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomDto.getId());
        
        // TODO
        // 1. master user 확인을 위해 user 조회
        // 2. master user id 와 user id 가 동일하다면 room password 변경 허락
        // 3. 그렇지 않다면 room password 변경 거부
        
        if(studyRoomOpt.isPresent()) {
            StudyRoom studyRoom = studyRoomOpt.get();
            
            if(!studyRoomDto.getUserId().equals(studyRoom.getMasterUserId())) {
                // TODO :: 커스텀 예외 처리하기. null pointer exception X.
                throw new NullPointerException("허용된 유저가 아닙니다.");
            }
            studyRoom.setRoomPassword(studyRoomDto.getRoomPassword());
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    // Room Password 체크
    public void checkRoomPassword(StudyRoomReqDto.ReqCheckRoomPassword studyRoomDto) {
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomDto.getId());
        
        if(studyRoomOpt.isPresent()) {
            if(studyRoomOpt.get().getRoomPassword().equals(studyRoomDto.getRoomPassword())) {
                return;
            }else {
                // TODO :: 커스텀 예외 처리하기. null pointer exception X.
                throw new NullPointerException("스터디룸 비밀번호 오류");
            }
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }
}
