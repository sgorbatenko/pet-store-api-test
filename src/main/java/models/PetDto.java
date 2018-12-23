package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

public class PetDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder(toBuilder = true)
    public static class Pet {
        private String id;
        private Category category;
        private String name;
        private ArrayList<String> photoUrls;
        private ArrayList<Tag> tags;
        private Status status;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Tag {
        private String id;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Category {
        private String id;
        private String name;
    }
}
