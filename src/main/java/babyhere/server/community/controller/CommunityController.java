package babyhere.server.community.controller;

import babyhere.server.community.dto.CommunityListRequest;
import babyhere.server.community.dto.CommunityListResponse;
import babyhere.server.community.entity.Community;
import babyhere.server.community.repository.CommunityRepository;
import babyhere.server.gpt.dto.CompletionChatResponse;
import babyhere.server.gpt.dto.GPTCompletionChatRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CommunityController {

    private final CommunityRepository communityRepository;

    @PostMapping("/community")
    public ResponseEntity<CommunityListResponse> communityCreate(@RequestBody CommunityListRequest communityListResponse) {

        String date = communityListResponse.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        Community community = new Community(communityListResponse.getTitle(), communityListResponse.getNickname(), communityListResponse.getDetail(), dateTime, Math.round(Math.random()*100));
        communityRepository.save(community);

        return new ResponseEntity<>(new CommunityListResponse(community.getId(), community.getTitle(), community.getNickname(), community.getDetail(), community.getDate().toString()), HttpStatus.OK);
    }

    @GetMapping("/community/heart")
    public ResponseEntity<List<CommunityListResponse>> communityListByHits() {

        List<Community> allByOrderByHits = communityRepository.findAllByOrderByHits();
        List<CommunityListResponse> communityListResponses = new ArrayList<>();
        for (Community allByOrderByHit : allByOrderByHits) {
            communityListResponses.add(new CommunityListResponse(allByOrderByHit.getId(),allByOrderByHit.getTitle(), allByOrderByHit.getNickname(), allByOrderByHit.getDetail(), allByOrderByHit.getDate().toString()));
        }

        return new ResponseEntity<>(communityListResponses, HttpStatus.OK);

    }

    @GetMapping("/community/latest")
    public ResponseEntity<List<CommunityListResponse>> communityListByDate() {

        List<Community> allByOrderByDateDesc = communityRepository.findAllByOrderByDateDesc();

        List<CommunityListResponse> communityListResponses = new ArrayList<>();
        for (Community allByOrderByHit : allByOrderByDateDesc) {
            communityListResponses.add(new CommunityListResponse(allByOrderByHit.getId(),allByOrderByHit.getTitle(), allByOrderByHit.getNickname(), allByOrderByHit.getDetail(), allByOrderByHit.getDate().toString()));
        }
        return new ResponseEntity<>(communityListResponses, HttpStatus.OK);

    }
}
