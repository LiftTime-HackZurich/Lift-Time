package ch.designbees.lifttime.domain.impl;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.MotionState;
import ch.designbees.lifttime.domainmodel.enm.MatchStatus;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import ch.designbees.lifttime.domainmodel.model.Lift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultLiftDomainService implements LiftDomainService {

    Collection<Lift> lifts = new ArrayList<>();

    public UUID createLiftFloor(short floor) {
        UUID id = UUID.randomUUID();
        Lift lift = Lift.builder()
                .id(id)
                .motionState(MotionState.MOVING)
                .status(MatchStatus.AVAILABLE)
                .duration(3L*floor)
                .lastFloor(floor)
                .speedUnit(3L)
                .build();
        lifts.add(lift);

        return id;
    }

    public void updateLiftFloor(UUID id, short floor) {
        Lift lift = getLift(id);
        lift.setMotionState(MotionState.MOVING);
        lift.setDuration(getDuration(floor, lift));
        lift.setLastFloor(floor);
    }

    public UUID matchPairs(UUID id, Theme theme) {
        Lift lift = getLift(id);
        if (isMatchable(theme))
        {
            for (Lift lif : lifts) {
                if (lif.getId() != id && lif.getDuration()-lift.getDuration() >= 5) {
                    lif.setStatus(MatchStatus.BUSY);
                    lift.setStatus(MatchStatus.BUSY);
                    return lif.getId();
                }
            }
        }
        lift.setStatus(MatchStatus.WAIT);
        return null;
    }

    private Lift getLift(UUID id) {
        return lifts.stream()
                .filter(lift -> lift.getId() == id)
                .findFirst()
                .get();
    }

    private boolean isMatchable(Theme theme) {
        return lifts.stream().anyMatch(lift ->
                lift.getTheme() == theme
                && lift.getStatus() == MatchStatus.WAIT);
    }

    private long getDuration(Short dest, Lift lift) {
        return (dest - lift.getLastFloor()) * lift.getSpeedUnit();
    }
}
