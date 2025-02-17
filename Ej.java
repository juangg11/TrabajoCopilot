import java.io.*;
import java.util.*;

class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class Ej {
    private static final String FILE_NAME = "agenda.dat";
    private static List<Contacto> agenda = new ArrayList<>();

    public static void main(String[] args) {
        cargarAgenda();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nGestión de Agenda de Teléfonos");
            System.out.println("1. Incluir nueva entrada");
            System.out.println("2. Eliminar contacto por nombre");
            System.out.println("3. Mostrar agenda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    incluirNuevaEntrada(scanner);
                    break;
                case 2:
                    eliminarContacto(scanner);
                    break;
                case 3:
                    mostrarAgenda();
                    break;
                case 4:
                    guardarAgenda();
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static void incluirNuevaEntrada(Scanner scanner) {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();
        agenda.add(new Contacto(nombre, telefono));
        System.out.println("Contacto añadido.");
    }

    private static void eliminarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;
        Iterator<Contacto> iterator = agenda.iterator();
        while (iterator.hasNext()) {
            Contacto contacto = iterator.next();
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                iterator.remove();
                encontrado = true;
                System.out.println("Contacto eliminado.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Contacto no encontrado.");
        }
    }

    private static void mostrarAgenda() {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("Agenda de contactos:");
            for (Contacto contacto : agenda) {
                System.out.println(contacto);
            }
        }
    }

    private static void cargarAgenda() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            agenda = (List<Contacto>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de la agenda. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
        }
    }

    private static void guardarAgenda() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(agenda);
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }
}