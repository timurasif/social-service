package com.demo.social.Data.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Integer Id;

    @Column(name = "content")
    private String content;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "post_id")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", insertable = false, updatable = false)
    private PostEntity post;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}