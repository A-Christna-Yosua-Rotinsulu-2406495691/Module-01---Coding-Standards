<h1 style="color:red"> Module 1 - Coding Standard </h1>
<h4 style="color:yellow">
    by Christna Yosua Rotinsulu - 2406495691
</h4>

<h3 style="color:pink">
    Refleksi 1 : Prinsip Clean Code dan Praktik Secure Coding
</h3>
<hr>

<p>
Prinsip clean code yang telah diterapkan:

1. <b style="color:red">Single Responsibility Principle</b>, di mana setiap kelas dalam aplikasi mempunyai satu tanggung jawab yang jelas, seperti:
- `Product.java` yang berperan untuk merepresentasikan model data produk
- `ProductRepository.java` yang menangani antarmuka logika bisnis
- `ProductServiceImpl.java` yang mengimplementasikan logika bisnis
- `ProductController.java` yang akan mengelola permintaan dan respons HTTP
- `HomeController.java` yang akan menangani routing di halaman utama

Pemisahan tersebut diharapkan dapat memastikan perubahan di satu lapisan tidak mempengaruhi lapisan lain dalam aplikasi sederhana yang saya buat.

2. <b style="color:red">Konvensi Penamaan yang Bermakna</b>, di mana saya menggunakan penamaan deskriptif dan konsisten:
- Kelas: `Product`, `ProductRepository`, `ProductService` - jelas tujuan fungsinya
- Metode: `create()`, `findAll()`, `findById()`, `update()`, `delete()` - berbasis kata kerja
- Variabel: `productName`, `productQuantity`, `productData` - nama yang menjelaskan diri
- Package: Diatur berdasarkan lapisan (`controller`, `model`, `repository`, `service`)

3. <b style="color:red">Format Kode yang Konsisten</b>, di mana kode yang saya buat mengikuti praktik pemformatan yang konsisten:
- Penempatan kurung dan spasi yang konsisten
- Pengelompokkan logis metode terkait
- Organisasi import standar

4. <b style="color:red">Dependency Injection</b>, di mana memanfaatkan pola dependency injection Spring:

    ```java!
    @Autowired
    private ProductRepository productRepository;
    ```

   Kode ini akan mempromosikan loose coupling dan membuat kode lebih mudah di-test

5. <b style="color:red">Implementasi Pola MVC</b>, di mana aplikasi saya mengikuti pola Spring MVC sesuai dengan yang ada di panduan, seperti model, view <i>(template TymeLeaf)</i>, dan controller (`ProductController`)
</p>

<h3 style="color:yellow">
    Praktik Secure Coding yang Diterapkan
</h3>

<p>

1. <b>Validasi Input (Dasar)</b>:

- Client-side: validasi form HTML5 dengan attribute `required` dan `min`
- Server Side: pemeriksaan null dasar di metode repository

2. <b>Proteksi XSS</b>:

- Thymeleaf otomatis meng-escape konten HTML:
    ```html
    <td th:text="${product.productName}">Nama Produk</td>
    ```

3. <b>Proteksi CSRF (Default Spring Boot)</b>:
   Spring Security menyediakan proteksi CSRF secara default
</p>

<h3 style="color:yellow">
    Area Kerentanan yang Ditemukan
</h3>

<p>
Selama mengerjakan tutorial modul 1 ini, saya menemukan beberapa area yang belum saya implementasikan secure code sehingga menimbulkan kerentanan untuk aplikasi yang telah saya buat. Beberapa area yang saya maksud adalah sebagai berikut:

1. <b style="color:cyan">Kurangnya Validasi Input</b> pada `ProductRepository.java`, khususnya pada method `create()` di mana saya menerima semua input yang dimasukkan oleh user tanpa perlu melakukan validasi ataupun sanitasi lebih lanjut.

2. <b style="color:cyan">Penanganan Error</b> yang saya lakukan belum sepenuhnya "baik" untuk umpan balik pengguna, di mana saya mengembalikan `null` pada method `findById()` sehingga ketika product yang diberikan action oleh pengguna dan tidak ditemukan di database akan memberikan sebuah kegagalan secara diam-diam. Walaupun, dalam praktik yang telah saya lakukan, belum ada kasus tertentu yang dapat menyebabkan "error" tersebut.

3. <b style="color:cyan">Tidak Ada Impelementasi Logging</b> di mana hal ini penting untuk memudahkan proses debugging atau monitoring lebih lanjut sebagaimana telah diperkenalkan dalam pertemuan kelas sebelumnya.
</p>

<hr>

<h3 style="color:cyan">
    Refleksi 2: Wawasan Testing dan Kualitas Kode
</h3>

<h3 style="color:yellow">
    1. Pengalaman Unit Testing
</h3>

<hr>

<p>
Setelah melakukan unit testing, saya menyadari beberapa aspek penting dan alasan bagian ini menjadi salah satu alur utama dalam dunia <i>software engineering</i>. 

1. <b style="color:red">Berapa banyak unit test untuk setiap Class?</b>
   Menurut pendapat saya, tidak ada jumlah tetap atau jumlah acuan tertentu untuk melakukan hal tersebut, tetapi pedoman yang baik adalah melakukan test untuk semua <b style="color:yellow">metode public, jalur eksekusi berbeda (if-else atau loop), edge cases (nilai null atau string kosong), dan kondisi error (apa yang terjadi ketika ada masalah tertentu)</b>. Pada tutorial ini, saya mendapatkan insight baru tentang melakukan unit testing pada scenario yang positif maupun negatif untuk mengetahui apakah kode yang telah saya buat berjalan dengan baik.
2. <b style="color:red">Memastikan test "cukup"</b>. Untuk memastikan hal tersebut, saya menganalisis cakupan kode menggunakan JaCoCo, mutation testing, review test, dan traceability persyaratan.
3. <b style="color:red">Wawasan Cakupan Kode</b>. Cakupan kode yang sebelumnya saya singgung berperan untuk mengukur presentase kode yang dieksekusi oleh test. Namun, terdapat batasan yang saya temukan selama melakukan unit testing, yaitu sebagai berikut:
   a. <b style="color:yellow">Linear Coverage</b> mengukur % baris dieksekusi tanpa menguji kebenaran logika.
   b. <b style="color:yellow">Branch Coverage</b> mengukur % cabang diambil tanpa menguji semua kombinasi input yang ada.
   c. <b style="color:yellow">Path Coverage</b> mengukur % jalur dieksekusi dan bisa menjadi sangat kompleks

Berdasarkan hal tersebut, saya mendapatkan satu kesimpulan penting bahwa 100% cakupan kode belum tentu kode yang saya buat bebas bug. Hal ini dapat disebabkan oleh beberapa faktor:
1. <b style="color:red">Melewatkan masalah integrasi</b> sehingga unit individual mungkin bekerja tetapi bisa jadi gagal bersama
2. <b style="color:red">Tidak Menguji Requirements</b> di mana testing mungkin mengeksekusi kode, tetapi testing tidak memverifikasi kebenaran logika bisnis yang dibuat.
3. <b style="color:red">Rasa Aman Palsu</b> di mana developer mungkin menulis test trivial hanya untuk mencapai target cakupan saja
4. <b style="color:red">Edge cases yang hilang</b> di mana alat cakupan tidak akan tahu edge cases mana yang penting dan krusial untuk ditesting dibandingkan developer itu sendiri
5. <b style="color:red">Masalah Timing/Race Condition</b> di mana bisa jadi terdapat masalah konkurensi yang tidak terdeteksi oleh cakupan
</p>

<h3 style="color:yellow">
    2. Masalah Kualitas Functional Test
</h3>

<hr>

<p>
Pada praktikum kemarin, saya membuat beberapa kesalahan yang sama ketika melakukan functional test, di mana saya menggunakan setup sama di setiap kelas test yang saya gunakan. 

<b style="color:orange">Masalah Clean Code</b>

1. <b>Melanggar Prinsip DRY (Don't Repeat Yourself)</b> di mana saya menggunakan kode setup yang sama diduplikasi di berbagai kelas test sehingga memerlukan pembaruan di banya file
2. <b>Biaya Pemeliharaan Tinggi</b> di mana menambah setup baru memerlukan pembaruan semua kelas test sehingga memungkinkan menimbulkan banyak inkonsistensi
3. <b>Keterbacaan Kurang</b> di mana kelas test akan menjadi berantakan dengan kode setup boilerplate dan logika test penting menjadi lebih sulit ditemukan

Berdasarkan hal tersebut, beberapa rekomendasi perbaikan yang dapat saya berikan adalah sebagai berikut:
1. <b style="color:yellow">Mengurangi Duplikasi Kode</b> di mana kode setup cukup ditulis sekali dan dapat digunakan di mana-mana
2. <b style="color:yellow">Pemeliharaan Lebih Mudah</b> di mana perubahan pada setup mempengaruhi semua test secara konsisten
3. <b style="color:yellow">Keterbacaan Lebih Baik</b> di mana kelas test cukup fokus kepada logika test, bukan setup utamanya
4. <b style="color:yellow">Reusabilitas Lebih Baik</b> di mana menambahkan metode helper umum untuk semua test

</p>

<h1 style="color:red"> Module 2 - CI/CD and Devops </h1>
<h4 style="color:yellow">
    by Christna Yosua Rotinsulu - 2406495691
</h4>

<hr>

<h3 style="color:yellow">🚀Deployment</h3>
<p>
Aplikasi telah saya deploy menggunakan <b>Render</b> dengan mekanisme pull-based deployment. 

<b style="color:cyan;font-size:18px">URL Deployment:</b>
<br>https://module-01-coding-standards.onrender.com
</p>

<h3 style="color:yellow">⚙️Implementasi CI/CD</h3>
<p>
<b style="color:cyan;font-size:18px">Continuous Integration (CI)</b>
<hr>
Dalam melakukan implementasi CI, saya menggunakan Github Actions dengan workflow akan berjalan otomatis ketika terjadi: 

- `push`
- `pull_request`

dengan tahapan utamanya:

1. Checkout repository
2. Setup Java 21
3. Menjalankan unit test
4. Membuat laporan code coverage menggunakan JaCoCo

dengan command utama `./gradlew test`

<b style="color:cyan;font-size:18px">Code Coverage </b><hr>
Code coverage diukur dengan menggunakan <b>JaCoCo</b> yang akan menghasilkan laporan code coverage pada `build/reports/jacoco/test/html/index.html`

Dalam module 1 kemarin, saya melakukan implementasi functional test ketika `create` product dan `homepage`. Namun, pada module 2 saya exclude agar lebih terfokus pada code coverage saja sesuai dengan spesifikasi panduan.
</p>

<h3 style="color:yellow">🔎Code Scanning</h3>
<hr>
<p>
1️⃣ <b>SonarCloud</b>
<br>Untuk melakukan code scanning, saya menggunakan SonarCloud untuk menganalisis:

- Code smells
- Bugs
- Security Hostpots
- Mantainability

dan status akhir Quality Gate: `passed`

2️⃣<b>PMD</b>
<br>Selain itu, saya menggunakan PMD sebagai static code analysis tambahan dengan konfigurasi utama:

- Versi : `7.0.0-rc4`
- Rule set: `bestpractices`

dengan command utama `./gradlew pmdMain`
</p>

<h3 style="color:yellow">☁️Strategi Deployment</h3>
<hr>
<p>
Deployment yang saya lakukan adalah dengan pendekatan <b>pull-based</b> di mana:

- Render memonitor repository Github
- Setiap perubahan pada branch utama akan memicu redeployment otomatis
- Aplikasi dijalankan menggunakan container Docker

Pada tahap deployment ini, saya menggunakan 🐳`Docker` yang akan mengemas aplikasi agar lebih kompatibel dengan environment deployment. Tahapan build yang sudah saya lakukan adalah sebagai berikut:

- Build Spring Boot Jar
- Menjalankan aplikasi pada container berbasis JRE
</p>

<h3 style="color:yellow">📊Refleksi</h3>
<hr>
<b style="color:pink;font-size:16px">1. Perbaikan kualitas kode dan strategi yang dilakukan</b>
<hr>

Selama mengerjakan modul ini, saya dihadapi beberapa masalah yang membuat saya cukup frustasi ketika pada tahap <b>code scannig</b> melalui SonarCloud dan PMD, terlebih ketika project saya tidak teridentifikasi binding ketika menjalankan <b>`./gradlew sonar --info`</b> padahal pada sonar cloud analyze project berhasil dilakukan. Strategi yang saya lakukan dalam menghadapi masalah ini adalah mencoba membuat organization dan analyze project baru. Pada akhirnya, build berhasil dijalankan dan workflow diterima. 

Selain itu, ketidaksesuaian nama template Thymeleaf ketika aplikasi dijalankan pada environment Linux di cloud menjadi salah satu tantangan yang saya hadapi terutama sistem Linux bersifat `case-insensitive` sehingga aplikasi berjalan normal di local tetapi error di `production`. Menghadapi hal ini, saya mencoba mengganti nama template menggunakan command `git mv`, walaupun pada akhirnya error, dan menyesuaikan controller agar `API endpoint` dan nama template sesuai dengan yang ada. 

<b style="color:pink;font-size:16px">2. Evaluasi Implementasi CI/CD</b>
<hr>

Setelah melalui proses yang panjang dan dipenuhi konflik batin, implementasi yang saya lakukan sudah sesuai dan memenuhi konsep `Continuous Integration` karena setiap push secara otomatis menjalankan testing dan code analysis. Hal ini akan membantu saya untuk mendeteksi error lebih awal sebelum kode digabungkan ke branch utama. 

Selain itu, proses deployment otomatis ke Render menunjukkan penerapan `Continuous Deployment` karena aplikasi dapat diperbarui secara otomatis tanpa proses manual tambahan. Secara keseluruhan, pipeline yang dibuat akan membantu meningkatkan kualitas kode, mempercepat proses development, serta memastikan aplikasi selalu dalam kondisi siap digunakan. 

<b style="color:orange;font-size:18px">⌚Pengembangan Lanjutan</b>
<hr>

Perbaikan yang akan saya lakukan ke depannya agar aplikasi dapat berjalan dengan baik dan efisien adalah sebagai berikut:

- Meningkatkan presentase code coverage
- Menambahkan unit test pada layer controller (<i>sudah dilakukan pada module 1</i>)
- Memperluas analisis keamanan pada template Thymelead, seperti `sql injection`, `xss`, dsb

<b style="color:orange;font-size:18px">⚙️Teknologi / Plugins yang Digunakan</b>
<hr>

1. Java 21
2. Spring Boot
3. Gradle (`saya down grade ke 8.7 agar lebih kompatibel dengan versi Spring Boot`)
4. Github Actions
5. JaCoCo
6. SonarCloud
7. PMD
8. Docker
9. Render