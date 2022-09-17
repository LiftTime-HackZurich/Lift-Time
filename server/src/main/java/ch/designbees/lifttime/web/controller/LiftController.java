package ch.designbees.lifttime.web.controller;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;


@RestController
@RequestMapping(LiftController.LIFT_ROOT_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    public ResponseEntity matchPairs() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String dtoAsString = objectMapper.writeValueAsString(new PersonData("cemil"));

        simpMessagingTemplate.convertAndSend("/topic/meetings/1234",  dtoAsString);

        return ResponseEntity.ok(ResponseEntity.noContent());
    }

    class PersonData implements Serializable {
        private String name;

        public PersonData(String pName){
            this.name = pName;
        }
    }
}
