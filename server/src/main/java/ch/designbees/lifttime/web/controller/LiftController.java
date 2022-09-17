package ch.designbees.lifttime.web.controller;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(LiftController.LIFT_ROOT_PATH)
@RequiredArgsConstructor
public class LiftController {
    public static final String LIFT_ROOT_PATH = "lift/api/v1";

    private final LiftDomainService liftDomainService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/{floor}")
    public ResponseEntity<UUID> createLift(@RequestBody short floor) {
        UUID uuid = liftDomainService.createLiftFloor(floor);
        return ResponseEntity.ok(uuid);
    }

    @PostMapping("/{id}/{floor}")
    public ResponseEntity updateLiftFloor(@PathVariable UUID id, @RequestBody short floor) {
        liftDomainService.updateLiftFloor(id, floor);
        return ResponseEntity.ok(ResponseEntity.noContent());
    }

    @MessageMapping("/meetings/{id}/themes/{themeEnum}")
    public ResponseEntity matchPairs(@PathVariable UUID id, @RequestBody Theme theme) {
        UUID matchId = liftDomainService.matchPairs(id, theme);

        simpMessagingTemplate.convertAndSend("/topic/meetings/" + matchId.toString(), theme);

        return ResponseEntity.ok(ResponseEntity.noContent());
    }
}
