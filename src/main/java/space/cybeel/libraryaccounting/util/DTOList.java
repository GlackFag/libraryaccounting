package space.cybeel.libraryaccounting.util;

import space.cybeel.libraryaccounting.dto.DataTransferObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class DTOList<T extends DataTransferObject> extends ArrayList<T> {
    public DTOList(int initialCapacity) {
        super(initialCapacity);
    }

    public DTOList() {
    }

    public DTOList(Collection<? extends T> c) {
        super(c);
    }

    public T getIfOrNull(Predicate<T> p) {
        return this.stream().filter(p).findFirst().orElse(null);
    }

    public T getIfOrDefault(Predicate<T> p, T defaultObj){
        return this.stream().filter(p).findFirst().orElse(defaultObj);
    }

    public T getByIdOrNull(int id){
        return getIfOrNull(x -> x.getId() == id);
    }

    public T getByIdOrDefault(int id, T defaultObj){
        return getIfOrDefault(x -> x.getId() == id, defaultObj);
    }
}
