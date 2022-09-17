package ch.designbees.lifttime.domainmodel.enm;

public enum MatchStatus {
    AVAILABLE("avaible"),
    WAIT("wait"),
    BUSY("busy"),
    BREAK_DOWN("breakDown");

    private String value;

    MatchStatus(String value) {
    }
}
