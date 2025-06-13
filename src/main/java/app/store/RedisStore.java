package app.store;

import redis.clients.jedis.Jedis;
import app.model.Student;
import com.google.gson.Gson;
import java.util.Random;

public class RedisStore {
    private static Jedis jedis;
    private static final Gson gson = new Gson();

    public static void init() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushDB();

        // Rastgele veriler için listeler
        String[] adlar = {"Ahmet", "Mehmet", "Ayşe", "Fatma", "Mustafa", "Emine", "Ali", "Zeynep", "Hasan", "Meryem"};
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Yıldız", "Arslan", "Doğan", "Kılıç", "Çetin"};
        // YENİ: Rastgele bölümler için liste eklendi
        String[] bolumler = {"Bilgisayar Mühendisliği", "Makine Mühendisliği", "Elektrik-Elektronik Mühendisliği", "İnşaat Mühendisliği", "Endüstri Mühendisliği", "Tıp", "Hukuk", "İşletme"};
        
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            
            // Rastgele bir ad ve soyad seç
            String rastgeleAd = adlar[random.nextInt(adlar.length)];
            String rastgeleSoyad = soyadlar[random.nextInt(soyadlar.length)];
            String tamAd = rastgeleAd + " " + rastgeleSoyad;

            // YENİ: Rastgele bir bölüm seç
            String rastgeleBolum = bolumler[random.nextInt(bolumler.length)];

            // Öğrenciyi rastgele oluşturulan isim ve bölümle yarat
            Student s = new Student(id, tamAd, rastgeleBolum);
            jedis.set(id, gson.toJson(s));
        }
        System.out.println("RedisStore: 10000 rastgele isimli ve bölümlü kayıt eklendi.");
    }

    public static Student get(String id) {
        String json = jedis.get(id);
        return gson.fromJson(json, Student.class);
    }
}