package com.tp.jpa;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductoRepository productoRepository = new ProductoRepository();
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        boolean condicion = true;
        Scanner sc = new Scanner(System.in);
        while (condicion) {


            System.out.println("------- INICIO ------- \n" +
                    "1 - Categoria \n" +
                    "2 - Productos \n" +
                    "3 - Reportes  \n" +
                    "4 - Salir\n");

            String opcion = sc.nextLine();

            if (opcion.equals("1")) {

                System.out.println("------ Categoria ------ \n" +
                        "1 - Alta Categoria \n" +
                        "2 - Baja Categoria \n" +
                        "3 - Modificar Categoria \n" +
                        "4 - Listado");
                String opcion2 = sc.nextLine();
                if (opcion2.equals("1")) {
                    System.out.println("Nombre: ");
                    String nombreCategoria = sc.nextLine();
                    if (nombreCategoria.isBlank()) {
                        System.out.println("Vuelve a intentarlo");
                        System.out.println("Ingrese el nombre del categoria: ");
                        nombreCategoria = sc.nextLine();
                    }
                    System.out.println("Descripcion: ");
                    String descripcionCategoria = sc.nextLine();
                    Categoria categoria = new Categoria();
                    categoria.setNombre(nombreCategoria);
                    categoria.setDescripcion(descripcionCategoria);

                    Categoria guardada = categoriaRepository.guardar(categoria);

                    System.out.println("ID Generado: " + guardada.getId());
                } else if (opcion2.equals("2")) {

                    System.out.println("Ingrese el ID: ");
                    Long idCategoria = Long.parseLong(sc.nextLine());

                    Optional<Categoria> cat = categoriaRepository.buscarPorId(idCategoria);
                    if (cat.isPresent()) {
                        categoriaRepository.eliminarLogico(idCategoria);
                        System.out.println("Categoría '" + cat.get().getNombre() + "' dada de baja correctamente.");
                    } else {
                        System.out.println("Error: no existe categoría con ID " + idCategoria);
                    }

                } else if (opcion2.equals("3")) {

                    System.out.println("Listado de Categorías:");
                    for (Categoria c : categoriaRepository.listarActivos()) {
                        System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " | " + c.getDescripcion());
                    }

                    System.out.println("Ingrese el ID a Modificar: ");
                    Long idCategoria = Long.parseLong(sc.nextLine());

                    Optional<Categoria> cat = categoriaRepository.buscarPorId(idCategoria);
                    if (cat.isPresent()) {
                        Categoria categoria = cat.get();
                        System.out.println("Nombre actual: " + categoria.getNombre());
                        System.out.println("Ingrese el nuevo nombre (o deje en blanco para mantener): ");
                        String nuevoNombre = sc.nextLine();
                        if (!nuevoNombre.isBlank()) {
                            categoria.setNombre(nuevoNombre);
                        }

                        System.out.println("Descripción actual: " + categoria.getDescripcion());
                        System.out.println("Ingrese la nueva descripción (o deje en blanco para mantener): ");
                        String nuevaDescripcion = sc.nextLine();
                        if (!nuevaDescripcion.isBlank()) {
                            categoria.setDescripcion(nuevaDescripcion);
                        }

                        categoriaRepository.guardar(categoria);
                        System.out.println("Categoría '" + categoria.getNombre() + "' modificada correctamente.");
                    } else {
                        System.out.println("Error: no existe categoría con ID " + idCategoria);
                    }

                } else if (opcion2.equals("4")) {
                    System.out.println("Listado de Categorías:");
                    for (Categoria c : categoriaRepository.listarActivos()) {
                        System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " | " + c.getDescripcion());
                    }
                }


            } else if (opcion.equals("2")) {
                System.out.println("------ Productos ------ \n" +
                        "1 - Alta Producto \n" +
                        "2 - Baja Producto \n" +
                        "3 - Modificar Producto \n" +
                        "4 - Listado");
                String opcion2 = sc.nextLine();
                if (opcion2.equals("1")) {
                    System.out.println("Categorias Activas: ");
                    for (Categoria c : categoriaRepository.listarActivos()) {
                        System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " | " + c.getDescripcion());
                    }

                    System.out.println("Ingrese el ID de la categoría: ");
                    Long idCategoria = Long.parseLong(sc.nextLine());
                    Optional<Categoria> cat = categoriaRepository.buscarPorId(idCategoria);
                    if (cat.isEmpty()) {
                        System.out.println("Error: no existe categoría con ID " + idCategoria);
                    } else {

                        System.out.println("Ingrese el nombre: ");
                        String nombreProducto = sc.nextLine();

                        if (nombreProducto.isBlank()) {
                            System.out.println("Vuelve a intentarlo");
                            System.out.println("Ingrese el nombre del producto: ");
                            nombreProducto = sc.nextLine();
                        }

                        System.out.println("Ingrese el Precio: ");
                        double precioProducto = Double.parseDouble(sc.nextLine());
                        if (precioProducto <= 0) {
                            System.out.println("Precio negativo, ingresa nuevamente: ");
                            precioProducto = Double.parseDouble(sc.nextLine());
                        }

                        System.out.println("Ingrese la descripcion: ");
                        String descripcionProducto = sc.nextLine();

                        System.out.println("Ingrese el Stock: ");
                        int stockProducto = Integer.parseInt(sc.nextLine());
                        if (stockProducto < 0) {
                            System.out.println("Stock negativo, ingresa nuevamente: ");
                            stockProducto = Integer.parseInt(sc.nextLine());
                        }


                        Producto producto = Producto.builder()
                                .nombre(nombreProducto)
                                .precio(precioProducto)
                                .descripcion(descripcionProducto)
                                .stock(stockProducto)
                                .categoria(cat.get())
                                .build();

                        Producto producoNuevo = productoRepository.guardar(producto);
                        System.out.println("Producto guardado - ID: " + producoNuevo.getId() + " | Categoría: " + cat.get().getNombre());

                    }
                } else if (opcion2.equals("2")) {

                    System.out.println("Ingrese el ID: ");
                    Long idProducto = Long.parseLong(sc.nextLine());

                    Optional<Producto> pro = productoRepository.buscarPorId(idProducto);
                    if (pro.isPresent()) {
                        productoRepository.eliminarLogico(idProducto);
                        System.out.println("Producto '" + pro.get().getNombre() + "' dada de baja correctamente.");
                    } else {
                        System.out.println("Error: no existe producto con ID " + idProducto);
                    }


                } else if (opcion2.equals("3")) {


                    System.out.println("Listado de Productos:");
                    for (Producto p : productoRepository.listarActivos()) {
                        System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | " + p.getPrecio() + " | " + p.getDescripcion() + " | " + p.getStock());
                    }

                    System.out.println("Ingrese el ID: ");
                    Long idProducto = Long.parseLong(sc.nextLine());

                    Optional<Producto> producto = productoRepository.buscarPorId(idProducto);
                    if (producto.isPresent()) {
                        System.out.println("Nuevo Nombre: ");
                        String nombreProducto = sc.nextLine();
                        if (!nombreProducto.isBlank()) {
                            producto.get().setNombre(nombreProducto);
                        }

                        System.out.println("Nuevo Precio (actual: " + producto.get().getPrecio() + ", Enter para mantener): ");
                        String precioStr = sc.nextLine();
                        if (!precioStr.isBlank()) {
                            double nuevoPrecio = Double.parseDouble(precioStr);
                            if (nuevoPrecio > 0) producto.get().setPrecio(nuevoPrecio);
                            else System.out.println("Precio inválido, se mantiene el anterior.");
                        }

                        System.out.println("Nuevo Stock (actual: " + producto.get().getStock() + ", Enter para mantener): ");
                        String stockStr = sc.nextLine();
                        if (!stockStr.isBlank()) {
                            int nuevoStock = Integer.parseInt(stockStr);
                            if (nuevoStock >= 0) producto.get().setStock(nuevoStock);
                            else System.out.println("Stock inválido, se mantiene el anterior.");
                        }


                        Producto p = producto.get();
                        productoRepository.guardar(p);
                        System.out.println("Producto '" + p.getNombre() + "' modificado correctamente.");

                    } else {
                        System.out.println("Error: no existe producto con ID " + idProducto);
                    }
                } else if (opcion2.equals("4")) {
                    System.out.println("Listado de Productos:");
                    for (Producto p : productoRepository.listarActivos()) {
                        System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | $" + p.getPrecio() + " | Stock: " + p.getStock() + " | Categoría: " + p.getCategoria().getNombre());
                    }
                } else if (opcion2.equals("5")) {
                    System.out.println("Saliendo.....");
                    break;

                }

            } else if (opcion.equals("3")) {
                System.out.println("----- Reportes -----\n" +
                        "1 - Productos por Categoria\n" +
                        "2 - Volver");
                String opcion2 = sc.nextLine();

                if (opcion2.equals("1")) {
                    // listar categorias activas
                    System.out.println("Categorias activas:");
                    for (Categoria c : categoriaRepository.listarActivos()) {
                        System.out.println("ID: " + c.getId() + " | " + c.getNombre());
                    }

                    // pedir ID de categoria
                    System.out.println("Ingrese el ID de la categoria: ");
                    Long idCategoria = Long.parseLong(sc.nextLine());

                    // llamar buscarPorCategoria
                    List<Producto> productos = productoRepository.buscarPorCategoria(idCategoria);

                    // mostrar resultados
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos activos en esa categoria.");
                    } else {
                        for (Producto p : productos) {
                            System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | $" + p.getPrecio() + " | Stock: " + p.getStock());
                        }
                    }
                }

            } else if (opcion.equals("4")) {
                System.out.println("Saliendo....");
                condicion = false;

            }

        }

    }
}

