package org.example;

import java.sql.*;
import java.util.Scanner;

public class ScheduleManagementApp {

    private static String DB_URL = "jdbc:mysql://localhost/laba7";
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "1q2w3e4r5t27";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        showMenu(scanner);

        scanner.close();
        createTablesIfNotExist();

        while (true) {
            System.out.println("1. Добавить запись");
            System.out.println("2. Обновить запись");
            System.out.println("3. Удалить запись");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Считываем символ новой строки после ввода числа

            switch (choice) {
                case 1:
                    addRecord(scanner);
                    break;
                case 2:
                    updateRecord(scanner);
                    break;
                case 3:
                    deleteRecord(scanner);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static void createTablesIfNotExist() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Создание таблицы дисциплин
            String createDisciplinesTable = "CREATE TABLE IF NOT EXISTS disciplines (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createDisciplinesTable);

            // Создание таблицы аудиторий
            String createClassroomsTable = "CREATE TABLE IF NOT EXISTS classrooms (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createClassroomsTable);

            // Создание таблицы групп студентов
            String createStudentGroupsTable = "CREATE TABLE IF NOT EXISTS student_groups (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createStudentGroupsTable);

            // Создание таблицы преподавателей
            String createProfessorsTable = "CREATE TABLE IF NOT EXISTS professors (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "first_name VARCHAR(255) NOT NULL," +
                    "last_name VARCHAR(255) NOT NULL," +
                    "patronymic VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createProfessorsTable);

            System.out.println("Таблицы успешно созданы или уже существуют.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addRecord(Scanner scanner) {
        System.out.println("Выберите тип записи:");
        System.out.println("1. Дисциплина");
        System.out.println("2. Аудитория");
        System.out.println("3. Группа студентов");
        System.out.println("4. Преподаватель");
        System.out.print("Выберите тип записи: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        switch (typeChoice) {
            case 1:
                addDiscipline(scanner);
                break;
            case 2:
                addClassroom(scanner);
                break;
            case 3:
                addStudentGroup(scanner);
                break;
            case 4:
                addProfessor(scanner);
                break;
            default:
                System.out.println("Некорректный выбор. Возврат в главное меню.");
        }
    }

    private static void addDiscipline(Scanner scanner) {
        System.out.print("Введите название дисциплины: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO disciplines (name) VALUES (?)")) {
            statement.setString(1, name);
            statement.executeUpdate();

            System.out.println("Запись успешно добавлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи.");
            e.printStackTrace();
        }
    }

    private static void addClassroom(Scanner scanner) {
        System.out.print("Введите название аудитории: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO classrooms (name) VALUES (?)")) {
            statement.setString(1, name);
            statement.executeUpdate();

            System.out.println("Запись успешно добавлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи.");
            e.printStackTrace();
        }
    }

    private static void addStudentGroup(Scanner scanner) {
        System.out.print("Введите название группы студентов: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO student_groups (name) VALUES (?)")) {
            statement.setString(1, name);
            statement.executeUpdate();

            System.out.println("Запись успешно добавлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи.");
            e.printStackTrace();
        }
    }

    private static void addProfessor(Scanner scanner) {
        System.out.print("Введите фамилию преподавателя: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите имя преподавателя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите отчество преподавателя: ");
        String patronymic = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO professors (first_name, last_name, patronymic) VALUES (?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, patronymic);
            statement.executeUpdate();

            System.out.println("Запись успешно добавлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи.");
            e.printStackTrace();
        }
    }

    private static void updateRecord(Scanner scanner) {
        System.out.println("Выберите тип записи для обновления:");
        System.out.println("1. Дисциплина");
        System.out.println("2. Аудитория");
        System.out.println("3. Группа студентов");
        System.out.println("4. Преподаватель");
        System.out.print("Выберите тип записи: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        switch (typeChoice) {
            case 1:
                updateDiscipline(scanner);
                break;
            case 2:
                updateClassroom(scanner);
                break;
            case 3:
                updateStudentGroup(scanner);
                break;
            case 4:
                updateProfessor(scanner);
                break;
            default:
                System.out.println("Некорректный выбор. Возврат в главное меню.");
        }
    }

    private static void updateDiscipline(Scanner scanner) {
        System.out.print("Введите id дисциплины для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа
        System.out.print("Введите новое название дисциплины: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE disciplines SET name = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении записи.");
            e.printStackTrace();
        }
    }

    private static void updateClassroom(Scanner scanner) {
        System.out.print("Введите id аудитории для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа
        System.out.print("Введите новое название аудитории: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE classrooms SET name = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении записи.");
            e.printStackTrace();
        }
    }

    private static void updateStudentGroup(Scanner scanner) {
        System.out.print("Введите id группы студентов для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа
        System.out.print("Введите новое название группы студентов: ");
        String name = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE student_groups SET name = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении записи.");
            e.printStackTrace();
        }
    }

    private static void updateProfessor(Scanner scanner) {
        System.out.print("Введите id преподавателя для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа
        System.out.print("Введите новую фамилию преподавателя: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите новое имя преподавателя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите новое отчество преподавателя: ");
        String patronymic = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE professors SET first_name = ?, last_name = ?, patronymic = ? WHERE id = ?")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, patronymic);
            statement.setInt(4, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении записи.");
            e.printStackTrace();
        }
    }

    private static void deleteRecord(Scanner scanner) {
        System.out.println("Выберите тип записи для удаления:");
        System.out.println("1. Дисциплина");
        System.out.println("2. Аудитория");
        System.out.println("3. Группа студентов");
        System.out.println("4. Преподаватель");
        System.out.print("Выберите тип записи: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        switch (typeChoice) {
            case 1:
                deleteDiscipline(scanner);
                break;
            case 2:
                deleteClassroom(scanner);
                break;
            case 3:
                deleteStudentGroup(scanner);
                break;
            case 4:
                deleteProfessor(scanner);
                break;
            default:
                System.out.println("Некорректный выбор. Возврат в главное меню.");
        }
    }

    private static void deleteDiscipline(Scanner scanner) {
        System.out.print("Введите id дисциплины для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM disciplines WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи.");
            e.printStackTrace();
        }
    }

    private static void deleteClassroom(Scanner scanner) {
        System.out.print("Введите id аудитории для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM classroomsWHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи.");
            e.printStackTrace();
        }
    }

    private static void deleteStudentGroup(Scanner scanner) {
        System.out.print("Введите id группы студентов для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM student_groups WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи.");
            e.printStackTrace();
        }
    }

    private static void deleteProfessor(Scanner scanner) {
        System.out.print("Введите id преподавателя для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки после ввода числа

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM professors WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным id не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи.");
            e.printStackTrace();
        }
    }

    private static void showMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Выберите операцию:");
            System.out.println("1. Добавить запись");
            System.out.println("2. Обновить запись");
            System.out.println("3. Удалить запись");
            System.out.println("4. Выйти");
            System.out.print("Выберите операцию: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Считываем символ новой строки после ввода числа

            switch (choice) {
                case 1:
                    addRecord(scanner);
                    break;
                case 2:
                    updateRecord(scanner);
                    break;
                case 3:
                    deleteRecord(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
            }
        }
    }
}