package com.demo.social.Data.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Integer id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "image_id")
    private Integer imageId;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", insertable = false, updatable = false)
    private ImageEntity image;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}