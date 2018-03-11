package sda.ldz5;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @NotNull
    private Object _id;
    @NotNull
    private Object student_id;
    @NotNull
    private String type;
    @NotNull
    private Double score;

}
