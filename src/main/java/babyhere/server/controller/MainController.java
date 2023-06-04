package babyhere.server.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
public class MainController {


    @GetMapping("/hello")
    public ResponseEntity<String> javaToPython() throws InterruptedException, IOException {


        String pythonScriptPath = "/home/admin/cry/baby_cry_detection/baby_cry_detection/prediction_simulation/prediction_simulation.py";
        String fileName = "signal_9s.wav";

        String command = "python3 " + pythonScriptPath + " --file_name " + fileName;

        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            // 명령어가 성공적으로 실행되었을 때의 처리
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // 명령어 실행 중 오류가 발생했을 때의 처리
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> javaToPythonTest() throws InterruptedException, IOException {


        String command = "python3 /Users/jeong-seungyeon/codes/server/hi.py ";

        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            // 명령어가 성공적으로 실행되었을 때의 처리
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // 명령어 실행 중 오류가 발생했을 때의 처리
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
