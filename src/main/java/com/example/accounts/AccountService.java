package com.example.accounts;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    public static final String ACCOUNT_SERVICE_ATTRIBUTE = "accountService";

    public AccountService() {
        loginToProfile = new HashMap<>();
        addNewUser(new UserProfile("admin", "admin2", "admin@example.com"));
    }

    public String getUserHomeDir(String login) {
        String targetDir = "C:\\Users\\filemanager\\" + login + "\\";
        File dir = new File(targetDir);

        if (!dir.exists()) {
            try {
                if (!dir.mkdirs()) {
                    // Если не получилось, используем временную директорию
                    String fallbackDir = System.getProperty("java.io.tmpdir") + "filemanager\\" + login + "\\";
                    new File(fallbackDir).mkdirs();
                    System.out.println("Используем временную директорию: " + fallbackDir);
                    return fallbackDir;
                }
            } catch (SecurityException e) {
                System.err.println("Нет прав на создание папки: " + targetDir);
                throw new RuntimeException("Ошибка доступа к файловой системе", e);
            }
        }
        return targetDir;
    }

    public void addNewUser(UserProfile profile) {
        loginToProfile.put(profile.getLogin(), profile);
        createTestFilesStructure(profile.getLogin());
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public boolean checkUser(String login, String password) {
        UserProfile profile = loginToProfile.get(login);
        return profile != null && profile.getPassword().equals(password);
    }

    private void createTestFilesStructure(String login) {
        String userDir = getUserHomeDir(login);
        new File(userDir).mkdirs();

        try {
            // Создаем несколько тестовых папок
            new File(userDir + "Documents").mkdirs();
            new File(userDir + "Images").mkdirs();
            new File(userDir + "Music").mkdirs();

            // Создаем несколько тестовых файлов
            createTestFile(userDir + "readme.txt", "Это тестовый файл readme");
            createTestFile(userDir + "Documents\\notes.txt", "Важные заметки");
            createTestFile(userDir + "Documents\\report.docx", "Это тестовый документ Word");
            createTestFile(userDir + "Images\\photo.jpg", "Тестовое изображение");
            createTestFile(userDir + "Music\\song.mp3", "Тестовая музыка");
        } catch (IOException e) {
            System.err.println("Ошибка при создании тестовых файлов: " + e.getMessage());
        }
    }

    private void createTestFile(String path, String content) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            java.nio.file.Files.write(file.toPath(), content.getBytes());
        }
    }
}
