package xyz.litewars.litewars.api.arena.team;

public class Team {
    private final Colors colors;
    private boolean isEditing;

    public Team (Colors colors, boolean isEditing) {
        this.colors = colors;
        this.isEditing = isEditing;
    }

    public Colors getColors() {
        return colors;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public Team setEditing(boolean editing) {
        isEditing = editing;
        return this;
    }
}
