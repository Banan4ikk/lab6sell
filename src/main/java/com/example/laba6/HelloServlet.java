package com.example.laba6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "changeServlet", value = "/change")
public class HelloServlet extends HttpServlet {

    public ArrayList<String> changeData(String fileName) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        String[] data = text.split("\n");
        ArrayList<String> changed = new ArrayList<>();
        for (String item : data) {
            StringBuilder sb = new StringBuilder();
            sb.append(item);
            sb.insert(0, "   ");
            item = sb.toString();
            changed.add(item);
        }
        ArrayList<String> arrPhrases = new ArrayList<>(); // Коллекция подстрок(фраз)
        for (String string : changed) {
            String[] arrWords = string.split(" ");  // Массив слов
            StringBuilder stringBuffer = new StringBuilder(); // Буфер для накопления фразы
            int cnt = 0;   // Счётчик, чтобы не выйти за пределы 80 символов
            int index = 0; // Индекс элемента в массиве arrWords. Сразу указывает на первый элемент
            int length = arrWords.length; // Общее количество слов (длина массива)

            while (index != length) {  // Пока не дойдём до последнего элемента
                if (cnt + arrWords[index].length() <= 80) { // Если текущая фраза + текущее слово в массиве arrWords не превышает 30
                    cnt += arrWords[index].length() + 1;  // То увеличиваем счётчик
                    stringBuffer.append(arrWords[index]).append(" ");  // и накапливаем фразу
                    index++;   // Переходим на следующее слово
                } else {   // Фраза превысит лимит в 80 символов
                    arrPhrases.add(stringBuffer.toString());   // Добавляем фразу в коллекцию
                    stringBuffer = new StringBuilder();
                    cnt = 0;                                   // Обнуляем счётчик
                }
            }
            if (stringBuffer.length() > 0) {
                arrPhrases.add(stringBuffer.toString());       // Забираем "остатки"
            }
        }

        return arrPhrases;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<String> changedFile = changeData("C:/Users/Konstantin/IdeaProjects/laba6/src/main/java/com/example/laba6/data.txt");

        String path = "C:/Users/Konstantin/IdeaProjects/laba6/src/main/java/com/example/laba6/changed.txt";
        for (String item : changedFile) {
            System.out.println(item);
        }
        try(FileWriter fw = new FileWriter(path)){
            for (String item : changedFile) {
                fw.write(item + "\n");
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }

}