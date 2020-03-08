package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    long id;
    long courseId;
    String title;
    String text;

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
