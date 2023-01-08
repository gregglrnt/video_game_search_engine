package fr.lernejo.fileinjector;

public class GameMessage {
    public final String $schema = "http://json-schema.org/schema#";
    public final String type = "object";
    public final GameEntry properties;
    public final String[] required = { "title", "thumbnail", "short_description", "genre", "platform", "publisher", "developer", "release_date" };

    public GameMessage(GameEntry game) {
        this.properties = game;
    }

}
