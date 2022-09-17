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

    @GetMapping("/setRoomId/{id}/themes/{themeEnum}/roomId/{roomId}")
    public ResponseEntity setRoomId(@PathVariable UUID id, @PathVariable Theme themeEnum, @PathVariable String roomId) {
        String matchId = liftDomainService.setRoomId(id, themeEnum,roomId);

        return ResponseEntity.ok(matchId);
    }

     @GetMapping("/getPair/{id}/themes/{themeEnum}")
     public ResponseEntity getPair(@PathVariable UUID id, @PathVariable Theme themeEnum) {
         String roomId = liftDomainService.getPair(id, themeEnum);
         return ResponseEntity.ok(roomId);
     }

    @RequestMapping(value = {"/getKey", "/getKey/{id}"})
    public ResponseEntity<UUID> createLift(@PathVariable(name = "id", required = false) UUID id) {
        UUID uuid = id == null ?  liftDomainService.createLift() : liftDomainService.createLift(id);
        return ResponseEntity.ok(uuid);
    }

}
