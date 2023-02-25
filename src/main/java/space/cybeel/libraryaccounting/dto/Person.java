package space.cybeel.libraryaccounting.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class Person extends DataTransferObject{
    @NotNull
    @Size(min = 5, max = 150)
    @Getter
    @Setter
    private String fullName;

    @Min(1850)
    @Max(2023)
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
