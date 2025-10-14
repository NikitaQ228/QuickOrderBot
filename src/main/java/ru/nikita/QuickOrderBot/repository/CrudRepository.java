package ru.nikita.QuickOrderBot.repository;

/**
 * Универсальный интерфейс для операций CRUD (создание, чтение, обновление, удаление)
 * над сущностями типа {@code T} с идентификатором типа {@code ID}.
 *
 * @param <T>  тип сущности
 * @param <ID> тип идентификатора сущности
 */
public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
}
