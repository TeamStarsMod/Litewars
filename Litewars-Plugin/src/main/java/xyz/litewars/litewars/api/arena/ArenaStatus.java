package xyz.litewars.litewars.api.arena;

public enum ArenaStatus {
    WAITING ("等待中……"),
    STARTING ("开始中……"),
    PLAYING ("正在游玩");

    private final String name;
    ArenaStatus (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
