package fr.lernejo.fileinjector;

public class GameMessage {
    private final String $schema = "http://json-schema.org/schema#";
    private final String type = "object";
    private GameEntry properties;
    private final String[] required = { "title", "thumbnail", "short_description", "genre", "platform", "publisher", "developer", "release_date" };

    public GameEntry properties() {
        return this.properties;
    }
    public GameMessage(GameEntry game) {
        this.properties = game;
    }

}
