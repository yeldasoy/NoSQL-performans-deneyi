package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.model.Student;
import app.store.HazelcastStore;
import app.store.MongoStore;
import app.store.RedisStore;

public class Main {
    public static void main(String[] args) {
        port(8080);
        Gson gson = new Gson();

        System.out.println("Veritabanları başlatılıyor...");
        try {
            RedisStore.init();
            HazelcastStore.init();
            MongoStore.init();
        } catch (Exception e) {
            System.err.println("Veritabanı başlatma hatası: " + e.getMessage());
            e.printStackTrace();
            stop();
            return;
        }
        System.out.println("Veritabanları başarıyla yüklendi.");

        // --- ENDPOINTS (BASİT VE STANDART URL YAPISI) ---

        get("/nosql-lab-rd/:id", (req, res) -> {
            res.type("application/json");
            Student student = RedisStore.get(req.params(":id"));
            if (student == null) res.status(404);
            return gson.toJson(student);
        });

        get("/nosql-lab-hz/:id", (req, res) -> {
            res.type("application/json");
            Student student = HazelcastStore.get(req.params(":id"));
            if (student == null) res.status(404);
            return gson.toJson(student);
        });

        get("/nosql-lab-mon/:id", (req, res) -> {
            res.type("application/json");
            Student student = MongoStore.get(req.params(":id"));
            if (student == null) res.status(404);
            return gson.toJson(student);
        });

        System.out.println("\nSunucu http://localhost:8080 adresinde başlatıldı.");
        System.out.println("Kullanılabilir endpointler (örnek):");
        System.out.println("GET http://localhost:8080/nosql-lab-rd/2025000001");
        System.out.println("GET http://localhost:8080/nosql-lab-hz/2025000001");
        System.out.println("GET http://localhost:8080/nosql-lab-mon/2025000001");
    }
}