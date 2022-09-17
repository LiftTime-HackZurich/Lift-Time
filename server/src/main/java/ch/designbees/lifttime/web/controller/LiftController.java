package ch.designbees.lifttime.web.controller;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

 @CrossOrigin
@RestController
@RequestMapping(LiftController.LIFT_ROOT_PATH)
@RequiredArgsConstructor
public class LiftController {
    public static final String LIFT_ROOT_PATH = "/lift/api/v1";

    private final LiftDomainService liftDomainService;
    private final SimpMessagingTemplate simpMessagingTemplate;

   
    @PostMapping("/{id}/{floor}")
    public ResponseEntity updateLiftFloor(@PathVariable UUID id, @RequestBody short floor) {
        liftDomainService.updateLiftFloor(id, floor);
        return ResponseEntity.ok(ResponseEntity.noContent());
    }

    @GetMapping("/meetings/{id}/themes/{themeEnum}/roomId/{roomId}")
    public ResponseEntity matchPairs(@PathVariable UUID id, @PathVariable String themeEnum, @PathVariable String roomId) {
        String matchId = liftDomainService.matchPairs(id, Theme.valueOf(themeEnum),roomId);

        //simpMessagingTemplate.convertAndSend("/topic/meetings/" + matchId.toString(), theme);

        return ResponseEntity.ok(matchId);
    }

   
    @GetMapping("/getKey")
    public ResponseEntity<UUID> createLift() {
        UUID uuid = liftDomainService.createLift();
        return ResponseEntity.ok(uuid);
    }

}
