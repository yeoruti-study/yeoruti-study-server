package com.planner.server.domain.record.service;

import com.planner.server.domain.record.dto.RecordReqDto;
import com.planner.server.domain.record.dto.RecordResDto;
import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.record.repository.RecordRepository;
import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import com.planner.server.domain.user_study_subject.repository.UserStudySubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserStudySubjectRepository userStudySubjectRepository;
    private final UserRepository userRepository;

    public UUID startRecording(RecordReqDto.ReqStartRecord req, UUID userId) throws Exception {
        UUID userStudySubjectId = req.getUserStudySubjectId();

        Optional<User> findUser = userRepository.findById(userId);
        Optional<UserStudySubject> findUserStudySubject = userStudySubjectRepository.findByIdJoinFetchUser(userStudySubjectId);

        if(!findUser.isPresent() || !findUserStudySubject.isPresent()) {
            throw new Exception("id 값 확인요망");
        }

        UUID madeRecordId = UUID.randomUUID();
        Record record = Record.builder()
                .id(madeRecordId)
                .user(findUser.get())
                .userStudySubject(findUserStudySubject.get())
                .startTime(LocalDateTime.now())
                .studying(true)
                .build();
        recordRepository.save(record);
        return madeRecordId;
    }

    @Transactional
    public void endRecording(RecordReqDto.ReqEndRecord req) throws Exception{
        UUID recordId = req.getRecordId();
        Optional<Record> findRecord = recordRepository.findByIdJoinFetchUserAndUserStudySubject(recordId);
        if(!findRecord.isPresent()){
            throw new Exception("id 값 확인요망");
        }
        Record record = findRecord.get();
        record.setEndTime(LocalDateTime.now());
        record.setStudying(false);

        Duration totalStudyTime = Duration.between(record.getStartTime(), record.getEndTime());
        record.setTotalStudyTime(totalStudyTime);
    }

    public RecordResDto.ResSearchListByUser findListByUser(UUID userId) throws Exception {
        List<Record> recordList = new ArrayList<>();

        try{
            recordList = recordRepository.findByUserJoinFetchUserAndUserStudySubject(userId);
        }catch (Exception e){
            throw new Exception("일치하는 데이터가 없습니다.");
        }
        List<RecordResDto> recordResDtoList = new ArrayList<>();
        recordList.stream().forEach(record -> recordResDtoList.add(RecordResDto.toDto(record)));

        RecordResDto.ResSearchListByUser resSearchListByUser = new RecordResDto.ResSearchListByUser(userId, recordResDtoList);
        return resSearchListByUser;
    }

    public List<RecordResDto> getByUserStudySubjectId(UUID id) throws Exception{
        List<Record> recordList = new ArrayList<>();
        try{
            recordList = recordRepository.findByUserStudySubjectJoinFetchUserAndUserStudySubject(id);
        }
        catch(Exception e){
            throw new Exception("[id] 확인 요망. friendUserId");
        }
        List<RecordResDto> recordResDtoList = new ArrayList<>();
        recordList.stream().forEach(record -> recordResDtoList.add(RecordResDto.toDto(record)));

        return recordResDtoList;
    }

    public void deleteOne(UUID recordId) throws Exception {
        Optional<Record> byId = recordRepository.findByIdJoinFetchUserAndUserStudySubject(recordId);
        if(!byId.isPresent()){
            throw new Exception("[id] 확인 요망");
        }
        Record record = byId.get();
        recordRepository.delete(record);
    }
}
