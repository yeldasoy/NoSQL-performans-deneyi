NoSQL Mini Proje 
=================================

Bu proje, Redis, Hazelcast ve MongoDB veritabanlarının belirli bir yük altındaki okuma performanslarını karşılaştırmak amacıyla geliştirilmiştir. Proje, her bir veritabanına 10.000 adet öğrenci kaydı ekler ve bu kayıtlara erişim hızlarını ölçmek için testler uygular.

Kullanılan Teknolojiler
=================================

Java 17
Maven: Proje yönetimi ve derleme aracı
SparkJava: Web endpoint'lerini oluşturmak için kullanılan mikro framework
Veritabanları:
Redis (In-memory key-value store)
Hazelcast (In-memory data grid)
MongoDB (NoSQL document database)
Docker: Veritabanı servislerini konteyner olarak çalıştırmak için
WSL (Ubuntu): Performans testlerini (siege, time) çalıştırmak için kullanılan Linux ortamı

Kurulum ve Çalıştırma Adımları
=================================
Bu projenin çalıştırılması, geliştirme ve test ortamlarının WSL (Windows Subsystem for Linux) içinde kurulmasını gerektirir.

1. Ön Gereksinimler

Docker Desktop for Windows'un kurulu olması.
WSL (Ubuntu dağıtımı)'nin Windows üzerinde aktif hale getirilmiş olması (wsl --install).
Docker Desktop ayarlarından WSL Integration'ın Ubuntu için aktif edilmiş olması.
WSL/Ubuntu terminali içine Java 17 ve Maven'ın kurulmuş olması:

```bash
sudo apt update
sudo apt install openjdk-17-jdk maven
```

2. Veritabanlarını Başlatma

Projenin ihtiyaç duyduğu üç veritabanı da Docker konteynerleri olarak çalıştırılmaktadır. Proje ana dizininde bulunan compose.yml dosyası bu işlemi yönetir.

Proje ana dizininde bir WSL/Ubuntu terminali açın.
Aşağıdaki komutla tüm veritabanı servislerini başlatın:

```bash
docker compose up -d
```

3. Projeyi Derleme ve Paketleme

Java projesini, çalıştırılabilir bir .jar dosyası haline getirmek için Maven kullanılır.

WSL/Ubuntu terminalinde proje ana dizinindeyken aşağıdaki komutu çalıştırın:

```bash
mvn clean package
```

4. Java Sunucusunu Başlatma

WSL/Ubuntu terminalinde aşağıdaki komutlardan birisini çalıştırın:

```bash
mvn exec:java -Dexec.mainClass="app.Main"
java -jar target/nosql-spark-lab-1.0-SNAPSHOT.jar
```

Konsolda "Sunucu http://localhost:8080 adresinde başlatıldı" mesajını gördüğünüzde, sunucu testlere hazır demektir. Bu terminali testler boyunca açık bırakın.


Performans Testlerini Çalıştırma
=================================
Testler, yeni bir WSL/Ubuntu terminali üzerinden çalıştırılmalıdır.

Önemli: WSL Ağ Yapılandırması

WSL içinden, Windows üzerinde çalışan Java sunucusuna localhost ile erişirken sorunlar yaşanabilmektedir. Bu nedenle, localhost yerine Windows'un WSL tarafından görünen IP adresini kullanmak en güvenilir yöntemdir.

1. Testleri yapacağınız yeni WSL terminalinde aşağıdaki komutla Windows'un IP adresini öğrenin:

```bash
cat /etc/resolv.conf | grep nameserver | awk '{ print $2 }'
```
2. Aşağıdaki test komutlarında <WINDOWS_IP_ADRESİNİZ> yazan yeri, yukarıdaki komuttan aldığınız IP adresi ile değiştirin.

Siege Testleri
=================================

```bash
# Redis Testi
siege -H "Accept: application/json" -c10 -r100 "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-rd/2025000001" > redis.results

# Hazelcast Testi
siege -H "Accept: application/json" -c10 -r100 "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-hz/2025000001" > hazelcast.results

# MongoDB Testi
siege -H "Accept: application/json" -c10 -r100 "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-mon/2025000001" > mongo.results
```
Koşum Zamanı Testleri
=================================

```bash
# Redis Koşum Zamanı
time seq 1 100 | xargs -P10 -I{} curl -s -o /dev/null "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-rd/2025000001"

# Hazelcast Koşum Zamanı
time seq 1 100 | xargs -P10 -I{} curl -s -o /dev/null "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-hz/2025000001"

# MongoDB Koşum Zamanı
time seq 1 100 | xargs -P10 -I{} curl -s -o /dev/null "http://<WINDOWS_IP_ADRESİNİZ>:8080/nosql-lab-mon/2025000001"
```










