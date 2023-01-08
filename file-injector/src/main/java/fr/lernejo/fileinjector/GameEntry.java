package fr.lernejo.fileinjector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"freetogame_profile_url", "game_url"})
public record GameEntry(@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Integer id,
                        String title,
                        String thumbnail,
                        String short_description,
                        String genre,
                        String platform,
                        String publisher,
                        String developer,
                        String release_date) {
}
