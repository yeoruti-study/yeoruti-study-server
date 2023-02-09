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
import com.planner.server.domain.user_study_subject.service.UserStudySubjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;

    private final UserStudySubjectRepository userStudySubjectRepository;

    Logger logger = LoggerFactory.getLogger(RecordService.class);

    public RecordResDto save(RecordReqDto req) throws Exception {
        User user = userService.findById(req.getUserId());
        UserResDto userResDto = UserResDto.toDto(user);

        Optional<UserStudySubject> byId = userStudySubjectRepository.findById(req.getUserStudySubjectId());
        if(!byId.isPresent())
            throw new Exception("[id] 확인 요망. UserStudySubject가 존재하지 않습니다.");

        UserStudySubject userStudySubject = byId.get();
        UserStudySubjectResDto userStudySubjectResDto = UserStudySubjectResDto.toDto(userStudySubject);

        Record record = Record.builder()
                .id(UUID.randomUUID())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .user(user)
                .userStudySubject(userStudySubject)
                .totalStudyTime(Duration.between(req.getStartTime(), req.getEndTime()))
                .studying(req.isStudying())
                .build();

        Record save = recordRepository.save(record);
        user.addRecord(save);

        return RecordResDto.builder()
                .id(record.getId())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .userDto(UserResDto.toDto(user))
                .userStudySubjectDto(UserStudySubjectResDto.toDto(userStudySubject))
                .totalStudyTime(record.getTotalStudyTime())
                .studying(record.isStudying())
                .build();
    }


    public List<RecordResDto> getAll() {
        List<Record> recordList = recordRepository.findAllByFetchJoin();
        List<RecordResDto> recordResDtoList = new ArrayList<>();

        recordList.stream().forEach(record -> recordResDtoList.add(RecordResDto.toDto(record)));
        return recordResDtoList;
    }

    public List<RecordResDto> getByUserId(UUID userId) throws Exception {
        List<Record> recordList = new ArrayList<>();

        try{
            recordList = recordRepository.findByUserId(userId);
        }catch (Exception e){
            throw new Exception("[user id] 확인 요망. 일치하는 데이터가 없습니다.");
        }
        List<RecordResDto> recordResDtoList = new ArrayList<>();
        recordList.stream().forEach(record -> recordResDtoList.add(RecordResDto.toDto(record)));

        return recordResDtoList;
    }

    public List<RecordResDto> getByUserStudySubjectId(UUID id) throws Exception{
        List<Record> recordList = new ArrayList<>();
        try{
            recordList = recordRepository.findByUserStudySubjectId(id);
        }
        catch(Exception e){
            throw new Exception("[id] 확인 요망. friendUserId");
        }
        List<RecordResDto> recordResDtoList = new ArrayList<>();
        recordList.stream().forEach(record -> recordResDtoList.add(RecordResDto.toDto(record)));

        return recordResDtoList;
    }

    public void deleteById(RecordReqDto req) throws Exception {
        Optional<Record> byId = recordRepository.findById(req.getId());
        if(!byId.isPresent()){
            throw new Exception("[id] 확인 요망");
        }
        Record record = byId.get();
        recordRepository.delete(record);
    }
}
