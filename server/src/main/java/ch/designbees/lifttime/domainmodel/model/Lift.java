package ch.designbees.lifttime.domainmodel.model;

import ch.designbees.lifttime.domainmodel.enm.MotionState;
import ch.designbees.lifttime.domainmodel.enm.MatchStatus;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class Lift {
    private UUID id;
    private MatchStatus status;
    private Theme theme;
    private MotionState motionState;
    private short lastFloor;
    private Long speedUnit;
    private Long duration;
    private String roomId;
}
