import java.util.List;

public interface GenericDAO<T> {
    void add(T t);
    List<T> getAll();
    T getById(int id);
    void update(T t);
    void delete(T t);

}
