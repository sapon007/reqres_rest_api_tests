package reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleUserModel {

    private DataModel data;
    private SupportModel support;

    @Getter
    @Setter
    public static class DataModel {
        private int id;
        private String email, avatar;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
    }

    @Getter
    @Setter
    public static class SupportModel {
        private String url, text;
    }
}
