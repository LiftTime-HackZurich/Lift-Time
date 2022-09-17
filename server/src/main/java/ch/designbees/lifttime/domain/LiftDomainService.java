package ch.designbees.lifttime.domain;

import ch.designbees.lifttime.domainmodel.enm.Theme;

import java.util.UUID;

public interface LiftDomainService {

    void updateLiftFloor(UUID id, short floor);

    String matchPairsOrSave(UUID id, Theme theme, String roomId);

    UUID createLift(UUID id);
}
