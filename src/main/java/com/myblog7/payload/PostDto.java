package com.myblog7.payload;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=2, message = "title should be atleast two characters")
    private String title;
    @NotEmpty
    @Size(min=4, message = "title should be atleast 4 characters")
    private String description;
    @NotEmpty
    @Size(min=5, message = "title should be atleast 5 characters")
    private String content;
}
