![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md)

**Chú ý:** Chúng tôi luôn chào đón những người đóng góp cho dự án. Trước khi đóng góp, xin vui lòng đọc kỹ ["các quy tắc" (Code of Conduct)](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md) của chúng tôi .

## Các tính năng hiện tại

* Đăng nhập
* Chiến đấu, giao tranh
* Danh sách bạn bè
* Dịch chuyển
* Hệ thống gacha
* *Một phần* của tính năng chơi chung (co-op)
* Gọi ra quái vật từ bảng điều khiển (console)
* Vật phẩm/Nhân vật (nhận vật phẩm/nhân vật, nâng cấp vật phẩm/nhân vật)

## Hướng dẫn cài đặt nhanh

**Ghi chú:** Để được hỗ trợ, vui lòng tham gia [Discord](https://discord.gg/T5vZU6UyeG).

### Phần mềm cần thiết

* [Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) hoặc cao hơn

  **Ghi chú:** Nếu bạn chỉ cần **sử dụng**, thì cài **jre** là đủ.

* [MongoDB](https://www.mongodb.com/try/download/community) (khuyến khích phiên bản từ 4.0 trở lên)

* Proxy Daemon: [mitmproxy](https://mitmproxy.org/) (nên sử dụng mitmdump), [Fiddler Classic](https://telerik-fiddler.s3.amazonaws.com/fiddler/FiddlerSetup.exe), v.v.

### Chạy chương trình (server)

**Ghi chú:** Nếu bạn đã cập nhật từ phiên bản cũ hơn, hãy xóa `config.json` để tạo lại.

1. Tải `grasscutter.jar`
    - Tài về từ [releases (bản phát hành)](https://github.com/Grasscutters/Grasscutter/releases/latest) hoặc [actions (các hoạt động)](https://github.com/Grasscutters/Grasscutter/actions/workflows/build.yml) hoặc [tự tạo của chính bạn](#building).
2. Tạo một thư mục `resources` trong cùng thư mục với Grasscutter.jar và chuyển các thư mục `BinOutput, ExcelBinOutput, Readables, Scripts, Subtitle, TextMap` của bạn đến `resources` _(Xem [wiki](https://github.com/Grasscutters/Grasscutter/wiki) để biết cách lấy các thư mục đó)_
3. Chạy Grasscutter với câu lệnh `java -jar grasscutter.jar`. **Hãy chắc rằng mongodb của bạn đã được chạy**

### Kết nối với game (client)

½. Tạo một tài khoản từ bảng điều khiển máy chủ (server console), sử dụng [câu lệnh (command)](https://github.com/Grasscutters/Grasscutter/wiki/Commands#:~:text=account%20%3Ccreate|delete%3E%20%3Cusername%3E%20[UID]).

1. Chương trình chuyển hướng lưu lượng truy cập: (chỉ sử dụng 1)
    - mitmdump: `mitmdump -s proxy.py -k`

        - Chứng chỉ CA tin cậy:

          - Chứng chỉ CA thường được lưu trữ trong `%USERPROFILE%\.mitmproxy`, click đúp `mitmproxy-ca-cert.cer` để [cài đặt](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) hoặc...

          - Sử dụng với command line (cmd) *(yêu cầu quyền quản trị viên)*

             ```shell
             certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
             ```

    - Fiddler Classic: Mở Fiddler Classic, bật tùy chọn `Decrypt HTTPS traffic` trong (Tools -> Options -> HTTPS) và thay đổi cổng (port) mặc định (Tools -> Options -> Connections) khác `8888`, chạy [script này](https://github.com/Grasscutters/Grasscutter/wiki/Resources#fiddler-classic-jscript) (sao chép và dán script vào trong `FiddlerScript`) và bấm `Save Script`.

    - [Hosts file](https://github.com/Grasscutters/Grasscutter/wiki/Resources#hosts-file)

2. Cài đặt network proxy thành `127.0.0.1:8080` hoặc cổng proxy bạn đã chỉ định.

-   Với mitmproxy: Sau khi thiết lập proxy và cài đặt chứng chỉ, hãy kiểm tra http://mitm.it/ nếu lưu lượng truy cập đi qua mitmproxy.

**You can also use `start.cmd` to start servers and proxy daemons automatically, but you have to set up `JAVA_HOME` environment and configure the `start_config.cmd` file.**

### Tự tạo server (Building)

Grasscutter sử dụng Gradle để xử lý các phần phụ thuộc và xây dựng.

**Phần mềm cần thiết:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) hoặc cao hơn
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Thiết lập môi trường (Setting up environments)
.\gradlew jar # Biên dịch (Compile)
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Biên dịch (Compile)
```

Bạn có thể tìm thấy tệp jar đã được biên dịch tại thư mục gốc của dự án.

### Các câu lệnh (commands) đã được chuyển đến [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Khắc phục nhanh các sụ cố

-   Nếu quá trình biên dịch (compile) không thành công, vui lòng kiểm tra cài đặt JDK của bạn (Đảm bảo rằng JDK phải từ phiên bản 17 trở lên và PATH của JDK đã được cài đặt).
-   Không thể kết nối, không thể đăng nhập, 4206, v.v... - Cài đặt proxy (proxy daemon) của bạn thường là *vấn đề*. Nếu bạn đang sử dụng Fiddler, hãy đổi port (cổng) mặc định khác 8888.
-   Thứ tự cài đặt: MongoDB > Grasscutter > Proxy Daemon (mitmdump, fiddler, v.v.) > Game
