![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**Perhatian:** Kami selalu menyambut kontributor untuk proyek ini. Sebelum menambahkan kontribusi Anda, harap baca [Kode Etik](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md) kami.

## Fitur terkini

* Logging in
* Pertempuran
* Daftar teman
* Teleportasi
* Sistem gacha
* Co-op *sebagian* berfungsi
* Memunculkan monster melalui konsol
* Fitur inventaris (menerima item/karakter, meng-upgrade item/karakter, dll)

## Panduan penyiapan cepat

**Catatan:** Untuk dukungan, silakan bergabung dengan [Discord](https://discord.gg/T5vZU6UyeG) kami.

### Requirements

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Catatan:** Jika Anda hanya ingin **menjalankannya**, maka **jre** saja sudah cukup.

* [MongoDB](https://www.mongodb.com/try/download/community) (recommended 4.0+)

* Daemon proksi: mitmproxy (mitmdump, direkomendasikan), Fiddler Classic, etc.

### Menjalankan

**Catatan:** Jika Anda memperbarui dari versi lama, hapus `config.json` untuk membuatnya kembali.

1. Dapatkan `grasscutter.jar`
   - Download dari [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Bangun sendiri](#Membangun)
2. Buat folder `resources` di direktori tempat grasscutter.jar berada dan pindahkan folder `BinOutput` dan `ExcelBinOutput` ke sana *(Periksa [wiki](https://github.com/Grasscutters/Grasscutter/wiki) untuk detail lebih lanjut tentang cara mendapatkannya.)*
3. Jalankan Grasscutter dengan `java -jar grasscutter.jar`. **Pastikan layanan mongodb juga berjalan.**

### Connecting to the client

½. Buat akun dengan menggunakan [server console command](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Pengalihan traffic: (pilih salah satu)
    - mitmdump: `mitmdump -s proxy.py -k`

      Trust CA certificate:

      ​**Catatan:** Sertifikat CA biasanya disimpan di `%USERPROFILE%\ .mitmproxy`, atau anda dapat download dari  `http://mitm.it`

      ​	klik dua kali untuk [menginstall](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) ataupun juga

      - melalui command line

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Fiddler Classic: Jalankan Fiddler Classic, nyalakan `Decrypt https traffic` dalam setting dan ubah port default di sana (Tools -> Options -> Connections) ke apa pun selain `8888`, dan muat [skrip ini](https://github.lunatic.moe/fiddlerscript).

    - [File host](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. Atur proxy jaringan ke `127.0.0.1:8080` atau port proxy yang anda tentukan.

**Anda juga dapat menggunakan `start.cmd` untuk memulai server dan proxy daemon secara otomatis, tetapi Anda harus mengatur JAVA_HOME enviroment**

### Membangun

Grasscutter menggunakan Gradle untuk menangani dependensi & pembangunan.

**Requirements:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile
```

Anda bisa menemukan output jar di root folder proyek.

### Commands telah dipindahkan ke [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Quick Troubleshooting

* Jika kompilasi tidak berhasil, periksa instalasi JDK Anda (JDK 17 dan validasi variabel bin PATH JDK)
* Klien saya tidak terhubung, tidak login, 4206, dan lain-lain - Sebagian besar pengaturan daemon proxy Anda adalah *masalahnya*, jika menggunakan
Fiddler pastikan berjalan pada port lain kecuali 8888
* Urutan startup: MongoDB > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Game
