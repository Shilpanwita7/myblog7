package com.myblog7.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"title"})}
)
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "description", nullable = false)
  private String description;
  @Column(name = "content", nullable = false)
  private String content;

//  one to many mapping, onk comments for a single post. tai List<Comment> or Set<Comment>, set will avoid duplicate comments
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments;
}
