package ch.designbees.lifttime.domain;

import ch.designbees.lifttime.domainmodel.enm.Theme;

import java.util.UUID;

public interface LiftDomainService {

    void updateLiftFloor(UUID id, short floor);

    String setRoomId(UUID id, Theme theme, String roomId);
    String getPair(UUID id, Theme theme);

    UUID createLift(UUID id);
    UUID createLift();
}
