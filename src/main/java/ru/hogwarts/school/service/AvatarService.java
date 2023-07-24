package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    //private final StudentRepository studentRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        //this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.findStudentById(studentId);
        //путь к папке где аватар и берем расширение файла(т е после ".")
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        //проверка папки по указанному пути и если нет то создаст
        Files.createDirectories(filePath.getParent());
        //удаление старой версии файла если есть
        Files.deleteIfExists(filePath);


        try (
                //открытие входного потока
                InputStream is = avatarFile.getInputStream();
                //создание файла для записи
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW );
                //расширяем поток с размером 1024
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                //запись
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            //передача данных + закрытие потока
            bis.transferTo(bos);
        }
        //заполняем обьект
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //переформатируем в нужный размер исходный файл
        avatar.setPreview(generateImagePreview(filePath));
        //сохраняем в БД
        avatarRepository.save(avatar);
    }
    //поиск , если нет то создаем
    public Avatar findAvatar(Long studentId){
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    private byte[] generateImagePreview(Path filePath) throws IOException{
        try(InputStream is = Files.newInputStream(filePath);
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage preview = new BufferedImage(100,height,image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image,0,0,100,height,null);
            graphics2D.dispose();

            ImageIO.write(preview,getExtensions(filePath.getFileName().toString()),baos);
              return baos.toByteArray();
        }

    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
