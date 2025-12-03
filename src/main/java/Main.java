import service.Estudiantes;
import service.Estudianteservice;
import service.EstudianteServiceImplementService;
import service.Notas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
/*
Autor: Dilan Salazar y Alexis González
Fecha: 28/11/2025
Descripción: Clase encargada de consumir el servidor en formato xml mediante la estructura SOAP
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // --- Inicialización de lector y servicio remoto ---
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Estudianteservice service = new EstudianteServiceImplementService().getEstudianteServiceImplementPort();

        Long id, idBuscar;
        double n1, n2, n3, examen;
        String nombre, apellido, correo, direccion, telefono;
        int opcion;

        // --- Menú principal del sistema ---
        do {
            System.out.println("----- MENÚ DE OPCIONES -----");
            System.out.println("1. Registrar Estudiante");
            System.out.println("2. Calcular Notas");
            System.out.println("3. Listar Estudiantes");
            System.out.println("4. Buscar Estudiante por ID");
            System.out.println("5. Obtener Notas por ID de Estudiante");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            // --- Control de opciones del menú ---
            switch (opcion) {

                // --- Registro de estudiantes ---
                case 1:
                    System.out.println("----- REGISTRAR ESTUDIANTE -----");
                    System.out.print("Nombre: ");
                    nombre = br.readLine();
                    System.out.print("Apellido: ");
                    apellido = br.readLine();
                    System.out.print("Correo: ");
                    correo = br.readLine();
                    System.out.print("Dirección: ");
                    direccion = br.readLine();
                    System.out.print("Teléfono: ");
                    telefono = br.readLine();

                    Estudiantes estudiante = new Estudiantes();
                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setCorreo(correo);
                    estudiante.setDireccion(direccion);
                    estudiante.setTelefono(telefono);

                    Estudiantes registrado = service.estudiante(estudiante);
                    System.out.println("Estudiante registrado con ID: " + registrado.getIdEstudiante());
                    break;

                // --- Cálculo y registro de notas ---
                case 2:
                    System.out.println("----- CALCULAR NOTAS -----");
                    System.out.print("ID del Estudiante: ");
                    id = Long.parseLong(br.readLine());
                    System.out.print("Nota 1: ");
                    n1 = Double.parseDouble(br.readLine());
                    System.out.print("Nota 2: ");
                    n2 = Double.parseDouble(br.readLine());
                    System.out.print("Nota 3: ");
                    n3 = Double.parseDouble(br.readLine());
                    System.out.print("Examen: ");
                    examen = Double.parseDouble(br.readLine());

                    Notas notasCalculadas = service.calcularNotas(id, n1, n2, n3, examen);
                    if (notasCalculadas != null) {
                        System.out.println("Promedio: " + notasCalculadas.getPromedio());
                        System.out.println("Estado: " + notasCalculadas.getEstado());
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;

                // --- Listado general de estudiantes con notas ---
                case 3:
                    System.out.println("----- LISTADO DE ESTUDIANTES -----");
                    List<Estudiantes> lista = service.listar();

                    for (Estudiantes e : lista) {
                        System.out.println("ID: " + e.getIdEstudiante());
                        System.out.println("Nombre: " + e.getNombre());
                        System.out.println("Apellido: " + e.getApellido());
                        System.out.println("Correo: " + e.getCorreo());
                        System.out.println("Dirección: " + e.getDireccion());
                        System.out.println("Teléfono: " + e.getTelefono());

                        Notas n = e.getNotas();
                        if (n != null) {
                            System.out.println("  > Promedio: " + n.getPromedio());
                            System.out.println("  > Estado: " + n.getEstado());
                        } else {
                            System.out.println("  > Notas no calculadas.");
                        }
                        System.out.println("--------------------");
                    }
                    break;

                // --- Búsqueda de estudiante por ID ---
                case 4:
                    System.out.println("----- BUSCAR POR ID -----");
                    System.out.print("ID a buscar: ");
                    idBuscar = Long.parseLong(br.readLine());

                    Estudiantes estudianteEncontrado = service.buscarPorId(idBuscar);
                    if (estudianteEncontrado != null) {
                        System.out.println("Estudiante encontrado:");
                        System.out.println("ID: " + estudianteEncontrado.getIdEstudiante());
                        System.out.println("Nombre: " + estudianteEncontrado.getNombre());
                        System.out.println("Apellido: " + estudianteEncontrado.getApellido());
                        System.out.println("Correo: " + estudianteEncontrado.getCorreo());
                        System.out.println("Dirección: " + estudianteEncontrado.getDireccion());
                        System.out.println("Teléfono: " + estudianteEncontrado.getTelefono());
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;

                // --- Obtención de notas mediante ID ---
                case 5:
                    System.out.println("----- OBTENER NOTAS POR ID -----");
                    System.out.print("ID del Estudiante: ");
                    idBuscar = Long.parseLong(br.readLine());

                    Notas notasObtenidas = service.obtenerNotas(idBuscar);
                    if (notasObtenidas != null) {
                        System.out.println("Notas encontradas:");
                        System.out.println("Nota 1: " + notasObtenidas.getNota1());
                        System.out.println("Nota 2: " + notasObtenidas.getNota2());
                        System.out.println("Nota 3: " + notasObtenidas.getNota3());
                        System.out.println("Examen: " + notasObtenidas.getExamen());
                        System.out.println("Promedio: " + notasObtenidas.getPromedio());
                        System.out.println("Estado: " + notasObtenidas.getEstado());
                    } else {
                        System.out.println("Notas no encontradas para el ID.");
                    }
                    break;

                // --- Salida del sistema ---
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;

                // --- Opción inválida ---
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 6);
    }
}
