package ch.designbees.lifttime.web.controller;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
    public ResponseEntity matchPairs(@PathVariable UUID id, @PathVariable Theme themeEnum, @PathVariable String roomId) {
        String matchId = liftDomainService.matchPairsOrSave(id, themeEnum,roomId);

        //simpMessagingTemplate.convertAndSend("/topic/meetings/" + matchId.toString(), theme);

        return ResponseEntity.ok(matchId);
    }

   
    @GetMapping("/getKey/{id}")
    public ResponseEntity<UUID> createLift(@PathVariable(required = false) UUID id) {
        UUID uuid = liftDomainService.createLift(id);
        return ResponseEntity.ok(uuid);
    }

}
