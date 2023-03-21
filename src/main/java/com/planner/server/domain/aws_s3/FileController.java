package com.planner.server.domain.aws_s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.planner.server.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class FileController {
    private final AwsS3Uploader awsS3Uploader;

    @PostMapping("/upload/one")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String fileUrl = awsS3Uploader.upload(multipartFile, "image");
            Message message = Message.builder()
                    .data(fileUrl)
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        } catch (IOException e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
    }

    @DeleteMapping("/delete/one")
    public ResponseEntity<?> deleteOne(@RequestBody ReqDel req){

        Message message = null;
        try{
            awsS3Uploader.delete(req.getTitle());
            message = Message.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
        } catch(AmazonServiceException e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("fail")
                    .memo(e.getMessage())
                    .build();
        } catch(AmazonClientException e){
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("fail")
                    .memo(e.getMessage())
                    .build();
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

}