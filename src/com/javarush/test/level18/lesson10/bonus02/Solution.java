package com.javarush.test.level18.lesson10.bonus02;

/* Прайсы
CrUD для таблицы внутри файла
Считать с консоли имя файла для операций CrUD
Программа запускается со следующим набором параметров:
-c productName price quantity
Значения параметров:
где id - 8 символов
productName - название товара, 30 chars (60 bytes)
price - цена, 8 символов
quantity - количество, 4 символа
-c  - добавляет товар с заданными параметрами в конец файла, генерирует id самостоятельно, инкрементируя максимальный id

В файле данные хранятся в следующей последовательности (без разделяющих пробелов):
id productName price quantity
Данные дополнены пробелами до их длины

Пример:
19846   Шорты пляжные синие           159.00  12
198478  Шорты пляжные черные с рисунко173.00  17
19847983Куртка для сноубордистов, разм10173.991234
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Solution {

    static class Product{
        int id;
        String productName;
        float price;
        int quantity;

        public Product(int id, String productName, float price, int quantity)
        {
            this.id = id;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }


        @Override
        public String toString()
        {
            return String.format(Locale.ENGLISH, "%-8d%-30.30s%-8.2f%-4d", id, productName, price, quantity);
        }
    }

    static class BD{

        private String filename;
        private ArrayList<Product> list = new ArrayList<Product>();
        private int maxid;

        public BD(String filename) throws IOException
        {
            this.filename = filename;
            readFile();
        }

        void readFile() throws IOException
        {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            String line;
            int id;
            String productname;
            Float price;
            int quantity;

            while ((line = file.readLine())!= null){

                if (line.isEmpty())
                    continue;

                id = Integer.parseInt(line.substring(0, 8).trim());
                productname = line.substring(8, 38).trim();
                price = Float.parseFloat(line.substring(38, 46).trim());
                quantity = Integer.parseInt(line.substring(46, 50).trim());
                list.add(new Product(id,productname,price,quantity));
                if (id > maxid) maxid = id;
            }

//            for (Product item : list)
//                System.out.println(item);

            file.close();
        }

        void addProduct(String[] args) throws IOException
        {

            FileOutputStream f = new FileOutputStream(filename);

            for (Product item : list)
                f.write((item.toString() + "\n").getBytes());


            StringBuilder name = new StringBuilder();

            for (int i = 1; i < args.length - 2; i++)
            {
                name.append(args[i]+" ");
            }

            String price = args[args.length - 2];
            price = price.replace(",",".");


            f.write(new Product(maxid + 1, name.toString(), Float.parseFloat(price), Integer.parseInt(args[args.length - 1])).toString().getBytes());
            f.close();
        }


    }


    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


//        BD bd = new BD("d:\\bd.txt");
        BD bd = new BD(reader.readLine());

        if (args[0].equals("-c") && args.length >= 4)
            bd.addProduct(args);


    }
}
