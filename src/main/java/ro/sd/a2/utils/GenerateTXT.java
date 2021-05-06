package ro.sd.a2.utils;

import ro.sd.a2.entity.Book;

import java.io.FileWriter;
import java.util.List;

public class GenerateTXT implements Strategy {

    public static int countTXT = 1;

    @Override
    public void generateDocument(List<Book> books) {

        try {
            String name = "txt_inventory" + countTXT + ".txt";
            countTXT++;
            FileWriter myWriter = new FileWriter(name);

            myWriter.write("Inventory:\n");
            for(Book book : books){
                String price = "";
                if(book.getPromotionPrice() > 0) {
                    price = book.getPromotionPrice() + "";
                }
                else{
                    price ="None";
                }

                StringBuilder builder = new StringBuilder().append("Id: ").append(book.getId() + "\n")
                        .append("Title: ").append(book.getTitle() + "\n")
                        .append("Author: ").append(book.getAuthor() + "\n")
                        .append("Description: ").append(book.getDescription() + "\n")
                        .append("Price: ").append(book.getPrice() + "\n")
                        .append("Promotion Price: ").append(price + "\n")
                        .append("Genre: ").append(book.getGenre().getType() + "\n" + "\n");
                myWriter.write(builder.toString());
            }

            myWriter.close();


        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}