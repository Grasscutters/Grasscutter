![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [HE](README_HE.md) | ES  | [RU](README_ru-RU.md)

**Atención:** Siempre damos la bienvenida a contribuidores del proyecto. Antes de añadir tu contribución, por favor lee cuidadosamente nuestro [Código de conducta](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Funcionalidades actuales

* Iniciar sesión
* Combate
* Lista de amigos
* Teletransportación
* Sistema Gacha
* Cooperativo *parcialmente* funcional
* Invocar monstruos desde la consola
* Funcionalidades de inventario (recibir objetos/personajes, mejorar objetos/personajes, etc)

## Guía rápida de configuración

**Nota:** Para soporte, únete a nuestro [Discord](https://discord.gg/T5vZU6UyeG).

### Requerimientos

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Nota:** Si solo quieres **ejecutarlo**, entonces **jre** es suficiente.

* [MongoDB](https://www.mongodb.com/try/download/community) (recomendado 4.0+)

* Servicio de proxy: mitmproxy (mitmdump, recomendado), Fiddler Classic, etc.

### Ejecución

**Nota:** Si actualizaste de una versión anterior, elimina `config.json` para que se genere de nuevo.

1. Consigue `grasscutter.jar`
   - Descarga desde [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Constrúyelo tu mismo](#Construcción)
2. Crea una carpeta `resources` en el directorio donde se encuentra grasscutter.jar y mueve las carpetas `BinOutput` y `ExcelBinOutput` ahí *(Consulta la [wiki](https://github.com/Grasscutters/Grasscutter/wiki) para más detalles de como conseguirlos.)*
3. Ejecuta Grasscutter con `java -jar grasscutter.jar`. **Asegúrate de que el servicio de mongodb está activo.**

### Conexión con el cliente

½. Crea una cuenta usando [el comando correspondiente en la consola del servidor](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Redirecciona el tráfico: (elegir uno)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      Autoriza el certificado CA:
    
      ​	**Nota:**El certificado CA normalmente se encuentra en `%USERPROFILE%\ .mitmproxy`, o puedes descargarlo de `http://mitm.it`
    
      ​	Doble clic para [instalar](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) o ...
    
      - Con línea de comandos
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: Ejecuta Fiddler Classic, activa `Decrypt https traffic` en las opciones y cambia el puerto por defecto ahí (Herramientas -> Opciones -> Conexiones) a alguno que no sea `8888`, y carga [este script](https://github.lunatic.moe/fiddlerscript).
      
    - [Archivo Hosts](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
    
2. Establece el proxy de red a `127.0.0.1:8080` o el puerto de proxy que pusiste.

**también puedes usar `start.cmd` para iniciar el servidor y el servicio de proxy automáticamente, pero tienes que configurar el entorno JAVA_HOME**

### Construcción

Grasscutter usa Gradle para manejar dependencias y construcción.

**Requerimientos:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Configuración de entornos
.\gradlew jar # Compilar
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compilar
```

Podrás encontrar el jar generado en la carpeta raíz del proyecto.

### ¡Los comandos han sido movidos a la [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Soluciones a errores comunes

* Si la compilación falla, por favor comprueba tu instalación de JDK (JDK 17 y valida la variable bin PATH del JDK)
* Mi cliente no conecta, no inicia sesión, 4206, etc... - Probablemente, tu configuración del proxy es *el problema*, si usas
  Fiddler asegúrate de que está usando un puerto distinto al 8888
* Secuencia de inicio: MongoDB > Grasscutter > Servicio de proxy (mitmdump, fiddler, etc.) > Juego
 
