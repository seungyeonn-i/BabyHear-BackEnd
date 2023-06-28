package babyhere.server.controller;

import babyhere.server.gpt.dto.CompletionChatResponse;
import babyhere.server.gpt.dto.GPTCompletionChatRequest;
import babyhere.server.gpt.service.GPTChatRestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MainController {

    private final GPTChatRestService gptChatRestService;


    @GetMapping("/hello")
    public ResponseEntity<String> javaToPython() throws InterruptedException, IOException {


        String pythonScriptPath = "/home/admin/cry/baby_cry_detection/baby_cry_detection/prediction_simulation/prediction_simulation.py";
        String fileName = "signal_9s.wav";

        String command = "python3 " + pythonScriptPath + " --file_name " + fileName;

        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();


        if (exitCode == 0) {
            // 명령어가 성공적으로 실행되었을 때의 처리

            Process processOut = Runtime.getRuntime().exec("cat /home/admin/cry/baby_cry_detection/prediction/prediction.txt");
            InputStream inputStream = processOut.getInputStream();
            InputStream errorStream = processOut.getErrorStream();

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = inputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            System.out.println(output.toString());

            return new ResponseEntity<>(output.toString(), HttpStatus.OK);
        } else {
            // 명령어 실행 중 오류가 발생했을 때의 처리
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @PostMapping("/record")
    public ResponseEntity<String> record(@RequestBody MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {

            String fileName = file.getOriginalFilename();
            assert fileName != null;

            Path targetPath = Path.of("/home/admin/cry/baby_cry_detection/baby_cry_detection/prediction_simulation", fileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            convertWebMToWav(file.getInputStream());

            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void convertWebMToWav(InputStream webmInputStream) throws IOException, InterruptedException {
//        String outputPath = "/Users/jeong-seungyeon/codes/server/signal_9s.wav";

        String ffmpegCommand = "ffmpeg -y -i /home/admin/cry/baby_cry_detection/baby_cry_detection/prediction_simulation/signal_9s.webm -acodec pcm_s16le -ar 44100 -ac 2 -vn -f wav /home/admin/cry/baby_cry_detection/baby_cry_detection/prediction_simulation/signal_9s.wav";


        Process process = Runtime.getRuntime().exec(ffmpegCommand);
        int exitCode = process.waitFor();


        if (exitCode == 0) {
            // 명령어가 성공적으로 실행되었을 때의 처리
            System.out.println("성공");

        }

    }





    @GetMapping("/test")
    public ResponseEntity<String> javaToPythonTest() throws InterruptedException, IOException {


        String command = "python3 /Users/jeong-seungyeon/codes/server/hi.py ";

        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            // 명령어가 성공적으로 실행되었을 때의 처리

            Process processOut = Runtime.getRuntime().exec("cat /Users/jeong-seungyeon/codes/server/a.txt");
            InputStream inputStream = processOut.getInputStream();
            InputStream errorStream = processOut.getErrorStream();

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = inputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            System.out.println(output.toString());

            return new ResponseEntity<>(output.toString(), HttpStatus.OK);
        } else {
            // 명령어 실행 중 오류가 발생했을 때의 처리
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recordTest")
    public ResponseEntity<String> recordTest(@RequestParam("file") MultipartFile file) {


        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {
            String fileName = "signal_9s.wav";
            assert fileName != null;

            Path targetPath = Path.of("/Users/jeong-seungyeon/codes/server", fileName);
//            convertWebMToWav(file.getInputStream());

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/completion/chat")
    public CompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest gptCompletionChatRequest) {

        return gptChatRestService.completionChat(gptCompletionChatRequest);
    }



}