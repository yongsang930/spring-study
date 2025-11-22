package com.example.simple_board.board.db;

import com.example.simple_board.post.db.PostEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    private String status;

    @OneToMany(mappedBy = "board")
    @Builder.Default
    @SQLRestriction("status = 'REGISTERED'")
    @OrderBy("id DESC")
    private List<PostEntity> postList = List.of();

}
