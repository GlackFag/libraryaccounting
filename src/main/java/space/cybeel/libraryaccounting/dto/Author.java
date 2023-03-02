package space.cybeel.libraryaccounting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Author extends DataTransferObject{

    public static final Author UNKNOWN_AUTHOR = new Author("Unknown Author", -1) {
        @Override
        public void setName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setId(int id) {
            throw new UnsupportedOperationException();
        }
    };

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int id;


    public Author() {
    }

    public Author(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
