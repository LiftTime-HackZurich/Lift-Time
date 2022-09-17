package ch.designbees.lifttime.domain.impl;

import ch.designbees.lifttime.domain.LiftDomainService;
import ch.designbees.lifttime.domainmodel.enm.MotionState;
import ch.designbees.lifttime.domainmodel.enm.MatchStatus;
import ch.designbees.lifttime.domainmodel.enm.Theme;
import ch.designbees.lifttime.domainmodel.model.Lift;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultLiftDomainService implements LiftDomainService {

    Collection<Lift> lifts = new ArrayList<>();

    // public UUID createLiftFloor(short floor) {
    //     UUID id = UUID.randomUUID();
    //     Lift lift = Lift.builder()
    //             .id(id)
    //             .motionState(MotionState.MOVING)
    //             .status(MatchStatus.AVAILABLE)
    //             .duration(3L*floor)
    //             .lastFloor(floor)
    //             .speedUnit(3L)
    //             .build();
    //     lifts.add(lift);

    //     return id;
    // }

    public void updateLiftFloor(UUID id, short floor) {
        Optional<Lift> liftOptionalt = getLift(id);
        if(liftOptionalt.isPresent()) {
            liftOptionalt.get().setMotionState(MotionState.MOVING);
            liftOptionalt.get().setDuration(getDuration(floor, liftOptionalt.get()));
            liftOptionalt.get().setLastFloor(floor);
        }
    }

    public String setRoomId(UUID id, Theme theme, String roomId) {
        Lift lift = getLift(id).get();
        lift.setTheme(theme);
        lift.setStatus(MatchStatus.WAIT);
        lift.setRoomId(roomId);
        return "OK";
    }

    public String getPair(UUID id, Theme theme) {
        Lift lift = getLift(id).get();
        Optional<Lift> optionalLift = getFirstMatch(id, theme);
        if (optionalLift.isPresent())
        {
            Lift firstLift = optionalLift.get();
            firstLift.setStatus(MatchStatus.BUSY);
            firstLift.setStatus(MatchStatus.BUSY);

            String firstListRoomId = firstLift.getRoomId();
            lift.setRoomId(firstListRoomId);
            return firstListRoomId;
        }
        return null;
    }


    private Optional<Lift> getLift(UUID id) {
        return lifts.stream()
                .filter(lift -> lift.getId().equals( id))
                .findFirst();
    }

    private Optional<Lift> getFirstMatch(UUID id, Theme theme) {

        return lifts
                .stream()
                .filter(lift ->
                        !lift.getId().equals(id)
                        && lift.getTheme() != null && lift.getTheme().equals( theme)
                        && lift.getStatus().equals( MatchStatus.WAIT)
                ).findFirst();
    }

    private long getDuration(Short dest, Lift lift) {
        return (dest - lift.getLastFloor()) * lift.getSpeedUnit();
    }

    public UUID createLift(UUID id){
        Optional<Lift> lift = getLift(id);

        if(!lift.isPresent()) {

            id =  createLift();
            System.out.println("Lift created " + id);
        }

        return id;
    }

    public UUID createLift(){

            UUID id = UUID.randomUUID();
            Lift lift=Lift.builder()
                    .id(id)
                    .motionState(MotionState.MOVING)
                    .status(MatchStatus.AVAILABLE)
                    .duration(0L)
                    .lastFloor((short) 0)
                    .speedUnit(3L)
                    .build();
            lifts.add(lift);
        System.out.println("Lift created (without id method) " + id);

        return id;
    }

}
