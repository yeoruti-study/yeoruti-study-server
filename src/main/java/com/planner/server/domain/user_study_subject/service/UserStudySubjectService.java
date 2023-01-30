package com.planner.server.domain.user_study_subject.service;

import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user_study_subject.dto.DeleteReqDto;
import com.planner.server.domain.user_study_subject.dto.SaveReqDto;
import com.planner.server.domain.user_study_subject.dto.UserSubjectDto;
import com.planner.server.domain.user_study_subject.dto.UserSubjectListDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import com.planner.server.domain.user_study_subject.repository.UserStudySubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserStudySubjectService {

    private final UserStudySubjectRepository userStudySubjectRepository;
    private final UserRepository userRepository;

    public void save(SaveReqDto req) throws Exception {
        Optional<UserStudySubject> byTitle = userStudySubjectRepository.findByTitle(req.getTitle());
        if(byTitle.isPresent()){
            throw new Exception("이미 존재하는 제목입니다. 다른 제목을 설정해주세요.");
        }
        Optional<User> byId = userRepository.findById(req.getUserId());
        if(!byId.isPresent()){
            throw new Exception("[userId] 값 확인 요망. 유저가 존재하지 않습니다.");
        }
        User user = byId.get();

        UserStudySubject userStudySubject = UserStudySubject.builder()
                .id(UUID.randomUUID())
                .title(req.getTitle())
                .user(user)
                .build();

        userStudySubjectRepository.save(userStudySubject);
    }

    public UserSubjectListDto findByUserId(UUID userId) throws Exception {

        List<UserStudySubject> subjectList;
        try{
            subjectList = userStudySubjectRepository.findByUserId(userId);
        }
        catch (Exception e){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }

        List<UserSubjectDto> userSubjectDtoList = new ArrayList<>();
        subjectList.forEach(s->userSubjectDtoList.add(UserSubjectDto.toDto(s)));

        return UserSubjectListDto.toDto(userSubjectDtoList);
    }


    public UserSubjectDto findById(UUID id) throws Exception {
        Optional<UserStudySubject> byId = userStudySubjectRepository.findById(id);
        if(!byId.isPresent())
            throw new Exception("parameter:[id] is wrong. There is no data for request id");

        UserStudySubject userStudySubject = byId.get();
        UserSubjectDto userSubjectDto = UserSubjectDto.toDto(userStudySubject);
        return userSubjectDto;
    }

    public void deleteById(DeleteReqDto req) throws Exception {
        Optional<UserStudySubject> byId = userStudySubjectRepository.findById(req.getId());
        if(!byId.isPresent()){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }
        UserStudySubject userStudySubject = byId.get();
        userStudySubjectRepository.delete(userStudySubject);
    }

    public void deleteByUserId(DeleteReqDto req) throws Exception {
        List<UserStudySubject> subjectList;
        try{
            subjectList = userStudySubjectRepository.findByUserId(req.getUserId());
        }
        catch (Exception e){
            throw new Exception("parameter:[id] is wrong. There is no data for request id");
        }

        subjectList.forEach(s -> userStudySubjectRepository.delete(s));
    }

}
