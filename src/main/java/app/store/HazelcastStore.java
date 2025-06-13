package app.store;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import app.model.Student;
import java.util.Random; // Rastgele seçim için import eklendi

public class HazelcastStore {
    private static IMap<String, Student> map;

    public static void init() {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        map = hz.getMap("ogrenciler");
        map.clear(); // Önceki verileri temizle

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
            map.put(id, s);
        }
        System.out.println("HazelcastStore: 10000 rastgele kayıt eklendi.");
    }

    public static Student get(String id) {
        return map.get(id);
    }
}