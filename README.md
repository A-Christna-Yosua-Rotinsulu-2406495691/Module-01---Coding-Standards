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





