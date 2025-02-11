package com.eeerrorcode.pilllaw.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eeerrorcode.pilllaw.entity.LetterEntity;
import com.eeerrorcode.pilllaw.repository.FollowLetterRepository;

import jakarta.transaction.Transactional;

// @Log4j2
@Service
// @RequiredArgsConstructor
public class LetterService {
    private final FollowLetterRepository letterRepository;
    // @Autowired
    public LetterService(FollowLetterRepository letterRepository) { 
        this.letterRepository = letterRepository; // 주입
    }
    @Transactional
    public LetterEntity sendLetter(String sender, String receiver, String content){
        LetterEntity letter = new LetterEntity();
        letter.setSender(sender);
        letter.setReceiver(receiver);
        letter.setContent(content);
        letter.setSentAt(LocalDateTime.now());

        return letterRepository.save(letter);
    }
//     // @Transactional(readOnly = true)
//     public List<LetterEntity> getReceivedLetter(String receiver) {
//         return letterRepository.findByReceiver(receiver);
//     }
// }
public List<LetterEntity> getReceivedLetters(String receiver) {
    List<LetterEntity> letters = letterRepository.findByReceiver(receiver);
    if (letters.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No letters found for receiver: " + receiver);
    }
    return letters;
}
}