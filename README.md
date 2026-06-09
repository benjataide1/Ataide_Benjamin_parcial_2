# TP JPA - Programación III
## Tecnicatura Universitaria en Programación - UTN

---

## Descripción

Sistema de gestión de Categorías y Productos desarrollado en Java utilizando **Java Persistence API (JPA)** con **Hibernate** como implementación y **MySQL** como base de datos.

El proyecto implementa un patrón de repositorio genérico con operaciones CRUD, baja lógica, y una consulta JPQL personalizada para filtrar productos por categoría, todo accesible desde un menú de consola interactivo.

---

## Tecnologías utilizadas

- Java 17+
- JPA / Hibernate 6.4.4
- MySQL 8
- Gradle
- Lombok

---

## Estructura del proyecto

```
src/main/java/com/tp/jpa/
├── model/
│   ├── Base.java               # Clase base con id, eliminado y createdAt
│   ├── Categoria.java          # Entidad Categoria
│   └── Producto.java           # Entidad Producto
├── repository/
│   ├── BaseRepository.java     # Repositorio genérico con CRUD
│   ├── CategoriaRepository.java
│   └── ProductoRepository.java # Incluye buscarPorCategoria() con JPQL
├── util/
│   └── JPAUtil.java            # Manejo del EntityManagerFactory
└── Main.java                   # Menú de consola con ABM completo
```

---

## Requisitos previos

- Java 17 o superior instalado
- MySQL corriendo en `localhost:3306`
- Gradle instalado (o usar el wrapper incluido `./gradlew`)

---

## Configuración

1. Crear la base de datos en MySQL:
```sql
CREATE DATABASE tp_jpa;
```

2. Configurar las credenciales en `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.user" value="tu_usuario"/>
<property name="jakarta.persistence.jdbc.password" value="tu_contraseña"/>
```

> Hibernate creará las tablas automáticamente al ejecutar el proyecto por primera vez gracias a `hbm2ddl.auto=update`.

---

## Cómo ejecutar

Desde la raíz del proyecto:

```bash
./gradlew run
```

O directamente desde IntelliJ IDEA ejecutando la clase `Main.java`.

---

## Funcionalidades

### Categorías
- **Alta**: ingreso de nombre y descripción, muestra el ID generado
- **Baja lógica**: marca `eliminado = true`, no elimina el registro de la BD
- **Modificación**: permite editar nombre y/o descripción, campo vacío conserva valor anterior
- **Listado**: muestra todas las categorías activas

### Productos
- **Alta**: selección de categoría activa, ingreso de nombre, descripción, precio y stock con validaciones
- **Baja lógica**: marca `eliminado = true`, confirma con el nombre del producto
- **Modificación**: permite editar nombre, precio y stock, campo vacío conserva valor anterior
- **Listado**: muestra todos los productos activos con su categoría

### Reportes
- **Productos por Categoría**: consulta JPQL que filtra productos activos por categoría seleccionada

---

## Decisiones técnicas

- **`BaseRepository<T>`**: repositorio genérico abstracto que evita repetir código de persistencia. Cada método abre y cierra su propio `EntityManager` en un bloque `finally`.
- **Baja lógica**: ningún registro se elimina físicamente de la base de datos. Se utiliza el campo `eliminado = true` para ocultarlo de los listados.
- **`@PrePersist`**: se utiliza para setear automáticamente `createdAt` y `eliminado = false` al momento de persistir por primera vez.
- **JPQL tipada**: `buscarPorCategoria()` usa `TypedQuery<Producto>` con parámetro nombrado `:categoriaId` para evitar casteos manuales y SQL injection.

---

## Autor

Benjamín Ataide - Programación III - UTN
