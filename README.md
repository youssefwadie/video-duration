# video-duration
Traverse the file system and calculate the videos' duration. 

#### Actual file tree
```bash
❯ tree ~/Videos/
/home/youssef/Videos
├── Messiah 2020 S01 720p NF WEBRip x264
│   ├── Messiah.2020.S01E01.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E02.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E03.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E04.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E05.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E06.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E07.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E08.720p.NF.WEBRip.x264.mkv
│   ├── Messiah.2020.S01E09.720p.NF.WEBRip.x264.mkv
│   └── Messiah.2020.S01E10.720p.NF.WEBRip.x264.mkv
├── Sopranos
│   ├── S01
│   │   ├── The Sopranos S01E01 The Sopranos.mkv
│   │   ├── The Sopranos S01E02 46 Long.mkv
│   │   ├── The Sopranos S01E03 Denial, Anger, Acceptance.mkv
│   │   ├── The Sopranos S01E04 Meadowlands.mkv
│   │   ├── The Sopranos S01E05 College.mkv
│   │   ├── The Sopranos S01E06 Pax Soprana.mkv
│   │   ├── The Sopranos S01E07 Down Neck.mkv
│   │   └── The Sopranos S01E08 The Legend of Tennessee Moltisanti.mkv
│   └── subs.sh
└── Vikings Valhalla S01 720p NF WEBRip x264
    ├── Vikings.Valhalla.S01E01.720p.NF.WEBRip.x264.mkv
    ├── Vikings.Valhalla.S01E02.720p.NF.WEBRip.x264.mkv
    ├── Vikings.Valhalla.S01E03.720p.NF.WEBRip.x264.mkv
    ├── Vikings.Valhalla.S01E04.720p.NF.WEBRip.x264.mkv
    ├── Vikings.Valhalla.S01E05.720p.NF.WEBRip.x264.mkv
    └── Vikings.Valhalla.S01E06.720p.NF.WEBRip.x264.mkv

4 directories, 26 files
```

---

#### Output
```bash
❯ mvn -q exec:java -Dexec.args="-t mp4 mkv -p /home/youssef/Videos"
```
![output](/images/pic1.png?raw=true)

---

#### Run
You can either compile and run the project with `exec-maven-plugin` or create an executable jar. 
Creating a standalone executable jar can be difficult, as the `humble-video` 
library uses native libraries to work with video files, 
but you can take a look at [install script](./install.sh) to see how this can be done.

##### To run with `exec-maven-plugin`
```bash
mvn clean compile
mvn -q exec:java -Dexec.args="-h"
```

---

#### Installation on a GNU/Linux System
```bash
git clone https://github.com/youssefwadie/video-duration.git
cd video-duration
sh install.sh
video-duration -h
```

---
#### Usage
```bash
usage: videos-duration [-d <depth>] [-h] [-p <starting-path>] [-t
       <file-types>] [-v]
traverse the file system tree to calculate the total videos' duration
 -p,--path <starting-path>   starting path to traverse. (defaults to the
                             current path)
 -t,--types <file-types>     file types to be scanned, multiple values,
                             whitespace separated. (default is mp4 and
                             mkv)
 -d,--depth <depth>          maximum depth in the traversal. (defaults to
                             the maximum possible depth)
 -v,--verbose                print errors. (defaults to quite mode)
 -h,--help                   display this help and exit
```

---


The functionality can be easily extended, just implement your own `FileScanner`.  
see the [sample](/src/main/java/com/github/youssefwadie/videoduration/core/BasicScannersProvider.java#L8) and provide it to
the `FileWalker`, [here](/src/main/java/com/github/youssefwadie/videoduration/cli/Main.java#L21)
