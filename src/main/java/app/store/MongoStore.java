package app.store;

import com.mongodb.client.*;
import org.bson.Document;
import app.model.Student;
import com.google.gson.Gson;
import java.util.Random; // Rastgele seçim için import eklendi
import static com.mongodb.client.model.Filters.eq;

public class MongoStore {
    private static MongoCollection<Document> collection;
    private static final Gson gson = new Gson();

    public static void init() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("nosqllab");
        collection = database.getCollection("ogrenciler");
        collection.drop(); // Önceki verileri temizle

        // Rastgele veriler için listeler
        String[] adlar = {"Ahmet", "Mehmet", "Ayşe", "Fatma", "Mustafa", "Emine", "Ali", "Zeynep", "Hasan", "Meryem"};
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Yıldız", "Arslan", "Doğan", "Kılıç", "Çetin"};
        String[] bolumler = {"Bilgisayar Mühendisliği", "Makine Mühendisliği", "Elektrik-Elektronik Mühendisliği", "İnşaat Mühendisliği", "Endüstri Mühendisliği", "Tıp", "Hukuk", "İşletme"};
        
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            
            // Rastgele ad, soyad ve bölüm seç
            String rastgeleAd = adlar[random.nextInt(adlar.length)];
            String rastgeleSoyad = soyadlar[random.nextInt(soyadlar.length)];
            String tamAd = rastgeleAd + " " + rastgeleSoyad;
            String rastgeleBolum = bolumler[random.nextInt(bolumler.length)];
            
            // Öğrenciyi rastgele verilerle yarat
            Student s = new Student(id, tamAd, rastgeleBolum);
            collection.insertOne(Document.parse(gson.toJson(s)));
        }
        System.out.println("MongoStore: 10000 rastgele kayıt eklendi.");
    }

    public static Student get(String id) {
        Document doc = collection.find(eq("student_no", id)).first();
        return doc != null ? gson.fromJson(doc.toJson(), Student.class) : null;
    }
}