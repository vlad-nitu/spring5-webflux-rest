package vlad.springframework.spring5webfluxrest.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;
}
