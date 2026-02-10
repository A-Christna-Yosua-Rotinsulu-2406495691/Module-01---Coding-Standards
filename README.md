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

<h2 style="color:yellow">
    Praktik Secure Coding yang Diterapkan
</h2>

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



