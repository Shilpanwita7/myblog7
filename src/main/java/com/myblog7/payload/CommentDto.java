package com.myblog7.payload;




import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min=2, message = "name should be atleast two characters")
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min=6, message = "body should be atleast 6 characters")
    private String body;
}

