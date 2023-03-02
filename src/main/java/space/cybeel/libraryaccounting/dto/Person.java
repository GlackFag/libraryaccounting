package space.cybeel.libraryaccounting.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class Person extends DataTransferObject{
    @NotNull
    @Size(min = 5, max = 150, message = "Name should be between 5 and 150")
    @Getter
    @Setter
    private String fullName;

    @Min(value = 1850, message = "You birth year should be grater or equal 1850")
    @Max(value = 2023, message = "You birth year should be lower or equal 2023")
    @Getter
    @Setter
    private int yearBorn;

    @Getter
    @Setter
    private int id;

    public Person(String fullName, int yearBorn, int id) {
        this.fullName = fullName;
        this.yearBorn = yearBorn;
        this.id = id;
    }

    public Person() {
    }
}
