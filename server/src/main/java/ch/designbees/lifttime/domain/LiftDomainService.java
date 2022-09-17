package ch.designbees.lifttime.domain;

import ch.designbees.lifttime.domainmodel.enm.Theme;

import java.util.UUID;

public interface LiftDomainService {

    void updateLiftFloor(UUID id, short floor);

    UUID matchPairs(UUID id, Theme theme);

    UUID createLiftFloor(short floor);
}
