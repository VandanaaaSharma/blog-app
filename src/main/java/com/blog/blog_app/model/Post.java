package com.blog.blog_app.model;                 // 1: Package name (matches folder)

import jakarta.persistence.*;                    // 2: JPA annotations like @Entity, @Id
import lombok.*;                                // 3: Lombok for getters/setters, etc.
import java.time.LocalDateTime;                 // 4: Date/time for createdAt

@Entity                                         // 5: Marks this class as a JPA entity (table)
@Table(name = "posts")                          // 6: DB table name "posts"
@Getter                                         // 7: Lombok generates getters
@Setter                                         // 8: Lombok generates setters
@NoArgsConstructor                              // 9: Lombok generates empty constructor
@AllArgsConstructor                             // 10: Lombok generates all-args constructor
@Builder                                        // 11: Lombok adds builder pattern
public class Post {

    @Id                                         // 12: Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
                                                // 13: Auto-increment ID
    private Long id;                            // 14: Column "id"

    @Column(nullable = false, length = 150)     // 15: Not null, max length 150
    private String title;                       // 16: Post title

    @Column(nullable = false, length = 500)     // 17: Short summary
    private String summary;                     // 18: Field for summary/description

    @Lob                                        // 19: "Large Object" → text/blob
    @Column(nullable = false)                   // 20: Full content cannot be null
    private String content;                     // 21: Full blog content

    private String author;                      // 22: Optional author name

    private LocalDateTime createdAt;            // 23: Creation time

    private LocalDateTime updatedAt;            // 24: Last update time
}
